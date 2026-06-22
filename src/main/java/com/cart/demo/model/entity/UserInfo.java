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
    @OneToOne(mappedBy = "userInfo")
    private User user;

    public UserInfo(String firstname, String lastname, String city, String country) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.city = city;
        this.country = country;
    }

    // Setters
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public  void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public void setCity(String city){
        this.city = city;
    }
    public void setCountry(String country){
        this.country = country;
    }
}