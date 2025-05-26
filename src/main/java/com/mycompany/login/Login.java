/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.login;
import javax.swing.JOptionPane;
/**
 *
 * @author musak
 */
public class Login {

    public String username;
    public String password;
    public String firstName;
    public String lastName;

 public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 10;
    }

    public boolean checkPasswordComplexity(String password) {
        boolean hasUppercase = false, hasDigit = false, hasSpecialChar = false;
        if (password.length() < 8) return false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUppercase = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecialChar = true;
        }
        return hasUppercase && hasDigit && hasSpecialChar;
    }

    public boolean checkPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.startsWith("+") && phoneNumber.length() <= 13 && phoneNumber.length() >= 10;
    }

    public String registerUser() {
        username = JOptionPane.showInputDialog("Enter username (must contain underscore and max 10 characters):");
        if (!checkUserName(username)) {
            return "Username is not correctly formatted.";
        }

        password = JOptionPane.showInputDialog("Enter password (min 8 chars, 1 capital, 1 number, 1 special char):");
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted.";
        }

        firstName = JOptionPane.showInputDialog("Enter your first name:");
        lastName = JOptionPane.showInputDialog("Enter your last name:");

        String phoneNumber = JOptionPane.showInputDialog("Enter your phone number (+ format, max 13 chars):");
        if (!checkPhoneNumber(phoneNumber)) {
            return "Cell phone number incorrectly formatted.";
        }

        return "User registered successfully!";
    }

    public boolean loginUser() {
        String inputUsername = JOptionPane.showInputDialog("Enter username to login:");
        String inputPassword = JOptionPane.showInputDialog("Enter password to login:");
        return inputUsername.equals(username) && inputPassword.equals(password);
    }

    public String returnLoginStatus(boolean isLoggedIn) {
        return isLoggedIn
            ? "Welcome " + firstName + " " + lastName + ", it is great to see you again."
            : "Username or password incorrect.";
    }

    public static void main(String[] args) {
        Login login = new Login();
        Message message = new Message();

        String regMsg = login.registerUser();
        JOptionPane.showMessageDialog(null, regMsg);

        boolean isLoggedIn = login.loginUser();
        JOptionPane.showMessageDialog(null, login.returnLoginStatus(isLoggedIn));

        if (isLoggedIn) {
            JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
            int numToSend = Integer.parseInt(JOptionPane.showInputDialog("How many messages would you like to send?"));
            for (int i = 0; i < numToSend; i++) {
                message.sendMessage();
            }

            JOptionPane.showMessageDialog(null, "Total messages sent: " + message.returnTotalMessages());
            message.printMessages();
        }
    }
}
