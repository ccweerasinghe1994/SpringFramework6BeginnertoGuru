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

The provided code snippet is a part of a Java class, likely representing a Book entity in a JPA (Java Persistence API) context. This snippet defines a many-to-many relationship between the Book entity and another entity, Author.

The @ManyToMany annotation indicates that each Book can be associated with multiple Author entities, and each Author can be associated with multiple Book entities. This type of relationship is common in scenarios where entities have a bidirectional relationship, such as books and authors, where a book can have multiple authors and an author can write multiple books.

The @JoinTable annotation specifies the details of the join table that will be used to manage this many-to-many relationship in the database. The name attribute of the @JoinTable annotation defines the name of the join table, which in this case is author_book. This table will contain the foreign keys that link the Book and Author entities.

The joinColumns attribute specifies the foreign key column in the join table that refers to the Book entity. Here, it is defined as book_id. The inverseJoinColumns attribute specifies the foreign key column in the join table that refers to the Author entity, defined as author_id. These columns will store the IDs of the Book and Author entities, respectively, establishing the many-to-many relationship between them.

Finally, the private Set<Author> authors; line declares a collection of Author entities associated with the Book entity. The use of Set ensures that each Author is unique within the collection, preventing duplicate entries. This collection will be populated with the Author entities that are linked to the Book through the join table.

```java
    @ManyToOne()
    private Publisher publisher;

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
```

The provided code defines a [`Publisher`](command:_github.copilot.openSymbolFromReferences?%5B%22Publisher%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A805%2C%22character%22%3A13%7D%7D%5D%5D "Go to definition") class in Java, which is part of the [`com.wchamara.spring6webapp.domain`](command:_github.copilot.openSymbolFromReferences?%5B%22com.wchamara.spring6webapp.domain%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A796%2C%22character%22%3A8%7D%7D%5D%5D "Go to definition") package. This class is annotated with `@Entity`, indicating that it is a JPA entity and will be mapped to a database table. The [`Publisher`](command:_github.copilot.openSymbolFromReferences?%5B%22Publisher%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A805%2C%22character%22%3A13%7D%7D%5D%5D "Go to definition") class contains several fields representing the attributes of a publisher, such as [`id`](command:_github.copilot.openSymbolFromReferences?%5B%22id%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A809%2C%22character%22%3A17%7D%7D%5D%5D "Go to definition"), [`publisherName`](command:_github.copilot.openSymbolFromReferences?%5B%22publisherName%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A811%2C%22character%22%3A19%7D%7D%5D%5D "Go to definition"), [`address`](command:_github.copilot.openSymbolFromReferences?%5B%22address%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A813%2C%22character%22%3A19%7D%7D%5D%5D "Go to definition"), [`city`](command:_github.copilot.openSymbolFromReferences?%5B%22city%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A815%2C%22character%22%3A19%7D%7D%5D%5D "Go to definition"), [`state`](command:_github.copilot.openSymbolFromReferences?%5B%22state%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A817%2C%22character%22%3A19%7D%7D%5D%5D "Go to definition"), and [`zipCode`](command:_github.copilot.openSymbolFromReferences?%5B%22zipCode%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A819%2C%22character%22%3A19%7D%7D%5D%5D "Go to definition").

The [`id`](command:_github.copilot.openSymbolFromReferences?%5B%22id%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A809%2C%22character%22%3A17%7D%7D%5D%5D "Go to definition") field is annotated with `@Id` and `@GeneratedValue(strategy = GenerationType.AUTO)`, which designates it as the primary key of the entity and specifies that its value will be automatically generated. The other fields are simple attributes that store the publisher's name, address, city, state, and zip code.

