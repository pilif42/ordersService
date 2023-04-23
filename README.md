# ordersService
Orders Service

## To build
mvn clean install

## To run
mvn clean spring-boot:run

## To test

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

### Prices endpoint
To list all prices: curl http://localhost:8080/prices

### Orders endpoint
To list all orders: curl http://localhost:8080/orders

## H2
Console at http://localhost:8080/h2-console 

Log in with aTester / aPassword defined in application.properties for this POC. 

With prod credentials, you would use environment variables.

## TODO
- Unit tests:
  - OrderEndpointTest to write fully. Replicate approach for other endpoints
  - CustomerServiceTest to write fully. Replicate for classes OrderService & PriceService
- Constraints on OrderDto
- Add Spring HATEOAS
