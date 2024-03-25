package com.indikaudaya.bestfarmer.repositories;

import com.indikaudaya.bestfarmer.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
}
