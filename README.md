# DAO JDBC Project

This project is a Java application that implements the Data Access Object (DAO) pattern using JDBC to interact with a MySQL database. It manages `Department` and `Seller` entities, providing full CRUD (Create, Read, Update, Delete) functionality.

## Project Structure

The project is organized into several packages:

- `application`: Contains the main classes to test the DAO implementations.
- `dao`: Interfaces and JDBC implementations for the DAO pattern.
- `db`: Database connection management and exception handling.
- `entities`: Domain model classes (`Department` and `Seller`).

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

This facilitates maintenance and allows for changing the persistence technology (e.g., switching from JDBC to JPA) with minimal impact on the application code.

## Author

**Arthur Dall Agnol Pinheiro**
* Estudante de Análise e Desenvolvimento de Sistemas,  UPF - Passo Fundo.
* [LinkedIn](https://www.linkedin.com/in/arthur-dall-agnol-pinheiro-b04285357/)
