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






In application.yml file, user can select kafka_queue or rabbit_queue to define the
queue type to be used and consumed by Twilo.

Url to run this locally:
localhost:8080/api/v1/customers

raw data: Ensure you fill in the details before running in postman and include country code
{
    "firstName": "",
    "lastName": "",
    "email": "",
    "phoneNumber": "",
    "message": ""
}


Added Prometheus to the metrics to read all API calls.
Ensure to update the token below. After updating the token, you can curl this to get total calls made to the API
using prometheus

curl -s http://localhost:8080/actuator/prometheus \
-H "Authorization: Bearer <token>" \
| grep 'uri="/api/v1/customers"' \
| grep 'status="200"' \
| awk '{print $2}'