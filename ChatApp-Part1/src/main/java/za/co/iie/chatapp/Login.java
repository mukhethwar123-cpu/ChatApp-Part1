package za.co.iie.chatapp;


 
public class Login {

    // ---- Captured registration details ----
    private final String userName;
    private final String userPassword;
    private final String phoneNumber;
    private final String name;
    private final String surname;

    // ---- System messages (kept as constants so Login and its tests
    //      always agree on exact wording) ----
    public static final String USERNAME_OK =
            "Username successfully captured.";
    public static final String USERNAME_ERROR =
            "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";

    public static final String PASSWORD_OK =
            "Password successfully captured.";
    public static final String PASSWORD_ERROR =
            "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";

    public static final String PHONE_OK =
            "Cell number successfully captured.";
    public static final String PHONE_ERROR =
            "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";

    public static final String LOGIN_ERROR =
            "Username or password incorrect, please try again.";

    // Characters that count as "special" for password complexity purposes.
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{};:'\",.<>/?|`~\\";

    // Minimum digits expected after the '+' and country code for a cell
    // number to be considered valid, based on the worked example in the
    // brief (+27838968976).
    //
    // Reference / attribution: general approach to manual (non-regex)
    // phone number length/format validation adapted from GeeksforGeeks,
    // "Check if a string is a valid E.164 phone number",
    // https://www.geeksforgeeks.org/ (accessed 2026), simplified here to
    // match the ten-character subscriber-number rule from this brief.
    private static final int MIN_PHONE_DIGITS = 9;
    private static final int MAX_PHONE_DIGITS = 13;

    public Login(String userName, String userPassword, String phoneNumber,
                 String name, String surname) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.surname = surname;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    /**
     * A username is valid when it contains at least one underscore and is
     * no longer than five characters.
     */
    public boolean checkUserName() {
        if (userName == null || userName.isEmpty()) {
            return false;
        }

        boolean hasUnderscore = false;
        for (int i = 0; i < userName.length(); i++) {
            if (userName.charAt(i) == '_') {
                hasUnderscore = true;
                break;
            }
        }

        return hasUnderscore && userName.length() <= 5;
    }

    /**
     * A password is valid when it is at least eight characters long and
     * contains at least one capital letter, one digit, and one special
     * character. Checked here with a single character scan rather than a
     * regular expression.
     */
    public boolean checkPasswordComplexity() {
        if (userPassword == null) {
            return false;
        }

        if (userPassword.length() < 8) {
            return false;
        }

        boolean hasCapital = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (int i = 0; i < userPassword.length(); i++) {
            char currentChar = userPassword.charAt(i);

            if (Character.isUpperCase(currentChar)) {
                hasCapital = true;
            } else if (Character.isDigit(currentChar)) {
                hasDigit = true;
            } else if (SPECIAL_CHARACTERS.indexOf(currentChar) >= 0) {
                hasSpecial = true;
            }
        }

        return hasCapital && hasDigit && hasSpecial;
    }

    /**
     * A cell number is valid when it starts with '+' (an international
     * dialling code), the remainder is made up only of digits, and the
     * total number of digits falls within the expected range for a
     * country code plus a ten-character subscriber number.
     */
    public boolean checkCellPhoneNumber() {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }

        if (phoneNumber.charAt(0) != '+') {
            return false;
        }

        String digitsOnly = phoneNumber.substring(1);
        if (digitsOnly.isEmpty()) {
            return false;
        }

        for (int i = 0; i < digitsOnly.length(); i++) {
            if (!Character.isDigit(digitsOnly.charAt(i))) {
                return false;
            }
        }

        int digitCount = digitsOnly.length();
        return digitCount >= MIN_PHONE_DIGITS && digitCount <= MAX_PHONE_DIGITS;
    }

    /**
     * Runs the three registration checks in turn and returns the matching
     * system message; a combined success message is returned only once
     * all three checks pass.
     */
    public String registerUser() {
        StringBuilder result = new StringBuilder();

        if (!checkUserName()) {
            return USERNAME_ERROR;
        }
        result.append(USERNAME_OK);

        if (!checkPasswordComplexity()) {
            return PASSWORD_ERROR;
        }
        result.append(" ").append(PASSWORD_OK);

        if (!checkCellPhoneNumber()) {
            return PHONE_ERROR;
        }
        result.append(" ").append(PHONE_OK);

        return result.toString();
    }

    /**
     * Confirms whether the supplied credentials match the ones captured
     * at registration.
     */
    public boolean loginUser(String enteredUserName, String enteredPassword) {
        if (enteredUserName == null || enteredPassword == null) {
            return false;
        }
        return enteredUserName.equals(userName) && enteredPassword.equals(userPassword);
    }

    /**
     * Produces the login feedback message for a given outcome.
     */
    public String returnLoginStatus(boolean loginWasSuccessful) {
        return loginWasSuccessful
                ? "Welcome " + name + ", " + surname + " it is great to see you again."
                : LOGIN_ERROR;
    }
}
