package project.app;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutRepository extends CrudRepository<Checkout, Long> {
}
