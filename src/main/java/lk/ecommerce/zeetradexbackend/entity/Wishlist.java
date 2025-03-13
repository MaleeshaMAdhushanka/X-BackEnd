package lk.ecommerce.zeetradexbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "wishlist")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wishlistId;

    @Column(name = "user_id", nullable = false)
    private int userId;
    @Column(name = "product_id", nullable = false)
    private int productId;


}
