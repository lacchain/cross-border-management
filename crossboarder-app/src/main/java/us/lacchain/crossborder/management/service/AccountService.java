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

import org.springframework.dao.DataAccessException;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private MovementDetailRepository movementDetailRepository;

    public List<MovementResult> getMovementsByAccount(String dltAddress)throws DataAccessException{
        return movementRepository.getAllMovementsByDltAddress(dltAddress);
    }

    public MovementDetail getMovementDetail(long idMovementDetail, String dltAddress) throws DataAccessException{
        return movementDetailRepository.getMovementDetail(idMovementDetail, dltAddress);
    }

    public List<AccountResult> getAllAccounts()throws DataAccessException{
        return accountRepository.getAllAccounts();
    }

    public List<Transaction> getTransactions()throws DataAccessException{
        return movementDetailRepository.getAllTransactions();
    }

    public MovementDetail getMovementDetail(long idMovementDetail) throws DataAccessException{
        return movementDetailRepository.getMovementDetail(idMovementDetail);
    }
}