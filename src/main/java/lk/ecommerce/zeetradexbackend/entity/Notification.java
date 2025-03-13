package lk.ecommerce.zeetradexbackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private String message;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private User toUser;

}
