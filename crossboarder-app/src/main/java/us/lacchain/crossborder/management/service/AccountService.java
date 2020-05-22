package us.lacchain.crossborder.management.service;

import us.lacchain.crossborder.management.model.AccountResult;
import us.lacchain.crossborder.management.model.MovementResult;
import us.lacchain.crossborder.management.model.MovementDetail;
import us.lacchain.crossborder.management.model.Transaction;
import us.lacchain.crossborder.management.repository.AccountRepository;
import us.lacchain.crossborder.management.repository.MovementRepository;
import us.lacchain.crossborder.management.repository.MovementDetailRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private MovementDetailRepository movementDetailRepository;

    public List<MovementResult> getMovementsByAccount(String dltAddress){
        return movementRepository.getAllMovementsByDltAddress(dltAddress);
    }

    public MovementDetail getMovementDetail(long idMovementDetail, String dltAddress){
        return movementDetailRepository.getMovementDetail(idMovementDetail, dltAddress);
    }

    public List<AccountResult> getAllAccounts(){
        return accountRepository.getAllAccounts();
    }

    public List<Transaction> getTransactions(){
        try{
            return movementDetailRepository.getAllTransactions();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println ("NULOOO");
        return null;
    }
}