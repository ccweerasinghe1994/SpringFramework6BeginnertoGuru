# 22 - Spring Authorization Server

## 001 Introduction
![alt text](image.png)
## 002 Overview of OAuth 2 and JWT
![alt text](image-1.png)
### Overview of OAuth 2.0

OAuth 2.0 is an **authorization framework** that allows third-party applications to access resources (like user data) on a server without exposing the user's credentials (like passwords). OAuth 2.0 is widely used by platforms such as **Google**, **Facebook**, **GitHub**, and many others to delegate limited access to resources for third-party applications, typically through **"Sign in with"** functionality.

At its core, OAuth 2.0 enables users to grant limited permissions to third-party applications, without sharing their username and password. The framework revolves around granting access tokens, which represent the user's authorization for a third-party application to access specific resources on their behalf.

### Key Concepts in OAuth 2.0

1. **Authorization**: OAuth 2.0 focuses on granting **limited access** to resources (like files, photos, or user data) without requiring the user to share full access to their account (like passwords).
2. **Actors**: OAuth 2.0 involves multiple parties:
   - **Resource Owner (User)**: The person who owns the account or the data.
   - **Client (Third-Party Application)**: The application that requests access to the resource owner’s data.
   - **Authorization Server**: The server that authenticates the resource owner and issues access tokens.
   - **Resource Server**: The server that holds the protected resources (like Google APIs, GitHub repositories, etc.).
3. **Access Token**: An access token is a temporary credential issued to the third-party application after the resource owner authorizes the application.
4. **Grant Types**: OAuth 2.0 defines several grant types (methods) for obtaining access tokens. The most common ones are:
   - **Authorization Code Grant**: Used by web and mobile apps for exchanging an authorization code for an access token.
   - **Client Credentials Grant**: Used when the client itself (not a user) is authorized to access resources.
   - **Implicit Grant**: Typically used in client-side web applications where tokens are directly issued.
   - **Resource Owner Password Credentials Grant**: Involves direct exchange of the user's credentials for a token (not recommended in most modern use cases).

### Detailed Explanation with Examples

#### Example 1: OAuth 2.0 in Action ("Sign in with Google")

Let’s assume you’re using a third-party application (e.g., a note-taking app) that wants to allow you to sign in using your Google account. Here’s how the OAuth 2.0 flow works in such a case:

1. **Client Application Requests Authorization**:
   - You, the **resource owner**, open the third-party app (the **client**) and click "Sign in with Google".
   - The app redirects you to Google’s **Authorization Server** (usually a URL like `https://accounts.google.com/o/oauth2/auth`).

2. **User Grants Authorization**:
   - Google asks you to sign in (if you’re not already signed in) and to grant specific permissions to the app (for example, "Allow this app to view your Google Contacts").
   - You review the requested permissions and, if you agree, authorize the app.

3. **Authorization Code is Issued**:
   - After you authorize the app, Google sends an **authorization code** back to the client application. This code is typically short-lived and is not yet the access token.

4. **Client Exchanges Code for Access Token**:
   - The third-party app takes the authorization code and makes a **back-channel request** to Google's **Token Endpoint** (a secure server-to-server request) to exchange the code for an **access token**.
   - The access token represents your permission for the third-party app to act on your behalf.

5. **Client Uses Access Token to Access Resources**:
   - With the access token, the third-party app can now make API requests to Google’s **Resource Server** (e.g., Google Contacts API) to fetch or modify data based on the permissions you granted.
   - Importantly, the third-party app does not have access to your password—it only has the **limited** permissions that you authorized.

6. **Access Tokens Can Expire**:
   - The access token is typically short-lived (e.g., 1 hour). After it expires, the client application can request a **refresh token** (if one was issued) to obtain a new access token without requiring the user to log in again.

##### OAuth 2.0 Authorization Code Flow Diagram:

```plaintext
+----------+                                +-----------------+
| Resource |--(1)-- User grants permission ->| Authorization    |
|  Owner   |                                | Server (Google)  |
+----------+                                +-----------------+
    |                                             |
    |      +--------------+                       |
    |      |              |                       |
    |      | Authorization|                       |
    |      |    Code       |                       |
    |      |              |                       |
    |      +--------------+                       |
    |             ^                               |
    |             |                               |
    v             |                               v
+-----------+     |                          +--------------------+
| Client    |--(2)---- Authorization Code -->|  Token Endpoint    |
| (App)     |                                | (Google)           |
+-----------+                                +--------------------+
    |                                              |
    |                                              |
    |      +---------------+                      |
    |      |               |                      |
    |      |  Access Token |                      |
    |      |               |                      |
    |      +---------------+                      |
    |              ^                               |
    |              |                               |
    v              |                               v
+--------------------+                       +----------------+
|  Resource Server   |--(3)--- Access Token ->| Protected Data  |
|    (Google APIs)   |                       | (Google Contacts)|
+--------------------+                       +----------------+
```

#### Example 2: "Sign in With GitHub" (OAuth 2.0)

GitHub also supports OAuth 2.0 for authorization. Let's walk through how OAuth 2.0 is applied when a third-party app (e.g., a CI/CD platform like CircleCI) wants to access your GitHub repositories.

1. **Third-party App Requests Authorization**:
   - You visit the third-party platform, and it asks you to **sign in with GitHub**.
   - The platform redirects you to GitHub’s **Authorization Server** (at `https://github.com/login/oauth/authorize`), where you are prompted to log in (if not already logged in).

2. **User Grants Access to Repositories**:
   - After logging in, GitHub asks whether you want to allow the third-party app to access your private or public repositories. The permissions requested are typically displayed as a list, such as:
     - Read/write access to repositories
     - Commit status
     - User email address
   - If you agree, you authorize the app.

3. **Authorization Code is Issued**:
   - GitHub returns an **authorization code** to the third-party app.

4. **Access Token Request**:
   - The app exchanges the authorization code for an **access token** by making a request to GitHub's **Token Endpoint** at `https://github.com/login/oauth/access_token`.

5. **Access Resources**:
   - Using the access token, the third-party platform can now perform actions on your repositories on your behalf, such as cloning, pushing code, or checking repository status.

6. **Scope of Permissions**:
   - OAuth 2.0 lets the user **limit access** to only specific scopes of resources. For example, the third-party app may only have read access to repositories and cannot write or delete code unless explicitly granted.

### Core OAuth 2.0 Grant Types

1. **Authorization Code Grant**: Used primarily for server-side and web applications. It involves exchanging an authorization code (received from the authorization server) for an access token. This method is most secure because the authorization code is exchanged over a server-to-server channel.

2. **Implicit Grant**: Used for client-side applications (e.g., single-page applications). The access token is returned directly in the URL. This is less secure since the token can be exposed in the browser URL, and OAuth 2.1 recommends avoiding this flow.

3. **Client Credentials Grant**: This grant type is used when the **client itself** needs to authenticate (without a user). This is common for machine-to-machine communication, where one service accesses another service’s API.

4. **Resource Owner Password Credentials Grant**: The user directly provides their username and password to the client, which exchanges them for an access token. This is no longer recommended because it exposes user credentials to the client.

### Benefits of OAuth 2.0

1. **Security**: OAuth 2.0 allows users to grant limited access to their resources without sharing passwords. Tokens are used instead of credentials.
2. **Fine-Grained Control**: OAuth 2.0 supports **scopes**, allowing users to grant specific permissions (e.g., read-only access to contacts, but not email).
3. **Centralized Authentication**: Users authenticate through trusted providers (e.g., Google, Facebook), reducing the risk of phishing and password reuse.
4. **Separation of Concerns**: OAuth 2.0 separates **authentication** (proving who the user is) from **authorization** (granting limited access to resources).

### Summary

OAuth 2.0 is a powerful authorization framework that allows users to delegate limited access to their resources without exposing sensitive credentials. This enables third-party applications to perform specific actions on behalf of the user while maintaining the security and privacy of the user’s account. Common use cases include "Sign in with Google/Facebook" and granting access to apps like GitHub to manage repositories or access email

![alt text](image-2.png)

OAuth 2.0 involves four key roles, each responsible for a specific function in the process of granting and managing access to protected resources. These roles collaborate to facilitate the secure delegation of access, ensuring that third-party applications (clients) can perform actions on behalf of a user without needing their credentials.

Let's break down these roles in detail and illustrate how they work together with practical examples.

---

### 1. **Resource Owner** (The User)

The **Resource Owner** is typically the **user** who owns the resources (such as data, accounts, or services) that a third-party application wants to access. The resource owner is the one who ultimately decides whether to grant access to the client application. 

#### Example:
- **User**: John Doe
- **Resource**: John’s Google Drive documents

John is the resource owner because the documents stored in his Google Drive are his. When a third-party app like a document editor requests access to John's Google Drive, John is responsible for approving or denying that request.

---

### 2. **Client** (Third-Party Application)

The **Client** is the **third-party application** that requests access to the resource owner's protected resources. The client must receive explicit permission from the resource owner before it can access or interact with those resources. Importantly, the client does not get full access to the user's account but rather receives **limited access** as defined by the resource owner.

#### Example:
- **Client**: A document editor app, say, **DocuEditor**
- **Purpose**: DocuEditor wants to access John’s Google Drive files so it can allow John to edit his documents from within the application.

In this example, the **client** is **DocuEditor**, which requests limited access to John's Google Drive, such as viewing and editing specific documents.

##### Flow:
1. **DocuEditor** redirects John to Google’s login page to request access to Google Drive.
2. John logs in and authorizes the app to view and edit his files.

DocuEditor, as the client, will receive an **access token** from Google, which allows it to perform the requested actions on John's files.

---

### 3. **Resource Server** (The API or Service Hosting the Resources)

The **Resource Server** hosts the actual resources (such as data, files, or services) that the client wants to access. It can be a REST API or another service that enforces the policies about who can access the data. 

The **resource server** validates access tokens and grants access to the requested resources if the token is valid.

#### Example:
- **Resource Server**: Google Drive API

In this scenario, **Google Drive API** is the resource server because it holds the documents that John owns. When the third-party client, DocuEditor, requests access to John’s files, it does so by making requests to the Google Drive API.

##### Flow:
1. After John authorizes DocuEditor, the app receives an access token.
2. DocuEditor uses this access token to interact with the **Google Drive API** (resource server) to retrieve, update, or delete files according to the permissions granted.

The **resource server** will verify the access token and check the **scope** of the permissions (what the app is allowed to do) before fulfilling the request.

---

### 4. **Authorization Server** (Identity and Token Management)

The **Authorization Server** is responsible for **authenticating** the resource owner (user) and issuing access tokens to the client after authorization. It validates the user's credentials and handles the exchange of authorization codes or credentials for access tokens, which are used to access protected resources.

The **authorization server** is also responsible for managing **refresh tokens** and handling token lifecycles (expiry, revocation, etc.).

#### Example:
- **Authorization Server**: Google OAuth 2.0 Server

In this scenario, Google’s OAuth 2.0 authorization server handles John’s login and authorization. After John approves DocuEditor’s access request, the **authorization server** issues an **access token** to the client (DocuEditor).

##### Flow:
1. **DocuEditor** (client) redirects John to the **Google OAuth 2.0 authorization server**.
2. John logs in with his Google credentials and grants permission for DocuEditor to access his Google Drive files.
3. The **authorization server** generates an **authorization code** (if using the authorization code flow) or directly issues an **access token** (depending on the flow).
4. DocuEditor exchanges the authorization code for an access token with the authorization server.
5. The access token is then used to interact with the resource server (Google Drive API).

### Complete Example of OAuth 2.0 Flow with Roles:

Let’s put all these roles together to illustrate a full OAuth 2.0 authorization flow with an example.

#### Scenario: 
John (the resource owner) wants to use a document editor app (**DocuEditor**, the client) to edit his Google Drive documents.

##### Step 1: Client Requests Authorization
- John opens DocuEditor and attempts to connect his Google Drive account.
- DocuEditor redirects John to Google’s **Authorization Server** (Google OAuth 2.0) to handle the authorization.

##### Step 2: User Logs in and Grants Permission
- The **Authorization Server** asks John to log in with his Google credentials (if he’s not already logged in).
- After logging in, Google asks John to approve or deny the following permissions:
  - View and edit documents in Google Drive.
- John grants permission for DocuEditor to access his Google Drive documents.

##### Step 3: Authorization Server Issues an Authorization Code
- Google’s **Authorization Server** redirects John back to DocuEditor with an **authorization code**.

##### Step 4: Client Exchanges Authorization Code for Access Token
- DocuEditor (the client) sends a request to Google’s **Authorization Server** to exchange the authorization code for an **access token**.
- The **Authorization Server** issues an access token to DocuEditor.

##### Step 5: Client Uses Access Token to Access Resource Server
- DocuEditor now uses the access token to make requests to the **Google Drive API** (the **Resource Server**).
- For example, DocuEditor might send a request to the Google Drive API to fetch a list of John’s documents:
  
```http
GET /drive/v3/files
Authorization: Bearer <access_token>
```

- The **Resource Server** (Google Drive API) verifies the token and responds with a list of documents that John has stored.

---

### Real-World Example: OAuth 2.0 Roles in "Sign in with GitHub"

Suppose John wants to use **CircleCI**, a Continuous Integration (CI) platform, to build and test code hosted in his GitHub repository. CircleCI requests access to John's GitHub account via OAuth 2.0. Let’s break this down by roles:

- **Resource Owner (John)**: John owns the GitHub repository.
- **Client (CircleCI)**: CircleCI is the third-party service requesting access to John’s GitHub repositories.
- **Resource Server (GitHub API)**: GitHub's API hosts John’s repositories and allows access to them.
- **Authorization Server (GitHub OAuth 2.0)**: GitHub’s OAuth 2.0 server authenticates John and issues an access token to CircleCI.

##### OAuth 2.0 Flow:
1. **Client Requests Authorization**: John clicks "Sign in with GitHub" on CircleCI, which redirects him to GitHub’s OAuth 2.0 authorization server.
2. **Authorization Server Authenticates User**: John logs into GitHub, and GitHub asks him if he wants to allow CircleCI access to his repositories.
3. **Authorization Server Issues Authorization Code**: After John approves the request, GitHub’s authorization server redirects John back to CircleCI with an authorization code.
4. **Client Requests Access Token**: CircleCI exchanges the authorization code with GitHub’s authorization server for an access token.
5. **Client Uses Access Token**: CircleCI uses the access token to interact with the GitHub API to access and clone John’s repositories for building and testing.

---

### Summary of OAuth 2.0 Roles:

1. **Resource Owner**: The user who owns the data and decides whether to grant access (e.g., John owning his Google Drive or GitHub repositories).
2. **Client**: The third-party application requesting access (e.g., DocuEditor or CircleCI).
3. **Resource Server**: The server hosting the protected resources (e.g., Google Drive API or GitHub API).
4. **Authorization Server**: The server that authenticates the resource owner, verifies the client's request, and issues access tokens (e.g., Google OAuth or GitHub OAuth).

These four roles work together in the OAuth 2.0 framework to securely delegate access to resources, ensuring that clients can access or perform actions on behalf of the user without ever knowing the user's password.

![alt text](image-3.png)
![alt text](image-4.png)

OAuth 2.0 offers several **authorization flows**, each designed for different use cases and application types. These flows dictate how access tokens are obtained by clients (applications) and how they are used to access protected resources. Below is a deep dive into the various OAuth 2.0 flows, including their purpose, how they work, and specific examples for each.

### 1. **Authorization Code Flow**

#### Purpose:
The **Authorization Code Flow** is the most secure and widely used OAuth 2.0 flow, particularly for **server-side web applications** where the source code is not publicly exposed. In this flow, the client application does not directly handle the user's credentials. Instead, the flow involves exchanging an authorization code for an access token in a secure back-end environment, protecting the token from being exposed in client-side environments like browsers.

#### How It Works:
1. The user is redirected to the **Authorization Server** to log in and grant access to the client application.
2. Once the user grants access, the **Authorization Server** redirects the user back to the client application with an **authorization code**.
3. The **client application** (on the server side) sends a request to the **Authorization Server** to exchange the authorization code for an **access token**.
4. The client can then use the access token to request resources from the **Resource Server**.

#### Example:
Imagine you're using a server-side web application called **PhotoApp** that wants to access your Google Photos on your behalf.

- **Step 1**: You visit PhotoApp and click **"Sign in with Google"**.
- **Step 2**: PhotoApp redirects you to Google’s **Authorization Server**, where you log in and grant access to your Google Photos.
- **Step 3**: After you authorize, Google redirects you back to PhotoApp with an **authorization code**.
- **Step 4**: PhotoApp’s back-end server sends this authorization code to Google’s **Token Endpoint** and exchanges it for an **access token**.
- **Step 5**: With the access token, PhotoApp can now retrieve and display your photos from Google Photos.

This flow is secure because the access token is never exposed in the browser or client-side code, reducing the risk of token theft.

---

### 2. **Client Credentials Flow**

#### Purpose:
The **Client Credentials Flow** is designed for **machine-to-machine (M2M)** scenarios, where the client itself (not a user) needs to authenticate and access resources. This flow is commonly used by backend services, APIs, or daemons where the **"user"** is effectively the service itself, and no end-user is involved in the interaction.

#### How It Works:
1. The client application authenticates directly with the **Authorization Server** using its own credentials (such as a client ID and secret).
2. The **Authorization Server** returns an **access token** directly to the client.
3. The client uses the access token to interact with the **Resource Server**.

