package org.frank.designpatterns.facade.email;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * EmailTemplate class that represents an email template with placeholders.
 * This is part of the subsystem used by the EmailFacade.
 */
public class EmailTemplate {
    
    private String name;
    private String subject;
    private String bodyTemplate;
    private Map<String, String> defaultValues;
    
    /**
     * Constructor for EmailTemplate.
     * 
     * @param name The name of the template
     * @param subject The subject line template
     * @param bodyTemplate The body template
     */
    public EmailTemplate(String name, String subject, String bodyTemplate) {
        this.name = name;
        this.subject = subject;
        this.bodyTemplate = bodyTemplate;
        this.defaultValues = new HashMap<>();
    }
    
    /**
     * Get the name of the template.
     * 
     * @return The template name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get the subject line template.
     * 
     * @return The subject line template
     */
    public String getSubject() {
        return subject;
    }
    
    /**
     * Get the body template.
     * 
     * @return The body template
     */
    public String getBodyTemplate() {
        return bodyTemplate;
    }
    
    /**
     * Set a default value for a placeholder.
     * 
     * @param placeholder The placeholder name
     * @param value The default value
     */
    public void setDefaultValue(String placeholder, String value) {
        defaultValues.put(placeholder, value);
    }
    
    /**
     * Get the default value for a placeholder.
     * 
     * @param placeholder The placeholder name
     * @return The default value, or null if not set
     */
    public String getDefaultValue(String placeholder) {
        return defaultValues.get(placeholder);
    }
    
    /**
     * Get all default values.
     * 
     * @return A map of placeholder names to default values
     */
    public Map<String, String> getDefaultValues() {
        return new HashMap<>(defaultValues);
    }
    
    /**
     * Fill the subject template with the provided values.
     * 
     * @param values The values to use for placeholders
     * @return The filled subject line
     */
    public String fillSubject(Map<String, String> values) {
        return fillTemplate(subject, values);
    }
    
    /**
     * Fill the body template with the provided values.
     * 
     * @param values The values to use for placeholders
     * @return The filled body
     */
    public String fillBody(Map<String, String> values) {
        return fillTemplate(bodyTemplate, values);
    }
    
    /**
     * Fill a template with the provided values.
     * 
     * @param template The template to fill
     * @param values The values to use for placeholders
     * @return The filled template
     */
    private String fillTemplate(String template, Map<String, String> values) {
        // Create a combined map with provided values taking precedence over default values
        Map<String, String> combinedValues = new HashMap<>(defaultValues);
        if (values != null) {
            combinedValues.putAll(values);
        }
        
        // Replace placeholders with values
        String result = template;
        Pattern pattern = Pattern.compile("\\{\\{([^}]+)\\}\\}");
        Matcher matcher = pattern.matcher(template);
        
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String placeholder = matcher.group(1);
            String value = combinedValues.getOrDefault(placeholder, "");
            matcher.appendReplacement(sb, Matcher.quoteReplacement(value));
        }
        matcher.appendTail(sb);
        
        return sb.toString();
    }
    
    /**
     * Get all placeholders in the template.
     * 
     * @return A map of placeholder names to their occurrences in the template
     */
    public Map<String, Integer> getPlaceholders() {
        Map<String, Integer> placeholders = new HashMap<>();
        
        // Find placeholders in subject
        countPlaceholders(subject, placeholders);
        
        // Find placeholders in body
        countPlaceholders(bodyTemplate, placeholders);
        
        return placeholders;
    }
    
    /**
     * Count the occurrences of placeholders in a template.
     * 
     * @param template The template to search
     * @param placeholders The map to update with placeholder counts
     */
    private void countPlaceholders(String template, Map<String, Integer> placeholders) {
        Pattern pattern = Pattern.compile("\\{\\{([^}]+)\\}\\}");
        Matcher matcher = pattern.matcher(template);
        
        while (matcher.find()) {
            String placeholder = matcher.group(1);
            placeholders.put(placeholder, placeholders.getOrDefault(placeholder, 0) + 1);
        }
    }
    
    @Override
    public String toString() {
        return "EmailTemplate{" +
                "name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                ", placeholders=" + getPlaceholders().keySet() +
                '}';
    }
}