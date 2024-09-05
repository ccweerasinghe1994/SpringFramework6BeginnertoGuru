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
  - [007 Spring Security with Web Application Context](#007-spring-security-with-web-application-context)
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
## 007 Spring Security with Web Application Context
## 008 HTTP Basic with RestTemplate
## 009 Refactor of RestTemplate Builder Config
## 010 HTTP Basic with RestTemplate Mock Context