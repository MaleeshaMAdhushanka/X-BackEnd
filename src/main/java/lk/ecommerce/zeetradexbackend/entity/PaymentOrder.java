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



}
