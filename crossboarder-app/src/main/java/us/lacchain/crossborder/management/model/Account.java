package us.lacchain.crossborder.management.model;

import javax.persistence.ColumnResult;
import java.util.Objects;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

@SqlResultSetMapping(name = "accountResultMapping", classes = {
    @ConstructorResult(targetClass = AccountResult.class, columns = { @ColumnResult(name = "company", type = String.class),
            @ColumnResult(name = "fullname", type= String.class), @ColumnResult(name = "name"), @ColumnResult(name = "bank_account"), @ColumnResult(name = "dlt_address", type = String.class), @ColumnResult(name = "currency",type = String.class), @ColumnResult(name = "balance", type = Float.class), @ColumnResult(name = "status",type = String.class) }) })
@NamedNativeQueries({
    @NamedNativeQuery(name = "AccountRepository.getAllAccounts", query = "SELECT users.company, users.fullname, banks.name, accounts.bank_account, accounts.dlt_address, accounts.currency, accounts.balance, CASE WHEN accounts.status = 0 THEN 'Pending' WHEN accounts.status = 1 THEN 'Active' ELSE 'Inactive' END AS status FROM users INNER JOIN accounts ON accounts.user_id = users.id INNER JOIN banks ON banks.tax_id = accounts.bank_id", resultSetMapping = "accountResultMapping")})
    
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    private String dlt_address;
    private String bank_account;
    private String currency;
    private float balance;
    private int status;
    private String bank_id;
    private String user_id;


    public Account() {
    }

    public Account(String dlt_address, String bank_account, String currency, float balance, int status, String bank_id, String user_id) {
        this.dlt_address = dlt_address;
        this.bank_account = bank_account;
        this.currency = currency;
        this.balance = balance;
        this.status = status;
        this.bank_id = bank_id;
        this.user_id = user_id;
    }

    public String getDlt_address() {
        return this.dlt_address;
    }

    public void setDlt_address(String dlt_address) {
        this.dlt_address = dlt_address;
    }

    public String getBank_account() {
        return this.bank_account;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
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

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBank_id() {
        return this.bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Account)) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(dlt_address, account.dlt_address) && Objects.equals(bank_account, account.bank_account) && Objects.equals(currency, account.currency) && balance == account.balance && status == account.status && Objects.equals(bank_id, account.bank_id) && Objects.equals(user_id, account.user_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dlt_address, bank_account, currency, balance, status, bank_id, user_id);
    }

    @Override
    public String toString() {
        return "{" +
            " dlt_address='" + getDlt_address() + "'" +
            ", bank_account='" + getBank_account() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", balance='" + getBalance() + "'" +
            ", status='" + getStatus() + "'" +
            ", bank_id='" + getBank_id() + "'" +
            ", user_id='" + getUser_id() + "'" +
            "}";
    }
    
}