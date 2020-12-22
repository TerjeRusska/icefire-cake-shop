package ee.cake.cake;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CakeRepository extends CrudRepository<Cake, Long> {
    List<Cake> findByAvailable(boolean available);
    List<Cake> findAll();
}
