package com.indikaudaya.bestfarmer.services;

import com.indikaudaya.bestfarmer.entities.SellerReview;
import com.indikaudaya.bestfarmer.repositories.SellerReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerReviewService {
    @Autowired
    private SellerReviewRepository sellerReviewRepository;

    public List<SellerReview> getAllSellerReview() {
        return sellerReviewRepository.findAll();
    }

    public SellerReview getSellerReviewById(Long id) {
        return sellerReviewRepository.findById(id).orElse(null);
    }

    public SellerReview getSellerReviewByUIdAndPid(Long bid,long pid) {
        return sellerReviewRepository.findByBuyerIdProductId(bid,pid);
    }

    public List<SellerReview> getAllReviewById(long id){
        return sellerReviewRepository.getAllBySellerReview(id);
    }

    public SellerReview saveSellerReview(SellerReview sellerReview) {
        return sellerReviewRepository.save(sellerReview);
    }

    public void deleteSellerReview(Long id) {
        sellerReviewRepository.deleteById(id);
    }
}
