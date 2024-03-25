package com.indikaudaya.bestfarmer.services;

import com.indikaudaya.bestfarmer.entities.Category;
import com.indikaudaya.bestfarmer.repositories.DefaultCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefailtCategoryService {
    @Autowired
    DefaultCategoryRepository defaultCategoryRepository;

    public List<Category> getAllCategory() {
        return defaultCategoryRepository.findAll();
    }
}
