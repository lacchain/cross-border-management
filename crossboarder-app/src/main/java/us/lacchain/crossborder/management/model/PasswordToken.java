package us.lacchain.crossborder.management.model;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = "password_token")
public class PasswordToken {

    @Id
    private String id;
    private String token;
    @Column(name = "expirydate", columnDefinition = "TIMESTAMP")
    private LocalDateTime expiryDate;
    private String user_id;
    
    public PasswordToken() {
    }

    public PasswordToken(String id, String token, LocalDateTime expiryDate, String user_id) {
        this.id = id;
        this.token = token;
        this.expiryDate = expiryDate;
        this.user_id = user_id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public PasswordToken id(String id) {
        this.id = id;
        return this;
    }

    public PasswordToken token(String token) {
        this.token = token;
        return this;
    }

    public PasswordToken expiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public PasswordToken user_id(String user_id) {
        this.user_id = user_id;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PasswordToken)) {
            return false;
        }
        PasswordToken passwordToken = (PasswordToken) o;
        return Objects.equals(id, passwordToken.id) && Objects.equals(token, passwordToken.token) && Objects.equals(expiryDate, passwordToken.expiryDate) && Objects.equals(user_id, passwordToken.user_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, expiryDate, user_id);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", token='" + getToken() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", user_id='" + getUser_id() + "'" +
            "}";
    }
}