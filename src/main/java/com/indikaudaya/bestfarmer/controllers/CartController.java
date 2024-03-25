package com.indikaudaya.bestfarmer.controllers;

import com.indikaudaya.bestfarmer.dto.*;
import com.indikaudaya.bestfarmer.entities.Cart;
import com.indikaudaya.bestfarmer.entities.Product;
import com.indikaudaya.bestfarmer.entities.User;
import com.indikaudaya.bestfarmer.entities.Watchlist;
import com.indikaudaya.bestfarmer.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public List<Cart> getAllCarts() {
        return cartService.getAllCart();
    }

    @GetMapping("/{id}")
    public Cart getCartById(@PathVariable Long id) {
        return cartService.getCartById(id);
    }

    @GetMapping("/user/{id}")
    public List<CartDTO> getCartByUserId(@PathVariable Long id) {
        List<Cart> cartByUserId = cartService.getCartByUserId(id);

        List<CartDTO> cartNew = new ArrayList<>();
        for (Cart cart : cartByUserId) {

            ArrayList<ProductImageDTO> productImageDTOS = new ArrayList<>();
            cart.getProducts().getProductImages().forEach(p -> {
                productImageDTOS.add(new ProductImageDTO(p.getPath()));
            });

            ArrayList<WatchlistDTO> watchlistDTOS = new ArrayList<>();
            cart.getProducts().getWatchlistList().forEach(w -> {
                watchlistDTOS.add(
                        new WatchlistDTO(
                                w.getId(),
                                new UserDTO(
                                        w.getUser().getId(),
                                        w.getUser().getEmail(),
                                        w.getUser().getMobile()
                                ),
                                new ProductDTO(
                                        w.getProducts().getId(),
                                        w.getProducts().getName(),
                                        w.getProducts().getDescription(),
                                        w.getProducts().getPrice(),
                                        w.getProducts().getQty(),
                                        w.getProducts().getUnit(),
                                        w.getProducts().getType(),
                                        w.getProducts().isDeliveryAvailable(),
                                        new CategoryDTO(w.getProducts().getCategory().getId(), w.getProducts().getCategory().getName()),
                                        productImageDTOS,
                                        new UserDTO(
                                                w.getProducts().getSeller().getId(),
                                                w.getProducts().getSeller().getEmail(),
                                                w.getProducts().getSeller().getMobile()
                                        ),
                                        w.getProducts().getWatchlistList().size()
                                )
                        ));
            });

            cartNew.add(
                    new CartDTO(
                            cart.getId(),
                            new ProductDTO(
                                    cart.getProducts().getId(),
                                    cart.getProducts().getName(),
                                    cart.getProducts().getDescription(),
                                    cart.getProducts().getPrice(),
                                    cart.getProducts().getQty(),
                                    cart.getProducts().getUnit(),
                                    cart.getProducts().getType(),
                                    cart.getProducts().isDeliveryAvailable(),
                                    new CategoryDTO(cart.getProducts().getCategory().getId(), cart.getProducts().getCategory().getName()),
                                    productImageDTOS,
                                    new UserDTO(
                                            cart.getProducts().getSeller().getId(),
                                            cart.getProducts().getSeller().getEmail(),
                                            cart.getProducts().getSeller().getMobile()
                                    ),
                                    cart.getProducts().getCarts().size(),
                                    watchlistDTOS
                            )
                    )
            );

        }
        return cartNew;
    }

    @GetMapping("/{uid}/{pid}")
    public CartDTO getWatchlistById(@PathVariable long uid, @PathVariable long pid) {

        Cart cart = cartService.getCartByUIdAndPid(uid, pid);

        CartDTO cartDTO = new CartDTO();
        if (cart != null) {
            Product p = cart.getProducts();

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

            cartDTO.setProductDTO(productDTO1);
            cartDTO.setId(cart.getId());

            User user = cart.getUser();
            UserDTO buyer = new UserDTO();
            seller.setId(user.getId());

            cartDTO.setUserDTO(buyer);
        }
        return cartDTO;
    }

    @PostMapping
    public CartDTO saveCart(@RequestBody CartDTO cart) {
        Cart toDb = new Cart();

        Product p = new Product();
        p.setId(cart.getProductDTO().getId());
        User user = new User();
        user.setId(cart.getUserDTO().getId());

        toDb.setProducts(p);
        toDb.setUser(user);

        Cart saveCart = cartService.saveCart(toDb);

        CartDTO cartDTO = new CartDTO();

        System.out.println(saveCart.getId());

        cartDTO.setId(saveCart.getId());

        UserDTO userDTO = new UserDTO();
        userDTO.setId(saveCart.getId());
        cartDTO.setUserDTO(userDTO);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(saveCart.getProducts().getId());
        cartDTO.setProductDTO(productDTO);

        return cartDTO;
    }

    @DeleteMapping("/{id}")
    public boolean deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return true;
    }

}
