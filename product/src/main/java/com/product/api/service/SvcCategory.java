package com.product.api.service;
import java.util.List;
import com.product.api.entity.Category;
import org.springframework.http.ResponseEntity;

public interface SvcCategory {
    public ResponseEntity<List<Category>> getCategories();
}