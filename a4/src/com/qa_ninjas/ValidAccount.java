package com.qa_ninjas;

public class ValidAccount {
    private int acctNum;
    private int acctBalance;
    private String name;
    private boolean isNew;

    public ValidAccount(int acctNum, int acctBalance, String name, boolean isNew) {
        this.acctNum = acctNum;
        this.acctBalance = acctBalance;
        this.name = name;
        this.isNew = isNew;
    }

    public int getAcctNum() {
        return acctNum;
    }

    public int getAcctBalance() {
        return acctBalance;
    }

    public String getName() {
        return name;
    }

    public void setAcctBalance(int acctBalance) {
        this.acctBalance = acctBalance;
    }

    public boolean isNew() {
        return isNew;
    }
}
