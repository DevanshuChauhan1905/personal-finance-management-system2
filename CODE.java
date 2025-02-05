import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FinanceManager financeManager = new FinanceManager();

        while (true) {
            System.out.println("\n--- Personal Finance Management System ---");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. View Transactions");
            System.out.println("4. View Balance");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            
            int choice = validateMenuOption(scanner);
            if (choice == -1) continue; // Handle invalid input gracefully

            switch (choice) {
                case 1:
                    System.out.print("Enter income amount: ");
                    double income = validateAmount(scanner);
                    if (income < 0) break; // Skip if invalid input
                    System.out.print("Enter income source: ");
                    scanner.nextLine(); // Consume newline before nextLine
                    String incomeSource = scanner.nextLine();
                    financeManager.addIncome(income, incomeSource);
                    break;
                case 2:
                    System.out.print("Enter expense amount: ");
                    double expense = validateAmount(scanner);
                    if (expense < 0) break; // Skip if invalid input
                    System.out.print("Enter expense category: ");
                    scanner.nextLine(); // Consume newline before nextLine
                    String expenseCategory = scanner.nextLine();
                    financeManager.addExpense(expense, expenseCategory);
                    break;
                case 3:
                    financeManager.viewTransactions();
                    break;
                case 4:
                    System.out.printf("Current Balance: %.2f\n", financeManager.getBalance());
                    break;
                case 5:
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static int validateMenuOption(Scanner scanner) {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            return choice;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number between 1 and 5.");
            scanner.nextLine(); // Clear buffer
            return -1;
        }
    }

    private static double validateAmount(Scanner scanner) {
        try {
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            if (amount <= 0) {
                System.out.println("Amount must be positive.");
                return -1;
            }
            return amount;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a numeric value.");
            scanner.nextLine(); // Clear buffer
            return -1;
        }
    }
}

class FinanceManager {
    private double balance;
    private final List<Transaction> transactions;

    public FinanceManager() {
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public void addIncome(double amount, String source) {
        balance += amount;
        transactions.add(new Transaction("Income", amount, source));
        System.out.println("Income added successfully.");
    }

    public void addExpense(double amount, String category) {
        if (amount > balance) {
            System.out.println("Insufficient balance. Cannot add expense.");
            return;
        }
        balance -= amount;
        transactions.add(new Transaction("Expense", amount, category));
        System.out.println("Expense added successfully.");
    }

    public void viewTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions available.");
            return;
        }
        System.out.println("\n--- Transactions ---");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    public double getBalance() {
        return balance;
    }
}

class Transaction {
    private final String type;
    private final double amount;
    private final String description;

    public Transaction(String type, double amount, String description) {
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Type: %s | Amount: %.2f | Description: %s", type, amount, description);
    }
}
