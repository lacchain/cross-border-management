package us.lacchain.crossborder.management.clients.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDetail implements Serializable {
    private float amountSent;
    private float feeApplied;
    private float amountConverted; 
    private float rateApplied;
    private float totalAmount;
}