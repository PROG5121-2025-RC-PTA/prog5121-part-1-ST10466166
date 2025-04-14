/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.login;
import javax.swing.JOptionPane;
/**
 *
 * @author RC_Student_lab
 */
public class Login {

    public String username;
    public String password;
    public String firstName;
    public String lastName;

    // Method to check username format
    public boolean checkUserName(String username) {
    return username.contains("_") && username.length() <= 5;
    }

    // Method to check password complexity
    public boolean checkPasswordComplexity(String password) {
    boolean hasUppercase = false;
    boolean hasDigit = false;
    boolean hasSpecialChar = false;
    if (password.length() < 8) return false;
    for (char c : password.toCharArray()) {
    if (Character.isUpperCase(c)) hasUppercase = true;
    else if (Character.isDigit(c)) hasDigit = true;
    else if (!Character.isLetterOrDigit(c)) hasSpecialChar = true;
        }
     return hasUppercase && hasDigit && hasSpecialChar;
    }
    // ✅ Method to check cell phone number
    public boolean checkPhoneNumber(String phoneNumber) {
     return phoneNumber.startsWith("+") && phoneNumber.length() <= 10;
    }
   //  User register method to include phone number
    public String registerUser() {
    username = JOptionPane.showInputDialog("Enter username (must contain underscore and max 5 characters):");
    if (!checkUserName(username)) {
     return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.";
        }
    password = JOptionPane.showInputDialog("Enter password (min 8 characters, a capital, a number, a special char):");
    if (!checkPasswordComplexity(password)) {
     return "Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number and a special character.";
        }
    firstName = JOptionPane.showInputDialog("Enter your first name:");
    lastName = JOptionPane.showInputDialog("Enter your last name:");
    String phoneNumber = JOptionPane.showInputDialog("Enter your cell phone number (include international code, max 10 chars):");
    if (!checkPhoneNumber(phoneNumber)) {
      return "Cell phone number incorrectly formatted or does not contain international code.";
        }
      return "Username successfully captured\nPassword successfully captured\nCell phone number successfully added\nUser registered successfully!";
    }

    // User login method
    public boolean loginUser() {
     String inputUsername = JOptionPane.showInputDialog("Enter username to login:");
     String inputPassword = JOptionPane.showInputDialog("Enter password to login:");
      return inputUsername.equals(username) && inputPassword.equals(password);
    }

    // Return login
    public String returnLoginStatus(boolean isLoggedIn) {
     if (isLoggedIn) {
      return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
    } else {
        return "Username or password incorrect, please try again.";
        }
    }

    //Login
    public static void main(String[] args) {
      Login loginSystem = new Login();
      // User Registration details
      String registrationMessage = loginSystem.registerUser();
      JOptionPane.showMessageDialog(null, registrationMessage);
      // User login details
      boolean isLoggedIn = loginSystem.loginUser();
      String loginStatus = loginSystem.returnLoginStatus(isLoggedIn);
      JOptionPane.showMessageDialog(null, loginStatus);
    }
}
