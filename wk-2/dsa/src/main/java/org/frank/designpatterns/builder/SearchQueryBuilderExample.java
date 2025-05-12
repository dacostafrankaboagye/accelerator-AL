package org.frank.designpatterns.builder;

import java.util.Arrays;

/**
 * Example class demonstrating the usage of the SearchQuery builder.
 */
public class SearchQueryBuilderExample {
    
    public static void main(String[] args) {
        System.out.println("Search Query Builder Example");
        System.out.println("---------------------------");
        
        // Example 1: Basic search with just a keyword
        SearchQuery query1 = new SearchQuery.SearchQueryBuilder()
                .keyword("laptop")
                .build();
        
        System.out.println("Query 1 (basic search):");
        System.out.println("SQL: " + query1.toSqlString());
        System.out.println("Object: " + query1);
        
        // Example 2: Search with categories and sorting
        SearchQuery query2 = new SearchQuery.SearchQueryBuilder()
                .keyword("phone")
                .addCategory("Electronics")
                .addCategory("Mobile Devices")
                .sortBy("price", false) // Sort by price descending
                .build();
        
        System.out.println("\nQuery 2 (with categories and sorting):");
        System.out.println("SQL: " + query2.toSqlString());
        
        // Example 3: Advanced search with multiple filters
        SearchQuery query3 = new SearchQuery.SearchQueryBuilder()
                .keyword("shoes")
                .categories(Arrays.asList("Footwear", "Sports", "Fashion"))
                .addFilter("brand", "Nike")
                .addFilter("color", "black")
                .priceRange(50.0, 200.0)
                .dateRange("2023-01-01", "2023-12-31")
                .page(2)
                .pageSize(25)
                .build();
        
        System.out.println("\nQuery 3 (advanced search with multiple filters):");
        System.out.println("SQL: " + query3.toSqlString());
        
        // Example 4: Search with excluded terms and exact match
        SearchQuery query4 = new SearchQuery.SearchQueryBuilder()
                .keyword("camera")
                .exactMatch(true)
                .excludeTerm("digital")
                .excludeTerm("phone")
                .sortBy("rating", false)
                .build();
        
        System.out.println("\nQuery 4 (with excluded terms and exact match):");
        System.out.println("SQL: " + query4.toSqlString());
        
        // Example 5: Complex search with all features
        SearchQuery query5 = new SearchQuery.SearchQueryBuilder()
                .keyword("book")
                .addCategory("Fiction")
                .addCategory("Sci-Fi")
                .addFilter("author", "Isaac Asimov")
                .addFilter("format", "hardcover")
                .priceRange(10.0, 50.0)
                .dateRange("1950-01-01", "2000-12-31")
                .excludeTerm("children")
                .excludeTerm("comic")
                .page(3)
                .pageSize(15)
                .sortBy("publication_date", true)
                .build();
        
        System.out.println("\nQuery 5 (complex search with all features):");
        System.out.println("SQL: " + query5.toSqlString());
        
        // Example 6: Demonstrate incremental building
        System.out.println("\nExample 6 (incremental building):");
        
        // Start with a basic builder
        SearchQuery.SearchQueryBuilder builder = new SearchQuery.SearchQueryBuilder()
                .keyword("furniture")
                .addCategory("Home");
        
        // Build an initial query
        SearchQuery initialQuery = builder.build();
        System.out.println("Initial Query SQL: " + initialQuery.toSqlString());
        
        // Add more filters
        builder.addFilter("material", "wood");
        builder.priceRange(100.0, 1000.0);
        
        // Build an updated query
        SearchQuery updatedQuery = builder.build();
        System.out.println("Updated Query SQL: " + updatedQuery.toSqlString());
        
        // Add even more filters
        builder.sortBy("popularity", false);
        builder.page(2);
        
        // Build the final query
        SearchQuery finalQuery = builder.build();
        System.out.println("Final Query SQL: " + finalQuery.toSqlString());
    }
}