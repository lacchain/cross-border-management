package us.lacchain.crossborder.management.service;

import us.lacchain.crossborder.management.clients.request.EventRequest;
import us.lacchain.crossborder.management.repository.AccountRepository;
import us.lacchain.crossborder.management.repository.MovementRepository;
import us.lacchain.crossborder.management.util.Client;
import us.lacchain.crossborder.management.model.Movement;
import us.lacchain.crossborder.management.mapper.PaymentInitiationMapper;
import us.lacchain.crossborder.management.clients.response.PaymentInitiationResponse;
import us.lacchain.crossborder.management.clients.response.PaymentStatusResponse;
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
    private static final float FREE_FEE = 0.0f;

    Logger logger = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private RestTemplate webClient;

    @Autowired
    private Client client;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MovementRepository movementRepository;

    @Value("${crossborder.contract.edollars}")
    private String contractDollars;

    @Value("${crossborder.contract.epesos}")
    private String contractPesos;

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

    @Value("${resources.supplied.api.citi.paymentinitiation.url}")
    private String paymentInitiationURL;

    @Value("${resources.supplied.api.citi.paymentstatus.url}")
    private String paymentStatusURL;

    public EventService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean processEvent(EventRequest request){

        logger.info("Event:"+request);
        logger.info("Status:"+request.getStatus());

        if(request.getStatus().equalsIgnoreCase("CONFIRMED")){
        logger.info("FilterId:"+request.getFilterId());    
            switch(request.getFilterId()){
                case "WhitelistedAddedDollars":
                    setWhitelistedAccount(request);
                    break;
                case "WhitelistedRemovedDollars":
                    setWhitelistedRemoved(request);
                    break;    
                case "TransferDollars":
                    setBalanceMinted(request);
                    break;
                case "WhitelistedAddedPesos":
                    setWhitelistedAccount(request);
                    break;
                case "WhitelistedRemovedPesos":
                    setWhitelistedRemoved(request);
                    break;    
                case "TransferPesos":
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
                    changeDollarsToPesos(request);
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
            logger.debug("Event UNCORFIRMED");
        }

        return true;
    }

    private void setWhitelistedAccount(EventRequest request){
        logger.info("index:"+request.getIndexedParameters().get(0));
        Map<String,Object> accountParameter = request.getIndexedParameters().get(0);
        String dltAddress = (String)accountParameter.get("value");
        String currency = "USD";
        logger.info("dltAddress:"+dltAddress);
        logger.info("contractPesosParam:"+contractPesos);
        logger.info("contractPesos:"+request.getAddress());
        if (contractPesos.equalsIgnoreCase(request.getAddress())){
            currency = "DOP";
        }
        
        accountRepository.setWhitelisted(dltAddress,currency);
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
        Map<String,Object> fromParameter = request.getIndexedParameters().get(0);
        Map<String,Object> accountParameter = request.getIndexedParameters().get(1);
        Map<String,Object> value = request.getNonIndexedParameters().get(0);
        String fromDltAddress = (String)fromParameter.get("value");
        String dltAddress = (String)accountParameter.get("value");
        int balance = (int)value.get("value");
        if (ZERO_ADDRESS.equalsIgnoreCase(fromDltAddress)){
            accountRepository.setBalance(dltAddress, (float)balance/10000);
            logger.info("new balance set");
            LocalDateTime localDateTime = LocalDateTime.now();
            Movement movement = new Movement(UUID.randomUUID().toString(),localDateTime,ZERO_ADDRESS,dltAddress,(float)balance/10000,mintMessage,balance/10000,0,0,null,null,null,null,null,null,4);
            movementRepository.save(movement);
            logger.info("new movement registered");
        }
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
        Movement movement = new Movement(operationId, localDateTime, ordererAddress, toAddress,(float)balance,detailMessage,0,0,0,request.getTransactionHash(),null,null,null,null,null,0);
        movementRepository.save(movement);
        logger.info("new movement registered");
    }

    private void executeTransfer(EventRequest request){
        logger.info("-->>>CALLING TO CITIIII FOR TRANSFER<<<----");
        Map<String,Object> amountParameter = request.getNonIndexedParameters().get(1);
        Map<String,Object> operationIdParameter = request.getNonIndexedParameters().get(0);
        int amount = (int)amountParameter.get("value");
        String operationId = (String)operationIdParameter.get("value");
        PaymentInitiationMapper mapper = new PaymentInitiationMapper();
        String paymentInitiationRequest = mapper.xmlToPaymentInitiationRequest(amount);
        logger.info(">>PaymentInitiationRequest<<:"+paymentInitiationRequest);
        String paymentInitiationResponse = webClient.postForObject(paymentInitiationURL, paymentInitiationRequest, String.class);
        PaymentInitiationResponse paymentResponse = mapper.mapPaymentInitiationResponse(paymentInitiationResponse);
        System.out.println(">>>>PaymentResponse>>>"+paymentResponse);
        
        //Register on movement table on DataBase
        //View if it goes
        movementRepository.setTransferInProgress(operationId, request.getTransactionHash(), paymentResponse.getEndToEndId(), paymentResponse.getAcctSvcrRef());
    }

    public void setFeeRate(){
        List<Movement> movements = movementRepository.getMovementsInProgress();
        logger.info("Movements in progress:"+movements.size());
        for (Movement movement:movements){
            //Call Citi Api statusPayment
            PaymentInitiationMapper mapper = new PaymentInitiationMapper();
            logger.info("movement.getEndtoend_id:"+movement.getEndtoend_id());
            String paymentStatusRequest = mapper.xmlToPaymentStatusRequest(movement.getEndtoend_id());
            logger.info(">>>>PaymentStatusRequest<<<<:"+paymentStatusRequest);
            String paymentStatusResponse = webClient.postForObject(paymentStatusURL, paymentStatusRequest, String.class);
            PaymentStatusResponse paymentResponse = mapper.mapPaymentStatusResponse(paymentStatusResponse, movement.getEndtoend_id());    
            if (paymentResponse.isExecuted()){
                logger.info("CALLING BLOCKCHAIN ---> SET FEE-RATE");
                JSONObject body = new JSONObject();
                body.put("operationId", movement.getId());
                body.put("fee", FREE_FEE);
                logger.info("valor que llega:"+paymentResponse.getAddtlInf());
                body.put("rate",paymentResponse.getAddtlInf());
                try{
                    String response = webClient.postForObject(setFeeRateURL, client.getEntity(body), String.class);
                    logger.info("response:"+response);
                    movementRepository.setFeeRateStatus(movement.getId());
                }catch(Exception ex){
                    System.out.println("ERROR:"+ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
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
            movementRepository.setFeeRate((float)fee,(float)rate/10000,request.getTransactionHash(),operationId); 
        }catch(Exception ex){
            System.out.println("ERROR:"+ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void changeDollarsToPesos(EventRequest request){
        logger.info("CALLING BLOCKCHAIN GO");
        logger.info("MARKET-MAKER --> CHANGE DOLAR");
        Map<String,Object> ordererParameter = request.getIndexedParameters().get(0);
        Map<String,Object> operationIdParameter = request.getNonIndexedParameters().get(0);
        String ordererAddress = (String)ordererParameter.get("value");
        String operationId = (String)operationIdParameter.get("value");
        
        JSONObject body = new JSONObject();
        body.put("operationId", operationId);
        try{
            String response = webClient.postForObject(changeDollarsToPesos, client.getEntity(body), String.class);
            logger.info("response:"+response);
        }catch(Exception ex){
            System.out.println("ERROR:"+ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void sendPesosToRecipient(EventRequest request){
        logger.info("CALLING BLOCKCHAIN GO");
        logger.info("MARKET-MAKER --> SEND PESOS");
        Map<String,Object> ordererParameter = request.getIndexedParameters().get(0);
        Map<String,Object> operationIdParameter = request.getNonIndexedParameters().get(0);
        String ordererAddress = (String)ordererParameter.get("value");
        String operationId = (String)operationIdParameter.get("value");
        
        JSONObject body = new JSONObject();
        body.put("operationId", operationId);
        try{
            String response = webClient.postForObject(sendPesosToRecepient, client.getEntity(body), String.class);
            logger.info("response:"+response);
        }catch(Exception ex){
            System.out.println("ERROR:"+ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void setTransferExecuted(EventRequest request){
    //TransferExecuted(address indexed issuer, address indexed orderer, string operationId, address indexed to, uint256 value);
        logger.debug("index:"+request.getNonIndexedParameters().get(0));
        Map<String,Object> operationIdParameter = request.getNonIndexedParameters().get(0);
        Map<String,Object> ordererParameter = request.getIndexedParameters().get(1);
        Map<String,Object> toParameter = request.getIndexedParameters().get(2);
        Map<String,Object> valueParameter = request.getNonIndexedParameters().get(1);
        String operationId = (String)operationIdParameter.get("value");
        int value = (int)valueParameter.get("value");
        logger.debug("operationId:"+operationId);
        //accountRepository.setBalance(dltAddress, balance);
        movementRepository.setTransferExecuted(request.getTransactionHash(),operationId,(float)value/10000);
    }
}