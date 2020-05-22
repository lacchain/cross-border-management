package us.lacchain.crossborder.management.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users_view")
public class UserView {

    @Id
    private String dlt_address;
    private String company;
    private String fullname;
    private String email;
    private String name;
    private String tax_id;
    private String city;
    private String bank_account;
    private String currency;
    private float balance;
    private int status;

    public UserView() {
    }

    public UserView(String dlt_address, String company, String fullname, String email, String name, String tax_id, String city, String bank_account, String currency, float balance, int status) {
        this.dlt_address = dlt_address;
        this.company = company;
        this.fullname = fullname;
        this.email = email;
        this.name = name;
        this.tax_id = tax_id;
        this.city = city;
        this.bank_account = bank_account;
        this.currency = currency;
        this.balance = balance;
        this.status = status;
    }

    public String getDlt_address() {
        return this.dlt_address;
    }

    public void setDlt_address(String dlt_address) {
        this.dlt_address = dlt_address;
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

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTax_id() {
        return this.tax_id;
    }

    public void setTax_id(String tax_id) {
        this.tax_id = tax_id;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
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

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserView)) {
            return false;
        }
        UserView userView = (UserView) o;
        return Objects.equals(dlt_address, userView.dlt_address) && Objects.equals(company, userView.company) && Objects.equals(fullname, userView.fullname) && Objects.equals(email, userView.email) && Objects.equals(name, userView.name) && Objects.equals(tax_id, userView.tax_id) && Objects.equals(city, userView.city) && Objects.equals(bank_account, userView.bank_account) && Objects.equals(currency, userView.currency) && balance == userView.balance && status == userView.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dlt_address, company, fullname, email, name, tax_id, city, bank_account, currency, balance, status);
    }

    @Override
    public String toString() {
        return "{" +
            " dlt_address='" + getDlt_address() + "'" +
            ", company='" + getCompany() + "'" +
            ", fullname='" + getFullname() + "'" +
            ", email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            ", tax_id='" + getTax_id() + "'" +
            ", city='" + getCity() + "'" +
            ", bank_account='" + getBank_account() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", balance='" + getBalance() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
   
}
