package us.lacchain.crossborder.management.service;

import us.lacchain.crossborder.management.clients.request.EventRequest;
import us.lacchain.crossborder.management.repository.AccountRepository;
import us.lacchain.crossborder.management.repository.MovementRepository;
import us.lacchain.crossborder.management.model.Movement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.dao.DataAccessException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EventService implements IEventService {

    private static final String ZERO_ADDRESS = "0x0000000000000000000000000000000000000000";

    Logger logger = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MovementRepository movementRepository;

    @Value("${crossborder.message.mint}")
    private String mintMessage;

    public EventService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean processEvent(EventRequest request){

        logger.info("Event:"+request);
        
        switch(request.getFilterId()){
            case "WhitelistedAdded":
                setWhitelistedAccount(request);
                break;
            case "Transfer":
                setBalanceMinted(request);
                break;
            case "TransferOrdered":
                setBalanceMinted(request);
                break;    
            default:
                logger.info("Event doesn't registered");
        }

        return true;
    }

    private void setWhitelistedAccount(EventRequest request){
        logger.debug("index:"+request.getIndexedParameters().get(0));
        Map<String,Object> accountParameter = request.getIndexedParameters().get(0);
        String dltAddress = (String)accountParameter.get("value");
        logger.debug("dltAddress:"+dltAddress);
        accountRepository.setWhitelisted(dltAddress);
    }

    private void setBalanceMinted(EventRequest request){
        logger.info("index"+request.getIndexedParameters().get(1));
        Map<String,Object> accountParameter = request.getIndexedParameters().get(1);
        Map<String,Object> value = request.getNonIndexedParameters().get(0);
        String dltAddress = (String)accountParameter.get("value");
        int balance = (int)value.get("value");
        accountRepository.setBalance(dltAddress, balance);
        logger.debug("new balance set");
        LocalDateTime localDateTime = LocalDateTime.now();
        Movement movement = new Movement(movementRepository.getNextMovementId(),localDateTime,ZERO_ADDRESS,dltAddress,(float)balance,mintMessage,balance,0,0,1);
        movementRepository.save(movement);
        logger.debug("new movement registered");
    }

}