#### Example:
Consider a microservice architecture where a **payment service** needs to authenticate with a **banking API** to process transactions.

- **Step 1**: The payment service uses its **client ID** and **client secret** to request an access token from the **Authorization Server**.
- **Step 2**: The **Authorization Server** authenticates the payment service and issues an **access token**.
- **Step 3**: The payment service uses the access token to call the **banking API** (resource server) and initiate a transaction on behalf of its users.

No user interaction is needed in this flow, making it ideal for backend services or system-to-system communications.

---

### 3. **Resource Owner Password Credentials Flow**

#### Purpose:
The **Resource Owner Password Credentials Flow** is used in scenarios where the client application is **highly trusted** and has direct access to the user's credentials. The client asks the user for their username and password, sends it to the **Authorization Server**, and receives an **access token** in return. This flow should only be used when other flows (such as **Authorization Code Flow**) are not feasible, and it is **not recommended** for most modern applications due to security concerns.

#### How It Works:
1. The user provides their **username** and **password** directly to the client application.
2. The client sends these credentials to the **Authorization Server**.
3. The **Authorization Server** verifies the credentials and issues an **access token**.
4. The client uses the access token to interact with the **Resource Server**.

#### Example:
Consider a **mobile banking app** for a bank where the app itself is fully trusted, and redirection to an external login page is not feasible (e.g., poor mobile network or user experience).

- **Step 1**: The user enters their **username** and **password** directly into the mobile app.
- **Step 2**: The mobile app sends these credentials to the **bank’s authorization server**.
- **Step 3**: The **Authorization Server** verifies the credentials and returns an **access token** to the mobile app.
- **Step 4**: The mobile app uses the access token to retrieve the user’s bank account information from the **Resource Server**.

This flow exposes the user’s credentials to the client, so it’s only used in trusted environments (such as a mobile app provided by the bank itself). It’s not recommended for third-party apps.

---

### 4. **Implicit Flow**

#### Purpose:
The **Implicit Flow** is intended for **client-side** web applications (such as **Single Page Applications** - SPAs) where the client cannot securely store secrets. In this flow, the client directly receives the access token without needing to exchange an authorization code. However, this flow is now **deprecated** in favor of more secure flows (such as the **Authorization Code Flow with PKCE**) due to the risks of exposing access tokens in the browser.

#### How It Works:
1. The user is redirected to the **Authorization Server** to log in and grant access.
2. The **Authorization Server** immediately returns an **access token** to the client application via a URL fragment (rather than an authorization code).
3. The client can then use the access token to interact with the **Resource Server**.

#### Example:
Consider an **Angular SPA** that interacts with an API to show a user's GitHub repositories.

- **Step 1**: The user visits the SPA and clicks **"Login with GitHub"**.
- **Step 2**: The app redirects the user to GitHub’s **Authorization Server**, where they log in and grant the app access to their repositories.
- **Step 3**: GitHub immediately returns an **access token** in the URL fragment, such as `http://localhost/#access_token=abcdef123456`.
- **Step 4**: The client-side app extracts the access token from the URL and uses it to call the GitHub API to retrieve the user’s repositories.

The **Implicit Flow** is faster since it skips the authorization code exchange step. However, it’s insecure because the access token is exposed in the browser's URL, where it can potentially be intercepted or leaked.

---

### 5. **Hybrid Flow**

#### Purpose:
The **Hybrid Flow** combines elements of the **Authorization Code Flow** and the **Implicit Flow**. It is used in cases where both **immediate access** and **long-term access** are required. For example, a client application can receive an **ID token** (for immediate use) along with an **authorization code** that can be exchanged for an **access token** on the back-end.

The hybrid flow is often used in **long-running applications** where the client needs to maintain access over a long period and can securely store secrets for token exchange.

#### How It Works:
1. The user is redirected to the **Authorization Server** and logs in.
2. The **Authorization Server** issues an **ID token** (immediate use) and an **authorization code** (long-term use).
3. The client uses the **ID token** for immediate access and later exchanges the **authorization code** for an **access token** on the back-end for further operations.

#### Example:
Consider a **desktop application** that needs both immediate user authentication and long-term access to a cloud service.

