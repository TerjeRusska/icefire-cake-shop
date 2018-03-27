package ee.cake.order;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderCakeRepository extends CrudRepository<OrderCake, Long> {
    List<OrderCake> findAll();
}
