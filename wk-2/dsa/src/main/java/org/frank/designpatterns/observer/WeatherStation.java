package org.frank.designpatterns.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * WeatherStation class that acts as the subject in the Observer pattern.
 * It maintains a list of observers and notifies them when weather data changes.
 */
public class WeatherStation {
    
    private List<WeatherObserver> observers;
    private float temperature;
    private float humidity;
    private float pressure;
    
    /**
     * Constructor for WeatherStation.
     */
    public WeatherStation() {
        this.observers = new ArrayList<>();
    }
    
    /**
     * Register an observer to receive weather updates.
     * 
     * @param observer The observer to register
     */
    public void registerObserver(WeatherObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("Observer registered: " + observer.getClass().getSimpleName());
        }
    }
    
    /**
     * Remove an observer from the list of registered observers.
     * 
     * @param observer The observer to remove
     * @return true if the observer was removed, false if it wasn't registered
     */
    public boolean removeObserver(WeatherObserver observer) {
        boolean removed = observers.remove(observer);
        if (removed) {
            System.out.println("Observer removed: " + observer.getClass().getSimpleName());
        }
        return removed;
    }
    
    /**
     * Notify all registered observers of weather data changes.
     */
    private void notifyObservers() {
        for (WeatherObserver observer : observers) {
            observer.update(temperature, humidity, pressure);
        }
    }
    
    /**
     * Set new weather measurements and notify all observers.
     * 
     * @param temperature The new temperature in degrees Celsius
     * @param humidity The new humidity percentage (0-100)
     * @param pressure The new pressure in hPa
     */
    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        
        System.out.println("\nWeather station: New measurements received");
        System.out.println("Temperature: " + temperature + "°C");
        System.out.println("Humidity: " + humidity + "%");
        System.out.println("Pressure: " + pressure + " hPa");
        
        // Notify all observers of the new measurements
        notifyObservers();
    }
    
    /**
     * Get the current temperature.
     * 
     * @return The current temperature in degrees Celsius
     */
    public float getTemperature() {
        return temperature;
    }
    
    /**
     * Get the current humidity.
     * 
     * @return The current humidity percentage (0-100)
     */
    public float getHumidity() {
        return humidity;
    }
    
    /**
     * Get the current pressure.
     * 
     * @return The current pressure in hPa
     */
    public float getPressure() {
        return pressure;
    }
    
    /**
     * Get the number of registered observers.
     * 
     * @return The number of registered observers
     */
    public int getObserverCount() {
        return observers.size();
    }
}