package com.indikaudaya.bestfarmer.dto;

import com.indikaudaya.bestfarmer.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
public class CategoryDTO implements Serializable {
    private long id;
    private String name;

    public CategoryDTO(Category category){
        this.id=category.getId();
        this.name=category.getName();
    }
}
