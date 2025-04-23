package com.product.api.service;

import java.util.List;

import com.product.api.dto.in.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.common.dto.ApiResponse;

import org.springframework.http.ResponseEntity;


public interface SvcCategory {
	
    public ResponseEntity<List<Category>> getCategories();
    public ResponseEntity<List<Category>> getActiveCategories();
	public ResponseEntity<Category> getCategory(int id);
	public ResponseEntity<ApiResponse> createCategory(DtoCategoryIn in);
	public ResponseEntity<ApiResponse> updateCategory(int id, DtoCategoryIn in);
	public ResponseEntity<ApiResponse> enableCategory(int id);
	public ResponseEntity<ApiResponse> disableCategory(int id);
    
}