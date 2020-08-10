package us.lacchain.crossborder.management.repository;

import us.lacchain.crossborder.management.model.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;

@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordToken, String> {

    PasswordToken findByToken(String token);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM password_token where token = :token", nativeQuery=true)
    void removeToken(@Param("token") String token);
}