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
    private final List<CartProductQuantity> products = new ArrayList<>();

    private Long userId;

    private LocalDateTime creationDate;

    public  Cart(CartStatus status, Long userId) {
        this.status = status;
        this.userId = userId;
        this.creationDate = LocalDateTime.now();
    }
}