package com.indikaudaya.bestfarmer.dto;

import com.indikaudaya.bestfarmer.entities.Watchlist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WatchlistDTO {

    private Long id;

    private UserDTO user;

    private ProductDTO products;

    public WatchlistDTO(Long id, ProductDTO products) {
        this.id = id;
        this.products = products;
    }
    public WatchlistDTO(Long id) {
        this.id = id;
    }
}
