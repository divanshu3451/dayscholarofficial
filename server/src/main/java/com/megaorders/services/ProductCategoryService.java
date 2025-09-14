package com.megaorders.services;

import com.megaorders.dtos.ProductCategoryResponseDTO;
import com.megaorders.models.ProductCategory;
import com.megaorders.repositories.ProductCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryrepository;

    public ProductCategoryService(ProductCategoryRepository repository) {
        this.productCategoryrepository = repository;
    }

    public List<ProductCategoryResponseDTO> getAllCategories() {
        try{
            return  productCategoryrepository.findAll()
                    .stream()
                    .map(cat -> new ProductCategoryResponseDTO(cat.getName(), cat.getDescription()))
                    .toList();
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            return null;
        }
    }

}
