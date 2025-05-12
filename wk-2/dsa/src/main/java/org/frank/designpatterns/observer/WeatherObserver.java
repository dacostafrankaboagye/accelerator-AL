package org.frank.designpatterns.observer;

/**
 * WeatherObserver interface that defines the methods that all weather observers must implement.
 * This is the "Observer" in the Observer pattern.
 */
public interface WeatherObserver {
    
    /**
     * Update method called by the WeatherStation when weather data changes.
     * 
     * @param temperature The current temperature in degrees Celsius
     * @param humidity The current humidity percentage (0-100)
     * @param pressure The current pressure in hPa
     */
    void update(float temperature, float humidity, float pressure);
}