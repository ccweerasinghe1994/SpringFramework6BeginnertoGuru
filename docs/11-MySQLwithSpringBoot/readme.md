# 11 - MySQL with Spring Boot

## 001 Introduction
![alt text](image.png)
## 002 Overview of MySQL
![alt text](image-1.png)
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
![alt text](image-12.png)
![alt text](image-13.png)
## 004 Create MySQL Schema and User Accounts
```sql
# SET GLOBAL authentication_policy = 'ONLY_MYSQL_NATIVE_PASSWORD';
DROP DATABASE IF EXISTS restdb;
DROP USER IF EXISTS `restadmin`@`%`;
CREATE DATABASE IF NOT EXISTS restdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
# CREATE USER IF NOT EXISTS `restadmin`@`%` IDENTIFIED WITH mysql_native_password BY 'password';
CREATE USER IF NOT EXISTS `restadmin`@`%` IDENTIFIED BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW,
    CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `restdb`.* TO `restadmin`@`%`;

```

The SQL script you've provided is a series of commands that are typically executed in a MySQL database to manage a database and a user with specific privileges. Below is an explanation of each part of the script:

### 1. `SET GLOBAL authentication_policy = 'ONLY_MYSQL_NATIVE_PASSWORD';`

- **Explanation**: This line, which is commented out (`#`), would set the global authentication policy for the MySQL server. The policy `ONLY_MYSQL_NATIVE_PASSWORD` would restrict user authentication to the `mysql_native_password` plugin only. However, in newer versions of MySQL, `caching_sha2_password` is the default, and the `authentication_policy` variable controls the allowed authentication methods. This line ensures that only the older `mysql_native_password` authentication method is allowed, enhancing compatibility with older applications.

### 2. `DROP DATABASE IF EXISTS restdb;`

- **Explanation**: This command deletes the database named `restdb` if it already exists. The `IF EXISTS` clause prevents an error if the database doesn't exist. This is typically used to reset the database before re-creating it.

### 3. `DROP USER IF EXISTS 'restadmin'@'%';`

- **Explanation**: This command deletes the user `restadmin` that can connect from any host (`'%'`). The `IF EXISTS` clause prevents an error if the user doesn't exist. This is usually done to remove any existing user account before creating a fresh one.

### 4. `CREATE DATABASE IF NOT EXISTS restdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;`

- **Explanation**: This command creates a new database named `restdb` if it does not already exist. The `CHARACTER SET utf8mb4` specifies the character encoding for the database, which supports a wide range of characters, including emojis. The `COLLATE utf8mb4_unicode_ci` specifies the collation, which determines how text is sorted and compared in the database, using Unicode rules.

### 5. `CREATE USER IF NOT EXISTS 'restadmin'@'%' IDENTIFIED BY 'password';`

- **Explanation**: This command creates a new user named `restadmin` with the password `'password'` who can connect from any host (`'%'`). The `IF NOT EXISTS` clause prevents an error if the user already exists. The `IDENTIFIED BY` clause sets the user's password.

   - **Note**: The comment above this command (`# CREATE USER IF NOT EXISTS 'restadmin'@'%' IDENTIFIED WITH mysql_native_password BY 'password';`) suggests an alternative method for creating the user using the `mysql_native_password` plugin, which is older and less secure than the newer `caching_sha2_password` plugin.

### 6. `GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW, CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON restdb.* TO 'restadmin'@'%';`

- **Explanation**: This command grants the `restadmin` user a comprehensive set of privileges on all tables within the `restdb` database. The privileges include the ability to select, insert, update, and delete data, create and drop tables, manage indexes, create and alter routines, manage events and triggers, and more.

### Summary

This script is typically used during the setup of a MySQL database environment, especially when you want to ensure a clean state by dropping any existing database and user before recreating them with specific privileges. The script sets up a `restdb` database and a `restadmin` user, granting them full control over the `restdb` database. The inclusion of the `mysql_native_password` option, though commented out, suggests a consideration for backward compatibility with older clients or systems that may not support the newer `caching_sha2_password` plugin.
## 005 Adding MySQL Dependencies

```xml
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
        </dependency>

```
## 006 Spring Boot MySQL Profile
```properties
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/restdb?unicode=true&characterEncoding=utf8&serverTimezone=UTC
spring.jpa.database=mysql
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

```
## 007 Console Logging of SQL Statements
## 008 JPA Updates for MySQL
## 009 Hikari Datasource Pool
## 010 Schema Script Generation
## 011 Spring Boot Database Initialization