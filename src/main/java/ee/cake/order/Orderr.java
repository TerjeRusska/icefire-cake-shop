package ee.cake.order;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="ORDERR")
public class Orderr {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ID")
    private Long id;
    @NotNull
    @Column(name="CUSTOMER_NAME")
    private String customerName;
    @NotNull
    @Column(name="PRICE")
    private BigDecimal price;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="STATUS_CODE")
    private Orderr.StatusCode statusCode;
    @Transient
    private List<OrderCake> orderedCakes;

    public enum StatusCode {
        SUBMITTED, READY, DELIVERED, CANCELLED
    }

    public Orderr(){}

    public Orderr(String customerName, BigDecimal price, Orderr.StatusCode statusCode){
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

    public Orderr.StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Orderr.StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public List<OrderCake> getOrderedCakes() {
        return orderedCakes;
    }

    public void setOrderedCakes(List<OrderCake> orderedCakes) {
        this.orderedCakes = orderedCakes;
    }

    public void addOrderedCakesToOrderrList(OrderCake orderCake){
        this.orderedCakes.add(orderCake);
    }
}
