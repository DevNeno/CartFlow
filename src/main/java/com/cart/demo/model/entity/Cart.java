package  com.cart.demo.model.entity;

import com.cart.demo.model.enumeration.CartStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
@NoArgsConstructor
@Getter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Setter
    private CartStatus status;

    @OneToMany(mappedBy = "cart")
    private List<CartProductQuantity> products = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="user_cart")
    private User user;

    private LocalDateTime creationDate;

    public  Cart(CartStatus status, User user) {
        this.status = status;
        this.user = user;
        this.creationDate = LocalDateTime.now();
    }
}