# 14 - Query Parameters with Spring MVC

## 001 Introduction
![alt text](image.png)
## 002 Overview of Query Parameters
**Query Parameters** are key-value pairs that appear in the URL after a question mark (`?`). They are used to pass data to a web application through the URL. In Spring MVC, query parameters are typically used in `GET` requests to filter or refine the results of a request.

### Example of Query Parameters in Spring MVC

#### 1. **Basic Example with a Single Query Parameter**
Suppose you want to create an endpoint that returns a list of users filtered by their status:

**URL:**
```
http://localhost:8080/users?status=active
```

**Controller:**
```java
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @GetMapping("/users")
    public List<User> getUsersByStatus(@RequestParam String status) {
        // Logic to retrieve users based on the status parameter
        return userService.getUsersByStatus(status);
    }
}
```
In this example:
- The `@RequestParam` annotation is used to bind the value of the query parameter `status` to the `status` method parameter.
- If `status=active` is passed in the URL, the method will filter and return users with an "active" status.

#### 2. **Multiple Query Parameters**

You can also use multiple query parameters to refine your search further.

**URL:**
```
http://localhost:8080/users?status=active&age=25
```

**Controller:**
```java
@GetMapping("/users")
public List<User> getUsersByStatusAndAge(@RequestParam String status, @RequestParam int age) {
    // Logic to retrieve users based on status and age
    return userService.getUsersByStatusAndAge(status, age);
}
```
In this case:
- Two query parameters `status` and `age` are used.
- The method will filter users who are "active" and 25 years old.

#### 3. **Optional Query Parameters**

You can also make query parameters optional by providing a default value or making the parameter nullable.

**URL:**
```
http://localhost:8080/users?status=active
```

**Controller:**
```java
@GetMapping("/users")
public List<User> getUsers(@RequestParam(required = false, defaultValue = "all") String status, 
                           @RequestParam(required = false) Integer age) {
    // Logic to retrieve users based on status and optionally age
    return userService.getUsers(status, age);
}
```
In this example:
- `status` has a default value of `"all"` if not provided.
- `age` is optional and can be `null`.

#### 4. **Mapping Query Parameters to an Object**

For more complex scenarios, you can map multiple query parameters to a custom object.

**URL:**
```
http://localhost:8080/users?status=active&age=25&city=NewYork
```

**Custom Object:**
```java
public class UserFilter {
    private String status;
    private Integer age;
    private String city;

    // Getters and Setters
}
```

**Controller:**
```java
@GetMapping("/users")
public List<User> getUsers(UserFilter filter) {
    // Logic to retrieve users based on the filter object
    return userService.getUsers(filter);
}
```
In this example:
- Spring will automatically bind the query parameters `status`, `age`, and `city` to the fields of the `UserFilter` object.

### Summary
Query parameters in Spring MVC allow you to pass data to your controller methods through the URL. You can handle single or multiple parameters, make them optional, or map them to a custom object for more complex scenarios.
## 003 List Beers Spring MVC Test
```java
    @Test
    void getBeerByName() throws Exception {
//        let's check the size of the list
        mockMvc.perform(get(BeerController.BEER_PATH).queryParam("beerName", "IPA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
//                .andExpect(result -> {
//                    String content = result.getResponse().getContentAsString();
//                    List<BeerDTO> beerDTOS = objectMapper.readValue(content, List.class);
//                    assertThat(beerDTOS.size()).isEqualTo(2);
//                });

    }
```
## 004 Capture Query Parameters with Spring MVC

```java
   @GetMapping(BEER_PATH)
    public List<BeerDTO> listAllBeers(@RequestParam(required = false) String beerName) {
        log.debug("listAllBeers() called in BeerController");
        return beerService.listAllBeers();
    }
```

```java
    @Test
    void getBeerByName() throws Exception {
//        let's check the size of the list
        mockMvc.perform(get(BeerController.BEER_PATH).queryParam("beerName", "IPA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(211));
//                .andExpect(result -> {
//                    String content = result.getResponse().getContentAsString();
//                    List<BeerDTO> beerDTOS = objectMapper.readValue(content, List.class);
//                    assertThat(beerDTOS.size()).isEqualTo(2);
//                });

    }

```

## 005 Update Service to Accept Query Parameter
## 006 Refactor Service with Conditional Logic
## 007 Find By Name with Spring Data JPA
## 008 Complete Implementation
## 009 Complete Search Functionality