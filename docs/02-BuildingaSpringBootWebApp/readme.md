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
The provided code snippet is a part of a Java class, likely representing a `Book` entity in a JPA (Java Persistence API) context. This snippet defines a many-to-many relationship between the `Book` entity and another entity, [`Author`](command:_github.copilot.openSymbolFromReferences?%5B%22Author%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cspring-6-webapp%5C%5Csrc%5C%5Cmain%5C%5Cjava%5C%5Ccom%5C%5Cwchamara%5C%5Cspring6webapp%5C%5Cdomain%5C%5CBook.java%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22path%22%3A%22%2FC%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A25%2C%22character%22%3A16%7D%7D%5D%5D "Go to definition").

The `@ManyToMany` annotation indicates that each `Book` can be associated with multiple [`Author`](command:_github.copilot.openSymbolFromReferences?%5B%22Author%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cspring-6-webapp%5C%5Csrc%5C%5Cmain%5C%5Cjava%5C%5Ccom%5C%5Cwchamara%5C%5Cspring6webapp%5C%5Cdomain%5C%5CBook.java%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22path%22%3A%22%2FC%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A25%2C%22character%22%3A16%7D%7D%5D%5D "Go to definition") entities, and each [`Author`](command:_github.copilot.openSymbolFromReferences?%5B%22Author%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cspring-6-webapp%5C%5Csrc%5C%5Cmain%5C%5Cjava%5C%5Ccom%5C%5Cwchamara%5C%5Cspring6webapp%5C%5Cdomain%5C%5CBook.java%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22path%22%3A%22%2FC%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A25%2C%22character%22%3A16%7D%7D%5D%5D "Go to definition") can be associated with multiple `Book` entities. This type of relationship is common in scenarios where entities have a bidirectional relationship, such as books and authors, where a book can have multiple authors and an author can write multiple books.

The `@JoinTable` annotation specifies the details of the join table that will be used to manage this many-to-many relationship in the database. The [`name`](command:_github.copilot.openSymbolFromReferences?%5B%22name%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cspring-6-webapp%5C%5Csrc%5C%5Cmain%5C%5Cjava%5C%5Ccom%5C%5Cwchamara%5C%5Cspring6webapp%5C%5Cdomain%5C%5CBook.java%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22path%22%3A%22%2FC%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A23%2C%22character%22%3A15%7D%7D%5D%5D "Go to definition") attribute of the `@JoinTable` annotation defines the name of the join table, which in this case is [`author_book`](command:_github.copilot.openSymbolFromReferences?%5B%22author_book%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cspring-6-webapp%5C%5Csrc%5C%5Cmain%5C%5Cjava%5C%5Ccom%5C%5Cwchamara%5C%5Cspring6webapp%5C%5Cdomain%5C%5CBook.java%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22path%22%3A%22%2FC%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A23%2C%22character%22%3A23%7D%7D%5D%5D "Go to definition"). This table will contain the foreign keys that link the `Book` and [`Author`](command:_github.copilot.openSymbolFromReferences?%5B%22Author%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cspring-6-webapp%5C%5Csrc%5C%5Cmain%5C%5Cjava%5C%5Ccom%5C%5Cwchamara%5C%5Cspring6webapp%5C%5Cdomain%5C%5CBook.java%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22path%22%3A%22%2FC%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A25%2C%22character%22%3A16%7D%7D%5D%5D "Go to definition") entities.

