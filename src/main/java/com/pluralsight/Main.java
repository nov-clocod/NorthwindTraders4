package com.pluralsight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Error on required application details to run");
            System.exit(1);
        }

        String username = args[0];
        String password = args[1];

        Scanner myScanner = new Scanner(System.in);
        try {
            boolean isDone = false;

            while (!isDone) {
                System.out.println("\nWhat do you want to do?");
                System.out.println("  1) Display all products");
                System.out.println("  2) Display all customers");
                System.out.println("  0) Exit");
                System.out.println("Select an option:");
                String userInput = myScanner.nextLine().trim();

                int userChoice = Integer.parseInt(userInput);

                switch (userChoice) {
                    case 1:
                        displayAllProducts(username, password);
                        break;
                    case 2:
                        displayAllCustomers(username, password);
                        break;
                    case 0:
                        isDone = true;
                        break;
                    default:
                        System.out.println("Invalid Choice!");
                        break;
                }
            }
        } catch (Exception ex) {
            System.out.println("Please check your inputs!");
            System.out.println(ex.getMessage());
        } finally {
            myScanner.close();
        }
    }

    public static void displayAllProducts(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet results = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/northwind",
                    username,
                    password
            );

            String query = """
                    SELECT ProductID, ProductName, UnitPrice, UnitsInStock
                    FROM products
                    """;

            preparedStatement = connection.prepareStatement(query);

            results = preparedStatement.executeQuery();

            System.out.println("\nID  " + " Name" + " ".repeat(37) + "Price   " + "Stock");
            System.out.println("---- " + "-".repeat(40) + " -------" + " -----");

            while (results.next()) {
                int productID = results.getInt(1);
                String productName = results.getString(2);
                double productPrice = results.getDouble(3);
                int productStock = results.getInt(4);

                System.out.printf("%-4d %-40s %-7.2f %-5d\n",
                        productID, productName, productPrice, productStock);
            }
        } catch (Exception ex) {
            System.out.println("Error occur!");
            System.out.println(ex.getMessage());
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (Exception ex) {
                    System.out.println("Error closing 'results' that was opened");
                    System.out.println(ex.getMessage());
                }
            }

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (Exception ex) {
                    System.out.println("Error closing 'preparedStatement' that was opened");
                    System.out.println(ex.getMessage());
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ex) {
                    System.out.println("Error closing an connection that was opened");
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    public static void displayAllCustomers(String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet results = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/northwind",
                    username,
                    password);

            String query = """
                    SELECT ContactName, CompanyName, City, Country, Phone
                    FROM customers
                    ORDER BY Country
                    """;

            preparedStatement = connection.prepareStatement(query);

            results = preparedStatement.executeQuery();

            System.out.println("\nContact Name" + " ".repeat(19)
                    + "Company" + " ".repeat(34)
                    + "City" + " ".repeat(12)
                    + "Country" + " ".repeat(9)
                    + "Phone" + " ".repeat(7));
            System.out.println("-".repeat(30) + " "
                    + "-".repeat(40) + " "
                    + "-".repeat(15) + " "
                    + "-".repeat(15) + " "
                    + "-".repeat(12));

            while (results.next()) {
                String contactName = results.getString(1);
                String companyName = results.getString(2);
                String city = results.getString(3);
                String country = results.getString(4);
                String phone = results.getString(5);

                System.out.printf("%-30s %-40s %-15s %-15s %-12s\n",
                        contactName, companyName, city, country, phone);
            }
        } catch (Exception ex) {
            System.out.println("Error occur!");
            System.out.println(ex.getMessage());
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (Exception ex) {
                    System.out.println("Error closing 'results' that was opened");
                    System.out.println(ex.getMessage());
                }
            }

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (Exception ex) {
                    System.out.println("Error closing 'preparedStatement' that was opened");
                    System.out.println(ex.getMessage());
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ex) {
                    System.out.println("Error closing an connection that was opened");
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}
