# DAO JDBC Project

This project is a Java application that implements the Data Access Object (DAO) pattern using JDBC to interact with a MySQL database. It manages `Department` and `Seller` entities, providing full CRUD (Create, Read, Update, Delete) functionality.

## Project Structure

The project is organized into several packages:

- `application`: Contains the main classes to test the DAO implementations.
- `dao`: Interfaces and JDBC implementations for the DAO pattern.
- `db`: Database connection management and exception handling.
- `entities`: Domain model classes (`Department` and `Seller`).

## Technologies Used

- **Java**: Core language.
- **JDBC (Java Database Connectivity)**: For database interaction.
- **MySQL**: Relational database.

## Getting Started

### Prerequisites

- JDK 11 or higher.
- MySQL Server.
- A MySQL connector driver (JAR) added to the project's classpath.

### Database Setup

1. Create a database named `daojdbc`.
2. Run the `script.sql` file located in the project root to create the `department` and `seller` tables and populate them with initial data.

### Configuration

Update the `db.properties` file in the project root with your database credentials:

```properties
user=your_username
password=your_password
dburl=jdbc:mysql://localhost:3306/daojdbc
useSSL=false
```

## How to Run

You can run the test classes located in the `src/application` package:

1. **SellerTest.java**: Runs CRUD operations for the `Seller` entity.
2. **DepartmentTest.java**: Runs CRUD operations for the `Department` entity.

### Example Operations

- **findById()**: Retrieves an entity based on its primary key.
- **findAll()**: Retrieves all records from the table.
- **insert()**: Adds a new record.
- **update()**: Modifies an existing record.
- **delete()**: Removes a record by object or ID.

## Dependency Injection and Factory Pattern

The project uses the **Factory** pattern through the `DaoFactory` class to manage the creation of DAO instances. This allows for a simple form of **Dependency Injection**, ensuring that application classes do not know the specific implementation (JDBC), only the interfaces.

### How it works:

1.  **Interfaces**: Define the contract for operations (`SellerDao`, `DepartmentDao`).
2.  **Implementations**: Classes that perform actual data access (`SellerDaoJDBC`, `DepartmentDaoJDBC`).
3.  **DaoFactory**: Class responsible for instantiating implementations and injecting the database connection.

**Usage example:**

```java
// The application knows only the interface, not the implementation
SellerDao sellerDao = DaoFactory.createSellerDao();
```

This facilitates maintenance and allows for changing the persistence technology (e.g., switching from JDBC to JPA) with minimal impact on the application code.

## Features

- **DAO Pattern**: Decouples business logic from data access logic.
- **Factory Pattern**: Uses `DaoFactory` to instantiate DAOs, hiding implementation details.
- **Exception Handling**: Custom `DBException` for database-related errors.
- **Connection Management**: Centralized `DB` class to open and close connections safely.

## Author

Arthur Dall Agnol Pinheiro.
