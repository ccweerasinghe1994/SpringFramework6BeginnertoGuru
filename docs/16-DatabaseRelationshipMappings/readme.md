# 16 - Database Relationship Mappings

## 001 Introduction
![alt text](image.png)
## 002 Overview of Database Relationships
![alt text](image-1.png)

Relational databases are a type of database that stores and provides access to data points that are related to one another. The data in a relational database is organized into tables, which are structured into rows and columns. Each table typically represents an entity or concept, such as customers, products, or orders, and each row in a table represents a specific instance of that entity.

### Key Concepts of Relational Databases:

1. **Tables**: 
   - Also known as relations, tables are the fundamental building blocks of a relational database. A table consists of rows (also called records or tuples) and columns (also called fields or attributes).
   - Each table usually has a primary key, a unique identifier for the rows in the table.

2. **Rows and Columns**:
   - **Rows**: Each row in a table represents a single record or data point. For example, in a table of customers, each row might represent one customer.
   - **Columns**: Each column in a table represents a specific attribute of the entity. For example, in a table of customers, columns might include `CustomerID`, `Name`, `Email`, and `PhoneNumber`.

3. **Primary Key**:
   - A primary key is a unique identifier for each row in a table. It ensures that each record can be uniquely identified. For example, a `CustomerID` might be a primary key in a customer table.

4. **Foreign Key**:
   - A foreign key is a field in one table that uniquely identifies a row in another table. It establishes a relationship between two tables. For example, an `Order` table might have a `CustomerID` that links each order to a customer in the `Customers` table.

5. **Relationships**:
   - **One-to-One**: A relationship where a row in one table is linked to a single row in another table.
   - **One-to-Many**: A relationship where a row in one table can be linked to multiple rows in another table. For example, one customer might have multiple orders.
   - **Many-to-Many**: A relationship where rows in one table can be linked to multiple rows in another table, and vice versa. This is often managed through a junction table.

6. **SQL (Structured Query Language)**:
   - SQL is the standard language used to interact with relational databases. SQL commands can be used to create, read, update, and delete data in a relational database (often abbreviated as CRUD operations).
   - Examples of SQL commands include `SELECT` (to retrieve data), `INSERT` (to add data), `UPDATE` (to modify data), and `DELETE` (to remove data).

### Advantages of Relational Databases:

- **Data Integrity**: Enforces rules through constraints like primary keys, foreign keys, and unique constraints, ensuring data accuracy and consistency.
- **Flexibility**: Tables can be easily modified or extended, and complex queries can be executed to retrieve specific data.
- **Security**: Access to data can be controlled at various levels, allowing for detailed permissions and roles.
- **Scalability**: Relational databases can handle large amounts of data and can scale horizontally or vertically.

### Popular Relational Database Management Systems (RDBMS):

- **MySQL**: An open-source relational database system, widely used for web applications.
- **PostgreSQL**: An open-source database known for its advanced features and standards compliance.
- **Oracle Database**: A commercial database known for its scalability and enterprise features.
- **Microsoft SQL Server**: A commercial database developed by Microsoft, widely used in enterprise environments.
- **SQLite**: A lightweight, embedded relational database often used in mobile applications and small-scale projects.

Relational databases are a fundamental technology in software development, data management, and many enterprise applications. They provide a structured and efficient way to store and retrieve related data.


![alt text](image-2.png)
![alt text](image-3.png)
![alt text](image-4.png)
![alt text](image-5.png)
![alt text](image-6.png)
![alt text](image-7.png)
![alt text](image-8.png)
![alt text](image-9.png)
![alt text](image-10.png)
![alt text](image-11.png)
## 003 Review of Database Changes
![alt text](image-12.png)

current database schema

![alt text](image-13.png)

```sql
create table beer
(
    beer_style       tinyint        not null,
    price            decimal(38, 2) not null,
    quantity_on_hand int            null,
    version          int            null,
    created_date     datetime(6)    null,
    updated_date     datetime(6)    null,
    id               varchar(36)    not null
        primary key,
    beer_name        varchar(50)    not null,
    upc              varchar(255)   not null,
    check (`beer_style` between 0 and 9)
);

create table customer
(
    version      int          null,
    created_date datetime(6)  null,
    update_date  datetime(6)  null,
    id           varchar(36)  not null
        primary key,
    name         varchar(255) null,
    email        varchar(255) null
);

create table flyway_schema_history
(
    installed_rank int                                 not null
        primary key,
    version        varchar(50)                         null,
    description    varchar(200)                        not null,
    type           varchar(20)                         not null,
    script         varchar(1000)                       not null,
    checksum       int                                 null,
    installed_by   varchar(100)                        not null,
    installed_on   timestamp default CURRENT_TIMESTAMP not null,
    execution_time int                                 not null,
    success        tinyint(1)                          not null
);

create index flyway_schema_history_s_idx
    on flyway_schema_history (success);


```
This SQL script creates three tables (`beer`, `customer`, `flyway_schema_history`) and an index on the `flyway_schema_history` table. Here's an explanation of each part of the code:

