package com.indikaudaya.bestfarmer.controllers;

import com.indikaudaya.bestfarmer.dto.SellerReviewDTO;
import com.indikaudaya.bestfarmer.dto.UserDTO;
import com.indikaudaya.bestfarmer.entities.Delivery;
import com.indikaudaya.bestfarmer.entities.Product;
import com.indikaudaya.bestfarmer.entities.SellerReview;
import com.indikaudaya.bestfarmer.entities.User;
import com.indikaudaya.bestfarmer.services.DeliveryService;
import com.indikaudaya.bestfarmer.services.SellerReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/seller-review")
public class SellerReviewController {
    @Autowired
    private SellerReviewService sellerReviewService;

    @GetMapping
    public List<SellerReview> getAllSellerReviews() {
        return sellerReviewService.getAllSellerReview();
    }

    @GetMapping("/review-all/{sellerId}")
    public List<SellerReviewDTO> getSellerReviewAllById(@PathVariable Long sellerId) {

        List<SellerReview> allReviewById = sellerReviewService.getAllReviewById(sellerId);

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

    @GetMapping("/review-all/{buyerId}/{productId}")
    public SellerReview getSellerReviewByUIdAndPid(@PathVariable Long buyerId,@PathVariable long productId) {
       return sellerReviewService.getSellerReviewByUIdAndPid(buyerId, productId);
    }


    @PostMapping
    public SellerReview saveSellerReview(@RequestBody SellerReview sellerReview) {
        return sellerReviewService.saveSellerReview(sellerReview);
    }

    @DeleteMapping("/{id}")
    public void deleteSellerReview(@PathVariable Long id) {
        sellerReviewService.deleteSellerReview(id);
    }
}
