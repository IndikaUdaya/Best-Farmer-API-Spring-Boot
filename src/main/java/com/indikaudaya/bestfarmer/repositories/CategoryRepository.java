package com.indikaudaya.bestfarmer.repositories;

import com.indikaudaya.bestfarmer.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.name=:name")
    Category searchByName(String name);

}
