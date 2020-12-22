package ee.cake.order;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderrRepository extends CrudRepository<Orderr, Long> {
    List<Orderr> findAll();
}
