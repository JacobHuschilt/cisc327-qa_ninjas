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
    static ArrayList<String> terminalOutput = new ArrayList();


    /**
     * QBasic Main Method.
     *
     * @param args 4 input arguments are expected/required/needed
     */
    public static void main(String[] args) {
        if (args.length != 4) {
            terminalOutput.add("Error: Did not find 4 arguments (filenames)");
            return;
        }

        String validAccountsFilename = args[0];
        String tsfFilename = args[1];
        String inputFilename = args[2];
        String outputFilename = args[3];

        AccountUtilities accountUtilities = new AccountUtilities();

        TransactionUtilities transactionUtilities = new TransactionUtilities();
        ArrayList<String> inputCommands = FileIO.readFile(inputFilename);

        boolean loggedIn = false;
        Session sessionType = Session.none;


        // Looping through the list of commands
        for (String command : inputCommands) {
            String[] splitCommand = command.split(",");

            if (!loggedIn && !splitCommand[0].equals("login")) {
                terminalOutput.add("Error! Not logged in");
            }

            switch (splitCommand[0]) {
                case "login": {
                    if (loggedIn) {
                        terminalOutput.add("Error: Already logged in!");
                    } else {
                        try {
                            if (splitCommand[1].equals("agent") || splitCommand[1].equals("machine")) {
                                loggedIn = true;
                                sessionType = Session.valueOf(splitCommand[1]);
                                accountUtilities.accountList = parseValidAccountsFileContents(FileIO.readFile(validAccountsFilename));
                            } else {
                                terminalOutput.add("Error: Invalid session type specified");
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            Main.terminalOutput.add("Error! Missing arguments for command: " + e);
                        }
                    }
                }
                case "logout": {
                    if (!loggedIn) {
                        terminalOutput.add("Error: Already Logged-out!");
                    } else {
                        loggedIn = false;
                        sessionType = Session.none;
                        // Append an EOS line to the TSF file
                        tsfChanges.add("EOS" + " " + "0000000" + " " + "000" + " " + "0000000" + " " + "***");
                        FileIO.writeToFile(tsfFilename, tsfChanges);
                        FileIO.writeToFile(outputFilename, terminalOutput);
                    }
                }
                case "createacct": {
                    if (sessionType != Session.agent) {
                        terminalOutput.add("Error! You can only create accounts in agent mode.");
                    } else {
                        try {
                            String acctNum = splitCommand[1];
                            String name = splitCommand[2];
                            accountUtilities.createAccount(acctNum, name);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            Main.terminalOutput.add("Error! Missing arguments for command: " + e);
                        }
                    }
                }
                case "deleteacct": {
                    if (sessionType != Session.agent) {
                        terminalOutput.add("Error! You can only create accounts in agent mode.");
                    } else {
                        try {
                            String acctNum = splitCommand[1];
                            String name = splitCommand[2];
                            accountUtilities.deleteAccount(acctNum, name);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            Main.terminalOutput.add("Error! Missing arguments for command: " + e);
                        }
                    }
                }
                case "transfer": {
                    try {
                        String toAcctNum = splitCommand[1];
                        String fromAcctNum = splitCommand[1];
                        String amount = splitCommand[1];
                        transactionUtilities.transfer(accountUtilities, toAcctNum, amount, fromAcctNum, sessionType);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        Main.terminalOutput.add("Error! Missing arguments for command: " + e);
                    }
                }
                case "deposit": {
                    try {
                        String toAcctNum = splitCommand[1];
                        String amount = splitCommand[1];
                        transactionUtilities.deposit(accountUtilities, toAcctNum, amount, sessionType);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        Main.terminalOutput.add("Error! Missing arguments for command: " + e);
                    }
                }
                case "withdraw": {
                    try {

                        String fromAcctNum = splitCommand[1];
                        String amount = splitCommand[1];
                        transactionUtilities.withdraw(accountUtilities, amount, fromAcctNum, sessionType);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        Main.terminalOutput.add("Error! Missing arguments for command: " + e);
                    }
                }
                default: {
                    terminalOutput.add("Error: Invalid command");
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
                terminalOutput.add("Error: Invalid account in validAccounts file");
            }
        }

        return accountList;
    }
}