- **Step 1**: The user opens the desktop app and clicks **"Sign in with Google"**.
- **Step 2**: The app redirects the user to Google’s **Authorization Server**. After logging in, Google issues an **ID token** (used immediately to verify the user's identity) and an **authorization code**.
- **Step 3**: The app uses the **ID token** to show the user’s Google profile details right away.
- **Step 4**: The app then exchanges the **authorization code** for an **access token** to retrieve additional resources, such as the user's Google Drive files.

The **Hybrid Flow** allows for both immediate authentication and secure long-term access to resources, making it ideal for applications that need to run continuously or over extended periods.

---

Here’s a summarized table of the OAuth 2.0 flows, outlining their key use cases, security levels, client types, and examples:

| **Flow**                          | **Use Case**                                                            | **Security Level** | **Client Type**            | **Example**                                                         |
|------------------------------------|-------------------------------------------------------------------------|--------------------|----------------------------|---------------------------------------------------------------------|
| **Authorization Code Flow**        | Server-side web apps, source code not exposed                           | High                | Web apps, server-side apps  | A web app accessing a user's Google Photos via server-side code     |
| **Client Credentials Flow**        | Machine-to-machine (M2M), no user interaction                           | High                | Backend services, daemons   | A payment service accessing a banking API to process transactions   |
| **Resource Owner Password Flow**   | Highly trusted apps, direct access to user credentials (not recommended) | Low                 | Native apps, legacy apps    | A mobile banking app where users directly enter their credentials   |
| **Implicit Flow**                  | Single-page apps (SPAs), fast access, no secure secret storage (deprecated) | Low                 | Client-side apps, SPAs       | An Angular app accessing GitHub without backend support             |
| **Hybrid Flow**                    | Long-running apps requiring both immediate and long-term access         | High                | Web apps, native apps       | A desktop app needing both immediate login and long-term API access |

### Key Points:
- **Authorization Code Flow**: Most secure, used in server-side web applications, tokens exchanged in the back end.
- **Client Credentials Flow**: Used in service-to-service communications, no user involved, the client acts as the user.
- **Resource Owner Password Flow**: Direct exchange of user credentials for tokens, only used in trusted environments (not recommended).
- **Implicit Flow**: Quick but less secure, used in client-side apps (SPAs) where the access token is returned immediately (deprecated in favor of Authorization Code with PKCE).
- **Hybrid Flow**: Combines immediate access and long-term access; suitable for applications that need ongoing access to resources.

![alt text](image-5.png)

Let's continue with the **Device Authorization Flow** and the **Authorization Code Flow with PKCE**, two important OAuth 2.0 flows that address specific use cases: **input-constrained devices** and **secure public clients** such as mobile or single-page applications (SPAs) that can't safely store client secrets.

---

### 6. **Device Authorization Flow**

#### Purpose:
The **Device Authorization Flow** (also known as **Device Flow**) is used for devices with limited or no input capabilities, such as smart TVs, game consoles, IoT devices, or printers. These devices usually do not have a keyboard or browser to allow users to log in and authorize access. In this flow, the device prompts the user to log in and authorize access on a separate device (e.g., a phone or computer), where input is easier.

#### How It Works:
1. The user begins the OAuth process on a device with limited input capability (e.g., a smart TV).
2. The device generates a **device code** and provides the user with a **verification URL** and **user code**.
3. The user switches to another device (e.g., their phone or laptop) and navigates to the **verification URL**, where they enter the **user code**.
4. The user logs in on the second device and authorizes the client.
5. The device continuously polls the **Authorization Server** to check if the user has completed the authorization.
6. Once the user authorizes, the **Authorization Server** issues an **access token** to the device.

#### Example:
Imagine setting up a streaming app, **StreamMaster**, on your smart TV:

- **Step 1**: On your TV, **StreamMaster** displays a screen with instructions to go to `streammaster.com/activate` on another device (like your phone) and enter the code `ABC123`.
- **Step 2**: You open your browser on your phone or laptop, go to the URL `streammaster.com/activate`, and enter the code `ABC123`.
- **Step 3**: On the **authorization page**, you log in with your account credentials and grant **StreamMaster** access to your profile and subscriptions.
- **Step 4**: The smart TV continues to **poll the authorization server** to check if you’ve authorized access.
- **Step 5**: Once authorization is granted, **StreamMaster** on the smart TV receives an **access token** and starts streaming content to your TV.

##### Advantages:
- No need to enter complex credentials (username/password) on the limited-input device.
- The user uses a second device that is easier to interact with, such as a mobile phone or computer.

##### Flow Diagram:

```plaintext
+----------------------+                +------------------+                 
| Device with Limited  |                | User's Device     |               
| Input (Smart TV)     |                | (Phone/Laptop)    |               
+----------------------+                +------------------+                 
       |                                      |                               
       |----(1) Request Device Code --------->|                               
       |<---(2) Show User Code + URL ---------|                               
       |                                      |                               
       |                                      |----(3) Enter Code, Log in --->|
       |                                      |<---(4) Grant Authorization --|
       |----(5) Poll Authorization Server -->|                               
       |                                      |                               
       |<---(6) Receive Access Token ---------|                               
```

---

### 7. **Authorization Code Flow with PKCE (Proof Key for Code Exchange)**

#### Purpose:
The **Authorization Code Flow with PKCE** (pronounced "Pixie") is an extension of the **Authorization Code Flow**, designed for **public clients** that cannot securely store client secrets, such as **mobile apps** and **Single Page Applications (SPAs)**. PKCE provides an additional layer of security by ensuring that even if an authorization code is intercepted, it cannot be exchanged for an access token by a malicious party.

PKCE is now **recommended** for all OAuth clients, especially public clients, because it mitigates the risk of **authorization code interception attacks**.

#### How It Works:
1. The client (e.g., mobile app) generates a **code verifier** (a cryptographically random string) and derives a **code challenge** from it (typically by hashing it).
2. The client redirects the user to the **Authorization Server** to log in, passing the **code challenge** along with the request.
3. After the user authorizes access, the **Authorization Server** returns an **authorization code** to the client.
4. The client then sends the authorization code to the **Authorization Server** in exchange for an **access token**, but now also includes the **code verifier**.
5. The **Authorization Server** validates that the **code verifier** matches the **code challenge** before issuing the access token.

#### Example:
Consider a **mobile app**, **FitTrack**, that wants to access a user's Google Fit data on their behalf:

- **Step 1**: **FitTrack** generates a **code verifier** (e.g., `RANDOMSTRING1234`) and a **code challenge** (e.g., a hash of that string: `ABCDHASH`).
- **Step 2**: **FitTrack** redirects the user to Google’s **Authorization Server**, including the **code challenge** in the request.
- **Step 3**: The user logs in and authorizes **FitTrack** to access their Google Fit data.
- **Step 4**: Google returns an **authorization code** to **FitTrack**.
- **Step 5**: **FitTrack** sends the **authorization code** and **code verifier** to Google’s **Token Endpoint**.
- **Step 6**: Google verifies that the **code verifier** matches the **code challenge** and, if valid, issues an **access token** to **FitTrack**.

##### Flow Diagram:

```plaintext
+------------------+                      +--------------------+
| Public Client    |                      | Authorization Server|
| (Mobile App)     |                      | (Google OAuth)      |
+------------------+                      +--------------------+
      |                                           |
      |---(1) Generate Code Verifier + Challenge->|
      |                                           |
      |---(2) Send Code Challenge with Auth Req-->|
      |                                           |
      |<---(3) Get Authorization Code-------------|
      |                                           |
      |---(4) Send Authorization Code + Verifier-->|
      |                                           |
      |<---(5) Receive Access Token ---------------|
```

##### Key Security Features:
- **Code Verifier**: A random string generated by the client.
- **Code Challenge**: A hashed version of the code verifier sent during the initial authorization request.
- **Prevention of Code Interception Attacks**: Even if an attacker intercepts the authorization code, they can’t use it to obtain an access token without the correct **code verifier**, which only the legitimate client possesses.

##### Example of Code Verifier and Challenge:
Let’s say **FitTrack** generates the following random string as the **code verifier**:

```plaintext
RANDOMSTRING1234
```

**FitTrack** then hashes the **code verifier** using SHA-256 to create the **code challenge**:

```plaintext
ABCDHASH
```

When **FitTrack** sends the authorization request to Google, it sends the **code challenge** (`ABCDHASH`). Later, when exchanging the authorization code for an access token, **FitTrack** must send the **code verifier** (`RANDOMSTRING1234`) to prove that it is the legitimate client that initiated the request.

---

### Summary of Additional OAuth Flows:

| **Flow**                                | **Use Case**                                                            | **Security Level** | **Client Type**                  | **Example**                                                        |
|-----------------------------------------|-------------------------------------------------------------------------|--------------------|----------------------------------|--------------------------------------------------------------------|
| **Device Authorization Flow**           | Input-constrained devices (smart TVs, IoT devices)                      | Medium              | Devices with limited input       | A smart TV app logging into a streaming service                    |
| **Authorization Code Flow with PKCE**   | Public clients (mobile apps, SPAs) where client secrets can't be stored | High                | Mobile apps, SPAs (client-side)  | A mobile app accessing a user's Google Fit data                    |

---

### Conclusion:

- **Device Authorization Flow**: Best for **input-constrained devices** (smart TVs, IoT devices), where users enter a code on a separate device to authorize access. The device polls the authorization server to receive an access token once authorization is granted.
- **Authorization Code Flow with PKCE**: Enhances security for **public clients** (such as mobile apps and SPAs) by adding an additional layer of verification (the **code verifier** and **code challenge**) to prevent **authorization code interception attacks**.

Both flows address unique security challenges and are designed to ensure secure authorization in a variety of contexts, from smart devices to mobile applications.

![alt text](image-6.png)

![alt text](image-7.png)

### Overview of JWT Tokens

**JWT** stands for **JSON Web Token**, a compact and self-contained means of securely transmitting information between two parties as a JSON object. JWT is often used in **stateless** authentication systems like **REST APIs**, where each HTTP request is independent and carries the required credentials. JWTs differ from traditional session-based authentication, where a session ID stored in cookies is used to identify a user.

#### Key Points:
- **RFC 7519** is the official specification for JWT, defining how the token should be structured and used.
- **Stateless**: JWT tokens are often used in **stateless** systems (such as HTTP/REST) where each request is **self-contained** and does not rely on server-side sessions.
- **JWT Structure**: JWTs consist of three parts: **Header**, **Payload**, and **Signature**, which are Base64-encoded and separated by periods (`.`).

---

### Structure of a JWT

A JWT is composed of three parts:
1. **Header**: Metadata about the token, including the algorithm used to generate the signature.
2. **Payload**: The actual data, or **claims**, being passed. This typically contains information about the user (such as their ID) and their roles or permissions.
3. **Signature**: A cryptographic signature that ensures the integrity of the token. This is created using the header, payload, and a secret key.

A JWT looks something like this:

```plaintext
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

It consists of three parts separated by dots:
```plaintext
Header.Payload.Signature
```

Let’s break down each part:

---

### 1. **Header**

The header typically consists of two parts:
- **Type of the token**: For JWT, this is always `"JWT"`.
- **Algorithm**: The algorithm used to sign the token, such as `HS256` (HMAC with SHA-256) or `RS256` (RSA with SHA-256).

#### Example Header (in JSON format):
```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

This JSON object is then Base64-encoded to form the first part of the JWT:

```plaintext
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9
```

#### Explanation:
- **"alg"**: The algorithm used to sign the token. In this case, `HS256` is used, which stands for HMAC using SHA-256.
- **"typ"**: The type of the token, which is `JWT` in this case.

---

### 2. **Payload**

The payload contains the **claims**—statements about the user or the system. Claims can be:
- **Registered Claims**: Predefined claims that are optional but recommended. For example:
  - `iss` (issuer): Identifies who issued the JWT.
  - `exp` (expiration): Defines when the token will expire.
  - `sub` (subject): The user or entity that the token refers to.
- **Public Claims**: Custom claims that can be created to carry information about the user (e.g., user ID, roles, permissions).
- **Private Claims**: Claims that are specific to your app, used between two parties sharing information.

#### Example Payload (in JSON format):
```json
{
  "sub": "1234567890",
  "name": "John Doe",
  "admin": true,
  "iat": 1516239022
}
```

In this payload:
- **"sub"**: The subject or user ID (`1234567890`).
- **"name"**: The user's name (`John Doe`).
- **"admin"**: A custom claim that signifies whether the user is an admin (`true`).
- **"iat"**: Issued At, the timestamp when the token was issued.

This payload is also Base64-encoded:

```plaintext
eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ
```

#### JWT Example (so far):
```plaintext
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ
```

---

### 3. **Signature**

The **signature** ensures that the token has not been altered. It is created by taking the encoded header, the encoded payload, and a **secret key**. The header and payload are concatenated and signed using the algorithm specified in the header (in this case, **HS256**).

#### Signature:
```plaintext
HMACSHA256(
  base64UrlEncode(header) + "." + base64UrlEncode(payload),
  secret
)
```

If the secret is `mysecret`, the resulting signature might look something like this (this is just a simulated example):

```plaintext
SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

The signature is then appended to the previous two parts, resulting in a full JWT:

```plaintext
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

---

### JWT in Action with an Example:

#### Use Case: Stateless Authentication in a REST API

Imagine you’re building a REST API for an e-commerce website, and users must log in to view their profile, add items to their cart, or place orders.

#### Flow:
1. **Login**: The user provides their credentials (e.g., email and password) to the API’s authentication endpoint.
   
2. **JWT Creation**:
   - If the credentials are valid, the server creates a **JWT** containing the user's information, such as their user ID and roles (admin, regular user, etc.).
   - The server signs the JWT using a **secret key** and returns it to the client (e.g., the user’s browser or mobile app).

3. **Client Stores the JWT**:
   - The client stores the JWT (typically in **local storage** or **session storage** in a browser, or secure storage in a mobile app).

4. **Subsequent Requests**:
   - For each subsequent request (e.g., fetching the user's profile, adding items to the cart), the client sends the JWT as a **Bearer Token** in the `Authorization` header of the HTTP request.

```http
GET /api/profile HTTP/1.1
Host: api.example.com
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

5. **Server Verifies the JWT**:
   - The server receives the request, extracts the JWT from the `Authorization` header, and **verifies** the token’s integrity and validity using the secret key.
   - If valid, the server processes the request and responds with the required data.

6. **No Server-Side Session**:
   - The server does not need to maintain any session data about the user because all the necessary user information (e.g., their ID, roles) is included in the JWT.
   - This makes the system **stateless**.

#### Example Claims in JWT for E-Commerce:

```json
{
  "sub": "123456",               // User ID
  "name": "Jane Doe",             // User name
  "role": "admin",                // User role
  "cart": [                       // User's shopping cart
    {
      "item_id": "A001",
      "quantity": 2
    },
    {
      "item_id": "B002",
      "quantity": 1
    }
  ],
  "exp": 1625199999               // Expiration timestamp
}
```

---

### Advantages of JWT:

1. **Stateless**: Since the JWT contains all the information needed to authenticate and authorize the user, the server doesn’t need to store session data, making the system **scalable**.
2. **Compact and Portable**: JWTs are compact and can be easily transmitted via HTTP headers or query strings. Their portability makes them ideal for **cross-domain** authentication.
3. **Secure**: JWTs are **signed** to ensure their integrity and authenticity. While the payload is not encrypted by default, the signature ensures that the token has not been tampered with.

### Disadvantages of JWT:

1. **No Inherent Encryption**: By default, JWTs are not encrypted, which means

 anyone with access to the token can read its contents (although it can be encrypted using **JWE**). It's crucial to only send tokens over **HTTPS** to prevent interception.
2. **Token Revocation**: Since the server doesn’t store any session information, revoking tokens can be challenging (e.g., logging out users). You may need to implement mechanisms such as a **token blacklist** to manage token invalidation.

---

### Conclusion:

JWT (JSON Web Tokens) are a secure, self-contained way of transmitting user information in stateless applications. They consist of three parts: the header, the payload (which carries the claims about the user), and the signature, which ensures the integrity of the token. JWTs are widely used for **stateless authentication** in **REST APIs**, enabling scalable and secure applications by removing the need for server-side session management. However, JWTs must be used carefully, ensuring that they are transmitted securely (over HTTPS) and managed effectively (e.g., by handling token expiration and revocation).

![alt text](image-8.png)

### JWT Token Signing

In OAuth 2.0 and other stateless authentication systems, **JWTs (JSON Web Tokens)** are **signed** to ensure their integrity and authenticity. Signing the JWT guarantees that the **contents** of the token (e.g., claims like user ID, roles, etc.) cannot be altered by anyone without detection. The signature ensures that the JWT was issued by a trusted authority and has not been tampered with.

JWT tokens can be signed using two primary methods:
1. **Symmetric encryption** (HMAC-based signing)
2. **Asymmetric encryption** (RSA or ECDSA-based signing)

### Why JWT Signing is Important

The primary reason for signing a JWT is to ensure **integrity** and **authenticity**:
- **Integrity**: No one can modify the JWT’s payload (like user roles or permissions) without invalidating the signature.
- **Authenticity**: The signature allows the recipient (such as a Resource Server) to confirm that the token was issued by a trusted **Authorization Server**.

---

### 1. Symmetric Signing (HMAC)

#### Symmetric Signing Overview

In **symmetric signing**, a **single secret key** is used for both signing and verifying the JWT token. This technique is based on HMAC (Hash-based Message Authentication Code), which combines the token's contents with the secret key to generate a signature.

- The same key is used by both the **Authorization Server** (to sign the token) and the **Resource Server** (to verify the signature).
- The secret key must be **shared** securely between the Authorization Server and the Resource Server.

#### Example: HMAC-SHA256 Signing (HS256)

**HS256** (HMAC with SHA-256) is a common symmetric signing algorithm. Let’s go through the process:

1. **Header**:
   - The JWT **header** specifies the algorithm used. For HMAC-SHA256, the header looks like this:

   ```json
   {
     "alg": "HS256",
     "typ": "JWT"
   }
   ```

   The header is then Base64-encoded.

2. **Payload**:
   - The **payload** contains the claims, such as user information:

   ```json
   {
     "sub": "1234567890",
     "name": "John Doe",
     "admin": true
   }
   ```

   The payload is also Base64-encoded.

3. **Signature**:
   - The **Authorization Server** generates the signature by combining the encoded header and payload, then signing them with a **secret key** using the HMAC algorithm:

   ```plaintext
   HMACSHA256(
     base64UrlEncode(header) + "." + base64UrlEncode(payload),
     secret
   )
   ```

   If the secret key is `mysecret`, the signature might look like this (this is just an example):

   ```plaintext
   SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
   ```

4. **Final JWT**:
   The final JWT looks like this, with the three parts (header, payload, signature) separated by dots:

   ```plaintext
   eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwicm9sZSI6ImFkbWluIn0.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
   ```

#### Verifying Symmetric JWT

- When the **Resource Server** receives the JWT, it uses the **shared secret key** (`mysecret`) to verify the signature.
- If the signature matches the header and payload, the JWT is valid and has not been altered.

#### Key Considerations for Symmetric Signing:
- **Key Sharing**: The secret key must be shared securely between the Authorization Server and the Resource Server, which can be challenging in distributed systems.
- **Single Key**: Since both signing and verification use the same key, both servers must be trusted equally. If the key is compromised, the system’s security is broken.

---

### 2. Asymmetric Signing (RSA or ECDSA)

#### Asymmetric Signing Overview

In **asymmetric signing**, a **key pair** is used:
- The **private key** is used by the **Authorization Server** to sign the JWT.
- The **public key** is shared with the **Resource Server**, which uses it to verify the JWT signature.

This method is more secure in distributed environments because the **private key** remains secret and is never shared, while the **public key** can be safely distributed to any service that needs to verify the JWT.

#### Example: RSA-SHA256 Signing (RS256)

**RS256** (RSA with SHA-256) is a common asymmetric signing algorithm used in JWT. Let’s walk through the process:

1. **Header**:
   - The **header** for RS256 specifies that the RSA algorithm is used for signing:

   ```json
   {
     "alg": "RS256",
     "typ": "JWT"
   }
   ```

   This header is Base64-encoded.

2. **Payload**:
   - The **payload** remains the same as before, containing user information:

   ```json
   {
     "sub": "1234567890",
     "name": "John Doe",
     "admin": true
   }
   ```

   This payload is also Base64-encoded.

3. **Signature**:
   - The **Authorization Server** uses its **private key** to sign the token. The signature is generated by concatenating the encoded header and payload, and then signing it with the private key using the RSA algorithm:

   ```plaintext
   RSASHA256(
     base64UrlEncode(header) + "." + base64UrlEncode(payload),
     privateKey
   )
   ```

   This results in a unique signature, such as:

   ```plaintext
   ABCD1234EFGHI5678... (truncated)
   ```

4. **Final JWT**:
   The final JWT is formed by concatenating the three parts:

   ```plaintext
   eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwicm9sZSI6ImFkbWluIn0.ABCD1234EFGHI5678...
   ```

#### Verifying Asymmetric JWT

- When the **Resource Server** receives the JWT, it retrieves the **public key** (usually from a **JWKS endpoint**) and uses it to verify the signature.
- If the signature is valid, the JWT is considered trustworthy and the claims are accepted.

#### Key Considerations for Asymmetric Signing:
- **Private Key Security**: The **private key** is never shared, making this approach more secure in distributed systems.
- **Public Key Distribution**: The **public key** can be shared openly. The Resource Server only needs access to the public key to verify JWT tokens, which simplifies security.
- **More Secure for Distributed Systems**: Asymmetric signing is ideal for systems where multiple services need to verify tokens, but only one entity (the Authorization Server) signs them.

---

### Example Scenario: JWT Signing in a Distributed System

Let’s consider an e-commerce platform that uses microservices architecture. The **Authorization Server** issues JWT tokens, and multiple **Resource Servers** (such as the order service, user profile service, and payment service) need to verify these tokens.

#### Symmetric Signing (HMAC-SHA256):

In a **symmetric encryption** setup:
- The **Authorization Server** and all the **Resource Servers** would need to share the same secret key to sign and verify JWTs.
- This means that the secret key needs to be distributed securely to every microservice that verifies JWT tokens, which introduces potential risks if the key is compromised.

#### Asymmetric Signing (RSA-SHA256):

In an **asymmetric encryption** setup:
- The **Authorization Server** uses a **private key** to sign the JWT.
- The **Resource Servers** use the corresponding **public key** to verify the JWT.
- The **public key** can be safely distributed to all the microservices, but the **private key** remains securely on the Authorization Server.
- Even if a public key is exposed, it cannot be used to generate valid JWTs.

#### Flow in Asymmetric Signing:

1. **Token Issuance**: 
   - The user logs into the system, and the Authorization Server generates a JWT, signs it with the **private key**, and sends it to the client (e.g., a web app or mobile app).

2. **Token Usage**:
   - The client sends the JWT in the `Authorization: Bearer <token>` header when making requests to various Resource Servers (e.g., order service, payment service).

3. **Token Verification**:
   - Each Resource Server retrieves the **public key** from the Authorization Server (or uses a cached copy).
   - The Resource Server verifies the JWT’s signature using the public key. If the signature is valid, the server processes the request.

---

### Conclusion

**JWT signing** is a critical part of securing stateless authentication systems like OAuth 2.0. The signature ensures that the contents of the JWT cannot be altered without detection. Two main techniques are used for signing JWTs:

1. **

![alt text](image-9.png)

### JWT Token Verification in OAuth 2.0

JWT (JSON Web Token) tokens are commonly used in OAuth 2.0 for secure, stateless authentication and authorization. To ensure that the JWT has not been tampered with, the **Authorization Server** (the server issuing the tokens) digitally signs the token. The **Resource Server** (the server protecting the resources) verifies the token's integrity by checking the signature using the **public key** provided by the Authorization Server.

Let's break down the process step by step to explain how JWT token verification works in detail.

---

### JWT Token Verification Flow

1. **Authorization Server Signs the JWT Token with a Private Key**
   - The **Authorization Server** generates the JWT token when a client application requests authorization (such as logging in or gaining access to protected resources).
   - The Authorization Server includes the **header**, **payload**, and **signature** in the JWT. The **signature** is created using a **private key** that only the Authorization Server knows.
   - The signature ensures that the token has not been altered. If anyone tries to tamper with the JWT (changing roles or user data), the signature verification will fail when the Resource Server checks it.

2. **The Resource Server Requests the Public Key from the Authorization Server**
   - The **Resource Server** receives the JWT token from the client (usually via the `Authorization: Bearer <token>` header in HTTP requests).
   - Since the Resource Server needs to verify that the token is valid and untampered, it uses the **public key** to verify the **signature**.
   - The Resource Server sends a request to the Authorization Server’s **JWKS (JSON Web Key Set) endpoint**, which provides the public key.
   
3. **The Resource Server Verifies the Signature Using the Public Key**
   - Once the Resource Server obtains the **public key**, it uses this key to verify the **signature** of the JWT token. If the signature is valid (i.e., the JWT has not been tampered with), the Resource Server proceeds with processing the request.
   - This signature verification ensures that the JWT token was indeed issued by the trusted Authorization Server and has not been altered.

4. **Public Key Caching by the Resource Server**
   - To avoid repeatedly requesting the public key from the Authorization Server for every incoming request, the Resource Server can **cache the public key**.
   - By caching the public key, the Resource Server can quickly verify JWT tokens in future requests without needing to communicate with the Authorization Server again.
   - Public keys typically have a long lifespan and are rotated infrequently, so caching them locally on the Resource Server is a common practice for performance reasons.

5. **Validation of JWT Tokens Without Additional Requests**
   - Once the Resource Server has the public key cached, it can verify JWT tokens **independently** for subsequent requests, making the system more efficient.
   - The Resource Server verifies the JWT token’s **signature**, checks its **claims** (such as expiration time, issuer, audience), and grants or denies access based on the verified token.

---

### Detailed Example

Let’s take an example where a user logs into a web application (the client), and the app uses OAuth 2.0 to access a protected API on behalf of the user.

#### Step 1: Authorization Server Signs the JWT

When the user logs into the web application:
1. The client (web app) redirects the user to an **Authorization Server** (e.g., Google’s OAuth server).
2. After successful login and authorization, the Authorization Server issues a **JWT token**. This token contains user information (claims) like `user_id`, `roles`, and `iat` (issued at), and is **signed using the private key**.

Here’s what a JWT might look like:

```plaintext
Header (Base64-encoded):
{
  "alg": "RS256",  // Algorithm is RS256, meaning RSA with SHA-256
  "typ": "JWT"     // Type of token
}

Payload (Base64-encoded):
{
  "sub": "1234567890",     // Subject (user ID)
  "name": "John Doe",      // User name
  "admin": true,           // User role
  "iat": 1625063329,       // Issued at (timestamp)
  "exp": 1625066929        // Expiration time
}

Signature:
The header and payload are concatenated and signed using the Authorization Server’s **private key**.
```

#### Step 2: Client Sends the JWT to the Resource Server

After receiving the JWT, the client includes it in the `Authorization` header when making requests to the **Resource Server** (e.g., the API that provides protected resources).

```http
GET /api/protected-resource HTTP/1.1
Host: api.example.com
Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwicm9sZSI6ImFkbWluIn0.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

The **JWT token** (`eyJhbGciOi...`) is sent in the request.

#### Step 3: Resource Server Requests the Public Key

When the Resource Server receives the JWT token, it needs to verify the token’s signature to ensure its validity and integrity.

- The Resource Server makes a request to the **Authorization Server’s JWKS endpoint** (typically something like `https://auth.example.com/.well-known/jwks.json`) to retrieve the **public key** corresponding to the private key used to sign the JWT.
  
The response might look like this (in JSON format):

```json
{
  "keys": [
    {
      "kty": "RSA",
      "kid": "example-key-id",
      "alg": "RS256",
      "use": "sig",
      "n": "modulus",
      "e": "exponent"
    }
  ]
}
```

- **`n` (modulus)** and **`e` (exponent)** are the key components used to construct the public key.
- **`kid`** (Key ID) is used to identify the specific key in case the Authorization Server uses multiple keys.

#### Step 4: Resource Server Verifies the JWT Signature

Using the public key obtained from the **JWKS endpoint**, the Resource Server verifies the **JWT signature**. It checks:
- That the token was signed by the Authorization Server using the corresponding private key.
- That the token has not been tampered with (i.e., the signature matches the payload).

If the signature is valid, the Resource Server proceeds to check the claims in the JWT (such as the expiration time, audience, etc.).

#### Step 5: Caching the Public Key

The **public key** retrieved from the Authorization Server can be **cached** by the Resource Server to avoid making repeated requests to the JWKS endpoint.

- The public key remains valid until it’s rotated (which typically happens on a scheduled basis).
- This caching allows the Resource Server to validate JWT tokens without making additional requests to the Authorization Server for every incoming request.

#### Step 6: Subsequent JWT Verifications

Once the public key is cached, the Resource Server can verify any future JWT tokens by:
1. Verifying the **signature** with the cached public key.
2. Checking claims such as:
   - **Expiration** (`exp`): Ensures the token hasn’t expired.
   - **Issuer** (`iss`): Ensures the token was issued by a trusted Authorization Server.
   - **Audience** (`aud`): Ensures the token is intended for this Resource Server.

If all checks pass, the token is valid, and the Resource Server proceeds with granting access to the requested resource.

---

### Example of JWT Verification

Let’s say a JWT token is issued like this:

```plaintext
eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwicm9sZSI6ImFkbWluIn0.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

Here’s how it will be verified:

1. **Extract the Header and Payload**:
   - Header: `eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9`
   - Payload: `eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwicm9sZSI6ImFkbWluIn0`
   - Signature: `SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c`

2. **Obtain the Public Key** from the Authorization Server.

3. **Verify the Signature**:
   - Using the **public key**, the Resource Server checks if the signature matches the header and payload.
   - If the signature is valid, the token hasn’t been altered.

4. **Check the Claims**:
   - **Expiration**: Ensure the token is not expired (`exp` claim).
   - **Issuer**: Ensure the token was issued by the correct Authorization Server (`iss` claim).
   - **Role

**: Ensure the user has the correct roles (e.g., `admin`).

If all checks pass, the JWT is considered valid.

---

### Benefits of JWT Verification Process:

1. **Security**: The token’s signature ensures that the token has not been tampered with.
2. **Statelessness**: The Resource Server doesn’t need to store session data or contact the Authorization Server for every request. It simply verifies the token using the public key.
3. **Efficiency**: Public key caching allows the Resource Server to verify tokens quickly without querying the Authorization Server repeatedly.
4. **Flexibility**: JWT tokens can be verified by any Resource Server that has the public key, enabling distributed systems where multiple microservices rely on the same token.

---

### Conclusion

JWT verification is a core part of secure, stateless authentication systems. The **Authorization Server** signs the JWT using a private key, and the **Resource Server** verifies the token using the public key. Once the public key is retrieved (typically via a **JWKS endpoint**), the Resource Server can cache it for efficient verification of future requests. This ensures that JWT tokens are valid, tamper-proof, and can be verified without ongoing interaction with the Authorization Server.
![alt text](image-10.png)
### Comparison of OAuth vs HTTP Basic Authentication

**HTTP Basic Authentication** and **OAuth 2.0** are both mechanisms for authenticating users and accessing resources, but they differ significantly in terms of security, implementation, and flexibility. Let’s dive into a detailed comparison, highlighting their differences and providing examples.

---

### 1. **HTTP Basic Authentication**

**HTTP Basic Authentication** is a simple authentication mechanism where the user’s credentials (username and password) are sent in the **Authorization** header of each HTTP request. It’s a basic method for authenticating users but lacks key security features compared to modern protocols like OAuth 2.0.

#### Key Features of HTTP Basic Authentication:
- **Credentials Sent in Every Request**: With HTTP Basic Authentication, the client must send the user’s credentials (typically base64-encoded) with every request to the server.
- **No Encryption**: The credentials are sent **unencrypted** unless the connection uses HTTPS, which means that if someone intercepts the request on an unsecured connection, they can easily decode the credentials.
- **No Authorization Roles**: HTTP Basic Authentication does not have a built-in concept of roles or permissions. It simply authenticates the user and grants access based on the credentials.
- **Simplicity**: It’s straightforward to implement, but it’s limited in terms of security and flexibility.

#### Example of HTTP Basic Authentication:

1. **Client Request**:
   The client sends the user’s credentials in the `Authorization` header with every request:

   ```http
   GET /api/protected-resource HTTP/1.1
   Host: api.example.com
   Authorization: Basic dXNlcjpwYXNzd29yZA==
   ```

   - The credentials `dXNlcjpwYXNzd29yZA==` are base64-encoded. Decoding it results in `user:password`.

2. **Server Response**:
   The server verifies the credentials with each request. If they are valid, it grants access to the resource:

   ```http
   HTTP/1.1 200 OK
   Content-Type: application/json
   ```

#### Security Concerns:
- **Credentials Shared with Every Request**: Each time the client makes a request, it sends the user’s credentials (even for non-sensitive requests), increasing the risk of exposure.
- **No Encryption in HTTP**: When using HTTP (not HTTPS), the credentials are transmitted in plain text and can be intercepted by malicious actors.
- **No Roles or Permissions**: Basic Authentication does not natively handle roles or scopes, so additional server-side logic is required to manage different user roles.

---

### 2. **OAuth 2.0 Authentication**

**OAuth 2.0** is an **authorization framework** designed to grant limited access to resources without exposing user credentials to the resource server. Unlike HTTP Basic, OAuth 2.0 uses **tokens** instead of credentials, which are more secure and flexible.

#### Key Features of OAuth 2.0:
- **Credentials Only Shared Once**: The user’s credentials are only shared with the **Authorization Server** during the login process. The credentials are not sent with every resource request.
- **Authorization Token**: After the user is authenticated, the **Authorization Server** issues an **access token**. The token is passed with each request to the **Resource Server** to access protected resources.
- **No Credentials in Token**: The access token contains no sensitive user credentials. Even if the token is intercepted, it does not expose the user’s password.
- **Scopes and Roles**: OAuth 2.0 defines **scopes** that control the level of access a user or client has to resources. These scopes (representing roles or permissions) are included in the token, allowing for fine-grained access control.

#### Example of OAuth 2.0 Authentication:

1. **User Logs In**:
   The client redirects the user to the **Authorization Server** (e.g., Google, GitHub) for login. The user provides their credentials to the Authorization Server.

2. **Token Issuance**:
   After successful login, the Authorization Server issues an **access token** to the client.

   Example JWT token:
   ```json
   {
     "sub": "1234567890",
     "name": "John Doe",
     "role": "admin",
     "scope": "read:profile write:profile"
   }
   ```

3. **Client Requests Resource**:
   The client includes the **access token** in the `Authorization` header when making requests to the Resource Server:

   ```http
   GET /api/profile HTTP/1.1
   Host: api.example.com
   Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
   ```

4. **Resource Server Verifies Token**:
   The Resource Server verifies the token (using the public key from the Authorization Server) and checks the **scopes** in the token to determine what resources the client can access.

#### Security Benefits:
- **Credentials Not Sent Repeatedly**: The user’s credentials are only sent to the **Authorization Server** once (during login). Afterward, the client sends only the token with resource requests.
- **Access Tokens Instead of Credentials**: The **access token** is passed with each request instead of sensitive credentials. Tokens are usually time-limited and can be revoked.
- **Encryption and Signature**: OAuth 2.0 tokens are typically signed or encrypted, ensuring their integrity and protecting sensitive information.
- **Scopes (Roles and Permissions)**: OAuth tokens contain scopes, allowing the Resource Server to determine what actions the client can perform. This is a key feature that Basic Authentication lacks.

---

### Example Use Cases

#### HTTP Basic Authentication Use Case:

A company runs a **legacy intranet** with a small number of employees accessing internal resources. Since the system is on a secure network and the number of users is small, HTTP Basic Authentication is used for simplicity. Every time an employee requests a resource (e.g., a document), they must provide their credentials.

- **Security Risk**: If the network isn’t fully secure, credentials can be intercepted in transit. Since HTTP Basic Authentication does not manage roles, a separate authorization system would need to be implemented.
- **No Fine-grained Permissions**: All employees have the same access, and there’s no built-in way to assign different permissions to different roles.

#### OAuth 2.0 Use Case:

A social media platform wants to allow third-party apps to access users’ profiles and post on their behalf (with permission). The platform uses OAuth 2.0 for secure, delegated access:

- **Step 1**: A user logs into a third-party app and authorizes it to access their profile and post content.
- **Step 2**: The Authorization Server (the social media platform) issues an **access token** to the app.
- **Step 3**: The third-party app makes requests to the Resource Server (the social media API), passing the access token in the `Authorization` header.
- **Step 4**: The Resource Server verifies the token and checks the **scopes** (e.g., `read:profile`, `write:post`) to determine what actions the third-party app can perform.

---

### Comparison of HTTP Basic vs OAuth 2.0:

| Feature                           | **HTTP Basic Authentication**                          | **OAuth 2.0**                                                     |
|------------------------------------|--------------------------------------------------------|--------------------------------------------------------------------|
| **Credentials Handling**           | User credentials (username & password) are sent with every request | Credentials are only shared with the Authorization Server once. Token is used afterward. |
| **Security**                       | Credentials can be intercepted if not using HTTPS. No encryption by default. | Tokens are signed/encrypted. No sensitive information in token.    |
| **Roles and Permissions**          | No built-in support for roles or scopes.                | Roles and permissions (scopes) are embedded in the token.          |
| **Stateless Authentication**       | Each request sends credentials, which makes it stateless but repetitive. | Stateless. The token carries all the information needed.           |
| **Delegated Authorization**        | Not supported.                                          | OAuth supports delegated authorization (e.g., "Sign in with Google"). |
| **Token Revocation**               | No token revocation.                                    | Access tokens can be revoked or expire, limiting their validity.   |
| **Complexity**                     | Simple to implement but lacks advanced features.        | More complex but highly flexible and secure.                       |

---

### Conclusion

- **HTTP Basic Authentication** is simple but lacks critical security features. It sends credentials in every request, does not support roles or scopes natively, and is vulnerable if not used over HTTPS. It’s suitable for **small-scale or internal applications** with basic security needs but is not ideal for modern, distributed systems.
  
- **OAuth 2.0**, on the other hand, offers **advanced security** through token-based authentication, **delegated authorization**, and **role-based access control** with scopes. It is designed for **distributed systems** where clients need limited access to resources without exposing credentials, making it the go-to choice for securing modern **REST APIs**, **mobile apps**, and **third-party integrations**.



## 003 Introduction to Spring Authorization Server


[spring-authorization-server/reference](https://docs.spring.io/spring-authorization-server/reference/index.html)


```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-authorization-server</artifactId>
</dependency>
```

The dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-authorization-server</artifactId>
</dependency>
```

is part of the Spring ecosystem and is specifically used to create an **OAuth 2.0 Authorization Server** using **Spring Boot**. This dependency enables your Spring Boot application to function as an **Authorization Server**, which is a core component in the **OAuth 2.0** protocol for issuing and managing tokens.

Let’s break this down in detail, explaining what an **Authorization Server** is, how Spring’s OAuth 2.0 Authorization Server works, and how to implement it with examples.

---

### What is an OAuth 2.0 Authorization Server?

The **Authorization Server** is responsible for managing user authentication and authorization in the OAuth 2.0 framework. It’s the central authority that issues **access tokens** and **refresh tokens** to client applications after verifying the identity of the resource owner (the user). These tokens are then used by clients to access protected resources on a **Resource Server** (e.g., API).

Key responsibilities of an Authorization Server:
1. **Authenticate users**: The server authenticates users when they log in.
2. **Authorize clients**: The server grants permission to clients to act on behalf of users.
3. **Issue tokens**: The server generates **access tokens** (and refresh tokens) that the client uses to access protected resources.
4. **Manage scopes**: It defines and enforces **scopes**—the permissions or roles granted to a client.

---

### How Does Spring Boot’s Authorization Server Work?

Spring Boot’s **`spring-boot-starter-oauth2-authorization-server`** makes it easy to set up an **OAuth 2.0 Authorization Server** using Spring Security. This starter dependency includes all the necessary components and configurations to build a fully functional authorization server without writing boilerplate code.

#### Key Features:
- **OAuth 2.0 & OpenID Connect Support**: Supports OAuth 2.0 grant types like Authorization Code, Client Credentials, Refresh Tokens, and optionally supports OpenID Connect for user authentication.
- **Token Issuance**: Manages issuing **JWT** tokens or opaque tokens for client authorization.
- **Customizable Authorization Flows**: Provides flexible configuration to support custom authorization flows.
- **Built-in Security**: Leverages Spring Security to handle authentication and token management.

---

### Step-by-Step Example: Setting Up an OAuth 2.0 Authorization Server

Let’s walk through the process of setting up an **OAuth 2.0 Authorization Server** in a Spring Boot application using this dependency.

#### Step 1: Add the Dependency

First, add the required dependency to your `pom.xml` file:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-authorization-server</artifactId>
</dependency>
```

This pulls in all the required libraries and configurations to build an OAuth 2.0 Authorization Server.

#### Step 2: Basic Configuration

Create a Spring Boot application with the following structure:

```bash
src
 ├── main
 │   ├── java
 │   │   └── com
 │   │       └── example
 │   │           └── authserver
 │   │               └── AuthServerApplication.java
 │   └── resources
 │       └── application.yml
```

#### Step 3: Main Application Class

Create the main application class in `AuthServerApplication.java`:

```java
package com.example.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }
}
```

#### Step 4: Create the Authorization Server Configuration

Now, you need to configure the Authorization Server. You’ll create a configuration class where you define how your authorization server behaves, such as supported grant types, token formats, and client details.

```java
package com.example.authserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class AuthorizationServerConfig {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.formLogin().and().build();
    }
}
```

#### Explanation:
- **OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http)**: This method applies default security configurations for an OAuth 2.0 Authorization Server. It sets up the endpoints required by the OAuth 2.0 protocol, such as `/oauth2/authorize` and `/oauth2/token`.
- **formLogin()**: Enables form-based login for user authentication.

#### Step 5: Configure Clients

OAuth 2.0 clients are applications that need to access protected resources using tokens issued by the Authorization Server. In this step, we define the clients that our Authorization Server will support.

```java
package com.example.authserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;

