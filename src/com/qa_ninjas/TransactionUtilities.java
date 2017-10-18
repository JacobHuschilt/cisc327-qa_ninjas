package com.qa_ninjas;

import java.util.ArrayList;

/**
 * Stores list of all transactions to be written to TSF file, and holds common
 * validation methods used by subclasses.
 * Created by jacobhuschilt on 10/18/17.
 */
public class TransactionUtilities {
    // Properties

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
        return false;
    }

    /**
     * Updates the temporary transaction list being stored in memory.
     * @param code Transaction Code
     * @param fromAcctNum Valid From Account Number
     * @param amount Valid Amount to be transferred in cents
     * @param toAcctNum Valid To Account Number
     * @param name Valid name
     */
    protected void updateTransactionList(String code, int fromAcctNum, int amount,
                                         int toAcctNum, String name) {
        // TODO: update Transaction list
    }
}
