package com.product.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.service.SvcCategory;
import com.product.common.dto.ApiResponse;
import com.product.api.dto.in.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.exception.ApiException;

import jakarta.validation.Valid;

// Controlador para manejar las categorías. Básicamente, aquí tenemos las rutas 
// que nos permiten hacer cosas con las categorías: obtenerlas, crearlas, actualizarlas y más.

@RestController
@RequestMapping("/category")
public class CtrlCategory {

	@Autowired
	SvcCategory svc; // Servicio que se encarga de la lógica de negocio para las categorías.
	
    // Devuelve todas las categorías que existen.
    @GetMapping
    public ResponseEntity<List<Category>> darCategorias() {
        return svc.getCategories();
    }
    
    // Devuelve solo las categorías activas (o sea, las que tienen status = 1).
    @GetMapping("/active")
	public ResponseEntity<List<Category>> getActiveCategories() {
		return svc.getActiveCategories();
	}
	
	// Busca una categoría por su ID.
	@GetMapping("/{id}")
	public ResponseEntity<Category> getRegion(@PathVariable int id) {
		return svc.getCategory(id);
	}
	
	// Crea una nueva categoría. 
	// Si hay errores en los datos que se envían, lanza una excepción con el mensaje del error.
	@PostMapping
	public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody DtoCategoryIn in, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());

		return svc.createCategory(in);
	}
	
	// Actualiza una categoría existente. 
	// Si los datos no son válidos, también lanza una excepción.
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> updateCategory(@PathVariable int id, @Valid @RequestBody DtoCategoryIn in,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			throw new ApiException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());

		return svc.updateCategory(id, in);
	}
	
	// Activa una categoría deshabilitada.
	@PatchMapping("/{id}/enable")
	public ResponseEntity<ApiResponse> enableCategory(@PathVariable int id) {
		return svc.enableCategory(id);
	}

	// Deshabilita una categoría.
	@PatchMapping("/{id}/disable")
	public ResponseEntity<ApiResponse> disableCategory(@PathVariable int id) {
		return svc.disableCategory(id);
	}
}
