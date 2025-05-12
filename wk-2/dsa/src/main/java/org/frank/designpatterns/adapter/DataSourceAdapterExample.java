package org.frank.designpatterns.adapter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Example class demonstrating the usage of the CSVDataSourceAdapter.
 */
public class DataSourceAdapterExample {
    
    public static void main(String[] args) {
        System.out.println("CSV Data Source Adapter Example");
        System.out.println("-------------------------------");
        
        try {
            // Create a temporary CSV file for the example
            File tempFile = createSampleCSVFile();
            System.out.println("Created sample CSV file: " + tempFile.getAbsolutePath());
            
            // Create the CSV data source
            CSVDataSource csvDataSource = new CSVDataSource(tempFile.getAbsolutePath(), ",", true);
            
            // Create the adapter that wraps the CSV data source
            DataSource dataSource = new CSVDataSourceAdapter(csvDataSource, "id");
            
            // Example 1: Get all records
            System.out.println("\nExample 1: Get all records");
            List<Map<String, Object>> allRecords = dataSource.getAllRecords();
            System.out.println("Total records: " + allRecords.size());
            for (Map<String, Object> record : allRecords) {
                System.out.println(record);
            }
            
            // Example 2: Get a record by ID
            System.out.println("\nExample 2: Get a record by ID");
            Map<String, Object> record = dataSource.getRecordById("2");
            System.out.println("Record with ID 2: " + record);
            
            // Example 3: Query records
            System.out.println("\nExample 3: Query records");
            Map<String, Object> filter = new HashMap<>();
            filter.put("city", "New York");
            List<Map<String, Object>> matchingRecords = dataSource.queryRecords(filter);
            System.out.println("Records in New York: " + matchingRecords.size());
            for (Map<String, Object> matchingRecord : matchingRecords) {
                System.out.println(matchingRecord);
            }
            
            // Example 4: Add a new record
            System.out.println("\nExample 4: Add a new record");
            Map<String, Object> newRecord = new HashMap<>();
            newRecord.put("id", "4");
            newRecord.put("name", "David Wilson");
            newRecord.put("age", "42");
            newRecord.put("city", "Miami");
            newRecord.put("email", "david.wilson@example.com");
            
            String newId = dataSource.addRecord(newRecord);
            System.out.println("Added new record with ID: " + newId);
            
            // Verify the new record was added
            allRecords = dataSource.getAllRecords();
            System.out.println("Total records after adding: " + allRecords.size());
            for (Map<String, Object> r : allRecords) {
                System.out.println(r);
            }
            
            // Example 5: Update a record
            System.out.println("\nExample 5: Update a record");
            Map<String, Object> updatedRecord = new HashMap<>(dataSource.getRecordById("3"));
            updatedRecord.put("city", "Seattle");
            updatedRecord.put("age", "29");
            
            boolean updateSuccess = dataSource.updateRecord("3", updatedRecord);
            System.out.println("Update successful: " + updateSuccess);
            
            // Verify the record was updated
            record = dataSource.getRecordById("3");
            System.out.println("Updated record: " + record);
            
            // Example 6: Delete a record
            System.out.println("\nExample 6: Delete a record");
            boolean deleteSuccess = dataSource.deleteRecord("1");
            System.out.println("Delete successful: " + deleteSuccess);
            
            // Verify the record was deleted
            allRecords = dataSource.getAllRecords();
            System.out.println("Total records after deleting: " + allRecords.size());
            for (Map<String, Object> r : allRecords) {
                System.out.println(r);
            }
            
            // Example 7: Get column names
            System.out.println("\nExample 7: Get column names");
            List<String> columnNames = dataSource.getColumnNames();
            System.out.println("Column names: " + columnNames);
            
            // Close the data source
            dataSource.close();
            
            System.out.println("\nDemonstration of direct usage of CSV data source (without adapter):");
            // Direct usage of CSV data source (for comparison)
            csvDataSource = new CSVDataSource(tempFile.getAbsolutePath(), ",", true);
            csvDataSource.loadData();
            
            List<List<String>> rows = csvDataSource.getAllRows();
            System.out.println("Total rows: " + rows.size());
            List<String> headers = csvDataSource.getHeaders();
            System.out.println("Headers: " + headers);
            
            System.out.println("\nAdapter Pattern Benefits:");
            System.out.println("1. Allows incompatible interfaces to work together");
            System.out.println("2. Enables using legacy code with modern interfaces");
            System.out.println("3. Provides a way to reuse existing code with new requirements");
            System.out.println("4. Helps in the transition from old systems to new ones");
            
        } catch (DataSourceException e) {
            System.err.println("Data source error: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Create a sample CSV file for the example.
     * 
     * @return The created file
     * @throws IOException If there is an error creating the file
     */
    private static File createSampleCSVFile() throws IOException {
        File tempFile = File.createTempFile("sample_data", ".csv");
        tempFile.deleteOnExit();
        
        try (FileWriter writer = new FileWriter(tempFile)) {
            // Write header
            writer.write("id,name,age,city,email\n");
            
            // Write data
            writer.write("1,John Doe,30,New York,john.doe@example.com\n");
            writer.write("2,Jane Smith,25,Los Angeles,jane.smith@example.com\n");
            writer.write("3,Bob Johnson,35,Chicago,bob.johnson@example.com\n");
        }
        
        return tempFile;
    }
}