@Configuration
public class ClientConfig {

    @Bean
    public InMemoryRegisteredClientRepository registeredClientRepository() {
        // Example client configuration
        RegisteredClient registeredClient = RegisteredClient.withId("client")
            .clientId("client-id")
            .clientSecret("{noop}client-secret") // No encoding for simplicity
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .redirectUri("http://localhost:8080/login/oauth2/code/client-id")
            .scope("read")
            .scope("write")
            .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }
}
```

#### Explanation:
- **RegisteredClient**: Represents an OAuth 2.0 client that will interact with the Authorization Server. It includes information like `clientId`, `clientSecret`, supported grant types, redirect URIs, and scopes.
- **InMemoryRegisteredClientRepository**: For simplicity, clients are stored in memory. In a production environment, you would use a database or another persistent store.

#### Step 6: Configure JWT Token Issuance

To issue JWT tokens, you need to configure the token format. You can configure Spring Security to use **JWT tokens** by adding the following configuration:

```java
package com.example.authserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

@Configuration
public class JwtConfig {

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return context -> {
            context.getClaims().claim("customClaim", "customValue");
        };
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoder.withPublicKey(yourPublicKey).build();  // Example
    }
}
```

#### Explanation:
- **OAuth2TokenCustomizer**: Customizes the JWT claims (such as adding additional fields).
- **JwtDecoder**: Decodes and validates the JWT token. You would configure it to use your public key or private key.

#### Step 7: Application Configuration

Add the following configuration to `application.yml` to configure the port and token settings:

```yaml
server:
  port: 9000

spring:
  security:
    oauth2:
      authorizationserver:
        issuer: http://localhost:9000
```

#### Step 8: Test the Authorization Server

Now, you can run the Spring Boot application and test the Authorization Server.

1. **Authorization Endpoint**: To get an authorization code, you can make a request to `/oauth2/authorize`.
   ```http
   GET http://localhost:9000/oauth2/authorize?response_type=code&client_id=client-id&redirect_uri=http://localhost:8080/login/oauth2/code/client-id&scope=read
   ```

2. **Token Endpoint**: To exchange the authorization code for an access token, you can make a request to `/oauth2/token`.
   ```http
   POST http://localhost:9000/oauth2/token
   Authorization: Basic <base64(client-id:client-secret)>
   Content-Type: application/x-www-form-urlencoded

   grant_type=authorization_code&code=<auth_code_received_earlier>&redirect_uri=http://localhost:8080/login/oauth2/code/client-id
   ```

3. **JWT Token**: If everything is set up correctly, you will receive a JWT token as part of the response.

---

### Key Points of Spring Boot OAuth 2.0 Authorization Server:

1. **Centralized Authentication**: This setup allows for a **centralized authentication server** that can issue access tokens to various clients, whether they are web apps, mobile apps, or APIs.
   
2. **Token Management**: You can manage the lifecycle of **access tokens**, **refresh tokens**, and even **JWT** tokens that are used by clients to access resources.

3. **Customizable Scopes**: You can define **scopes** (permissions) for each client. This allows for fine-grained control over which resources

 clients can access and what operations they can perform.

4. **JWT Support**: Spring Security provides built-in support for JWT tokens, which are widely used in modern distributed applications.

5. **Flexible Configuration**: Spring allows you to customize various aspects of the authorization process, including token generation, client configuration, and security settings.

---

### Conclusion

The `spring-boot-starter-oauth2-authorization-server` dependency simplifies the process of setting up an OAuth 2.0 Authorization Server using Spring Boot. By leveraging this starter, you can easily create a secure, robust, and scalable authorization server that manages user authentication, token issuance, and access control for various applications. The Authorization Server handles the **OAuth 2.0 flows** (like Authorization Code and Client Credentials) and **token management** (JWT or opaque tokens) in a secure and customizable way.

## 004 Create New Project

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.3.3</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.wchamara</groupId>
    <artifactId>spring-6-auth-server</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>spring-6-auth-server</name>
    <description>spring-6-auth-server</description>
    <url/>
    <licenses>
        <license/>
    </licenses>
    <developers>
        <developer/>
    </developers>
    <scm>
        <connection/>
        <developerConnection/>
        <tag/>
        <url/>
    </scm>
    <properties>
        <java.version>21</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-oauth2-authorization-server</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```



This is a **Maven POM (Project Object Model)** file that is used to configure a Spring Boot project with an **OAuth 2.0 Authorization Server**. The POM file defines the dependencies, build configurations, and project metadata necessary for building and running the project. Below, I will explain each section of this POM file in detail, highlighting the purpose and function of the dependencies and configurations included.

### Project Structure

1. **Parent Section**:
   ```xml
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>3.3.3</version>
       <relativePath/> <!-- lookup parent from repository -->
   </parent>
   ```
   - This section defines the **parent** of the project, which is the Spring Boot starter parent. It provides default configuration for the Spring Boot application, such as dependency management and plugin configurations. By extending `spring-boot-starter-parent`, your project automatically inherits useful configurations for Spring Boot.
   - **Version**: The Spring Boot version used in this project is `3.3.3`.
   - **Relative Path**: The `relativePath` is empty here, which means Maven will look for the parent in the central repository.

