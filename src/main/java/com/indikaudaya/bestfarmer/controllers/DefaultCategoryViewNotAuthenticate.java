package com.indikaudaya.bestfarmer.controllers;

import com.indikaudaya.bestfarmer.entities.Category;
import com.indikaudaya.bestfarmer.services.DefailtCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/default/category")
public class DefaultCategoryViewNotAuthenticate {
    @Autowired
    DefailtCategoryService defailtCategoryService;

    @GetMapping
    public List<Category> getAllCategory() {
        return defailtCategoryService.getAllCategory();
    }
}
