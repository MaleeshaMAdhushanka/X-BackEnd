package lk.ecommerce.zeetradexbackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int product_id;

    @Column(name = "name", nullable = false)
    private String productName;

    @Column(name = "description")
    private String productDescription;

    @Column(name = "price", nullable = false)
    private float productPrice;

    @Column(name = "discount", nullable = false)
    private int productDiscount;

    @Column(name = "quantity", nullable = false)
    private int productQuantity;

    @Column(name = "images")
    private String productImages;

    @Column(name = "category_id", nullable = false)
    private int categoryId;







}
