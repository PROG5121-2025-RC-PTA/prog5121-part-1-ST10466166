/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.login;
import javax.swing.JOptionPane;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author musak
 */
class Message {
    private final List<JSONObject> sentMessages = new ArrayList<>();
    private static int totalMessages = 0;

    public boolean checkMessageID(String id) {
        return id.length() <= 10;
    }

    public boolean checkRecipientCell(String cell) {
        return cell.startsWith("+") && cell.length() <= 10;
    }

    public String createMessageHash(String id, String message) {
        String[] words = message.trim().split("\\s+");
        String first = words.length > 0 ? words[0] : "";
        String last = words.length > 1 ? words[words.length - 1] : first;
        return id.substring(0, 2) + ":" + message.length() + ":" + first.toUpperCase() + last.toUpperCase();
    }

    public String sendMessage() {
        String id = String.format("%010d", new Random().nextInt(999999999));
        if (!checkMessageID(id)) {
            return "Invalid Message ID.";
        }

        String recipient = JOptionPane.showInputDialog("Enter recipient number (include + and max 10 chars):");
        if (!checkRecipientCell(recipient)) {
            return "Invalid recipient cell.";
        }

        String messageText = JOptionPane.showInputDialog("Enter your message (max 250 chars):");
        if (messageText.length() > 250) {
            return "Please enter a message of less than 250 characters.";
        }

        String hash = createMessageHash(id, messageText);

        String option = JOptionPane.showInputDialog("""
                What would you like to do with the message?
                1 - Send Message
                2 - Disregard Message
                3 - Store Message to send later
                """);

        if (option.equals("2")) {
            return "Message disregarded.";
        }

        JSONObject messageObj = new JSONObject();
        messageObj.put("MessageID", id);
        messageObj.put("MessageHash", hash);
        messageObj.put("Recipient", recipient);
        messageObj.put("Message", messageText);

        if (option.equals("1")) {
            sentMessages.add(messageObj);
            totalMessages++;
            JOptionPane.showMessageDialog(null, printMessage(messageObj));
        }

        if (option.equals("3")) {
            storeMessage(messageObj);
            JOptionPane.showMessageDialog(null, "Message stored for later.");
        }

        return "Done.";
    }

    private String printMessage(JSONObject msg) {
        return """
               Message Sent:
               ID: """ + msg.get("MessageID") + "\n" +
               "Hash: " + msg.get("MessageHash") + "\n" +
               "Recipient: " + msg.get("Recipient") + "\n" +
               "Message: " + msg.get("Message");
    }

    public void printMessages() {
        for (JSONObject msg : sentMessages) {
            JOptionPane.showMessageDialog(null, printMessage(msg));
        }
    }

    public int returnTotalMessages() {
        return totalMessages;
    }

    public void storeMessage(JSONObject message) {
        JSONArray array = new JSONArray();
        array.add(message);
        try (FileWriter file = new FileWriter("stored_messages.json", true)) {
            file.write(array.toJSONString() + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to JSON file: " + e.getMessage());
        }
    }

public void sendMessageSimulated(String option, JSONObject messageObj) {
    if (option.equals("2")) {
        System.out.println("Message disregarded.");
        return;
    }

    if (option.equals("1")) {
        sentMessages.add(messageObj);
        totalMessages++;
        System.out.println("Message successfully sent.");
    }

    if (option.equals("3")) {
        storeMessage(messageObj);
        System.out.println("Message successfully stored.");
    }
}
}




    
