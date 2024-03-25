package com.indikaudaya.bestfarmer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor@AllArgsConstructor
public class InvoiceDTO implements Serializable {

    private Long id;

    private String invoiceNumber;

    private OrderDTO order;

    public InvoiceDTO(Long id, String invoiceNumber) {
        this.id = id;
        this.invoiceNumber = invoiceNumber;
    }
}
