# 13 - CSV File Uploads

## 001 Introduction

![alt text](image.png)

## 002 Beer CSV Data

[csv file](../../spring-6-rest-mvc/src/main/resources/csvdata/beers.scv)

## 003 Beer CSV POJO

```java
package guru.springframework.spring6restmvc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jt, Spring Framework Guru.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeerCSVRecord {
    private Integer row;
    private Integer count;
    private String abv;
    private String ibu;
    private Integer id;
    private String beer;
    private String style;
    private Integer breweryId;
    private Float ounces;
    private String style2;
    private String count_y;
    private String city;
    private String state;
    private String label;

}
```

## 004 Mapping with OpenCSV

![alt text](image-1.png)


```xml
        <dependency>
            <groupId>com.opencsv</groupId>
            <artifactId>opencsv</artifactId>
            <version>5.9</version>
        </dependency>
```
```java
package com.wchamara.spring6restmvc.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeerCSVRecord {

    @CsvBindByName
    private Integer row;

    @CsvBindByName(column = "count.x")
    private Integer count;

    @CsvBindByName
    private String abv;

    @CsvBindByName
    private String ibu;

    @CsvBindByName
    private Integer id;

    @CsvBindByName
    private String beer;

    @CsvBindByName
    private String style;

    @CsvBindByName(column = "brewery_id")
    private Integer breweryId;

    @CsvBindByName
    private Float ounces;

    @CsvBindByName
    private String style2;

    @CsvBindByName(column = "count.y")
    private String count_y;

    @CsvBindByName
    private String city;

    @CsvBindByName
    private String state;

    @CsvBindByName
    private String label;

}
```

## 005 CSV Parse Service

```java
package com.wchamara.spring6restmvc.service;

import com.wchamara.spring6restmvc.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface BeerCsvService {
    List<BeerCSVRecord> convertCsv(File file);
}

```
```java
package com.wchamara.spring6restmvc.service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.wchamara.spring6restmvc.model.BeerCSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class BeerCsvServiceImpl implements BeerCsvService {
    @Override
    public List<BeerCSVRecord> convertCsv(File file) {
        try {
            return new CsvToBeanBuilder<BeerCSVRecord>(new FileReader(file))
                    .withType(BeerCSVRecord.class)
                    .build()
                    .parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}

```
```java
package com.wchamara.spring6restmvc.service;

import com.wchamara.spring6restmvc.model.BeerCSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class BeerCsvServiceImpTest {

    BeerCsvService beerCsvService = new BeerCsvServiceImpl();

    @Test
    void convertCsv() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csvdata/beers.scv");

        List<BeerCSVRecord> recs = beerCsvService.convertCsv(file);

        System.out.println(recs.size());

        assertThat(recs.size()).isGreaterThan(0);
    }
}

```
```java

```



## 006 Save CSV Data to Database



## 007 Hibernate Create and Update Timestamp



## 008 Fix Integration Tests
