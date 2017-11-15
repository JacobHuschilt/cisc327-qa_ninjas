package com.qa_ninjas;

/**
 * Stores list of all transactions to be written to TSF file, and holds common
 * validation methods used by subclasses.
 * Created by jacobhuschilt on 10/18/17.
 */
public class TransactionUtilities {

    /**
     * Global Properties
     */
    private static final int NO_ACCOUNT = -1;

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
        } catch (NumberFormatException exception) {
            System.out.println("Error! Amount is invalid.");

            return false;
        }

    }

    private boolean isValidTransaction(AccountUtilities accountUtilities, int fromAcctNum, int toAcctNum, int amount) {
        if (fromAcctNum == NO_ACCOUNT && toAcctNum == NO_ACCOUNT) { // transfer

            return false;
        } else if (fromAcctNum == NO_ACCOUNT) { // deposit

            return false;
        } else if (toAcctNum == NO_ACCOUNT) { // withdraw

            return false;
        } else { // invalid transaction
            System.out.println("Error: Invalid transaction");

            return false;
        }
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
            System.out.println("Error! Amount input is invalid.");
        } else if (!AccountUtilities.isValidAcct(toAcctNum) || !AccountUtilities.isValidAcct(fromAcctNum)) {
            System.out.println("Error! Account number(s) are invalid.");
        } else if (accountUtilities.isNewAccount(Integer.parseInt(toAcctNum)) || accountUtilities.isNewAccount(Integer.parseInt(fromAcctNum))) {
            System.out.println("Error! No transactions are allowed on new accounts.");
        } else {
            // TODO: Check to see that the transfer does not give anyone a negative account balance
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
            System.out.println("Error! Amount input is not valid.");
        } else if (!AccountUtilities.isValidAcct(fromAcctNum)) {
            System.out.println("Error! Account number is invalid.");
        } else if (accountUtilities.isNewAccount(Integer.parseInt(fromAcctNum))) {
            System.out.println("Error! No transactions are allowed on new accounts.");
        } else {
            if (sessionType == Session.machine) {
                boolean accountWithdrawLimitReached = accountUtilities.updateAtmAmountWithdrawn(Integer.parseInt(fromAcctNum), Integer.parseInt(amount));
                if (!accountWithdrawLimitReached) {
                    return;
                }
            }
            // TODO: Check to see that the transfer does not give anyone a negative account balance
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
            System.out.println("Error! Amount input is not valid.");
        } else if (!AccountUtilities.isValidAcct(toAcctNum)) {
            System.out.println("Error! Account number is invalid.");
        } else if (accountUtilities.isNewAccount(Integer.parseInt(toAcctNum))) {
            System.out.println("Error! No transactions are allowed on new accounts.");
        } else {
            // TODO: Check to see that the transfer does not give anyone a negative account balance
            updateTransactionList("DEP", toAcctNum, amount, "0000000", "");
        }
    }
}
