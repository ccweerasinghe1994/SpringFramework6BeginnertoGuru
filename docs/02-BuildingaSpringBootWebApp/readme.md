# 02 - Building a Spring Boot Web App

## 001 Introduction
## 002 Introduction to Spring
![alt text](image.png)
![alt text](image-1.png)
![alt text](image-2.png)
![alt text](image-3.png)
![alt text](image-4.png)
![alt text](image-5.png)
## 003 Application Overview
![alt text](image-6.png)
![alt text](image-7.png)
![alt text](image-8.png)
![alt text](image-9.png)

## 004 Spring Initilizer
go to start.spring.io

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.3.2</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.wchamara</groupId>
    <artifactId>spring-6-webapp</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>spring-6-webapp</name>
    <description>spring-6-webapp</description>
    <url/>
    <licenses>
        <license/>
    </licenses>
    <developers>
        <developer/>
    </developers>
    <scm>
        <connection/>
        <developerConnection/>
        <tag/>
        <url/>
    </scm>
    <properties>
        <java.version>21</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```
## 005 Open Project in IntelliJ

[maven folder structure](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html)

![alt text](image-10.png)
![alt text](image-11.png)
## 006 Using JPA Entities
```java
package com.wchamara.spring6webapp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents an author entity.
 * This class is mapped to a database table using JPA annotations.
 */
@Entity
public class Author {

    /**
     * The unique identifier for the author.
     * This value is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The first name of the author.
     */
    private String firstName;

    /**
     * The last name of the author.
     */
    private String lastName;

}
```
```java
package com.wchamara.spring6webapp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents a book entity.
 * This class is mapped to a database table using JPA annotations.
 */
@Entity
public class Book {

    /**
     * The unique identifier for the book.
     * This value is automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The title of the book.
     */
    private String title;

    /**
     * The ISBN of the book.
     */
    private String isbn;
}
```
## 007 JPA Relationships
## 008 Code Examples in GitHub
## 009 Equality in Hibernate
## 010 Spring Data Repositories
## 011 Initializing Data with Spring
## 012 Publisher Relationships
## 013 Introduction to H2 Database Console
## 014 Introduction to Spring MVC
## 015 Create Service Layer
## 016 Configuring Spring Controllers
## 017 Thymeleaf Templates
## 018 Project Review