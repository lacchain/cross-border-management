package us.lacchain.crossborder.management.repository;

import us.lacchain.crossborder.management.model.Movement;
import us.lacchain.crossborder.management.model.MovementResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement, String> {

    @Query(name = "MovementRepository.getAllMovementsByDltAddress", nativeQuery=true)
    List<MovementResult> getAllMovementsByDltAddress(@Param("dltAddress") String dltAddress);

    @Query(value = "SELECT nextval('movements_sequence')", nativeQuery=true)
    long getNextMovementId();

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update movements set status = 2 where id = :operationId", nativeQuery=true)
    void setTransferExecuted(@Param("operationId") String operationId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update movements set status = 1 where id = :operationId", nativeQuery=true)
    void setTransferInProgress(@Param("operationId") String operationId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update movements set fee = :fee, rate = :rate, set_fee = :transactionHash where id = :operationId", nativeQuery=true)
    void setFeeRate(@Param("fee") int fee, @Param("rate") int rate, @Param("transactionHash") String transactionHash, @Param("operationId") String operationId);
}