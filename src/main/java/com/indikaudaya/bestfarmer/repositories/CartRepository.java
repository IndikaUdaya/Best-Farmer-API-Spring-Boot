package com.indikaudaya.bestfarmer.repositories;

import com.indikaudaya.bestfarmer.entities.Cart;
import com.indikaudaya.bestfarmer.entities.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser_IdAndProducts_Id(long uid, long pid);

    List<Cart> findByUser_Id(Long uid);
}
