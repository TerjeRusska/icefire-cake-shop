package ee.cake.order;

import ee.cake.cake.Cake;
import ee.cake.cake.CakeDao;
import ee.cake.cake.CakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private CakeRepository cakeRepository;

    @Autowired
    private CakeDao cakeDao;

    public void insert(NewOrderJson json) {
        Long orderId = insertOrder(json.getCustomerName(), findTotalOrderPrice(json));
        Long orderCakeId = insertOrderCake(orderId, json.getCakeId(), json.getAmount());
        Orderr orderr = orderrRepository.findOne(orderId);
    }

    /*public List<Order> findAllOrders() {
        return database.query("SELECT * FROM ORDER;", new OrderMapper());
    }*/

    /*public List<OrderCake> findAllOrderCakes() {
        return database.query("SELECT * FROM ORDER_CAKE;", new OrderCakeMapper());
    }*/

    public List<OrderCake> findAllOrderCakes() {
        return orderCakeRepository.findAll();
    }

    public List<Orderr> findAllOrders() {
        return orderrRepository.findAll();
    }

    /*public void updateStatus(Long orderId, Order.StatusCode statusCode) {
        List<Object> args = new ArrayList<>();
        args.add(statusCode.toString());
        args.add(orderId);

        database.update("UPDATE ORDER SET STATUS_CODE = ? WHERE ID = ?;", args.toArray());
    }*/

    public void updateStatus(Long orderId, Orderr.StatusCode statusCode) {
        Orderr orderToUpdate = orderrRepository.findOne(orderId);
        orderToUpdate.setStatusCode(statusCode);
        orderrRepository.save(orderToUpdate);
    }

    /*private List<OrderCake> findOrderedCakesByOrder(Long orderId) {
        List<Object> args = new ArrayList<>();
        args.add(orderId);

        return database.query("SELECT * FROM ORDER_CAKE WHERE ORDER_ID = ?;", args.toArray(), new OrderCakeMapper());

    }*/

    private List<OrderCake> findOrderedCakesByOrder(Long orderId) {
        return orderCakeRepository.findByOrderId(orderId);
    }

    private BigDecimal findTotalOrderPrice(NewOrderJson json) {
        Cake cake = cakeDao.findById(json.getCakeId());
        BigDecimal cakeAmount = BigDecimal.valueOf(json.getAmount());

        return cake.getPrice().multiply(cakeAmount);
    }

    /*private void insertOrderCake(Long orderId, Long cakeId, Integer amount) {
        List<Object> args = new ArrayList<>();
        args.add(orderId);
        args.add(cakeId);
        args.add(amount);

        System.out.println(orderId);
        System.out.println(cakeId);
        System.out.println(amount);

        //database.update("INSERT INTO ORDER_CAKE (order_id, cake_id, amount) VALUES (?,?,?);", args.toArray());
        database.update("INSERT INTO ORDER_CAKE (order_id, cake_id, amount) VALUES (1,1,1);");
    }*/

    private Long insertOrderCake(Long orderId, Long cakeId, Integer amount) {
        OrderCake orderCake = new OrderCake(cakeId, orderId, amount, cakeDao.findById(cakeId));
        orderCakeRepository.save(orderCake);
        return orderCake.getId();
    }

    /*private Long insertOrder(String customerName, BigDecimal amount) {
        List<Object> args = new ArrayList<>();
        args.add(customerName);
        args.add(amount);
        args.add(SUBMITTED.toString());

        database.update("INSERT INTO ORDER (customer_name, price, status_code) VALUES (?,?,?);", args.toArray());

        return database.queryForObject("CALL IDENTITY()", Long.class); //return last generated identity
    }*/

    private Long insertOrder(String customerName, BigDecimal amount) {
        Orderr order = new Orderr(customerName, amount, SUBMITTED);
        orderrRepository.save(order);
        return order.getId();
    }

    /*public List<Orderr> testOrders() {
        return database.query("SELECT * FROM ORDERR;", new OrderMapper());
    }*/

    private final class OrderMapper implements RowMapper<Orderr> {
        @Override
        public Orderr mapRow(ResultSet rs, int rowNum) throws SQLException {
            Orderr order = new Orderr();
            order.setId(rs.getLong("id"));
            order.setCustomerName(rs.getString("customer_name"));
            order.setPrice(rs.getBigDecimal("price"));
            order.setStatusCode(Orderr.StatusCode.valueOf(rs.getString("status_code")));
            order.setOrderedCakes(findOrderedCakesByOrder(order.getId()));
            return order;
        }
    }

    private final class OrderCakeMapper implements RowMapper<OrderCake> {
        @Override
        public OrderCake mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderCake orderCake = new OrderCake();
            orderCake.setId(rs.getLong("id"));
            orderCake.setAmount(rs.getInt("amount"));
            orderCake.setCake(cakeDao.findById(rs.getLong("cake_id")));
            return orderCake;
        }
    }
}
