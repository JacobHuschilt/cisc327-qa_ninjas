package com.qa_ninjas;

import java.util.ArrayList;
import java.util.function.IntBinaryOperator;

/**
 * Stores list of all transactions to be written to TSF file, and holds common
 * validation methods used by subclasses.
 * Created by jacobhuschilt on 10/18/17.
 */
public class TransactionUtilities {
    /**
     * Validates a specified amount for a specified sessionType.
     *
     * @param amount      Amount to be transferred
     * @param sessionType The current sessionType as an enum
     * @return true if the specified amount is valid for a given sessionType, and false otherwise
     */
    private boolean isValidAmount(String amount, Session sessionType) {
        // making sure that the amount input is actually a number
        try {
            int amountInt = (Integer.parseInt(amount));
            if (sessionType == Session.agent) {
                if (amountInt > 99999999) {
                    return false;
                }
            } else if (sessionType == Session.machine) {
                if (amountInt > 100000) {
                    return false;
                }
            }
            return true;
        } catch (
                NumberFormatException exception)

        {
            Main.terminalOutput.add("Error! Amount is invalid.");
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
    private void updateTransactionList(String code, String toAcctNum, String amount,
                                       String fromAcctNum, String name) {
        Main.tsfChanges.add(code + " " + toAcctNum + " " + amount + " " + fromAcctNum + " " + name);
    }

    /**
     * Transfers an amount for the required given parameters after validating.
     *
     * @param accountUtilities AccountUtilities reference
     * @param toAcctNum        un-verified account number to be used
     * @param amount           un-verified amount to be transferred
     * @param fromAcctNum      un-verified account number to be used
     * @param sessionType      session type
     */
    void transfer(AccountUtilities accountUtilities, String toAcctNum, String amount, String fromAcctNum, Session sessionType) {
        if (!isValidAmount(amount, sessionType)) {
            // either out of range or input isn't a number
            Main.terminalOutput.add("Error! Amount input is invalid.");
        } else if (!AccountUtilities.isValidAcct(toAcctNum) || !AccountUtilities.isValidAcct(fromAcctNum)) {
            Main.terminalOutput.add("Error! Account number(s) are invalid.");
        } else if (accountUtilities.isNewAccount(Integer.parseInt(toAcctNum)) || accountUtilities.isNewAccount(Integer.parseInt(fromAcctNum))) {
            Main.terminalOutput.add("Error! No transactions are allowed on new accounts.");
        } else {
            updateTransactionList("XFR", toAcctNum, amount, fromAcctNum, "");
        }
    }

    /**
     * Withdraws an amount for the required given parameters after validating.
     *
     * @param accountUtilities AccountUtilities reference
     * @param amount           un-verified amount to be withdrawn
     * @param fromAcctNum      un-verified account number to be used
     * @param sessionType      session type
     */
    void withdraw(AccountUtilities accountUtilities, String amount, String fromAcctNum, Session sessionType) {
        if (!isValidAmount(amount, sessionType)) {
            // either out of range or input isn't a number
            Main.terminalOutput.add("Error! Amount input is not valid.");
        } else if (!AccountUtilities.isValidAcct(fromAcctNum)) {
            Main.terminalOutput.add("Error! Account number is invalid.");
        } else if (accountUtilities.isNewAccount(Integer.parseInt(fromAcctNum))) {
            Main.terminalOutput.add("Error! No transactions are allowed on new accounts.");
        } else {
            updateTransactionList("WDR", "0000000", amount, fromAcctNum, "");
        }
    }

    /**
     * Deposits an amount for the required given parameters after validating.
     *
     * @param accountUtilities AccountUtilities reference
     * @param toAcctNum        un-verified account number to be used
     * @param amount           un-verified amount to be deposited
     * @param sessionType      session type
     */
    void deposit(AccountUtilities accountUtilities, String toAcctNum, String amount, Session sessionType) {
        if (!isValidAmount(amount, sessionType)) {
            // either out of range or input isn't a number
            Main.terminalOutput.add("Error! Amount input is not valid.");
        } else if (!AccountUtilities.isValidAcct(toAcctNum)) {
            Main.terminalOutput.add("Error! Account number is invalid.");
        } else if (accountUtilities.isNewAccount(Integer.parseInt(toAcctNum))) {
            Main.terminalOutput.add("Error! No transactions are allowed on new accounts.");
        } else {
            updateTransactionList("DEP", toAcctNum, amount, "0000000", "");
        }
    }
}
