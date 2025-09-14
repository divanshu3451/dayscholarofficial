package com.megaorders.controllers;

import com.megaorders.dtos.ProductCategoryResponseDTO;
import com.megaorders.models.ProductCategory;
import com.megaorders.services.ProductCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/category")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService service) {
        this.productCategoryService = service;
    }

    @GetMapping("/public/all")
    public List<ProductCategoryResponseDTO> getAllCategories() {
        List<ProductCategoryResponseDTO> categories = productCategoryService.getAllCategories();
        if(categories !=null && categories.size()>0){
            return categories;
        }
        return Collections.emptyList();
    }


}
