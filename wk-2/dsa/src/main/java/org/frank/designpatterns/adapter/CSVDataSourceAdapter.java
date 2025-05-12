package org.frank.designpatterns.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Adapter that allows a CSVDataSource to be used through the DataSource interface.
 * This is an example of the Adapter pattern.
 */
public class CSVDataSourceAdapter implements DataSource {
    
    /**
     * The CSV data source that we're adapting.
     */
    private final CSVDataSource csvDataSource;
    
    /**
     * The name of the column to use as the ID.
     */
    private final String idColumnName;
    
    /**
     * Constructor for CSVDataSourceAdapter.
     * 
     * @param csvDataSource The CSV data source to adapt
     * @param idColumnName The name of the column to use as the ID
     * @throws DataSourceException If the CSV data source is invalid
     */
    public CSVDataSourceAdapter(CSVDataSource csvDataSource, String idColumnName) throws DataSourceException {
        this.csvDataSource = csvDataSource;
        this.idColumnName = idColumnName;
        
        // Load the data from the CSV file
        if (!csvDataSource.loadData()) {
            throw new DataSourceException("Failed to load data from CSV file: " + csvDataSource.getFilePath(), "LOAD_ERROR");
        }
        
        // Verify that the ID column exists
        if (!csvDataSource.hasHeader() || !csvDataSource.getHeaders().contains(idColumnName)) {
            throw new DataSourceException("ID column '" + idColumnName + "' not found in CSV file", "INVALID_ID_COLUMN");
        }
    }
    
    @Override
    public List<Map<String, Object>> getAllRecords() throws DataSourceException {
        List<Map<String, Object>> records = new ArrayList<>();
        List<String> headers = csvDataSource.getHeaders();
        List<List<String>> rows = csvDataSource.getAllRows();
        
        for (List<String> row : rows) {
            Map<String, Object> record = new HashMap<>();
            for (int i = 0; i < Math.min(headers.size(), row.size()); i++) {
                record.put(headers.get(i), row.get(i));
            }
            records.add(record);
        }
        
        return records;
    }
    
    @Override
    public Map<String, Object> getRecordById(String id) throws DataSourceException {
        // Find the row with the matching ID
        List<Integer> matchingRows = csvDataSource.findRowsByColumnValue(idColumnName, id);
        
        if (matchingRows.isEmpty()) {
            return null;
        }
        
        // Get the first matching row
        int rowIndex = matchingRows.get(0);
        List<String> row = csvDataSource.getRow(rowIndex);
        List<String> headers = csvDataSource.getHeaders();
        
        // Convert the row to a map
        Map<String, Object> record = new HashMap<>();
        for (int i = 0; i < Math.min(headers.size(), row.size()); i++) {
            record.put(headers.get(i), row.get(i));
        }
        
        return record;
    }
    
    @Override
    public List<Map<String, Object>> queryRecords(Map<String, Object> filter) throws DataSourceException {
        List<Map<String, Object>> allRecords = getAllRecords();
        List<Map<String, Object>> matchingRecords = new ArrayList<>();
        
        // Filter the records based on the provided criteria
        for (Map<String, Object> record : allRecords) {
            boolean matches = true;
            
            for (Map.Entry<String, Object> entry : filter.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                
                if (!record.containsKey(key) || !record.get(key).equals(value)) {
                    matches = false;
                    break;
                }
            }
            
            if (matches) {
                matchingRecords.add(record);
            }
        }
        
        return matchingRecords;
    }
    
    @Override
    public String addRecord(Map<String, Object> record) throws DataSourceException {
        List<String> headers = csvDataSource.getHeaders();
        List<String> values = new ArrayList<>();
        
        // Check if the record has an ID
        if (!record.containsKey(idColumnName)) {
            throw new DataSourceException("Record must have an ID in column '" + idColumnName + "'", "MISSING_ID");
        }
        
        String id = record.get(idColumnName).toString();
        
        // Check if a record with this ID already exists
        if (getRecordById(id) != null) {
            throw new DataSourceException("Record with ID '" + id + "' already exists", "DUPLICATE_ID");
        }
        
        // Convert the record to a list of values in the same order as the headers
        for (String header : headers) {
            Object value = record.getOrDefault(header, "");
            values.add(value.toString());
        }
        
        // Add the row to the CSV data
        csvDataSource.addRow(values);
        
        // Save the changes to the CSV file
        if (!csvDataSource.saveData()) {
            throw new DataSourceException("Failed to save data to CSV file", "SAVE_ERROR");
        }
        
        return id;
    }
    
    @Override
    public boolean updateRecord(String id, Map<String, Object> record) throws DataSourceException {
        // Find the row with the matching ID
        List<Integer> matchingRows = csvDataSource.findRowsByColumnValue(idColumnName, id);
        
        if (matchingRows.isEmpty()) {
            return false;
        }
        
        // Get the first matching row
        int rowIndex = matchingRows.get(0);
        List<String> headers = csvDataSource.getHeaders();
        List<String> values = new ArrayList<>();
        
        // Convert the record to a list of values in the same order as the headers
        for (String header : headers) {
            Object value = record.getOrDefault(header, "");
            values.add(value.toString());
        }
        
        // Update the row in the CSV data
        csvDataSource.updateRow(rowIndex, values);
        
        // Save the changes to the CSV file
        if (!csvDataSource.saveData()) {
            throw new DataSourceException("Failed to save data to CSV file", "SAVE_ERROR");
        }
        
        return true;
    }
    
    @Override
    public boolean deleteRecord(String id) throws DataSourceException {
        // Find the row with the matching ID
        List<Integer> matchingRows = csvDataSource.findRowsByColumnValue(idColumnName, id);
        
        if (matchingRows.isEmpty()) {
            return false;
        }
        
        // Delete the first matching row
        int rowIndex = matchingRows.get(0);
        csvDataSource.deleteRow(rowIndex);
        
        // Save the changes to the CSV file
        if (!csvDataSource.saveData()) {
            throw new DataSourceException("Failed to save data to CSV file", "SAVE_ERROR");
        }
        
        return true;
    }
    
    @Override
    public List<String> getColumnNames() throws DataSourceException {
        return csvDataSource.getHeaders();
    }
    
    @Override
    public void close() {
        // Nothing to close for CSV files
    }
    
    /**
     * Get the underlying CSV data source.
     * 
     * @return The CSV data source
     */
    public CSVDataSource getCsvDataSource() {
        return csvDataSource;
    }
}