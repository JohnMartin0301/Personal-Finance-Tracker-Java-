import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class PersonalFinanceTracker {

    private static Scanner scanner = new Scanner(System.in);
    private static double totalAmount;
    private static List<Transaction> transactions = new ArrayList<>();
    private static double allowanceLeft;

    public static void main(String[] args) {
        calculateFinances();
    }

    private static void calculateFinances() {
        System.out.print("Enter your allowance: ");
        totalAmount = scanner.nextDouble();

        if (totalAmount <= 0) {
            System.out.println("Invalid input. Please enter a valid amount.");
            return;
        }

        allowanceLeft = totalAmount;

        double needsPercentage = 50;
        double savingsPercentage = 30;
        double wantsPercentage = 20;

        double needsAmount = (totalAmount * needsPercentage) / 100;
        double savingsAmount = (totalAmount * savingsPercentage) / 100;
        double wantsAmount = (totalAmount * wantsPercentage) / 100;

        DecimalFormat df = new DecimalFormat("#.00");
        String currentMonth = getCurrentMonth();

        System.out.println("Amount for Needs: $" + df.format(needsAmount));
        System.out.println("Amount for Savings: $" + df.format(savingsAmount));
        System.out.println("Amount for Wants: $" + df.format(wantsAmount));

        System.out.print("Do you want to enter expenses for " + currentMonth + "? (yes/no): ");
        String answer = scanner.next();

        if (answer.equalsIgnoreCase("yes")) {
            enterExpenses();
        } else {
            System.out.println("Allowance left for " + currentMonth + ": $" + df.format(allowanceLeft));
        }
    }


    private static void enterExpenses() {
        System.out.print("Enter the expense category (Needs/Savings/Wants): ");
        String category = scanner.next().toLowerCase();

        if (category.equals("needs") || category.equals("savings") || category.equals("wants")) {
            System.out.print("Enter the transaction description: ");
            scanner.nextLine();  // Consume the newline character left by previous input
            String description = scanner.nextLine();  // Read the entire line as description

            System.out.print("Enter the transaction amount: ");
            double amount = scanner.nextDouble();

            if (amount <= 0) {
                System.out.println("Invalid input. Please enter a valid amount.");
                enterExpenses();
                return;
            }

            transactions.add(new Transaction(description, amount, category));
            allowanceLeft -= amount;
            System.out.println("Expense added successfully!");

            DecimalFormat df = new DecimalFormat("#.00");
            String currentMonth = getCurrentMonth();
            System.out.println("Allowance left for " + currentMonth + ": $" + df.format(allowanceLeft));

            System.out.print("Do you want to add more expenses for " + currentMonth + "? (yes/no): ");
            String moreExpenses = scanner.next();

            if (moreExpenses.equalsIgnoreCase("yes")) {
                enterExpenses();
            } else {
                displayExpenses();
            }
        } else {
            System.out.println("Invalid category. Please choose from Needs, Savings, or Wants.");
            enterExpenses();
        }
    }



    private static void displayExpenses() {
        DecimalFormat df = new DecimalFormat("#.00");
        String currentMonth = getCurrentMonth();
        
        System.out.println("Expenses for " + currentMonth + ": ");
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            System.out.println((i + 1) + ". " + transaction.getDescription() + ": $" +
                    df.format(transaction.getAmount()) + " (" + transaction.getCategory() + ")");
        }

        System.out.println("Amount spent for " + currentMonth + ": $" + df.format(totalAmount - allowanceLeft));

        System.out.print("Do you want to track finances for another month? (yes/no): ");
        String answer = scanner.next();

        if (answer.equalsIgnoreCase("yes")) {
            transactions.clear();
            calculateFinances();
        } else {
            scanner.close();
            System.out.println("Program terminated.");
        }
    }


    private static class Transaction {
        private String description;
        private double amount;
        private String category;

        public Transaction(String description, double amount, String category) {
            this.description = description;
            this.amount = amount;
            this.category = category;
        }

        public String getDescription() {
            return description;
        }

        public double getAmount() {
            return amount;
        }

        public String getCategory() {
            return category;
        }
    }

    private static String getCurrentMonth() {
        String[] months = {
            "January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"
        };
        Calendar calendar = Calendar.getInstance();
        int currentMonthIndex = calendar.get(Calendar.MONTH);
        return months[currentMonthIndex];
    }
}
