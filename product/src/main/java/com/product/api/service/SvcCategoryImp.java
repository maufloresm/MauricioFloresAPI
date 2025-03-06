package com.product.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.exception.ApiException;

@Service
public class SvcCategoryImp implements SvcCategory {

    @Autowired
    RepoCategory repo;


    @Override
    public ResponseEntity<List<Category>> getCategories() {
    	try {
    		return new ResponseEntity<>(repo.getCategories(), HttpStatus.OK);
    	} catch (DataAccessException e) {
    		System.out.println(e.getLocalizedMessage());
    		throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error al consultar la base de datos");
    	}
    }


    
}
