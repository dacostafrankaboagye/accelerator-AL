package org.frank.designpatterns.proxy;

import java.io.File;

/**
 * Example class demonstrating the usage of the FileDownloaderProxy.
 */
public class FileDownloaderProxyExample {
    
    public static void main(String[] args) {
        System.out.println("File Downloader Proxy Example");
        System.out.println("-----------------------------");
        
        // Create the real downloader
        FileDownloader realDownloader = new FileDownloaderImpl();
        
        // Create the proxy with authorization and progress reporting
        FileDownloaderProxy proxy = new FileDownloaderProxy(realDownloader);
        
        // Add authorized users
        proxy.addAuthorizedUser("alice");
        proxy.addAuthorizedUser("bob");
        proxy.addAuthorizedUser("charlie");
        
        // Set the current user
        try {
            proxy.setCurrentUser("alice");
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
        
        System.out.println("\nExample 1: Downloading a file with authorization");
        // Create a temporary directory for downloads
        File downloadDir = new File("downloads");
        if (!downloadDir.exists()) {
            downloadDir.mkdir();
        }
        
        // Add a download listener
        proxy.addDownloadListener("http://example.com/sample.pdf", new DownloadProgressReporter());
        
        // Download a file
        try {
            File destination = new File(downloadDir, "sample.pdf");
            boolean started = proxy.downloadFile("http://example.com/sample.pdf", destination);
            System.out.println("Download started: " + started);
            
            // Wait for a moment to see some progress
            System.out.println("Waiting for download progress...");
            Thread.sleep(3000);
            
            // Check progress
            int progress = proxy.getDownloadProgress("http://example.com/sample.pdf");
            System.out.println("Current progress: " + progress + "%");
            
            // Get download speed
            long speed = proxy.getDownloadSpeed("http://example.com/sample.pdf");
            System.out.println("Download speed: " + (speed / 1024) + " KB/s");
            
            // Get estimated time remaining
            long timeRemaining = proxy.getEstimatedTimeRemaining("http://example.com/sample.pdf");
            System.out.println("Estimated time remaining: " + timeRemaining + " seconds");
            
        } catch (FileDownloadException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
        
        System.out.println("\nExample 2: Attempting to download a disallowed file type");
        try {
            File destination = new File(downloadDir, "malware.exe");
            proxy.downloadFile("http://example.com/malware.exe", destination);
        } catch (FileDownloadException e) {
            System.err.println("Error: " + e.getMessage() + " (Code: " + e.getErrorCode() + ")");
        }
        
        System.out.println("\nExample 3: Attempting to download a file that's too large");
        try {
            File destination = new File(downloadDir, "large_file.zip");
            proxy.downloadFile("http://example.com/large_file_200MB.zip", destination);
        } catch (FileDownloadException e) {
            System.err.println("Error: " + e.getMessage() + " (Code: " + e.getErrorCode() + ")");
        }
        
        System.out.println("\nExample 4: Changing users and attempting to cancel another user's download");
        // Start a download as alice
        try {
            File destination = new File(downloadDir, "alice_file.pdf");
            proxy.downloadFile("http://example.com/alice_file.pdf", destination);
            
            // Change to bob
            proxy.setCurrentUser("bob");
            
            // Try to cancel alice's download
            boolean cancelled = proxy.cancelDownload("http://example.com/alice_file.pdf");
            System.out.println("Download cancelled by bob: " + cancelled);
            
            // Change back to alice
            proxy.setCurrentUser("alice");
            
            // Alice cancels her own download
            cancelled = proxy.cancelDownload("http://example.com/alice_file.pdf");
            System.out.println("Download cancelled by alice: " + cancelled);
            
        } catch (FileDownloadException e) {
            System.err.println("Error: " + e.getMessage() + " (Code: " + e.getErrorCode() + ")");
        }
        
        System.out.println("\nExample 5: Adding a new allowed file type");
        // Add a new allowed file type
        proxy.addAllowedFileType("docx");
        
        // Try to download a docx file
        try {
            File destination = new File(downloadDir, "document.docx");
            boolean started = proxy.downloadFile("http://example.com/document.docx", destination);
            System.out.println("Download started: " + started);
        } catch (FileDownloadException e) {
            System.err.println("Error: " + e.getMessage() + " (Code: " + e.getErrorCode() + ")");
        }
        
        // Shutdown the proxy and downloader
        proxy.shutdown();
        if (realDownloader instanceof FileDownloaderImpl) {
            ((FileDownloaderImpl) realDownloader).shutdown();
        }
        
        System.out.println("\nProxy Pattern Benefits:");
        System.out.println("1. Controls access to the real object");
        System.out.println("2. Adds authorization checks before allowing operations");
        System.out.println("3. Provides progress reporting through listeners");
        System.out.println("4. Validates file types and sizes before downloading");
        System.out.println("5. Ensures only the owner of a download can cancel it");
        System.out.println("6. Provides a layer of protection for the real downloader");
    }
    
    /**
     * Implementation of the DownloadListener interface for reporting download progress.
     */
    private static class DownloadProgressReporter implements FileDownloaderProxy.DownloadListener {
        
        @Override
        public void onDownloadStarted(String url) {
            System.out.println("Download started: " + url);
        }
        
        @Override
        public void onDownloadProgress(String url, int progress, long speed, long timeRemaining) {
            System.out.println("Download progress: " + progress + "%, Speed: " + 
                              (speed / 1024) + " KB/s, Time remaining: " + timeRemaining + " seconds");
        }
        
        @Override
        public void onDownloadCompleted(String url) {
            System.out.println("Download completed: " + url);
        }
        
        @Override
        public void onDownloadCancelled(String url) {
            System.out.println("Download cancelled: " + url);
        }
    }
}