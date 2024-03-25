package com.indikaudaya.bestfarmer.dto;

import com.indikaudaya.bestfarmer.entities.User;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SellerReviewDTO implements Serializable {

    private Long id;
    private String comment;
    private double rating;
    private UserDTO buyer;
    private UserDTO seller;
    private String reviewDate;
    private long productId ;
}
