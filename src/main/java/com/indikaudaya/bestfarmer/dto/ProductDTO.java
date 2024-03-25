package com.indikaudaya.bestfarmer.dto;

import com.indikaudaya.bestfarmer.entities.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO implements Serializable {
    private Long id;

    private String name;
    private String description;
    private double price;
    private double qty;
    private String unit;
    private String type;

    private long orderId;

    private boolean deliveryAvailable;

    private CategoryDTO category;

    private List<ProductImageDTO> productImages;

    private UserDTO seller;

    private List<WatchlistDTO> watchlistList;

    private int cartCount;

    private int watchlistCount;

    private boolean status;

    private double orderQty;
    private String orderDate;
    private boolean productDelivered;

    public ProductDTO(Long id, String name, String description, double price, double qty, String unit, String type, boolean deliveryAvailable, CategoryDTO category, List<ProductImageDTO> productImages, UserDTO seller, int cartCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.qty = qty;
        this.unit = unit;
        this.type = type;
        this.deliveryAvailable = deliveryAvailable;
        this.category = category;
        this.productImages = productImages;
        this.seller = seller;
        this.cartCount = cartCount;
    }

    public ProductDTO(Long id, String name, String description, double price, double qty, String unit, String type, boolean deliveryAvailable, CategoryDTO category, List<ProductImageDTO> productImages, UserDTO seller, int cartCount, List<WatchlistDTO> dto) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.qty = qty;
        this.unit = unit;
        this.type = type;
        this.deliveryAvailable = deliveryAvailable;
        this.category = category;
        this.productImages = productImages;
        this.seller = seller;
        this.cartCount = cartCount;
        this.watchlistList = dto;
    }


}
