package us.lacchain.crossborder.management.repository;

import us.lacchain.crossborder.management.model.Movement;
import us.lacchain.crossborder.management.model.MovementResult;
import org.springframework.data.jpa.repository.JpaRepository;
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
}