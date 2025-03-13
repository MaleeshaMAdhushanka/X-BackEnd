package lk.ecommerce.zeetradexbackend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderType;
    private Double price;
    private LocalDateTime timestamp;
    private String status;
}
