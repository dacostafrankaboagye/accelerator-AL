package org.frank.designpatterns.observer;

/**
 * CurrentConditionsDisplay class that displays the current weather conditions.
 * This is a concrete observer in the Observer pattern.
 */
public class CurrentConditionsDisplay implements WeatherObserver {
    
    private float temperature;
    private float humidity;
    private float pressure;
    private WeatherStation weatherStation;
    
    /**
     * Constructor for CurrentConditionsDisplay.
     * 
     * @param weatherStation The weather station to observe
     */
    public CurrentConditionsDisplay(WeatherStation weatherStation) {
        this.weatherStation = weatherStation;
        weatherStation.registerObserver(this);
    }
    
    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        display();
    }
    
    /**
     * Display the current weather conditions.
     */
    public void display() {
        System.out.println("\nCurrent Conditions Display:");
        System.out.println("Temperature: " + temperature + "°C");
        System.out.println("Humidity: " + humidity + "%");
        System.out.println("Pressure: " + pressure + " hPa");
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
     * Unregister from the weather station.
     */
    public void unregister() {
        weatherStation.removeObserver(this);
    }
}