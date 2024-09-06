# 21 - Spring Security Basic Auth

- [21 - Spring Security Basic Auth](#21---spring-security-basic-auth)
  - [001 Introduction](#001-introduction)
  - [002 Adding Spring Security Dependencies](#002-adding-spring-security-dependencies)
    - [1. **`spring-boot-starter-security`**](#1-spring-boot-starter-security)
      - [Description:](#description)
      - [What it Includes:](#what-it-includes)
      - [Example Usage:](#example-usage)
        - [Example 1: Basic Authentication](#example-1-basic-authentication)
        - [Example 2: Custom Authentication and Authorization](#example-2-custom-authentication-and-authorization)
      - [Key Components Provided:](#key-components-provided)
    - [2. **`spring-security-test`**](#2-spring-security-test)
      - [Description:](#description-1)
      - [What it Includes:](#what-it-includes-1)
      - [Example Usage:](#example-usage-1)
        - [Example 1: Testing Controller with Mock User](#example-1-testing-controller-with-mock-user)
        - [Example 2: Testing with Custom Security Context](#example-2-testing-with-custom-security-context)
      - [Key Components Provided:](#key-components-provided-1)
    - [Summary](#summary)
  - [003 Calling Rest API with Postman and HTTP Basic](#003-calling-rest-api-with-postman-and-http-basic)
  - [004 Customizing User Name and Password](#004-customizing-user-name-and-password)
    - [What Do These Properties Do?](#what-do-these-properties-do)
    - [Deep Dive Into Behavior](#deep-dive-into-behavior)
      - [1. **Default Authentication**](#1-default-authentication)
      - [2. **HTTP Basic Authentication**](#2-http-basic-authentication)
      - [3. **Securing All Endpoints**](#3-securing-all-endpoints)
      - [Example Flow:](#example-flow)
      - [4. **Console Output Without Configuration**](#4-console-output-without-configuration)
    - [Customizing Security](#customizing-security)
      - [1. **Multiple Users with Roles**](#1-multiple-users-with-roles)
      - [2. **Password Encoding**](#2-password-encoding)
      - [3. **Custom Authentication Providers**](#3-custom-authentication-providers)
    - [Summary](#summary-1)
  - [005 Testing Spring Security with JUnit 5](#005-testing-spring-security-with-junit-5)
    - [Testing Spring Security with JUnit 5](#testing-spring-security-with-junit-5)
    - [Key Concepts in Testing Spring Security](#key-concepts-in-testing-spring-security)
    - [Setup](#setup)
      - [Maven Dependencies:](#maven-dependencies)
    - [Example Application](#example-application)
    - [Security Configuration](#security-configuration)
    - [Testing with JUnit 5](#testing-with-junit-5)
      - [1. **Basic Test Setup**](#1-basic-test-setup)
      - [Explanation of Tests:](#explanation-of-tests)
      - [2. **Testing with `@WithUserDetails`**](#2-testing-with-withuserdetails)
      - [3. **Testing Authentication Failures**](#3-testing-authentication-failures)
    - [Advanced Testing](#advanced-testing)
      - [1. **Testing with Custom Security Context**](#1-testing-with-custom-security-context)
    - [Summary](#summary-2)
  - [006 Spring Security Config - Disable CSRF](#006-spring-security-config---disable-csrf)
    - [Key Components in the Code:](#key-components-in-the-code)
    - [Detailed Explanation](#detailed-explanation)
      - [1. **CSRF Protection**](#1-csrf-protection)
        - [Example Scenario:](#example-scenario)
      - [2. **HttpSecurity in Detail**](#2-httpsecurity-in-detail)
      - [3. **Spring Boot Default Security**](#3-spring-boot-default-security)
    - [Example Use Case: REST API with CSRF Disabled for API Endpoints](#example-use-case-rest-api-with-csrf-disabled-for-api-endpoints)
    - [Summary](#summary-3)
  - [007 Spring Security with Web Application Context](#007-spring-security-with-web-application-context)
    - [Key Components:](#key-components)
    - [Code Breakdown and Explanation:](#code-breakdown-and-explanation)
      - [1. **Authorization Configuration with `authorizeHttpRequests()`**:](#1-authorization-configuration-with-authorizehttprequests)
        - [Example 1:](#example-1)
        - [Example 2: Custom Authorization](#example-2-custom-authorization)
      - [2. **HTTP Basic Authentication Configuration**:](#2-http-basic-authentication-configuration)
        - [Example:](#example)
      - [3. **CSRF (Cross-Site Request Forgery) Protection**:](#3-csrf-cross-site-request-forgery-protection)
        - [Example:](#example-1)
      - [4. **Returning the SecurityFilterChain**:](#4-returning-the-securityfilterchain)
    - [Full Request Flow Example:](#full-request-flow-example)
    - [Summary:](#summary-4)
  - [008 HTTP Basic with RestTemplate](#008-http-basic-with-resttemplate)
  - [009 Refactor of RestTemplate Builder Config](#009-refactor-of-resttemplate-builder-config)
  - [010 HTTP Basic with RestTemplate Mock Context](#010-http-basic-with-resttemplate-mock-context)


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

![alt text](image.png)

## 004 Customizing User Name and Password
In a Spring Boot application, the properties:

```properties
spring.security.user.name=user1
spring.security.user.password=password
```

are part of Spring Security's **default security configuration**. When these properties are defined, Spring Security uses them to configure a simple, in-memory user with a specific username and password. Let’s explore this deeply to understand their purpose, behavior, and usage.

### What Do These Properties Do?

1. **`spring.security.user.name=user1`**:
   - This property sets the **username** for a default user that Spring Security will use for authentication.
   - The default value for this property is `user`, so if not explicitly defined, Spring Boot uses `user` as the username.

2. **`spring.security.user.password=password`**:
   - This property sets the **password** for the default user.
   - By default, if this property is not set, Spring Boot generates a random password at startup, which is printed in the console log.

When these two properties are defined, they create a simple, in-memory user with the provided username and password. This user is used for **basic authentication** in the application until you override or customize the security configuration.

### Deep Dive Into Behavior

#### 1. **Default Authentication**

When Spring Boot starts with `spring-boot-starter-security`, it automatically sets up basic HTTP authentication. By default, it will secure all endpoints in the application, requiring a user to authenticate using the credentials provided in the properties.

Example configuration:

```properties
spring.security.user.name=user1
spring.security.user.password=password
```

This configuration means that when any request is made to your Spring Boot application, such as accessing `http://localhost:8080/`, the application will prompt for a username and password.

#### 2. **HTTP Basic Authentication**

Once the application starts, accessing any endpoint will require a user to log in. In a browser, this results in a prompt:

```plaintext
Username: user1
Password: password
```

After providing the correct username and password, the request will proceed. If the authentication fails (i.e., the username and password don't match), the request will be rejected with a `401 Unauthorized` error.

#### 3. **Securing All Endpoints**

By default, Spring Security secures all endpoints in a Spring Boot application. If you don’t configure security rules, Spring Security will apply these default rules:

- **All endpoints** will require authentication.
- The user specified by `spring.security.user.name` and `spring.security.user.password` will be used for login.
- **No roles or authorities** are assigned unless you explicitly configure them.

For instance, if you create a simple REST controller:

```java
@RestController
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "Welcome to the home page!";
    }
}
```

When you try to access `http://localhost:8080/`, you will be prompted for authentication using the credentials `user1` and `password`.

#### Example Flow:

- User attempts to access the `/` endpoint.
- Spring Security intercepts the request and checks if the user is authenticated.
- If not authenticated, Spring Security presents an authentication challenge (e.g., browser login prompt or HTTP 401 error).
- The user enters `user1` and `password`.
- Spring Security validates the credentials. If correct, access is granted to the requested resource.

#### 4. **Console Output Without Configuration**

If you don’t provide these properties, Spring Boot still configures security, but with a randomly generated password. You will see output like this in the console at startup:

```plaintext
Using generated security password: 5bc4b2f2-44a6-4d65-8898-43c51e5eecf3
```

In this case, the default username is `user`, and the password is generated each time the application starts.

### Customizing Security

The `spring.security.user.name` and `spring.security.user.password` properties provide a quick way to secure an application, especially for development or testing. However, for production, you often need more robust security configurations.

Here are a few customizations you might perform:

#### 1. **Multiple Users with Roles**

You can configure multiple users with different roles by overriding the default security configuration. For instance:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin").password("{noop}adminpassword").roles("ADMIN")
            .and()
            .withUser("user1").password("{noop}password").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/").permitAll()
            .and()
            .formLogin();
    }
}
```

In this example:
- **Two users** are configured: `admin` and `user1`.
- The `/admin` endpoint is restricted to users with the `ADMIN` role.
- The `/` endpoint is accessible without authentication.

#### 2. **Password Encoding**

In the example above, the `{noop}` prefix in the password indicates that **no encoding** is applied. In production, you should use an encoded password (e.g., BCrypt).

For example:

```java
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("admin").password(new BCryptPasswordEncoder().encode("adminpassword")).roles("ADMIN")
        .and()
        .withUser("user1").password(new BCryptPasswordEncoder().encode("password")).roles("USER");
}
```

Now, passwords are securely hashed using BCrypt.

#### 3. **Custom Authentication Providers**

Instead of using in-memory authentication, you can configure Spring Security to authenticate against a **database**, **LDAP**, or other external services.

### Summary

- **`spring.security.user.name` and `spring.security.user.password`** are simple properties to create a default, in-memory user in Spring Boot for basic authentication.
- This setup is mainly used for development and testing purposes, providing a quick way to secure the application.
- All endpoints are secured by default, and the user must authenticate using the provided username and password.
- For production systems, you can expand on this basic setup with custom configurations, such as adding multiple users, roles, password encoding, and integrating with external authentication systems (e.g., databases, LDAP).

![alt text](image-1.png)

## 005 Testing Spring Security with JUnit 5
```java
    @Test
    void getBeerByIdWillReturnNotFoundException() throws Exception {

        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.empty());


        mockMvc.perform(
                get(BeerController.BEER_PATH_ID, UUID.randomUUID())
                        .with(httpBasic("user1", "password"))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

```

### Testing Spring Security with JUnit 5

Spring Security provides the ability to secure applications with various authentication and authorization mechanisms. When testing Spring Security configurations, JUnit 5 (the latest version of the JUnit testing framework) combined with Spring Security’s **`spring-security-test`** module, allows you to write meaningful unit and integration tests.

### Key Concepts in Testing Spring Security

- **Mocking Authentication**: Using `@WithMockUser` or `@WithUserDetails` to simulate authentication in tests.
- **MockMvc**: Testing the web layer (controllers) with mock HTTP requests and simulating Spring Security context.
- **Security Context Management**: Testing the state of the security context.

Let's go into detail on how to perform these tests, starting with basic security test configuration and moving to advanced scenarios.

### Setup

First, you need to add the necessary dependencies to your `pom.xml` or `build.gradle`:

#### Maven Dependencies:
```xml
<dependencies>
    <!-- Spring Boot Starter Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

    <!-- Spring Boot Starter Test for Testing -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>

    <!-- Spring Security Test for Mocking Security Context -->
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

This setup includes:
- `spring-boot-starter-security`: For Spring Security features.
- `spring-boot-starter-test`: For general testing, including JUnit and MockMvc.
- `spring-security-test`: For testing Spring Security configurations and contexts.

### Example Application

Let’s say you have a Spring Boot application with a simple controller:

```java
@RestController
public class HelloController {

    @GetMapping("/admin")
    public String admin() {
        return "Hello Admin!";
    }

    @GetMapping("/user")
    public String user() {
        return "Hello User!";
    }

    @GetMapping("/public")
    public String publicAccess() {
        return "Hello Public!";
    }
}
```

In this example:
- `/admin` is restricted to users with an `ADMIN` role.
- `/user` is restricted to users with a `USER` role.
- `/public` is accessible by everyone.

### Security Configuration

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin").password("{noop}adminpass").roles("ADMIN")
            .and()
            .withUser("user").password("{noop}userpass").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/public").permitAll()
            .and()
            .formLogin().permitAll()
            .and()
            .logout().permitAll();
    }
}
```

### Testing with JUnit 5

#### 1. **Basic Test Setup**

To test Spring Security, you can leverage Spring Boot's testing features like `@SpringBootTest` and `MockMvc`.

```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityTests {

    @Autowired
    private MockMvc mockMvc;

    // Test access to public endpoint
    @Test
    public void accessPublicWithoutAuth() throws Exception {
        mockMvc.perform(get("/public"))
               .andExpect(status().isOk())
               .andExpect(content().string("Hello Public!"));
    }

    // Test access to user endpoint with a mock user
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void accessUserWithUserRole() throws Exception {
        mockMvc.perform(get("/user"))
               .andExpect(status().isOk())
               .andExpect(content().string("Hello User!"));
    }

    // Test access to admin endpoint with a mock admin user
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void accessAdminWithAdminRole() throws Exception {
        mockMvc.perform(get("/admin"))
               .andExpect(status().isOk())
               .andExpect(content().string("Hello Admin!"));
    }

    // Test unauthorized access to admin endpoint with a user role
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void denyAccessToAdminWithUserRole() throws Exception {
        mockMvc.perform(get("/admin"))
               .andExpect(status().isForbidden());
    }
}
```

#### Explanation of Tests:
1. **`accessPublicWithoutAuth()`**: This test checks that the `/public` endpoint is accessible without authentication.
2. **`accessUserWithUserRole()`**: This test uses `@WithMockUser` to simulate a user with the role `USER`, ensuring they can access the `/user` endpoint.
3. **`accessAdminWithAdminRole()`**: This test uses `@WithMockUser` to simulate a user with the role `ADMIN`, ensuring they can access the `/admin` endpoint.
4. **`denyAccessToAdminWithUserRole()`**: This test ensures that a user with the `USER` role is denied access to the `/admin` endpoint.

#### 2. **Testing with `@WithUserDetails`**

If you want to test authentication with specific user details defined in a custom `UserDetailsService`, you can use the `@WithUserDetails` annotation.

Here’s how it works:

```java
@Test
@WithUserDetails("admin") // assumes "admin" is loaded by a custom UserDetailsService
public void accessAdminWithCustomUserDetails() throws Exception {
    mockMvc.perform(get("/admin"))
           .andExpect(status().isOk())
           .andExpect(content().string("Hello Admin!"));
}
```

In this case, Spring will load the user `admin` from the `UserDetailsService` defined in the `SecurityConfig`.

#### 3. **Testing Authentication Failures**

You can also test how your application responds to failed authentication attempts or lack of authentication.

```java
@Test
public void denyAccessToUserWithoutAuthentication() throws Exception {
    mockMvc.perform(get("/user"))
           .andExpect(status().isUnauthorized()); // Expect 401 Unauthorized
}
```

This test ensures that a request to the `/user` endpoint without any authentication results in a `401 Unauthorized` response.

### Advanced Testing

#### 1. **Testing with Custom Security Context**

Sometimes, you may need to define a custom security context for more complex tests. You can achieve this by configuring a `SecurityContext` directly in your tests:

```java
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;

@TestExecutionListeners(listeners = {WithSecurityContextTestExecutionListener.class}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public class CustomSecurityContextTest {

    @Test
    public void customSecurityContext() {
        SecurityContext context = SecurityContextHolder.getContext();
        assertNotNull(context.getAuthentication());
        assertEquals("user", context.getAuthentication().getName());
    }
}
```

In this test, you have complete control over the security context, which allows you to test authentication behavior in fine detail.

### Summary

- **`@WithMockUser`**: Simulates a user being authenticated in a test with a specific role.
- **`@WithUserDetails`**: Allows you to load user details from a custom `UserDetailsService`.
- **MockMvc**: Facilitates testing of web controllers and simulates HTTP requests while interacting with the security context.
- **Security Context**: Can be directly manipulated in advanced testing scenarios.

By using these tools, you can ensure your Spring Security configurations work as expected under various authentication and authorization scenarios, ensuring that your application remains secure while responding correctly to user roles and permissions.
## 006 Spring Security Config - Disable CSRF

```java
package com.wchamara.spring6restmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(
                httpSecurityCsrfConfigurer -> {
                    httpSecurityCsrfConfigurer.ignoringRequestMatchers("/api/**");
                }
        );

        return http.build();
    }
}

```

```java
package com.wchamara.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wchamara.spring6restmvc.config.SpringSecurityConfig;
import com.wchamara.spring6restmvc.model.BeerDTO;
import com.wchamara.spring6restmvc.model.BeerStyle;
import com.wchamara.spring6restmvc.service.BeerService;
import com.wchamara.spring6restmvc.service.BeerServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
@WebMvcTest(BeerController.class)
@Import(SpringSecurityConfig.class)
class BeerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    BeerService beerService;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<BeerDTO> beerArgumentCaptor;

    @Autowired
    ObjectMapper objectMapper;

    BeerServiceImpl beerServiceImpl = new BeerServiceImpl();

    @Test
    void getBeerByIdReturnsBeer() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.listAllBeers(null, false, BeerStyle.ALE, 1, 25).getContent().get(0);
        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.of(beerDTO));


        mockMvc.perform(
                        get(BeerController.BEER_PATH_ID, beerDTO.getId())
                                .with(httpBasic("user1", "password"))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(beerDTO.getId().toString()))
                .andExpect(jsonPath("$.beerName").value(beerDTO.getBeerName()))
                .andExpect(jsonPath("$.beerStyle").value(beerDTO.getBeerStyle().toString()))
                .andExpect(jsonPath("$.upc").value(beerDTO.getUpc()))
                .andExpect(jsonPath("$.quantityOnHand").value(beerDTO.getQuantityOnHand()))
                .andExpect(jsonPath("$.price").value(beerDTO.getPrice().toString()))
                .andExpect(jsonPath("$.createdDate").exists())
                .andExpect(jsonPath("$.updatedDate").exists());
        ;
    }


    @Test
    void getBeerByIdWillReturnNotFoundException() throws Exception {

        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.empty());


        mockMvc.perform(
                        get(BeerController.BEER_PATH_ID, UUID.randomUUID())
                                .with(httpBasic("user1", "password"))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }


    @Test
    void listAllBeersReturnsListOfBeers() throws Exception {
        given(beerService.listAllBeers(any(), any(), any(), any(), any())).willReturn(beerServiceImpl.listAllBeers(null, false, BeerStyle.ALE, 1, 25));

        mockMvc.perform(
                        get(
                                BeerController.BEER_PATH)
                                .with(httpBasic("user1", "password"))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id").exists())
                .andExpect(jsonPath("$.content[0].beerName").exists())
                .andExpect(jsonPath("$.content[0].beerStyle").exists())
                .andExpect(jsonPath("$.content[0].upc").exists())
                .andExpect(jsonPath("$.content[0].quantityOnHand").exists())
                .andExpect(jsonPath("$.content[0].price").exists())
                .andExpect(jsonPath("$.content[0].createdDate").exists())
                .andExpect(jsonPath("$.content[0].updatedDate").exists());
    }

    @Test
    void saveNewBeerReturnsCreated() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.listAllBeers(null, false, BeerStyle.ALE, 1, 25).getContent().get(0);
        beerDTO.setId(null);
        beerDTO.setVersion(null);

        given(beerService.saveNewBeer(any())).willReturn(beerServiceImpl.listAllBeers(null, false, BeerStyle.ALE, 1, 25).getContent().get(1));

        mockMvc.perform(
                        post(BeerController.BEER_PATH)
                                .with(httpBasic("user1", "password"))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(beerDTO))
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

    }

    @Test
    void testCreateNewBeerNullBeerName() throws Exception {
        BeerDTO beerDTO = BeerDTO.builder().build();


        given(beerService.saveNewBeer(any())).willReturn(beerServiceImpl.listAllBeers(null, false, BeerStyle.ALE, 1, 25).getContent().get(1));

        ResultActions resultActions = mockMvc.perform(
                        post(BeerController.BEER_PATH)
                                .with(httpBasic("user1", "password"))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(beerDTO))
                )
                .andExpect(status().isBadRequest());

        System.out.println(resultActions.andReturn().getResponse().getContentAsString());

    }

    @Test
    void updateBeerReturnsNoContent() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.listAllBeers(null, false, BeerStyle.ALE, 1, 25).getContent().get(0);
        given(beerService.updateBeer(any(), any())).willReturn(Optional.of(beerDTO));


        mockMvc.perform(
                        put(BeerController.BEER_PATH_ID, beerDTO.getId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(beerDTO))
                )
                .andExpect(status().isNoContent());
        verify(beerService).updateBeer(any(UUID.class), any(BeerDTO.class));
    }

    @Test
    void deleteBeerReturnsNoContent() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.listAllBeers(null, false, BeerStyle.ALE, 1, 25).getContent().get(0);
        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.of(beerDTO));

        mockMvc.perform(delete(BeerController.BEER_PATH_ID, beerDTO.getId()))
                .andExpect(status().isNoContent());

        verify(beerService).deleteBeer(uuidArgumentCaptor.capture());

        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(beerDTO.getId());
    }

    @Test
    void patchBeerReturnsNoContent() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.listAllBeers(null, false, BeerStyle.ALE, 1, 25).getContent().get(0);

        Map<String, Object> beerMap = new HashMap<>();

        beerMap.put("beerName", "New Beer Name");

        mockMvc.perform(
                        patch(BeerController.BEER_PATH_ID, beerDTO.getId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(beerMap))
                )
                .andExpect(status().isNotFound());

//        verify(beerService).patchBeer(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());
//        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(beerDTO.getId());
//        assertThat(beerArgumentCaptor.getValue().getBeerName()).isEqualTo("New Beer Name");

    }


}
```


The given code snippet represents a **Spring Security configuration** class using the new approach introduced in Spring Security 5.7 and 6, where `SecurityFilterChain` is used instead of the now-deprecated `WebSecurityConfigurerAdapter`. Let’s break it down step by step, understand how it works, and explore it with examples.

### Key Components in the Code:

1. **`@Configuration` Annotation**:
   - This marks the class as a configuration class, meaning it will be processed by Spring’s configuration mechanism to define beans and configuration settings. Spring will automatically scan for these configuration classes during startup.
   
2. **`SecurityFilterChain` Bean**:
   - The `SecurityFilterChain` defines the security filters that will apply to incoming HTTP requests. This is a central component in Spring Security's filtering mechanism.
   - In this example, a custom `SecurityFilterChain` bean is defined using the `securityFilterChain(HttpSecurity http)` method.
   
3. **`HttpSecurity`**:
   - The `HttpSecurity` object is provided by Spring Security and is used to configure various aspects of HTTP security, such as CSRF protection, authorization, authentication, and more.

4. **CSRF Configuration**:
   - The `http.csrf()` configuration part defines how **CSRF (Cross-Site Request Forgery)** protection is handled in the application.
   - **`csrf().ignoringRequestMatchers("/api/**")`**: This disables CSRF protection for URLs that start with `/api/`. This is common for RESTful APIs because traditional CSRF attacks are less of a concern in stateless, API-based applications.

### Detailed Explanation

#### 1. **CSRF Protection**

CSRF is an attack where an attacker tricks a user into performing actions they did not intend to perform, typically by forging requests to the server where the user is authenticated. Spring Security enables CSRF protection by default to protect against these kinds of attacks in form-based authentication.

However, in **stateless applications** or **APIs**, CSRF protection is often unnecessary because APIs typically use tokens (like JWT) for authentication, not cookies. This is why many developers choose to **disable** CSRF protection for their API routes.

In the example, CSRF protection is disabled for all requests matching `/api/**` by using:

```java
httpSecurityCsrfConfigurer.ignoringRequestMatchers("/api/**");
```

This means that:
- Any URL that starts with `/api/` will not have CSRF protection.
- This is suitable for REST APIs where statelessness is a core principle, and there are no sessions or cookies involved.

##### Example Scenario:
Let’s say you are building a RESTful API with endpoints like:
- `POST /api/users` – to create a new user.
- `POST /api/orders` – to create an order.

Since CSRF protection is unnecessary for these API endpoints (due to statelessness and token-based authentication), you can disable CSRF for all `/api/**` routes. The non-API endpoints, like a web application with form submissions, will still have CSRF protection by default.

#### 2. **HttpSecurity in Detail**

The `HttpSecurity` object is used to configure security for the HTTP layer of the application. In the example, only CSRF configuration is included, but Spring Security allows you to configure other security mechanisms like authentication, authorization, and form login.

You could expand the configuration to specify more security settings. For example:

```java
http
    .authorizeRequests()
        .antMatchers("/admin/**").hasRole("ADMIN")
        .antMatchers("/api/**").permitAll()
        .anyRequest().authenticated()
    .and()
    .formLogin()
        .loginPage("/login").permitAll()
    .and()
    .logout().permitAll();
```

In this expanded configuration:
- `/admin/**` URLs are restricted to users with the `ADMIN` role.
- `/api/**` URLs are open to all users without authentication.
- Other URLs require the user to be authenticated.
- A custom login page is configured at `/login`.
- Logout is permitted for everyone.

#### 3. **Spring Boot Default Security**

When using Spring Boot and Spring Security, the framework applies **default security configurations** if you don’t provide your own. These defaults include enabling CSRF protection and requiring authentication for all requests. By defining a `SecurityFilterChain` bean, you are overriding Spring Boot’s default security configuration and providing custom rules.

### Example Use Case: REST API with CSRF Disabled for API Endpoints

Consider a Spring Boot application that has both a web front-end and a RESTful API. The front-end uses traditional form-based authentication, which requires CSRF protection. However, the API is consumed by mobile or JavaScript clients using token-based authentication (e.g., JWT), which does not require CSRF protection.

Here’s how you might configure security for such a use case:

```java
@Configuration
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"));

        // Authorize requests
        http
            .authorizeRequests()
            .antMatchers("/api/public/**").permitAll()  // Public API
            .antMatchers("/api/admin/**").hasRole("ADMIN")  // Admin API
            .antMatchers("/admin/**").hasRole("ADMIN")  // Admin web pages
            .anyRequest().authenticated()  // All other requests need authentication
            .and()
            .httpBasic();  // Use HTTP Basic authentication for simplicity

        return http.build();
    }
}
```

- **CSRF Protection**: Disabled for API routes (`/api/**`).
- **API Routes**: 
  - `/api/public/**` is accessible to everyone.
  - `/api/admin/**` requires an authenticated user with the `ADMIN` role.
- **Web Routes**: 
  - `/admin/**` requires the `ADMIN` role.
  - All other web pages require authentication.
- **Authentication**: HTTP Basic authentication is used in this example for simplicity, but this can be replaced with more sophisticated mechanisms like JWT or OAuth2.

### Summary

The code provided shows how to configure Spring Security using the `SecurityFilterChain` and `HttpSecurity` objects in Spring Security 6 (and 5.7+). In particular, CSRF protection is disabled for API endpoints, which is a common pattern in stateless applications or token-based authentication systems. 

This configuration ensures:
- **CSRF Protection**: Disabled for APIs where it’s not needed.
- **Security Filter Chain**: Custom filters and rules can be added to protect certain URLs, enforce authentication, and define user roles.
- **Modern Security Practices**: With the deprecation of `WebSecurityConfigurerAdapter`, the use of `SecurityFilterChain` provides more flexibility and modular security configurations.

By customizing the security configuration, you can secure various parts of the application differently depending on their needs (e.g., form-based login for the web front-end, token-based for APIs).

## 007 Spring Security with Web Application Context

```java
package com.wchamara.spring6restmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Use lambda-style configuration for authorizeHttpRequests
        http.authorizeHttpRequests(auth -> {
                    auth.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())  // Default basic HTTP authentication
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"));  // Disable CSRF for API routes

        return http.build();
    }
}

```

The provided code configures Spring Security in a Spring Boot application using the `SecurityFilterChain` approach, which is the preferred way in Spring Security 5.7+ and Spring Security 6. This code configures HTTP security for the application, setting up authentication rules, enabling HTTP basic authentication, and handling CSRF (Cross-Site Request Forgery) protection for specific routes.

Let’s break this down and explain each section in detail with examples.

### Key Components:

1. **`SecurityFilterChain` Bean**:
   - `SecurityFilterChain` defines the security rules that apply to incoming HTTP requests. By configuring a `SecurityFilterChain` bean, you're specifying how requests should be authorized, authenticated, and which protections should be applied (like CSRF).
   - The `HttpSecurity` object allows you to configure the web-based security at a high level.

2. **Lambda-style Configuration**:
   - Instead of the traditional method chaining (which has been deprecated), Spring Security 6 prefers lambda-based configurations, which improves readability and clarity by grouping related security configurations together.

### Code Breakdown and Explanation:

#### 1. **Authorization Configuration with `authorizeHttpRequests()`**:

```java
http.authorizeHttpRequests(auth -> {
    auth.anyRequest().authenticated();
});
```

- **`authorizeHttpRequests()`**: This method is used to configure authorization for HTTP requests. It determines how different endpoints or URL patterns should be protected.
  
- **`anyRequest().authenticated()`**: This is the simplest form of authorization. It ensures that **all requests** made to any endpoint of the application require the user to be authenticated.

##### Example 1:
Suppose you have endpoints like:

```java
@RestController
public class MyController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint";
    }

    @GetMapping("/private")
    public String privateEndpoint() {
        return "This is a private endpoint, login required";
    }
}
```

With the `anyRequest().authenticated()` rule, **both `/public` and `/private`** will require authentication. Even though the `/public` endpoint is supposed to be public, this rule forces the user to authenticate before accessing any endpoint.

##### Example 2: Custom Authorization
If you wanted to make `/public` accessible without authentication, you could adjust the configuration like this:

```java
http.authorizeHttpRequests(auth -> {
    auth.antMatchers("/public").permitAll();  // Allow public access
    auth.anyRequest().authenticated();  // All other requests require authentication
});
```

Now, the `/public` endpoint will be accessible without logging in, while `/private` and other endpoints still require authentication.

#### 2. **HTTP Basic Authentication Configuration**:

```java
.httpBasic(Customizer.withDefaults())
```

- **HTTP Basic Authentication** is a simple and widely used authentication mechanism. It requires the user to provide a username and password in the request’s header, and Spring Security will authenticate the user using this information.
  
- **`Customizer.withDefaults()`**: This applies the default configuration for HTTP Basic authentication. By using the default customizer, no additional configuration is needed, and Spring Security will automatically provide basic authentication dialogs for browsers and handle authentication based on the username and password provided.

##### Example:
When a user tries to access a protected endpoint like `/private`, they will be prompted with a browser dialog box asking for a username and password. If the correct credentials are provided, access is granted.

Example of HTTP Basic Authentication request:

```http
GET /private HTTP/1.1
Host: localhost:8080
Authorization: Basic dXNlcjpwYXNzd29yZA==  (Base64-encoded "user:password")
```

If credentials are incorrect or not provided, the response will be:

```http
HTTP/1.1 401 Unauthorized
WWW-Authenticate: Basic realm="Realm"
```

#### 3. **CSRF (Cross-Site Request Forgery) Protection**:

```java
.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
```

- **CSRF** is an attack that tricks users into performing actions they didn’t intend. Spring Security enables CSRF protection by default for non-GET requests (like POST, PUT, DELETE) to ensure that malicious actors cannot forge requests on behalf of authenticated users.
  
- **Disabling CSRF for APIs**: In this case, CSRF protection is **disabled** for any routes that start with `/api/`. This is a common practice for stateless APIs (especially REST APIs), where sessions are not used, and token-based authentication (e.g., JWT) is typically employed.

##### Example:
Assume your application has API endpoints like:

```java
@RestController
@RequestMapping("/api")
public class ApiController {

    @PostMapping("/data")
    public String postData() {
        return "Data posted!";
    }
}
```

If CSRF protection is not disabled for the `/api/**` endpoints, every time you try to POST data to `/api/data`, Spring Security would expect a CSRF token to be included in the request body or header. However, since APIs are often stateless (not session-based), it’s common to disable CSRF for API routes, as shown in the configuration.

If CSRF were enabled and you sent a POST request without a valid CSRF token, you would receive:

```http
HTTP/1.1 403 Forbidden
```

But since it is disabled for `/api/**`, the request will proceed as expected.

#### 4. **Returning the SecurityFilterChain**:

```java
return http.build();
```

- After configuring all the security rules (authorization, authentication, CSRF), calling `http.build()` creates the `SecurityFilterChain` that Spring Security will apply to incoming HTTP requests.
- This chain contains the series of filters (like CSRF filters, authentication filters, etc.) that will intercept and handle each HTTP request before it reaches the controller.

### Full Request Flow Example:

Here’s how the configuration behaves when you make requests to your application:

1. **Accessing `/public` (if configured as public)**:
   - Since it's marked as public (e.g., using `permitAll()`), no authentication is required, and the request is successful.

   ```http
   GET /public HTTP/1.1
   Response: 200 OK
   Body: "This is a public endpoint"
   ```

2. **Accessing `/private` (or any other protected endpoint)**:
   - A user tries to access a protected resource like `/private`.
   - The request is intercepted by Spring Security, and since the request is authenticated, the user is prompted for credentials via HTTP Basic Authentication.
   - If the correct username and password are provided, the request proceeds, and access is granted.
   
   ```http
   GET /private HTTP/1.1
   Authorization: Basic dXNlcjpwYXNzd29yZA== (user:password)
   Response: 200 OK
   Body: "This is a private endpoint"
   ```

3. **Posting to `/api/data`**:
   - CSRF protection is disabled for the `/api/**` routes, so POST requests can be made without providing a CSRF token.
   - The data is successfully posted to the server.

   ```http
   POST /api/data HTTP/1.1
   Body: { "data": "sample" }
   Response: 200 OK
   Body: "Data posted!"
   ```

### Summary:

- **Lambda-based Configuration**: In Spring Security 6, security rules are configured using a lambda-style configuration, which is clearer and more modular.
- **Authorization**: The `authorizeHttpRequests()` method allows you to specify which endpoints require authentication and which are publicly accessible.
- **Basic Authentication**: This configuration uses HTTP Basic authentication, which requires the client to provide credentials in the HTTP request header.
- **CSRF**: Cross-Site Request Forgery protection is disabled for API routes (`/api/**`), which is common for stateless APIs.

This configuration provides a simple, secure structure for applications that have both public and protected routes, along with API endpoints that don't require CSRF protection.

```java
package com.wchamara.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wchamara.spring6restmvc.entities.Beer;
import com.wchamara.spring6restmvc.model.BeerDTO;
import com.wchamara.spring6restmvc.model.BeerStyle;
import com.wchamara.spring6restmvc.repositories.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerController beerController;
    @Autowired
    ObjectMapper objectMapper;
    MockMvc mockMvc;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    private BeerRepository beerRepository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }


    @Test
    void testListOfYoursWithPagination() throws Exception {
//        let's check the size of the list
        mockMvc.perform(
                        get(BeerController.BEER_PATH)
                                .with(httpBasic(BeerControllerTest.USER_NAME, BeerControllerTest.PASSWORD))
                                .queryParam("beerName", "IPA")
                                .queryParam("beerStyle", BeerStyle.IPA.name())
                                .queryParam("showInventory", "TRUE")
                                .queryParam("pageNumber", "2")
                                .queryParam("pageSize", "50")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()").value(50))
                .andExpect(jsonPath("$.content[0].quantityOnHand").isNotEmpty())
        ;
    }

    @Test
    void getBeerByName() throws Exception {
//        let's check the size of the list
        mockMvc.perform(
                        get(BeerController.BEER_PATH)
                                .with(httpBasic(BeerControllerTest.USER_NAME, BeerControllerTest.PASSWORD))
                                .queryParam("beerName", "IPA")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()").value(25));
//                .andExpect(result -> {
//                    String content = result.getResponse().getContentAsString();
//                    List<BeerDTO> beerDTOS = objectMapper.readValue(content, List.class);
//                    assertThat(beerDTOS.size()).isEqualTo(336);
//                });

    }

    @Test
    void getBeerByNameQueryParameter() throws Exception {
//        let's check the size of the list
        mockMvc.perform(
                        get(BeerController.BEER_PATH)
                                .with(httpBasic(BeerControllerTest.USER_NAME, BeerControllerTest.PASSWORD))
                                .queryParam("beerName", "IPA")
                                .queryParam("beerStyle", BeerStyle.IPA.name())
                                .queryParam("showInventory", "TRUE")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()").value(25))
                .andExpect(jsonPath("$.content[0].quantityOnHand").isNotEmpty())
        ;


    }

    @Test
    void getBeerByNameQueryParameterAndShowInventoryFalse() throws Exception {
//        let's check the size of the list
        mockMvc.perform(
                        get(BeerController.BEER_PATH)
                                .with(httpBasic(BeerControllerTest.USER_NAME, BeerControllerTest.PASSWORD))
                                .queryParam("beerName", "IPA")
                                .queryParam("beerStyle", BeerStyle.IPA.name())
                                .queryParam("showInventory", "FALSE")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()").value(25))
                .andExpect(jsonPath("$.content[0].quantityOnHand").isEmpty())
        ;


    }


    @Test
    void testListAllBeers() {
        Page<BeerDTO> beerDTOS = beerController.listAllBeers(null, false, null, 25, 1);

        assertEquals(25, beerDTOS.getContent().size());
    }

    @Test
    @Transactional
    @Rollback
    void testEmptyListBeers() {
        beerRepository.deleteAll();
        Page<BeerDTO> beerDTOS = beerController.listAllBeers(null, false, null, 25, 1);
        assertThat(beerDTOS.getContent().size()).isEqualTo(0);
    }

    @Test
    void getBeerById() {
        BeerDTO beerDTO = beerController.getBeerById(beerRepository.findAll().get(0).getId());
        assertThat(beerDTO).isNotNull();
    }

    @Test
    void beerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> beerController.getBeerById(UUID.randomUUID()));
    }

    @Test
    @Transactional
    @Rollback
    void testNewBeerSuccess() {

        BeerDTO newBeer1 = BeerDTO.builder().beerName("New Beer").build();
        ResponseEntity responseEntity = beerController.saveNewBeer(newBeer1);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().get("Location")).isNotNull();

        String[] location = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedId = UUID.fromString(location[location.length - 1]);

        Beer savedBeer = beerRepository.findById(savedId).get();
        assertThat(savedBeer.getBeerName()).isEqualTo("New Beer");
        assertThat(savedBeer.getId()).isEqualTo(savedId);

    }

    @Test
    @Transactional
    @Rollback
    void testUpdateBeer() {

        BeerDTO beerDTO = beerController.getBeerById(beerRepository.findAll().get(0).getId());
        beerDTO.setBeerName("Updated Beer");
        ResponseEntity responseEntity = beerController.updateBeer(beerDTO.getId(), beerDTO);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer updatedBeer = beerRepository.findById(beerDTO.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo("Updated Beer");
    }

    @Test
    void testUpdateBeerNotFound() {
        BeerDTO beerDTO = BeerDTO.builder().beerName("Updated Beer").build();
        assertThrows(NotFoundException.class, () -> beerController.updateBeer(UUID.randomUUID(), beerDTO));
    }

    @Test
    void testDeleteBeer() {
        UUID id = beerRepository.findAll().get(0).getId();
        ResponseEntity responseEntity = beerController.deleteBeer(id);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(beerRepository.findById(id)).isEmpty();
    }

    @Test
    @Transactional
    @Rollback
    void testDeleteBeerNotFound() {
        assertThrows(NotFoundException.class, () -> beerController.deleteBeer(UUID.randomUUID()));
    }


    @Test
    void updateBeerReturnsNoContent() throws Exception {
        Beer beer = beerRepository.findAll().get(0);

        Map<String, Object> beerMap = Map.of(
                "beerName", "New Beer NameNew Beer NameNew Beer NameNew Beer NameNew BeNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew BeNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew BeNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew BeNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew BeNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew BeNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer NameNew Beer Name",
                "beerStyle", "New Beer Style",
                "price", 12.99,
                "quantityOnHand", 100
        );


        MvcResult mvcResult = mockMvc.perform(
                        patch(BeerController.BEER_PATH_ID, beer.getId())
                                .with(httpBasic(BeerControllerTest.USER_NAME, BeerControllerTest.PASSWORD))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(beerMap))
                )
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }


}
```

## 008 HTTP Basic with RestTemplate
## 009 Refactor of RestTemplate Builder Config
## 010 HTTP Basic with RestTemplate Mock Context