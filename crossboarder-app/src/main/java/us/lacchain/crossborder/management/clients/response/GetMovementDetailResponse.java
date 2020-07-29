package us.lacchain.crossborder.management.clients.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

import us.lacchain.crossborder.management.clients.response.TransferDetail;
import us.lacchain.crossborder.management.clients.response.CustomerDetail;
import us.lacchain.crossborder.management.clients.response.TransactionHistory;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetMovementDetailResponse implements Serializable {
    private String operationId;
    private String endtoendId;
    private String apimguid;
    private String status;
    private TransferDetail transferDetails;
    private CustomerDetail senderDetails;
    private CustomerDetail recipientDetails;
    private TransactionHistory transactionHistory;
}
