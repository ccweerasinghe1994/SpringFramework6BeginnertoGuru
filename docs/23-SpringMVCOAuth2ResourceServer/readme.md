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
                .oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer -> httpSecurityOAuth2ResourceServerConfigurer.jwt(Customizer.withDefaults()));
        return http.build();
    }
}
```
This `SpringSecurityConfig` class configures **Spring Security** in a **Spring Boot** application to act as an **OAuth 2.0 Resource Server** that authenticates requests using **JWT (JSON Web Token)** tokens.

The configuration includes a **SecurityFilterChain** bean, which defines the security filters applied to incoming HTTP requests. It specifies that all requests require authentication and configures the application to validate incoming JWT tokens as part of the OAuth 2.0 Resource Server functionality.

Let’s break this down step by step with detailed explanations and examples of how this configuration works.

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
                .oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer -> 
                    httpSecurityOAuth2ResourceServerConfigurer.jwt(Customizer.withDefaults()));

        return http.build();
    }
}
```

#### 1. **SecurityFilterChain Bean**:
This method creates a **Spring Bean** that defines the **Security Filter Chain** for handling incoming HTTP requests. The **SecurityFilterChain** determines the security policies applied to each request.

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
```

- **`SecurityFilterChain`**: This interface defines the filter chain that intercepts incoming HTTP requests. The filters are applied to ensure that the request is processed based on the defined security rules (e.g., authentication and authorization).
  
- **`HttpSecurity`**: This is the main configuration object for defining how security is handled. It allows for configuring details such as what resources require authentication and how those resources are protected (in this case, through JWT tokens).

#### 2. **Authorizing HTTP Requests**:

```java
http.authorizeHttpRequests(auth -> {
    auth.anyRequest().authenticated();
})
```

- **`authorizeHttpRequests()`**: This method allows you to define the authorization rules for incoming requests. You can specify which endpoints require authentication and which ones can be accessed without authentication.
  
- **`auth.anyRequest().authenticated()`**: This rule enforces that **all requests** to the application require authentication. No public endpoints are allowed. Every request must include valid credentials (in this case, a valid JWT token) to be processed successfully.

##### Example Scenario:
If you have an API endpoint, such as `/api/messages`, this rule ensures that no client can access it unless they provide a valid JWT token in their request.

**Example API Request Without JWT**:
```http
GET /api/messages
```

**Response**:
```http
HTTP/1.1 401 Unauthorized
```

Without a valid JWT token in the request header, the server will reject the request and respond with a `401 Unauthorized` error.

#### 3. **Configuring the OAuth 2.0 Resource Server**:

```java
.oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer -> 
    httpSecurityOAuth2ResourceServerConfigurer.jwt(Customizer.withDefaults()));
