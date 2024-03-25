package com.indikaudaya.bestfarmer.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Data
@AllArgsConstructor
@Entity
public class ProductImage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String path;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductImage() {

    }
}
