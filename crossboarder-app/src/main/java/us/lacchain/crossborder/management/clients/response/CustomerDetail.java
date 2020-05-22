package us.lacchain.crossborder.management.clients.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetail implements Serializable {
    private String name;
    private String bank;
    private String bankAccount; 
    private String dltAddress;
}