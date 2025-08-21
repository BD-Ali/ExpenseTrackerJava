# Expense Tracker

A simple command‑line expense tracker written in pure Java. This
project demonstrates how to build a small but complete application
without any external libraries or build tools. It persists data to
a simple text file so your recorded expenses are retained between runs.

## Features

- **Add new expenses** with a date, amount, category and description
- **List all recorded expenses** in the order they were entered
- **Summarise totals by category** to see where your money is going
- Data is saved to a local text file in a tab‑separated format
- Includes a tiny Python script to summarise expenses outside of the Java app

## Requirements

- **Java 17** or newer
- (Optional) **Python 3** to run the summary script

## Getting started

Clone or download this repository and change into its directory. There
is no need to use Maven or any build tool – the project is pure
Java and can be compiled with the JDK alone.

### Compiling

Use the `javac` compiler to build the application. From the project
root run:

```sh
javac -d out $(find src/main/java -name "*.java")
```

This will compile the sources to the `out` directory.

### Running the application

To run the program, execute the main class from the output
directory:

```sh
java -cp out com.example.expensetracker.ExpenseTracker
```

The program will create (or update) a file called `expenses.txt` in
the working directory to store your data. Feel free to open this
file in a text editor to inspect or back up your expenses.

### Using the summary script

After you have recorded some expenses you can quickly see how much
you’ve spent per category without starting the Java program. Run the
provided Python script:

```sh
python3 summarize_expenses.py
```

It will read `expenses.txt` in the current directory and print a
breakdown of totals by category.

## Contributing

Contributions are welcome! If you have ideas for improvements or
additional features feel free to open an issue or submit a pull
request.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.