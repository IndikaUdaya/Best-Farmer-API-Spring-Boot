package com.indikaudaya.bestfarmer.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String email;
    private String mobile;
    private String password;

    private List<CartDTO> carts;

    private List<DeliveryDTO> buyerDetails;

    private List<DeliveryDTO> sellerDetails;

    private List<WatchlistDTO> watchlist;

    private List<SellerReviewDTO> buyerReview;

    private List<ProductDTO> products;

    private String userType;

    private boolean status;

    public UserDTO(Long id, String email, String mobile,  String userType, boolean status) {
        this.id = id;
        this.email = email;
        this.mobile = mobile;
        this.userType = userType;
        this.status = status;
    }

    public UserDTO(Long id, String email, String mobile,  boolean status) {
        this.id = id;
        this.email = email;
        this.mobile = mobile;
        this.status = status;
    }
    public UserDTO(Long id, String email, String mobile) {
        this.id = id;
        this.email = email;
        this.mobile = mobile;
    }
}
