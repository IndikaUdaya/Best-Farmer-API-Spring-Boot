package com.indikaudaya.bestfarmer.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;
    private double qty;
    private String unit;
    private String type;

    private boolean status=true;

    @Column(name = "delivery_option")
    private boolean deliveryAvailable;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private List<ProductImage> productImages;

    @OneToMany(mappedBy = "product", cascade = CascadeType.DETACH)
    private List<SellerReview> sellerReviews;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @OneToMany(mappedBy = "product",cascade = CascadeType.DETACH)
    private List<Delivery> deliveryList;

    @OneToMany(mappedBy = "product",cascade = CascadeType.DETACH)
    private List<ProductOrderItem> orders;

    @OneToMany(mappedBy = "products",cascade = CascadeType.DETACH)
    private List<Watchlist> watchlistList;

    @OneToMany(mappedBy = "products",cascade = CascadeType.DETACH)
    private List<Cart> carts;

    public Product() {
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", qty=" + qty +
                ", unit='" + unit + '\'' +
                ", type='" + type + '\'' +
                ", deliveryAvailable=" + deliveryAvailable +
                ", category=" + category +
                ", productImages=" + productImages +
                ", seller=" + seller +
                ", deliveryList=" + deliveryList +
                ", watchlistList=" + watchlistList +
                ", carts=" + carts +
                '}';
    }
}
