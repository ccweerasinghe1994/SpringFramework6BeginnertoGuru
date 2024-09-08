# 23 - Spring MVC OAuth2 Resource Server

## 001 Introduction 
![alt text](image.png)
## 002 Maven Dependencies
The `<dependency>` you provided is for **Spring Boot's OAuth 2.0 Resource Server** functionality. Adding this dependency allows your Spring Boot application to act as an **OAuth 2.0 Resource Server**, which means the application can accept and process **OAuth 2.0 access tokens** to protect its APIs and resources, ensuring only authorized clients can access them.

Here’s a detailed explanation of this dependency, its purpose, and how it works in a real-world scenario.

---

### Dependency Overview

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>
```

- **Group ID**: `org.springframework.boot` – This is the Spring Boot parent group.
- **Artifact ID**: `spring-boot-starter-oauth2-resource-server` – This is the specific starter that provides the OAuth 2.0 Resource Server functionality.

By including this dependency, Spring Boot will automatically set up the necessary components to transform your application into a Resource Server capable of handling **OAuth 2.0** tokens, typically **JWT tokens** (JSON Web Tokens).

---

### What is a Resource Server in OAuth 2.0?

A **Resource Server** is a component of the **OAuth 2.0** architecture that hosts protected resources (such as APIs or data). The Resource Server verifies access tokens issued by an **Authorization Server** before granting or denying access to a client application.

- **Client**: An application (like a mobile app or web app) that requests access to resources.
- **Authorization Server**: Issues access tokens after authenticating users.
- **Resource Server**: Validates the access token and determines whether to allow or deny access to the protected resources (such as APIs).

When an application serves as a Resource Server, it typically receives an access token from the client. This token is validated to ensure that it is:
- **Authentic**: The token was issued by a trusted Authorization Server.
- **Valid**: The token has not expired or been revoked.
- **Authorized**: The token contains the necessary permissions (scopes) to access the requested resources.

---

### How the Dependency Works in a Real-World Scenario

Once you add the `spring-boot-starter-oauth2-resource-server` dependency, Spring Boot automatically configures your application to handle OAuth 2.0 access tokens. Here’s how it works:

#### 1. **Token Validation**:
When a client makes an HTTP request to an API endpoint in the Resource Server, the request includes an **access token** (usually in the `Authorization` header as a Bearer token). For example:

```http
GET /api/messages
Authorization: Bearer eyJraWQiOiIyMzQ1IiwidHlwIjoiSldUIiw...
```

The Resource Server extracts the token and verifies its authenticity and validity. It can either:
- **Validate the token locally**: For example, by verifying the signature of a **JWT** token using a **public key** provided by the Authorization Server.
- **Validate the token remotely**: By sending the token to the **Authorization Server's introspection endpoint** to check its status.

#### 2. **Protected API**:
With this setup, your API endpoints are protected automatically. For instance, you can restrict access to a specific endpoint, requiring a valid access token to access it.

Here’s an example of an API endpoint that would be protected in a Resource Server:

```java
@RestController
public class MessageController {

    @GetMapping("/api/messages")
    public String getMessages() {
        return "You have access to the protected messages!";
    }
}
```

If a client tries to access `/api/messages` without a valid access token, the Resource Server will respond with an HTTP **401 Unauthorized** status code.

---

### Configuring Spring Security to Protect Resources

In order to protect the resources, you will configure Spring Security to handle JWT or opaque tokens. Here’s an example of how you might configure Spring Security to handle OAuth 2.0 access tokens (JWT in this case):

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .antMatchers("/api/public").permitAll()
                    .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2ResourceServer ->
                oauth2ResourceServer
                    .jwt()  // Enable JWT token validation
            );
    }
}
```

- **Public Endpoint**: `/api/public` is an open endpoint that doesn’t require authentication.
- **Protected Endpoints**: All other endpoints (e.g., `/api/messages`) require the user to be authenticated using an access token.

---

### Example with JWT Tokens

Let’s assume the Resource Server is validating **JWT tokens** issued by an Authorization Server. Here’s how the process works:

1. **Client Application Requests a Token**: 
   - A client (e.g., a mobile app or web app) requests an access token from the Authorization Server by sending the user’s credentials or performing a redirect to the authorization page.
   - The Authorization Server responds with an access token (a **JWT** token in this case).

   Example JWT token:
   ```json
   {
     "alg": "RS256",
     "typ": "JWT",
     "kid": "12345"
   }
   .
   {
     "sub": "user123",
     "scope": "read:messages",
     "iss": "http://auth-server.com",
     "exp": 1725679907
   }
   .
   <signature>
   ```

