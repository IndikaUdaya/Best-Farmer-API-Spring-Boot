package com.indikaudaya.bestfarmer.entities;

import com.sun.source.tree.CaseTree;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String mobile;
    private String password;
    private String userType;
    private boolean status = true;

    @OneToMany(mappedBy = "user")
    private List<Cart> carts;

    @OneToMany(mappedBy = "buyer")
    private List<Delivery> buyerDetails;

    @OneToMany(mappedBy = "seller")
    private List<Delivery> sellerDetails;

    @OneToMany(mappedBy = "buyer")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<Watchlist> watchlists;

    @OneToMany(mappedBy = "buyer")
    private List<SellerReview> buyerReview;

    @OneToMany(mappedBy = "seller")
    private List<Product> products;

    public User() {
    }

    public User(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