```

This line configures the application to function as an **OAuth 2.0 Resource Server** that uses **JWT tokens** for authentication.

- **`oauth2ResourceServer()`**: This method sets up the application as an OAuth 2.0 Resource Server. Resource Servers validate access tokens (such as JWT tokens) that are issued by an Authorization Server.
  
- **`jwt(Customizer.withDefaults())`**: This configures the Resource Server to expect **JWT tokens** for authentication. By using `Customizer.withDefaults()`, Spring Security automatically handles JWT token validation, including verifying the token’s signature, expiration, and claims, using default settings.

---

### How It Works: OAuth 2.0 Resource Server with JWT Validation

Once the configuration is applied, the **Spring Boot application** becomes an OAuth 2.0 **Resource Server**. The application will only allow access to its protected resources if the client provides a valid **JWT token**.

Here’s how the process works in a real-world scenario:

#### Step 1: Authorization Server Issues JWT Token

A client (e.g., a web or mobile app) authenticates with an **Authorization Server** to obtain a **JWT access token**. The Authorization Server issues the token after the client successfully authenticates and provides the necessary credentials.

Example JWT token issued by the Authorization Server:

```json
{
  "alg": "RS256", // Signing algorithm used (RSA with SHA-256)
  "typ": "JWT",
  "kid": "12345"  // Key ID for identifying the public key used for signature verification
}
.
{
  "sub": "user123",           // Subject: The authenticated user (user ID)
  "scope": "read:messages",   // Scopes: The permissions granted by the token
  "iss": "http://auth-server",// Issuer: The Authorization Server that issued the token
  "exp": 1725679607,          // Expiration: Token's expiration time (Unix timestamp)
}
.
<signature>
```

The **JWT token** consists of:
- **Header**: Metadata about the token, such as the signing algorithm (`alg`) and the key ID (`kid`).
- **Payload**: Claims such as the user ID (`sub`), the permissions (`scope`), and the expiration time (`exp`).
- **Signature**: A cryptographic signature that ensures the token has not been tampered with. The signature is generated by the Authorization Server’s private key.

#### Step 2: Client Sends JWT Token to Resource Server

The client includes the **JWT token** in the `Authorization` header of its request when attempting to access a protected resource on the **Resource Server**.

**Example Request**:
```http
GET /api/messages
Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...
```

#### Step 3: Resource Server Validates the JWT Token

When the Resource Server receives the request, it uses the configuration defined in `SpringSecurityConfig` to validate the **JWT token**:

1. **Extract JWT Token**: The Resource Server extracts the JWT from the `Authorization` header.
  
2. **Verify JWT Signature**: The Resource Server verifies the token’s signature using the public key provided by the **Authorization Server** (via a JWK Set endpoint). This ensures that the token has not been tampered with.

3. **Validate JWT Claims**:
   - **Expiration (`exp`)**: The token must not be expired.
   - **Issuer (`iss`)**: The token must be issued by the correct Authorization Server (as defined by the `issuer-uri`).
   - **Scopes (`scope`)**: The token must contain the necessary permissions (e.g., `read:messages`) to access the requested resource.

If all checks pass, the Resource Server grants access to the requested resource. Otherwise, it responds with a `401 Unauthorized` or `403 Forbidden` error.

#### Step 4: Resource Server Grants Access or Denies

If the JWT token is valid and contains the necessary scopes, the Resource Server returns the requested resource:

**Example Response (Access Granted)**:
```json
{
  "messages": [
    "Hello, this is a protected message!",
    "You have successfully accessed the API."
  ]
}
```

If the token is invalid (e.g., expired, tampered with, or lacking the required scopes), the Resource Server denies access and returns a `401 Unauthorized` response.

**Example Response (Access Denied)**:
```json
{
  "error": "unauthorized",
  "error_description": "Full authentication is required to access this resource"
}
```

---

### Example Use Cases for This Configuration

1. **Securing APIs in a Microservices Architecture**:
   In a microservices setup, each service might act as a Resource Server, protected by OAuth 2.0 and JWT-based authentication. For example:
   - A **User Service** handles user profiles, and a **Message Service** handles messaging functionality.
   - Each service validates JWT tokens to ensure that only authorized clients can access specific resources.

2. **Cloud-Based Applications**:
   In cloud-based applications (such as a cloud-based API platform), the application can act as a Resource Server that validates JWT tokens issued by a central Authorization Server. This setup ensures secure communication between clients and services.

---

### Conclusion

The `SpringSecurityConfig` class sets up a **Spring Boot** application as an **OAuth 2.0 Resource Server** using **JWT tokens** for authentication. The configuration ensures that all HTTP requests to the application must be authenticated with a valid JWT token. The Resource Server validates the token’s signature, checks its claims, and determines whether the client has the required permissions to access the requested resources.

This setup is commonly used to secure APIs in modern applications, especially in microservices architectures or cloud-based systems where token-based authentication provides flexibility, security, and scalability.

remove the spring security configuration from properties file
```properties
# spring security
#spring.security.user.name=user1
#spring.security.user.password=password
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:9000
```
The configuration property `spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:9000` is used in a **Spring Boot** application to configure the **Resource Server** to validate **JWT (JSON Web Tokens)** issued by a specific **Authorization Server**.

Let’s break this down step by step, explaining what the property does, how it fits into the OAuth 2.0 and JWT ecosystem, and provide a practical example of how it works.

---

### 1. **Understanding the Property: `spring.security.oauth2.resourceserver.jwt.issuer-uri`**

- **`spring.security.oauth2.resourceserver.jwt.issuer-uri`**: This property tells the Spring Security configuration where to find the **Authorization Server** that issues the **JWT** tokens.
- **`http://localhost:9000`**: This is the URL of the **Authorization Server** that issued the JWT. The **Resource Server** uses this issuer URI to discover the public keys used to verify the JWT signature and to validate the token.

