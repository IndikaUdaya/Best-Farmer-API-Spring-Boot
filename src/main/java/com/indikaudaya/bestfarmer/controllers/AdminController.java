package com.indikaudaya.bestfarmer.controllers;

import com.indikaudaya.bestfarmer.dto.*;
import com.indikaudaya.bestfarmer.entities.Product;
import com.indikaudaya.bestfarmer.entities.SellerReview;
import com.indikaudaya.bestfarmer.entities.Watchlist;
import com.indikaudaya.bestfarmer.services.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/admin/")
public class AdminController {

    @Autowired
    AdminServices adminServices;

    @GetMapping("count")
    public Integer getAllProductCount() {
        return adminServices.getAllProductCount();
    }

    @GetMapping("count/{userType}")
    public Integer getCount(@PathVariable("userType") String userType) {
        return adminServices.getSellerCount(userType);
    }

    @GetMapping("product")
    public Double getProfitThisMonth() {
        return adminServices.getProfitThisMonth("2023-12-01", "2323-12-31");
    }

    @PutMapping("seller-id/{email}/{status}")
    public boolean updateSellerProductStatus(@PathVariable("email") String email, @PathVariable("status") boolean status) {
        int i = adminServices.updateProductStatus(email, status);
        return (i != 0) ? true : false;
    }

    @PutMapping("product/product-id/{id}/{status}")
    public boolean updateProductStatusById(@PathVariable("id") long id, @PathVariable("status") boolean status) {
        int i = adminServices.updateProductStatusById(id, status);
        return (i != 0) ? true : false;
    }

    @GetMapping("seller-review/review-all/{id}")
    public List<SellerReviewDTO> getSellerReviewAllById(@PathVariable("id") Long sellerId) {

        List<SellerReview> allReviewById = adminServices.getAllReviewById(sellerId);

        List<SellerReviewDTO> list = new ArrayList<>();

        if (allReviewById != null) {
            for (SellerReview s : allReviewById) {
                list.add(new SellerReviewDTO(
                        s.getId(),
                        s.getComment(),
                        s.getRating(),
                        new UserDTO(
                                s.getBuyer().getId(),
                                s.getBuyer().getEmail(),
                                s.getBuyer().getMobile(),
                                s.getBuyer().isStatus()
                        ),
                        new UserDTO(
                                s.getSeller().getId(),
                                s.getSeller().getEmail(),
                                s.getSeller().getMobile(),
                                s.getSeller().isStatus()
                        ),
                        s.getReviewDate(),
                        s.getProduct().getId()
                ));
            }
        }
        return list;
    }

    @GetMapping("product/all-product")
    public List<ProductDTO> getAllProduct() {
        List<Product> allProduct = adminServices.getAllProduct();
        return productToProductDTO(allProduct);
    }

    @GetMapping("product/search/{searchWord}/{status}")
    public List<ProductDTO> getAllProductsBySearch(@PathVariable String searchWord, @PathVariable boolean status) {
        List<Product> allProductBySearch = adminServices.getAllProductBySearch( searchWord,status);
           return productToProductDTO(allProductBySearch);
    }

    @GetMapping("product/search/search-word/{searchWord}")
    public List<ProductDTO> getAllProductsByProductName(@PathVariable String searchWord) {
        List<Product> allProductBySearch = adminServices.getAllProductName(searchWord);
       return productToProductDTO(allProductBySearch);
    }

    @GetMapping("product/search/status/{status}")
    public List<ProductDTO> getAllProductsByStatus(@PathVariable boolean status) {
        List<Product> allProductBySearch = adminServices.getAllProductByStatus(status);
        return productToProductDTO(allProductBySearch);
    }


   private List<ProductDTO> productToProductDTO(List<Product> product){

       List<ProductDTO> pd = new ArrayList<>();

       if (!product.isEmpty()) {
           for (Product p : product) {

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
               productDTO.setWatchlistCount(p.getWatchlistList().size());
               productDTO.setStatus(p.isStatus());

               ArrayList<SellerReviewDTO> sellerReviewDTOS = new ArrayList<>();

               for (SellerReview sellerReview : p.getSeller().getBuyerReview()) {
                   sellerReviewDTOS.add(new SellerReviewDTO(
                           sellerReview.getId(),
                           sellerReview.getComment(),
                           sellerReview.getRating(),
                           new UserDTO(
                                   sellerReview.getBuyer().getId(),
                                   sellerReview.getBuyer().getEmail(),
                                   sellerReview.getBuyer().getMobile(),
                                   sellerReview.getBuyer().getUserType(),
                                   sellerReview.getBuyer().isStatus()
                           ),
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

               seller.setBuyerReview(sellerReviewDTOS);

               productDTO.setSeller(seller);

               pd.add(productDTO);
           }
       }
       return pd;
    }


}
