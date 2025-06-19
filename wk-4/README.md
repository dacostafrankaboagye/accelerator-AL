## Mastering Build Automation
- Scope: Configure a Maven build for a Spring Boot microservice application.
- Exercise 1.1: Setting Up Maven Build
    - Design a multi-module project structure for your microservice application using Maven. Separate modules can handle functionalities like core business logic, data access, and API layer.
- Exercise 1.2: Advanced Maven Features
    - Explore advanced Maven features like custom profiles, plugins for code coverage (e.g., JaCoCo), and static code analysis (e.g., SpotBugs).

- Solution
    - [./microservice-parent/README.md](./microservice-parent/README.md)

## Building Continuous Delivery Pipelines with Jenkins and Docker
- Scope: Set up a CI/CD pipeline using Jenkins and Docker to automate 
the build, test, and deployment process for a Spring Boot application.
- Exercise 2.1: Introduction to Jenkins
    - If you're new to Jenkins, set up a Jenkins server and explore its basic functionalities for creating jobs and managing builds.
- Exercise 2.2: Building a Jenkins Pipeline
    - Design a Jenkins pipeline for your Spring Boot application that includes stages for:
        - Code checkout from a version control system (e.g., Git)
        - Maven (or Gradle) build and unit testing
        - Docker image building and pushing to a registry
        - Deployment to a staging or production environment
        (consider tools like Ansible or Chef for configuration management)
- Exercise 2.3: Containerizing with Docker
    - Create a Dockerfile for your Spring Boot application, specifying the base image, dependencies, and application startup command.
- Solution
    - [./CDPipelineswithJenkinsandDocker/portfolioapplication/README.md](./CDPipelineswithJenkinsandDocker/portfolioapplication/README.md)

## Building a Multithreaded Application
- Scope: Develop a multithreaded application that processes a large 
dataset in parallel.
- Exercise 3.1: Memory Management Fundamentals
    - Research the garbage collection process in Java. How does garbage collection work, and what are common pitfalls to avoid (e.g., memory leaks)?
- Exercise 3.2: Threading Basics
    - Create a simple multithreaded application that performs tasks like downloading files or processing data concurrently using threads.
- Exercise 3.3: Advanced Concurrency
    - Explore advanced concurrency features in Java:
        - Fork/Join Framework: Implement parallel processing of tasks using the fork/join framework for efficient utilization of multiple cores.
        - Concurrent Collections: Utilize concurrent collections like ConcurrentHashMap for thread-safe access to shared data structures.
- Solution
    - [./MultithreadedApp/README.md](./MultithreadedApp/README.md)