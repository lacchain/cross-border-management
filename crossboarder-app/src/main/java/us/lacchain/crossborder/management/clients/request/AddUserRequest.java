package us.lacchain.crossborder.management.clients.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

import javax.persistence.Embedded;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserRequest implements Serializable {
    
    @Valid
    @Embedded
    @NotNull
    private AccountDetail accountDetails;
    
    @Valid
    @Embedded
    @NotNull
    private BankDetail bankDetails;
}