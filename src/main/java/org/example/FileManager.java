package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static final String FILE_NAME = "transactions.csv";

        public static List<Transaction> getTransactions() {
            List<Transaction> list = new ArrayList<>();

            try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/Transactions.csv"))) {

                bufferedReader.readLine();

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] parts = line.split("\\|");

                    list.add(new Transaction(
                            parts[0],
                            parts[1],
                            parts[2],
                            parts[3],
                            Double.parseDouble(parts[4])
                    ));
                }

            } catch (IOException ex) {
                System.out.println("Error reading file");
            }

            return list;
        }

        public static void saveTransaction(Transaction transaction) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/Transactions.csv", true))) {
                bufferedWriter.write("\n" + transaction.toCSV());
            } catch (IOException ex) {
                System.out.println("Error writing file");
            }
        }
    }
