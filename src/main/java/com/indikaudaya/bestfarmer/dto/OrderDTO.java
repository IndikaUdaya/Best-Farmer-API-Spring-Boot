package com.indikaudaya.bestfarmer.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDTO implements Serializable {

    private long orderId;

    private boolean deliveryStatus;

    private UserDTO buyer;

    private InvoiceDTO invoice;

    private List<ProductOrderItemDTO> orderItems;
}
