package com.qa_ninjas;

import java.util.ArrayList;

/**
 * This class is a parent class to CreateAcct, DeleteAcct.  It contains methods
 * that validate account numbers and account names and update the list of valid
 * accounts.  The ArrayList called "accountList" takes ValidAccount objects.
 */
public class AccountUtilities {
    ArrayList<ValidAccount> accountList = new ArrayList();

    /**
     * This method validates a given account number.  It prints an error message
     * warning the user if the account number did not meet the requirements.
     *
     * @param acctNum un-validated account number
     * @return true if the account number is valid, false otherwise.
     */
    static boolean isValidAcct(String acctNum) {
        try {
            int acctNumInt = Integer.parseInt(acctNum);
            return acctNum.length() == 7 || acctNum.charAt(0) != '0';
        } catch (NumberFormatException exception) {
            System.out.println("Error! Invalid Account Number: " + exception);
            return false;
        }
    }

    /**
     * Determines if the account exists in the validAccounts file/temporary storage.
     *
     * @param acctNum account number to search with
     * @return true if account exists, false otherwise
     */
    private boolean doesAccountExist(int acctNum) {
        for (ValidAccount singleAccount : accountList) {
            if (acctNum == singleAccount.getAcctNum()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Searches the list of valid accounts to determine if the specified account is new.
     *
     * @param acctNum a valid account number
     * @return true if account is new, otherwise false
     */
    boolean isNewAccount(int acctNum) {
        for (ValidAccount account : accountList) {
            if (account.getAcctNum() == acctNum) {
                if (account.isNew()) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Updates the amount withdrawn from an atm machine in the current session for the specified account number.
     *
     * @param acctNum a valid account number
     * @param amount a valid amount to be withdrawn
     * @return true if daily new amount plus old is under daily at limit, otherwise
     */
    boolean updateAtmAmountWithdrawn(int acctNum, int amount) {
        for (ValidAccount account : accountList) {
            if (account.getAcctNum() == acctNum) {
                int newAmount = account.getAmountWithdrawnInSession() + amount;

                if (newAmount < 100000) {
                    account.setAmountWithdrawnInSession(newAmount);
                    return true;
                } else {
                    System.out.println("Error: Daily ATM withdraw limit reached!");
                    break;
                }
            }
        }

        return false;
    }

    /**
     * This method validates a given account name.  It warns the user if the account
     * name is too short, too long or contains non-alphabetic characters.
     *
     * @param name an un-verified name
     * @return true if valid and false otherwise.
     */
    private boolean isValidName(String name) {
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isAlphabetic(name.charAt(i)) && name.charAt(i) != ' ') {
                System.out.println("Error! Invalid Account Name: " + name);
                return false;
            }
        }

        if (name.length() < 3 || name.length() > 30) {
            System.out.println("Error! Unacceptable length for Account Name: " + name);
            return false;
        }

        return true;
    }

    /**
     * This method handles the addition or deletion of an account using the valid
     * account list declared above. It simply handles updating and does not return
     * anything. The third parameter "isNew" flag is set to true if the current
     * account that the method is working with is in fact new, false otherwise.
     *
     * @param acctNum a valid account number
     * @param name    a valid name
     * @param isNew   a boolean indicating whether to add or delete the specified account
     */
    private void updateAccountList(int acctNum, String name, boolean isNew) {
        if (isNew) { // adding new account
            // Update the Master TSF File change list
            Main.tsfChanges.add("NEW" + " " + acctNum + " " + "000" + " " + "0000000" + " " + name);

            ValidAccount newAcct = new ValidAccount(acctNum, name, true);
            accountList.add(newAcct);
        } else { // removing existing account
            for (ValidAccount singleAccount : accountList) {
                // Find the account in the list, and delete it
                if (acctNum == singleAccount.getAcctNum()) {
                    // Update the Master TSF File change list
                    Main.tsfChanges.add("DEL" + " " + acctNum + " " + "000" + " " + "0000000" + " " + name);

                    accountList.remove(singleAccount);
                    return;
                }
            }

            System.out.println("Error: Could not find account to delete in list of valid accounts");
        }
    }

    /**
     * Creates an account for the specified account number and name.
     *
     * @param acctNum an un-verified account number
     * @param name    an un-verified account name
     */
    void createAccount(String acctNum, String name) {
        if (!isValidAcct(acctNum)) {
            System.out.println("Error: Bad Account number");
        } else if (!isValidName(name)) {
            System.out.println("Error: Bad account name");
        } else if (doesAccountExist(Integer.parseInt(acctNum))) {
            System.out.println("Error: Account already exists with the specified number!");
        } else {
            updateAccountList(Integer.parseInt(acctNum), name, true);
        }
    }

    /**
     * Deletes an account with the specified account number.
     *
     * @param acctNum an un-verified account number
     * @param name    an un-verified account name
     */
    void deleteAccount(String acctNum, String name) {
        if (isValidAcct(acctNum)) {
            if (!doesAccountExist(Integer.parseInt(acctNum))) {
                System.out.println("Error: Account does not exist");
            } else { // account exists, and we can delete it!!!
                updateAccountList(Integer.parseInt(acctNum), name, false);
            }
        } else {
            System.out.println("Error: Invalid account number");
        }
    }
}
