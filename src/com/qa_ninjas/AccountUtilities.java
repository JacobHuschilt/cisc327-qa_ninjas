package com.qa_ninjas;

import java.util.ArrayList;

/**
 * This class is a parent class to CreateAcct, DeleteAcct.  It contains methods
 * that validate account numbers and account names and update the list of valid
 * accounts.  The ArrayList called "accountList" takes ValidAccount objects.
 */
public class AccountUtilities {
    public boolean isAuthorized;
    ArrayList<ValidAccount> accountList = new ArrayList();

    /**
     * This method validates a given account number.  It prints an error message
     * warning the user if the account number did not meet the requirements.
     * @param acctNum
     * @return true if the account number is valid, false otherwise.
     */
    protected boolean isValidAcct(String acctNum) {
        try {
            int acctNumInt = Integer.parseInt(acctNum);
            if (acctNum.length() != 7) {
                return false;
            }
            if (acctNum.charAt(0) == '0') {
                return false;
            }
            for (ValidAccount singleAccount : accountList) {
                if (acctNumInt == singleAccount.getAcctNum()) {
                    return false;
                }
            }
            return true;
        } catch (NumberFormatException exception) {
            System.out.println("Error! Invalid Account Number: " + exception);
            return false;
        }
    }

    /**
     * This method validates a given account name.  It warns the user if the account
     * name is too short, too long or contains non-alphabetic characters.
     * @param name
     * @return true if valid and false otherwise.
     */
    protected boolean isValidName(String name) {
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetter(name.charAt(i))) {
                System.out.println("Error! Invalid Account Name: " + name);
                return false;
            }
        }
        if (name.length() < 3 || name.length() > 30) {
            System.out.println("Error! Account Name Too Long: " + name);
            return false;
        }
        return true;
    }

    /**
     * This method handles the addition or deletion of an account using the valid
     * account list declared above. It simply handles updating and does not return
     * anything. The third parameter "isNew" flag is set to true if the current
     * account that the method is working with is in fact new, false otherwise.
     * @param acctNum
     * @param name
     * @param isNew
     */
    protected void updateAccountList(int acctNum, String name, boolean isNew) {
        if (isNew) { // adding new account
            ValidAccount newAcct = new ValidAccount(acctNum, name, true);
            accountList.add(newAcct);
        } else { // removing existing account
            for (ValidAccount singleAccount : accountList) {
                // if the account number is found, delete that account object
                if (acctNum == singleAccount.getAcctNum()){
                    accountList.remove(singleAccount);
                }
            }
        }
    }

}
