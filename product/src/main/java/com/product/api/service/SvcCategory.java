package com.product.api.service;

import com.product.api.commons.dto.ApiResponse;
import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SvcCategory {

    ResponseEntity<List<Category>> getCategories();
    
    ResponseEntity<List<Category>> getActiveCategories();
    
    ResponseEntity<ApiResponse> createCategory(DtoCategoryIn in);
    
    ResponseEntity<ApiResponse> updateCategory(DtoCategoryIn in, Integer id);
    
    ResponseEntity<ApiResponse> enableCategory(Integer id);
    
    ResponseEntity<ApiResponse> disableCategory(Integer id);
}