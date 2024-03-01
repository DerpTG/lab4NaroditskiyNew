package org.example;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class PizzaRabbitGet {

    private final static String QUEUE_NAME = "pizzaQueue";
    private final Gson gson = new Gson(); // Gson instance for JSON parsing

    public void startReceiving() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); // Host is set to localhost
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                // Deserialize the JSON string to a Pizza object
                Pizza pizza = gson.fromJson(message, Pizza.class);
                // Use the displayPizzaDetails method to print pizza details
                pizza.displayPizzaDetails();
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
        }
    }
}
