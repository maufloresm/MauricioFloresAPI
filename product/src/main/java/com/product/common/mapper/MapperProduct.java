package com.product.common.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.product.api.dto.in.DtoProductIn;
import com.product.api.dto.out.DtoProductListOut;
import com.product.api.entity.Product;

@Service
public class MapperProduct {
    
    // Convierte una lista de productos en una lista de DTOs para enviarlos como respuesta
    public List<DtoProductListOut> fromProductList(List<Product> products) {
        List<DtoProductListOut> list = new ArrayList<>();
        for (Product product : products) {
            list.add(new DtoProductListOut(
                product.getProduct_id(),  // ID del producto
                product.getGtin(),        // Código GTIN
                product.getProduct(),     // Nombre del producto
                product.getPrice(),       // Precio
                product.getStatus()       // Estado (activo/inactivo)
            ));
        }
        return list;
    }
    
    // Convierte un DTO de entrada en un objeto Product para guardarlo en la BD
    public Product fromDto(DtoProductIn dto) {
        Product product = new Product();
        product.setGtin(dto.getGtin());               // Código GTIN
        product.setProduct(dto.getProduct());         // Nombre del producto
        product.setDescription(dto.getDescription()); // Descripción
        product.setPrice(dto.getPrice());             // Precio
        product.setStock(dto.getStock());             // Stock disponible
        product.setCategory_id(dto.getCategory_id()); // ID de la categoría
        product.setStatus(1); // Se registra como activo por defecto
        return product;
    }
    
    // Mismo mapeo que el anterior, pero incluyendo un ID (para actualizaciones)
    public Product fromDto(Integer id, DtoProductIn dto) {
        Product product = fromDto(dto);
        product.setProduct_id(id); // Asigna el ID existente
        return product;
    }
}
