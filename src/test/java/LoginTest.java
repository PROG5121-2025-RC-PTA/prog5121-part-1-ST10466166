/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author RC_Student_lab
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
}

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

