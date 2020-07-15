package us.lacchain.crossborder.management.repository;

import us.lacchain.crossborder.management.model.Account;
import us.lacchain.crossborder.management.model.AccountResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    @Query(name = "AccountRepository.getAllAccounts", nativeQuery=true)
    List<AccountResult> getAllAccounts();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update accounts set status = 1, currency = :currency where dlt_address = :dltAddress", nativeQuery=true)
    void setWhitelisted(@Param("dltAddress") String dltAddress, @Param("currency") String currency);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update accounts set status = 0 where dlt_address = :dltAddress", nativeQuery=true)
    void setWhitelistedRemove(@Param("dltAddress") String dltAddress);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update accounts set balance = :balance where dlt_address = :dltAddress", nativeQuery=true)
    void setBalance(@Param("dltAddress") String dltAddress, @Param("balance") int balance);

}