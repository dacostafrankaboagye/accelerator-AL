# Lab Exercises
## Exercise #1: Securing a Spring Boot API with OAuth2 (Authorization Code Grant)
- In this exercise, you will secure a simple Spring Boot API with OAuth2 using the 
Authorization Code Grant flow.
- You will configure Spring Security as a resource server and integrate with a 
public authorization server (e.g., Google, GitHub).
- Users will be redirected to the authorization server's login page to grant 
access. Upon successful authorization, an access token will be returned to 
your application, allowing users to access the secured API endpoints
- Solution
    - [./ex1/securingapiwithoauth2/README.md](./ex1/securingapiwithoauth2/README.md)

## Exercise #2: Accessing User Information with OpenID Connect
- Building upon Exercise #1, you will explore how to use the UserInfo Endpoint 
exposed by the authorization server to retrieve user information (claims) 
associated with the access token.
- You will leverage Spring Security's OIDC support to access the UserInfo 
Endpoint and retrieve user details like name, email, etc
- Solution
    - [./ex2/README.md](./ex2/README.md)


## Exercise #3 (Optional): Custom Authorization Server with Spring Security OAuth2
- (For advanced users) In this optional exercise, you will explore setting up a 
custom authorization server using Spring Security OAuth2.
- You will configure user registration, login functionality, and access token 
issuance within your Spring Security application.
- Solution
    - [./ex2/README.md](./ex2/README.md)