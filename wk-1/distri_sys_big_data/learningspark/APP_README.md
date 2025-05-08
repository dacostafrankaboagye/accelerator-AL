# Folder Structure Viewer

This utility displays the folder structure of the project. It's a simple Java application that recursively traverses the directory tree and prints a tree-like representation of all files and directories.

## How to Run

You can run the Folder Structure Viewer using Maven:

```
# From the root directory of the project
mvn compile
mvn exec:java
```

This will display a tree-like representation of all files and directories in the project, helping you navigate the codebase more easily.

## What It Does

The application:

1. Gets the current directory using `System.getProperty("user.dir")`
2. Recursively traverses all subdirectories
3. Prints each file and directory with proper indentation to create a tree-like structure

## Example Output

```
├── learningspark
  ├── README.md
  ├── pom.xml
  ├── src
    ├── main
      ├── java
        ├── org
          ├── frank
            ├── learningspark
              ├── App.java
  ├── dataframe-operations
    ├── README.md
    ├── pom.xml
    ├── src
      ├── main
        ├── java
          ├── org
            ├── frank
              ├── learningspark
                ├── dataframe
                  ├── BasicDataFrameExample.java
        ├── resources
          ├── sales.csv
  ├── rdd-operations
    ├── README.md
    ├── pom.xml
    ├── src
      ├── main
        ├── java
          ├── org
            ├── frank
              ├── learningspark
                ├── rdd
                  ├── BasicRDDExample.java
```

This makes it easy to understand the project structure at a glance.