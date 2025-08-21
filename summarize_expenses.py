#!/usr/bin/env python3
"""
summarize_expenses.py
----------------------

This script reads the ``expenses.txt`` file produced by the Java
ExpenseTracker application and prints a simple summary of total
amounts spent per category. It is a convenience script so you can
quickly see where your money is going without having to run the
interactive Java program.

Usage:
    python3 summarize_expenses.py

It will look for ``expenses.txt`` in the current working directory.
If the file does not exist, it will print a message and exit.
"""
import json
import os
import sys
from collections import defaultdict


def main() -> None:
    filename = 'expenses.txt'
    if not os.path.exists(filename):
        print(f"No expenses file found at '{filename}'. Run the Java application first.")
        return
    try:
        with open(filename, 'r', encoding='utf-8') as f:
            data = json.load(f)
    except Exception as e:
        print(f"Failed to read {filename}: {e}")
        return
    if not isinstance(data, list):
        print(f"Unexpected data format in {filename} – expected a list of expenses.")
        return
    totals: dict[str, float] = defaultdict(float)
    for exp in data:
        try:
            category = exp.get('category', 'Uncategorised')
            amount = float(exp.get('amount', 0))
        except Exception:
            continue
        totals[category] += amount
    if not totals:
        print("No expenses recorded yet.")
        return
    print("Summary of expenses by category:")
    for cat, total in totals.items():
        print(f"  {cat}: {total:.2f}")


if __name__ == '__main__':
    main()