package com.example.expensetracker;

/**
 * Represents a single expense entry. Each expense contains
 * a date, an amount, a category and a short description.
 */
public class Expense {
    private String date;
    private double amount;
    private String category;
    private String description;

    /**
     * Creates a new expense.
     *
     * @param date        the date of the expense (e.g. "2025-08-21")
     * @param amount      the amount spent
     * @param category    the category of the expense (e.g. "Food", "Transport")
     * @param description a short description of the expense
     */
    public Expense(String date, double amount, String category, String description) {
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%s - %.2f %s: %s", date, amount, category, description);
    }
}
