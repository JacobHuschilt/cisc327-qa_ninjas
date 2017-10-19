package com.qa_ninjas;

public class ValidAccount {
    private int acctNum;
    private String name;
    private boolean isNew;

    public ValidAccount(int acctNum, String name, boolean isNew) {
        this.acctNum = acctNum;
        this.name = name;
        this.isNew = isNew;
    }

    public int getAcctNum() {
        return acctNum;
    }

    public boolean isNew() {
        return isNew;
    }




}
