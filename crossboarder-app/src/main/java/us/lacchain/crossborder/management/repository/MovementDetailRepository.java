package us.lacchain.crossborder.management.repository;

import us.lacchain.crossborder.management.model.MovementDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import us.lacchain.crossborder.management.model.Transaction;
import java.util.List;

@Repository
public interface MovementDetailRepository extends JpaRepository<MovementDetail, String> {

    @Query(value="SELECT * FROM movements_view m WHERE m.id = :idMovementDetail and (UPPER(sender_dlt_address) = UPPER(:dltAddress) or UPPER(receiver_dlt_address) = UPPER(:dltAddress))", nativeQuery = true)
    MovementDetail getMovementDetail(@Param("idMovementDetail") String idMovementDetail, @Param("dltAddress") String dltAddress);

    @Query(value="SELECT * FROM movements_view m WHERE m.id = :idMovementDetail", nativeQuery = true)
    MovementDetail getMovementDetail(@Param("idMovementDetail") String idMovementDetail);

    @Query(name = "MovementDetailRepository.getAllTransactions", nativeQuery=true)
    List<Transaction> getAllTransactions();

}