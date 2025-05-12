package org.frank.designpatterns.proxy;

import java.util.HashMap;
import java.util.Map;

/**
 * Proxy for the RemoteService that adds caching and connection management.
 * This is the "Proxy" in the Proxy pattern.
 */
public class RemoteServiceProxy implements RemoteService {
    
    private final RemoteService realService;
    private final Map<String, String> cache;
    private final int maxRetries;
    private final long retryDelayMs;
    private final boolean useCache;
    
    /**
     * Constructor for RemoteServiceProxy with custom parameters.
     * 
     * @param realService The real remote service
     * @param maxRetries The maximum number of retries for failed operations
     * @param retryDelayMs The delay between retries in milliseconds
     * @param useCache Whether to use caching
     */
    public RemoteServiceProxy(RemoteService realService, int maxRetries, long retryDelayMs, boolean useCache) {
        this.realService = realService;
        this.cache = new HashMap<>();
        this.maxRetries = maxRetries;
        this.retryDelayMs = retryDelayMs;
        this.useCache = useCache;
    }
    
    /**
     * Constructor for RemoteServiceProxy with default parameters.
     * 
     * @param realService The real remote service
     */
    public RemoteServiceProxy(RemoteService realService) {
        this(realService, 3, 1000, true); // 3 retries, 1 second delay, use cache
    }
    
    @Override
    public String getData(String id) throws RemoteServiceException {
        // Check cache first if caching is enabled
        if (useCache && cache.containsKey(id)) {
            System.out.println("Cache hit for ID: " + id);
            return cache.get(id);
        }
        
        System.out.println("Cache miss for ID: " + id + ", fetching from remote service");
        
        // Try to get data from the real service with retries
        String data = null;
        RemoteServiceException lastException = null;
        
        for (int attempt = 0; attempt < maxRetries; attempt++) {
            try {
                long startTime = System.currentTimeMillis();
                data = realService.getData(id);
                long endTime = System.currentTimeMillis();
                
                System.out.println("Retrieved data in " + (endTime - startTime) + "ms");
                
                // Cache the result if caching is enabled
                if (useCache) {
                    cache.put(id, data);
                }
                
                return data;
            } catch (RemoteServiceException e) {
                lastException = e;
                
                // If the service is not available or the data is not found, don't retry
                if ("SERVICE_UNAVAILABLE".equals(e.getErrorCode()) || "NOT_FOUND".equals(e.getErrorCode())) {
                    break;
                }
                
                System.out.println("Attempt " + (attempt + 1) + " failed: " + e.getMessage() + 
                                  ". Retrying in " + retryDelayMs + "ms");
                
                try {
                    Thread.sleep(retryDelayMs);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RemoteServiceException("Operation interrupted", ie, "INTERRUPTED");
                }
            }
        }
        
        // If we get here, all attempts failed
        if (lastException != null) {
            throw lastException;
        } else {
            throw new RemoteServiceException("Failed to get data after " + maxRetries + " attempts", "MAX_RETRIES_EXCEEDED");
        }
    }
    
    @Override
    public boolean saveData(String id, String data) throws RemoteServiceException {
        // Try to save data to the real service with retries
        RemoteServiceException lastException = null;
        
        for (int attempt = 0; attempt < maxRetries; attempt++) {
            try {
                long startTime = System.currentTimeMillis();
                boolean result = realService.saveData(id, data);
                long endTime = System.currentTimeMillis();
                
                System.out.println("Saved data in " + (endTime - startTime) + "ms");
                
                // Update the cache if caching is enabled
                if (useCache) {
                    cache.put(id, data);
                }
                
                return result;
            } catch (RemoteServiceException e) {
                lastException = e;
                
                // If the service is not available, don't retry
                if ("SERVICE_UNAVAILABLE".equals(e.getErrorCode())) {
                    break;
                }
                
                System.out.println("Attempt " + (attempt + 1) + " failed: " + e.getMessage() + 
                                  ". Retrying in " + retryDelayMs + "ms");
                
                try {
                    Thread.sleep(retryDelayMs);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RemoteServiceException("Operation interrupted", ie, "INTERRUPTED");
                }
            }
        }
        
        // If we get here, all attempts failed
        if (lastException != null) {
            throw lastException;
        } else {
            throw new RemoteServiceException("Failed to save data after " + maxRetries + " attempts", "MAX_RETRIES_EXCEEDED");
        }
    }
    
    @Override
    public boolean deleteData(String id) throws RemoteServiceException {
        // Try to delete data from the real service with retries
        RemoteServiceException lastException = null;
        
        for (int attempt = 0; attempt < maxRetries; attempt++) {
            try {
                long startTime = System.currentTimeMillis();
                boolean result = realService.deleteData(id);
                long endTime = System.currentTimeMillis();
                
                System.out.println("Deleted data in " + (endTime - startTime) + "ms");
                
                // Remove from cache if caching is enabled
                if (useCache) {
                    cache.remove(id);
                }
                
                return result;
            } catch (RemoteServiceException e) {
                lastException = e;
                
                // If the service is not available or the data is not found, don't retry
                if ("SERVICE_UNAVAILABLE".equals(e.getErrorCode()) || "NOT_FOUND".equals(e.getErrorCode())) {
                    break;
                }
                
                System.out.println("Attempt " + (attempt + 1) + " failed: " + e.getMessage() + 
                                  ". Retrying in " + retryDelayMs + "ms");
                
                try {
                    Thread.sleep(retryDelayMs);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RemoteServiceException("Operation interrupted", ie, "INTERRUPTED");
                }
            }
        }
        
        // If we get here, all attempts failed
        if (lastException != null) {
            throw lastException;
        } else {
            throw new RemoteServiceException("Failed to delete data after " + maxRetries + " attempts", "MAX_RETRIES_EXCEEDED");
        }
    }
    
    @Override
    public boolean isAvailable() {
        return realService.isAvailable();
    }
    
    @Override
    public long getResponseTime() {
        return realService.getResponseTime();
    }
    
    /**
     * Clear the cache.
     */
    public void clearCache() {
        cache.clear();
        System.out.println("Cache cleared");
    }
    
    /**
     * Get the number of items in the cache.
     * 
     * @return The number of items in the cache
     */
    public int getCacheSize() {
        return cache.size();
    }
    
    /**
     * Check if an item is in the cache.
     * 
     * @param id The ID to check
     * @return true if the item is in the cache, false otherwise
     */
    public boolean isInCache(String id) {
        return cache.containsKey(id);
    }
}