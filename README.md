A spring service application to create a customer, and send a sms using twilo. Messages are sent to kafka/rabbitmq depending on the config set and then sent to twilo api.
The goal is to implement asychronous system where messages are sent to a queue to allow normal process flow for other interactions.

Before running, run the docker-compose.yml to create the containers and run the database schema in customer/read.me
