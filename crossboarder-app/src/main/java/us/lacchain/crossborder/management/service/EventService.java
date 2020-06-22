package us.lacchain.crossborder.management.service;

import us.lacchain.crossborder.management.clients.request.EventRequest;
import us.lacchain.crossborder.management.repository.AccountRepository;
import us.lacchain.crossborder.management.repository.MovementRepository;
import us.lacchain.crossborder.management.util.Client;
import us.lacchain.crossborder.management.model.Movement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.dao.DataAccessException;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONObject;

@Service
public class EventService implements IEventService {

    private static final String ZERO_ADDRESS = "0x0000000000000000000000000000000000000000";

    Logger logger = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private RestTemplate webClient;

    @Autowired
    private Client client;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MovementRepository movementRepository;

    @Value("${crossborder.message.detail.mint}")
    private String mintMessage;

    @Value("${crossborder.message.detail.transfer}")
    private String detailMessage;

    @Value("${resources.supplied.api.blockchain.setfeerate.url}")
    private String setFeeRateURL;

    @Value("${resources.supplied.api.blockchain.sendDollarsToExchange.url}")
    private String sendDollarsToExchange;

    @Value("${resources.supplied.api.blockchain.changeDollarsToPesos.url}")
    private String changeDollarsToPesos;

    @Value("${resources.supplied.api.blockchain.sendPesosToRecepient.url}")
    private String sendPesosToRecepient;

    public EventService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean processEvent(EventRequest request){

        logger.info("Event:"+request);
        logger.info("Status:"+request.getStatus());

        if(request.getStatus().equalsIgnoreCase("CONFIRMED")){
        logger.info("FilterId:"+request.getFilterId());    
            switch(request.getFilterId()){
                case "WhitelistedAdded":
                    setWhitelistedAccount(request);
                    break;
                case "WhitelistedRemoved":
                    setWhitelistedRemoved(request);
                    break;    
                case "Transfer":
                    setBalanceMinted(request);
                    break;
                case "TransferOrdered":
                    setTransferOrdered(request);
                    break;
                case "TransferApproved":
                    executeTransfer(request);
                    break;
                case "FeeRateSet":
                    sendDollarsToExchange(request);  
                    break;
                case "SendedToMarket":
                    changeDolarToPesos(request);
                    break;   
                case "Exchanged":
                    sendPesosToRecipient(request);
                    break;
                case "TransferExecuted":
                    setTransferExecuted(request);
                    break;
                default:
                    logger.info("Event doesn't registered");
            }
        }else{
            logger.debug("Event UNCORFIMED");
        }

        return true;
    }

    private void setWhitelistedAccount(EventRequest request){
        logger.info("index:"+request.getIndexedParameters().get(0));
        Map<String,Object> accountParameter = request.getIndexedParameters().get(0);
        String dltAddress = (String)accountParameter.get("value");
        logger.info("dltAddress:"+dltAddress);
        accountRepository.setWhitelisted(dltAddress);
    }

    private void setWhitelistedRemoved(EventRequest request){
        logger.info("index:"+request.getIndexedParameters().get(0));
        Map<String,Object> accountParameter = request.getIndexedParameters().get(0);
        String dltAddress = (String)accountParameter.get("value");
        logger.info("dltAddress:"+dltAddress);
        accountRepository.setWhitelistedRemove(dltAddress);
    }

    private void setBalanceMinted(EventRequest request){
        logger.info("index"+request.getIndexedParameters().get(1));
        Map<String,Object> accountParameter = request.getIndexedParameters().get(1);
        Map<String,Object> value = request.getNonIndexedParameters().get(0);
        String dltAddress = (String)accountParameter.get("value");
        int balance = (int)value.get("value");
        accountRepository.setBalance(dltAddress, balance);
        logger.info("new balance set");
        LocalDateTime localDateTime = LocalDateTime.now();
        Movement movement = new Movement(UUID.randomUUID().toString(),localDateTime,ZERO_ADDRESS,dltAddress,(float)balance,mintMessage,balance,0,0,1);
        movementRepository.save(movement);
        logger.info("new movement registered");
    }

