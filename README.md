# PlantBreeding

## Overview

PlantBreeding is a plant management application designed to help users track plant health, maintenance tasks, and fertilizers used for various plants. The application allows users to add new plants, schedule maintenance tasks, and monitor plant status. With a focus on ease of use, it provides functionality to manage plant data, care tasks, and fertilizers efficiently.

## Features

- **Plant Management**:
  - Add new plants and edit their details, including health status, height, and description.
  - Retrieve a list of all plants with filtering options (e.g., show all perennial herbs, show all annual plants).
  - Fetch detailed information about a specific plant.
  - Delete a plant (deletes related tasks, but does not affect fertilizers).

- **Maintenance Task Management**:
  - Schedule and manage maintenance tasks like watering, trimming, and fertilizing.
  - Set cyclic tasks (e.g., daily, weekly) for a plant and track their status (Scheduled, Done, Overdue, Rejected).
  - Implement automatic status updates for overdue tasks.

- **Fertilizer Management**:
  - Add and manage fertilizers used in plant care.
  - Associate multiple fertilizers with plants and vice versa.

## Technical Aspects

- **Technology Stack**:
  - **Backend Framework**: Spring Boot
  - **Build Tool**: Maven
  - **Programming Language**: Java 17+
  - **Database**: Any relational database (H2, PostgreSQL)
  - **ORM**: Hibernate
  - **Architecture**: Layered architecture (Data Layer, Business Logic Layer, Presentation Layer)
  - **Scheduler**: For automatic task status updates.

- **Additional Technologies**:
  - **DTOs** (Data Transfer Objects) for data mapping, using libraries like MapStruct or ModelMapper.
  - **Unit Testing**: Test services and controllers to ensure proper functionality.

- **Database Design**:
  - Each entity (Plant, Task, Fertilizer) includes `id`, `createdDate`, `lastModifiedDate`, and `version` fields (with `@Version` annotation for optimistic locking).
