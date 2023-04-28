# ordersService
Orders Service

## To build
mvn clean install

## To run
mvn clean spring-boot:run

## To test

### Greeting endpoint
curl http://localhost:8080/greeting
curl http://localhost:8080/greeting?name=Joe

### Customers endpoint
To list all customers:
curl http://localhost:8080/customers

To retrieve one customer:
curl http://localhost:8080/customers/1

To create a customer: 
curl -X POST -H "Content-Type: application/json" --json '{"emailAddress":"joe@gmail.com"}' http://localhost:8080/customers

To list orders for a customer: 
curl http://localhost:8080/customers/1/orders

To create an order for a customer: 
curl -X POST -H "Content-Type: application/json" --json '{"productCodeQuantityMap" : {"FRUIT_APPLE" : 3, "FRUIT_ORANGE" : 4}}' http://localhost:8080/customers/1/orders

Expected response when no offer:
{"id":1,"customerId":1,"productCodeQuantityMap":{"FRUIT_APPLE":3,"FRUIT_ORANGE":4},"priceInCents":280}

Expected response when offers (BOGOF on Apples, THREE4TWO on Oranges):
{"id":2,"customerId":1,"productCodeQuantityMap":{"FRUIT_APPLE":3,"FRUIT_ORANGE":4},"priceInCents":195}

### Prices endpoint
To list all prices: 
curl http://localhost:8080/prices

### Orders endpoint
To list all orders: 
curl http://localhost:8080/orders

To retrieve a specific order:
curl http://localhost:8080/orders/1

### Products endpoint
To list all products:
curl http://localhost:8080/products

### Offers endpoint
To list all offers:
curl http://localhost:8080/offers

To create a BOGOF offer on Apples:
curl -X POST -H "Content-Type: application/json" --json '{"code":"BOGOF", "product":{"id": 1}}' http://localhost:8080/offers

To create a THREE4TWO offer on Oranges:
curl -X POST -H "Content-Type: application/json" --json '{"code":"THREE4TWO", "product":{"id": 2}}' http://localhost:8080/offers

### To test Step 3: Store and retrieve orders
curl -X POST -H "Content-Type: application/json" --json '{"emailAddress":"joe@gmail.com"}' http://localhost:8080/customers

curl -X POST -H "Content-Type: application/json" --json '{"code":"BOGOF", "product":{"id": 1}}' http://localhost:8080/offers

curl -X POST -H "Content-Type: application/json" --json '{"code":"THREE4TWO", "product":{"id": 2}}' http://localhost:8080/offers

curl -X POST -H "Content-Type: application/json" --json '{"productCodeQuantityMap" : {"FRUIT_APPLE" : 3, "FRUIT_ORANGE" : 4}}' http://localhost:8080/customers/1/orders

curl http://localhost:8080/orders

curl http://localhost:8080/orders/1

Then check the in-memory store at http://localhost:8080/h2-console with aTester / aPassword
  - select * from ORDERS
  - select * from ORDER_PRODUCT_MAPPING

## H2
Console at http://localhost:8080/h2-console 

Log in with aTester / aPassword defined in application.properties for this POC. 

With prod credentials, you would use environment variables.

## TODO
- Add Spring HATEOAS on products, prices, etc.
- Add a React front-end
- Add Postgresql with Liquibase. Try to use the JPA Buddy plugin to write changelog files from entity beans.
- Unit tests:
  - OrderEndpointTest to write fully. Replicate approach for other endpoints
  - CustomerServiceTest to write fully. Replicate for classes OrderService & PriceService
  - Coverage on PriceEngineService
- Improve/add constraints on all DTOs so we bounce off invalid data at the earliest opportunity.
