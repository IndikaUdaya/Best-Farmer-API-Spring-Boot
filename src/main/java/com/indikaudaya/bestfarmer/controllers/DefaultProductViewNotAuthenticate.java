package com.indikaudaya.bestfarmer.controllers;

import com.indikaudaya.bestfarmer.dto.*;
import com.indikaudaya.bestfarmer.entities.Product;
import com.indikaudaya.bestfarmer.entities.SellerReview;
import com.indikaudaya.bestfarmer.entities.Watchlist;
import com.indikaudaya.bestfarmer.repositories.DefaultSellerReviewRepository;
import com.indikaudaya.bestfarmer.repositories.SellerReviewRepository;
import com.indikaudaya.bestfarmer.services.DefaultProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/default/product")
public class DefaultProductViewNotAuthenticate {
    @Autowired
    DefaultProductService defaultProductService;

    @Autowired
    DefaultSellerReviewRepository sellerReviewRepository;

    @GetMapping
    public List<ProductDTO> getDefaultAllProduct() {

        List<Product> allProduct = defaultProductService.getAllProduct();

        ArrayList<ProductDTO> pd = new ArrayList<>();

        if (allProduct != null) {

            for (Product p : allProduct) {

                ProductDTO productDTO = new ProductDTO();

                ArrayList<ProductImageDTO> pi = new ArrayList<>();
                p.getProductImages().forEach(productImage -> {
                    pi.add(new ProductImageDTO(productImage.getPath()));
                });

                productDTO.setProductImages(pi);
                productDTO.setId(p.getId());
                productDTO.setCategory(new CategoryDTO(p.getCategory().getId(), p.getCategory().getName()));
                productDTO.setName(p.getName());
                productDTO.setDescription(p.getDescription());
                productDTO.setQty(p.getQty());
                productDTO.setPrice(p.getPrice());
                productDTO.setType(p.getType());
                productDTO.setUnit(p.getUnit());
                productDTO.setDeliveryAvailable(p.isDeliveryAvailable());
                productDTO.setCartCount(p.getCarts().size());

                Long sellerId = p.getSeller().getId();
                List<SellerReview> allBySellerReview = sellerReviewRepository.getAllBySellerReview(sellerId);

                ArrayList<SellerReviewDTO> sellerReviewDTOS = new ArrayList<>();

                for (SellerReview sellerReview :allBySellerReview) {
                    sellerReviewDTOS.add(new SellerReviewDTO(
                            sellerReview.getId(),
                            sellerReview.getComment(),
                            sellerReview.getRating(),
                            null,
                            new UserDTO(
                                    sellerReview.getSeller().getId(),
                                    sellerReview.getSeller().getEmail(),
                                    sellerReview.getSeller().getMobile(),
                                    sellerReview.getSeller().getUserType(),
                                    sellerReview.getSeller().isStatus()
                            ),
                            sellerReview.getReviewDate(),
                            sellerReview.getProduct().getId()
                    ));
                }

                UserDTO seller = new UserDTO();
                seller.setId(p.getSeller().getId());
                seller.setEmail(p.getSeller().getEmail());
                seller.setMobile(p.getSeller().getMobile());
                seller.setStatus(p.getSeller().isStatus());

                seller.setBuyerReview(sellerReviewDTOS);

                productDTO.setSeller(seller);

                pd.add(productDTO);
            }
        }
        return pd;
    }

    @GetMapping("/{product-id}")
    public ProductDTO getDefaultById(@PathVariable("product-id") long proId) {

        Product allProduct = defaultProductService.getProductById(proId);

        ProductDTO productDTO = new ProductDTO();
        if (allProduct != null) {

            ArrayList<ProductImageDTO> pi = new ArrayList<>();

            allProduct.getProductImages().forEach(productImage -> {
                pi.add(new ProductImageDTO(productImage.getPath()));
            });

            productDTO.setProductImages(pi);
            productDTO.setId(allProduct.getId());
            productDTO.setCategory(new CategoryDTO(allProduct.getCategory().getId(), allProduct.getCategory().getName()));
            productDTO.setName(allProduct.getName());
            productDTO.setDescription(allProduct.getDescription());
            productDTO.setQty(allProduct.getQty());
            productDTO.setPrice(allProduct.getPrice());
            productDTO.setType(allProduct.getType());
            productDTO.setUnit(allProduct.getUnit());

            ArrayList<SellerReviewDTO> reviewDTOS = new ArrayList<>();

            allProduct.getSeller().getBuyerReview().forEach(sellerReview -> {
                reviewDTOS.add(new SellerReviewDTO(
                        sellerReview.getId(),
                        sellerReview.getComment(),
                        sellerReview.getRating(),
                        new UserDTO(
                                sellerReview.getBuyer().getId(),
                                sellerReview.getBuyer().getEmail(),
                                sellerReview.getBuyer().getMobile(),
                                sellerReview.getBuyer().isStatus()
                        ),
                        new UserDTO(
                                sellerReview.getSeller().getId(),
                                sellerReview.getSeller().getEmail(),
                                sellerReview.getSeller().getMobile(),
                                sellerReview.getSeller().isStatus()
                        ),
                        sellerReview.getReviewDate(),
                        sellerReview.getProduct().getId()
                ));
            });

            productDTO.setCartCount(allProduct.getCarts().size());
            productDTO.setDeliveryAvailable(allProduct.isDeliveryAvailable());

            UserDTO seller = new UserDTO();
            seller.setId(allProduct.getSeller().getId());
            seller.setEmail(allProduct.getSeller().getEmail());
            seller.setMobile(allProduct.getSeller().getMobile());
            seller.setBuyerReview(reviewDTOS);
            productDTO.setSeller(seller);

        }
        return productDTO;
    }

