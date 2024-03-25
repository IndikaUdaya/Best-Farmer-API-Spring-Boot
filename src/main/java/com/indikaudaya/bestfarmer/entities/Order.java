package com.indikaudaya.bestfarmer.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date",updatable = false)
    private String orderDate;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        orderDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Column(name = "delivery_status")
    private boolean deliveryStatus;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @OneToOne(mappedBy = "order",cascade = CascadeType.PERSIST)
    private Invoice invoice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<ProductOrderItem> orderItems;

}
