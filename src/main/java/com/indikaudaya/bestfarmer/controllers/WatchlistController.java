package com.indikaudaya.bestfarmer.controllers;

import com.indikaudaya.bestfarmer.dto.*;
import com.indikaudaya.bestfarmer.entities.*;
import com.indikaudaya.bestfarmer.services.SellerReviewService;
import com.indikaudaya.bestfarmer.services.WatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistController {
    @Autowired
    private WatchlistService watchlistService;

    @GetMapping
    public List<Watchlist> getAllWatchlist() {
        return watchlistService.getAllWatchlist();
    }

    @GetMapping("/{id}")
    public Watchlist getWatchlistById(@PathVariable Long id) {
        return watchlistService.getWatchlistById(id);
    }

    @GetMapping("/{uid}/{pid}")
    public WatchlistDTO getWatchlistById(@PathVariable Long uid, @PathVariable Long pid) {
        Watchlist watchlist = watchlistService.getWatchlistByUIdAndPid(uid, pid);

        WatchlistDTO watchlistDTO = new WatchlistDTO();
        if (watchlist != null) {
            Product p = watchlist.getProducts();

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

            watchlistDTO.setProducts(productDTO1);
            watchlistDTO.setId(watchlist.getId());

            User user = watchlist.getUser();
            UserDTO buyer = new UserDTO();
            seller.setId(user.getId());

            watchlistDTO.setUser(buyer);
        }
        return watchlistDTO;
    }

    @GetMapping("/user/{id}")
    public List<WatchlistDTO> getWatchlistByUserId(@PathVariable Long id) {
        List<Watchlist> watchlistByUserId = watchlistService.getWatchlistByUserId(id);

        List<WatchlistDTO> watchlist = new ArrayList<>();
        for (Watchlist watch : watchlistByUserId) {

            ArrayList<ProductImageDTO> productImageDTOS = new ArrayList<>();
            watch.getProducts().getProductImages().forEach(p -> {
                productImageDTOS.add(new ProductImageDTO(p.getPath()));
            });

            ArrayList<WatchlistDTO> watchlistDTOS = new ArrayList<>();
            watch.getProducts().getWatchlistList().forEach(w -> {
                watchlistDTOS.add(
                        new WatchlistDTO(
                                w.getId(),
                                new UserDTO(
                                        w.getUser().getId(),
                                        w.getUser().getEmail(),
                                        w.getUser().getMobile(),
                                        w.getUser().isStatus()
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
                                                w.getProducts().getSeller().getMobile(),
                                                w.getProducts().getSeller().isStatus()
                                        ),
                                        w.getProducts().getWatchlistList().size()
                                )
                        ));
            });

            watchlist.add(
                    new WatchlistDTO(
                            watch.getId(),
                            new ProductDTO(
                                    watch.getProducts().getId(),
                                    watch.getProducts().getName(),
                                    watch.getProducts().getDescription(),
                                    watch.getProducts().getPrice(),
                                    watch.getProducts().getQty(),
                                    watch.getProducts().getUnit(),
                                    watch.getProducts().getType(),
                                    watch.getProducts().isDeliveryAvailable(),
                                    new CategoryDTO(watch.getProducts().getCategory().getId(), watch.getProducts().getCategory().getName()),
                                    productImageDTOS,
                                    new UserDTO(
                                            watch.getProducts().getSeller().getId(),
                                            watch.getProducts().getSeller().getEmail(),
                                            watch.getProducts().getSeller().getMobile(),
                                            watch.getProducts().getSeller().isStatus()
                                    ),
                                    watch.getProducts().getCarts().size(),
                                    watchlistDTOS
                            )
                    )
            );

        }
        return watchlist;
    }

    @PostMapping
    public WatchlistDTO saveWatchlist(@RequestBody Watchlist watchlist) {

        Watchlist saveWatchlist = watchlistService.saveWatchlist(watchlist);

        WatchlistDTO watchlistDTO = new WatchlistDTO();
        watchlistDTO.setId(saveWatchlist.getId());

        UserDTO userDTO = new UserDTO();
        userDTO.setId(saveWatchlist.getId());
        watchlistDTO.setUser(userDTO);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(saveWatchlist.getProducts().getId());
        watchlistDTO.setProducts(productDTO);

        return watchlistDTO;
    }

    @DeleteMapping("/{id}")
    public boolean deleteWatchlist(@PathVariable Long id) {
        watchlistService.deleteWatchlistUser(id);
        return true;
    }
}