The [`books`](command:_github.copilot.openSymbolFromReferences?%5B%22books%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A822%2C%22character%22%3A22%7D%7D%5D%5D "Go to definition") field is a [`Set<Book>`](command:_github.copilot.openSymbolFromReferences?%5B%22Set%3CBook%3E%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A802%2C%22character%22%3A17%7D%7D%5D%5D "Go to definition") and is annotated with `@OneToMany(mappedBy = "publisher")`. This indicates a one-to-many relationship between [`Publisher`](command:_github.copilot.openSymbolFromReferences?%5B%22Publisher%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A805%2C%22character%22%3A13%7D%7D%5D%5D "Go to definition") and [`Book`](command:_github.copilot.openSymbolFromReferences?%5B%22Book%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A822%2C%22character%22%3A16%7D%7D%5D%5D "Go to definition"), where one publisher can have multiple books. The [`mappedBy`](command:_github.copilot.openSymbolFromReferences?%5B%22mappedBy%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A821%2C%22character%22%3A15%7D%7D%5D%5D "Go to definition") attribute specifies that the [`publisher`](command:_github.copilot.openSymbolFromReferences?%5B%22publisher%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A821%2C%22character%22%3A27%7D%7D%5D%5D "Go to definition") field in the [`Book`](command:_github.copilot.openSymbolFromReferences?%5B%22Book%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A822%2C%22character%22%3A16%7D%7D%5D%5D "Go to definition") class owns the relationship.

The class includes getter and setter methods for each field, allowing for encapsulation and controlled access to the fields. These methods are standard in JavaBeans and are used by frameworks and libraries for various purposes, such as data binding and serialization.

The [`equals`](command:_github.copilot.openSymbolFromReferences?%5B%22equals%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A882%2C%22character%22%3A19%7D%7D%5D%5D "Go to definition") and [`hashCode`](command:_github.copilot.openSymbolFromReferences?%5B%22hashCode%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A890%2C%22character%22%3A15%7D%7D%5D%5D "Go to definition") methods are overridden to provide proper equality checks and hash code generation based on the [`id`](command:_github.copilot.openSymbolFromReferences?%5B%22id%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A809%2C%22character%22%3A17%7D%7D%5D%5D "Go to definition"), [`publisherName`](command:_github.copilot.openSymbolFromReferences?%5B%22publisherName%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A811%2C%22character%22%3A19%7D%7D%5D%5D "Go to definition"), [`address`](command:_github.copilot.openSymbolFromReferences?%5B%22address%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A813%2C%22character%22%3A19%7D%7D%5D%5D "Go to definition"), [`city`](command:_github.copilot.openSymbolFromReferences?%5B%22city%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A815%2C%22character%22%3A19%7D%7D%5D%5D "Go to definition"), [`state`](command:_github.copilot.openSymbolFromReferences?%5B%22state%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A817%2C%22character%22%3A19%7D%7D%5D%5D "Go to definition"), and [`zipCode`](command:_github.copilot.openSymbolFromReferences?%5B%22zipCode%22%2C%5B%7B%22uri%22%3A%7B%22%24mid%22%3A1%2C%22fsPath%22%3A%22c%3A%5C%5CUsers%5C%5Cchama%5C%5COneDrive%5C%5CDocuments%5C%5CGitHub%5C%5CSpringFramework6BeginnertoGuru%5C%5Cdocs%5C%5C02-BuildingaSpringBootWebApp%5C%5Creadme.md%22%2C%22_sep%22%3A1%2C%22external%22%3A%22file%3A%2F%2F%2Fc%253A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22path%22%3A%22%2Fc%3A%2FUsers%2Fchama%2FOneDrive%2FDocuments%2FGitHub%2FSpringFramework6BeginnertoGuru%2Fdocs%2F02-BuildingaSpringBootWebApp%2Freadme.md%22%2C%22scheme%22%3A%22file%22%7D%2C%22pos%22%3A%7B%22line%22%3A819%2C%22character%22%3A19%7D%7D%5D%5D "Go to definition") fields. This is important for the correct functioning of collections and other data structures that rely on these methods.

Overall, this class serves as a model for a publisher entity in a Spring-based web application, providing the necessary annotations and methods to integrate with JPA and other frameworks.

```java
package com.wchamara.spring6webapp.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String publisherName;

    private String address;

    private String city;

    private String state;

    private String zipCode;

    @OneToMany(mappedBy = "publisher")
    private Set<Book> books = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publisher publisher = (Publisher) o;
        return Objects.equals(id, publisher.id) && Objects.equals(publisherName, publisher.publisherName) && Objects.equals(address, publisher.address) && Objects.equals(city, publisher.city) && Objects.equals(state, publisher.state) && Objects.equals(zipCode, publisher.zipCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, publisherName, address, city, state, zipCode);
    }
}

```

The selected code is a Java interface named PublisherRepository. It is located in the com.wchamara.spring6webapp.repository package. This interface extends the CrudRepository interface provided by the Spring Data framework.

