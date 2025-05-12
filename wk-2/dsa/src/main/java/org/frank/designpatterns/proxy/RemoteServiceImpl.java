package org.frank.designpatterns.proxy;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Implementation of the RemoteService interface.
 * This is the "RealSubject" in the Proxy pattern.
 */
public class RemoteServiceImpl implements RemoteService {
    
    private final Map<String, String> dataStore;
    private final Random random;
    private final double failureProbability;
    private final int minLatency;
    private final int maxLatency;
    private boolean available;
    
    /**
     * Constructor for RemoteServiceImpl with custom parameters.
     * 
     * @param failureProbability The probability of a failure (0.0 to 1.0)
     * @param minLatency The minimum latency in milliseconds
     * @param maxLatency The maximum latency in milliseconds
     * @param initiallyAvailable Whether the service is initially available
     */
    public RemoteServiceImpl(double failureProbability, int minLatency, int maxLatency, boolean initiallyAvailable) {
        this.dataStore = new HashMap<>();
        this.random = new Random();
        this.failureProbability = failureProbability;
        this.minLatency = minLatency;
        this.maxLatency = maxLatency;
        this.available = initiallyAvailable;
        
        // Initialize with some data
        dataStore.put("1", "Sample data 1");
        dataStore.put("2", "Sample data 2");
        dataStore.put("3", "Sample data 3");
    }
    
    /**
     * Constructor for RemoteServiceImpl with default parameters.
     */
    public RemoteServiceImpl() {
        this(0.1, 100, 1000, true); // 10% failure rate, 100-1000ms latency, initially available
    }
    
    @Override
    public String getData(String id) throws RemoteServiceException {
        // Simulate network latency
        simulateLatency();
        
        // Check if the service is available
        if (!available) {
            throw new RemoteServiceException("Service is not available", "SERVICE_UNAVAILABLE");
        }
        
        // Simulate random failures
        if (random.nextDouble() < failureProbability) {
            throw new RemoteServiceException("Failed to get data", "GET_FAILED");
        }
        
        // Get the data
        String data = dataStore.get(id);
        
        if (data == null) {
            throw new RemoteServiceException("Data not found for ID: " + id, "NOT_FOUND");
        }
        
        return data;
    }
    
    @Override
    public boolean saveData(String id, String data) throws RemoteServiceException {
        // Simulate network latency
        simulateLatency();
        
        // Check if the service is available
        if (!available) {
            throw new RemoteServiceException("Service is not available", "SERVICE_UNAVAILABLE");
        }
        
        // Simulate random failures
        if (random.nextDouble() < failureProbability) {
            throw new RemoteServiceException("Failed to save data", "SAVE_FAILED");
        }
        
        // Save the data
        dataStore.put(id, data);
        
        return true;
    }
    
    @Override
    public boolean deleteData(String id) throws RemoteServiceException {
        // Simulate network latency
        simulateLatency();
        
        // Check if the service is available
        if (!available) {
            throw new RemoteServiceException("Service is not available", "SERVICE_UNAVAILABLE");
        }
        
        // Simulate random failures
        if (random.nextDouble() < failureProbability) {
            throw new RemoteServiceException("Failed to delete data", "DELETE_FAILED");
        }
        
        // Check if the data exists
        if (!dataStore.containsKey(id)) {
            throw new RemoteServiceException("Data not found for ID: " + id, "NOT_FOUND");
        }
        
        // Delete the data
        dataStore.remove(id);
        
        return true;
    }
    
    @Override
    public boolean isAvailable() {
        return available;
    }
    
    @Override
    public long getResponseTime() {
        if (!available) {
            return -1;
        }
        
        return (minLatency + maxLatency) / 2; // Average latency
    }
    
    /**
     * Set whether the service is available.
     * 
     * @param available Whether the service is available
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    /**
     * Simulate network latency.
     */
    private void simulateLatency() {
        try {
            int latency = minLatency + random.nextInt(maxLatency - minLatency + 1);
            Thread.sleep(latency);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}