# spring-integration-producer

This is the producer microservice of a message-driven microservices based on the publish/subscribe model using Spring Integration, Spring Cloud Stream and RabbitMQ message broker. 
It is based on https://piotrminkowski.com/2018/06/15/building-and-testing-message-driven-microservices-using-spring-cloud-stream/

To start the rabbit-mq broker using docker, use the below command:
docker run -d -p 5672:5672 -p 15672:15672 --name my-rabbit rabbitmq:3-management

See # spring-integration-rabbitmq-consumer for the consumer.
