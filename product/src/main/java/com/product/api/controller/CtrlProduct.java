package com.product.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.product.api.service.SvcCategoryImp;
import com.product.api.entity.Category;

@RestController
@RequestMapping("/category")
public class CtrlProduct {

	@Autowired
	SvcCategoryImp svc;
	
    @GetMapping
    public ResponseEntity<List<Category>> darCategorias() {
        return svc.getCategories();
    }
}
