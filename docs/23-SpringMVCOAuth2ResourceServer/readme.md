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
![alt text](image-1.png)
![alt text](image-2.png)
## 005 Spring MockMVC Testing with JWT

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

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
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

    public final static String USER_NAME = "user1";
    public final static String PASSWORD = "password";

    @Test
    void getBeerByIdReturnsBeer() throws Exception {
        BeerDTO beerDTO = beerServiceImpl.listAllBeers(null, false, BeerStyle.ALE, 1, 25).getContent().get(0);
        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.of(beerDTO));


        mockMvc.perform(
                        get(BeerController.BEER_PATH_ID, beerDTO.getId())
                                .with(jwt().jwt(jwt -> {
                                            jwt.claims(claims -> {
                                                        claims.put("scope", "message:read");
                                                        claims.put("scope", "message:write");
                                                    }).subject("messaging-client")
                                                    .notBefore(Instant.now().minusSeconds(5l));
                                        }
                                ))
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
                                .with(jwt().jwt(jwt -> {
                                            jwt.claims(claims -> {
                                                        claims.put("scope", "message:read");
                                                        claims.put("scope", "message:write");
                                                    }).subject("messaging-client")
                                                    .notBefore(Instant.now().minusSeconds(5l));
                                        }
                                ))
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
                                .with(jwt().jwt(jwt -> {
                                            jwt.claims(claims -> {
                                                        claims.put("scope", "message:read");
                                                        claims.put("scope", "message:write");
                                                    }).subject("messaging-client")
                                                    .notBefore(Instant.now().minusSeconds(5l));
                                        }
                                ))
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
                                .with(jwt().jwt(jwt -> {
                                            jwt.claims(claims -> {
                                                        claims.put("scope", "message:read");
                                                        claims.put("scope", "message:write");
                                                    }).subject("messaging-client")
                                                    .notBefore(Instant.now().minusSeconds(5l));
                                        }
                                ))
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
                                .with(jwt().jwt(jwt -> {
                                            jwt.claims(claims -> {
                                                        claims.put("scope", "message:read");
                                                        claims.put("scope", "message:write");
                                                    }).subject("messaging-client")
                                                    .notBefore(Instant.now().minusSeconds(5l));
                                        }
                                ))
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
                                .with(jwt().jwt(jwt -> {
                                            jwt.claims(claims -> {
                                                        claims.put("scope", "message:read");
                                                        claims.put("scope", "message:write");
                                                    }).subject("messaging-client")
                                                    .notBefore(Instant.now().minusSeconds(5l));
                                        }
                                ))
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

        mockMvc.perform(
                        delete(BeerController.BEER_PATH_ID, beerDTO.getId())
                                .with(jwt().jwt(jwt -> {
                                            jwt.claims(claims -> {
                                                        claims.put("scope", "message:read");
                                                        claims.put("scope", "message:write");
                                                    }).subject("messaging-client")
                                                    .notBefore(Instant.now().minusSeconds(5l));
                                        }
                                ))
                )
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
                                .with(jwt().jwt(jwt -> {
                                            jwt.claims(claims -> {
                                                        claims.put("scope", "message:read");
                                                        claims.put("scope", "message:write");
                                                    }).subject("messaging-client")
                                                    .notBefore(Instant.now().minusSeconds(5l));
                                        }
                                ))
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

The `BeerControllerTest` class is a unit test for the `BeerController` in a Spring Boot application. It uses **JUnit 5**, **Mockito**, and **Spring MockMvc** to test how the controller behaves when handling HTTP requests. This test suite is focused on the **BeerController**, ensuring that it properly processes requests to fetch a beer by its ID.

Here’s a detailed explanation of the test configuration, methods, and key concepts:

---

### 1. **Annotations and Test Setup**

```java
@WebMvcTest(BeerController.class)
@Import(SpringSecurityConfig.class)
class BeerControllerTest {
```

- **`@WebMvcTest(BeerController.class)`**: This annotation tells Spring to only load the **`BeerController`** and the necessary beans for web testing, such as Spring MVC infrastructure. It isolates the controller, allowing you to test it without needing to start the entire application.
  - **Purpose**: Focus on the web layer by testing the controller's logic in isolation without involving other components like services or repositories.

- **`@Import(SpringSecurityConfig.class)`**: This imports the **SpringSecurityConfig** class, which configures the OAuth 2.0 Resource Server behavior. Since the controller requires authentication, Spring Security needs to be configured for the tests to properly simulate authenticated requests.
  - **Purpose**: To ensure that security configurations (such as JWT authentication) are applied in the test environment, enabling JWT-based authentication testing.

---

### 2. **Mocking Dependencies**

```java
@MockBean
BeerService beerService;
```

- **`@MockBean BeerService beerService;`**: This annotation tells Spring to inject a **mock** of the `BeerService` into the `BeerController` during the test. By mocking the service, you control its behavior and responses, isolating the controller from the actual service logic.
  - **Purpose**: Control the behavior of the `BeerService` (which the controller depends on) without needing to rely on actual service logic or database interactions.

---

### 3. **Testing Dependencies**

```java
@Autowired
MockMvc mockMvc;

@Autowired
ObjectMapper objectMapper;
```

- **`MockMvc`**: This is a Spring-provided class that simulates HTTP requests and allows you to test the controller as if it were handling real HTTP requests.
  - **Purpose**: Simulate HTTP calls in the test environment and verify responses from the controller.
  
- **`ObjectMapper`**: A Jackson object that serializes and deserializes Java objects into JSON and vice versa. It is useful for verifying JSON responses and generating request payloads.
  - **Purpose**: Convert Java objects into JSON for request payloads and validate JSON responses.

---

### 4. **Captor Usage for Argument Capture**

```java
@Captor
ArgumentCaptor<UUID> uuidArgumentCaptor;

@Captor
ArgumentCaptor<BeerDTO> beerArgumentCaptor;
```

- **Argument Captors**: These are used to capture the arguments passed into mocked methods (e.g., methods in the `BeerService`) so you can verify or assert their values.
  - **`uuidArgumentCaptor`**: Captures the UUID argument passed to `beerService.getBeerById()`.
  - **`beerArgumentCaptor`**: Captures the `BeerDTO` object passed to the service in cases where a beer object is being created or updated.

---

### 5. **Mocking Service Behavior**

```java
BeerDTO beerDTO = beerServiceImpl.listAllBeers(null, false, BeerStyle.ALE, 1, 25).getContent().get(0);
given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.of(beerDTO));
```

- **`beerServiceImpl.listAllBeers()`**: This generates a sample `BeerDTO` object from a mock implementation. This DTO is used in the test to simulate a beer being fetched from the service.
  - **Purpose**: Prepare test data for validation.
  
- **`given(beerService.getBeerById(any(UUID.class)))`**: This sets up the mock `beerService` to return the `beerDTO` when `getBeerById` is called with any `UUID` as the parameter.
  - **Purpose**: Mock the behavior of the `getBeerById` method so the test can simulate the controller returning a beer object without relying on actual service logic or data.

---

### 6. **Simulating a JWT-Authenticated Request**

```java
mockMvc.perform(
    get(BeerController.BEER_PATH_ID, beerDTO.getId())
        .with(jwt().jwt(jwt -> {
            jwt.claims(claims -> {
                claims.put("scope", "message:read");
                claims.put("scope", "message:write");
            }).subject("messaging-client")
            .notBefore(Instant.now().minusSeconds(5l));
        }))
        .accept(MediaType.APPLICATION_JSON)
)
```

- **`get(BeerController.BEER_PATH_ID, beerDTO.getId())`**: This simulates an HTTP GET request to the `BeerController` to retrieve a beer by its ID. The `BEER_PATH_ID` is a path constant defined in the `BeerController` that represents the URL endpoint for fetching a beer by ID.

- **`with(jwt())`**: This method is used to simulate a request from an authenticated client by attaching a JWT to the request.
  - **`claims.put("scope", "message:read")`**: This adds a scope to the JWT, allowing the request to simulate a user with specific permissions (in this case, `message:read` and `message:write`).
  - **`.subject("messaging-client")`**: This sets the subject of the JWT token, representing the authenticated client.
  - **`notBefore(Instant.now().minusSeconds(5))`**: This ensures that the token is valid and not rejected due to timing issues by setting the token's `notBefore` claim.

- **Purpose**: The purpose of this block is to simulate an authenticated request using a JWT token. The JWT contains claims (such as scopes and subject) that the controller would typically validate before granting access to the resource.

---

### 7. **Validating the Response**

```java
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
```

- **`andExpect(status().isOk())`**: Ensures that the HTTP response status is 200 (OK), indicating that the request was successfully processed.
  
- **`andExpect(content().contentType(MediaType.APPLICATION_JSON))`**: Verifies that the response is in JSON format.

- **`jsonPath("$.id").value(beerDTO.getId().toString())`**: Uses **JSONPath** to verify that the JSON response contains the expected `id` field, which matches the ID of the `BeerDTO` object.
  
- **Other `jsonPath` Verifications**: Similar checks are applied to verify the `beerName`, `beerStyle`, `upc`, `quantityOnHand`, and `price` fields in the JSON response. These checks ensure that the controller properly converts the `BeerDTO` object to JSON format and returns it in the expected structure.

- **Purpose**: These assertions confirm that the controller returns the correct data in the expected format (JSON) with the correct values.

---

### Conclusion

This test verifies the behavior of the `BeerController` when handling a request to retrieve a beer by its ID. The test simulates an authenticated client request with a JWT, mocks the `BeerService` behavior, and validates the controller’s response. Key aspects of this test include:
1. **Mocking dependencies**: Ensuring the controller logic is isolated from the actual service.
2. **Simulating JWT authentication**: Using Spring Security's `jwt()` function to simulate an OAuth 2.0 authenticated request.
3. **Validating the response**: Ensuring the response from the controller is as expected, including the correct JSON structure and HTTP status.

This approach is typical for testing REST controllers in Spring applications where JWT authentication and service layer logic are involved.

This block of code is used to **simulate a JWT-authenticated request** in a unit test using **Spring Security Test** utilities. It mimics an OAuth 2.0 authenticated request by attaching a **JWT (JSON Web Token)** to the mock HTTP request. Here's a step-by-step breakdown of each part of this code and its purpose, along with an example of how it works in real-world scenarios.

---

### **Code Breakdown and Purpose**

```java
.with(jwt().jwt(jwt -> {
            jwt.claims(claims -> {
                claims.put("scope", "message:read");
                claims.put("scope", "message:write");
            }).subject("messaging-client")
            .notBefore(Instant.now().minusSeconds(5l));
        }))
```

This is a **JWT authentication setup** in a **MockMvc** test. It simulates an HTTP request where the client includes a JWT in the `Authorization` header. The JWT contains **claims**, such as the subject (representing the client) and scopes (defining the permissions). Let’s break down each section:

---

#### 1. **`.with(jwt())`**

This part attaches a **JWT** to the simulated HTTP request. The **`jwt()`** method is part of Spring Security's **SecurityMockMvcRequestPostProcessors** API, which provides a way to add security-related information to a mock request.

- **Purpose**: The `jwt()` method creates a JWT for the mock request, allowing you to simulate a JWT-based authentication flow in your tests. This JWT will be included in the `Authorization: Bearer` header of the request.

**Example**:
```java
mockMvc.perform(
    get("/api/messages")
        .with(jwt()) // Attaches a JWT token to the request
);
```

---

#### 2. **`.jwt(jwt -> { ... })`**

This part customizes the contents of the JWT token. It allows you to set specific claims, such as the token's subject (i.e., who the token represents) and any custom claims like `scope`.

- **Purpose**: This is where you define the structure of the JWT, including its claims (the information embedded within the token), such as the user's identity (subject), permissions (scopes), and other properties like token validation time.

---

#### 3. **`.claims(claims -> { claims.put("scope", "message:read"); claims.put("scope", "message:write"); })`**

The `.claims()` method sets the **claims** for the JWT token. Claims are key-value pairs that provide information about the token, such as what the client is allowed to do (scopes), who the client is (subject), when the token was issued, and its expiration.

- **`claims.put("scope", "message:read")`**: This line adds a **`scope`** claim to the JWT. The scope defines what actions the client is authorized to perform. In this case, the client is granted the **`message:read`** permission.
- **`claims.put("scope", "message:write")`**: Another scope is added, allowing the client to perform **`message:write`** operations.

**Purpose**: By adding these scope claims, you simulate a client that has specific permissions. In this case, the client is authorized to both read and write messages, which would be validated by the API before granting access to specific endpoints.

**Example in OAuth 2.0**:
In a real-world OAuth 2.0 system, the `scope` claim is often used to define what resources or actions a client is allowed to access. For example:
- **`read:messages`**: Allows the client to read messages.
- **`write:messages`**: Allows the client to create or modify messages.

In this test case, the client is granted the ability to both read and write messages, simulating a typical authorization scenario.

---

#### 4. **`.subject("messaging-client")`**

The **`subject`** is another claim in the JWT token that represents the identity of the client or user. In OAuth 2.0, the **`sub`** (subject) claim is used to identify the principal (i.e., the user or client) that the token is issued for.

- **`subject("messaging-client")`**: This means that the token is being issued for a client identified as `"messaging-client"`. This could represent an application or service interacting with the API.

**Purpose**: By setting the `subject` claim, you simulate an authenticated client that is known to the system. The controller or API can later use this information to identify the client making the request.

**Example in OAuth 2.0**:
In real-world applications, the `sub` claim would be something like:
- **User subject**: `"user123"` — representing a user.
- **Client subject**: `"messaging-client"` — representing a machine-to-machine client application.
  
The `subject` helps distinguish between different users or applications when they access the API.

---

#### 5. **`.notBefore(Instant.now().minusSeconds(5l))`**

The **`notBefore`** claim (often abbreviated as `nbf`) defines the earliest time when the token is considered valid. In this case, the token is valid starting from 5 seconds before the current time.

- **`Instant.now().minusSeconds(5l)`**: This sets the **`notBefore`** claim to 5 seconds before the current time. This ensures that there are no issues related to timing skew, especially in cases where the test might fail because the token is technically issued slightly "in the future."

**Purpose**: Setting a `notBefore` time ensures that the token is not used before its intended activation time. This helps avoid issues where a token is issued but isn't valid for immediate use, which might be a security concern in some systems.

**Example in Real Use**:
In OAuth 2.0, the `nbf` claim is used to prevent a token from being used before it is supposed to be active. For instance, a token might have a delay before becoming active, allowing the system to enforce a more secure time window.

---

### **Practical Example Scenario**

Let’s consider a real-world scenario where an API only allows clients with specific permissions to perform certain actions, such as reading or writing messages.

#### Example: A Messaging API

1. **Authorization Server** issues a **JWT token** to the `messaging-client` that contains two scopes: `message:read` and `message:write`.
2. The `messaging-client` makes a request to the **Message Service API** to retrieve a list of messages.
3. The **Resource Server** (the Spring Boot app with Spring Security configured as a Resource Server) checks the **JWT token**:
   - Verifies the **signature** using the public key.
   - Checks the **scopes** (`message:read`, `message:write`) to see if the client is allowed to read or write messages.
   - Checks the **subject** (`messaging-client`) to identify the client.
   - Validates the **notBefore** time to ensure the token is valid for use at the time of the request.

4. If the token is valid and contains the necessary scopes, the server allows the `messaging-client` to access the API endpoint and retrieve the messages.

---

### **Conclusion**

This code block is a part of a unit test that simulates an HTTP request to a Spring MVC controller secured with JWT-based authentication. It simulates a JWT token with custom claims (such as `scope` and `subject`) to authenticate the client. The use of `.with(jwt())` allows the test to mock a JWT token and simulate a real-world scenario where clients authenticate with a JWT to gain access to specific API endpoints.

This approach is useful for testing **JWT-secured REST APIs**, ensuring that the controller behaves correctly when a valid or invalid JWT token is presented. It allows developers to test authorization logic, such as scope-based access control, in an isolated test environment.

## 006 Refactor JWT Tests
