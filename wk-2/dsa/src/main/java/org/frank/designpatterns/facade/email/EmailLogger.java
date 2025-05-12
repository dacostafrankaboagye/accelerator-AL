package org.frank.designpatterns.facade.email;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EmailLogger class that logs email-related events.
 * This is part of the subsystem used by the EmailFacade.
 */
public class EmailLogger {
    
    /**
     * Enum for log levels.
     */
    public enum LogLevel {
        INFO,
        WARNING,
        ERROR,
        DEBUG
    }
    
    /**
     * Enum for event types.
     */
    public enum EventType {
        COMPOSE,
        VALIDATE,
        SEND,
        DELIVER,
        OPEN,
        CLICK,
        BOUNCE,
        ERROR
    }
    
    private LogLevel minLogLevel;
    private List<Map<String, Object>> logs;
    private DateTimeFormatter dateFormatter;
    
    /**
     * Constructor for EmailLogger.
     * 
     * @param minLogLevel The minimum log level to record
     */
    public EmailLogger(LogLevel minLogLevel) {
        this.minLogLevel = minLogLevel;
        this.logs = new ArrayList<>();
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    }
    
    /**
     * Constructor for EmailLogger with default log level (INFO).
     */
    public EmailLogger() {
        this(LogLevel.INFO);
    }
    
    /**
     * Log an event.
     * 
     * @param level The log level
     * @param eventType The event type
     * @param message The log message
     * @param details Additional details about the event
     */
    public void log(LogLevel level, EventType eventType, String message, Map<String, Object> details) {
        if (level.ordinal() < minLogLevel.ordinal()) {
            return; // Skip logs below the minimum level
        }
        
        LocalDateTime timestamp = LocalDateTime.now();
        
        Map<String, Object> logEntry = new HashMap<>();
        logEntry.put("timestamp", timestamp);
        logEntry.put("level", level);
        logEntry.put("eventType", eventType);
        logEntry.put("message", message);
        
        if (details != null) {
            logEntry.put("details", new HashMap<>(details));
        }
        
        logs.add(logEntry);
        
        // Print the log entry
        System.out.println(formatLogEntry(logEntry));
    }
    
    /**
     * Log an event without additional details.
     * 
     * @param level The log level
     * @param eventType The event type
     * @param message The log message
     */
    public void log(LogLevel level, EventType eventType, String message) {
        log(level, eventType, message, null);
    }
    
    /**
     * Log an info event.
     * 
     * @param eventType The event type
     * @param message The log message
     * @param details Additional details about the event
     */
    public void info(EventType eventType, String message, Map<String, Object> details) {
        log(LogLevel.INFO, eventType, message, details);
    }
    
    /**
     * Log an info event without additional details.
     * 
     * @param eventType The event type
     * @param message The log message
     */
    public void info(EventType eventType, String message) {
        log(LogLevel.INFO, eventType, message);
    }
    
    /**
     * Log a warning event.
     * 
     * @param eventType The event type
     * @param message The log message
     * @param details Additional details about the event
     */
    public void warning(EventType eventType, String message, Map<String, Object> details) {
        log(LogLevel.WARNING, eventType, message, details);
    }
    
    /**
     * Log a warning event without additional details.
     * 
     * @param eventType The event type
     * @param message The log message
     */
    public void warning(EventType eventType, String message) {
        log(LogLevel.WARNING, eventType, message);
    }
    
    /**
     * Log an error event.
     * 
     * @param eventType The event type
     * @param message The log message
     * @param details Additional details about the event
     */
    public void error(EventType eventType, String message, Map<String, Object> details) {
        log(LogLevel.ERROR, eventType, message, details);
    }
    
    /**
     * Log an error event without additional details.
     * 
     * @param eventType The event type
     * @param message The log message
     */
    public void error(EventType eventType, String message) {
        log(LogLevel.ERROR, eventType, message);
    }
    
    /**
     * Log a debug event.
     * 
     * @param eventType The event type
     * @param message The log message
     * @param details Additional details about the event
     */
    public void debug(EventType eventType, String message, Map<String, Object> details) {
        log(LogLevel.DEBUG, eventType, message, details);
    }
    
    /**
     * Log a debug event without additional details.
     * 
     * @param eventType The event type
     * @param message The log message
     */
    public void debug(EventType eventType, String message) {
        log(LogLevel.DEBUG, eventType, message);
    }
    
    /**
     * Get all logs.
     * 
     * @return A list of log entries
     */
    public List<Map<String, Object>> getLogs() {
        return new ArrayList<>(logs);
    }
    
    /**
     * Get logs filtered by level.
     * 
     * @param level The log level to filter by
     * @return A list of log entries with the specified level
     */
    public List<Map<String, Object>> getLogsByLevel(LogLevel level) {
        List<Map<String, Object>> filteredLogs = new ArrayList<>();
        
        for (Map<String, Object> log : logs) {
            if (log.get("level") == level) {
                filteredLogs.add(new HashMap<>(log));
            }
        }
        
        return filteredLogs;
    }
    
    /**
     * Get logs filtered by event type.
     * 
     * @param eventType The event type to filter by
     * @return A list of log entries with the specified event type
     */
    public List<Map<String, Object>> getLogsByEventType(EventType eventType) {
        List<Map<String, Object>> filteredLogs = new ArrayList<>();
        
        for (Map<String, Object> log : logs) {
            if (log.get("eventType") == eventType) {
                filteredLogs.add(new HashMap<>(log));
            }
        }
        
        return filteredLogs;
    }
    
    /**
     * Clear all logs.
     */
    public void clearLogs() {
        logs.clear();
    }
    
    /**
     * Set the minimum log level.
     * 
     * @param minLogLevel The minimum log level to record
     */
    public void setMinLogLevel(LogLevel minLogLevel) {
        this.minLogLevel = minLogLevel;
    }
    
    /**
     * Get the minimum log level.
     * 
     * @return The minimum log level
     */
    public LogLevel getMinLogLevel() {
        return minLogLevel;
    }
    
    /**
     * Format a log entry for display.
     * 
     * @param logEntry The log entry to format
     * @return The formatted log entry
     */
    private String formatLogEntry(Map<String, Object> logEntry) {
        LocalDateTime timestamp = (LocalDateTime) logEntry.get("timestamp");
        LogLevel level = (LogLevel) logEntry.get("level");
        EventType eventType = (EventType) logEntry.get("eventType");
        String message = (String) logEntry.get("message");
        
        StringBuilder sb = new StringBuilder();
        sb.append(timestamp.format(dateFormatter))
          .append(" [").append(level).append("] ")
          .append("[").append(eventType).append("] ")
          .append(message);
        
        @SuppressWarnings("unchecked")
        Map<String, Object> details = (Map<String, Object>) logEntry.get("details");
        if (details != null && !details.isEmpty()) {
            sb.append(" - Details: ").append(details);
        }
        
        return sb.toString();
    }
}