2. **Client Makes an API Request**:
   - The client sends the access token along with a request to the Resource Server:
     ```http
     GET /api/messages
     Authorization: Bearer eyJraWQiOiIyMzQ1IiwidHlwIjoiSldUIiw...
     ```

3. **Resource Server Validates the Token**:
   - The Resource Server validates the **JWT** token by verifying its signature using the public key retrieved from the Authorization Server.
   - It also checks the token’s expiration, audience, and scopes to ensure it’s valid and grants the necessary permissions.

4. **Access is Granted or Denied**:
   - If the token is valid and has the required scopes (e.g., `"read:messages"`), the Resource Server grants access to the requested API resource and responds with the data.
   - If the token is invalid, expired, or doesn’t have the necessary permissions, the Resource Server denies access and returns a `401 Unauthorized` or `403 Forbidden` response.

---

### Example Application: Building a Resource Server

Here’s how you might build a simple Resource Server using the `spring-boot-starter-oauth2-resource-server` dependency.

1. **Add the Dependency**:
   First, add the following dependency in your `pom.xml` file:

   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
   </dependency>
   ```

2. **Configure the Resource Server**:
   Configure Spring Security to enable OAuth 2.0 Resource Server capabilities with JWT token support:

   ```java
   @Configuration
   @EnableWebSecurity
   public class SecurityConfig extends WebSecurityConfigurerAdapter {

       @Override
       protected void configure(HttpSecurity http) throws Exception {
           http
               .authorizeRequests(authorizeRequests ->
                   authorizeRequests
                       .antMatchers("/api/public").permitAll() // Allow public access
                       .anyRequest().authenticated() // Protect other endpoints
               )
               .oauth2ResourceServer(oauth2ResourceServer ->
                   oauth2ResourceServer
                       .jwt()  // Enable JWT validation
               );
       }
   }
   ```

3. **Create a Controller with Protected and Public Endpoints**:

   ```java
   @RestController
   public class MessageController {

       @GetMapping("/api/public")
       public String getPublicMessage() {
           return "This is a public message!";
       }

       @GetMapping("/api/messages")
       public String getMessages() {
           return "You have access to the protected messages!";
       }
   }
   ```

4. **Test the Resource Server**:
   - Access the public endpoint: `GET /api/public` – This should return the public message without needing any authentication.
   - Access the protected endpoint: `GET /api/messages` – This will require a valid JWT token in the `Authorization` header. If a valid token is provided, the Resource Server will grant access.

---

### Conclusion

The `spring-boot-starter-oauth2-resource-server` dependency provides the necessary tools to transform a Spring Boot application into an **OAuth 2.0 Resource Server**. The Resource Server is responsible for verifying access tokens, typically JWT tokens, and ensuring that only authenticated and authorized clients can access protected resources. This setup is commonly used in modern microservice architectures, where services communicate using OAuth 2.0 tokens to protect APIs and sensitive data.
## 003 Spring Security Configuration

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
        http.authorizeHttpRequests(auth -> {
                    auth.anyRequest().authenticated();
                })
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }
}

```
This `SpringSecurityConfig` class defines a **security filter chain** using Spring Security in a **Spring Boot** application. The configuration sets up the application as an **OAuth 2.0 Resource Server** and ensures that all incoming HTTP requests must be authenticated using **JWT (JSON Web Token)** tokens.

Let’s break down the key elements of this configuration step by step, and provide practical examples.

---

### Overview of Key Concepts

1. **SecurityFilterChain**: This defines the security filter logic for handling HTTP requests. It determines which requests need to be authenticated and how they are handled.
  
2. **OAuth 2.0 Resource Server**: The application is configured as a Resource Server that validates JWT tokens received from client applications. The Resource Server protects APIs and ensures that only clients with valid tokens can access them.

3. **JWT (JSON Web Token)**: This is a token format used to securely transmit information between parties, typically between the Authorization Server and the Resource Server. The JWT contains claims (such as user information and permissions) and is signed by the Authorization Server.

---

### Code Breakdown

```java
@Configuration
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
                    auth.anyRequest().authenticated();
                })
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }
}
```