2. **Project Metadata**:
   ```xml
   <groupId>com.wchamara</groupId>
   <artifactId>spring-6-auth-server</artifactId>
   <version>0.0.1-SNAPSHOT</version>
   <name>spring-6-auth-server</name>
   <description>spring-6-auth-server</description>
   ```
   - This section contains basic information about the project:
     - **groupId**: Uniquely identifies the project’s group, often the reverse domain name (e.g., `com.wchamara`).
     - **artifactId**: The name of the application (`spring-6-auth-server`).
     - **version**: The current version of the project (`0.0.1-SNAPSHOT`). The `SNAPSHOT` suffix means that the project is in development and subject to change.
     - **name**: A human-readable name for the project.
     - **description**: A short description of the project.

3. **Properties**:
   ```xml
   <properties>
       <java.version>21</java.version>
   </properties>
   ```
   - **Java version**: This property defines the version of Java that the project will use (`21`). Ensure your development environment and runtime support Java 21.

---

### Dependencies Section

The **dependencies** section defines the libraries required by the project. Spring Boot simplifies dependency management by including a large number of predefined starters that bundle necessary libraries and configurations.

1. **JDBC Starter**:
   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-jdbc</artifactId>
   </dependency>
   ```
   - **spring-boot-starter-jdbc**: This dependency provides Spring’s JDBC support. It includes support for working with relational databases and simplifies database interactions by integrating with Spring’s data access layer.

2. **Spring Security Starter**:
   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-security</artifactId>
   </dependency>
   ```
   - **spring-boot-starter-security**: This brings in Spring Security, which provides authentication and authorization capabilities. This dependency is critical for securing the Authorization Server and handling the security aspects of OAuth 2.0, such as token issuance and validation.

3. **Spring Web Starter**:
   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-web</artifactId>
   </dependency>
   ```
   - **spring-boot-starter-web**: This includes all the necessary components for building a web application. It sets up Spring MVC (for handling HTTP requests) and includes an embedded **Tomcat server** for running the application.

4. **Spring Boot DevTools**:
   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-devtools</artifactId>
       <scope>runtime</scope>
       <optional>true</optional>
   </dependency>
   ```
   - **spring-boot-devtools**: This dependency enables development tools, such as automatic application restarts when code changes are made. It’s designed to improve developer productivity. It is set to **runtime scope** and is **optional**, meaning it’s not included in production builds.

5. **H2 Database**:
   ```xml
   <dependency>
       <groupId>com.h2database</groupId>
       <artifactId>h2</artifactId>
       <scope>runtime</scope>
   </dependency>
   ```
   - **H2 database**: H2 is an in-memory database often used during development and testing. This database doesn’t require a separate database server and runs within the JVM, making it ideal for lightweight and temporary data storage during development.
   - **Runtime Scope**: The H2 dependency is only included at runtime, meaning it is not needed during the compilation phase.

6. **Spring Boot Test Starter**:
   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-test</artifactId>
       <scope>test</scope>
   </dependency>
   ```
   - **spring-boot-starter-test**: This dependency includes everything needed for testing a Spring Boot application, including libraries like JUnit, Mockito, and Spring’s testing framework. It’s set to **test scope**, meaning it’s only used when running tests.

7. **Spring Security Test**:
   ```xml
   <dependency>
       <groupId>org.springframework.security</groupId>
       <artifactId>spring-security-test</artifactId>
       <scope>test</scope>
   </dependency>
   ```
   - **spring-security-test**: This dependency adds support for testing security-related features in Spring applications. It helps simulate different users and roles during tests, allowing you to verify your security configuration works as expected.

8. **OAuth 2.0 Authorization Server**:
   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-oauth2-authorization-server</artifactId>
   </dependency>
   ```
   - **spring-boot-starter-oauth2-authorization-server**: This is the most critical dependency, as it adds support for setting up an **OAuth 2.0 Authorization Server** in your Spring Boot application. This server handles issuing and managing OAuth 2.0 tokens, authenticating users, and supporting authorization grant types like **Authorization Code** and **Client Credentials**.

---

### Build Section

The **build** section specifies how Maven should build the project and includes the necessary plugins.

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

- **spring-boot-maven-plugin**: This plugin allows you to package and run your Spring Boot application directly from the command line. It enables building a **fat JAR** (a JAR that contains all dependencies) and includes helpful features for running the application, building Docker images, or deploying the app.

---

### Example Scenario

Let’s assume you are building a **Spring Boot OAuth 2.0 Authorization Server** for an application where users log in and clients access protected resources using tokens.

#### Flow Example:

1. **Client Application Requests Authorization**:
   - The client (e.g., a web or mobile app) redirects the user to the **Authorization Server** (your Spring Boot app) to log in and grant authorization.
   - The Authorization Server validates the credentials using Spring Security, which was added via the `spring-boot-starter-security` dependency.

2. **Token Issuance**:
   - After successful authentication, the Authorization Server issues an **OAuth 2.0 access token** (possibly a JWT token). The client uses this token to access protected resources hosted on a **Resource Server**.

3. **Token Verification**:
   - When the client sends the access token to the Resource Server (using `Authorization: Bearer <token>`), the Resource Server can verify the token by checking its validity and claims. This might involve checking the **H2 database** if persistent storage for tokens is configured.

#### Testing the Authorization Server:

- The `spring-boot-starter-test` and `spring-security-test` dependencies provide everything needed to create **unit tests** and **integration tests** for your Authorization Server. You can write tests to ensure that:
  - Tokens are issued correctly.
  - Authentication and authorization are functioning properly.
  - Security rules (like role-based access) are enforced correctly.

---

### Conclusion

This POM file sets up a Spring Boot application with an **OAuth 2.0 Authorization Server**. The various dependencies included in the POM allow the application to:
- Secure user authentication and manage access to resources using **OAuth 2.0 tokens**.
- Provide an **embedded H2 database** for development and testing purposes.
- Leverage **Spring Security** to secure the authorization process.
- Enable efficient development using **DevTools** and robust testing with **Spring Boot's testing framework**.

With this setup, you can build a powerful **OAuth 2.0 Authorization Server** that is easy to configure, scalable,

## 005 Add Authorization Server Filter Chain
## 006 Add Default Security Filter Chain

```java
package com.wchamara.spring6authserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());	// Enable OpenID Connect 1.0
        http
                // Redirect to the login page when not authenticated from the
                // authorization endpoint
                .exceptionHandling((exceptions) -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                )
                // Accept access tokens for User Info and/or Client Registration
                .oauth2ResourceServer((resourceServer) -> resourceServer
                        .jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                // Form login handles the redirect to the login page from the
                // authorization server filter chain
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

}

```
This `SecurityConfig` class configures Spring Security for an **OAuth 2.0 Authorization Server** in a Spring Boot application. It defines two `SecurityFilterChain` beans, which are responsible for handling different security rules within the application. The configuration covers both the security of the **Authorization Server** itself and the default security for other parts of the application.

Let's go through the code in detail, breaking down each section and explaining the purpose with examples.

---

### Overview of Key Components

- **Authorization Server**: The **OAuth 2.0 Authorization Server** is responsible for managing client authentication, issuing tokens, and supporting OAuth 2.0 flows such as **Authorization Code** and **Client Credentials**. It is also configured to support **OpenID Connect (OIDC)** for identity management.
- **Security Filter Chain**: The `SecurityFilterChain` defines a chain of security filters that apply to incoming HTTP requests. Each chain is evaluated in order, and the first matching chain is applied to the request.
- **Form Login**: A simple form-based login mechanism is enabled to handle user authentication.

### 1. **First Security Filter Chain: `authorizationServerSecurityFilterChain`**

The first security filter chain is dedicated to securing the **Authorization Server** endpoints, such as `/oauth2/authorize`, `/oauth2/token`, and any other OAuth 2.0 or OpenID Connect endpoints.

```java
@Bean
@Order(1)
public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
        throws Exception {
    // Apply default security configurations for OAuth 2.0 Authorization Server
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

    // Enable OpenID Connect (OIDC) 1.0 support
    http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
            .oidc(Customizer.withDefaults());

    http
            .exceptionHandling((exceptions) -> exceptions
                    .defaultAuthenticationEntryPointFor(
                            new LoginUrlAuthenticationEntryPoint("/login"),
                            new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                    )
            )
            // Enable JWT support for resource server
            .oauth2ResourceServer((resourceServer) -> resourceServer
                    .jwt(Customizer.withDefaults()));

    return http.build();
}
```

#### a. **OAuth2 Authorization Server Default Security**

```java
OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
```
- This method applies default security settings for an OAuth 2.0 Authorization Server. These defaults include setting up:
  - Endpoints for handling authorization (`/oauth2/authorize`), token requests (`/oauth2/token`), and other OAuth 2.0 mechanisms.
  - Configuring security settings for protecting these endpoints and ensuring that only authenticated clients can access them.

#### b. **OpenID Connect (OIDC) 1.0 Support**

```java
http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
        .oidc(Customizer.withDefaults());
```
- **OpenID Connect (OIDC)** is an identity layer built on top of OAuth 2.0. It allows the Authorization Server to act as an **Identity Provider (IdP)**, issuing **ID tokens** along with access tokens. These ID tokens are used to authenticate and identify users across different applications.
- By enabling OIDC, this configuration adds support for OpenID Connect flows (such as obtaining user profile information).

#### c. **Exception Handling: Redirect to Login Page**

```java
http.exceptionHandling((exceptions) -> exceptions
        .defaultAuthenticationEntryPointFor(
                new LoginUrlAuthenticationEntryPoint("/login"),
                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
        )
);
```
- This configuration handles exceptions when users are not authenticated. Specifically, when an unauthenticated user attempts to access an endpoint (e.g., `/oauth2/authorize`), they are redirected to the `/login` page to authenticate.
- The `MediaTypeRequestMatcher(MediaType.TEXT_HTML)` ensures that the redirection only occurs for HTML requests (i.e., when the user is interacting with a browser). For API calls (e.g., JSON or other media types), a different handling mechanism would apply.

#### d. **OAuth 2.0 Resource Server: JWT Support**

```java
http.oauth2ResourceServer((resourceServer) -> resourceServer
        .jwt(Customizer.withDefaults()));
```
- This configures the **OAuth 2.0 Resource Server** to handle **JWT (JSON Web Tokens)** for verifying access tokens. When the Authorization Server issues tokens, it will typically use JWT, and this configuration enables the resource server (or protected endpoints) to validate the JWT tokens.
- **JWT tokens** are signed tokens that contain claims about the user and are used by the client to access protected resources.

### Example:
Imagine you have a **client application** (like a mobile app or web app) that wants to authenticate users and get an **access token** from your Authorization Server. Here’s how the flow works:
1. The user accesses the client application and is redirected to the Authorization Server’s `/oauth2/authorize` endpoint.
2. If the user is not logged in, they are redirected to the `/login` page to authenticate.
3. Once logged in, the Authorization Server issues an access token and possibly an ID token if OpenID Connect is enabled.
4. The client uses the access token to access protected resources on the Resource Server, which verifies the token using JWT.

---

### 2. **Second Security Filter Chain: `defaultSecurityFilterChain`**

This filter chain applies the default security settings for other parts of the application, such as protecting regular endpoints and enabling **form-based login**.

```java
@Bean
@Order(2)
public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
        throws Exception {
    http
            .authorizeHttpRequests((authorize) -> authorize
                    .anyRequest().authenticated()
            )
            // Form login handles the redirect to the login page from the
            // authorization server filter chain
            .formLogin(Customizer.withDefaults());

    return http.build();
}
```

#### a. **Require Authentication for All Requests**

```java
.authorizeHttpRequests((authorize) -> authorize
        .anyRequest().authenticated()
)
```
- This line ensures that **all requests** to the application require authentication. This means that any endpoint in the application will be protected, and the user must be authenticated to access it.
- This configuration works in tandem with the Authorization Server’s security filter chain. After the user authenticates, their session or token will allow them to access other protected endpoints.

#### b. **Form-Based Login**

```java
.formLogin(Customizer.withDefaults());
```
- This enables **form-based login**, where users are presented with a login form to enter their username and password. Spring Security provides a default login page out of the box, but you can customize it if needed.
- The form login flow is tied into the security filter chain so that when a user attempts to access a protected resource, they are redirected to the login page if they are not authenticated.

### Example:
Let’s assume your application has both an OAuth 2.0 Authorization Server and regular user-facing endpoints:
1. When the user tries to access `/profile`, they will be redirected to the login page (due to the `.anyRequest().authenticated()` rule).
2. After logging in via the form, the user is authenticated, and their session is established.
3. Now, they can access any protected resources (e.g., `/profile`, `/dashboard`) without being redirected again.

---

### Complete Flow of the Application

1. **User Authentication via OAuth 2.0**:
   - The client application (like a web app or mobile app) directs the user to the Authorization Server’s `/oauth2/authorize` endpoint.
   - If the user is not authenticated, they are redirected to the login page (`/login`).
   - After successful login, the Authorization Server issues **access tokens** (and possibly **ID tokens** if using OIDC).
   - These tokens are then used by the client to access protected resources.

2. **Accessing Protected Endpoints**:
   - The user can log in through the form-based login for non-OAuth parts of the application, such as a user profile page or dashboard.
   - All requests to the application require authentication (`.anyRequest().authenticated()`), and the user will be redirected to the login page if not authenticated.

3. **JWT Token Validation**:
   - The **Resource Server** component of the application validates any incoming JWT tokens in API requests. If a valid JWT token is presented, the user is allowed to access the protected resources.

---

### Conclusion

This `SecurityConfig` class configures Spring Security for both the **OAuth 2.0 Authorization Server** and the **default application security**. Key features include:
- **OAuth 2.0 Authorization**: Provides secure authorization flows using OAuth 2.0 and OpenID Connect for token issuance and validation.
- **JWT Support**: Configures JWT for token-based security.
- **Form-Based Authentication**: Provides form login for regular users who access other parts of the application.
- **Role of Security Filter Chains**: The `authorizationServerSecurityFilterChain` secures OAuth 2.0 endpoints, while the `defaultSecurityFilterChain` secures the general application endpoints.

By separating security configurations into two distinct filter chains, this setup ensures the proper handling of OAuth 2.0 endpoints while also securing other areas of the application with Spring Security's default mechanisms.

## 007 Create User Details Service

```java
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
```
The provided code defines a Spring Security configuration for managing **user authentication** in a **Spring Boot** application. It uses **in-memory authentication**, which is suitable for testing and development environments. This configuration consists of two beans: one for managing user details (`UserDetailsService`) and one for encoding passwords (`PasswordEncoder`).

Let's explain each part in detail with examples:

### 1. **UserDetailsService Bean**

```java
@Bean
public UserDetailsService userDetailsService() {
    UserDetails userDetails = User.builder()
            .username("user")
            .password(passwordEncoder().encode("password"))
            .roles("USER")
            .build();

    return new InMemoryUserDetailsManager(userDetails);
}
```

#### Explanation:

- **`UserDetailsService`**:
  - The `UserDetailsService` is a core Spring Security interface that defines how the application retrieves user information (like username, password, roles) for authentication purposes.
  - In this case, it uses an **in-memory** implementation (`InMemoryUserDetailsManager`), which stores user details directly in memory. This is useful for development and testing but not suitable for production systems, where a database-backed user store would be preferred.

- **`User.builder()`**:
  - The `User.builder()` method creates a `UserDetails` object. The `UserDetails` object holds the information about the user that Spring Security uses for authentication and authorization.
  - In this example, a single user with the following attributes is defined:
    - **Username**: `"user"`
    - **Password**: `"password"` (which is encoded using a password encoder).
    - **Role**: `"USER"` (which defines the user’s permissions in the application).

- **Password Encoding**:
  - The password is encoded using a `PasswordEncoder` (which is defined as a separate bean, explained later). Encoding passwords is a critical security practice because storing plaintext passwords is highly insecure.
  - In this example, `"password"` is passed through `passwordEncoder().encode("password")`, meaning that the password is hashed before being stored in memory.

- **`InMemoryUserDetailsManager`**:
  - This class implements the `UserDetailsService` interface, managing users in memory.
  - It is initialized with a list of `UserDetails` objects, so when users log in, Spring Security checks the credentials against the stored in-memory user.

#### Example Scenario:
Let’s assume your application is using this configuration. When a user tries to log in with the username `"user"` and password `"password"`, here’s what happens:
1. The application receives the login request.
2. Spring Security uses the `UserDetailsService` to retrieve the user details for `"user"`.
3. The `InMemoryUserDetailsManager` returns the stored user details for `"user"`, including the encoded password.
4. Spring Security then compares the **encoded password** stored in memory with the **encoded password** provided by the user.
5. If the passwords match, the user is authenticated and granted access to the application with the role `"USER"`.

---

### 2. **PasswordEncoder Bean**

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

#### Explanation:

- **`PasswordEncoder`**:
  - A `PasswordEncoder` is responsible for encoding (hashing) passwords before storing them and verifying passwords during login.
  - It is crucial for security, as it ensures that passwords are not stored in plaintext. Instead, they are hashed so that even if the database is compromised, the attacker cannot easily retrieve the original passwords.
  
- **`BCryptPasswordEncoder`**:
  - `BCryptPasswordEncoder` is a widely used implementation of the `PasswordEncoder` interface, based on the **BCrypt hashing function**.
  - **BCrypt** is a one-way hashing function designed specifically for password hashing. It is considered secure because it:
    - Includes a **salt** (a random value) to ensure that even identical passwords result in different hashes, preventing **rainbow table attacks**.
    - Is **slow by design**, meaning it takes a relatively long time to compute the hash, which helps defend against **brute force attacks**.

