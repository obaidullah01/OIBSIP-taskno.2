import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ATMInterface {
    private static Map<String, String> userCredentials = new HashMap<>();
    private static Map<String, Double> userAccounts = new HashMap<>();
    private static String currentUser;

    public static void main(String[] args) {
        initializeData();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the ATM!");

        while (true) {
            System.out.print("Enter User ID: ");
            String userId = scanner.nextLine();
            System.out.print("Enter PIN: ");
            String pin = scanner.nextLine();

            if (authenticateUser(userId, pin)) {
                currentUser = userId;
                System.out.println("Login Successful!");
                break;
            } else {
                System.out.println("Invalid credentials. Try again.");
            }
        }

        boolean quit = false;
        while (!quit) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the new line character

            switch (choice) {
                case 1:
                    showTransactionsHistory();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("Thank you for using the ATM. Goodbye!");
        scanner.close();
    }

    private static void initializeData() {
        userCredentials.put("user1", "1234");
        userAccounts.put("user1", 10000.0);
    }

    private static boolean authenticateUser(String userId, String pin) {
        return userCredentials.containsKey(userId) && userCredentials.get(userId).equals(pin);
    }

    private static void displayMenu() {
        System.out.println("\nATM Menu:");
        System.out.println("1. Transactions History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
        System.out.print("Enter your choice: ");
    }

    private static void showTransactionsHistory() {
        System.out.println("\n--- Transactions History ---");
        // Implement the logic to show transaction history for the current user (if available).
    }

    private static void withdraw() {
        System.out.print("Enter the amount to withdraw: ");
        Scanner scanner = new Scanner(System.in);
        double amount = scanner.nextDouble();

        double balance = userAccounts.get(currentUser);
        if (amount > balance) {
            System.out.println("Insufficient balance.");
        } else {
            balance -= amount;
            userAccounts.put(currentUser, balance);
            System.out.println("Withdraw successful. Updated balance: " + balance);
        }
    }

    private static void deposit() {
        System.out.print("Enter the amount to deposit: ");
        Scanner scanner = new Scanner(System.in);
        double amount = scanner.nextDouble();

        double balance = userAccounts.get(currentUser);
        balance += amount;
        userAccounts.put(currentUser, balance);
        System.out.println("Deposit successful. Updated balance: " + balance);
    }

    private static void transfer() {
        System.out.print("Enter the recipient's user ID: ");
        Scanner scanner = new Scanner(System.in);
        String recipient = scanner.nextLine();

        if (!userAccounts.containsKey(recipient)) {
            System.out.println("Recipient user ID not found.");
            return;
        }

        System.out.print("Enter the amount to transfer: ");
        double amount = scanner.nextDouble();

        double senderBalance = userAccounts.get(currentUser);
        if (amount > senderBalance) {
            System.out.println("Insufficient balance.");
        } else {
            double recipientBalance = userAccounts.get(recipient);
            senderBalance -= amount;
            recipientBalance += amount;
            userAccounts.put(currentUser, senderBalance);
            userAccounts.put(recipient, recipientBalance);
            System.out.println("Transfer successful.");
        }
    }
}
