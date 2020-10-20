package us.lacchain.crossborder.management.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "banks")
public class Bank {

    @Id
    private String tax_id;
    private String code;
    private String name;
    private String city;
    

    public Bank() {
    }

    public Bank(String tax_id, String code, String name, String city) {
        this.tax_id = tax_id;
        this.code = code;
        this.name = name;
        this.city = city;
    }

    public String getTax_id() {
        return this.tax_id;
    }

    public void setTax_id(String tax_id) {
        this.tax_id = tax_id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Bank)) {
            return false;
        }
        Bank bank = (Bank) o;
        return Objects.equals(tax_id, bank.tax_id) && Objects.equals(code, bank.code) && Objects.equals(name, bank.name) && Objects.equals(city, bank.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tax_id, code, name, city);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Bank{");
        sb.append("tax_id=").append(tax_id);
        sb.append(", code=").append(code).append('\'');
        sb.append(", name=").append(name).append('\'');
        sb.append(", city=").append(city);
        sb.append('}');
        return sb.toString();    
    }

}