#### Example of How `BCryptPasswordEncoder` Works:

1. **Password Encoding**:
   - When you define the user with the password `"password"`, the `passwordEncoder().encode("password")` method hashes it before storing it.
   
   Example of a hashed password:
   ```plaintext
   $2a$10$8bJ8aOJdf6W1F3Z7UvZ85.dXNMbFqmqsw1XWYO0Iq8BXnb4XW7fMi
   ```
   This string is the hashed version of `"password"`, which is what will be stored in memory.

2. **Password Verification**:
   - When a user logs in, Spring Security uses the `PasswordEncoder` to compare the encoded (hashed) password with the one provided by the user at login.
   - If the hashed version of the password provided by the user matches the stored hash, the user is authenticated.

#### Example Scenario:
Imagine you’re logging into an application using the username `"user"` and the password `"password"`. Here’s what happens:
1. When you attempt to log in, Spring Security takes the password you provided (`"password"`) and passes it through the `BCryptPasswordEncoder.encode()` method.
2. Spring Security compares the newly hashed password with the hashed password stored in memory (`$2a$10$...`).
3. If the two hashed values match, authentication is successful, and you are granted access to the application as a `"USER"`.

---

### Example Use Case

Let’s build a simple example using this setup:

#### Scenario:
You are developing a Spring Boot application that requires basic login functionality. You want to secure access to certain endpoints and authenticate users with predefined credentials stored in memory for testing purposes.

#### Step-by-Step Flow:

1. **User Requests a Secured Page**:
   - The user navigates to a secured page in the application (e.g., `/dashboard`).
   - Spring Security intercepts the request and checks whether the user is authenticated.

2. **User is Redirected to the Login Page**:
   - If the user is not authenticated, Spring Security redirects them to the login page where they can enter their credentials.

3. **User Provides Credentials**:
   - The user enters the username `"user"` and password `"password"`.

4. **Password Verification**:
   - Spring Security retrieves the user details from the `InMemoryUserDetailsManager`.
   - It compares the provided password (after hashing it with `BCrypt`) with the stored hash.

5. **Authentication Success**:
   - If the passwords match, the user is authenticated and granted access to the secured page with the role `"USER"`.

---

### Benefits of This Approach:

1. **In-Memory User Management**:
   - **Simplicity**: Using `InMemoryUserDetailsManager` is easy to set up and is ideal for testing and development environments.
   - **No Database Required**: You don’t need a database or external storage for user details.

2. **Password Security**:
   - **BCrypt**: Using `BCryptPasswordEncoder` ensures that passwords are hashed securely with random salts, making it resistant to common attacks like rainbow table and brute force attacks.
   - **Best Practices**: This approach aligns with security best practices by avoiding plaintext password storage and using a strong hashing algorithm.

3. **Modular Design**:
   - You can easily switch from in-memory authentication to a database-backed authentication system (e.g., using `JdbcUserDetailsManager` or `UserDetailsService` with a custom database) without changing how passwords are encoded and verified.

---

### Conclusion

This configuration defines a simple **in-memory user authentication** mechanism in Spring Security, using `BCryptPasswordEncoder` for secure password hashing. It allows you to:
- Securely define users with roles in memory for testing or development purposes.
- Hash and validate passwords securely using **BCrypt**.
- Easily transition to more complex authentication mechanisms (like database-backed authentication) without changing the password encoding logic.

By using `BCryptPasswordEncoder`, you ensure that passwords are stored and handled securely, making this setup suitable for building secure, production-grade applications once connected to a proper user storage mechanism (like a database).
## 008 Add Registered Client Repository
```java
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("oidc-client")
                .clientSecret("{noop}secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/oidc-client")
                .postLogoutRedirectUri("http://127.0.0.1:8080/")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

        return new InMemoryRegisteredClientRepository(oidcClient);
    }
```

This method defines a **RegisteredClientRepository** bean in a Spring Boot application, which is responsible for managing **OAuth 2.0 clients** that interact with the **Authorization Server**. Specifically, this setup defines an **OIDC (OpenID Connect) client** that uses the **Authorization Code** and **Refresh Token** grant types to interact with the server for authentication and authorization.

Let’s go step by step to explain the code in detail, along with practical examples.

### What is a Registered Client?

A **Registered Client** is a representation of a third-party application (client) that will interact with your **OAuth 2.0 Authorization Server**. This could be a mobile app, a web app, or any other system that needs to authenticate users and access protected resources on behalf of those users.

In the OpenID Connect (OIDC) flow, the client typically authenticates a user via the Authorization Server, retrieves an **ID token** (for identity verification), and possibly retrieves an **access token** (for accessing resources).

---

### Code Breakdown

```java
@Bean
public RegisteredClientRepository registeredClientRepository() {
    RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("oidc-client")
            .clientSecret("{noop}secret")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .redirectUri("http://127.0.0.1:8080/login/oauth2/code/oidc-client")
            .postLogoutRedirectUri("http://127.0.0.1:8080/")
            .scope(OidcScopes.OPENID)
            .scope(OidcScopes.PROFILE)
            .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
            .build();

    return new InMemoryRegisteredClientRepository(oidcClient);
}
```

---

### 1. **Client Registration (RegisteredClient)**

#### a. **UUID for Client ID**:
```java
RegisteredClient.withId(UUID.randomUUID().toString())
```
- The `RegisteredClient.withId(UUID.randomUUID().toString())` method generates a **unique client identifier**. The `UUID` ensures that each client registered with the Authorization Server has a unique identifier. This ID is used internally by the server to manage and track registered clients.

#### b. **Client ID and Secret**:
```java
.clientId("oidc-client")
.clientSecret("{noop}secret")
```
- **Client ID**: This is the **public identifier** for the client. It is sent along with the authorization request to identify which application is trying to authenticate users. In this case, the client ID is `"oidc-client"`.
  
- **Client Secret**: The client secret (`"secret"`) is used as part of the **authentication** process when the client interacts with the Authorization Server. The secret is only known by the client and the server.
  - The `"{noop}secret"` uses the `{noop}` prefix, which means the client secret is stored **without encoding**. This is fine for development, but in production, you should encode the client secret using something like **BCrypt** for better security.

#### c. **Client Authentication Method**:
```java
.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
```
- This defines how the client will authenticate with the **Authorization Server** when making requests (e.g., for obtaining tokens). In this case, **Client Secret Basic** authentication is used, meaning that the client ID and client secret will be passed via the HTTP `Authorization` header in Base64-encoded format.
  
#### Example:
In a typical OAuth 2.0 interaction, the client sends the following header to authenticate with the server:
```
Authorization: Basic Base64Encode(clientId:clientSecret)
```
For example:
```
Authorization: Basic b2lkYy1jbGllbnQ6c2VjcmV0
```

---

### 2. **Authorization Grant Types**

```java
.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
```

- **Authorization Code Grant Type**: This is the most common OAuth 2.0 flow used by web and mobile applications to obtain an **access token** on behalf of a user. In this flow:
  - The user is redirected to the Authorization Server to log in.
  - After successful login, the client receives an **authorization code**, which it exchanges for an **access token**.
  - This flow ensures that the client never sees the user's credentials directly.
  
- **Refresh Token Grant Type**: This allows the client to request a new **access token** after the original token expires, without requiring the user to log in again. This is especially useful for long-lived sessions or background operations.

#### Example Scenario:
1. A user is logging into an app that uses this **OIDC client**.
2. The client redirects the user to the Authorization Server's login page.
3. After login, the client exchanges the authorization code for an **access token** and a **refresh token**.
4. When the access token expires, the client can use the refresh token to get a new access token without requiring the user to log in again.

---

### 3. **Redirect URIs**

```java
.redirectUri("http://127.0.0.1:8080/login/oauth2/code/oidc-client")
.postLogoutRedirectUri("http://127.0.0.1:8080/")
```

- **Redirect URI**: This is the URI to which the Authorization Server redirects the user after they successfully authenticate. In this case, the user will be redirected to `http://127.0.0.1:8080/login/oauth2/code/oidc-client` after logging in. The client then exchanges the authorization code for an access token at this endpoint.
  
  The redirect URI must exactly match the one registered in the client. If the URI does not match, the authorization request will fail as a security precaution.

- **Post Logout Redirect URI**: This URI is where the user will be redirected after they log out. Once the user logs out from the Authorization Server, they are taken back to `http://127.0.0.1:8080/`.

#### Example Scenario:
- After successful login, the user is redirected to `http://127.0.0.1:8080/login/oauth2/code/oidc-client` where the client receives the authorization code.
- After logging out, the user is redirected to `http://127.0.0.1:8080/`, which could be the homepage of the application.

---

### 4. **Scopes**

```java
.scope(OidcScopes.OPENID)
.scope(OidcScopes.PROFILE)
```

- **OIDC Scopes**: In the context of OpenID Connect (OIDC), **scopes** define what information the client is requesting from the Authorization Server. In this example:
  - **`OidcScopes.OPENID`**: This is a required scope for OpenID Connect, meaning that the client is requesting an **ID token** (in addition to any access tokens).
  - **`OidcScopes.PROFILE`**: This scope allows the client to request the user's profile information (e.g., name, email, etc.).
  
#### Example Scenario:
- When the client requests authentication, it asks for these scopes (`openid` and `profile`).
- If the user consents, the **ID token** issued to the client will include information from the user’s profile, like their name and email.

---

### 5. **Client Settings**

```java
.clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
```

- **Client Settings**: These settings control how the client behaves in the authorization flow.
  - **`requireAuthorizationConsent(true)`**: This setting ensures that the user must explicitly **consent** to the scopes requested by the client during the authorization process. For example, if the client requests access to the user’s profile information, the user will be shown a consent page where they can approve or deny this request.

#### Example Scenario:
When a user logs in, they are shown a screen that lists what information the client is requesting (e.g., "This app wants access to your profile information"). The user must click **Allow** to proceed.

---

### 6. **InMemoryRegisteredClientRepository**

```java
return new InMemoryRegisteredClientRepository(oidcClient);
```

- **InMemoryRegisteredClientRepository**: This is a simple, in-memory store for registered clients. It holds the details of the registered client (`oidcClient`) and provides methods to retrieve the client by its client ID.
  
- **In-Memory Storage**: This approach is suitable for development and testing purposes. In a production environment, you would typically store clients in a database or another persistent store, like `JdbcRegisteredClientRepository`.

#### Example:
In a real-world application, you might store registered clients in a database, allowing multiple applications to register and authenticate users. For now, in-memory storage simplifies development since you don’t need an external database to store clients.

---

### Full Example Scenario: OIDC Flow with Authorization Code and Refresh Tokens

1. **User Visits Application**: A user accesses a client application (e.g., a web app) that uses this registered client to authenticate.
2. **Redirect to Authorization Server**: The client redirects the user to the Authorization Server’s login page. This is the OAuth 2.0 **Authorization Code flow**.
3. **User Logs In**: The user logs in with their credentials (e.g., username and password).
4. **User Consents to Scopes**: The user is

 shown a consent screen, asking them to allow the client to access their profile information.
5. **Authorization Code Issued**: After successful login and consent, the Authorization Server redirects the user back to the client’s **redirect URI**, along with an **authorization code**.
6. **Token Exchange**: The client exchanges the authorization code for an **access token** and a **refresh token**.
7. **Accessing Resources**: The client can now use the access token to request the user’s profile information from a Resource Server.
8. **Token Refresh**: When the access token expires, the client can use the refresh token to request a new access token without requiring the user to log in again.

---

### Conclusion

This code defines an **OIDC client** using Spring Security’s **RegisteredClient** class and stores it in an **in-memory repository**. The client is configured to use the **Authorization Code** flow, with support for **refresh tokens**, and it includes settings for handling user consent. It also supports **OpenID Connect (OIDC)**, allowing the client to request user identity information through scopes like `openid` and `profile`. This setup is ideal for development and testing but can be adapted for production with persistent storage (e.g., a database).


## 009 Create JWK Source

```java
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        }
        catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }
```

This `SecurityConfig` class configures an **OAuth 2.0 Authorization Server** with a **JWT-based** signing mechanism using **RSA keys**. The primary role of this class is to securely sign **JSON Web Tokens (JWT)** issued by the Authorization Server and to provide a way for external systems (such as Resource Servers) to verify these tokens. It does this by creating a **JWKSource** that holds the public key necessary for verifying tokens.

Here’s a detailed breakdown of the code, explaining each component and its role with examples.

---

### Overview of JWT and JWKSource

- **JWT (JSON Web Token)**: A compact, URL-safe token format that is often used in OAuth 2.0 systems for transferring information between parties in a secure, self-contained way. JWTs are commonly used for access tokens and ID tokens.
  
- **JWKSource (JSON Web Key Source)**: In the context of JWT, a **JWKSource** is a set of **JSON Web Keys (JWKs)** that contains the cryptographic keys used to sign and verify JWTs. These keys can be public keys (used for verification) and private keys (used for signing).

- **RSA Encryption**: In this example, **RSA** is used to generate a public/private key pair. The **private key** is used to sign the tokens, while the **public key** is shared via the **JWKSet**, allowing external systems to verify the token signatures.

### Breakdown of the Code

#### 1. **`JWKSource<SecurityContext>` Bean**: Creating the JWK Source

```java
@Bean
public JWKSource<SecurityContext> jwkSource() {
    KeyPair keyPair = generateRsaKey();
    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
    RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
    RSAKey rsaKey = new RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyID(UUID.randomUUID().toString())
            .build();
    JWKSet jwkSet = new JWKSet(rsaKey);
    return new ImmutableJWKSet<>(jwkSet);
}
```

This method defines a **Spring Bean** that returns a **JWKSource**, which is required for JWT signing and verification. It sets up a **public-private key pair** using **RSA encryption**. Let’s break it down:

##### a. **Generate RSA Key Pair**

```java
KeyPair keyPair = generateRsaKey();
RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
```

- This generates an **RSA key pair** using the helper method `generateRsaKey()`. The RSA key pair consists of:
  - **Public Key** (`RSAPublicKey`): This will be used for **verifying** JWT tokens.
  - **Private Key** (`RSAPrivateKey`): This will be used for **signing** JWT tokens.

##### b. **Create RSAKey Object**

```java
RSAKey rsaKey = new RSAKey.Builder(publicKey)
        .privateKey(privateKey)
        .keyID(UUID.randomUUID().toString())
        .build();
```

- An **`RSAKey`** is constructed using the public and private keys. This object is part of the **Nimbus JOSE + JWT** library, which provides tools for working with cryptographic keys, signing, and verifying JWTs.
- **`keyID(UUID.randomUUID().toString())`**: Each key is assigned a unique identifier (`kid`), generated using `UUID`. The key ID helps when verifying tokens, as it allows systems to identify which key was used to sign the token. This is useful when multiple keys are in rotation (for example, during key rollover).

##### c. **Create JWKSet**

```java
JWKSet jwkSet = new JWKSet(rsaKey);
```

- **JWKSet**: This is a collection of **JWK (JSON Web Keys)**. In this case, it contains the single RSA key we just generated. In real-world scenarios, you could have multiple keys in the set, allowing for key rotation or supporting multiple algorithms.

##### d. **Return Immutable JWKSet**

```java
return new ImmutableJWKSet<>(jwkSet);
```

- **ImmutableJWKSet**: This is a **JWKSource** that holds an immutable set of JWKs. Once created, the JWKSet cannot be changed. This is essential for security, as it ensures that the cryptographic keys are not modified at runtime.
- This **JWKSource** is then used by the **Authorization Server** to sign and verify JWT tokens.

#### Example Use Case:

When the Authorization Server issues a JWT token, it uses the **private key** to sign the token. The resulting JWT has three parts:
1. **Header**: Contains information about the signing algorithm (e.g., RS256) and the key ID (`kid`).
2. **Payload**: Contains the claims (e.g., user information, token expiration).
3. **Signature**: Generated using the private key, ensuring the token’s authenticity.

When another service (like a **Resource Server**) receives this JWT, it fetches the **public key** from the **JWKSet** (typically exposed through a JWKS endpoint) and verifies the signature to ensure the token is valid.

---

#### 2. **The `generateRsaKey()` Method**: Generating the RSA Key Pair

```java
private static KeyPair generateRsaKey() {
    KeyPair keyPair;
    try {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);  // Key size of 2048 bits
        keyPair = keyPairGenerator.generateKeyPair();
    }
    catch (Exception ex) {
        throw new IllegalStateException(ex);
    }
    return keyPair;
}
```

##### Key Points:

1. **Key Pair Generation**:
   - The `KeyPairGenerator.getInstance("RSA")` generates an RSA key pair.
   - The key size is set to **2048 bits**, which is a commonly used key length for RSA and is considered secure for most applications.
   
2. **Exception Handling**:
   - If the key generation fails (e.g., due to an issue with the cryptography provider), the method throws an `IllegalStateException`.

3. **Return Key Pair**:
   - The method returns a **KeyPair** object, which contains both the **public key** and the **private key**.

##### Example Scenario:

When an application initializes, the `generateRsaKey()` method is called to generate a new RSA key pair. The **private key** will be kept securely within the Authorization Server and used to sign tokens. The **public key** will be made available to other services via a **JWKS endpoint**.

---

