# 21 - Spring Security Basic Auth

## 001 Introduction
## 002 Adding Spring Security Dependencies
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <scope>test</scope>
        </dependency>
```

The two dependencies you mentioned are crucial for adding security features and security testing capabilities to a Spring Boot application. Let's break down their roles and how they integrate into a Spring Boot project.

### 1. **`spring-boot-starter-security`**

#### Description:
This dependency brings in the core **Spring Security** features into your Spring Boot application. Spring Security is a powerful and highly customizable authentication and access-control framework. It provides comprehensive security services for Java applications, primarily web-based.

#### What it Includes:
- **Authentication**: Managing user identities, including login and logout.
- **Authorization**: Controlling access to resources based on roles, permissions, and policies.
- **CSRF Protection**: Cross-Site Request Forgery protection for web applications.
- **Session Management**: Control and secure session creation, expiration, and invalidation.
- **Security Filters**: A set of filters that handle security at various levels, such as login forms, authorization checks, and user details management.
- **Password Encoding**: Provides password hashing and encoding mechanisms (e.g., BCrypt).

#### Example Usage:
Once you add this dependency, Spring Security automatically applies some default security configurations. For example:

- **Basic HTTP Authentication** is automatically enabled.
- **CSRF Protection** is enabled for non-GET requests.
- **Form-based login** is provided at `/login`.

##### Example 1: Basic Authentication

By default, Spring Security applies **basic authentication** to all endpoints. Without any additional configuration, Spring Boot applications require a username and password to access any URL.

```java
// Main Application Class
@SpringBootApplication
public class SecurityExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityExampleApplication.class, args);
    }
}
```

Now, if you start your application and try to access any endpoint (e.g., `http://localhost:8080`), you will be prompted for a username and password.

By default, Spring Boot creates a default user with a generated password, which is printed in the console when the application starts:

```
Using generated security password: 3e7f6f9c-e8c8-4214-b187-45763b658d2b
```

##### Example 2: Custom Authentication and Authorization

You can also define custom authentication and authorization rules using `SecurityConfig`:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin").password("{noop}password").roles("ADMIN")
            .and()
            .withUser("user").password("{noop}password").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated()
            .and()
            .formLogin().permitAll()
            .and()
            .logout().permitAll();
    }
}
```

This configuration allows:

- Users with role `ADMIN` to access `/admin/**` URLs.
- Users with role `USER` to access `/user/**` URLs.
- Other users must authenticate before accessing any URL.

#### Key Components Provided:
- **AuthenticationManager**: Handles authentication.
- **WebSecurityConfigurerAdapter**: Allows configuration of security settings.
- **HttpSecurity**: Manages the security for web-based requests.
- **UserDetailsService**: Loads user-specific data.
- **PasswordEncoder**: Encodes and decodes passwords.

### 2. **`spring-security-test`**

#### Description:
This dependency is used for **testing** Spring Security features in a Spring Boot application. It allows you to write unit and integration tests that interact with the security context, authenticate users, and verify access control.

#### What it Includes:
- **Security Mocking**: Provides annotations and methods to mock authentication in tests, such as simulating a user login.
- **Test Utilities**: Includes utilities to help with security-related assertions, like checking roles, user identities, etc.
- **Pre-built Annotations**: Allows you to simulate different security contexts using annotations.

#### Example Usage:

##### Example 1: Testing Controller with Mock User

You can use the `@WithMockUser` annotation to simulate a user being authenticated during a test. Here’s an example where you test an endpoint that requires an authenticated user:

```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void shouldAllowAccessToUser() throws Exception {
        mockMvc.perform(get("/user"))
               .andExpect(status().isOk());
    }

    @Test
    public void shouldDenyAccessToUnauthenticatedUser() throws Exception {
        mockMvc.perform(get("/user"))
               .andExpect(status().isUnauthorized());
    }
}
```

Here’s how the example works:
- **`@WithMockUser`**: Simulates an authenticated user with the username `user` and role `USER`. This allows the test to pass through the authentication checks.
- **`MockMvc`**: A Spring testing utility that simulates HTTP requests to your controllers.

##### Example 2: Testing with Custom Security Context

If you want more control over the security context in your tests, you can use `SecurityContextTestExecutionListener`:

```java
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
public class CustomSecurityTest {

    @Test
    @WithSecurityContext
    public void testCustomUserDetails() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assertEquals("customUser", authentication.getName());
    }
}
```

In this case, `@WithSecurityContext` allows you to define custom authentication logic, such as setting specific user details or roles.

#### Key Components Provided:
- **`@WithMockUser`**: Mock an authenticated user in a test.
- **`@WithSecurityContext`**: Provides a custom security context in tests.
- **Security Mocking Utilities**: Various utilities to mock, test, and assert the security configuration in your application.

### Summary

- **`spring-boot-starter-security`**: Adds authentication, authorization, and other security features to your Spring Boot application. It helps protect endpoints and manage user roles, sessions, and password encoding.
- **`spring-security-test`**: Helps you write unit and integration tests for Spring Security-enabled applications. It provides mocking capabilities for simulating authenticated users and verifying that security rules are properly enforced.

These two dependencies enable comprehensive security management and testing in a Spring Boot application.
## 003 Calling Rest API with Postman and HTTP Basic
## 004 Customizing User Name and Password
## 005 Testing Spring Security with JUnit 5
## 006 Spring Security Config - Disable CSRF
## 007 Spring Security with Web Application Context
## 008 HTTP Basic with RestTemplate
## 009 Refactor of RestTemplate Builder Config
## 010 HTTP Basic with RestTemplate Mock Context