    @GetMapping("/{cateName}/{searchWord}")
    public List<ProductDTO> getAllProductsBySearch(@PathVariable String cateName, @PathVariable String searchWord) {
        List<Product> allProductBySearch = defaultProductService.getAllProductBySearch(cateName, searchWord);

        List<ProductDTO> productDTOList = new ArrayList<>();

        for (Product bySearch : allProductBySearch) {

            ProductDTO dto = new ProductDTO();

            dto.setId(bySearch.getId());
            dto.setName(bySearch.getName());
            dto.setDescription(bySearch.getDescription());
            dto.setPrice(bySearch.getPrice());
            dto.setQty(bySearch.getQty());
            dto.setUnit(bySearch.getUnit());
            dto.setType(bySearch.getType());
            dto.setDeliveryAvailable(bySearch.isDeliveryAvailable());
            dto.setCategory(new CategoryDTO(bySearch.getCategory().getId(), bySearch.getCategory().getName()));

            List<ProductImageDTO> imageDTOS = new ArrayList<>();

            bySearch.getProductImages().forEach(im -> {
                imageDTOS.add(new ProductImageDTO(im.getPath()));
            });
            dto.setSeller(new UserDTO(bySearch.getSeller().getId(), bySearch.getSeller().getEmail(), bySearch.getSeller().getMobile()));

            dto.setProductImages(imageDTOS);

            dto.setCartCount(bySearch.getCarts().size());

            List<WatchlistDTO> watchlistDTOS = new ArrayList<>();
            for (Watchlist watchlist : bySearch.getWatchlistList()) {
                watchlistDTOS.add(new WatchlistDTO(watchlist.getId()));
            }
            dto.setWatchlistList(watchlistDTOS);
            dto.setWatchlistCount(bySearch.getWatchlistList().size());

            productDTOList.add(dto);

        }

        return productDTOList;
    }

    @GetMapping("/search-word/{searchWord}")
    public List<ProductDTO> getAllProductsByProductName(@PathVariable String searchWord) {
        List<Product> allProductBySearch = defaultProductService.getAllProductName(searchWord);


        List<ProductDTO> productDTOList = new ArrayList<>();

        for (Product bySearch : allProductBySearch) {

            ProductDTO dto = new ProductDTO();

            dto.setId(bySearch.getId());
            dto.setName(bySearch.getName());
            dto.setDescription(bySearch.getDescription());
            dto.setPrice(bySearch.getPrice());
            dto.setQty(bySearch.getQty());
            dto.setUnit(bySearch.getUnit());
            dto.setType(bySearch.getType());
            dto.setDeliveryAvailable(bySearch.isDeliveryAvailable());
            dto.setCategory(new CategoryDTO(bySearch.getCategory().getId(), bySearch.getCategory().getName()));

            List<ProductImageDTO> imageDTOS = new ArrayList<>();

            bySearch.getProductImages().forEach(im -> {
                imageDTOS.add(new ProductImageDTO(im.getPath()));
            });
            dto.setSeller(new UserDTO(bySearch.getSeller().getId(), bySearch.getSeller().getEmail(), bySearch.getSeller().getMobile()));

            dto.setProductImages(imageDTOS);

            dto.setCartCount(bySearch.getCarts().size());

            List<WatchlistDTO> watchlistDTOS = new ArrayList<>();
            for (Watchlist watchlist : bySearch.getWatchlistList()) {
                watchlistDTOS.add(new WatchlistDTO(watchlist.getId()));
            }
            dto.setWatchlistList(watchlistDTOS);
            dto.setWatchlistCount(bySearch.getWatchlistList().size());

            productDTOList.add(dto);

        }

        return productDTOList;
    }

    @GetMapping("/category-name/{cateName}")
    public List<ProductDTO> getAllProductsByCategoryName(@PathVariable String cateName) {
        List<Product> allProductBySearch = defaultProductService.getAllProductByCategoryName(cateName);


        List<ProductDTO> productDTOList = new ArrayList<>();

        for (Product bySearch : allProductBySearch) {

            ProductDTO dto = new ProductDTO();

            dto.setId(bySearch.getId());
            dto.setName(bySearch.getName());
            dto.setDescription(bySearch.getDescription());
            dto.setPrice(bySearch.getPrice());
            dto.setQty(bySearch.getQty());
            dto.setUnit(bySearch.getUnit());
            dto.setType(bySearch.getType());
            dto.setDeliveryAvailable(bySearch.isDeliveryAvailable());
            dto.setCategory(new CategoryDTO(bySearch.getCategory().getId(), bySearch.getCategory().getName()));

            List<ProductImageDTO> imageDTOS = new ArrayList<>();

            bySearch.getProductImages().forEach(im -> {
                imageDTOS.add(new ProductImageDTO(im.getPath()));
            });
            dto.setSeller(new UserDTO(bySearch.getSeller().getId(), bySearch.getSeller().getEmail(), bySearch.getSeller().getMobile()));

            dto.setProductImages(imageDTOS);

            dto.setCartCount(bySearch.getCarts().size());

            List<WatchlistDTO> watchlistDTOS = new ArrayList<>();
            for (Watchlist watchlist : bySearch.getWatchlistList()) {
                watchlistDTOS.add(new WatchlistDTO(watchlist.getId()));
            }
            dto.setWatchlistList(watchlistDTOS);
            dto.setWatchlistCount(bySearch.getWatchlistList().size());

            productDTOList.add(dto);

        }

        return productDTOList;
    }


}
