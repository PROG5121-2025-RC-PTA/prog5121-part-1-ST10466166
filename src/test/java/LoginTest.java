/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */


import static org.junit.jupiter.api.Assertions.*;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    private Message message;

    @BeforeEach
    public void setUp() {
        message = new Message();

        // Message 1: Sent
        JSONObject msg1 = new JSONObject();
        msg1.put("MessageID", "0000000001");
        msg1.put("Recipient", "+27834557896");
        msg1.put("Message", "Did you get the cake?");
        msg1.put("MessageHash", message.createMessageHash("0000000001", "Did you get the cake?"));
        message.sendMessageSimulated("1", msg1);

        // Message 2: Stored
        JSONObject msg2 = new JSONObject();
        msg2.put("MessageID", "0000000002");
        msg2.put("Recipient", "+27838884567");
        msg2.put("Message", "Where are you? You are late! I have asked you to be on time.");
        msg2.put("MessageHash", message.createMessageHash("0000000002", "Where are you? You are late! I have asked you to be on time."));
        message.sendMessageSimulated("3", msg2);

        // Message 3: Disregarded - not added

        // Message 4: Sent
        JSONObject msg4 = new JSONObject();
        msg4.put("MessageID", "08388884567");
        msg4.put("Recipient", "08388884567");
        msg4.put("Message", "It is dinner time!");
        msg4.put("MessageHash", message.createMessageHash("08388884567", "It is dinner time!"));
        message.sendMessageSimulated("1", msg4);

        // Message 5: Stored
        JSONObject msg5 = new JSONObject();
        msg5.put("MessageID", "0000000005");
        msg5.put("Recipient", "+27838884567");
        msg5.put("Message", "Ok, I am leaving without you.");
        msg5.put("MessageHash", message.createMessageHash("0000000005", "Ok, I am leaving without you."));
        message.sendMessageSimulated("3", msg5);
    }

    @Test
    public void testSentMessagesPopulated() {
        assertEquals(2, message.returnTotalMessages()); // Only 2 were actually sent
    }

    @Test
    public void testLongestMessage() {
        // Should return the longest sent message
        String expectedLongest = "Where are you? You are late! I have asked you to be on time.";
        String actualLongest = (String) message.getLongestMessage();  // You need to implement this
        assertEquals(expectedLongest, actualLongest);
    }

    @Test
    public void testSearchByMessageID() {
        JSONObject result = (JSONObject) message.searchMessageByID("08388884567");  // You need to implement this
        assertNotNull(result);
        assertEquals("It is dinner time!", result.get("Message"));
    }

    @Test
    public void testSearchByRecipient() {
        String recipient = "+27838884567";
        int count = message.searchCountByRecipient(recipient); // You need to implement this helper
        assertEquals(2, count); // Messages 2 and 5 stored
    }

    @Test
    public void testDeleteByHash() {
        String hash = message.createMessageHash("0000000002", "Where are you? You are late! I have asked you to be on time.");
        boolean deleted = message.deleteMessageByHash(hash);  // You need to implement this to return boolean
        assertTrue(deleted);
    }

    @Test
    public void testDisplayReportIncludesSentData() {
        String report = message.generateReport();  // You need to implement this to return a string
        assertTrue(report.contains("Message Hash"));
        assertTrue(report.contains("Recipient"));
        assertTrue(report.contains("Message"));
    }
}
}
