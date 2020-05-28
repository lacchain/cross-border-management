package us.lacchain.crossborder.management.repository;

import us.lacchain.crossborder.management.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, String> {

}