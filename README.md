# PlantBreeding
Project CRUD 

Overview
This application is designed to manage plant cultivation and maintenance tasks. Users can track plant health, tasks related to plant care, and fertilizers used for various plants. The system allows easy retrieval and management of plant data, including their health status, planting date, and maintenance tasks.

Features
Plant Management:

Add new plants, edit their details (health status, height, etc.), and delete them (which also deletes related tasks).
List all plants, with the ability to filter by plant type and other criteria (e.g., perennial herbs).
Retrieve detailed information about a specific plant.
Maintenance Tasks:

Schedule and track maintenance tasks (e.g., watering, trimming, fertilizing) for each plant, including cyclic tasks with custom frequencies (daily, weekly, monthly).
Track the status of tasks (Scheduled, Done, Overdue, Rejected).
Automatic task status update for overdue tasks.
Fertilizer Management:

Add and manage fertilizers used in plant care, including details like type and application method.
Plants can be associated with multiple fertilizers, and a fertilizer can be used by multiple plants.
Technical Aspects
Technology Stack:

Backend Framework: Spring Boot
Build Tool: Maven
Programming Language: Java 17 or higher
Database: Any relational database (H2, PostgreSQL, MySQL)
ORM: Hibernate
Architectural Pattern: Layered architecture (Data layer, Business Logic layer, Presentation layer)
Scheduler: For automatic task status update
Key Technologies:

DTOs (Data Transfer Objects): Used for data mapping between layers, utilizing libraries like MapStruct or ModelMapper.
Versioning: Entities utilize @Version annotation to handle optimistic locking.
Unit Testing:

Unit tests are implemented for service classes and controllers to ensure the application’s correctness.
