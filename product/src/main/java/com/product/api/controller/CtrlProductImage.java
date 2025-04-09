package com.product.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.service.SvcProductImage;
import com.product.common.dto.ApiResponse;
import com.product.exception.ApiException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product-image")
@Tag(name = "product image", description = "Carga de imágenes de productos")
public class CtrlProductImage {


	@Autowired
	SvcProductImage svc;

	@PostMapping
    public ResponseEntity<ApiResponse> createProductImage(@Valid @RequestBody DtoProductImageIn in, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
        	throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());
        return svc.createProductImage(in);
    }
	
    @DeleteMapping("/{id}")
     @Operation(summary = "Borra una imagen", description = "Borra una imagen que esté ligada	 a un producto del sistema y a nuestra base de datos.")
    public ResponseEntity<ApiResponse> deleteProductImage(@PathVariable Integer id) {
        return svc.deleteProductImage(id);
    }
}
