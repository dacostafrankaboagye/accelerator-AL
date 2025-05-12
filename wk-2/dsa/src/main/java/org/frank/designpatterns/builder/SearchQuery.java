package org.frank.designpatterns.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SearchQuery class that represents a complex search query with various filter criteria.
 * Uses the Builder pattern to construct the query step by step.
 */
public class SearchQuery {
    // Search parameters
    private final String keyword;
    private final List<String> categories;
    private final Map<String, String> filters;
    private final int page;
    private final int pageSize;
    private final String sortBy;
    private final boolean sortAscending;
    private final String dateRangeStart;
    private final String dateRangeEnd;
    private final double minPrice;
    private final double maxPrice;
    private final List<String> excludeTerms;
    private final boolean exactMatch;
    
    /**
     * Private constructor that takes a builder to initialize the fields.
     * This constructor is private to enforce the use of the builder.
     * 
     * @param builder The builder to use for initialization
     */
    private SearchQuery(SearchQueryBuilder builder) {
        this.keyword = builder.keyword;
        this.categories = builder.categories;
        this.filters = builder.filters;
        this.page = builder.page;
        this.pageSize = builder.pageSize;
        this.sortBy = builder.sortBy;
        this.sortAscending = builder.sortAscending;
        this.dateRangeStart = builder.dateRangeStart;
        this.dateRangeEnd = builder.dateRangeEnd;
        this.minPrice = builder.minPrice;
        this.maxPrice = builder.maxPrice;
        this.excludeTerms = builder.excludeTerms;
        this.exactMatch = builder.exactMatch;
    }
    
    // Getters for all fields
    
    public String getKeyword() {
        return keyword;
    }
    
    public List<String> getCategories() {
        return new ArrayList<>(categories); // Return a copy to prevent modification
    }
    
    public Map<String, String> getFilters() {
        return new HashMap<>(filters); // Return a copy to prevent modification
    }
    
