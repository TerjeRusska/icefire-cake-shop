package ee.cake.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ee.cake.cake.Cake;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ORDER_CAKE")
public class OrderCake {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="ID")
    private Long id;
    @NotNull
    @Column(name = "CAKE_ID")
    private Long cakeId;
    @NotNull
    @Column(name = "ORDER_ID")
    private Long orderId;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "CAKE")
    private Cake cake;
    @NotNull
    @Column(name="AMOUNT")
    private Integer amount;

    public OrderCake(Long cakeId, Long orderId, Integer amount, Cake cake) {
        this.cakeId = cakeId;
        this.orderId = orderId;
        this.amount = amount;
        this.cake = cake;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCakeId() {
        return cakeId;
    }

    public void setCakeId(Long cakeId) {
        this.cakeId = cakeId;
    }
}
