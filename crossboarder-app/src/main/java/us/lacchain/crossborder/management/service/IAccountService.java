package us.lacchain.crossborder.management.service;

import us.lacchain.crossborder.management.model.MovementResult;
import us.lacchain.crossborder.management.model.MovementDetail;
import us.lacchain.crossborder.management.model.AccountResult;
import us.lacchain.crossborder.management.model.Transaction;

import java.util.List;

public interface IAccountService {

    List<MovementResult> getMovementsByAccount(String dltAddress);
    MovementDetail getMovementDetail(long idMovementDetail, String dltAddress);
    List<AccountResult> getAllAccounts();
    List<Transaction> getTransactions();

}