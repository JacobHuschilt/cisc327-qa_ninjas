package com.qa_ninjas;

import java.util.ArrayList;

/**
 * This class is a parent class to CreateAcct, DeleteAcct.  It contains methods
 * that validate account numbers and account names and update the list of valid
 * accounts.  The ArrayList called "accountList" takes ValidAccount objects.
 */
public class AccountUtilities {
    /**
     * Global Properties
     */
    ArrayList<ValidAccount> accountList = new ArrayList<>();


    /**
     * This method validates a given account number.  It prints an error message
     * warning the user if the account number did not meet the requirements.
     *
     * @param acctNum   un-validated account number
     * @return          true if the account number is valid, false otherwise.
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
     * This method validates a given account name.  It warns the user if the account
     * name is too short, too long or contains non-alphabetic characters.
     *
     * @param name  un-verified name
     * @return      true if valid and false otherwise.
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
     * Determines if the account exists in the validAccounts file/temporary storage.
     *
     * @param acctNum   account number to search with
     * @return          true if account exists, false otherwise
     */
    private boolean doesAccountExist(int acctNum) {
        return (getAccountFromList(acctNum) != null);
    }

    /**
     * Searches the list of valid accounts to determine if the specified account is new.
     *
     * @param acctNum   valid account number
     * @return          true if account is new, otherwise false
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
     * Retrieves the account object associated with the specified acctNum.
     *
     * @param acctNum   valid account number
     * @return          valid account object if found in the list, null otherwise
     */
    ValidAccount getAccountFromList(int acctNum) {
        for (ValidAccount account : accountList) {
            if (acctNum == account.getAcctNum()) {
                return account;
            }
        }

        return null; // can't find account
    }

    /**
     * This method handles the addition or deletion of an account using the valid
     * account list declared above. It simply handles updating and does not return
     * anything. The third parameter "isNew" flag is set to true if the current
     * account that the method is working with is in fact new, false otherwise.
     *
     * @param acctNum valid account number
     * @param name    valid name
     * @param isNew   boolean indicating whether to add or delete the specified account
     */
    private void updateAccountList(int acctNum, String name, boolean isNew) {
        if (isNew) { // adding new account
            ValidAccount newAcct = new ValidAccount(acctNum, 0, name, true);
            accountList.add(newAcct);
        } else { // removing existing account
            for (ValidAccount account : accountList) {
                // Find the account in the list, and delete it
                if (acctNum == account.getAcctNum()) {
                    accountList.remove(account);
                    return;
                }
            }

            System.out.println("Error: Could not find account to delete in list of valid accounts");
        }
    }

    /**
     * Creates an account for the specified account number and name.
     *
     * @param acctNum un-verified account number
     * @param name    un-verified account name
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
     * @param acctNum un-verified account number
     * @param name    un-verified account name
     */
    void deleteAccount(String acctNum, String name) {
        if (!isValidAcct(acctNum)) {
            System.out.println("Error: Invalid account number");
        } else if (!doesAccountExist(Integer.parseInt(acctNum))) {
            System.out.println("Error: Account does not exist");
        } else if (getAccountFromList(Integer.parseInt(acctNum)).getAcctBalance() != 0) {
            System.out.println("Error! Cannot delete an account with a balance that is not 0.");
        } else if (!getAccountFromList(Integer.parseInt(acctNum)).getName().equals(name)) {
            System.out.println("Error! Name does not match name associated with the account.");
        } else { // account exists, and all information is verified, and can be deleted!!!
            updateAccountList(Integer.parseInt(acctNum), name, false);
        }
    }
}