    private void setTransferOrdered(EventRequest request){
        logger.info("index"+request.getIndexedParameters().get(1));
        Map<String,Object> ordererParameter = request.getIndexedParameters().get(0);
        Map<String,Object> toParameter = request.getIndexedParameters().get(1);
        Map<String,Object> operationIdParameter = request.getNonIndexedParameters().get(0);
        Map<String,Object> valueParameter = request.getNonIndexedParameters().get(1);
        String ordererAddress = (String)ordererParameter.get("value");
        String toAddress = (String)toParameter.get("value");
        String operationId = (String)operationIdParameter.get("value");
        int balance = (int)valueParameter.get("value");
        LocalDateTime localDateTime = LocalDateTime.now();
        Movement movement = new Movement(operationId, localDateTime, ordererAddress, toAddress,(float)balance,detailMessage,0,0,0,0);
        movementRepository.save(movement);
        logger.info("new movement registered");
    }

    private void executeTransfer(EventRequest request){
        logger.info("-->>>CALLING TO CITIIII FOR TRANSFER<<<----");
        logger.info("-->>>WAITING ANSWER FROM CITIIII<<<----");
        logger.info("index:"+request.getNonIndexedParameters().get(0));
        Map<String,Object> operationIdParameter = request.getNonIndexedParameters().get(0);
        String operationId = (String)operationIdParameter.get("value");
        logger.info("operationId:"+operationId);
        logger.info("CALLING BLOCKCHAIN ---> SET FEE");
        JSONObject body = new JSONObject();
        body.put("operationId", operationId);
        try{
            String response = webClient.postForObject(setFeeRateURL, client.getEntity(body), String.class);
            logger.info("response:"+response);
        }catch(Exception ex){
            System.out.println("ERROR:"+ex.getMessage());
            ex.printStackTrace();
        }
        movementRepository.setTransferInProgress(operationId);
    }

    private void sendDollarsToExchange(EventRequest request){
        logger.info("CALL BLOCKCHAIN GO");
        logger.info("CITI --> SENT DOLAR TO EXCHANGE");
        Map<String,Object> ordererParameter = request.getIndexedParameters().get(0);
        Map<String,Object> operationIdParameter = request.getNonIndexedParameters().get(0);
        Map<String,Object> feeParameter = request.getNonIndexedParameters().get(1);
        Map<String,Object> rateParameter = request.getNonIndexedParameters().get(2);
        String ordererAddress = (String)ordererParameter.get("value");
        String operationId = (String)operationIdParameter.get("value");
        int fee = (int)feeParameter.get("value");
        int rate = (int)rateParameter.get("value");
        JSONObject body = new JSONObject();
        body.put("operationId", operationId);
        try{
            String response = webClient.postForObject(sendDollarsToExchange, client.getEntity(body), String.class);
            logger.info("response:"+response);
            movementRepository.setFeeRate(fee,rate,response,operationId); 
        }catch(Exception ex){
            System.out.println("ERROR:"+ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void changeDolarToPesos(EventRequest request){
        logger.info("CALLING BLOCKCHAIN GO");
        logger.info("MARKET-MAKER --> CHANGE DOLAR");
    }

    private void sendPesosToRecipient(EventRequest request){
        logger.info("CALLING BLOCKCHAIN GO");
        logger.info("MARKET-MAKER --> SEND PESOS");
    }

    private void setTransferExecuted(EventRequest request){
        logger.debug("index:"+request.getNonIndexedParameters().get(0));
        Map<String,Object> operationIdParameter = request.getNonIndexedParameters().get(0);
        String operationId = (String)operationIdParameter.get("value");
        logger.debug("operationId:"+operationId);
        movementRepository.setTransferExecuted(operationId);
    }
}