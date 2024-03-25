package com.indikaudaya.bestfarmer.services;

import com.indikaudaya.bestfarmer.entities.Invoice;
import com.indikaudaya.bestfarmer.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepository;

    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }
}
