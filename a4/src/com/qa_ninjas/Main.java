package com.qa_ninjas;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Main class file to delegate for the QBasic Back Office.
 * <p>
 * Created by jacobhuschilt on 11/13/17.
 * ©2017 QA_Ninjas
 */
public class Main {

    /**
     * Global Properties
     */
    private static AccountUtilities accountUtilities = new AccountUtilities();
    private static TransactionUtilities transactionUtilities = new TransactionUtilities();

    /**
     * Main method for the QBasic Back Office.
     *
     * @param args [0] old master accounts filename,
     *             [1] merged transaction summary filename,
     *             [2] new master accounts filename,
     *             [3] new valid accounts filename
     */
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Fatal Error: Need 4 arguments");
            return;
        }

        String oldMasterAccountsFilename = args[0];
        String mergedTSFilename = args[1];
        String newMasterAccountsFilename = args[2];
        String newValidAccountsFilename = args[3];

        try {
            accountUtilities.accountList = parseMasterAccountsFileContents(FileIO.readFile(oldMasterAccountsFilename));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error! Missing arguments for command: " + e);
        }

        ArrayList<String> transactionsToBeExecuted = FileIO.readFile(mergedTSFilename);

        // Loop through the TSF file of commands to be processed
        handleTSFileCommands(transactionsToBeExecuted);

        // Write the new files after program execution is complete
        writeNewMasterAccountsFile(newMasterAccountsFilename, accountUtilities.accountList);
        writeNewValidAccountsFile(newValidAccountsFilename, accountUtilities.accountList);
    }

    /**
     * Loops through the commands from the merged transaction summary file and process them.
     *
     * @param lines List of un-parsed commands
     */
    private static void handleTSFileCommands(ArrayList<String> lines) {
        // Looping through the list of lines
        for (String commandLine : lines) {
            String[] splitCommand = commandLine.split(" ", 5);

            try {
                String command = splitCommand[0];
                String toAcctNum = splitCommand[1];
                String amount = splitCommand[2];
                String fromAcctNum = splitCommand[3];
                String name = splitCommand[4];

                switch (command) {
                    case "NEW": {
                        accountUtilities.createAccount(toAcctNum, name);
                        break;
                    }
                    case "DEL": {
                        accountUtilities.deleteAccount(toAcctNum, name);
                        break;
                    }
                    case "XFR": {
                        transactionUtilities.transfer(accountUtilities, toAcctNum, amount, fromAcctNum);
                        break;
                    }
                    case "DEP": {
                        transactionUtilities.deposit(accountUtilities, toAcctNum, amount);
                        break;
                    }
                    case "WDR": {
                        transactionUtilities.withdraw(accountUtilities, amount, fromAcctNum);
                        break;
                    }
                    case "EOS": {
                        continue;
                    }
                    default: {
                        System.out.println("Error: Invalid command");
                        break;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Fatal Error! Invalid input file.");
                return;
            }
        }
    }

    /**
     * Writes to the master accounts file given a filename and account list.
     *
     * @param filename    filename as a String
     * @param accountList list of accounts to write
     */
    private static void writeNewMasterAccountsFile(String filename, ArrayList<ValidAccount> accountList) {
        ArrayList<String> masterAccountsFileList = new ArrayList<>(accountList.size());

        // sort the accounts
        Collections.sort(accountList);

        for (ValidAccount account : accountList) {
            masterAccountsFileList.add(account.getAcctNum() + " " + account.getAcctBalance() + " " + account.getName());
        }

        FileIO.writeToFile(filename, masterAccountsFileList);
    }

    /**
     * Writes a new valid accounts file given a filename and list of accounts to record.
     *
     * @param filename      String representing the filename to be written to
     * @param validAccounts ArrayList of valid accounts
     */
    private static void writeNewValidAccountsFile(String filename, ArrayList<ValidAccount> validAccounts) {
        ArrayList<String> linesToWrite = new ArrayList<>(validAccounts.size());

        for (ValidAccount account : validAccounts) {
            linesToWrite.add(account.getAcctNum() + "");
        }

        linesToWrite.add("0000000");

        FileIO.writeToFile(filename, linesToWrite);
    }

    /**
     * Parses the master accounts file contents into ValidAccount objects for use in the program.
     *
     * @param contents  file contents as a List of Strings
     * @return          file contents as a list of ValidAccount objects
     */
    private static ArrayList<ValidAccount> parseMasterAccountsFileContents(ArrayList<String> contents) {
        ArrayList<ValidAccount> accountList = new ArrayList<>();

        for (String account : contents) {
            String[] accountInformation = account.split(" ", 3);

            try {
                int accountNum = Integer.parseInt(accountInformation[0]);
                int accountBalance = Integer.parseInt(accountInformation[1]);
                String accountName = accountInformation[2];

                ValidAccount validAccount = new ValidAccount(accountNum, accountBalance, accountName, false);

                accountList.add(validAccount);
            } catch (Exception e) {
                System.out.println("Error: Invalid account in master accounts file");
            }
        }

        return accountList;
    }
}