The [`joinColumns`](command:_github.copilot.openSymbolFromReferences?%5B%22joinColumns%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cspring-6-webapp%5C%5Csrc%5C%5Cmain%5C%5Cjava%5C%5Ccom%5C%5Cwchamara%5C%5Cspring6webapp%5C%5Cdomain%5C%5CBook.java%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22path%22%3A%22%2FC%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A23%2C%22character%22%3A37%7D%7D%5D%5D "Go to definition") attribute specifies the foreign key column in the join table that refers to the `Book` entity. Here, it is defined as [`book_id`](command:_github.copilot.openSymbolFromReferences?%5B%22book_id%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cspring-6-webapp%5C%5Csrc%5C%5Cmain%5C%5Cjava%5C%5Ccom%5C%5Cwchamara%5C%5Cspring6webapp%5C%5Cdomain%5C%5CBook.java%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22path%22%3A%22%2FC%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A23%2C%22character%22%3A71%7D%7D%5D%5D "Go to definition"). The [`inverseJoinColumns`](command:_github.copilot.openSymbolFromReferences?%5B%22inverseJoinColumns%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cspring-6-webapp%5C%5Csrc%5C%5Cmain%5C%5Cjava%5C%5Ccom%5C%5Cwchamara%5C%5Cspring6webapp%5C%5Cdomain%5C%5CBook.java%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22path%22%3A%22%2FC%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A24%2C%22character%22%3A12%7D%7D%5D%5D "Go to definition") attribute specifies the foreign key column in the join table that refers to the [`Author`](command:_github.copilot.openSymbolFromReferences?%5B%22Author%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cspring-6-webapp%5C%5Csrc%5C%5Cmain%5C%5Cjava%5C%5Ccom%5C%5Cwchamara%5C%5Cspring6webapp%5C%5Cdomain%5C%5CBook.java%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22path%22%3A%22%2FC%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A25%2C%22character%22%3A16%7D%7D%5D%5D "Go to definition") entity, defined as [`author_id`](command:_github.copilot.openSymbolFromReferences?%5B%22author_id%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cspring-6-webapp%5C%5Csrc%5C%5Cmain%5C%5Cjava%5C%5Ccom%5C%5Cwchamara%5C%5Cspring6webapp%5C%5Cdomain%5C%5CBook.java%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22path%22%3A%22%2FC%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A24%2C%22character%22%3A53%7D%7D%5D%5D "Go to definition"). These columns will store the IDs of the `Book` and [`Author`](command:_github.copilot.openSymbolFromReferences?%5B%22Author%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cspring-6-webapp%5C%5Csrc%5C%5Cmain%5C%5Cjava%5C%5Ccom%5C%5Cwchamara%5C%5Cspring6webapp%5C%5Cdomain%5C%5CBook.java%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22path%22%3A%22%2FC%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A25%2C%22character%22%3A16%7D%7D%5D%5D "Go to definition") entities, respectively, establishing the many-to-many relationship between them.