#### 1. **SecurityFilterChain Bean**

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
```

- **`SecurityFilterChain`**: This bean configures Spring Security to apply specific security rules to HTTP requests.
- The **`HttpSecurity`** object allows you to configure the security policies for handling HTTP requests, such as requiring authentication for certain requests or allowing others to be accessed without credentials.

#### 2. **Authorizing HTTP Requests**

```java
http.authorizeHttpRequests(auth -> {
    auth.anyRequest().authenticated();
})
```

- **`authorizeHttpRequests()`**: This method allows you to define security policies for different HTTP endpoints. In this case:
  - **`anyRequest().authenticated()`**: This means **every request** to the application must be authenticated. No endpoint is open to public access. Any HTTP request must include valid credentials (in the form of a JWT token) to be processed.

##### Example Scenario:

If you had an API endpoint such as `/api/messages`, this rule ensures that no client can access it unless they provide a valid JWT token in their request.

**Example:**
```http
GET /api/messages
Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5c...
```

If the client doesn't provide a valid token, the server responds with:
```http
HTTP/1.1 401 Unauthorized
```

#### 3. **Configuring the OAuth 2.0 Resource Server**

```java
.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
```

- **`oauth2ResourceServer()`**: This configures the application to act as an **OAuth 2.0 Resource Server**.
- **`.jwt(Customizer.withDefaults())`**: This tells Spring Security that the Resource Server will use **JWT tokens** for authentication. The **defaults** configuration enables Spring to handle JWT validation, signature checking, and claims verification automatically.

When a client application sends a request to the Resource Server, Spring Security will:
1. **Extract the JWT**: It reads the `Authorization` header to retrieve the token.
2. **Validate the JWT**: It validates the signature of the JWT using the **public key** from the **Authorization Server**. If the signature is valid, it proceeds to check the token's claims (such as expiration, issuer, and audience).
3. **Verify Claims**: If the claims (such as expiration time and audience) are valid, the request is considered authenticated.

##### Example of JWT Validation:

- **Client Request**: The client application sends the JWT as part of the HTTP request:
  
  ```http
  GET /api/messages
  Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...
  ```

- **JWT Token**: The JWT looks something like this:
  ```plaintext
  eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyMTIzIiwic2NvcGUiOiJyZWFkOm1lc3NhZ2VzIiwiZXhwIjoxNjY0NDU0NjAwfQ.VjB5SOXsT0spO25AL...
  ```

  The JWT contains:
  - **Header**: Metadata about the token (e.g., signing algorithm, key ID).
  - **Payload**: Claims such as the user identifier (`sub`), token expiration time (`exp`), and the scopes granted to the token.
  - **Signature**: This ensures the token has not been tampered with. It is signed with the private key of the **Authorization Server**.

- **Resource Server Behavior**:
  1. The Resource Server extracts the JWT from the request.
  2. It verifies the signature of the token using the public key provided by the Authorization Server.
  3. It checks the claims, such as whether the token has expired (`exp`) and whether the token allows access to the resource (based on the `scope`).
  4. If all checks pass, the Resource Server processes the request and returns the requested resource.

---

### Full Example in a Real-World Scenario

#### Step 1: Client Requests Access Token

1. A client application (e.g., a web app) requests an **access token** from the **Authorization Server**.
2. The Authorization Server authenticates the user and issues a **JWT access token**.

#### Step 2: Client Makes an API Request with the JWT Token

The client application then makes an API request to the **Resource Server** (your Spring Boot app) with the JWT in the `Authorization` header.

**Example Request**:
```http
GET /api/messages
Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...
```

#### Step 3: Resource Server Validates the Token

The Resource Server (your Spring Boot application):
1. Extracts the JWT from the `Authorization` header.
2. Validates the token’s signature using the **public key** from the Authorization Server.
3. Checks the claims in the token (e.g., expiration time, audience, and scope).
4. If the token is valid and the user has the appropriate permissions (scopes), the server allows access to the requested API endpoint.

If valid, the server returns the protected resource, for example:

**Example Response**:
```json
{
  "messages": [
    "Message 1",
    "Message 2"
  ]
}
```

#### Step 4: Invalid Token Handling

If the JWT is invalid (e.g., expired, tampered with, or missing required claims), the Resource Server will deny access and return a **401 Unauthorized** error.

**Example Error Response**:
```json
{
  "error": "unauthorized",
  "error_description": "Full authentication is required to access this resource"
}
```

---

### Conclusion

This `SpringSecurityConfig` class configures your Spring Boot application as a **Resource Server** in an OAuth 2.0 system. By adding the **JWT validation** setup, all incoming HTTP requests to the application will require authentication using a JWT access token. Spring Security will handle the token validation, including verifying the signature and checking the token's claims, ensuring that only authorized clients can access protected resources.

This setup is a standard approach for securing APIs in microservices architectures where the Resource Server relies on external **Authorization Servers** to issue and validate access tokens.
## 004 Testing with Postman

## 005 Spring MockMVC Testing with JWT

## 006 Refactor JWT Tests
