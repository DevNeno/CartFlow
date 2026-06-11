package com.cart.demo.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "userInfo")
@NoArgsConstructor
@Getter
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstname;
    private String lastname;
    private String city;
    private String country;
    @OneToOne
    @JoinColumn(name="IdUser")
    private User user;

    public UserInfo(String firstname, String lastname, String city, String country) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.city = city;
        this.country = country;
    }
}