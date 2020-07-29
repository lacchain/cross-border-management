package us.lacchain.crossborder.management.repository;

import us.lacchain.crossborder.management.model.UserView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserViewRepository extends JpaRepository<UserView, String> {

    @Query(value="SELECT * FROM users_view u WHERE UPPER(u.dlt_address) = UPPER(:dltAddress)", nativeQuery = true)
    UserView findUserByDltAddress(@Param("dltAddress") String dltAddress);

    @Query(value="SELECT * FROM users_view u WHERE UPPER(u.dlt_address) = UPPER(:dltAddress) and u.bank_account = :accountNumber", nativeQuery = true)
    UserView findUserByAddressAccount(@Param("dltAddress") String dltAddress, @Param("accountNumber") String accountNumber);

}