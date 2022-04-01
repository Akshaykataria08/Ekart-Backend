# Ekart-Backend
Ecommerce backend applicaiton

Steps to run the application.

1. Clone https://github.com/Akshaykataria08/Ekart-Config
2. Clone the repository.
3. Set spring profile as "local" in environment variables for each project.
4. Create all the necessary databases(Mysql) required by each microservice and API gateway(Get dbs information from the application.properties file). 
5. Start Config Server.
6. Start Eureka Server(Eureka Server connects to Config Server and Config Server will register itself to the Eureka server).
7. Start API Gateway.
8. Start all microservices.
