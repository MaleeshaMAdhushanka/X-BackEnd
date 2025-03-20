package lk.ecommerce.zeetradexbackend.entity;

import jakarta.persistence.*;
import lk.ecommerce.zeetradexbackend.enums.PaymentMethod;
import lk.ecommerce.zeetradexbackend.enums.PaymentOrderStatus;

@Entity
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private Long amount;

    private PaymentOrderStatus status;

    private PaymentMethod paymentMethod;

    //one user have many payment order
    @ManyToOne
    private User user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public PaymentOrderStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentOrderStatus status) {
        this.status = status;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
