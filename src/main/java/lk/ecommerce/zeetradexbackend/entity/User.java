package lk.ecommerce.zeetradexbackend.entity;

import jakarta.persistence.*;
import lk.ecommerce.zeetradexbackend.enums.USER_ROLE;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    private String mobile;

    private String password;

    private Boolean status;

    private Boolean isVerified;

    private Boolean twoFactorAuthEnabled;

    private String twoFactorAuthSendTo;

    private String picture;

    private String role;

    @OneToMany(mappedBy = "user")
    private List<Assets> assets;

    @OneToMany(mappedBy = "user")
    private List<Withdrawal> withdrawals;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<Wallet> wallets;






}
