package org.example;

import com.google.gson.Gson;
import java.io.*;
import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Pizza Creation
        System.out.println("Pizza Creation:");
        System.out.println("Enter Pizza ID of the pizza:");
        String id = scanner.nextLine();
        System.out.println("Enter price of the pizza:");
        String price = scanner.nextLine();
        System.out.println("Enter size of the pizza:");
        String size = scanner.nextLine();
        System.out.println("Enter sauce type:");
        String sauceType = scanner.nextLine();
        System.out.println("Enter main topping:");
        String mainTopping = scanner.nextLine();
        System.out.println("Enter crust thickness:");
        String crustThickness = scanner.nextLine();
        Pizza pizza = new Pizza(id, price, size, sauceType, mainTopping, crustThickness);

        // Serialize Pizza object to JSON using Gson
        Gson gson = new Gson();
        String json = gson.toJson(pizza);
        System.out.println("Serialized Pizza JSON:");
        System.out.println(json);

        // Application Type Menu
        while (true) {
            System.out.println("\nChoose Application Type:");
            System.out.println("1. Flat File Data");
            System.out.println("2. RabbitMQ Message Data");
            System.out.println("3. Web Service");
            System.out.println("4. Exit");
            int appChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (appChoice == 1) {
                // Handle Flat File Data
                while (true) {
                    System.out.println("\nFlat File Data Options:");
                    System.out.println("1. Send Data");
                    System.out.println("2. Get Data");
                    System.out.println("3. Back");
                    int flatFileChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (flatFileChoice == 1) {
                        PizzaFlatSend.sendPizzaData(json,"pizza_data.csv");
                    } else if (flatFileChoice == 2) {
                        List<Pizza> pizzas = PizzaFlatGet.getPizzaData("pizza_data.csv");
                        PizzaFlatGet.printPizzaData(pizzas);
                    } else if (flatFileChoice == 3) {
                        break; // Exit the while loop for handling Flat File Data
                    } else {
                        System.out.println("Invalid choice.");
                    }

                }
            } else if (appChoice == 2) {
                /// Start receiving messages from RabbitMQ
                PizzaRabbitGet receiver = new PizzaRabbitGet();
                System.out.println("Starting RabbitMQ receiver...");
                try {
                    receiver.startReceiving();
                } catch (Exception e) {
                    System.err.println("Failed to receive messages from RabbitMQ: " + e.getMessage());
                    e.printStackTrace();
                }

                // Send pizza JSON to RabbitMQ
                System.out.println("Sending Pizza JSON to RabbitMQ...");
                PizzaRabbitSend sender = new PizzaRabbitSend(json);
                try {
                    sender.sendToQueue();
                    System.out.println("Pizza JSON sent to RabbitMQ successfully.");
                } catch (Exception e) {
                    System.err.println("Failed to send Pizza JSON to RabbitMQ: " + e.getMessage());
                    e.printStackTrace();
                }

                // Wait for user input to proceed, ensuring that messages can be consumed
                System.out.println("RabbitMQ operations completed. Press any key to continue...");
                scanner.nextLine();

                // Close the receiver to clean up resources
                try {
                    receiver.stopReceiving();
                } catch (Exception e) {
                    System.err.println("Failed to stop the RabbitMQ receiver: " + e.getMessage());
                    e.printStackTrace();
                }

            } else if (appChoice == 3) {
                // Start the Web Service to serve Pizza JSON only once
                PizzaWebSend pizzaWebSend = new PizzaWebSend(json);
                try {
                    pizzaWebSend.startServer();
                    System.out.println("Web service started successfully.");

                    while (true) {
                        // Allow user to choose the endpoint to interact with or go back
                        System.out.println("\nSelect the endpoint to send a request:");
                        System.out.println("1. /pizza");
                        System.out.println("2. /csv");
                        System.out.println("3. Go back");
                        int endpointChoice = scanner.nextInt();
                        scanner.nextLine();

                        switch (endpointChoice) {
                            case 1:
                                new PizzaWebGet("pizza").callWebService();
                                break;
                            case 2:
                                new PizzaWebGet("csv").callWebService();
                                break;
                            case 3:
                                break;
                            default:
                                System.out.println("Invalid choice. Please select a valid option.");
                                break;
                        }
                        if (endpointChoice == 3) {
                            pizzaWebSend.stopServer();
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Failed to start the web service.");
                    e.printStackTrace();
                }
            } else if (appChoice == 4) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid application type choice.");
            }
        }
        scanner.close();
    }
}