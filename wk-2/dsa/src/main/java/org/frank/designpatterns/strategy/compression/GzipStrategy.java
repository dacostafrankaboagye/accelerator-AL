package org.frank.designpatterns.strategy.compression;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Concrete implementation of the CompressionStrategy interface using GZIP compression.
 */
public class GzipStrategy implements CompressionStrategy {

    @Override
    public File compress(File sourceFile, String destinationPath) throws IOException {
        // Create the destination file with .gz extension
        String gzipFileName = sourceFile.getName() + getFileExtension();
        File gzipFile = new File(destinationPath, gzipFileName);
        
        try (FileInputStream fis = new FileInputStream(sourceFile);
             FileOutputStream fos = new FileOutputStream(gzipFile);
             GZIPOutputStream gzos = new GZIPOutputStream(fos)) {
            
            // Transfer bytes from the file to the GZIP file
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                gzos.write(buffer, 0, length);
            }
            
            System.out.println("File compressed successfully using GZIP: " + gzipFile.getAbsolutePath());
            return gzipFile;
        }
    }

    @Override
    public File decompress(File compressedFile, String destinationPath) throws IOException {
        // Create the destination directory if it doesn't exist
        File destDir = new File(destinationPath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        
        // Remove the .gz extension to get the original file name
        String originalFileName = compressedFile.getName();
        if (originalFileName.endsWith(getFileExtension())) {
            originalFileName = originalFileName.substring(0, originalFileName.length() - getFileExtension().length());
        }
        
        File decompressedFile = new File(destinationPath, originalFileName);
        
        try (GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(compressedFile));
             FileOutputStream fos = new FileOutputStream(decompressedFile)) {
            
            // Transfer bytes from the GZIP file to the output file
            byte[] buffer = new byte[1024];
            int length;
            while ((length = gzis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            
            System.out.println("File decompressed successfully using GZIP: " + decompressedFile.getAbsolutePath());
            return decompressedFile;
        }
    }

    @Override
    public String getName() {
        return "GZIP Compression";
    }

    @Override
    public String getFileExtension() {
        return ".gz";
    }
}