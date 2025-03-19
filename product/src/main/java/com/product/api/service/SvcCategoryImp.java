package com.product.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.commons.dto.ApiResponse;
import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

@Service
public class SvcCategoryImp implements SvcCategory {

    @Autowired
    RepoCategory repo;

    @Override
    public ResponseEntity<List<Category>> getCategories() {
        try {
            return new ResponseEntity<>(repo.getCategories(), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<List<Category>> getActiveCategories() {
        try {
            return new ResponseEntity<>(repo.getActiveCategories(), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> createCategory(DtoCategoryIn in) {
        try {
            repo.createCategory(in.getCategory(), in.getTag());
            return new ResponseEntity<>(new ApiResponse("La categoría ha sido registrada"), HttpStatus.CREATED);
        } catch (DataAccessException e) {
            if (e.getLocalizedMessage().contains("ux_category")) {
                throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está registrado");
            }
            if (e.getLocalizedMessage().contains("ux_tag")) {
                throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está registrado");
            }
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> updateCategory(DtoCategoryIn in, Integer id) {
        try {
            validateCategoryId(id);
            repo.updateCategory(id, in.getCategory(), in.getTag());
            return new ResponseEntity<>(new ApiResponse("La categoría ha sido actualizada"), HttpStatus.OK);
        } catch (DataAccessException e) {
            if (e.getLocalizedMessage().contains("ux_category")) {
                throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está registrado");
            }
            if (e.getLocalizedMessage().contains("ux_tag")) {
                throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está registrado");
            }
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> enableCategory(Integer id) {
        try {
            validateCategoryId(id);
            repo.enableCategory(id);
            return new ResponseEntity<>(new ApiResponse("La categoría ha sido activada"), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    @Override
    public ResponseEntity<ApiResponse> disableCategory(Integer id) {
        try {
            validateCategoryId(id);
            repo.disableCategory(id);
            return new ResponseEntity<>(new ApiResponse("La categoría ha sido desactivada"), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    private void validateCategoryId(Integer id) {
        try {
            if (repo.getCategory(id) == null) {
                throw new ApiException(HttpStatus.NOT_FOUND, "El ID de la categoría no existe");
            }
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }
}