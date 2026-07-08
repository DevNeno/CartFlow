package com.cart.demo.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "userInfo")
@NoArgsConstructor
@Getter
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Setter
    private String firstname;
    @Setter
    private String lastname;
    @Setter
    private String city;
    @Setter
    private String country;
    @Setter
    @OneToOne(mappedBy = "userInfo")
    private User user;

    public UserInfo(String firstname, String lastname, String city, String country) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.city = city;
        this.country = country;
    }
}