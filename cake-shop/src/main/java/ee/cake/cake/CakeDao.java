package ee.cake.cake;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
@Transactional
public class CakeDao {

    @Autowired
    private CakeRepository cakeRepository;

    public List<Cake> findAvailableCakes() {
        return cakeRepository.findByAvailable(true);
    }

    public List<Cake> findAllCakes() {
        return cakeRepository.findAll();
    }

    public void insert(NewCakeJson json) {
        String name = json.getName();
        BigDecimal price = json.getPrice();
        cakeRepository.save(new Cake(name, price, true));
    }

    public Cake findById(Long cakeId) {
        return cakeRepository.findOne(cakeId);
    }

    public void updateAvailability(Long cakeId, boolean availability) {
        Cake cakeToUpdate = cakeRepository.findOne(cakeId);
        cakeToUpdate.setAvailable(availability);
        cakeRepository.save(cakeToUpdate);
    }
}
