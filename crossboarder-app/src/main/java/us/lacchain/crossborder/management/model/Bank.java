package us.lacchain.crossborder.management.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "banks")
public class Bank {

    @Id
    private String tax_id;
    private String name;
    private String city;
    

    public Bank() {
    }

    public Bank(String tax_id, String name, String city) {
        this.tax_id = tax_id;
        this.name = name;
        this.city = city;
    }

    public String getTax_id() {
        return this.tax_id;
    }

    public void setTax_id(String tax_id) {
        this.tax_id = tax_id;
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
        return Objects.equals(tax_id, bank.tax_id) && Objects.equals(name, bank.name) && Objects.equals(city, bank.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tax_id, name, city);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Bank{");
        sb.append("tax_id=").append(tax_id);
        sb.append(", name=").append(name).append('\'');
        sb.append(", city=").append(city);
        sb.append('}');
        return sb.toString();    
    }

}