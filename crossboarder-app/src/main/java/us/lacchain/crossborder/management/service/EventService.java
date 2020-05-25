package us.lacchain.crossborder.management.service;

import us.lacchain.crossborder.management.clients.request.EventRequest;
import us.lacchain.crossborder.management.repository.AccountRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EventService implements IEventService {

    Logger logger = LoggerFactory.getLogger(EventService.class);

    @Autowired
    private AccountRepository accountRepository;

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
            default:
                logger.info("Event doesn't registered");
        }

        return true;
    }

    private void setWhitelistedAccount(EventRequest request) throws Exception{
        logger.debug("index:"+request.getIndexedParameters().get(0));
        Map<String,Object> accountParameter = request.getIndexedParameters().get(0);
        String dltAddress = (String)accountParameter.get("value");
        logger.debug("dltAddress:"+dltAddress);
        accountRepository.setWhitelisted(dltAddress);
    }

    private void setBalanceMinted(EventRequest request) throws Exception{
        logger.info("index"+request.getIndexedParameters().get(1));
        Map<String,Object> accountParameter = request.getIndexedParameters().get(1);
        Map<String,Object> value = request.getNonIndexedParameters().get(0);
        String dltAddress = (String)accountParameter.get("value");
        int balance = (int)value.get("value");
        accountRepository.setBalance(dltAddress, balance);
    }

}