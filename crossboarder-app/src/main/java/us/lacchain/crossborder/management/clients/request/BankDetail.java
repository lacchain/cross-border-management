package us.lacchain.crossborder.management.clients.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BankDetail implements Serializable {
    @NotNull(message="bankName can't be null")
    @Size(min = 3)
    private String bankName;
    @NotNull(message="bankTaxId can't be null")
    @Size(min = 3)
    private String bankTaxId;
    @NotNull(message="bankCode can't be null")
    @Size(min = 3)
    private String bankCode;
    @NotNull(message="bankCity can't be null")
    @Size(min = 3)
    private String bankCity;
    @NotNull(message="bankAccount can't be null")
    @Size(min = 3)
    private String bankAccount;
}