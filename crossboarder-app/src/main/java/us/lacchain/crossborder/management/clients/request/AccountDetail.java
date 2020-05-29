package us.lacchain.crossborder.management.clients.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AccountDetail implements Serializable {
    @NotNull(message="company can't be null")
    @Size(min = 3)
    private String company;
    @NotNull(message="fullname can't be null")
    @Size(min = 3)
    private String fullname;
    @Email
    private String email;
    @NotNull(message="password can't be null")
    @Size(min = 6)
    private String password;
    @NotNull(message="dltAddress can't be null")
    @Size(min = 20)
    private String dltAddress;
    private String currency;
    private float balance;
    private String status;
}