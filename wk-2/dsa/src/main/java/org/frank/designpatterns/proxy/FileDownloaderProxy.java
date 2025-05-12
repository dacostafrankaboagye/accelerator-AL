package org.frank.designpatterns.proxy;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Proxy for the FileDownloader that adds authorization checks and progress reporting.
 * This is the "Proxy" in the Proxy pattern.
 */
public class FileDownloaderProxy implements FileDownloader {

    private final FileDownloader realDownloader;
    private final Set<String> authorizedUsers;
    private final Map<String, DownloadListener> listeners;
    private final ScheduledExecutorService progressReporter;
    private final Map<String, String> downloadOwners; // URL -> User
    private final Set<String> allowedFileTypes;
    private final long maxFileSize;
    private String currentUser;

    /**
     * Constructor for FileDownloaderProxy with custom parameters.
     * 
     * @param realDownloader The real file downloader
     * @param maxFileSize The maximum allowed file size in bytes
     */
    public FileDownloaderProxy(FileDownloader realDownloader, long maxFileSize) {
        this.realDownloader = realDownloader;
        this.authorizedUsers = new HashSet<>();
        this.listeners = new HashMap<>();
        this.progressReporter = Executors.newScheduledThreadPool(1);
        this.downloadOwners = new HashMap<>();
        this.allowedFileTypes = new HashSet<>();
        this.maxFileSize = maxFileSize;
        this.currentUser = null;

        // Add some default allowed file types
        allowedFileTypes.add("jpg");
        allowedFileTypes.add("png");
        allowedFileTypes.add("pdf");
        allowedFileTypes.add("txt");
        allowedFileTypes.add("zip");

        // Start the progress reporter
        startProgressReporter();
    }

    /**
     * Constructor for FileDownloaderProxy with default parameters.
     * 
     * @param realDownloader The real file downloader
     */
    public FileDownloaderProxy(FileDownloader realDownloader) {
        this(realDownloader, 100 * 1024 * 1024); // 100 MB max file size
    }

    /**
     * Set the current user.
     * 
     * @param username The username
     * @throws IllegalArgumentException If the user is not authorized
     */
    public void setCurrentUser(String username) {
        if (!authorizedUsers.contains(username)) {
            throw new IllegalArgumentException("User not authorized: " + username);
        }
        this.currentUser = username;
        System.out.println("Current user set to: " + username);
    }

    /**
     * Add an authorized user.
     * 
     * @param username The username to authorize
     */
    public void addAuthorizedUser(String username) {
        authorizedUsers.add(username);
        System.out.println("Added authorized user: " + username);
    }

    /**
     * Remove an authorized user.
     * 
     * @param username The username to remove
     * @return true if the user was removed, false if they weren't authorized
     */
    public boolean removeAuthorizedUser(String username) {
        boolean removed = authorizedUsers.remove(username);
        if (removed) {
            System.out.println("Removed authorized user: " + username);
        }
        return removed;
    }

    /**
     * Add an allowed file type.
     * 
     * @param fileExtension The file extension to allow (without the dot)
     */
    public void addAllowedFileType(String fileExtension) {
        allowedFileTypes.add(fileExtension.toLowerCase());
        System.out.println("Added allowed file type: " + fileExtension);
    }

    /**
     * Remove an allowed file type.
     * 
     * @param fileExtension The file extension to disallow
     * @return true if the file type was removed, false if it wasn't allowed
     */
    public boolean removeAllowedFileType(String fileExtension) {
        boolean removed = allowedFileTypes.remove(fileExtension.toLowerCase());
        if (removed) {
            System.out.println("Removed allowed file type: " + fileExtension);
        }
        return removed;
    }

    /**
     * Add a download listener for a URL.
     * 
     * @param url The URL to listen for
     * @param listener The listener to add
     */
    public void addDownloadListener(String url, DownloadListener listener) {
        listeners.put(url, listener);
    }

    /**
     * Remove a download listener for a URL.
     * 
     * @param url The URL to stop listening for
     * @return true if a listener was removed, false otherwise
     */
    public boolean removeDownloadListener(String url) {
        return listeners.remove(url) != null;
    }

    @Override
    public boolean downloadFile(String url, File destination) throws FileDownloadException {
        // Check if user is authorized
        if (currentUser == null) {
            throw new FileDownloadException("No user is currently set", "NO_USER");
        }

        // Check file type
        String fileExtension = getFileExtension(url);
        if (!allowedFileTypes.contains(fileExtension.toLowerCase())) {
            throw new FileDownloadException("File type not allowed: " + fileExtension, "INVALID_FILE_TYPE");
        }

        // Check file size (in a real implementation, this would involve a HEAD request)
        // For simulation, we'll assume the file size is 10MB per MB in the URL (if specified)
        long estimatedSize = estimateFileSize(url);
        if (estimatedSize > maxFileSize) {
            throw new FileDownloadException("File size exceeds maximum allowed size", "FILE_TOO_LARGE");
        }

        System.out.println("Authorization check passed for user: " + currentUser);
        System.out.println("File type check passed for extension: " + fileExtension);
        System.out.println("File size check passed for estimated size: " + estimatedSize + " bytes");

        // Record the download owner
        downloadOwners.put(url, currentUser);

        // Notify listener that download is starting
        DownloadListener listener = listeners.get(url);
        if (listener != null) {
            listener.onDownloadStarted(url);
        }

        // Start the actual download
        return realDownloader.downloadFile(url, destination);
    }

