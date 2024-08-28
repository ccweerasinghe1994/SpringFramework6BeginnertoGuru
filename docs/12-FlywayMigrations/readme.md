# 12 - Flyway Migrations

## 001 Introduction
![alt text](image.png)
## 002 Overview of Flyway
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

## 003 Flyway Dependencies
```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-mysql</artifactId>
</dependency>
```
## 004 Flyway Migration Script Configuration
```properties
spring.jpa.hibernate.ddl-auto=validate
```

```sql
drop table if exists beer;

drop table if exists customer;

create table beer
(
    beer_style       tinyint        not null check (beer_style between 0 and 9),
    price            decimal(38, 2) not null,
    quantity_on_hand integer,
    version          integer,
    created_date     datetime(6),
    updated_date     datetime(6),
    id               varchar(36)    not null,
    beer_name        varchar(50)    not null,
    upc              varchar(255)   not null,
    primary key (id)
) engine = InnoDB;

create table customer
(
    version      integer,
    created_date datetime(6),
    update_date  datetime(6),
    id           varchar(36) not null,
    name         varchar(255),
    primary key (id)
) engine = InnoDB;

```
![alt text](image-12.png)

## 005 Add Database Column
## 006 Flyway Advanced Spring Boot Configuration
## 007 Fixing Integration Tests using H2
