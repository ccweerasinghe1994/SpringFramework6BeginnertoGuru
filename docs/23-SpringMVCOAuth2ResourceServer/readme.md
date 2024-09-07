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

## 004 Testing with Postman

## 005 Spring MockMVC Testing with JWT

## 006 Refactor JWT Tests
