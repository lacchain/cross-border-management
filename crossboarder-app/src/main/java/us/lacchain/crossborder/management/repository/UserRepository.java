package us.lacchain.crossborder.management.repository;

import us.lacchain.crossborder.management.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import us.lacchain.crossborder.management.model.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    //@Query(value = "SELECT u.email,u.password, accounts.dlt_address FROM users u INNER JOIN accounts ON accounts.user_id = u.id WHERE u.email = :email and u.password = :password", nativeQuery = true)
    //UserLogin getUserLogin(@Param("email") String email, @Param("password") String password);

    @Query(name = "UserRepository.getUserLogin", nativeQuery=true)
    UserLogin getUserLogin(@Param("email") String email, @Param("password") String password, @Param("dltAddress") String dltAddress);

    User findByEmail(String email);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update users set password = :password where users.id = (SELECT password_token.user_id FROM password_token WHERE password_token.token = :token)", nativeQuery=true)
    int resetPassword(@Param("token") String token, @Param("password") String password);

}