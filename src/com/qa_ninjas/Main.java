/**
 * Jacob Huschilt 10197679
 * Heni Virág
 * Gaveshini Sriyananda
 * <p>
 * Created: 09/16/2017
 * <p>
 * ©2017 QA_Ninjas
 */

package com.qa_ninjas;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Main QBasic class, handles all I/O and delegation.
 */
public class Main {
    /**
     * QBasic Main Method.
     * @param args 3 input arguments are expected/required/needed
     */
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Error: Did not find 3 arguments");
            return;
        }

        String validAccountsFilename = args[0];
        String inputFilename = args[1];
        String outputFilename = args[2];

        AccountUtilities accountUtilities = new AccountUtilities();
        accountUtilities.accountList = FileIO.readFile(validAccountsFilename);

        TransactionUtilities transactionUtilities = new TransactionUtilities();
        ArrayList<String> inputCommands = FileIO.readFile(inputFilename);

        ArrayList<String> tsfChanges = new ArrayList();

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
                        loggedIn = true;
                        if (splitCommand[1].equals("agent") || splitCommand[1].equals("machine")) {
                            sessionType = Session.valueOf(splitCommand[1]);
                        } else {
                            System.out.println("Error: Invalid session type specified");
                        }
                    }
                }
                case "logout": {
                    if (!loggedIn) {
                        System.out.println("Error: Already Logged-out!");
                    } else {
                        loggedIn = false;
                        sessionType = Session.none;
                        // TODO: Write to TSF File
                    }
                }
                case "createacct": {
                    if (sessionType != Session.agent) {
                        System.out.println("Error! You can only create accounts in agent mode.");
                    } else {
                        String acctNum = splitCommand[1];
                        String name = splitCommand[2];
                        accountUtilities.createAccount(acctNum, name);
                    }
                }
                case "deleteacct": {
                    if (sessionType != Session.agent) {
                        System.out.println("Error! You can only create accounts in agent mode.");
                    } else {
                        String acctNum = splitCommand[1];
                        String name = splitCommand[2];
                        accountUtilities.deleteAccount(acctNum, name);
                    }
                }
                case "transfer": {
                    String toAcctNum = splitCommand[1];
                    String fromAcctNum = splitCommand[1];
                    String amount = splitCommand[1];
                    transactionUtilities.transfer(accountUtilities, toAcctNum, amount, fromAcctNum, sessionType);
                }
                case "deposit": {
                    String toAcctNum = splitCommand[1];
                    String amount = splitCommand[1];
                    transactionUtilities.deposit(accountUtilities, toAcctNum, amount, sessionType);
                }
                case "withdraw": {
                    String fromAcctNum = splitCommand[1];
                    String amount = splitCommand[1];
                    transactionUtilities.withdraw(accountUtilities, amount, fromAcctNum, sessionType);
                }
                default: {
                    System.out.println("Error: Invalid command");
                    continue;
                }
            }
        }


        // TODO: write to commandLineOutputFile

        // TODO: write to TSFFile
    }
}
