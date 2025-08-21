package com.example.expensetracker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/* No external JSON library is used. Expenses are stored in a simple
 * tab-separated text file for maximum portability. */

/**
 * A simple command-line expense tracker application. It allows users to
 * record expenses, list them and view totals by category. Data is
 * persisted to a JSON file on disk so subsequent runs will pick up
 * where the previous run left off.
 */
public class ExpenseTracker {
    /**
     * The name of the JSON file used to persist expense data. You can
     * change this constant to store the file elsewhere.
     */
    private static final String DATA_FILE = "expenses.txt";

    private final List<Expense> expenses;

    /**
     * Constructs a new expense tracker, loading existing expenses from
     * disk if they are present.
     */
    public ExpenseTracker() {
        this.expenses = new ArrayList<>();
        loadExpenses();
    }

    /**
     * Loads expenses from the data file. Each line in the file
     * represents a single expense encoded as tab-separated values:
     * date TAB amount TAB category TAB description. Lines that
     * cannot be parsed are ignored. If the file does not exist
     * nothing happens.
     */
    private void loadExpenses() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t", -1);
                if (parts.length < 4) {
                    continue;
                }
                String date = parts[0];
                double amount;
                try {
                    amount = Double.parseDouble(parts[1]);
                } catch (NumberFormatException ex) {
                    continue;
                }
                String category = parts[2];
                String description = parts[3];
                expenses.add(new Expense(date, amount, category, description));
            }
        } catch (Exception e) {
            System.err.println("Warning: failed to read existing expenses. Starting fresh.");
        }
    }

    /**
     * Saves the current list of expenses to disk. Each expense is
     * written as a line of tab-separated values. If an error occurs
     * while writing, it will be printed to stderr.
     */
    private void saveExpenses() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Expense e : expenses) {
                // Encode newline and tab characters in the description by replacing tabs with spaces
                String safeDescription = e.getDescription().replace("\t", " ").replace("\n", " ");
                bw.write(e.getDate() + "\t" + e.getAmount() + "\t" + e.getCategory() + "\t" + safeDescription);
                bw.newLine();
            }
        } catch (Exception e) {
            System.err.println("Error writing expenses to file: " + e.getMessage());
        }
    }

    /**
     * Handles user input to add a new expense to the list. Prompts
     * the user for date, amount, category and description.
     */
    private void addExpense(Scanner scanner) {
        System.out.print("Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Amount: ");
        String amountStr = scanner.nextLine();
        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid number. Please try again.");
            return;
        }
        System.out.print("Category: ");
        String category = scanner.nextLine();
        System.out.print("Description: ");
        String description = scanner.nextLine();

        Expense expense = new Expense(date, amount, category, description);
        expenses.add(expense);
        saveExpenses();
        System.out.println("Expense added successfully.");
    }

    /**
     * Lists all recorded expenses to the console. If no expenses have
     * been recorded, a message is displayed instead.
     */
    private void listExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }
        for (int i = 0; i < expenses.size(); i++) {
            Expense e = expenses.get(i);
            System.out.printf("%d. %s - %.2f [%s] %s%n", i + 1, e.getDate(), e.getAmount(), e.getCategory(), e.getDescription());
        }
    }

    /**
     * Aggregates expenses by category and prints the total for each
     * category to the console.
     */
    private void showTotalByCategory() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }
        Map<String, Double> totals = new LinkedHashMap<>();
        for (Expense e : expenses) {
            totals.put(e.getCategory(), totals.getOrDefault(e.getCategory(), 0.0) + e.getAmount());
        }
        System.out.println("Totals by category:");
        for (Map.Entry<String, Double> entry : totals.entrySet()) {
            System.out.printf("%s: %.2f%n", entry.getKey(), entry.getValue());
        }
    }

    /**
     * Runs the interactive command-line interface. Presents a menu
     * repeatedly until the user chooses to exit.
     */
    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println();
                System.out.println("Expense Tracker Menu:");
                System.out.println("1. Add expense");
                System.out.println("2. List expenses");
                System.out.println("3. Show total by category");
                System.out.println("0. Exit");
                System.out.print("Choose: ");
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        addExpense(scanner);
                        break;
                    case "2":
                        listExpenses();
                        break;
                    case "3":
                        showTotalByCategory();
                        break;
                    case "0":
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    /**
     * Program entry point. Creates an instance of the application and
     * starts the interactive loop.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        ExpenseTracker tracker = new ExpenseTracker();
        tracker.run();
    }
}
