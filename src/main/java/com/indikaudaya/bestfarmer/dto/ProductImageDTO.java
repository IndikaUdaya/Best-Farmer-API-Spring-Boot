package com.indikaudaya.bestfarmer.dto;

import com.indikaudaya.bestfarmer.entities.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageDTO implements Serializable {
    private String path;

    public ProductImageDTO(ProductImage productImage) {
        this.path = productImage.getPath();
    }
}
