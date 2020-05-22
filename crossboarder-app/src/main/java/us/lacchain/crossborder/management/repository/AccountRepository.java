package us.lacchain.crossborder.management.repository;

import us.lacchain.crossborder.management.model.Account;
import us.lacchain.crossborder.management.model.AccountResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Component;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    @Query(name = "AccountRepository.getAllAccounts", nativeQuery=true)
    List<AccountResult> getAllAccounts();

}