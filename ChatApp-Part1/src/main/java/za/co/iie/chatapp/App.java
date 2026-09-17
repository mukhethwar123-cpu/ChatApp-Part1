package za.co.iie.chatapp;

import java.util.Scanner;


public class App {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static Login currentUser = null;

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n===== ChatApp =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Quit");
            System.out.print("Choose an option: ");

            choice = readMenuChoice();

            switch (choice) {
                case 1 -> handleRegistration();
                case 2 -> handleLogin();
                case 3 -> System.out.println("Goodbye!");
                default -> System.out.println("Please enter 1, 2, or 3.");
            }

        } while (choice != 3);

        SCANNER.close();
    }

    private static int readMenuChoice() {
        String input = SCANNER.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void handleRegistration() {
        System.out.print("Choose a username (underscore, max 5 chars): ");
        String userName = SCANNER.nextLine();

        System.out.print("Choose a password (8+ chars, capital, digit, special char): ");
        String password = SCANNER.nextLine();

        System.out.print("Enter your cell number (e.g. +27838968976): ");
        String phoneNumber = SCANNER.nextLine();

        System.out.print("Enter your name: ");
        String name = SCANNER.nextLine();

        System.out.print("Enter your surname: ");
        String surname = SCANNER.nextLine();

        Login candidate = new Login(userName, password, phoneNumber, name, surname);
        System.out.println(candidate.registerUser());

        boolean allValid = candidate.checkUserName()
                && candidate.checkPasswordComplexity()
                && candidate.checkCellPhoneNumber();

        if (allValid) {
            currentUser = candidate;
            System.out.println("You can now log in with these details.");
        }
    }

    private static void handleLogin() {
        if (currentUser == null) {
            System.out.println("No account has been registered yet.");
            return;
        }

        System.out.print("Username: ");
        String userName = SCANNER.nextLine();
        System.out.print("Password: ");
        String password = SCANNER.nextLine();

        boolean success = currentUser.loginUser(userName, password);
        System.out.println(currentUser.returnLoginStatus(success));
    }
}
