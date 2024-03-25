package com.indikaudaya.bestfarmer.services;

import com.indikaudaya.bestfarmer.entities.Cart;
import com.indikaudaya.bestfarmer.entities.Watchlist;
import com.indikaudaya.bestfarmer.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public List<Cart> getAllCart() {
        return cartRepository.findAll();
    }

    public Cart getCartById(long id) {
        return cartRepository.findById(id).orElse(null);
    }

    public Cart getCartByUIdAndPid(long uid, long pid) {
        return cartRepository.findByUser_IdAndProducts_Id(uid,pid);
    }

    public List<Cart> getCartByUserId(Long uid){
        return cartRepository.findByUser_Id(uid);
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }
}
