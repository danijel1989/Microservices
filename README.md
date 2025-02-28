Microservice project (master branch):

Four separated projects, each representing microservice:

1) Eureka (Discovery service),
2) Api-gateway (spring api gateway),
3) User-service (Microservice representing user management - basic CRUD operations for User entity),
4) Product-service (Microservice representing product management - basic CRUD operation for Product entity).

- In this example I wanted to connect microservices using Eureka discovery, Spring api gateway and Feign client.
  
- As well I've implemented Spring Security JWT layer with the idea to restrict direct access to user-service and 
  product-service and to force user to send requests only through port 8080 (API-GATEWAY).
  
- For communication between user-service and product-service I've used Feign client and RestTemplate.
  
- For each microservice I used separate database.
  User-service with PostgreSQL and product-service with MySql.

Technologies:
Java 17, Spring Boot, relational databases (PostgreSQL, MySql), Spring Security JWT, Maven
