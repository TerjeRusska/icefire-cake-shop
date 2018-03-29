package ee.cake.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    private OrderDao orderDao;

    @PostMapping("new")
    public void createNewOrder(@RequestBody NewOrderJson json) {
        orderDao.insert(json);
    }

    @PostMapping("/{orderId}/cancel")
    public void cancelOrder(@PathVariable Long orderId) {
        orderDao.updateStatus(orderId, Orderr.StatusCode.CANCELLED);
    }

    @PostMapping("/{orderId}/ready")
    public void readyOrder(@PathVariable Long orderId) {
        orderDao.updateStatus(orderId, Orderr.StatusCode.READY);

    }

    @PostMapping("/{orderId}/deliver")
    public void deliverOrder(@PathVariable Long orderId) {
        orderDao.updateStatus(orderId, Orderr.StatusCode.DELIVERED);
    }

    @GetMapping("all")
    public List<Orderr> findAllOrders() {
        return orderDao.findAllOrders();
    }

    @GetMapping("allordercakes")
    public List<OrderCake> findAllCakeOrders() {
        return orderDao.findAllOrderCakes();
    }
}
