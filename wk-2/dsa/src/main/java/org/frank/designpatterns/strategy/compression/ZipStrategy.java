package org.frank.designpatterns.strategy.compression;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Concrete implementation of the CompressionStrategy interface using ZIP compression.
 */
public class ZipStrategy implements CompressionStrategy {

    @Override
    public File compress(File sourceFile, String destinationPath) throws IOException {
        // Create the destination file with .zip extension
        String zipFileName = sourceFile.getName() + getFileExtension();
        File zipFile = new File(destinationPath, zipFileName);
        
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             ZipOutputStream zos = new ZipOutputStream(fos);
             FileInputStream fis = new FileInputStream(sourceFile)) {
            
            // Create a new ZIP entry
            ZipEntry zipEntry = new ZipEntry(sourceFile.getName());
            zos.putNextEntry(zipEntry);
            
            // Transfer bytes from the file to the ZIP file
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }
            
            // Close the ZIP entry
            zos.closeEntry();
            
            System.out.println("File compressed successfully using ZIP: " + zipFile.getAbsolutePath());
            return zipFile;
        }
    }

    @Override
    public File decompress(File compressedFile, String destinationPath) throws IOException {
        // Create the destination directory if it doesn't exist
        File destDir = new File(destinationPath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        
        File extractedFile = null;
        
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(compressedFile))) {
            ZipEntry zipEntry = zis.getNextEntry();
            
            while (zipEntry != null) {
                String fileName = zipEntry.getName();
                extractedFile = new File(destinationPath, fileName);
                
                // Create directories if needed
                if (zipEntry.isDirectory()) {
                    extractedFile.mkdirs();
                } else {
                    // Create parent directories if needed
                    new File(extractedFile.getParent()).mkdirs();
                    
                    // Extract the file
                    try (FileOutputStream fos = new FileOutputStream(extractedFile)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                }
                
                zis.closeEntry();
                zipEntry = zis.getNextEntry();
            }
            
            System.out.println("File decompressed successfully using ZIP: " + extractedFile.getAbsolutePath());
            return extractedFile;
        }
    }

    @Override
    public String getName() {
        return "ZIP Compression";
    }

    @Override
    public String getFileExtension() {
        return ".zip";
    }
}