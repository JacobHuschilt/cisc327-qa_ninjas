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
     * @param transactionList List of existing transactions
     */
    public TransactionUtilities(ArrayList<String> transactionList) {
        this.transactionList = transactionList;
    }


    /**
     * Validates a specified amount for a specified sessionType.
     * @param amount Amount to be transferred
     * @param sessionType The current sessionType as an enum
     * @return true if the specified amount is valid for a given sessionType, and false otherwise
     */
    protected boolean isValidAmount(int amount, Session sessionType) {
        // TODO: Check to see if amount is valid
        if (sessionType == Session.AGENT) {
            if (amount > 99999999) {
                return false;
            } else if (sessionType == Session.MACHINE) {
                if (amount > 100000) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Updates the temporary transaction list being stored in memory.
     * @param code Transaction Code
     * @param fromAcctNum Valid From Account Number
     * @param amount Valid Amount to be transferred in cents
     * @param toAcctNum Valid To Account Number
     * @param name Valid name
     */
    protected void updateTransactionList(String code, int toAcctNum, int amount,
                                         int fromAcctNum, String name) {
//        if (code == "XFR") {
//            String transfer =
//
//        }
        // TODO: update Transaction list
    }


    protected void transfer(String fromAcctNum, String amount, String toAcctNum) {
        // TODO: validate number and amount, then transfer and update list, then record the transaction in the
        // TODO: cont'd ^^^^ Main class so we can have a record to write to the file upon logout
    }

    protected void withdraw(String fromAcctNum, String amount) {
        // TODO: validate number and amount, then withdraw and update list, then record the transaction in the
        // TODO: cont'd ^^^^ Main class so we can have a record to write to the file upon logout
    }

    protected void deposit(String toAcctNum, String amount) {
        // TODO: validate number and amount, then deposit and update list, then record the transaction in the
        // TODO: cont'd ^^^^ Main class so we can have a record to write to the file upon logout
    }
}
