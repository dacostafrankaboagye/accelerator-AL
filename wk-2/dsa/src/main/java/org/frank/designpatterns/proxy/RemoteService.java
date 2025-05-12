package org.frank.designpatterns.proxy;

/**
 * RemoteService interface that defines operations that can be performed on a remote service.
 * This is the "Subject" in the Proxy pattern.
 */
public interface RemoteService {
    
    /**
     * Get data from the remote service.
     * 
     * @param id The ID of the data to retrieve
     * @return The data as a string
     * @throws RemoteServiceException If there is an error accessing the remote service
     */
    String getData(String id) throws RemoteServiceException;
    
    /**
     * Save data to the remote service.
     * 
     * @param id The ID to associate with the data
     * @param data The data to save
     * @return true if the data was saved successfully, false otherwise
     * @throws RemoteServiceException If there is an error accessing the remote service
     */
    boolean saveData(String id, String data) throws RemoteServiceException;
    
    /**
     * Delete data from the remote service.
     * 
     * @param id The ID of the data to delete
     * @return true if the data was deleted successfully, false otherwise
     * @throws RemoteServiceException If there is an error accessing the remote service
     */
    boolean deleteData(String id) throws RemoteServiceException;
    
    /**
     * Check if the remote service is available.
     * 
     * @return true if the service is available, false otherwise
     */
    boolean isAvailable();
    
    /**
     * Get the response time of the remote service in milliseconds.
     * 
     * @return The response time in milliseconds, or -1 if the service is not available
     */
    long getResponseTime();
}