### Practical Example: OAuth 2.0 Authorization Server Flow with RSA Signed JWTs

1. **User Authentication**:
   - A client (e.g., a web or mobile app) sends a user to the **Authorization Server** for authentication. The user logs in, and the Authorization Server issues an **access token** (a JWT) signed using the **private key** from the RSA key pair.

2. **JWT Token Signing**:
   - The JWT token is signed with the **private key**. This ensures the token is tamper-proof. Any modification to the token’s content (e.g., changing claims) would invalidate the signature.

   Example JWT Header:
   ```json
   {
     "alg": "RS256",
     "typ": "JWT",
     "kid": "1234-5678-90ab-cdef"  // Key ID for identifying the key used to sign
   }
   ```

3. **Token Verification (Resource Server)**:
   - The client sends the JWT token to a **Resource Server** (e.g., an API) to access protected resources.
   - The Resource Server retrieves the **public key** from the Authorization Server’s **JWKS endpoint** (which is backed by the `jwkSource()` method).
   - The Resource Server verifies the JWT’s signature using the **public key** from the JWKS. If the signature is valid, the token is considered authentic, and the user is allowed to access the resource.

---

### Why RSA and JWKS?

1. **Security**: The private key is only known to the Authorization Server, ensuring that only the server can sign tokens. The public key is shared via the JWKS, allowing external services (like Resource Servers) to verify the tokens without needing to access the private key.

2. **Key Rotation**: By assigning a **key ID (kid)** to each key, the Authorization Server can rotate keys (i.e., generate new RSA key pairs) without breaking existing JWT tokens. The `kid` ensures that external services can identify which key was used to sign a specific token.

3. **Decoupling of Signing and Verification**: The Authorization Server signs tokens, while multiple Resource Servers can independently verify those tokens using the public key exposed via the JWKS. This separation of duties makes the system more scalable and secure.

---

### Conclusion

This `SecurityConfig` class configures the **RSA-based JWT signing** for an OAuth 2.0 Authorization Server. It generates a public/private key pair using RSA and provides the public key through a **JWKSource** that is used by external services to verify JWT tokens. The private key is kept secure, and only the Authorization Server uses it to sign tokens. This setup ensures that tokens issued by the

 server are secure, tamper-proof, and verifiable by any system that has access to the public key. This configuration is foundational for building secure, token-based systems using OAuth 2.0.

## 010 Create JwtDecoder

```java
@Bean 
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}
```

The `jwtDecoder()` method in this code creates a **JWT decoder** for decoding and validating **JSON Web Tokens (JWTs)** issued by the **OAuth 2.0 Authorization Server**. The method uses the **JWKSource** (JSON Web Key Source) to retrieve the public key needed to verify the token's signature. This is an essential component in any system that needs to validate incoming JWTs to ensure they are legitimate and have not been tampered with.

### What is a JWT Decoder?

A **JWT Decoder** is responsible for:
- **Decoding** the JWT: Breaking down the JWT into its components (header, payload, signature).
- **Validating** the JWT: Ensuring that the JWT is signed correctly and verifying that the claims (such as expiration time) are valid.
- **Verifying the Signature**: Ensuring that the token’s signature was generated by the **private key** of a trusted Authorization Server, using the corresponding **public key**.

---

### Breakdown of the Code

```java
@Bean
public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
    return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
}
```

This method is a Spring **`@Bean`** that configures a **JwtDecoder** using a **JWKSource**. Let’s break it down step-by-step.

---

### 1. **JWKSource<SecurityContext> Parameter**

```java
JWKSource<SecurityContext> jwkSource
```

The `jwtDecoder()` method takes in a **JWKSource** as a parameter. The **JWKSource** holds the public key(s) used to verify JWT signatures. Here’s how it fits in:

- **JWKSource**: A `JWKSource` is a source of JSON Web Keys (JWK). In this case, the JWKSource was previously configured in your `SecurityConfig` class. It holds the **public key** that corresponds to the private key used by the Authorization Server to sign JWTs.
  
- **How the Decoder Uses the JWKSource**: When the JWT Decoder needs to verify a JWT token, it retrieves the **public key** from the JWKSource, based on the **key ID (kid)** in the JWT header. The JWT Decoder uses this public key to verify that the token's signature was created by the Authorization Server’s private key.

---

### 2. **OAuth2AuthorizationServerConfiguration.jwtDecoder()**

```java
return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
```

This line delegates the creation of the `JwtDecoder` to the built-in method provided by **`OAuth2AuthorizationServerConfiguration.jwtDecoder()`**, passing the `JWKSource` as a parameter.

- **`OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource)`**: This method creates a **JwtDecoder** that can:
  - Decode incoming JWT tokens.
  - Verify the signature using the **JWKSource** (the set of public keys).
  - Validate the claims, such as expiration, audience, issuer, and more.

By using the **JWKSource**, the JwtDecoder dynamically retrieves the public key(s) needed to verify JWTs, making the system flexible and capable of handling key rotation.

---

### 3. **JwtDecoder**: What Does It Do?

The **JwtDecoder** is responsible for:
- **Decoding**: Breaking down the JWT token into its three parts: the header, payload, and signature.
- **Signature Verification**: Using the **public key** from the **JWKSource**, the JwtDecoder verifies that the signature was generated using the corresponding **private key**. This ensures that the JWT token was indeed issued by a trusted Authorization Server and has not been tampered with.
- **Claims Validation**: The JwtDecoder also checks the claims in the token to ensure they are valid. For example:
  - **Expiration (`exp`)**: Ensures that the token has not expired.
  - **Issuer (`iss`)**: Verifies that the token was issued by the correct Authorization Server.
  - **Audience (`aud`)**: Checks that the token was intended for the current resource server.

#### Example: Decoding and Validating a JWT

Here’s an example of what happens when a **JwtDecoder** decodes and validates a JWT:

##### Example JWT Token (Before Decoding):
```plaintext
eyJhbGciOiJSUzI1NiIsImtpZCI6IjEyMzQ1In0.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaXNzIjoiYXV0aC1zZXJ2ZXIuY29tIiwiZXhwIjoxNjYwNzI4MDAwfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

A JWT consists of three parts:
1. **Header**:
   ```json
   {
     "alg": "RS256", // Algorithm used for signing
     "kid": "12345"  // Key ID (identifies the key used to sign the token)
   }
   ```

2. **Payload**:
   ```json
   {
     "sub": "1234567890", // Subject (user ID)
     "name": "John Doe",  // User name
     "iss": "auth-server.com", // Issuer (Authorization Server)
     "exp": 1660728000   // Expiration time (Unix timestamp)
   }
   ```

3. **Signature**: This is a cryptographic signature that ensures the token has not been tampered with.

##### JwtDecoder Steps:
1. **Decode**: The JwtDecoder breaks the JWT into its three parts (header, payload, signature).
2. **Retrieve Public Key**: The JwtDecoder uses the `kid` from the header (in this case, `"12345"`) to look up the public key in the **JWKSource** (which was provided by the `jwkSource` bean).
3. **Verify Signature**: The JwtDecoder verifies that the signature was created using the corresponding private key by checking it with the public key.
4. **Validate Claims**: The JwtDecoder checks the claims in the payload:
   - The token has not expired (checks `exp`).
   - The token was issued by the correct Authorization Server (checks `iss`).
   - The token is intended for the right audience (checks `aud`, if included).

If all these checks pass, the token is considered valid, and the user is authenticated.

---

### Real-World Scenario

#### 1. **OAuth 2.0 Authorization Flow**:
   - A client (like a web app) requests an access token by authenticating a user with the Authorization Server.
   - The Authorization Server issues a JWT token, signed with its **private key**. The JWT token contains information such as the user’s identity, expiration time, and the key ID (`kid`), which points to the key that signed the token.

#### 2. **Using the Access Token**:
   - The client sends the JWT access token to a Resource Server (such as an API) to access protected resources.
   - The Resource Server needs to verify that the token is valid before granting access.

#### 3. **Verifying the Token**:
   - The Resource Server uses the **JwtDecoder** (configured in this code) to decode and validate the token.
   - The **JwtDecoder** retrieves the public key from the Authorization Server's **JWKSource** based on the `kid` in the token header.
   - It verifies the token’s signature using the public key and checks the claims (like expiration).
   - If the signature is valid and the claims pass, the Resource Server grants access to the protected resource.

---

### Why Use `JWKSource` and `JwtDecoder`?

1. **Decoupling of Signing and Verification**:
   - The **Authorization Server** uses the private key to sign the JWT tokens.
   - Any **Resource Server** that needs to validate the tokens can use the public key, provided through the **JWKSource**, to verify the signature. This decouples the token signing and verification processes, allowing for scalability.

2. **Key Rotation**:
   - The **JWKSource** can manage multiple keys, allowing the Authorization Server to rotate its keys. The `kid` in the token header tells the Resource Server which public key to use for verification, ensuring smooth key rotation.

3. **Security**:
   - By using the **JwtDecoder** with a public key from a **JWKSource**, the system ensures that only tokens issued and signed by the trusted Authorization Server are accepted, enhancing security.

---

### Conclusion

This method configures a **JwtDecoder** in an OAuth 2.0 Authorization Server or Resource Server, allowing it to decode, verify, and validate **JWT tokens**. The `JWKSource` provides the **public keys** needed to verify the signature of the tokens. This setup ensures that tokens issued by the Authorization Server can be verified by any system that has access to the **JWKSource**, enabling secure, token-based authentication in distributed systems.

## 011 Set Authorization Server Settings

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

}

```

The `authorizationServerSettings()` method in this code creates a **Spring bean** that configures the settings for the **OAuth 2.0 Authorization Server**. This bean provides various configuration options that control the behavior of the authorization server, including the endpoints for handling OAuth 2.0 and OpenID Connect (OIDC) requests, the issuer URL, token settings, and more.

Let's break down the code step by step and explain its purpose, providing examples to illustrate how it works.

### Code Overview

```java
@Bean 
public AuthorizationServerSettings authorizationServerSettings() {
    return AuthorizationServerSettings.builder().build();
}
```

This method creates an instance of `AuthorizationServerSettings`, which is a configuration class provided by Spring Security to set up the core behavior of the **OAuth 2.0 Authorization Server**. In this case, it uses the default settings by calling `.builder().build()`.

---

### What is `AuthorizationServerSettings`?

**`AuthorizationServerSettings`** is a configuration class in Spring Security that allows you to customize the settings of your OAuth 2.0 Authorization Server. It helps you configure critical elements like the authorization endpoints, token endpoints, and other URLs that are used to manage OAuth 2.0 flows and OIDC interactions.

Some of the settings that can be configured include:
- **Authorization Endpoint**: The URL where clients direct users to authenticate and grant authorization.
- **Token Endpoint**: The URL where the authorization server issues access tokens after clients authenticate or provide an authorization code.
- **Issuer**: The URL representing the Authorization Server that issues tokens.
- **JWK Set URL**: The endpoint that provides public keys for verifying JWT tokens.
- **Token Revocation Endpoint**: The URL where clients can revoke tokens.

By using the `AuthorizationServerSettings.builder()` method, you can customize each of these settings or simply use the defaults, as in the example provided.

---

### Breakdown of the Code

#### 1. **`@Bean` Annotation**

```java
@Bean 
```

- **Spring Bean**: The `@Bean` annotation indicates that this method produces a **Spring bean** that will be managed by the Spring container. This bean will hold the configuration settings for the Authorization Server.
- The bean is automatically injected wherever it’s needed in your Spring application. For example, Spring Security will use this bean to configure the OAuth 2.0 Authorization Server.

#### 2. **`AuthorizationServerSettings.builder()`**

```java
return AuthorizationServerSettings.builder().build();
```

- **Builder Pattern**: The `AuthorizationServerSettings.builder()` method provides a builder for the `AuthorizationServerSettings` class. The **builder pattern** allows you to configure various properties of the authorization server in a flexible way.
  
- **`build()` Method**: Calling `build()` creates an instance of `AuthorizationServerSettings` with the configuration defined in the builder. In this case, no specific settings are customized, so the default settings are applied.

#### 3. **What Happens When You Use Default Settings?**

By using the default settings (as done in this example), the **Authorization Server** is automatically configured with the default endpoints for handling OAuth 2.0 requests, including:

- **Authorization Endpoint**: `"/oauth2/authorize"`
- **Token Endpoint**: `"/oauth2/token"`
- **JWK Set Endpoint**: `"/oauth2/jwks"`
- **Token Revocation Endpoint**: `"/oauth2/revoke"`

These default endpoints allow the Authorization Server to function according to the OAuth 2.0 and OpenID Connect (OIDC) specifications.

---

### Real-World Example: OAuth 2.0 Authorization Server Setup

#### 1. **Authorization Endpoint (`/oauth2/authorize`)**

This is the endpoint where the user is redirected to authenticate and grant authorization. For example:
- A client application (such as a web app) sends the user to `http://auth-server.com/oauth2/authorize` to log in.
- Once the user logs in, they are asked to authorize the application to act on their behalf (e.g., to access their profile data).
- If the user consents, the Authorization Server generates an **authorization code** that the client can exchange for an access token.

#### 2. **Token Endpoint (`/oauth2/token`)**

This is the endpoint where clients exchange the **authorization code** (or other grant types, such as client credentials) for an **access token**.
- The client sends the authorization code (or credentials) to `http://auth-server.com/oauth2/token` along with its client ID and secret.
- The Authorization Server verifies the request and issues an access token.

#### 3. **JWK Set Endpoint (`/oauth2/jwks`)**

This endpoint exposes the **public keys** that the Authorization Server uses to sign **JWT tokens**. Resource servers (like APIs) can use these public keys to verify the authenticity of the JWT tokens.
- When a client uses a JWT token to access a protected resource, the resource server retrieves the public key from the **JWK Set Endpoint** to verify the token’s signature.

#### 4. **Token Revocation Endpoint (`/oauth2/revoke`)**

This is the endpoint where clients can revoke tokens that are no longer needed. For example, if a user logs out, the client can revoke the access token to prevent further use.
- The client sends a request to `http://auth-server.com/oauth2/revoke` to invalidate the token, ensuring that it can no longer be used to access protected resources.

---

### Customizing the Authorization Server Settings

If you wanted to customize these settings, you could modify the `AuthorizationServerSettings` like this:

```java
@Bean 
public AuthorizationServerSettings authorizationServerSettings() {
    return AuthorizationServerSettings.builder()
            .issuer("http://auth-server.com")  // Set the issuer URL
            .authorizationEndpoint("/custom-authorize")  // Customize authorization endpoint
            .tokenEndpoint("/custom-token")  // Customize token endpoint
            .jwkSetEndpoint("/custom-jwks")  // Customize JWK Set endpoint
            .build();
}
```

Here’s what’s happening:
- **`issuer("http://auth-server.com")`**: The issuer URL defines the base URL of the Authorization Server. This is included in the **`iss`** claim of JWT tokens, allowing clients to identify where the token was issued from.
- **`authorizationEndpoint("/custom-authorize")`**: You can customize the authorization endpoint (where the user logs in and grants authorization).
- **`tokenEndpoint("/custom-token")`**: You can change the token endpoint URL.
- **`jwkSetEndpoint("/custom-jwks")`**: Customize the endpoint where public keys are exposed.

---

### Example Use Case in an OAuth 2.0 Flow

1. **User Authentication**:
   - A client application (e.g., a mobile or web app) redirects the user to the Authorization Server’s authorization endpoint (`/oauth2/authorize` by default).
   - The user logs in, and the Authorization Server generates an **authorization code**.

2. **Access Token Request**:
   - The client exchanges the authorization code for an **access token** by sending a request to the token endpoint (`/oauth2/token` by default).
   - The Authorization Server verifies the request and issues the access token, which the client can use to access protected resources.

3. **Verifying JWT Token**:
   - The client sends the JWT token to a **Resource Server** to access a protected API.
   - The Resource Server retrieves the public key from the Authorization Server’s **JWK Set Endpoint** (`/oauth2/jwks` by default) and verifies the JWT token’s signature.

4. **Token Revocation**:
   - If the client no longer needs the token (e.g., the user logs out), the client can revoke the token by sending a request to the revocation endpoint (`/oauth2/revoke` by default).

---

### Conclusion

The `authorizationServerSettings()` method in this code creates a bean that configures the core settings for the **OAuth 2.0 Authorization Server**. It defines the URLs for key OAuth 2.0 and OIDC operations, such as token issuance, authorization, and key management. By default, the `AuthorizationServerSettings.builder().build()` method configures the server with default endpoints, but you can customize these settings as needed.

This setup is critical for enabling OAuth 2.0 flows like **Authorization Code** and **Client Credentials**, and for managing secure communication between clients, resource servers, and the Authorization Server.


```java
public final class AuthorizationServerSettings extends AbstractSettings {

	...

	public static Builder builder() {
		return new Builder()
			.authorizationEndpoint("/oauth2/authorize")
			.deviceAuthorizationEndpoint("/oauth2/device_authorization")
			.deviceVerificationEndpoint("/oauth2/device_verification")
			.tokenEndpoint("/oauth2/token")
			.tokenIntrospectionEndpoint("/oauth2/introspect")
			.tokenRevocationEndpoint("/oauth2/revoke")
			.jwkSetEndpoint("/oauth2/jwks")
			.oidcLogoutEndpoint("/connect/logout")
			.oidcUserInfoEndpoint("/userinfo")
			.oidcClientRegistrationEndpoint("/connect/register");
	}

	...

}
```

