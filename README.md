# Ekart-Backend
Ecommerce backend applicaiton

Steps to run the application.

1. Set spring profile as "local" in environment variables for each project.
2. Create all the necessary databases(MySQL) required by each microservice(You can find DB details in application.yml files in https://github.com/Akshaykataria08/Ekart-Config repo). 
3. Start Config Server.
4. Start Eureka Server(Eureka Server connects to Config Server and Config Server will register itself to the Eureka server).
5. Start API Gateway.
6. Start all microservices.
