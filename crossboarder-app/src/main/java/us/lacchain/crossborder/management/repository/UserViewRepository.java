package us.lacchain.crossborder.management.repository;

import us.lacchain.crossborder.management.model.UserView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserViewRepository extends JpaRepository<UserView, String> {

    @Query(value="SELECT * FROM users_view u WHERE u.dlt_address = :dltAddress", nativeQuery = true)
    UserView findUserByDltAddress(@Param("dltAddress") String dltAddress);

}