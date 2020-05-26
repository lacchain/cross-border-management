package us.lacchain.crossborder.management.model;

import java.io.Serializable;
import java.util.Objects;

public class UserLogin implements Serializable {
    private String email;
    private String password;
    private String role; 
    private String dltAddress;


    public UserLogin() {
    }

    public UserLogin(String email, String password, String role, String dltAddress) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.dltAddress = dltAddress;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDltAddress() {
        return this.dltAddress;
    }

    public void setDltAddress(String dltAddress) {
        this.dltAddress = dltAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserLogin)) {
            return false;
        }
        UserLogin userLogin = (UserLogin) o;
        return Objects.equals(email, userLogin.email) && Objects.equals(password, userLogin.password) && Objects.equals(role, userLogin.role) && Objects.equals(dltAddress, userLogin.dltAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, role, dltAddress);
    }

    @Override
    public String toString() {
        return "{" +
            " email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", role='" + getRole() + "'" +
            ", dltAddress='" + getDltAddress() + "'" +
            "}";
    }
    

}