The `AuthorizationServerSettings` class, which extends the `AbstractSettings`, defines the **endpoints** used by an **OAuth 2.0 Authorization Server**. These settings specify where client applications (such as web or mobile apps) should send requests for authentication, authorization, token issuance, and other OAuth-related tasks. 

In this class, the **builder pattern** is used to configure and customize the behavior of the Authorization Server by defining various key endpoints, including those for authorization, token management, device authorization, and OpenID Connect (OIDC) endpoints.

Let’s break down each endpoint with detailed explanations and examples of their usage in real-world OAuth 2.0 flows.

### Key Endpoints Defined in `AuthorizationServerSettings`

1. **Authorization Endpoint (`/oauth2/authorize`)**

   **Definition**:
   ```java
   authorizationEndpoint("/oauth2/authorize")
   ```

   - This is the **authorization endpoint**, where clients (such as a web app) direct users to authenticate and provide authorization to access resources on their behalf. In the **Authorization Code flow**, users are redirected to this endpoint to log in and consent to granting the client access to their resources.

   **Example**:
   - A web app wants to access a user's profile data from an API. The user is redirected to:
     ```
     http://auth-server.com/oauth2/authorize?client_id=client123&response_type=code&scope=profile&redirect_uri=http://webapp.com/callback
     ```

   - The user logs in, consents to allow the web app access, and the server issues an **authorization code** to the app via the redirect URI.

2. **Device Authorization Endpoint (`/oauth2/device_authorization`)**

   **Definition**:
   ```java
   deviceAuthorizationEndpoint("/oauth2/device_authorization")
   ```

   - This is the **device authorization endpoint**, where device-based applications (such as smart TVs or IoT devices) request authorization. Since such devices may not have full input capabilities, users are directed to another device (like a phone or computer) to grant access.

   **Example**:
   - A user opens a streaming app on their smart TV, and the TV displays a code. The user is instructed to visit:
     ```
     http://auth-server.com/oauth2/device_authorization
     ```

   - The user enters the code on their phone or computer to authorize the TV to access their account.

3. **Device Verification Endpoint (`/oauth2/device_verification`)**

   **Definition**:
   ```java
   deviceVerificationEndpoint("/oauth2/device_verification")
   ```

   - This is the endpoint where users enter a code to verify and authorize device-based applications (such as smart TVs or IoT devices).

   **Example**:
   - Continuing from the previous example, the user enters the code displayed on the TV at the verification URL:
     ```
     http://auth-server.com/oauth2/device_verification?user_code=XYZ123
     ```

   - After successful verification, the Authorization Server grants access to the TV.

4. **Token Endpoint (`/oauth2/token`)**

   **Definition**:
   ```java
   tokenEndpoint("/oauth2/token")
   ```

   - This is the **token endpoint**, where client applications exchange an **authorization code**, client credentials, or other grant types (e.g., refresh tokens) for an **access token**. This endpoint is also used in the **Client Credentials flow**.

   **Example**:
   - A web app exchanges an authorization code for an access token by sending a POST request to:
     ```
     POST http://auth-server.com/oauth2/token
     Content-Type: application/x-www-form-urlencoded
     grant_type=authorization_code&code=abc123&client_id=client123&client_secret=secret&redirect_uri=http://webapp.com/callback
     ```

   - The Authorization Server responds with an access token that the app can use to access protected resources.

5. **Token Introspection Endpoint (`/oauth2/introspect`)**

   **Definition**:
   ```java
   tokenIntrospectionEndpoint("/oauth2/introspect")
   ```

   - This endpoint allows **resource servers** to check the validity of an access token. It returns information about the token, such as whether it is active, its scopes, and its expiration time.

   **Example**:
   - A Resource Server (such as an API) receives an access token from a client and wants to verify if it’s valid. It sends a request to:
     ```
     POST http://auth-server.com/oauth2/introspect
     Content-Type: application/x-www-form-urlencoded
     token=xyz123
     ```

   - The Authorization Server responds with details about the token, such as whether it is active and the scopes granted to it:
     ```json
     {
       "active": true,
       "scope": "profile email",
       "exp": 1617715999,
       "client_id": "client123",
       "username": "user123"
     }
     ```

6. **Token Revocation Endpoint (`/oauth2/revoke`)**

   **Definition**:
   ```java
   tokenRevocationEndpoint("/oauth2/revoke")
   ```

   - This endpoint allows clients to **revoke tokens** (both access and refresh tokens). Revoking a token ensures that it can no longer be used to access protected resources.

   **Example**:
   - A mobile app logs out the user and sends a request to revoke the token:
     ```
     POST http://auth-server.com/oauth2/revoke
     Content-Type: application/x-www-form-urlencoded
     token=xyz123&client_id=client123&client_secret=secret
     ```

   - The Authorization Server revokes the token, preventing further use.

7. **JWK Set Endpoint (`/oauth2/jwks`)**

   **Definition**:
   ```java
   jwkSetEndpoint("/oauth2/jwks")
   ```

   - This is the **JSON Web Key Set (JWKS) endpoint**, where the Authorization Server publishes its **public keys**. Resource Servers use these public keys to verify the signatures of JWT tokens issued by the Authorization Server.

   **Example**:
   - A Resource Server that receives a JWT token from a client fetches the public key to verify the token’s signature:
     ```
     GET http://auth-server.com/oauth2/jwks
     ```

   - The response contains the public keys in JSON Web Key (JWK) format:
     ```json
     {
       "keys": [
         {
           "kid": "1234",
           "kty": "RSA",
           "alg": "RS256",
           "n": "abc123...",
           "e": "AQAB"
         }
       ]
     }
     ```

8. **OIDC Logout Endpoint (`/connect/logout`)**

   **Definition**:
   ```java
   oidcLogoutEndpoint("/connect/logout")
   ```

   - This is the **OpenID Connect (OIDC) logout endpoint**, where users can log out of their session with the Authorization Server. After logging out, they are redirected back to the client application.

   **Example**:
   - A user logs out of their account, and the client sends a logout request:
     ```
     GET http://auth-server.com/connect/logout?id_token_hint=abc123&post_logout_redirect_uri=http://webapp.com
     ```

   - The Authorization Server logs the user out and redirects them back to the client’s logout confirmation page.

9. **OIDC User Info Endpoint (`/userinfo`)**

   **Definition**:
   ```java
   oidcUserInfoEndpoint("/userinfo")
   ```

   - This endpoint provides information about the user (such as profile and email) as part of the **OpenID Connect (OIDC)** flow. After the client obtains an ID token, it can retrieve additional user details by querying this endpoint.

   **Example**:
   - A client requests the user’s profile data:
     ```
     GET http://auth-server.com/userinfo
     Authorization: Bearer xyz123
     ```

   - The Authorization Server responds with user information:
     ```json
     {
       "sub": "user123",
       "name": "John Doe",
       "email": "john@example.com"
     }
     ```

10. **OIDC Client Registration Endpoint (`/connect/register`)**

    **Definition**:
    ```java
    oidcClientRegistrationEndpoint("/connect/register")
    ```

    - This endpoint is used to **dynamically register clients** with the Authorization Server. Clients can register themselves and provide information like the redirect URI and client ID.

    **Example**:
    - A client application dynamically registers itself with the Authorization Server:
      ```
      POST http://auth-server.com/connect/register
      Content-Type: application/json
      {
        "client_name": "New Client",
        "redirect_uris": ["http://webapp.com/callback"]
      }
      ```

    - The Authorization Server registers the client and provides details such as the client ID and secret.

---

### How These Endpoints Work Together in OAuth 2.0 and OIDC

When an application needs to authenticate users or request access to resources, it interacts with the **Authorization Server** using the endpoints defined above. These endpoints ensure that OAuth 2.0 and OpenID Connect (OIDC) flows, such as **Authorization Code Flow**, **Client Credentials Flow**, and **Device Authorization Flow**, work smoothly.

### Example: OAuth 2.0 Authorization Code Flow

1. **Authorization Request**: The client redirects the user to the **authorization endpoint** (`/oauth2/authorize`) to log in and authorize access.
2. **Token Request**: The client exchanges the authorization code for an access token at the **

## 012 Get Authorization Token Using Postman

This string is a **JSON Web Token (JWT)**, which is a compact, URL-safe token format used in **OAuth 2.0** and **OpenID Connect** for secure transmission of information between parties. JWTs consist of three parts:

1. **Header**: Contains metadata about the token, including the signing algorithm and key identifier (`kid`).
2. **Payload**: Contains claims, which are pieces of information about the user or the token itself (e.g., subject, issuer, expiration).
3. **Signature**: Ensures that the token has not been tampered with. It is generated by signing the header and payload with a private key.

In your example, the JWT is base64-encoded, so we'll need to decode it to see the contents of the **header** and **payload**.

### Structure of the JWT

The JWT consists of three parts, separated by dots (`.`):

```plaintext
eyJraWQiOiI0MTZhY2ZiNi1mNDNkLTQyYzAtOTk5ZC1iZTBlYTk2OTRkYTYiLCJhbGciOiJSUzI1NiJ9
.
eyJzdWIiOiJtZXNzYWdpbmctY2xpZW50IiwiYXVkIjoibWVzc2FnaW5nLWNsaWVudCIsIm5iZiI6MTcyNTY3OTYwNywic2NvcGUiOlsibWVzc2FnZS5yZWFkIiwibWVzc2FnZS53cml0ZSJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjkwMDAiLCJleHAiOjE3MjU2Nzk5MDcsImlhdCI6MTcyNTY3OTYwNywianRpIjoiZWQ4NWFmNDItNDc3Ni00YjY2LWI1MTItMDA1ZmFlN2ZlZTZlIn0
.
jmDZft-6PC_vWAmVnuRNs1Zt57fMoeDRNZ1_L_heRtvLPxN0V6n5nFDdaxSb8Ynw_olnBYqxhqPUW4b5rcsazVsyp4SQeKdjHtzX_aCcRwgegVaNnOsoi8NOsWcIyo3PLhtIrrytt7AACVYs4IzrT_qvuT2ww5ujOphEiEczWmlTZxKYiFkvoSSJVBzE7uVdr5OguUWpqvLtTxmdBXlny9cK6AcBnaF_zCLdX_N1c7Ekx9VUpiONTd_j9X2X1VirmDbmgzLRsjcZ7XdnqTeSPFS6zlkipiPgPyc94_Ny61DppsDHpk7hTeCCG_xRrfLGEHyrRTDLxRJflik63P-xqw
```

Each of these parts is base64-encoded and can be decoded separately.

---

### Step 1: Decoding the JWT

#### 1. **Header** (Base64-decoded):

The header contains metadata about the JWT, including the signing algorithm and the key ID (`kid`).

**Encoded Header**:
```plaintext
eyJraWQiOiI0MTZhY2ZiNi1mNDNkLTQyYzAtOTk5ZC1iZTBlYTk2OTRkYTYiLCJhbGciOiJSUzI1NiJ9
```

**Decoded Header** (JSON format):
```json
{
  "kid": "416acfb6-f43d-42c0-999d-be0ea9694da6",
  "alg": "RS256"
}
```

- **`kid` (Key ID)**: `"416acfb6-f43d-42c0-999d-be0ea9694da6"` – This identifies the specific public key that the Resource Server should use to verify the JWT signature. This key is typically part of a **JSON Web Key Set (JWKS)**, which contains the public keys used by the Authorization Server to sign tokens.
- **`alg` (Algorithm)**: `"RS256"` – This specifies that the token is signed using the **RS256** algorithm, which is **RSA with SHA-256**. RSA is an asymmetric encryption algorithm, where the token is signed with a **private key** and verified using a **public key**.

#### 2. **Payload** (Base64-decoded):

The payload contains **claims**, which are pieces of information about the token and the user (or client).

**Encoded Payload**:
```plaintext
eyJzdWIiOiJtZXNzYWdpbmctY2xpZW50IiwiYXVkIjoibWVzc2FnaW5nLWNsaWVudCIsIm5iZiI6MTcyNTY3OTYwNywic2NvcGUiOlsibWVzc2FnZS5yZWFkIiwibWVzc2FnZS53cml0ZSJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjkwMDAiLCJleHAiOjE3MjU2Nzk5MDcsImlhdCI6MTcyNTY3OTYwNywianRpIjoiZWQ4NWFmNDItNDc3Ni00YjY2LWI1MTItMDA1ZmFlN2ZlZTZlIn0
```

**Decoded Payload** (JSON format):
```json
{
  "sub": "messaging-client",
  "aud": "messaging-client",
  "nbf": 1725679607,
  "scope": ["message.read", "message.write"],
  "iss": "http://localhost:9000",
  "exp": 1725679907,
  "iat": 1725679607,
  "jti": "ed85af42-4776-4b66-b512-005fae7fee6e"
}
```

- **`sub` (Subject)**: `"messaging-client"` – This identifies the subject of the token. In this case, the subject is `"messaging-client"`, which likely represents the client application that requested the token.
- **`aud` (Audience)**: `"messaging-client"` – The audience claim indicates the intended recipient of the token. In this case, the token is intended for the **"messaging-client"**.
- **`nbf` (Not Before)**: `1725679607` – This is a Unix timestamp representing the time before which the token is not valid. It ensures that the token cannot be used before a certain time. The timestamp is in seconds since the epoch (Jan 1, 1970).
- **`scope`**: `["message.read", "message.write"]` – This claim represents the **scopes** (or permissions) granted to the token. In this case, the token allows the client to perform both **read** and **write** operations on messages.
- **`iss` (Issuer)**: `"http://localhost:9000"` – This is the **issuer** of the token, which identifies the **Authorization Server** that created the token. The issuer is often an Authorization Server or Identity Provider.
- **`exp` (Expiration Time)**: `1725679907` – This is the **expiration time** of the token, represented as a Unix timestamp. Once this time passes, the token is no longer valid.
- **`iat` (Issued At)**: `1725679607` – This indicates when the token was issued, represented as a Unix timestamp.
- **`jti` (JWT ID)**: `"ed85af42-4776-4b66-b512-005fae7fee6e"` – A unique identifier for this particular token, which can be used to prevent replay attacks (i.e., the same token being used multiple times).

#### 3. **Signature**:

The signature ensures that the token has not been tampered with. It is created by taking the base64-encoded **header** and **payload**, combining them, and signing them with the **private key** using the algorithm specified in the header (`RS256` in this case).

**Encoded Signature**:
```plaintext
jmDZft-6PC_vWAmVnuRNs1Zt57fMoeDRNZ1_L_heRtvLPxN0V6n5nFDdaxSb8Ynw_olnBYqxhqPUW4b5rcsazVsyp4SQeKdjHtzX_aCcRwgegVaNnOsoi8NOsWcIyo3PLhtIrrytt7AACVYs4IzrT_qvuT2ww5ujOphEiEczWmlTZxKYiFkvoSSJVBzE7uVdr5OguUWpqvLtTxmdBXlny9cK6AcBnaF_zCLdX_N1c7Ekx9VUpiONTd_j9X2X1VirmDbmgzLRsjcZ7XdnqTeSPFS6zlkipiPgPyc94_Ny61DppsDHpk7hTeCCG_xRrfLGEHyrRTDLxRJflik63P-xqw
```

This signature is computed by applying the **RS

256** signing algorithm to the base64-encoded header and payload using the private key. The **public key** corresponding to the private key (identified by the `kid` in the header) is used to verify the signature.

---

### Step 2: Explanation with Examples

#### 1. **Authentication and Authorization with OAuth 2.0**

Imagine that a client application (such as a messaging service) needs to access an API to read and write messages on behalf of a user. This client application is registered with the **Authorization Server** and obtains a JWT token that it can use to access the API.

Here’s how the process works:

1. **Client Requests Authorization**: The client (e.g., "messaging-client") requests authorization from the Authorization Server at `http://localhost:9000`. The client requests the `message.read` and `message.write` scopes, which allow it to read and write messages.

2. **Authorization Server Issues Token**: The Authorization Server authenticates the client and issues a JWT token with the requested scopes. This JWT token is signed using the Authorization Server's private key, ensuring that it cannot be tampered with.

3. **Client Uses JWT to Access API**: The client sends the JWT token as part of its request to the messaging API. The API verifies the token's signature by retrieving the Authorization Server's public key (via the **JWK Set** endpoint) and checking that the token was signed correctly.

4. **API Verifies Token and Grants Access**: The API verifies the token's claims (e.g., expiration time, audience, and scopes). If the token is valid, the API allows the client to read and write messages.

#### 2. **Key Concepts:**

- **Scopes**: The token has the scopes `"message.read"` and `"message.write"`, which define the permissions the client has. The API checks these scopes before granting access.
- **Expiration (`exp`)**: The token is only valid until the expiration time (`exp`). After this time, the client must request a new token.
- **Signature Verification**: The API verifies the token's signature using the public key from the JWK Set to ensure the token was not tampered with.

---

### Conclusion

This JWT represents a secure token that can be used by a client application to access a protected resource (e.g., a messaging API). The **header** contains metadata about the signing algorithm and key used to sign the token. The **payload** contains claims such as the subject, issuer, audience, and scopes. The **signature** ensures that the token has not been tampered with, and the **RS256 algorithm** is used for signing. By decoding the token, you can see the claims and understand the context of the token's use in an OAuth 2.0 flow.