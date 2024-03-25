package com.indikaudaya.bestfarmer.controllers;

import com.indikaudaya.bestfarmer.dto.*;
import com.indikaudaya.bestfarmer.entities.*;
import com.indikaudaya.bestfarmer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserService userService;
    @GetMapping("/{email}")
    public UserDTO getUser(@PathVariable String email) {
        User userByEmail = userService.getUserByEmail(email);
        UserDTO user;
        if (userByEmail != null) {
            user = new UserDTO();

            user.setId(userByEmail.getId());
            user.setEmail(userByEmail.getEmail());
            user.setMobile(user.getMobile());

            List<Cart> carts = userByEmail.getCarts();
            ArrayList<CartDTO> cartDTO = new ArrayList<>();
            carts.forEach(c -> {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(c.getId());
                cartDTO.add(new CartDTO(c.getId(), productDTO));
            });

            user.setCarts(cartDTO);

            ArrayList<ProductDTO> productDTO = new ArrayList<>();

            userByEmail.getProducts().forEach(p -> {
                ProductDTO productDTO1 = new ProductDTO();

                ArrayList<ProductImageDTO> newImage = new ArrayList<>();

                p.getProductImages().forEach(productImage -> {
                    ProductImageDTO productImage1 = new ProductImageDTO();
                    productImage1.setPath(productImage.getPath());
                    newImage.add(productImage1);
                });

                productDTO1.setCategory(new CategoryDTO(p.getCategory().getId(), p.getCategory().getName()));
                productDTO1.setDeliveryAvailable(p.isDeliveryAvailable());
                productDTO1.setDescription(p.getDescription());
                productDTO1.setName(p.getName());
                productDTO1.setPrice(p.getPrice());
                productDTO1.setQty(p.getQty());
                productDTO1.setType(p.getType());
                productDTO1.setUnit(p.getUnit());
                productDTO1.setProductImages(newImage);

                UserDTO seller = new UserDTO();
                seller.setId(p.getSeller().getId());

                productDTO1.setSeller(seller);

                productDTO.add(productDTO1);

            });

            user.setProducts(productDTO);

            ArrayList<WatchlistDTO> watchlist = new ArrayList<>();

            userByEmail.getWatchlists().forEach(w -> {
                WatchlistDTO watchlistDTO = new WatchlistDTO();
                watchlistDTO.setId(w.getId());

                Product p = w.getProducts();
                ProductDTO productDTO1 = new ProductDTO();

                ArrayList<ProductImageDTO> newImage = new ArrayList<>();

                p.getProductImages().forEach(productImage -> {
                    ProductImageDTO productImage1 = new ProductImageDTO();
                    productImage1.setPath(productImage.getPath());
                    newImage.add(productImage1);
                });

                productDTO1.setCategory(new CategoryDTO(p.getCategory().getId(), p.getCategory().getName()));
                productDTO1.setDeliveryAvailable(p.isDeliveryAvailable());
                productDTO1.setDescription(p.getDescription());
                productDTO1.setName(p.getName());
                productDTO1.setPrice(p.getPrice());
                productDTO1.setQty(p.getQty());
                productDTO1.setType(p.getType());
                productDTO1.setUnit(p.getUnit());
                productDTO1.setProductImages(newImage);

                productDTO1.setId(w.getProducts().getId());

                UserDTO seller = new UserDTO();
                seller.setId(p.getSeller().getId());

                productDTO1.setSeller(seller);

                watchlistDTO.setProducts(productDTO1);

                watchlist.add(watchlistDTO);
            });

            user.setWatchlist(watchlist);

            user.setEmail(userByEmail.getEmail());
            user.setId(userByEmail.getId());
            user.setStatus(userByEmail.isStatus());
            user.setUserType(userByEmail.getUserType());

        } else {
            user = new UserDTO();
        }
        return user;
    }


}
