package us.lacchain.crossborder.management.clients.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserRequest implements Serializable {
    private AccountDetail accountDetails;
    private BankDetail bankDetails;
}