package com.qa_ninjas;

public class ValidAccount {
    private int acctNum;
    private int acctBalance;
    private String name;
    private boolean isNew;
    private boolean isActive;
    private int amountWithdrawnInSession;

    public ValidAccount(int acctNum, int acctBalance, String name, boolean isNew, boolean isActive) {
        this.acctNum = acctNum;
        this.acctBalance = acctBalance;
        this.name = name;
        this.isNew = isNew;
        this.isActive = isActive;
        this.amountWithdrawnInSession = 0;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getAmountWithdrawnInSession() {
        return amountWithdrawnInSession;
    }

    public void setAmountWithdrawnInSession(int amountWithdrawnInSession) {
        this.amountWithdrawnInSession = amountWithdrawnInSession;
    }
}
