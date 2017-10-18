package com.qa_ninjas;

import java.util.ArrayList;

public abstract class AccountUtilities {
    public boolean isAuthorized;
    ArrayList<ValidAccount> accountList = new ArrayList();

    protected boolean isValidAcct(int acctNum) {
        return false;
    }

    protected void updateAccountList(boolean isNew, int acctNum, String name) {
        System.out.println();
    }

}
