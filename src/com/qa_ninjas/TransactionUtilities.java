package com.qa_ninjas;

import java.util.ArrayList;

/**
 * Stores list of all transactions to be written to TSF file, and holds common
 * validation methods used by subclasses.
 * Created by jacobhuschilt on 10/18/17.
 */
public class TransactionUtilities {

    // Properties
    // TODO: Move to Main, and combine with account list modifications
    private ArrayList<String> transactionList;


    /**
     * Constructor for TransactionUtilities
     *
     * @param transactionList List of existing transactions
     */
    public TransactionUtilities(ArrayList<String> transactionList) {
        this.transactionList = transactionList;
    }


    /**
     * Validates a specified amount for a specified sessionType.
     *
     * @param amount      Amount to be transferred
     * @param sessionType The current sessionType as an enum
     * @return true if the specified amount is valid for a given sessionType, and false otherwise
     */
    protected boolean isValidAmount(String amount, Session sessionType) {
        // making sure that the amount input is actually a number
        try {
            int amountInt = (Integer.parseInt(amount));
            if (sessionType == Session.AGENT) {
                if (amountInt > 99999999) {
                    return false;
                } else if (sessionType == Session.MACHINE) {
                    if (amountInt > 100000) {
                        return false;
                    }
                }
            }
            return true;
        } catch (NumberFormatException exception) {
            System.out.println("Error! Amount is invalid.");
            return false;
        }
    }


    /**
     * Updates the temporary transaction list being stored in memory.
     *
     * @param code        Transaction Code
     * @param fromAcctNum Valid From Account Number
     * @param amount      Valid Amount to be transferred in cents
     * @param toAcctNum   Valid To Account Number
     * @param name        Valid name
     */
    protected void updateTransactionList(String code, String toAcctNum, String amount,
                                         String fromAcctNum, String name) {
        transactionList.add(code + " " + toAcctNum + " " + amount + " " + fromAcctNum + " " + name);
        // TODO: Move list to main, and combine with accounts
    }


    protected void transfer(String toAcctNum, String amount, String fromAcctNum, String name, Session sessionType) {
        if (!isValidAmount(amount, sessionType)) {
            // either out of range or input isn't a number
            System.out.println("Error! Amount input is not valid.");
        } else {
            updateTransactionList("XFR", toAcctNum, amount, fromAcctNum, name);
        }
    }

    protected void withdraw(String amount, String fromAcctNum, String name, Session sessionType) {
        if (!isValidAmount(amount, sessionType)) {
            // either out of range or input isn't a number
            System.out.println("Error! Amount input is not valid.");
        } else {
            updateTransactionList("WDR", "0000000", amount, fromAcctNum, name);
        }
    }

    protected void deposit(String toAcctNum, String amount, String name, Session sessionType) {
        if (!isValidAmount(amount, sessionType)) {
            // either out of range or input isn't a number
            System.out.println("Error! Amount input is not valid.");
        } else {
            updateTransactionList("DEP", toAcctNum, amount, "0000000", name);
        }
    }
}
