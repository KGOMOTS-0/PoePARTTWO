/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poepart2;

/**
 *
 * @author Student
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

public class Message {

    private String messageID;
    private String recipient;
    private String message;
    private String messageHash;
    private int messageNumber;

    private static int totalMessages = 0;
    private static final List<Message> sentMessages = new ArrayList<>();
    private static final List<Message> storedMessages = new ArrayList<>();

    public Message(String recipient, String message, int messageNumber) {
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.message = message;
        this.messageNumber = messageNumber;
        this.messageHash = createMessageHash();
    }

    private String generateMessageID() {
        Random random = new Random();
        long number = 1000000000L
                + (long) (random.nextDouble() * 9000000000L);

        return String.valueOf(number);
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipient != null
                && recipient.matches("^\\+27[0-9]{9}$")) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does not "
                + "contain an international code. Please correct the "
                + "number and try again.";
    }

    public String createMessageHash() {
        String firstTwoDigits = messageID.substring(0, 2);
        String trimmedMessage = message.trim();

        if (trimmedMessage.isEmpty()) {
            return (firstTwoDigits + ":" + messageNumber + ":")
                    .toUpperCase();
        }

        String[] words = trimmedMessage.split("\\s+");

        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        return (firstTwoDigits + ":" + messageNumber + ":"
                + firstWord + lastWord).toUpperCase();
    }

    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                sentMessages.add(this);
                totalMessages++;
                return "Message successfully sent.";

            case 2:
                if (!storedMessages.contains(this)) {
                    storedMessages.add(this);
                }
                return storeMessage();

            case 3:
                return "Message disregarded.";

            default:
                return "Invalid option.";
        }
    }

    public String SentMessage() {
        String[] options = {
            "Send Message",
            "Store Message to send later",
            "Disregard Message"
        };

        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose what to do with this message:",
                "QuickChat",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) {
            return SentMessage(1);
        } else if (choice == 1) {
            return SentMessage(2);
        } else if (choice == 2) {
            return SentMessage(3);
        }

        return "No option selected.";
    }

    public String printMessages() {
        StringBuilder output = new StringBuilder();

        for (Message sentMessage : sentMessages) {
            output.append(sentMessage.messageDetails()).append("\n\n");
        }

        if (output.length() == 0) {
            return "No messages have been sent yet.";
        }

        return output.toString();
    }

    public String messageDetails() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + message;
    }

    public int returnTotalMessages() {
        return totalMessages;
    }

    public String storeMessage() {
        try (FileWriter writer = new FileWriter("messages.json")) {

            writer.write("[\n");

            for (int i = 0; i < storedMessages.size(); i++) {
                Message stored = storedMessages.get(i);

                writer.write("  {\n");
                writer.write("    \"messageID\": \""
                        + escapeJSON(stored.messageID) + "\",\n");
                writer.write("    \"messageHash\": \""
                        + escapeJSON(stored.messageHash) + "\",\n");
                writer.write("    \"recipient\": \""
                        + escapeJSON(stored.recipient) + "\",\n");
                writer.write("    \"message\": \""
                        + escapeJSON(stored.message) + "\"\n");
                writer.write("  }");

                if (i < storedMessages.size() - 1) {
                    writer.write(",");
                }

                writer.write("\n");
            }

            writer.write("]\n");

            return "Message successfully stored.";

        } catch (IOException e) {
            return "Error storing message: " + e.getMessage();
        }
    }

    private String escapeJSON(String value) {
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    public static List<Message> getSentMessages() {
        return new ArrayList<>(sentMessages);
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }
}
  

