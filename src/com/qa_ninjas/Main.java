/**
 * Jacob Huschilt 10197679
 * Heni Virág 10142490
 * Gaveshini Sriyananda 10146093
 * <p>
 * Created: 09/16/2017
 * <p>
 * ©2017 QA_Ninjas
 */

package com.qa_ninjas;

import java.util.ArrayList;

/**
 * Main QBasic class, handles all I/O and delegation.
 */
public class Main {

    /**
     * Public Properties
     */
    static ArrayList<String> tsfChanges = new ArrayList();


    /**
     * QBasic Main Method.
     *
     * @param args 4 input arguments are expected/required/needed
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Error: Did not find 3 arguments (filenames)");
            return;
        }

        String validAccountsFilename = args[0];
        String tsfFilename = args[1];
        String inputFilename = args[2];

        AccountUtilities accountUtilities = new AccountUtilities();

        TransactionUtilities transactionUtilities = new TransactionUtilities();
        ArrayList<String> inputCommands = FileIO.readFile(inputFilename);

        boolean loggedIn = false;
        Session sessionType = Session.none;


        // Looping through the list of commands
        for (String command : inputCommands) {
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
                                accountUtilities.accountList = parseValidAccountsFileContents(FileIO.readFile(validAccountsFilename));
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
                        // Append an EOS line to the TSF file
                        Main.tsfChanges.add("EOS" + " " + "0000000" + " " + "000" + " " + "0000000" + " " + "***");
                        FileIO.writeToFile(tsfFilename, Main.tsfChanges);
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

    /**
     * Parses the validAccounts file contents into ValidAccount objects for use in the program.
     *
     * @param contents the file contents as a List of Strings
     * @return file contents as a list of ValidAccount objects
     */
    private static ArrayList<ValidAccount> parseValidAccountsFileContents(ArrayList<String> contents) {
        ArrayList<ValidAccount> accountList = new ArrayList();

        for (String account : contents) {
            if (account.equals("0000000")) { // end of file
                break;
            }

            try {
                int accountNum = Integer.parseInt(account);
                ValidAccount validAccount = new ValidAccount(accountNum, "", false);

                accountList.add(validAccount);
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid account in validAccounts file");
            }
        }

        return accountList;
    }
}
