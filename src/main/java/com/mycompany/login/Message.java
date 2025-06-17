package com.mycompany.login;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import org.json.simple.*;

/**
 *
 * @author musak
 */
     public class Message {

    private final List<JSONObject> sentMessages = new ArrayList<>();
    private final List<String> disregardedMessages = new ArrayList<>();
    private final List<JSONObject> storedMessages = new ArrayList<>();
    private final List<String> messageHashes = new ArrayList<>();
    private final List<String> messageIDs = new ArrayList<>();
    private final List<String> recipients = new ArrayList<>();
    private final List<String> senders = new ArrayList<>();
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
        if (!checkMessageID(id)) return "Invalid Message ID.";

        String recipient = JOptionPane.showInputDialog("Enter recipient number (include + and max 10 chars):");
        if (!checkRecipientCell(recipient)) return "Invalid recipient cell.";

        String messageText = JOptionPane.showInputDialog("Enter your message (max 250 chars):");
        if (messageText == null || messageText.length() > 250) return "Please enter a message under 250 characters.";

        String hash = createMessageHash(id, messageText);

        String option = JOptionPane.showInputDialog("""
                What would you like to do with the message?
                1 - Send Message
                2 - Disregard Message
                3 - Store Message to send later
                """);

        JSONObject messageObj = new JSONObject();
        messageObj.put("MessageID", id);
        messageObj.put("MessageHash", hash);
        messageObj.put("Recipient", recipient);
        messageObj.put("Message", messageText);

        switch (option) {
            case "1" -> {
                sentMessages.add(messageObj);
                recipients.add(recipient);
                senders.add("You");
                messageHashes.add(hash);
                messageIDs.add(id);
                totalMessages++;
                JOptionPane.showMessageDialog(null, printMessage(messageObj));
            }
            case "2" -> {
                disregardedMessages.add(messageText);
                return "Message disregarded.";
            }
            case "3" -> {
                storeMessage(messageObj);
                storedMessages.add(messageObj);
                JOptionPane.showMessageDialog(null, "Message stored for later.");
            }
            default -> {
                return "Invalid option.";
            }
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
        try (FileWriter file = new FileWriter("stored_messages.json", true)) {
            file.write(message.toJSONString() + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to JSON file: " + e.getMessage());
        }
    }

    public String createHash(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(message.getBytes());
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) hex.append(String.format("%02x", b));
            return hex.toString().substring(0, 8);
        } catch (NoSuchAlgorithmException e) {
            return Integer.toHexString(message.hashCode());
        }
    }

    public void loadStoredMessagesFromJSON() {
        // Optional: add JSON reading logic if needed
    }

    public void loadTestData() {
        for (int i = 1; i <= 3; i++) {
            JSONObject msg = new JSONObject();
            String id = "TEST" + i;
            String text = "Sample message number " + i;
            String hash = createMessageHash(id, text);
            msg.put("MessageID", id);
            msg.put("Message", text);
            msg.put("Recipient", "+278000000" + i);
            msg.put("MessageHash", hash);

            sentMessages.add(msg);
            recipients.add("+278000000" + i);
            senders.add("Tester");
            messageHashes.add(hash);
            messageIDs.add(id);
            totalMessages++;
        }
    }

    public void displaySendersAndRecipients() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sent messages yet.");
            return;
        }
        StringBuilder sb = new StringBuilder("Senders and Recipients:\n");
        for (int i = 0; i < sentMessages.size(); i++) {
            sb.append("Sender: ").append(senders.get(i))
              .append(" → Recipient: ").append(recipients.get(i)).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void displayLongestSentMessage() {
        if (sentMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages sent.");
            return;
        }
        JSONObject longest = sentMessages.get(0);
        for (JSONObject msg : sentMessages) {
            if (msg.get("Message").toString().length() > longest.get("Message").toString().length()) {
                longest = msg;
            }
        }
        JOptionPane.showMessageDialog(null, "Longest Message:\n" + printMessage(longest));
    }

    public void searchByMessageID() {
        String inputID = JOptionPane.showInputDialog("Enter Message ID to search:");
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(inputID)) {
                JOptionPane.showMessageDialog(null, printMessage(sentMessages.get(i)));
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message ID not found.");
    }

    public void deleteByHash() {
        String hash = JOptionPane.showInputDialog("Enter message hash to delete:");
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equals(hash)) {
                sentMessages.remove(i);
                recipients.remove(i);
                senders.remove(i);
                messageHashes.remove(i);
                messageIDs.remove(i);
                totalMessages--;
                JOptionPane.showMessageDialog(null, "Message deleted.");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Hash not found.");
    }

    public void searchByRecipient() {
        String target = JOptionPane.showInputDialog("Enter recipient to search for:");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < recipients.size(); i++) {
            if (recipients.get(i).equalsIgnoreCase(target)) {
                sb.append(printMessage(sentMessages.get(i))).append("\n\n");
            }
        }
        JOptionPane.showMessageDialog(null, sb.length() > 0 ? sb.toString() : "No messages to this recipient.");
    }

    public void displayReport() {
        StringBuilder sb = new StringBuilder("Full Sent Message Report:\n");
        for (int i = 0; i < sentMessages.size(); i++) {
            sb.append("From: ").append(senders.get(i))
              .append(" | To: ").append(recipients.get(i))
              .append(" | Msg: ").append(sentMessages.get(i).get("Message"))
              .append(" | ID: ").append(messageIDs.get(i))
              .append(" | Hash: ").append(messageHashes.get(i)).append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.length() > 0 ? sb.toString() : "No messages to display.");
    }
}
