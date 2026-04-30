package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n--- HOME ---");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "D":
                    addTransaction(true);
                    break;
                case "P":
                    addTransaction(false);
                    break;
                case "L":
                    showLedger();
                    break;
                case "X":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }


    public static void addTransaction(boolean isDeposit) {

        System.out.print("Description: ");
        String desc = scanner.nextLine();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine();

        double amount = 0;

        while (true) {
            try {
                System.out.print("Amount: ");
                amount = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }

        if (!isDeposit) {
            amount *= -1;
        }

        Transaction transaction = new Transaction(
                LocalDate.now().toString(),
                LocalTime.now().withNano(0).toString(),
                desc,
                vendor,
                amount
        );

        FileManager.saveTransaction(transaction);
        System.out.println("Transaction saved!");
    }


    public static void showLedger() {

        List<Transaction> list = FileManager.getTransactions();

        while (true) {
            System.out.println("\n--- LEDGER ---");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "A":
                    showAll(list);
                    break;
                case "D":
                    showDeposits(list);
                    break;
                case "P":
                    showPayments(list);
                    break;
                case "R":
                    showReports(list);
                    break;
                case "H":
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }


    public static void showAll(List<Transaction> list) {

        Collections.sort(list, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction a, Transaction b) {
                return b.getDate().compareTo(a.getDate());
            }
        });

        for (Transaction transaction : list) {
            System.out.println(transaction);
        }
    }


    public static void showDeposits(List<Transaction> list) {

        for (Transaction t : list) {
            if (t.getAmount() > 0) {
                System.out.println(t);
            }
        }
    }

    public static void showPayments(List<Transaction> list) {

        for (Transaction transaction : list) {
            if (transaction.getAmount() < 0) {
                System.out.println(transaction);
            }
        }
    }

    // Report Section
    public static void showReports(List<Transaction> list) {

        System.out.println("\n--- REPORTS ---");
        System.out.println("2) Previous Month");
        System.out.println("4) Previous Year");
        System.out.println("0) Back");

        String choice = scanner.nextLine();

        switch (choice) {
            case "2":
                previousMonth(list);
                break;
            case "4":
                previousYear(list);
                break;
        }
    }

    //Month here
    public static void previousMonth(List<Transaction> list) {

        String month = "2024-02";

        for (Transaction transaction : list) {
            if (transaction.getDate().startsWith(month)) {
                System.out.println(transaction);
            }
        }
    }

    // Year here
    public static void previousYear(List<Transaction> list) {

        String year = "2024";

        for (Transaction transaction : list) {
            if (transaction.getDate().startsWith(year)) {
                System.out.println(transaction);
            }
        }
    }
}