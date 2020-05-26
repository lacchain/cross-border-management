package us.lacchain.crossborder.management.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.ConstructorResult;
import javax.persistence.ColumnResult;

@SqlResultSetMapping(name = "userResultMapping", classes = {
    @ConstructorResult(targetClass = UserLogin.class, columns = { @ColumnResult(name = "email", type = String.class),
            @ColumnResult(name = "password", type= String.class), @ColumnResult(name = "role"), @ColumnResult(name = "dlt_address")}) })
@NamedNativeQueries({
    @NamedNativeQuery(name = "UserRepository.getUserLogin", query = "SELECT u.email,u.password, u.role, accounts.dlt_address FROM users u INNER JOIN accounts ON accounts.user_id = u.id WHERE u.email = :email and u.password = :password and accounts.dlt_address = :dltAddress", resultSetMapping = "userResultMapping")})

@Entity
@Table(name = "users")
public class User {

    @Id
    private String id;
    private String fullname;
    private String email;
    private String password;
    private String company;
    private String role;

    public User() {
    }

    public User(String id, String fullname, String email, String password, String company, String role) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.company = company;
        this.role = role;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", fullname='").append(fullname).append('\'');
        sb.append(", email=").append(email).append('\'');
        sb.append(", company=").append(company).append('\'');
        sb.append(", role=").append(role);
        sb.append('}');
        return sb.toString();
    }
}