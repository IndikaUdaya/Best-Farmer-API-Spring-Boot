package com.indikaudaya.bestfarmer.dto;

import com.indikaudaya.bestfarmer.entities.Order;
import com.indikaudaya.bestfarmer.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderItemDTO implements Serializable {

    private Long id;

    private OrderDTO order;

    private ProductDTO product;

    private double quantity;

    private  long productId;


}
