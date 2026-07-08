package com.cart.demo.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@NoArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Setter
    private String username;
    @Setter
    private String password;
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "IdUserInfo")
    private UserInfo userInfo;

    private final List<Long> purchaseOrdersId = new ArrayList<>();

    public User(String username, String password, UserInfo userInfo) {
        this.username = username;
        this.password = password;
        this.userInfo = userInfo;
    }

}