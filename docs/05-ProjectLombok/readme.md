# 05 - Project Lombok

## 001 Introduction
![alt text](image.png)
## 002 Create New Project
![alt text](image-1.png)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <!-- Model version for the POM -->
    <modelVersion>4.0.0</modelVersion>
    
    <!-- Parent project information -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.3.2</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    
    <!-- Project coordinates -->
    <groupId>com.wchamara</groupId>
    <artifactId>spring-6-rest-mvc</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    
    <!-- Project metadata -->
    <name>spring-6-rest-mvc</name>
    <description>spring-6-rest-mvc</description>
    <url/>
    
    <!-- Licensing information -->
    <licenses>
        <license/>
    </licenses>
    
    <!-- Developer information -->
    <developers>
        <developer/>
    </developers>
    
    <!-- Source control management information -->
    <scm>
        <connection/>
        <developerConnection/>
        <tag/>
        <url/>
    </scm>
    
    <!-- Project properties -->
    <properties>
        <java.version>21</java.version>
    </properties>
    
    <!-- Project dependencies -->
    <dependencies>
        <!-- Spring Boot Starter Web dependency -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- Spring Boot DevTools dependency -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        
        <!-- Lombok dependency -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        
        <!-- Spring Boot Starter Test dependency -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <!-- Build configuration -->
    <build>
        <plugins>
            <!-- Spring Boot Maven Plugin -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```
## 002 NewProjectSpring6RestMVC

## 003 Project Lombok Features
## 004 Project Lombok POJOs
## 005 Project Lombok Builders
## 006 Project Lombok Constructors
## 007 Project Lombok Logging
## 008 Delombok