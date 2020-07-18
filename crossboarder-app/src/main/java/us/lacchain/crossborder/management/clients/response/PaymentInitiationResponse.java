package us.lacchain.crossborder.management.clients.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInitiationResponse implements Serializable {
    private String msgId;
    private String instrId;
    private String endToEndId;
    private String txSts;
    private String acctSvcrRef;
    private float addtlInf;
}