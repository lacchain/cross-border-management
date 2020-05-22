package us.lacchain.crossborder.management.clients.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetail implements Serializable {
    private String company;
    private String fullname;
    private String email;
    private String password;
    private String dltAddress;
    private String currency;
    private float balance;
    private int status;
}