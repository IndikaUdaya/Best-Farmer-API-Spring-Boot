package com.indikaudaya.bestfarmer.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@Entity
@ToString
@Table(name = "seller_review")
public class SellerReview implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    private Double rating;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "review_date", updatable = false)
    private String reviewDate;

    @PrePersist
    public void prePersist() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.reviewDate = dateFormat.format(new Date());
    }

    public SellerReview() {
    }

}
