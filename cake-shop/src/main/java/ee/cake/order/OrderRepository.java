package ee.cake.order;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long>{
    List<Order> findAll();
}
