package com.qa_ninjas;

public class ValidAccount {
    private int acctNum;
    private int acctBalance;
    private String name;
    private boolean isNew;
    private int amountWithdrawnInSession;

    public ValidAccount(int acctNum, int acctBalance, String name, boolean isNew) {
        this.acctNum = acctNum;
        this.name = name;
        this.isNew = isNew;
        this.amountWithdrawnInSession = 0;
    }

    public int getAcctNum() {
        return acctNum;
    }

    public int getAcctBalance() {
        return acctBalance;
    }

    public void setAcctBalance(int acctBalance) {
        this.acctBalance = acctBalance;
    }

    public boolean isNew() {
        return isNew;
    }

    public int getAmountWithdrawnInSession() {
        return amountWithdrawnInSession;
    }

    public void setAmountWithdrawnInSession(int amountWithdrawnInSession) {
        this.amountWithdrawnInSession = amountWithdrawnInSession;
    }
}
