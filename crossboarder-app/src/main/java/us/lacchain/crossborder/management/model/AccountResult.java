package us.lacchain.crossborder.management.model;

import java.util.Objects;

public class AccountResult {

    private String company;
    private String fullname;
    private String bankName;
    private String bankAccount;
    private String dltAddress;
    private String currency;
    private float balance;
    private String status;


    public AccountResult() {
    }

    public AccountResult(String company, String fullname, String bankName, String bankAccount, String dltAddress, String currency, float balance, String status) {
        this.company = company;
        this.fullname = fullname;
        this.bankName = bankName;
        this.bankAccount = bankAccount;
        this.dltAddress = dltAddress;
        this.currency = currency;
        this.balance = balance;
        this.status = status;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return this.bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getDltAddress() {
        return this.dltAddress;
    }

    public void setDltAddress(String dltAddress) {
        this.dltAddress = dltAddress;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getBalance() {
        return this.balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AccountResult)) {
            return false;
        }
        AccountResult accountResult = (AccountResult) o;
        return Objects.equals(company, accountResult.company) && Objects.equals(fullname, accountResult.fullname) && Objects.equals(bankName, accountResult.bankName) && Objects.equals(bankAccount, accountResult.bankAccount) && Objects.equals(dltAddress, accountResult.dltAddress) && Objects.equals(currency, accountResult.currency) && balance == accountResult.balance && Objects.equals(status, accountResult.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, fullname, bankName, bankAccount, dltAddress, currency, balance, status);
    }

    @Override
    public String toString() {
        return "{" +
            " company='" + getCompany() + "'" +
            ", fullname='" + getFullname() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", bankAccount='" + getBankAccount() + "'" +
            ", dltAddress='" + getDltAddress() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", balance='" + getBalance() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }    
}