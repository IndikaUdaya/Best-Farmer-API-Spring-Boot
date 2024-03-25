package com.indikaudaya.bestfarmer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDTO {

    private Long id;

    private String address;

    private LocalDateTime deliveryDate;

    private double deliveryCostPerKm;

    private ProductDTO product;

    private UserDTO buyer;

    private UserDTO seller;

}