The purpose of this interface is to define the methods for performing CRUD (Create, Read, Update, Delete) operations on the Publisher entity. The CrudRepository interface provides generic methods for these operations, such as save(), findById(), findAll(), and deleteById().

By extending the CrudRepository interface, the PublisherRepository interface inherits these methods and can be used to interact with the underlying data source, such as a database, to perform CRUD operations on Publisher objects.

The CrudRepository interface is a part of the Spring Data framework, which simplifies the process of working with databases in Spring applications. It provides a set of generic interfaces and classes that can be extended or implemented to perform common database operations without writing boilerplate code.

In this case, the PublisherRepository interface is parameterized with two types: Publisher and Long. The Publisher type represents the entity class that this repository is responsible for, and the Long type represents the data type of the primary key of the Publisher entity.

Overall, this code defines a repository interface that allows for easy and standardized access to the Publisher entity's data, thanks to the Spring Data framework.

```java
package com.wchamara.spring6webapp.repository;

import com.wchamara.spring6webapp.domain.Publisher;
import org.springframework.data.repository.CrudRepository;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {
}

```

The provided code is a Java class named BootstrapData that implements the CommandLineRunner interface, which is part of the Spring Boot framework. This class is annotated with @Component, making it a Spring-managed bean that will be automatically detected and instantiated by the Spring container.

The BootstrapData class has three private final fields: authorRepository, bookRepository, and publisherRepository. These fields are instances of repository interfaces that extend Spring Data JPA repositories, allowing for CRUD operations on Author, Book, and Publisher entities, respectively. The constructor of the BootstrapData class initializes these fields through dependency injection.

The run method, which is overridden from the CommandLineRunner interface, is executed when the Spring Boot application starts. Within this method, instances of Author, Book, and Publisher are created and populated with sample data. Specifically, two authors (Eric Evans and Rod Johnson), two books ("Domain Driven Design" and "J2EE Development without EJB"), and one publisher (SFG Publishing) are instantiated and their properties are set.

The publisher is saved to the database using the publisherRepository, and the books are associated with this publisher. The authors and books are then saved to their respective repositories. After saving, the books are linked to the publisher, and the authors are linked to the books. These associations are saved back to the database to ensure the relationships are persisted.

Finally, the method prints out the counts of authors, books, and publishers in the repositories to the console, providing a simple verification that the data has been correctly initialized and saved. This setup is useful for bootstrapping the application with initial data, making it easier to test and develop against a pre-populated database.

```java
package com.wchamara.spring6webapp.bootstrap;

import com.wchamara.spring6webapp.domain.Author;
import com.wchamara.spring6webapp.domain.Book;
import com.wchamara.spring6webapp.domain.Publisher;
import com.wchamara.spring6webapp.repository.AuthorRepository;
import com.wchamara.spring6webapp.repository.BookRepository;
import com.wchamara.spring6webapp.repository.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
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

        Publisher publisher = new Publisher();
        publisher.setPublisherName("SFG Publishing");
        publisher.setAddress("St. Petersburg");
        publisher.setCity("St. Petersburg");
        publisher.setState("FL");
        publisher.setZipCode("33701");
        Publisher savedPublisher = publisherRepository.save(publisher);

        savedPublisher.getBooks().add(ddd);
        savedPublisher.getBooks().add(noEJB);

        publisherRepository.save(savedPublisher);

        System.out.println("Started in Bootstrap");

        Author ericSaved = authorRepository.save(eric);
        Author rodSaved = authorRepository.save(rod);

        Book dddSaved = bookRepository.save(ddd);
        Book noEJBSaved = bookRepository.save(noEJB);

        dddSaved.setPublisher(savedPublisher);
        noEJBSaved.setPublisher(savedPublisher);

        bookRepository.save(dddSaved);
        bookRepository.save(noEJBSaved);

        ericSaved.getBooks().add(noEJBSaved);
        rodSaved.getBooks().add(dddSaved);

        authorRepository.save(ericSaved);
        authorRepository.save(rodSaved);

        System.out.println("Authors: " + authorRepository.count());
        System.out.println("Books: " + bookRepository.count());
        System.out.println("Publisher: " + publisherRepository.count());


    }

}

```
## 013 Introduction to H2 Database Console
## 014 Introduction to Spring MVC
## 015 Create Service Layer
## 016 Configuring Spring Controllers
## 017 Thymeleaf Templates
## 018 Project Review