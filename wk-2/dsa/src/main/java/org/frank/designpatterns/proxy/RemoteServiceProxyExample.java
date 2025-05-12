package org.frank.designpatterns.proxy;

/**
 * Example class demonstrating the usage of the RemoteServiceProxy.
 */
public class RemoteServiceProxyExample {
    
    public static void main(String[] args) {
        System.out.println("Remote Service Proxy Example");
        System.out.println("---------------------------");
        
        // Create the real service with high latency and failure rate for demonstration
        RemoteServiceImpl realService = new RemoteServiceImpl(0.3, 500, 2000, true);
        
        // Create the proxy with caching and retry capabilities
        RemoteServiceProxy proxy = new RemoteServiceProxy(realService, 3, 1000, true);
        
        System.out.println("\nExample 1: Getting data with caching");
        // Get data for ID "1" twice - the second call should be cached
        try {
            System.out.println("First call for ID 1:");
            String data1 = proxy.getData("1");
            System.out.println("Data: " + data1);
            
            System.out.println("\nSecond call for ID 1 (should be cached):");
            String data1Again = proxy.getData("1");
            System.out.println("Data: " + data1Again);
            
            System.out.println("\nCache size: " + proxy.getCacheSize());
            System.out.println("Is ID 1 in cache? " + proxy.isInCache("1"));
            System.out.println("Is ID 2 in cache? " + proxy.isInCache("2"));
        } catch (RemoteServiceException e) {
            System.err.println("Error: " + e.getMessage() + " (Code: " + e.getErrorCode() + ")");
        }
        
        System.out.println("\nExample 2: Saving data");
        // Save new data
        try {
            System.out.println("Saving data for ID 4:");
            boolean saved = proxy.saveData("4", "New data for ID 4");
            System.out.println("Data saved successfully: " + saved);
            
            System.out.println("\nRetrieving the saved data (should be cached):");
            String data4 = proxy.getData("4");
            System.out.println("Data: " + data4);
        } catch (RemoteServiceException e) {
            System.err.println("Error: " + e.getMessage() + " (Code: " + e.getErrorCode() + ")");
        }
        
        System.out.println("\nExample 3: Clearing the cache");
        // Clear the cache and get data again
        proxy.clearCache();
        
        try {
            System.out.println("Getting data for ID 1 after cache clear (should hit remote service):");
            String data1AfterClear = proxy.getData("1");
            System.out.println("Data: " + data1AfterClear);
        } catch (RemoteServiceException e) {
            System.err.println("Error: " + e.getMessage() + " (Code: " + e.getErrorCode() + ")");
        }
        
        System.out.println("\nExample 4: Handling service unavailability");
        // Make the service unavailable and try to get data
        realService.setAvailable(false);
        
        try {
            System.out.println("Getting data when service is unavailable:");
            String unavailableData = proxy.getData("2");
            System.out.println("Data: " + unavailableData); // This line should not be reached
        } catch (RemoteServiceException e) {
            System.err.println("Error: " + e.getMessage() + " (Code: " + e.getErrorCode() + ")");
        }
        
        // Make the service available again
        realService.setAvailable(true);
        
        System.out.println("\nExample 5: Deleting data");
        // Delete data
        try {
            System.out.println("Deleting data for ID 1:");
            boolean deleted = proxy.deleteData("1");
            System.out.println("Data deleted successfully: " + deleted);
            
            System.out.println("\nTrying to get deleted data:");
            try {
                String deletedData = proxy.getData("1");
                System.out.println("Data: " + deletedData); // This line should not be reached
            } catch (RemoteServiceException e) {
                System.err.println("Error: " + e.getMessage() + " (Code: " + e.getErrorCode() + ")");
            }
        } catch (RemoteServiceException e) {
            System.err.println("Error: " + e.getMessage() + " (Code: " + e.getErrorCode() + ")");
        }
        
        System.out.println("\nExample 6: Retry mechanism for transient failures");
        // The real service has a 30% failure rate, so this might fail sometimes
        // but the proxy will retry up to 3 times
        try {
            System.out.println("Getting data with potential transient failures:");
            String data3 = proxy.getData("3");
            System.out.println("Data: " + data3);
        } catch (RemoteServiceException e) {
            System.err.println("Error after retries: " + e.getMessage() + " (Code: " + e.getErrorCode() + ")");
        }
        
        System.out.println("\nProxy Pattern Benefits:");
        System.out.println("1. Controls access to the real object");
        System.out.println("2. Adds functionality like caching and retry logic");
        System.out.println("3. Reduces network calls and improves performance");
        System.out.println("4. Handles errors and provides better reliability");
        System.out.println("5. Can implement lazy initialization");
        System.out.println("6. Provides a layer of protection for the real service");
    }
}