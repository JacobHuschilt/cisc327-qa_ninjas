package com.qa_ninjas;

import java.util.ArrayList;

/**
 * Main class file to delegate for the QBasic Back Office.
 *
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
     * Main method for the QBAsic Back Office.
     *
     * @param args [0] master accounts filename, [1] merged transaction summary filename
     */
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Fatal Error: Need 2 arguments");
            return;
        }

        String masterAccountsFilename = args[0];
        String mergedTSFilename = args[1];


        // read the 2 files
        try {
            accountUtilities.accountList = parseMasterAccountsFileContents(FileIO.readFile(masterAccountsFilename));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error! Missing arguments for command: " + e);
        }
        ArrayList<String> masterAccountList = FileIO.readFile(masterAccountsFilename);
        ArrayList<String> transactionsToBeExecuted = FileIO.readFile(mergedTSFilename);

        handleTSFileCommands(transactionsToBeExecuted);

        // TODO: Output a new Master Accounts file
        
        // TODO: Output failures to the console
    }

    private static void handleTSFileCommands(ArrayList<String> commands) {
        boolean loggedIn = false;
        Session sessionType = Session.none;

        // Looping through the list of commands
        for (String command : commands) {
            String[] splitCommand = command.split(",");

            if (!loggedIn && !splitCommand[0].equals("login")) {
                System.out.println("Error! Not logged in");
            }

            switch (splitCommand[0]) {
                case "login": {
                    if (loggedIn) {
                        System.out.println("Error: Already logged in!");
                    } else {
                        try {
                            if (splitCommand[1].equals("agent") || splitCommand[1].equals("machine")) {
                                loggedIn = true;
                                sessionType = Session.valueOf(splitCommand[1]);
                            } else {
                                System.out.println("Error: Invalid session type specified.");
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Error! Missing arguments for command: " + e);
                        }
                    }
                    break;
                }
                case "logout": {
                    if (!loggedIn) {
                        System.out.println("Error: Already Logged-out!");
                    } else {
                        loggedIn = false;
                        sessionType = Session.none;
                    }
                    break;
                }
                case "createacct": {
                    if (sessionType != Session.agent) {
                        System.out.println("Error! You can only create accounts in agent mode.");
                    } else {
                        try {
                            String acctNum = splitCommand[1];
                            String name = splitCommand[2];
                            accountUtilities.createAccount(acctNum, name);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Error! Missing arguments for command: " + e);
                        }
                    }
                    break;
                }
                case "deleteacct": {
                    if (sessionType != Session.agent) {
                        System.out.println("Error! You can only delete accounts in agent mode.");
                    } else {
                        try {
                            String acctNum = splitCommand[1];
                            String name = splitCommand[2];
                            accountUtilities.deleteAccount(acctNum, name);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("Error! Missing arguments for command: " + e);
                        }
                    }
                    break;
                }
                case "transfer": {
                    try {
                        String toAcctNum = splitCommand[1];
                        String fromAcctNum = splitCommand[2];
                        String amount = splitCommand[3];
                        transactionUtilities.transfer(accountUtilities, toAcctNum, amount, fromAcctNum, sessionType);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Error! Missing arguments for command: " + e);
                    }
                    break;
                }
                case "deposit": {
                    try {
                        String toAcctNum = splitCommand[1];
                        String amount = splitCommand[2];
                        transactionUtilities.deposit(accountUtilities, toAcctNum, amount, sessionType);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Error! Missing arguments for command: " + e);
                    }
                    break;
                }
                case "withdraw": {
                    try {
                        String fromAcctNum = splitCommand[1];
                        String amount = splitCommand[2];
                        transactionUtilities.withdraw(accountUtilities, amount, fromAcctNum, sessionType);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Error! Missing arguments for command: " + e);
                    }
                    break;
                }
                default: {
                    System.out.println("Error: Invalid command");
                    break;
                }
            }
        }
    }

    private static ArrayList<String> formatMasterAccountsList(ArrayList<String> accountList) {
        ArrayList<String> masterAccountsFileList = new ArrayList<>(accountList.size());

        return masterAccountsFileList;
    }

    /**
     * Parses the master accounts file contents into ValidAccount objects for use in the program.
     *
     * @param contents the file contents as a List of Strings
     * @return file contents as a list of ValidAccount objects
     */
    private static ArrayList<ValidAccount> parseMasterAccountsFileContents(ArrayList<String> contents) {
        ArrayList<ValidAccount> accountList = new ArrayList();

        for (String account : contents) {
            String[] accountInformation = account.split(" ");

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
