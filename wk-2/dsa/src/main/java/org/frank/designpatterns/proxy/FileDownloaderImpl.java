package org.frank.designpatterns.proxy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Implementation of the FileDownloader interface.
 * This is the "RealSubject" in the Proxy pattern.
 */
public class FileDownloaderImpl implements FileDownloader {
    
    private final ExecutorService executorService;
    private final Map<String, DownloadInfo> downloads;
    private final Random random;
    
    /**
     * Constructor for FileDownloaderImpl.
     */
    public FileDownloaderImpl() {
        this.executorService = Executors.newFixedThreadPool(5); // Allow 5 concurrent downloads
        this.downloads = new ConcurrentHashMap<>();
        this.random = new Random();
    }
    
    @Override
    public boolean downloadFile(String url, File destination) throws FileDownloadException {
        // Check if already downloading
        if (isDownloading(url)) {
            throw new FileDownloadException("Already downloading: " + url, "ALREADY_DOWNLOADING");
        }
        
        // Create download info
        DownloadInfo downloadInfo = new DownloadInfo(url, destination);
        downloads.put(url, downloadInfo);
        
        // Start download in a separate thread
        Future<?> future = executorService.submit(() -> {
            try {
                simulateDownload(downloadInfo);
            } catch (Exception e) {
                downloadInfo.setError(e);
                downloadInfo.setCompleted(true);
                System.err.println("Download error for " + url + ": " + e.getMessage());
            }
        });
        
        downloadInfo.setFuture(future);
        
        return true;
    }
    
    @Override
    public int getDownloadProgress(String url) {
        DownloadInfo info = downloads.get(url);
        if (info == null) {
            return -1;
        }
        return info.getProgress();
    }
    
    @Override
    public boolean cancelDownload(String url) {
        DownloadInfo info = downloads.get(url);
        if (info == null || info.isCompleted()) {
            return false;
        }
        
        info.getFuture().cancel(true);
        info.setCompleted(true);
        info.setCancelled(true);
        
        System.out.println("Download cancelled: " + url);
        return true;
    }
    
    @Override
    public long getDownloadSpeed(String url) {
        DownloadInfo info = downloads.get(url);
        if (info == null || info.isCompleted()) {
            return -1;
        }
        return info.getSpeed();
    }
    
    @Override
    public long getEstimatedTimeRemaining(String url) {
        DownloadInfo info = downloads.get(url);
        if (info == null || info.isCompleted()) {
            return -1;
        }
        
        long speed = info.getSpeed();
        if (speed <= 0) {
            return -1;
        }
        
        long remaining = info.getTotalSize() - info.getDownloadedSize();
        return remaining / speed;
    }
    
    @Override
    public boolean isDownloading(String url) {
        DownloadInfo info = downloads.get(url);
        return info != null && !info.isCompleted();
    }
    
    /**
     * Simulate downloading a file.
     * 
     * @param downloadInfo The download information
     * @throws IOException If there is an error downloading the file
     * @throws InterruptedException If the download is interrupted
     */
    private void simulateDownload(DownloadInfo downloadInfo) throws IOException, InterruptedException {
        String url = downloadInfo.getUrl();
        File destination = downloadInfo.getDestination();
        
        System.out.println("Starting download: " + url + " to " + destination.getAbsolutePath());
        
        // Simulate file size determination
        long fileSize = 1024 * 1024 * (5 + random.nextInt(20)); // 5-25 MB
        downloadInfo.setTotalSize(fileSize);
        
        // Create parent directories if they don't exist
        if (!destination.getParentFile().exists()) {
            destination.getParentFile().mkdirs();
        }
        
        // Simulate download
        try (FileOutputStream fos = new FileOutputStream(destination)) {
            long downloadedSize = 0;
            long startTime = System.currentTimeMillis();
            
            // Download in chunks
            byte[] buffer = new byte[8192];
            int chunkSize;
            
            while (downloadedSize < fileSize) {
                // Check if cancelled
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException("Download interrupted");
                }
                
                // Simulate network delay
                Thread.sleep(50 + random.nextInt(50));
                
                // Simulate reading a chunk
                chunkSize = (int) Math.min(buffer.length, fileSize - downloadedSize);
                
                // Fill buffer with random data
                random.nextBytes(buffer);
                
                // Write to file
                fos.write(buffer, 0, chunkSize);
                downloadedSize += chunkSize;
                
                // Update progress
                downloadInfo.setDownloadedSize(downloadedSize);
                int progress = (int) ((downloadedSize * 100) / fileSize);
                downloadInfo.setProgress(progress);
                
                // Update speed
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - startTime;
                if (elapsedTime > 0) {
                    long speed = downloadedSize * 1000 / elapsedTime; // bytes per second
                    downloadInfo.setSpeed(speed);
                }
                
                System.out.println("Download progress for " + url + ": " + progress + "%, Speed: " + 
                                  (downloadInfo.getSpeed() / 1024) + " KB/s");
            }
            
            // Download completed
            downloadInfo.setCompleted(true);
            System.out.println("Download completed: " + url);
        } catch (IOException e) {
            System.err.println("IO error during download: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Class to store information about a download.
     */
    private static class DownloadInfo {
        private final String url;
        private final File destination;
        private long totalSize;
        private long downloadedSize;
        private int progress;
        private long speed;
        private boolean completed;
        private boolean cancelled;
        private Exception error;
        private Future<?> future;
        
        public DownloadInfo(String url, File destination) {
            this.url = url;
            this.destination = destination;
            this.totalSize = 0;
            this.downloadedSize = 0;
            this.progress = 0;
            this.speed = 0;
            this.completed = false;
            this.cancelled = false;
            this.error = null;
        }
        
        public String getUrl() {
            return url;
        }
        
        public File getDestination() {
            return destination;
        }
        
        public long getTotalSize() {
            return totalSize;
        }
        
        public void setTotalSize(long totalSize) {
            this.totalSize = totalSize;
        }
        
        public long getDownloadedSize() {
            return downloadedSize;
        }
        
        public void setDownloadedSize(long downloadedSize) {
            this.downloadedSize = downloadedSize;
        }
        
        public int getProgress() {
            return progress;
        }
        
        public void setProgress(int progress) {
            this.progress = progress;
        }
        
        public long getSpeed() {
            return speed;
        }
        
        public void setSpeed(long speed) {
            this.speed = speed;
        }
        
        public boolean isCompleted() {
            return completed;
        }
        
        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
        
        public boolean isCancelled() {
            return cancelled;
        }
        
        public void setCancelled(boolean cancelled) {
            this.cancelled = cancelled;
        }
        
        public Exception getError() {
            return error;
        }
        
        public void setError(Exception error) {
            this.error = error;
        }
        
        public Future<?> getFuture() {
            return future;
        }
        
        public void setFuture(Future<?> future) {
            this.future = future;
        }
    }
    
    /**
     * Shutdown the executor service.
     */
    public void shutdown() {
        executorService.shutdown();
    }
}