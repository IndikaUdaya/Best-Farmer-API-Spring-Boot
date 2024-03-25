package com.indikaudaya.bestfarmer.dto;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartDTO implements Serializable {

    private long id;
    private ProductDTO productDTO;
    private UserDTO userDTO;

    public CartDTO(long userId, ProductDTO productDTO) {
        this.id = userId;
        this.productDTO = productDTO;
    }

}
