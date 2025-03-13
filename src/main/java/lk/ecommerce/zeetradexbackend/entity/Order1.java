package lk.ecommerce.zeetradexbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "order")
public class Order1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "order_id", nullable = false)
    private String orderId;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "date", nullable = false)
    private Timestamp date;

    @Column(name = "payment_type", nullable = false)
    private String paymentType;

    @Column(name = "user_id", nullable = false)
    private int userId;
}
