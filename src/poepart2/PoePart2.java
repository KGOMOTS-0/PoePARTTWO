/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package poepart2;

import java.util.Scanner;

/**
 *
 * @author student
 */
public class PoePart2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to QuickChat.");

        // Basic registration and login for the new project
        System.out.println("\n===== REGISTER =====");

        System.out.print("Enter username: ");
        String username = input.nextLine();

        System.out.print("Enter password: ");
        String password = input.nextLine();

        System.out.print("Enter cellphone (+27XXXXXXXXX): ");
        String cellphone = input.nextLine();

        // Validate registration
        if (!username.contains("_") || username.length() > 5) {
            System.out.println(
                    "Registration failed: username must contain "
                    + "an underscore and be no longer than 5 characters.");
            input.close();
            return;
        }

        if (password.length() < 8
                || !password.matches(".*[A-Z].*")
                || !password.matches(".*[a-z].*")
                || !password.matches(".*\\d.*")
                || !password.matches(".*[^a-zA-Z0-9].*")) {
            System.out.println(
                    "Registration failed: password does not meet "
                    + "the complexity requirements.");
            input.close();
            return;
        }

        if (!cellphone.matches("^\\+27[0-9]{9}$")) {
            System.out.println(
                    "Registration failed: cellphone number is invalid.");
            input.close();
            return;
        }

        System.out.println("Registration was a success.");

        // Login
        System.out.println("\n===== LOGIN =====");

        System.out.print("Enter username: ");
        String loginUsername = input.nextLine();

        System.out.print("Enter password: ");
        String loginPassword = input.nextLine();

        System.out.print("Enter cellphone: ");
        String loginCellphone = input.nextLine();

        boolean loggedIn = loginUsername.equals(username)
                && loginPassword.equals(password)
                && loginCellphone.equals(cellphone);

        if (!loggedIn) {
            System.out.println("Login failed.");
            System.out.println(
                    "You must log in successfully before sending messages.");
            input.close();
            return;
        }

        System.out.println("Login successful.");
        System.out.println("Welcome to QuickChat.");

        // Ask how many messages the user wants to enter
        int numberOfMessages;

        while (true) {
            System.out.print(
                    "How many messages would you like to enter? ");

            try {
                numberOfMessages = Integer.parseInt(input.nextLine());

                if (numberOfMessages >= 0) {
                    break;
                }

                System.out.println("Enter zero or a positive number.");

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }

        int messagesCreated = 0;

        // Main menu runs until the user chooses Quit
        while (true) {

            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");

            String option = input.nextLine();

            switch (option) {

                case "1":

                    if (messagesCreated >= numberOfMessages) {
                        System.out.println(
                                "You have reached your message limit.");
                        break;
                    }

                    System.out.print(
                            "Enter recipient cellphone (+27XXXXXXXXX): ");
                    String recipient = input.nextLine();

                    Message validationMessage =
                            new Message(recipient, "Test message", 1);

                    if (!validationMessage.checkRecipientCell()
                            .equals("Cell phone number successfully captured.")) {
                        System.out.println(
                                validationMessage.checkRecipientCell());
                        break;
                    }

                    System.out.print("Enter your message: ");
                    String messageText = input.nextLine();

                    if (messageText.trim().isEmpty()) {
                        System.out.println("Message cannot be empty.");
                        break;
                    }

                    if (messageText.length() > 250) {
                        System.out.println(
                                "Message exceeds 250 characters by "
                                + (messageText.length() - 250)
                                + ", please reduce the size.");
                        break;
                    }

                    Message newMessage = new Message(
                            recipient, messageText, messagesCreated + 1);

                    System.out.println("\nMessage details:");
                    System.out.println(newMessage.messageDetails());

                    System.out.println("\nChoose an action:");
                    System.out.println("1) Send Message");
                    System.out.println("2) Store Message to send later");
                    System.out.println("3) Disregard Message");
                    System.out.print("Choose an option: ");

                    String action = input.nextLine();

                    try {
                        int choice = Integer.parseInt(action);

                        if (choice < 1 || choice > 3) {
                            System.out.println("Invalid option.");
                            break;
                        }

                        System.out.println(newMessage.SentMessage(choice));

                        if (choice == 1 || choice == 2 || choice == 3) {
                            messagesCreated++;
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Please enter 1, 2 or 3.");
                    }

                    break;

                case "2":
                    System.out.println("Coming Soon.");
                    break;

                case "3":
                    System.out.println("\nThank you for using QuickChat.");

                    Message summary = new Message(
                            "+27821234567", "Summary message", 1);

                    System.out.println(
                            "Total messages sent: "
                            + summary.returnTotalMessages());

                    input.close();
                    return;

                default:
                    System.out.println(
                            "Invalid option. Please choose 1, 2 or 3.");
            }
        }
    }
}
