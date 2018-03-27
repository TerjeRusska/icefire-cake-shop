package ee.cake.order;

import ee.cake.cake.Cake;

import javax.persistence.*;

@Entity
public class OrderCake {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Transient
    private Cake cake;
    private Long order_id;
    private Long cake_id;
    private Integer amount;

    public OrderCake(Cake cake, Long orderId, Long cakeId, Integer amount) {
        this.cake = cake;
        this.order_id = orderId;
        this.cake_id = cakeId;
        this.amount = amount;
    }

    public OrderCake(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cake getCake() {
        return cake;
    }

    public void setCake(Cake cake) {
        this.cake = cake;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Long getCake_id() {
        return cake_id;
    }

    public void setCake_id(Long cake_id) {
        this.cake_id = cake_id;
    }
}
