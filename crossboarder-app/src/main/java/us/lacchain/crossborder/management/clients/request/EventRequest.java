package us.lacchain.crossborder.management.clients.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest implements Serializable {
    private String name;
    private String filterId;
    private String nodeName;
    private List<Map<String,Object>> indexedParameters;
    private List<Map<String,Object>> nonIndexedParameters;
    private String transactionHash;
    private int logIndex;
    private long blockNumber;
    private String blockHash; 
    private String address;
    private String status;
    private String eventSpecificationSignature;      
    private String networkName;
    private String id;
}