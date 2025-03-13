package lk.ecommerce.zeetradexbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "uid", nullable = false)
    private int userId;

    @Column(name = "pid", nullable = false)
    private int productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