Finally, the [`private Set<Author> authors;`](command:_github.copilot.openSymbolFromReferences?%5B%22private%20Set%3CAuthor%3E%20authors%3B%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cspring-6-webapp%5C%5Csrc%5C%5Cmain%5C%5Cjava%5C%5Ccom%5C%5Cwchamara%5C%5Cspring6webapp%5C%5Cdomain%5C%5CBook.java%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22path%22%3A%22%2FC%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A25%2C%22character%22%3A12%7D%7D%5D%5D "Go to definition") line declares a collection of [`Author`](command:_github.copilot.openSymbolFromReferences?%5B%22Author%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cspring-6-webapp%5C%5Csrc%5C%5Cmain%5C%5Cjava%5C%5Ccom%5C%5Cwchamara%5C%5Cspring6webapp%5C%5Cdomain%5C%5CBook.java%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22path%22%3A%22%2FC%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A25%2C%22character%22%3A16%7D%7D%5D%5D "Go to definition") entities associated with the `Book` entity. The use of [`Set`](command:_github.copilot.openSymbolFromReferences?%5B%22Set%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cspring-6-webapp%5C%5Csrc%5C%5Cmain%5C%5Cjava%5C%5Ccom%5C%5Cwchamara%5C%5Cspring6webapp%5C%5Cdomain%5C%5CBook.java%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22path%22%3A%22%2FC%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A25%2C%22character%22%3A12%7D%7D%5D%5D "Go to definition") ensures that each [`Author`](command:_github.copilot.openSymbolFromReferences?%5B%22Author%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cspring-6-webapp%5C%5Csrc%5C%5Cmain%5C%5Cjava%5C%5Ccom%5C%5Cwchamara%5C%5Cspring6webapp%5C%5Cdomain%5C%5CBook.java%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22path%22%3A%22%2FC%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A25%2C%22character%22%3A16%7D%7D%5D%5D "Go to definition") is unique within the collection, preventing duplicate entries. This collection will be populated with the [`Author`](command:_github.copilot.openSymbolFromReferences?%5B%22Author%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cspring-6-webapp%5C%5Csrc%5C%5Cmain%5C%5Cjava%5C%5Ccom%5C%5Cwchamara%5C%5Cspring6webapp%5C%5Cdomain%5C%5CBook.java%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22path%22%3A%22%2FC%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fspring-6-webapp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fwchamara%2Fspring6webapp%2Fdomain%2FBook.java%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A25%2C%22character%22%3A16%7D%7D%5D%5D "Go to definition") entities that are linked to the `Book` through the join table.
```java
package com.wchamara.spring6webapp.domain;

import jakarta.persistence.*;

import java.util.Set;

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

    /**
     * The set of books associated with the author.
     * This relationship is managed by the Book entity.
     */
    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;

    /**
     * Gets the unique identifier for the author.
     * @return the unique identifier for the author.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the author.
     * @param id the unique identifier for the author.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the first name of the author.
     * @return the first name of the author.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the author.
     * @param firstName the first name of the author.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the author.
     * @return the last name of the author.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the author.
     * @param lastName the last name of the author.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
```
```java

The selected code snippet is part of the `Author` class in a Spring Boot application using JPA (Java Persistence API) for ORM (Object-Relational Mapping). The code defines a many-to-many relationship between the `Author` and `Book` entities.

The `@ManyToMany` annotation is used to specify that the `Author` entity has a many-to-many relationship with the `Book` entity. This means that an author can be associated with multiple books, and a book can be associated with multiple authors. The `mappedBy` attribute in the `@ManyToMany` annotation indicates that the `books` field in the `Author` class is the inverse side of the relationship, and the `authors` field in the `Book` class owns the relationship.

```java
@ManyToMany(mappedBy = "authors")
```

The `private Set<Book> books;` line declares a collection of `Book` objects associated with the `Author`. The `Set` collection type is used to ensure that each book is unique within the collection.

```java
private Set<Book> books;
```

Together, these lines of code establish a bidirectional many-to-many relationship between the `Author` and `Book` entities, allowing the application to navigate from an author to their books and vice versa.


```java
package com.wchamara.spring6webapp.domain;

import jakarta.persistence.*;

import java.util.Set;

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

    /**
     * The set of authors associated with the book.
     * This relationship is managed by the Author entity.
     */
    @ManyToMany
    @JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;

    /**
     * Gets the unique identifier for the book.
     * @return the unique identifier for the book.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the book.
     * @param id the unique identifier for the book.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the title of the book.
     * @return the title of the book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     * @param title the title of the book.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the ISBN of the book.
     * @return the ISBN of the book.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN of the book.
     * @param isbn the ISBN of the book.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
```
```
## 008 Code Examples in GitHub
## 009 Equality in Hibernate
```java
package com.wchamara.spring6webapp.domain;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

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

    /**
     * The set of authors associated with the book.
     * This relationship is managed by the Author entity.
     */
    @ManyToMany
    @JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;

    /**
     * Gets the unique identifier for the book.
     * @return the unique identifier for the book.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the book.
     * @param id the unique identifier for the book.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the title of the book.
     * @return the title of the book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     * @param title the title of the book.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the ISBN of the book.
     * @return the ISBN of the book.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN of the book.
     * @param isbn the ISBN of the book.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Gets the set of authors associated with the book.
     * @return the set of authors associated with the book.
     */
    public Set<Author> getAuthors() {
        return authors;
    }

    /**
     * Sets the set of authors associated with the book.
     * @param authors the set of authors associated with the book.
     */
    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    /**
     * Checks if this book is equal to another object.
     * @param o the object to compare with.
     * @return true if this book is equal to the other object, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && Objects.equals(title, book.title) && Objects.equals(isbn, book.isbn) && Objects.equals(authors, book.authors);
    }

    /**
     * Generates a hash code for this book.
     * @return the hash code for this book.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, title, isbn, authors);
    }
}
```
```java
package com.wchamara.spring6webapp.domain;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

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

    /**
     * The set of books associated with the author.
     * This relationship is managed by the Book entity.
     */
    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;

    /**
     * Gets the set of books associated with the author.
     * @return the set of books associated with the author.
     */
    public Set<Book> getBooks() {
        return books;
    }

    /**
     * Sets the set of books associated with the author.
     * @param books the set of books associated with the author.
     */
    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    /**
     * Gets the unique identifier for the author.
     * @return the unique identifier for the author.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the author.
     * @param id the unique identifier for the author.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the first name of the author.
     * @return the first name of the author.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the author.
     * @param firstName the first name of the author.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the author.
     * @return the last name of the author.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the author.
     * @param lastName the last name of the author.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Checks if this author is equal to another object.
     * @param o the object to compare with.
     * @return true if this author is equal to the other object, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id == author.id && Objects.equals(firstName, author.firstName) && Objects.equals(lastName, author.lastName) && Objects.equals(books, author.books);
    }

    /**
     * Generates a hash code for this author.
     * @return the hash code for this author.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, books);
    }
}
```
## 010 Spring Data Repositories
```java
/**
 * This interface represents a repository for managing Author entities.
 * It extends the CrudRepository interface, providing basic CRUD operations
 * for the Author entity.
 *
 * @param <Author> the type of entity being managed by the repository
 * @param <Long>   the type of the entity's ID
 */
