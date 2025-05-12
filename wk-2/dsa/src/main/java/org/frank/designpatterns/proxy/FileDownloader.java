package org.frank.designpatterns.proxy;

import java.io.File;

/**
 * FileDownloader interface that defines operations for downloading files.
 * This is the "Subject" in the Proxy pattern.
 */
public interface FileDownloader {
    
    /**
     * Download a file from a URL.
     * 
     * @param url The URL of the file to download
     * @param destination The destination file
     * @return true if the download was successful, false otherwise
     * @throws FileDownloadException If there is an error downloading the file
     */
    boolean downloadFile(String url, File destination) throws FileDownloadException;
    
    /**
     * Get the download progress for a URL.
     * 
     * @param url The URL of the file
     * @return The download progress as a percentage (0-100), or -1 if not downloading
     */
    int getDownloadProgress(String url);
    
    /**
     * Cancel a download.
     * 
     * @param url The URL of the file to cancel
     * @return true if the download was cancelled, false if it wasn't downloading
     */
    boolean cancelDownload(String url);
    
    /**
     * Get the download speed for a URL.
     * 
     * @param url The URL of the file
     * @return The download speed in bytes per second, or -1 if not downloading
     */
    long getDownloadSpeed(String url);
    
    /**
     * Get the estimated time remaining for a download.
     * 
     * @param url The URL of the file
     * @return The estimated time remaining in seconds, or -1 if not downloading
     */
    long getEstimatedTimeRemaining(String url);
    
    /**
     * Check if a file is currently being downloaded.
     * 
     * @param url The URL of the file
     * @return true if the file is being downloaded, false otherwise
     */
    boolean isDownloading(String url);
}