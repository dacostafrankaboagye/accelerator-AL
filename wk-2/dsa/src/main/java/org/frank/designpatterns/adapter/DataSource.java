package org.frank.designpatterns.adapter;

import java.util.List;
import java.util.Map;

/**
 * Interface for accessing data from various sources.
 * This represents the "Target" in the Adapter pattern.
 */
public interface DataSource {
    
    /**
     * Get all records from the data source.
     * 
     * @return A list of records, where each record is a map of column names to values
     * @throws DataSourceException If there is an error accessing the data
     */
    List<Map<String, Object>> getAllRecords() throws DataSourceException;
    
    /**
     * Get a specific record by ID.
     * 
     * @param id The ID of the record to retrieve
     * @return The record as a map of column names to values, or null if not found
     * @throws DataSourceException If there is an error accessing the data
     */
    Map<String, Object> getRecordById(String id) throws DataSourceException;
    
    /**
     * Query records based on a filter.
     * 
     * @param filter A map of column names to values to filter by
     * @return A list of matching records
     * @throws DataSourceException If there is an error accessing the data
     */
    List<Map<String, Object>> queryRecords(Map<String, Object> filter) throws DataSourceException;
    
    /**
     * Add a new record to the data source.
     * 
     * @param record The record to add as a map of column names to values
     * @return The ID of the newly added record
     * @throws DataSourceException If there is an error adding the record
     */
    String addRecord(Map<String, Object> record) throws DataSourceException;
    
    /**
     * Update an existing record.
     * 
     * @param id The ID of the record to update
     * @param record The updated record data
     * @return true if the record was updated successfully, false if the record was not found
     * @throws DataSourceException If there is an error updating the record
     */
    boolean updateRecord(String id, Map<String, Object> record) throws DataSourceException;
    
    /**
     * Delete a record.
     * 
     * @param id The ID of the record to delete
     * @return true if the record was deleted successfully, false if the record was not found
     * @throws DataSourceException If there is an error deleting the record
     */
    boolean deleteRecord(String id) throws DataSourceException;
    
    /**
     * Get the column names (schema) of the data source.
     * 
     * @return A list of column names
     * @throws DataSourceException If there is an error accessing the schema
     */
    List<String> getColumnNames() throws DataSourceException;
    
    /**
     * Close the data source and release any resources.
     */
    void close();
}