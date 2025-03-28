package com.product.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.dto.in.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.common.dto.ApiResponse;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

// Servicio que se encarga de manejar las categorías. Sí, alguien tiene que hacerlo.
@Service
public class SvcCategoryImp implements SvcCategory {

    @Autowired
    RepoCategory repo;

    // Devuelve todas las categorías disponibles, porque compartir es vivir.
    @Override
    public ResponseEntity<List<Category>> getCategories() {
        try {
            return new ResponseEntity<>(repo.getCategories(), HttpStatus.OK);
        } catch (DataAccessException e) {
            System.out.println(e.getLocalizedMessage());
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar la base de datos. Sí, otra vez...");
        }
    }

    // Devuelve solo las categorías activas, porque las inactivas están tomando un descanso.
    @Override
    public ResponseEntity<List<Category>> getActiveCategories() {
        try {
            return new ResponseEntity<>(repo.getActiveCategories(), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    // Devuelve una categoría específica. Solo si existe, claro.
    @Override
    public ResponseEntity<Category> getCategory(int id) {
        try {
            validateCategoryId(id);
            return new ResponseEntity<>(repo.getCategory(id), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    // Crea una nueva categoría. Porque el mundo siempre necesita más categorías.
    @Override
    public ResponseEntity<ApiResponse> createCategory(DtoCategoryIn in) {
        try {
            repo.createCategory(in.getCategory(), in.getTag());
            return new ResponseEntity<>(new ApiResponse("La categoría ha sido registrada. ¡Un nuevo miembro en la familia!"), HttpStatus.CREATED);
        } catch (DataAccessException e) {
            if (e.getLocalizedMessage().contains("ux_category"))
                throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está registrado. Se original.");
            if (e.getLocalizedMessage().contains("ux_category"))
                throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está registrado. ¿Un poco de creatividad, quizás?");
            throw new DBAccessException(e);
        }
    }

    // Actualiza una categoría. Porque cambiar de opinión es válido.
    @Override
    public ResponseEntity<ApiResponse> updateCategory(int id, DtoCategoryIn in) {
        try {
            validateCategoryId(id);
            repo.updateCategory(id, in.getCategory(), in.getTag());
            return new ResponseEntity<>(new ApiResponse("La categoría ha sido actualizada. ¡Modernización en camino!"), HttpStatus.OK);
        } catch (DataAccessException e) {
            if (e.getLocalizedMessage().contains("ux_categoría"))
                throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está registrado. Sí, otra vez.");
            if (e.getLocalizedMessage().contains("ux_tag"))
                throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está registrado. Quizás prueba con otro.");
            throw new DBAccessException(e);
        }
    }

    // Activa una categoría, porque todo el mundo merece una segunda oportunidad.
    @Override
    public ResponseEntity<ApiResponse> enableCategory(int id) {
        try {
            validateCategoryId(id);
            repo.enableCategory(id);
            return new ResponseEntity<>(new ApiResponse("La categoría ha sido activada. ¡Bienvenido de nuevo!"), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    // Desactiva una categoría, porque a veces es mejor tomarse un descanso.
    @Override
    public ResponseEntity<ApiResponse> disableCategory(int id) {
        try {
            validateCategoryId(id);
            repo.disableCategory(id);
            return new ResponseEntity<>(new ApiResponse("La categoría ha sido desactivada. Hasta pronto."), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    // Verifica si el id de la categoría existe. Porque preguntar antes de actuar es importante.
    private void validateCategoryId(int id) {
        try {
            if (repo.getCategory(id) == null) {
                throw new ApiException(HttpStatus.NOT_FOUND, "El id de la categoría no existe. Intenta con otro.");
            }
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }
}
