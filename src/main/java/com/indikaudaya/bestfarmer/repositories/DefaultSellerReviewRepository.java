package com.indikaudaya.bestfarmer.repositories;


import com.indikaudaya.bestfarmer.entities.SellerReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DefaultSellerReviewRepository extends JpaRepository<SellerReview, Long> {
    @Query("SELECT s FROM SellerReview s WHERE s.seller.id=:id")
    List<SellerReview> getAllBySellerReview(long id);

}
