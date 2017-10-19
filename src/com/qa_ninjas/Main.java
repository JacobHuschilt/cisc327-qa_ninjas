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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

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

        ArrayList<String> inputCommands = FileIO.readFile(inputFilename);

        boolean loggedIn = false;
        String sessionType = "";


        // Looping through the list of commands
        for (String command : inputCommands) {
            String[] splitCommand = command.split(",");

            switch (splitCommand[0]) {
                case "login": {
                    if (loggedIn) {
                        System.out.println("Error: Already logged in!");
                    } else {
                        loggedIn = true;
                        if (splitCommand[1].equals("agent") || splitCommand[1].equals("machine")) {
                            sessionType = splitCommand[1];
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
                        sessionType = "";
                    }
                }
                case "createacct": {
                    // TODO: Stuff
                }
                case "deleteacct": {
                    // TODO: Stuff
                }
                case "transfer": {
                    // TODO: Stuff
                }
                case "deposit": {
                    // TODO: Stuff
                }
                case "withdraw": {
                    // TODO: Stuff
                }
                default: {
                    System.out.println("Error: Invalid command");
                    continue;
                }
            }
        }


        // TODO: write to commandLineOutputFile

        // TODO: write to TSFFile

        // TODO: REMOVE THIS TESTING CODE!!!
        testAccountUtilities();
    }

    // TODO: REMOVE THIS TESTING CODE
    public static void testAccountUtilities() {
        AccountUtilities accountUtilities = new AccountUtilities();

        System.out.println("Testing isValidAcct with 0123456 (should return false): " + accountUtilities.isValidAcct("0123456"));
        System.out.println("Testing isValidAcct with 1234567 (should return true): " + accountUtilities.isValidAcct("1234567"));
    }
}
