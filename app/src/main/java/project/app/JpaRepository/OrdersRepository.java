package project.app.JpaRepository;

import project.app.Entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    @Transactional
    @Modifying
    @Query("update Orders set status = :orderStatus, updatedAt = :updatedAt where id = :orderId")
    void setStatusById(@Param("orderId") Integer orderId,
                       @Param("orderStatus")  Integer orderStatus,
                       @Param("updatedAt") Timestamp updatedAt);

    @Query("select o from Orders o where o.userId = :userId")
    List<Orders> findByUserId(@Param("userId") Integer userId);

}