    @Override
    public int getDownloadProgress(String url) {
        return realDownloader.getDownloadProgress(url);
    }

    @Override
    public boolean cancelDownload(String url) {
        // Check if the current user owns this download
        String owner = downloadOwners.get(url);
        if (owner != null && !owner.equals(currentUser)) {
            System.out.println("Authorization failed: Only the user who started the download can cancel it");
            return false;
        }

        boolean cancelled = realDownloader.cancelDownload(url);

        if (cancelled) {
            // Notify listener that download was cancelled
            DownloadListener listener = listeners.get(url);
            if (listener != null) {
                listener.onDownloadCancelled(url);
            }

            // Remove the download owner
            downloadOwners.remove(url);
        }

        return cancelled;
    }

    @Override
    public long getDownloadSpeed(String url) {
        return realDownloader.getDownloadSpeed(url);
    }

    @Override
    public long getEstimatedTimeRemaining(String url) {
        return realDownloader.getEstimatedTimeRemaining(url);
    }

    @Override
    public boolean isDownloading(String url) {
        return realDownloader.isDownloading(url);
    }

    /**
     * Start the progress reporter.
     */
    private void startProgressReporter() {
        progressReporter.scheduleAtFixedRate(() -> {
            for (Map.Entry<String, DownloadListener> entry : listeners.entrySet()) {
                String url = entry.getKey();
                DownloadListener listener = entry.getValue();

                if (realDownloader.isDownloading(url)) {
                    int progress = realDownloader.getDownloadProgress(url);
                    long speed = realDownloader.getDownloadSpeed(url);
                    long timeRemaining = realDownloader.getEstimatedTimeRemaining(url);

                    listener.onDownloadProgress(url, progress, speed, timeRemaining);
                } else if (progress != null && progress.containsKey(url) && progress.get(url) == 100) {
                    // Download completed
                    listener.onDownloadCompleted(url);
                    progress.remove(url);
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    // Keep track of completed downloads
    private final Map<String, Integer> progress = new HashMap<>();

    /**
     * Get the file extension from a URL.
     * 
     * @param url The URL
     * @return The file extension (without the dot)
     */
    private String getFileExtension(String url) {
        int lastDotPos = url.lastIndexOf('.');
        if (lastDotPos == -1 || lastDotPos == url.length() - 1) {
            return "";
        }

        // Check if there's a query string or fragment
        int queryPos = url.indexOf('?', lastDotPos);
        int fragmentPos = url.indexOf('#', lastDotPos);

        if (queryPos != -1 || fragmentPos != -1) {
            int endPos = (queryPos != -1) ? queryPos : url.length();
            endPos = (fragmentPos != -1 && fragmentPos < endPos) ? fragmentPos : endPos;
            return url.substring(lastDotPos + 1, endPos);
        }

        return url.substring(lastDotPos + 1);
    }

    /**
     * Estimate the file size from a URL.
     * This is a simulation - in a real implementation, this would involve a HEAD request.
     * 
     * @param url The URL
     * @return The estimated file size in bytes
     */
    private long estimateFileSize(String url) {
        // For simulation, we'll look for a pattern like "10MB" in the URL
        // and use that as the file size
        String lowerUrl = url.toLowerCase();
        for (int i = 1; i <= 1000; i++) {
            String pattern = i + "mb";
            if (lowerUrl.contains(pattern)) {
                return i * 1024 * 1024L;
            }
        }

        // Default to 5MB if no size is specified
        return 5 * 1024 * 1024L;
    }

    /**
     * Shutdown the proxy.
     */
    public void shutdown() {
        progressReporter.shutdown();
    }

    /**
     * Interface for download listeners.
     */
    public interface DownloadListener {
        /**
         * Called when a download starts.
         * 
         * @param url The URL being downloaded
         */
        void onDownloadStarted(String url);

        /**
         * Called when download progress is updated.
         * 
         * @param url The URL being downloaded
         * @param progress The download progress (0-100)
         * @param speed The download speed in bytes per second
         * @param timeRemaining The estimated time remaining in seconds
         */
        void onDownloadProgress(String url, int progress, long speed, long timeRemaining);

        /**
         * Called when a download completes.
         * 
         * @param url The URL that was downloaded
         */
        void onDownloadCompleted(String url);

        /**
         * Called when a download is cancelled.
         * 
         * @param url The URL that was being downloaded
         */
        void onDownloadCancelled(String url);
    }
}
