package ee.cake.order;

import ee.cake.cake.Cake;
import ee.cake.cake.CakeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static ee.cake.order.Orderr.StatusCode.SUBMITTED;

@Component
@Transactional
public class OrderDao {

    @Autowired
    private JdbcTemplate database;

    @Autowired
    private OrderrRepository orderrRepository;

    @Autowired
    private OrderCakeRepository orderCakeRepository;

    @Autowired
    private CakeDao cakeDao;

    public void insert(NewOrderJson json) {
        Long orderId = insertOrder(json.getCustomerName(), findTotalOrderPrice(json));
        insertOrderCake(orderId, json.getCakeId(), json.getAmount());
    }

    public List<Orderr> findAllOrders() {
        return OrderMapper(orderrRepository.findAll());
    }

    private List<Orderr> OrderMapper(List<Orderr> orderrList) {
        for (Orderr orderr : orderrList) {
            orderr.setOrderedCakes(findOrderedCakesByOrder(orderr.getId()));
        }
        return orderrList;
    }

    public void updateStatus(Long orderId, Orderr.StatusCode statusCode) {
        Orderr orderToUpdate = orderrRepository.findOne(orderId);
        orderToUpdate.setStatusCode(statusCode);
        orderrRepository.save(orderToUpdate);
    }

    private List<OrderCake> findOrderedCakesByOrder(Long orderId) {
        return OrderedCakeMapper(orderCakeRepository.findByOrderId(orderId));
    }

    private List<OrderCake> OrderedCakeMapper(List<OrderCake> orderCakeList) {
        for (OrderCake orderCake : orderCakeList) {
            orderCake.setCake(cakeDao.findById(orderCake.getCakeId()));
        }
        return orderCakeList;
    }

    private BigDecimal findTotalOrderPrice(NewOrderJson json) {
        Cake cake = cakeDao.findById(json.getCakeId());
        BigDecimal cakeAmount = BigDecimal.valueOf(json.getAmount());

        return cake.getPrice().multiply(cakeAmount);
    }

    private void insertOrderCake(Long orderId, Long cakeId, Integer amount) {
        OrderCake orderCake = new OrderCake(cakeId, orderId, amount);
        orderCakeRepository.save(orderCake);
    }

    private Long insertOrder(String customerName, BigDecimal amount) {
        Orderr order = new Orderr(customerName, amount, SUBMITTED);
        orderrRepository.save(order);
        return order.getId();
    }

    public List<OrderCake> findAllOrderCakes() {
        return OrderedCakeMapper(orderCakeRepository.findAll());
    }
}
