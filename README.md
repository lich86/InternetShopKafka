[![My Skills](https://skillicons.dev/icons?i=java,maven,spring,hibernate,mysql,kafka&theme=light)](https://skillicons.dev)

This is a test task involving a system of order processing with four services:

- OrderService
- PaymentService
- ShipmentService
- NotificationService

1. OrderService creates new orders and sends messages to the "new_orders" topic. It listens to the "paid_orders" and "shipped_orders" topics and updates orders accordingly.

2. PaymentService listens to the "new_orders" topic to create new payments. It updates payments using the update() method in its controller and sends messages to the "paid_orders" topic.

3. ShipmentService listens to the "paid_orders" topic to create new shipments. It updates shipments using the update() method in its controller and sends messages to the "shipped_orders" topic.

4. NotificationService listens to the "shipped_orders" topic and sends notifications to customers about their orders.

The goal of this task is to learn how to work with Kafka.

## Technologies:

- Programming Language: Java 17
- Framework: Spring Boot
- Database: MySQL
- Database Migrations: Liquibase
- Project Build: Maven
- Message broker: Kafka