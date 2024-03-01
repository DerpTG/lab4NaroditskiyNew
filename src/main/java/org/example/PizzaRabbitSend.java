package org.example;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class PizzaRabbitSend {
    private final static String QUEUE_NAME = "pizzaQueue";
    private String pizzaJson;

    // Constructor accepts the pizza JSON string
    public PizzaRabbitSend(String pizzaJson) {
        this.pizzaJson = pizzaJson;
    }

    // Method to send the pizza JSON to the RabbitMQ queue
    public void sendToQueue() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // Set the host. Adjust as necessary.
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, pizzaJson.getBytes());
            System.out.println(" [x] Sent Pizza JSON: '" + pizzaJson + "'");
        }
    }
}