    public int getPage() {
        return page;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public String getSortBy() {
        return sortBy;
    }
    
    public boolean isSortAscending() {
        return sortAscending;
    }
    
    public String getDateRangeStart() {
        return dateRangeStart;
    }
    
    public String getDateRangeEnd() {
        return dateRangeEnd;
    }
    
    public double getMinPrice() {
        return minPrice;
    }
    
    public double getMaxPrice() {
        return maxPrice;
    }
    
    public List<String> getExcludeTerms() {
        return new ArrayList<>(excludeTerms); // Return a copy to prevent modification
    }
    
    public boolean isExactMatch() {
        return exactMatch;
    }
    
    /**
     * Generate a SQL-like representation of the search query.
     * 
     * @return A SQL-like string representing the search query
     */
    public String toSqlString() {
        StringBuilder sql = new StringBuilder("SELECT * FROM items WHERE 1=1");
        
        // Add keyword search
        if (keyword != null && !keyword.isEmpty()) {
            if (exactMatch) {
                sql.append(" AND name = '").append(keyword).append("'");
            } else {
                sql.append(" AND name LIKE '%").append(keyword).append("%'");
            }
        }
        
        // Add category filter
        if (!categories.isEmpty()) {
            sql.append(" AND category IN (");
            for (int i = 0; i < categories.size(); i++) {
                if (i > 0) {
                    sql.append(", ");
                }
                sql.append("'").append(categories.get(i)).append("'");
            }
            sql.append(")");
        }
        
        // Add custom filters
        for (Map.Entry<String, String> filter : filters.entrySet()) {
            sql.append(" AND ").append(filter.getKey()).append(" = '").append(filter.getValue()).append("'");
        }
        
        // Add date range
        if (dateRangeStart != null && !dateRangeStart.isEmpty()) {
            sql.append(" AND date >= '").append(dateRangeStart).append("'");
        }
        if (dateRangeEnd != null && !dateRangeEnd.isEmpty()) {
            sql.append(" AND date <= '").append(dateRangeEnd).append("'");
        }
        
        // Add price range
        if (minPrice > 0) {
            sql.append(" AND price >= ").append(minPrice);
        }
        if (maxPrice > 0 && maxPrice > minPrice) {
            sql.append(" AND price <= ").append(maxPrice);
        }
        
        // Add excluded terms
        for (String term : excludeTerms) {
            sql.append(" AND name NOT LIKE '%").append(term).append("%'");
        }
        
        // Add sorting
        if (sortBy != null && !sortBy.isEmpty()) {
            sql.append(" ORDER BY ").append(sortBy);
            if (sortAscending) {
                sql.append(" ASC");
            } else {
                sql.append(" DESC");
            }
        }
        
        // Add pagination
        sql.append(" LIMIT ").append(pageSize).append(" OFFSET ").append((page - 1) * pageSize);
        
        return sql.toString();
    }
    
    @Override
    public String toString() {
        return "SearchQuery{" +
                "keyword='" + keyword + '\'' +
                ", categories=" + categories +
                ", filters=" + filters +
                ", page=" + page +
                ", pageSize=" + pageSize +
                ", sortBy='" + sortBy + '\'' +
                ", sortAscending=" + sortAscending +
                ", dateRangeStart='" + dateRangeStart + '\'' +
                ", dateRangeEnd='" + dateRangeEnd + '\'' +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", excludeTerms=" + excludeTerms +
                ", exactMatch=" + exactMatch +
                '}';
    }
    
    /**
     * Builder class for SearchQuery.
     */
    public static class SearchQueryBuilder {
        // Search parameters with default values
        private String keyword = "";
        private List<String> categories = new ArrayList<>();
        private Map<String, String> filters = new HashMap<>();
        private int page = 1;
        private int pageSize = 10;
        private String sortBy = "";
        private boolean sortAscending = true;
        private String dateRangeStart = "";
        private String dateRangeEnd = "";
        private double minPrice = 0.0;
        private double maxPrice = 0.0;
        private List<String> excludeTerms = new ArrayList<>();
        private boolean exactMatch = false;
        
        /**
         * Default constructor for SearchQueryBuilder.
         */
        public SearchQueryBuilder() {
            // Initialize with default values
        }
        
        /**
         * Set the search keyword.
         * 
         * @param keyword The search keyword
         * @return This builder for method chaining
         */
        public SearchQueryBuilder keyword(String keyword) {
            this.keyword = keyword;
            return this;
        }
        
        /**
         * Add a category to filter by.
         * 
         * @param category The category to add
         * @return This builder for method chaining
         */
        public SearchQueryBuilder addCategory(String category) {
            this.categories.add(category);
            return this;
        }
        
        /**
         * Set the categories to filter by.
         * 
         * @param categories The categories to filter by
         * @return This builder for method chaining
         */
        public SearchQueryBuilder categories(List<String> categories) {
            this.categories = new ArrayList<>(categories);
            return this;
        }
        
        /**
         * Add a custom filter.
         * 
         * @param key The filter key
         * @param value The filter value
         * @return This builder for method chaining
         */
        public SearchQueryBuilder addFilter(String key, String value) {
            this.filters.put(key, value);
            return this;
        }
        
        /**
         * Set the page number for pagination.
         * 
         * @param page The page number (1-based)
         * @return This builder for method chaining
         */
        public SearchQueryBuilder page(int page) {
            this.page = Math.max(1, page); // Ensure page is at least 1
            return this;
        }
        
        /**
         * Set the page size for pagination.
         * 
         * @param pageSize The page size
         * @return This builder for method chaining
         */
        public SearchQueryBuilder pageSize(int pageSize) {
            this.pageSize = Math.max(1, pageSize); // Ensure pageSize is at least 1
            return this;
        }
        
        /**
         * Set the field to sort by.
         * 
         * @param sortBy The field to sort by
         * @param ascending Whether to sort in ascending order
         * @return This builder for method chaining
         */
        public SearchQueryBuilder sortBy(String sortBy, boolean ascending) {
            this.sortBy = sortBy;
            this.sortAscending = ascending;
            return this;
        }
        
        /**
         * Set the date range to filter by.
         * 
         * @param start The start date (inclusive)
         * @param end The end date (inclusive)
         * @return This builder for method chaining
         */
        public SearchQueryBuilder dateRange(String start, String end) {
            this.dateRangeStart = start;
            this.dateRangeEnd = end;
            return this;
        }
        
        /**
         * Set the price range to filter by.
         * 
         * @param min The minimum price (inclusive)
         * @param max The maximum price (inclusive)
         * @return This builder for method chaining
         */
        public SearchQueryBuilder priceRange(double min, double max) {
            this.minPrice = min;
            this.maxPrice = max;
            return this;
        }
        
        /**
         * Add a term to exclude from the search results.
         * 
         * @param term The term to exclude
         * @return This builder for method chaining
         */
        public SearchQueryBuilder excludeTerm(String term) {
            this.excludeTerms.add(term);
            return this;
        }
        
        /**
         * Set whether to perform an exact match on the keyword.
         * 
         * @param exactMatch Whether to perform an exact match
         * @return This builder for method chaining
         */
        public SearchQueryBuilder exactMatch(boolean exactMatch) {
            this.exactMatch = exactMatch;
            return this;
        }
        
        /**
         * Build the SearchQuery object.
         * 
         * @return A new SearchQuery object with the specified parameters
         */
        public SearchQuery build() {
            return new SearchQuery(this);
        }
    }
}