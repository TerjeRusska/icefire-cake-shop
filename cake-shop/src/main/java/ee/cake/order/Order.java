package ee.cake.order;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String customerName;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private StatusCode statusCode;
    @ElementCollection
    //@Transient
    private List<OrderCake> orderedCakes;

    public enum StatusCode {
        SUBMITTED, READY, DELIVERED, CANCELLED
    }

    public Order(){}

    public Order(String customerName, BigDecimal price, StatusCode statusCode){
        this.customerName = customerName;
        this.price = price;
        this.statusCode = statusCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public List<OrderCake> getOrderedCakes() {
        return orderedCakes;
    }

    public void setOrderedCakes(List<OrderCake> orderedCakes) {
        this.orderedCakes = orderedCakes;
    }
}