package com.wchamara.spring6webapp.repository;
     
import com.wchamara.spring6webapp.domain.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
```
```java
/**
 * This interface represents a repository for managing books in a Spring Boot web application.
 * It extends the `CrudRepository` interface, which provides basic CRUD operations for the `Book` entity.
 *
 * @param <Book> The type of entity being managed by the repository.
 * @param <Long> The type of the primary key of the entity.
 */
package com.wchamara.spring6webapp.repository;

import com.wchamara.spring6webapp.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}

```
## 011 Initializing Data with Spring
let's create a bootstrap class that will initialize some data in the database when the application starts up. This class will implement the `CommandLineRunner` interface, which provides a callback method that will be executed after the application context is loaded and before the application is started.

```java
package com.wchamara.spring6webapp.bootstrap;

import com.wchamara.spring6webapp.domain.Author;
import com.wchamara.spring6webapp.domain.Book;
import com.wchamara.spring6webapp.repository.AuthorRepository;
import com.wchamara.spring6webapp.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Author eric = new Author();
        eric.setFirstName("Eric");
        eric.setLastName("Evans");

        Author rod = new Author();
        rod.setFirstName("Rod");
        rod.setLastName("Johnson");

        Book ddd = new Book();
        ddd.setTitle("Domain Driven Design");
        ddd.setIsbn("1234");

        Book noEJB = new Book();
        noEJB.setTitle("J2EE Development without EJB");
        noEJB.setIsbn("2345");

        System.out.println("Started in Bootstrap");

        Author ericSaved = authorRepository.save(eric);
        Author rodSaved = authorRepository.save(rod);

        Book dddSaved = bookRepository.save(ddd);
        Book noEJBSaved = bookRepository.save(noEJB);

        ericSaved.getBooks().add(noEJBSaved);
        rodSaved.getBooks().add(dddSaved);

        authorRepository.save(ericSaved);
        authorRepository.save(rodSaved);

        System.out.println("Authors: " + authorRepository.count());
        System.out.println("Books: " + bookRepository.count());


    }

}
```

```java
    /**
     * The set of books associated with the author.
     * This relationship is managed by the Book entity.
     */
    @ManyToMany(mappedBy = "authors")
    private Set<Book> books = new HashSet<>();
```

```java
    @ManyToMany
    @JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new HashSet<>();

```

```java
package com.wchamara.spring6webapp.repository;

import com.wchamara.spring6webapp.domain.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}

```
```java
package com.wchamara.spring6webapp.repository;

import com.wchamara.spring6webapp.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}

```

## 012 Publisher Relationships
## 013 Introduction to H2 Database Console
## 014 Introduction to Spring MVC
## 015 Create Service Layer
## 016 Configuring Spring Controllers
## 017 Thymeleaf Templates
## 018 Project Review