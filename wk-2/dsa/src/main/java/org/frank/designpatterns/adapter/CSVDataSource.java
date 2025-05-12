package org.frank.designpatterns.adapter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class for reading and writing CSV files.
 * This represents the "Adaptee" in the Adapter pattern - a legacy or incompatible class
 * that we want to use through a different interface.
 */
public class CSVDataSource {
    
    private String filePath;
    private String delimiter;
    private boolean hasHeader;
    private List<String> headers;
    private List<List<String>> data;
    
    /**
     * Constructor for CSVDataSource.
     * 
     * @param filePath The path to the CSV file
     * @param delimiter The delimiter used in the CSV file (e.g., ",", ";")
     * @param hasHeader Whether the CSV file has a header row
     */
    public CSVDataSource(String filePath, String delimiter, boolean hasHeader) {
        this.filePath = filePath;
        this.delimiter = delimiter;
        this.hasHeader = hasHeader;
        this.headers = new ArrayList<>();
        this.data = new ArrayList<>();
    }
    
    /**
     * Load data from the CSV file.
     * 
     * @return true if the data was loaded successfully, false otherwise
     */
    public boolean loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                List<String> values = parseCSVLine(line);
                
                if (lineNumber == 0 && hasHeader) {
                    headers = values;
                } else {
                    data.add(values);
                }
                
                lineNumber++;
            }
            
            return true;
        } catch (IOException e) {
            System.err.println("Error loading CSV file: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Save data to the CSV file.
     * 
     * @return true if the data was saved successfully, false otherwise
     */
    public boolean saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write header if it exists
            if (hasHeader && !headers.isEmpty()) {
                writer.write(String.join(delimiter, headers));
                writer.newLine();
            }
            
            // Write data rows
            for (List<String> row : data) {
                writer.write(String.join(delimiter, row));
                writer.newLine();
            }
            
            return true;
        } catch (IOException e) {
            System.err.println("Error saving CSV file: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Parse a CSV line into a list of values.
     * 
     * @param line The CSV line to parse
     * @return A list of values
     */
    private List<String> parseCSVLine(String line) {
        // This is a simple implementation that doesn't handle quoted values with delimiters
        return Arrays.asList(line.split(delimiter));
    }
    
    /**
     * Get all rows from the CSV file.
     * 
     * @return A list of rows, where each row is a list of values
     */
    public List<List<String>> getAllRows() {
        return new ArrayList<>(data);
    }
    
    /**
     * Get a specific row by index.
     * 
     * @param rowIndex The index of the row to retrieve
     * @return The row as a list of values, or null if the index is out of bounds
     */
    public List<String> getRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < data.size()) {
            return new ArrayList<>(data.get(rowIndex));
        }
        return null;
    }
    
    /**
     * Add a new row to the CSV data.
     * 
     * @param values The values to add as a row
     * @return The index of the newly added row
     */
    public int addRow(List<String> values) {
        data.add(new ArrayList<>(values));
        return data.size() - 1;
    }
    
    /**
     * Update an existing row.
     * 
     * @param rowIndex The index of the row to update
     * @param values The new values for the row
     * @return true if the row was updated successfully, false if the index is out of bounds
     */
    public boolean updateRow(int rowIndex, List<String> values) {
        if (rowIndex >= 0 && rowIndex < data.size()) {
            data.set(rowIndex, new ArrayList<>(values));
            return true;
        }
        return false;
    }
    
    /**
     * Delete a row.
     * 
     * @param rowIndex The index of the row to delete
     * @return true if the row was deleted successfully, false if the index is out of bounds
     */
    public boolean deleteRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < data.size()) {
            data.remove(rowIndex);
            return true;
        }
        return false;
    }
    
    /**
     * Get the headers of the CSV file.
     * 
     * @return A list of header names, or an empty list if there are no headers
     */
    public List<String> getHeaders() {
        return new ArrayList<>(headers);
    }
    
    /**
     * Set the headers of the CSV file.
     * 
     * @param headers The new headers
     */
    public void setHeaders(List<String> headers) {
        this.headers = new ArrayList<>(headers);
        this.hasHeader = true;
    }
    
    /**
     * Find rows that match a specific value in a column.
     * 
     * @param columnIndex The index of the column to search in
     * @param value The value to search for
     * @return A list of matching row indices
     */
    public List<Integer> findRowsByColumnValue(int columnIndex, String value) {
        List<Integer> matchingRows = new ArrayList<>();
        
        for (int i = 0; i < data.size(); i++) {
            List<String> row = data.get(i);
            if (columnIndex < row.size() && row.get(columnIndex).equals(value)) {
                matchingRows.add(i);
            }
        }
        
        return matchingRows;
    }
    
    /**
     * Find rows that match a specific value in a column by header name.
     * 
     * @param columnName The name of the column to search in
     * @param value The value to search for
     * @return A list of matching row indices
     */
    public List<Integer> findRowsByColumnValue(String columnName, String value) {
        if (!hasHeader || !headers.contains(columnName)) {
            return new ArrayList<>();
        }
        
        int columnIndex = headers.indexOf(columnName);
        return findRowsByColumnValue(columnIndex, value);
    }
    
    /**
     * Get the number of rows in the CSV data.
     * 
     * @return The number of rows
     */
    public int getRowCount() {
        return data.size();
    }
    
    /**
     * Get the number of columns in the CSV data.
     * 
     * @return The number of columns, or 0 if there is no data
     */
    public int getColumnCount() {
        if (hasHeader && !headers.isEmpty()) {
            return headers.size();
        } else if (!data.isEmpty()) {
            return data.get(0).size();
        }
        return 0;
    }
    
    /**
     * Get the file path of the CSV file.
     * 
     * @return The file path
     */
    public String getFilePath() {
        return filePath;
    }
    
    /**
     * Get the delimiter used in the CSV file.
     * 
     * @return The delimiter
     */
    public String getDelimiter() {
        return delimiter;
    }
    
    /**
     * Check if the CSV file has a header row.
     * 
     * @return true if the CSV file has a header row, false otherwise
     */
    public boolean hasHeader() {
        return hasHeader;
    }
}