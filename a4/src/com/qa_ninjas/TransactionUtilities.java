package com.qa_ninjas;

/**
 * Class to handle all transaction related operations, including validation, and execution.
 *
 * Created by jacobhuschilt on 11/10/17.
 */
public class TransactionUtilities {

    /**
     * Validates a specified amount.
     *
     * @param amount    Amount to be transferred
     * @return          true if the specified amount is valid, and false otherwise
     */
    private boolean isValidAmount(String amount) {
        // making sure that the amount input is actually a number
        try {
            int amountInt = (Integer.parseInt(amount));

            return true;
        } catch (NumberFormatException exception) {
            System.out.println("Error! Amount is invalid.");

            return false;
        }

    }

    /**
     * Validates that a transaction will not leave a negative balance in an account (invalid).
     *
     * @param accountUtilities  accountUtilities reference
     * @param fromAcctNum       a valid account number
     * @param amount            a valid amount
     * @return                  true if the transaction does not result in a negative account balance, false otherwise
     */
    private boolean isValidTransaction(AccountUtilities accountUtilities, int fromAcctNum, int amount) {
        try {
            ValidAccount fromAccount = accountUtilities.getAccountFromList(fromAcctNum);

            if (fromAccount.getAcctBalance() - amount < 0) {
                System.out.println("Error: Cannot have a negative balance after transaction.");
                return false;
            }

            return true;
        } catch (NullPointerException e) {
            System.out.println("Error: Account Does not exist.");
            return false;
        }
    }

    /**
     * Updates the account balances after a transaction is verified externally, using the specified amount and transaction type.
     *
     * @param accountUtilities  AccountUtilities reference
     * @param transactionType   transaction type as an enum reference
     * @param fromAcctNum       valid from account number or 0 if not applicable
     * @param toAcctNum         valid to account number or 0 if not applicable
     * @param amount            valid amount
     */
    private void updateAccountBalancesForTransaction(AccountUtilities accountUtilities, Transaction transactionType,
                                                     int fromAcctNum, int toAcctNum, int amount) {
        ValidAccount fromAccount, toAccount;

        switch (transactionType) {
            case transfer: {
                fromAccount = accountUtilities.getAccountFromList(fromAcctNum);
                toAccount = accountUtilities.getAccountFromList(toAcctNum);

                fromAccount.setAcctBalance(fromAccount.getAcctBalance() - amount);
                toAccount.setAcctBalance(toAccount.getAcctBalance() + amount);

                break;
            }
            case deposit: {
                toAccount = accountUtilities.getAccountFromList(toAcctNum);
                if (toAccount == null) {
                    System.out.println("Error: To Account not found");
                    break;
                }
                toAccount.setAcctBalance(toAccount.getAcctBalance() + amount);

                break;
            }
            case withdraw: {
                fromAccount = accountUtilities.getAccountFromList(fromAcctNum);
                if (fromAccount == null) {
                    System.out.println("Error: From Account not found");
                    break;
                }

                fromAccount.setAcctBalance(fromAccount.getAcctBalance() - amount);

                break;
            }
        }
    }

    /**
     * Transfers an amount for the required given parameters after validating.
     *
     * @param accountUtilities AccountUtilities reference
     * @param toAcctNum        un-verified account number to be used
     * @param amount           un-verified amount to be transferred
     * @param fromAcctNum      un-verified account number to be used
     */
    void transfer(AccountUtilities accountUtilities, String toAcctNum, String amount, String fromAcctNum) {
        if (!isValidAmount(amount)) {
            // either out of range or input isn't a number
            System.out.println("Error! Amount input is invalid.");
        } else if (!AccountUtilities.isValidAcct(toAcctNum) || !AccountUtilities.isValidAcct(fromAcctNum)) {
            System.out.println("Error! Account number(s) are invalid.");
        } else if (accountUtilities.isNewAccount(Integer.parseInt(toAcctNum)) || accountUtilities.isNewAccount(Integer.parseInt(fromAcctNum))) {
            System.out.println("Error! No transactions are allowed on new accounts.");
        } else if (!isValidTransaction(accountUtilities, Integer.parseInt(fromAcctNum), Integer.parseInt(amount))) {
            System.out.println("Error! Cannot have negative account balance after transaction.");
        } else {
            updateAccountBalancesForTransaction(accountUtilities, Transaction.transfer, Integer.parseInt(fromAcctNum), Integer.parseInt(toAcctNum), Integer.parseInt(amount));
        }
    }

    /**
     * Withdraws an amount for the required given parameters after validating.
     *
     * @param accountUtilities AccountUtilities reference
     * @param amount           un-verified amount to be withdrawn
     * @param fromAcctNum      un-verified account number to be used
     */
    void withdraw(AccountUtilities accountUtilities, String amount, String fromAcctNum) {
        if (!isValidAmount(amount)) {
            // either out of range or input isn't a number
            System.out.println("Error! Amount input is not valid.");
        } else if (!AccountUtilities.isValidAcct(fromAcctNum)) {
            System.out.println("Error! Account number is invalid.");
        } else if (accountUtilities.isNewAccount(Integer.parseInt(fromAcctNum))) {
            System.out.println("Error! No transactions are allowed on new accounts.");
        } else if (!isValidTransaction(accountUtilities, Integer.parseInt(fromAcctNum), Integer.parseInt(amount))) {
            System.out.println("Error! Cannot have negative account balance after transaction.");
        } else {
            updateAccountBalancesForTransaction(accountUtilities, Transaction.withdraw, Integer.parseInt(fromAcctNum), 0, Integer.parseInt(amount));
        }
    }

    /**
     * Deposits an amount for the required given parameters after validating.
     *
     * @param accountUtilities AccountUtilities reference
     * @param toAcctNum        un-verified account number to be used
     * @param amount           un-verified amount to be deposited
     */
    void deposit(AccountUtilities accountUtilities, String toAcctNum, String amount) {
        if (!isValidAmount(amount)) {
            // either out of range or input isn't a number
            System.out.println("Error! Amount input is not valid.");
        } else if (!AccountUtilities.isValidAcct(toAcctNum)) {
            System.out.println("Error! Account number is invalid.");
        } else if (accountUtilities.isNewAccount(Integer.parseInt(toAcctNum))) {
            System.out.println("Error! No transactions are allowed on new accounts.");
        } else {
            updateAccountBalancesForTransaction(accountUtilities, Transaction.deposit, 0, Integer.parseInt(toAcctNum), Integer.parseInt(amount));
        }
    }
}
