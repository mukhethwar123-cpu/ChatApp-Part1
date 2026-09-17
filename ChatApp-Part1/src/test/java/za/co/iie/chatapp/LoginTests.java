package za.co.iie.chatapp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * LoginTests.java (alternate test suite)
 *
 * Same test data as required by the PROG5121 Part 1 brief, but written
 * using JUnit 5 parameterized tests where the brief supplies a clear
 * true/false pair for a single check, plus separate tests for the
 * combined registerUser() / login flow.
 */
class LoginTests {

    private static final String VALID_USERNAME = "kyl_1";
    private static final String INVALID_USERNAME = "kyle!!!!!!!";
    private static final String VALID_PASSWORD = "Ch&&sec@ke99!";
    private static final String INVALID_PASSWORD = "password";
    private static final String VALID_PHONE = "+27838968976";
    private static final String INVALID_PHONE = "08966553";

    @ParameterizedTest(name = "username \"{0}\" -> {1}")
    @DisplayName("Username format check")
    @CsvSource({
            "kyl_1, true",
            "kyle!!!!!!!, false"
    })
    void usernameFormatIsCheckedCorrectly(String candidateUsername, boolean expected) {
        Login login = new Login(candidateUsername, VALID_PASSWORD, VALID_PHONE, "Kyle", "Smith");
        assertEquals(expected, login.checkUserName());
    }

    @ParameterizedTest(name = "password \"{0}\" -> {1}")
    @DisplayName("Password complexity check")
    @CsvSource({
            "Ch&&sec@ke99!, true",
            "password, false"
    })
    void passwordComplexityIsCheckedCorrectly(String candidatePassword, boolean expected) {
        Login login = new Login(VALID_USERNAME, candidatePassword, VALID_PHONE, "Kyle", "Smith");
        assertEquals(expected, login.checkPasswordComplexity());
    }

    @ParameterizedTest(name = "phone \"{0}\" -> {1}")
    @DisplayName("Cell phone number format check")
    @CsvSource({
            "+27838968976, true",
            "08966553, false"
    })
    void cellPhoneNumberFormatIsCheckedCorrectly(String candidatePhone, boolean expected) {
        Login login = new Login(VALID_USERNAME, VALID_PASSWORD, candidatePhone, "Kyle", "Smith");
        assertEquals(expected, login.checkCellPhoneNumber());
    }

    @Test
    @DisplayName("registerUser() returns the combined success message when everything is valid")
    void registerUserSucceedsWhenAllDetailsAreValid() {
        Login login = new Login(VALID_USERNAME, VALID_PASSWORD, VALID_PHONE, "Kyle", "Smith");
        String expected = Login.USERNAME_OK + " " + Login.PASSWORD_OK + " " + Login.PHONE_OK;
        assertEquals(expected, login.registerUser());
    }

    @Test
    @DisplayName("registerUser() reports a bad username before checking anything else")
    void registerUserReportsUsernameErrorFirst() {
        Login login = new Login(INVALID_USERNAME, VALID_PASSWORD, VALID_PHONE, "Kyle", "Smith");
        assertEquals(Login.USERNAME_ERROR, login.registerUser());
    }

    @Test
    @DisplayName("registerUser() reports a weak password when the username is fine")
    void registerUserReportsPasswordError() {
        Login login = new Login(VALID_USERNAME, INVALID_PASSWORD, VALID_PHONE, "Kyle", "Smith");
        assertEquals(Login.PASSWORD_ERROR, login.registerUser());
    }

    @Test
    @DisplayName("registerUser() reports a bad phone number when username and password are fine")
    void registerUserReportsPhoneError() {
        Login login = new Login(VALID_USERNAME, VALID_PASSWORD, INVALID_PHONE, "Kyle", "Smith");
        assertEquals(Login.PHONE_ERROR, login.registerUser());
    }

    @Test
    @DisplayName("A matching username and password logs the user in")
    void loginSucceedsWithMatchingCredentials() {
        Login login = new Login(VALID_USERNAME, VALID_PASSWORD, VALID_PHONE, "Kyle", "Smith");
        assertTrue(login.loginUser(VALID_USERNAME, VALID_PASSWORD));
    }

    @Test
    @DisplayName("A wrong password fails to log the user in")
    void loginFailsWithWrongPassword() {
        Login login = new Login(VALID_USERNAME, VALID_PASSWORD, VALID_PHONE, "Kyle", "Smith");
        assertFalse(login.loginUser(VALID_USERNAME, "someOtherPassword1!"));
    }

    @Test
    @DisplayName("A successful login produces the personalised welcome message")
    void loginStatusMessageOnSuccess() {
        Login login = new Login(VALID_USERNAME, VALID_PASSWORD, VALID_PHONE, "Kyle", "Smith");
        assertEquals("Welcome Kyle, Smith it is great to see you again.",
                login.returnLoginStatus(true));
    }

    @Test
    @DisplayName("A failed login produces the generic error message")
    void loginStatusMessageOnFailure() {
        Login login = new Login(VALID_USERNAME, VALID_PASSWORD, VALID_PHONE, "Kyle", "Smith");
        assertEquals(Login.LOGIN_ERROR, login.returnLoginStatus(false));
    }
}
