/** Project: Lab 4 Systems Integration Pizza Shop
 * Purpose Details: System Integration Using Flat Files, RabbitMQ, and Web Service/JSON
 * Course: IST 242
 * Author: Felix Naroditskiy
 * Date Developed: 2/21/2024
 * Last Date Changed: 3/4/2024
 * Rev: 1.0

 */

package org.example;

import com.google.gson.Gson;
import java.io.*;
import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        /**
         * Creates a new Pizza object based on user input.
         *
         * @param id The unique identifier for the pizza. Input by the user.
         * @param price The price of the pizza. Input by the user.
         * @param size The size of the pizza (e.g., small, medium, large). Input by the user.
         * @param sauceType The type of sauce for the pizza. Input by the user.
         * @param mainTopping The main topping on the pizza. Input by the user.
         * @param crustThickness The thickness of the pizza crust. Input by the user.
         * @return A new Pizza object instantiated with the provided attributes.
         */
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

        /**
         * @param pizza The Pizza object to be serialized into JSON.
         * @return json The JSON string representation of the Pizza object.
         */
        Gson gson = new Gson();
        String json = gson.toJson(pizza);
        System.out.println("Serialized Pizza JSON:");
        System.out.println(json);

        // Application Type Menu
        while (true) {
            /**
             * @param appChoice The user choice of which app to utilize the pizza json object.
             */
            System.out.println("\nChoose Application Type:");
            System.out.println("1. Flat File Data");
            System.out.println("2. RabbitMQ Message Data");
            System.out.println("3. Web Service");
            System.out.println("4. Exit");
            int appChoice = scanner.nextInt();
            scanner.nextLine();

            if (appChoice == 1) {
                // Handle Flat File Data
                while (true) {
                    /**
                     * Handles user interactions for flat file data operations. Based on the user's choice,
                     * it either sends pizza data to a CSV file, retrieves pizza data from the CSV file, or exits the loop
                     * to return to the main menu. The operations include:
                     *
                     * @param flatFileChoice The user's choice of operation regarding flat file data handling.
                     */
                    System.out.println("\nFlat File Data Options:");
                    System.out.println("1. Send Data");
                    System.out.println("2. Get Data");
                    System.out.println("3. Back");
                    int flatFileChoice = scanner.nextInt();
                    scanner.nextLine();

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
                /**
                 * Handles the RabbitMQ message data operations based on the user's choice. This section initializes
                 * the communication with RabbitMQ for both sending and receiving pizza data serialized as JSON.
                 *
                 * @param receiver The instance of PizzaRabbitGet used to receive messages from RabbitMQ.
                 * @param sender The instance of PizzaRabbitSend used to send the serialized Pizza JSON
                 * to RabbitMQ.
                 * @param json The serialized JSON string of the Pizza object to be sent to RabbitMQ.
                 */
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
                /**
                 * Handles the Web Service operations to serve Pizza data in JSON format.
                 *
                 * @param pizzaWebSend The instance of PizzaWebSend used to start and manage the web server.
                 * @param json The serialized JSON string of the Pizza object to be served by the web server.
                 * @param endpointChoice The user's choice of endpoint to interact with, or to stop the server and go back.
                 */
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

                        if (endpointChoice == 1) {
                            new PizzaWebGet("pizza").callWebService();
                        } else if (endpointChoice == 2) {
                            new PizzaWebGet("csv").callWebService();
                        } else if (endpointChoice == 3) {
                            pizzaWebSend.stopServer();
                            break;
                        } else {
                            System.out.println("Invalid choice. Please select a valid option.");
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