By setting this property, you’re effectively saying: 
- "The Resource Server should expect tokens issued by the Authorization Server located at `http://localhost:9000`."
- The Resource Server will retrieve metadata and the public key(s) from the Authorization Server at this URL to validate JWT tokens.

---

### 2. **How JWT Token Validation Works with the `issuer-uri` Property**

The **Resource Server** in OAuth 2.0 uses **JWT tokens** to authenticate requests. The `issuer-uri` plays a key role in this process by pointing to the Authorization Server that issued the token. 

#### a. **Discovery of Metadata and JWK Set**:

When a client sends a JWT token to the Resource Server, Spring Security looks up the metadata and keys for that JWT by querying the **Authorization Server’s issuer URI**. Typically, the Authorization Server provides a **metadata endpoint** (`/.well-known/openid-configuration`) that contains details about the issuer and where to retrieve the **public keys** (also known as the **JWK Set**) used to verify JWT signatures.

- The **issuer URI** usually points to the base URL of the Authorization Server, and from there, Spring Security automatically retrieves the necessary metadata and key information.

**Example Metadata Discovery**:
```http
GET http://localhost:9000/.well-known/openid-configuration
```

The response from the Authorization Server might look like this:
```json
{
  "issuer": "http://localhost:9000",
  "jwks_uri": "http://localhost:9000/oauth2/jwks",
  "authorization_endpoint": "http://localhost:9000/oauth2/authorize",
  "token_endpoint": "http://localhost:9000/oauth2/token",
  "userinfo_endpoint": "http://localhost:9000/oauth2/userinfo",
  "end_session_endpoint": "http://localhost:9000/oauth2/logout"
}
```

- **`issuer`**: This identifies the Authorization Server that issued the JWT tokens.
- **`jwks_uri`**: This is the endpoint where the **public keys** (JWK Set) are available for verifying JWT signatures.

Spring Security uses the `issuer-uri` to fetch this metadata, ensuring that tokens are only accepted if they were issued by the Authorization Server at this URI.

#### b. **Retrieving Public Keys for JWT Signature Verification**:

Once the metadata is retrieved, the Resource Server will use the **`jwks_uri`** (provided in the metadata) to fetch the **JWK Set** (JSON Web Key Set) from the Authorization Server. The public keys from this set are used to verify the **JWT signature**.

**Example JWK Set Endpoint**:
```http
GET http://localhost:9000/oauth2/jwks
```

**Response** (JWK Set):
```json
{
  "keys": [
    {
      "kid": "12345",
      "kty": "RSA",
      "alg": "RS256",
      "n": "base64url-encoded-modulus",
      "e": "AQAB"
    }
  ]
}
```

This provides the public key necessary to validate the JWT signature. The **JWT token’s header** contains a `kid` (Key ID), which tells the Resource Server which key to use from the JWK Set for validation.

#### c. **Validating the JWT Token**:

Once the **public key** is retrieved, Spring Security uses it to validate the JWT token. It verifies that:
1. The **signature** is valid.
2. The **issuer (`iss`) claim** in the token matches the `issuer-uri`.
3. The **expiration time (`exp`) claim** hasn’t passed.
4. The **audience (`aud`) claim** is correct (i.e., the token is meant for this Resource Server).