### 1. **`beer` Table**:
   - **Columns**:
     - `beer_style`: A `TINYINT` (a small integer) that cannot be null. The value must be between 0 and 9, as enforced by a `CHECK` constraint.
     - `price`: A `DECIMAL(38, 2)` value representing the price of the beer, which cannot be null.
     - `quantity_on_hand`: An `INT` representing the stock quantity of the beer, which can be null.
     - `version`: An `INT` used for tracking changes or optimistic locking, which can be null.
     - `created_date` & `updated_date`: `DATETIME(6)` values for storing timestamps, allowing up to microseconds precision, which can be null.
     - `id`: A `VARCHAR(36)` representing the unique identifier for each beer, which is the primary key and cannot be null.
     - `beer_name`: A `VARCHAR(50)` for storing the name of the beer, which cannot be null.
     - `upc`: A `VARCHAR(255)` for the Universal Product Code, which cannot be null.
   - **Constraints**:
     - `CHECK (beer_style between 0 and 9)`: Ensures that the `beer_style` value is between 0 and 9.

### 2. **`customer` Table**:
   - **Columns**:
     - `version`: An `INT` that can be null, possibly for versioning.
     - `created_date` & `update_date`: `DATETIME(6)` columns for tracking creation and update times, which can be null.
     - `id`: A `VARCHAR(36)` that serves as the primary key and cannot be null.
     - `name`: A `VARCHAR(255)` for the customer's name, which can be null.
     - `email`: A `VARCHAR(255)` for the customer's email, which can be null.

### 3. **`flyway_schema_history` Table**:
   - **Columns**:
     - `installed_rank`: An `INT` that serves as the primary key.
     - `version`: A `VARCHAR(50)` for the version of the migration script, which can be null.
     - `description`: A `VARCHAR(200)` for a brief description of the migration, which cannot be null.
     - `type`: A `VARCHAR(20)` for the type of migration, which cannot be null.
     - `script`: A `VARCHAR(1000)` for the script name, which cannot be null.
     - `checksum`: An `INT` for storing a checksum, which can be null.
     - `installed_by`: A `VARCHAR(100)` for tracking who installed the migration, which cannot be null.
     - `installed_on`: A `TIMESTAMP` column that defaults to the current timestamp when a record is inserted, which cannot be null.
     - `execution_time`: An `INT` for the execution time of the migration, which cannot be null.
     - `success`: A `TINYINT(1)` for indicating whether the migration was successful, which cannot be null.
   - **Index**:
     - `flyway_schema_history_s_idx`: An index on the `success` column to improve query performance when filtering by `success`.

## 004 One to Many Bidirectional

![alt text](image-14.png)

```sql
drop table if exists beer_order;
drop table if exists beer_order_line;


create table `beer_order`
(
    id                 varchar(36) NOT NULL,
    version            bigint       DEFAULT NULL,
    created_date       datetime(6)  DEFAULT NULL,
    last_modified_date datetime(6)  DEFAULT NULL,
    customer_ref       varchar(255) DEFAULT NULL,
    customer_id        varchar(36)  DEFAULT NULL,
    primary key (id),
    CONSTRAINT FOREIGN KEY (customer_id) REFERENCES customer (id)
) engine = InnoDB;


create table `beer_order_line`
(
    id                 varchar(36) NOT NULL,
    beer_id            varchar(36) DEFAULT NULL,
    created_date       datetime(6) DEFAULT NULL,
    last_modified_date datetime(6) DEFAULT NULL,
    order_quantity     int         DEFAULT NULL,
    quantity_allocated int         DEFAULT NULL,
    version            bigint      DEFAULT NULL,
    beer_order_id      varchar(36) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FOREIGN KEY (beer_order_id) REFERENCES beer_order (id),
    CONSTRAINT FOREIGN KEY (beer_id) REFERENCES beer (id)
) engine = InnoDB;

```



This SQL script drops two tables (`beer_order` and `beer_order_line`) if they exist, then creates them with specific columns, primary keys, and foreign key constraints. Let's break down each part of the script:

### 1. **Dropping Tables**
```sql
DROP TABLE IF EXISTS beer_order;
DROP TABLE IF EXISTS beer_order_line;
```
- These commands ensure that if the `beer_order` and `beer_order_line` tables already exist in the database, they will be deleted before creating new ones. This is useful for avoiding errors when running the script multiple times.

