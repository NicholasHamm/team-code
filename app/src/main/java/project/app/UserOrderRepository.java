package project.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder, Integer> {

}