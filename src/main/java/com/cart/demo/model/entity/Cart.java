package  com.cart.demo.model.entity;

import com.cart.demo.model.enumeration.CartStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private CartStatus status;

    @OneToMany(mappedBy = "cart")
    private List<CartProductQuantity> products = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="user_cart")
    private User user;

    public  Cart(CartStatus status, User user) {
        this.status = status;
        this.user = user;
    }
}