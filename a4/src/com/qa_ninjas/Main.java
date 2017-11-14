package com.qa_ninjas;

import java.io.File;
import java.util.ArrayList;

/**
 * Main class file to delegate for the QBasic Back Office.
 *
 * Created by jacobhuschilt on 11/13/17.
 * ©2017 QA_Ninjas
 */
public class Main {

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

        AccountUtilities accountUtilities = new AccountUtilities();
        TransactionUtilities transactionUtilities = new TransactionUtilities();


        // read the 2 files
        try {
            accountUtilities.accountList = parseValidAccountsFileContents(FileIO.readFile(masterAccountsFilename));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error! Missing arguments for command: " + e);
        }
        ArrayList<String> masterAccountList = FileIO.readFile(masterAccountsFilename);
        ArrayList<String> transactionsToBeExecuted = FileIO.readFile(mergedTSFilename);

        // TODO: Output a new Master Accounts file
        
        // TODO: Output failures to the console
    }

    ArrayList<String> formatMasterAccountsList(ArrayList<String> accountList) {
        ArrayList<String> masterAccountsFileList = new ArrayList<>(accountList.size());

        return masterAccountsFileList;
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

    private static ArrayList<St>
}
