package lk.ecommerce.zeetradexbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "watchlists")
public class watchlists {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
