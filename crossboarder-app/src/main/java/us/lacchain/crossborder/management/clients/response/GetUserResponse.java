package us.lacchain.crossborder.management.clients.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;

import us.lacchain.crossborder.management.clients.request.AccountDetail;
import us.lacchain.crossborder.management.clients.request.BankDetail;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResponse implements Serializable {
    private AccountDetail accountDetails;
    private BankDetail bankDetails;
}