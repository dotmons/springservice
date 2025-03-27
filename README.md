A spring service application to create a customer, and send a sms using twilo. Messages are sent to kafka/rabbitmq depending on the config set and then sent to twilo api.
The goal is to implement asychronous system where messages are sent to a queue to allow normal process flow for other interactions.

Before running, run the docker-compose.yml to create the containers and run the database schema in customer/read.me


Details to run:

This is a customer microservice application running on port 8080 with
a postgres database running on port 5432

To run the database here, lunch a client side database for postgres, and connect
using the datasource in application.yml

Implement this:
1:
password: password
url: jdbc:postgresql://localhost:5432/postgres
username: dotmonsscode
Then run this command:

create database customer;
create database fraud;


After running the application for the first time, run this:
insert into user_details_login(id, password, username, userrole)
values(1,'$2a$10$abcdefghijklmnopqrstuu2njjerFUdKeNqVoGia/slSqhJQ.vuAy' 'username1', 1);



To login:
1. Run in postman
localhost:8080/api/v1/customers/auth/userLogin
header:
    username: username1
    password: password1

A token is generated to be used during the lifetime of the application.


In application.yml file, user can select kafka_queue or rabbit_queue to define the
queue type to be used and consumed by Twilo.

To run customer service:
Url to run this locally:
localhost:8080/api/v1/customers

Run in postman
raw data: Ensure you fill in the details before running in postman and include country code

Go to authorization and select bearer token. Then fill in the token above to run
{
"firstName": "",
"lastName": "",
"email": "",
"phoneNumber": "",
"message": ""
}