### 2. **Creating the `beer_order` Table**
```sql
CREATE TABLE `beer_order`
(
    id                 varchar(36) NOT NULL,
    version            bigint       DEFAULT NULL,
    created_date       datetime(6)  DEFAULT NULL,
    last_modified_date datetime(6)  DEFAULT NULL,
    customer_ref       varchar(255) DEFAULT NULL,
    customer_id        varchar(36)  DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FOREIGN KEY (customer_id) REFERENCES customer (id)
) ENGINE = InnoDB;
```
- **`id`**: A `VARCHAR(36)` column that serves as the primary key for the table. The size `36` is typically used for storing UUIDs, and `NOT NULL` ensures that this field cannot be left empty.
- **`version`**: A `BIGINT` column that might be used for versioning (optimistic locking) to manage concurrency.
- **`created_date` & `last_modified_date`**: `DATETIME(6)` columns that store timestamps with up to six decimal places of precision (microseconds). These fields can be used to track when the order was created and last modified.
- **`customer_ref`**: A `VARCHAR(255)` column that stores a reference or identifier for the customer, which might be used for business purposes.
- **`customer_id`**: A `VARCHAR(36)` column that holds the ID of the customer associated with the order.
- **`PRIMARY KEY (id)`**: This constraint ensures that the `id` field uniquely identifies each record in the table.
- **`FOREIGN KEY (customer_id) REFERENCES customer (id)`**: This constraint establishes a relationship between the `beer_order` table and the `customer` table, linking the `customer_id` in `beer_order` to the `id` in `customer`. This ensures referential integrity, meaning that any `customer_id` in the `beer_order` table must exist in the `customer` table.

- **`ENGINE = InnoDB`**: Specifies that the table should use the InnoDB storage engine, which supports transactions, foreign keys, and row-level locking.

### 3. **Creating the `beer_order_line` Table**
```sql
CREATE TABLE `beer_order_line`
(
    id                 varchar(36) NOT NULL,
    beer_id            varchar(36) DEFAULT NULL,
    created_date       datetime(6) DEFAULT NULL,
    last_modified_date datetime(6) DEFAULT NULL,
    order_quantity     int         DEFAULT NULL,
    quantity_allocated int         DEFAULT NULL,
    version            bigint      DEFAULT NULL,
    beer_order_id      varchar(36) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FOREIGN KEY (beer_order_id) REFERENCES beer_order (id),
    CONSTRAINT FOREIGN KEY (beer_id) REFERENCES beer (id)
) ENGINE = InnoDB;
```
- **`id`**: A `VARCHAR(36)` column that serves as the primary key for the table, similar to the `beer_order` table.
- **`beer_id`**: A `VARCHAR(36)` column that stores the ID of the beer associated with this order line.
- **`created_date` & `last_modified_date`**: `DATETIME(6)` columns for tracking when the order line was created and last modified, similar to the `beer_order` table.
- **`order_quantity`**: An `INT` column that records the quantity of beer ordered.
- **`quantity_allocated`**: An `INT` column that records the quantity of beer that has been allocated for the order. This could be used to track inventory levels.
- **`version`**: A `BIGINT` column, potentially used for versioning.
- **`beer_order_id`**: A `VARCHAR(36)` column that links each order line to a specific order in the `beer_order` table.
- **`PRIMARY KEY (id)`**: Ensures that each `id` in the `beer_order_line` table is unique.
- **`FOREIGN KEY (beer_order_id) REFERENCES beer_order (id)`**: Establishes a relationship between `beer_order_line` and `beer_order`, linking `beer_order_id` in `beer_order_line` to `id` in `beer_order`.
- **`FOREIGN KEY (beer_id) REFERENCES beer (id)`**: Establishes a relationship between `beer_order_line` and `beer`, linking `beer_id` in `beer_order_line` to `id` in `beer`.
- **`ENGINE = InnoDB`**: Specifies the use of the InnoDB storage engine for the `beer_order_line` table, as with `beer_order`.

### Summary
- **`beer_order`**: Represents a customer's order, with references to the customer.
- **`beer_order_line`**: Represents individual items within an order, with references to the beer and the order it belongs to.

These tables are structured to maintain data integrity and relationships between orders, customers, and beers, using foreign key constraints and the InnoDB engine for support of ACID-compliant transactions.

![alt text](image-15.png)

![alt text](image-16.png)
## 005 Create Beer Order Repository

```java
package com.wchamara.spring6restmvc.repositories;

import com.wchamara.spring6restmvc.entities.BeerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {
}

```

```java
package com.wchamara.spring6restmvc.repositories;

import com.wchamara.spring6restmvc.entities.Beer;
import com.wchamara.spring6restmvc.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BeerOrderRepositoryTest {


    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    Customer testCustomer;
    Beer testBeer;

    @BeforeEach
    void setUp() {
        testCustomer = customerRepository.findAll().get(0);
        testBeer = beerRepository.findAll().get(0);
    }

    @Test
    void name() {
        System.out.println("testCustomer = " + testCustomer.toString());
        System.out.println("testBeer = " + testBeer.toString());

    }
}
```


## 006 Persisting Beer Order Relationships
## 007 Association Helper Methods
## 008 Many to Many
## 009 Many to Many Persistence
## 010 One to One Bi-Directional
## 011 Cascade on Persist
## 012 Hibernate Cascade Types
