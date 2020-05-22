package us.lacchain.crossborder.management.clients.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistory implements Serializable {
    private String operationRequested;
    private String setFee;
    private String operationApproved; 
}