package com.product.api.service;

import org.springframework.http.ResponseEntity;
import com.product.api.dto.in.DtoProductImageIn;
import com.product.common.dto.ApiResponse;


public interface SvcProductImage {
	
	public ResponseEntity<ApiResponse> createProductImage(DtoProductImageIn in);
	public ResponseEntity<ApiResponse> deleteProductImage(Integer id);
	
}