---

### 3. **Practical Example of How `issuer-uri` Works**

#### Scenario: Securing an API with JWT Tokens

1. **Authorization Server**: Running at `http://localhost:9000`, it issues JWT tokens to clients after successful authentication.

2. **Client Application**: Requests a JWT token from the Authorization Server and uses this token to access protected APIs on the Resource Server.

3. **Resource Server**: Secured using Spring Security, and it validates JWT tokens to allow or deny access to APIs.

#### Step 1: Client Requests Access Token from Authorization Server

The client authenticates with the **Authorization Server** (at `http://localhost:9000`) and requests an access token:

**Token Request**:
```http
POST http://localhost:9000/oauth2/token
Content-Type: application/x-www-form-urlencoded

grant_type=client_credentials&client_id=client123&client_secret=secret
```

**Authorization Server Response**:
```json
{
  "access_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjEyMzQ1In0.eyJzdWIiOiJjbGllbnQxMjMiLCJzY29wZSI6InJlYWQiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjkwMDAiLCJleHAiOjE2NjA3MzUwMDB9.KYd...",
  "token_type": "Bearer",
  "expires_in": 3600
}
```

The JWT token contains important claims:
- **`sub`**: The subject (client identifier).
- **`scope`**: The scope (permissions granted by the token).
- **`iss`**: The issuer (Authorization Server URL).
- **`exp`**: The expiration time of the token.

#### Step 2: Client Sends the JWT to the Resource Server

The client uses the **JWT token** to access a protected resource on the **Resource Server**.

**Client Request**:
```http
GET http://localhost:8080/api/messages
Authorization: Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6IjEyMzQ1In0.eyJzdWIiOiJjbGllbnQxMjMiLCJzY29wZSI6InJlYWQiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjkwMDAiLCJleHAiOjE2NjA3MzUwMDB9.KYd...
```

#### Step 3: Resource Server Validates the JWT Token

The **Resource Server** (your Spring Boot app) is configured with the following property:

```properties
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:9000
```

- **Step 3a**: The Resource Server contacts the **Authorization Server** at `http://localhost:9000/.well-known/openid-configuration` to retrieve the metadata, including the `jwks_uri`.
  
- **Step 3b**: The Resource Server retrieves the **JWK Set** from the `jwks_uri` to get the public keys.

- **Step 3c**: The Resource Server uses the **public key** from the JWK Set to verify the **signature** of the JWT token.

- **Step 3d**: The Resource Server verifies the token’s claims:
  - **Issuer (`iss`) claim**: Must match `http://localhost:9000`.
  - **Expiration (`exp`) claim**: The token must not be expired.
  - **Audience (`aud`) claim**: Must match the audience intended for this Resource Server.
  - **Scopes**: The token must have the appropriate scopes (e.g., `read:messages`).

If all checks pass, the Resource Server grants access to the requested resource.

#### Step 4: Access Granted

If the JWT token is valid and passes all checks, the Resource Server responds with the protected resource:

**Resource Server Response**:
```json
{
  "messages": [
    "Message 1",
    "Message 2"
  ]
}
```

---

### 4. **Why `issuer-uri` is Important**

- **Security**: By specifying the `issuer-uri`, the Resource Server knows which **Authorization Server** issued the token and can validate that the token comes from a trusted source. This prevents tokens issued by rogue Authorization Servers from being accepted.
  
- **Automatic Key Management**: The Resource Server automatically fetches the public keys (via the `jwks_uri`) needed to verify JWT signatures. This simplifies key management, especially when public keys rotate.

- **JWT Validation**: It ensures the JWT tokens are not only validly signed but also adhere to other important constraints like expiration, audience, and scopes.

---

### Conclusion

The `spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:9000
## 004 Testing with Postman

## 005 Spring MockMVC Testing with JWT

## 006 Refactor JWT Tests
