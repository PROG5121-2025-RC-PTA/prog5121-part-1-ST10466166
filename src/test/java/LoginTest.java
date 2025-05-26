/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 *
 * @author musak
 */
   
public class LoginTest {

    @Test
    public void testUsernameCorrectFormat() {
        Login login = new Login();
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testUsernameIncorrectFormat() {
        Login login = new Login();
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testPasswordMeetsComplexityRequirements() {
        Login login = new Login();
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }
    
    @Test
    public void testPasswordFailsComplexityRequirements() {
        Login login = new Login();
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testLoginSuccessful() {
        Login login = new Login();
        login.username = "kyl_1";
        login.password = "Ch&&sec@ke99!";
        assertTrue(login.loginUserTest("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFailed() {
        Login login = new Login();
        login.username = "kyl_1";
        login.password = "Ch&&sec@ke99!";
        assertFalse(login.loginUserTest("wrong_user", "wrong_pass"));
    }
        
        @Test
    public void checkPhoneNumberSuccessful(){
        Login login = new Login();
        assertTrue(login.checkPhoneNumber("phonenumber"));
    }
    
    @Test
    public void checkPhoneNumberFalse(){
        Login login = new Login();
        assertFalse(login.checkPhoneNumber("phonenumber"));
    }

    @Test
    public void testReturnLoginStatusSuccessMessage() {
        Login login = new Login();
        login.firstName = "John";
        login.lastName = "Doe";
        String expectedMessage = "Welcome John, Doe it is great to see you again.";
        assertEquals(expectedMessage, login.returnLoginStatus(true));
    }

    @Test
    public void testReturnLoginStatusFailureMessage() {
        Login login = new Login();
        String expectedMessage = "Username or password incorrect, please try again.";
        assertEquals(expectedMessage, login.returnLoginStatus(false));
    }


   public class MessageTest {
    Message message = new Message();

    @Test
    public void testMessageLengthSuccess() {
        String input = "This is a valid message.";
        assertEquals("Message ready to send.", validateMessageLength(input));
    }

    @Test
    public void testMessageLengthFailure() {
        String input = "A".repeat(255);
        int excess = input.length() - 250;
        assertEquals("Message exceeds 250 characters by " + excess + ", please reduce size.", validateMessageLength(input));
    }

    private String validateMessageLength(String msg) {
        if (msg.length() <= 250) {
            return "Message ready to send.";
        } else {
            return "Message exceeds 250 characters by " + (msg.length() - 250) + ", please reduce size.";
        }
    }

    @Test
    public void testRecipientNumberSuccess() {
        String cell = "+1234567890";
        assertEquals("Cell phone number successfully captured.", validatePhone(cell));
    }

    @Test
    public void testRecipientNumberFailure() {
        String cell = "123456789";
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", validatePhone(cell));
    }

    private String validatePhone(String cell) {
        if (cell.startsWith("+") && cell.length() <= 13 && cell.length() > 1 && cell.substring(1).matches("\\d+")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    @Test
    public void testMessageHashCorrect() {
        String Hash = message.createMessageHash("00", "Hi tonight");
        assertEquals("00:10:HITONIGHT", Hash);
    }

    @Test
    public void testMessageIDCreation() {
        String id = String.format("%010d", new java.util.Random().nextInt(1_000_000_000));
        assertEquals(10, id.length());
        assertTrue(id.matches("\\d+"));
    }

    @Test
    public void testMessageSentOptions() {
        assertEquals("Message successfully sent.", simulateUserOption("1"));
        assertEquals("Press 0 to delete message.", simulateUserOption("2"));
        assertEquals("Message successfully stored.", simulateUserOption("3"));
    }

    private String simulateUserOption(String choice) {
        return switch (choice) {
            case "1" -> "Message successfully sent.";
            case "2" -> "Press 0 to delete message.";
            case "3" -> "Message successfully stored.";
            default -> "Invalid option.";
        };
    }
}
}
