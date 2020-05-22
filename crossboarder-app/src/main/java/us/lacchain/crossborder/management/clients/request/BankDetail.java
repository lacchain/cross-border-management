package us.lacchain.crossborder.management.clients.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankDetail implements Serializable {
    private String bankName;
    private String bankTaxId;
    private String bankCity;
    private String bankAccount;
}