package lk.ecommerce.zeetradexbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double quantity;
    private Double buyPrice;
    private Double sellPrice;

    @ManyToOne
    @JoinColumn(name = "coin_id", nullable = false)
    private Coin coin;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
