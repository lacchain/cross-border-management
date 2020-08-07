package us.lacchain.crossborder.management.repository;

import us.lacchain.crossborder.management.model.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordToken, String> {
}