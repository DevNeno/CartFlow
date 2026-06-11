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

    @ManyToMany
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "IdCart"),
            inverseJoinColumns = @JoinColumn(name = "IdProduct")
    )
    private List<Product> products = new ArrayList<>();

    public  Cart(CartStatus status) {
        this.status = status;
    }
}