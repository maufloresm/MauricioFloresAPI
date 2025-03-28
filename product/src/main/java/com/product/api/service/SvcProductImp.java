package com.product.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.product.api.dto.in.DtoProductIn;
import com.product.api.dto.out.DtoProductListOut;
import com.product.api.dto.out.DtoProductOut;
import com.product.api.entity.Product;
import com.product.api.entity.ProductImage;
import com.product.api.repository.RepoProduct;
import com.product.api.repository.RepoProductImage;
import com.product.common.dto.ApiResponse;
import com.product.common.mapper.MapperProduct;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

@Service
public class SvcProductImp implements SvcProduct {
    
    @Autowired
    RepoProduct repo;
    
    @Autowired
    RepoProductImage repoProductImage;
    
    @Autowired
    MapperProduct mapper;
    
    @Value("${app.upload.dir}")
    private String uploadDir;

    // Obtener la lista de productos
    @Override
    public ResponseEntity<List<DtoProductListOut>> getProducts() {
        try {
            List<Product> products = repo.findAll();
            return new ResponseEntity<>(mapper.fromProductList(products), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    // Obtener un producto por ID
    @Override
    public ResponseEntity<DtoProductOut> getProduct(Integer id) {
        try {
            validateProductId(id);
            DtoProductOut product = repo.getProduct(id);
            
            if (product == null) {
                throw new ApiException(HttpStatus.NOT_FOUND, "ID no existente");
            }
            
            List<String> imagenes = readProductImages(id);
            
            if (!imagenes.isEmpty()) {
                product.setImage(imagenes.get(0));
            }
            product.setImages(imagenes);
            
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }
    
    // Leer imágenes de un producto
    private List<String> readProductImages(Integer productId) {
        try {
            List<ProductImage> productImages = repoProductImage.findByProductId(productId);
            List<String> imageUrls = new ArrayList<>();
            
            if (productImages == null || productImages.isEmpty()) {
                return imageUrls;
            }
            
            for (ProductImage image : productImages) {
                String imageUrl = image.getImage();
                
                if (imageUrl.startsWith("/")) {
                    imageUrl = imageUrl.substring(1);
                }
                
                Path imagePath = Paths.get(uploadDir, imageUrl);
                
                if (Files.exists(imagePath)) {
                    byte[] imageBytes = Files.readAllBytes(imagePath);
                    imageUrls.add(Base64.getEncoder().encodeToString(imageBytes));
                } else {
                    imageUrls.add("");
                }
            }
            
            return imageUrls;
            
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        } catch (IOException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al leer el archivo");
        }
    }

    // Crear un producto
    @Override
    public ResponseEntity<ApiResponse> createProduct(DtoProductIn in) {
        try {
            Product product = mapper.fromDto(in);
            repo.save(product);
            return new ResponseEntity<>(new ApiResponse("Producto registrado"), HttpStatus.CREATED);
        } catch (DataAccessException e) {
            manejarExcepcionProducto(e);
            throw new DBAccessException(e);
        }
    }

    // Actualizar un producto
    @Override
    public ResponseEntity<ApiResponse> updateProduct(Integer id, DtoProductIn in) {
        try {
            validateProductId(id);
            Product product = mapper.fromDto(id, in);
            repo.save(product);
            return new ResponseEntity<>(new ApiResponse("Producto actualizado"), HttpStatus.OK);
        } catch (DataAccessException e) {
            manejarExcepcionProducto(e);
            throw new DBAccessException(e);
        }
    }

    // Habilitar un producto
    @Override
    public ResponseEntity<ApiResponse> enableProduct(Integer id) {
        try {
            validateProductId(id);
            Product product = repo.findById(id).get();
            product.setStatus(1);
            repo.save(product);
            return new ResponseEntity<>(new ApiResponse("Producto activado"), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    // Deshabilitar un producto
    @Override
    public ResponseEntity<ApiResponse> disableProduct(Integer id) {
        try {
            validateProductId(id);
            Product product = repo.findById(id).get();
            product.setStatus(0);
            repo.save(product);
            return new ResponseEntity<>(new ApiResponse("Producto desactivado"), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }
    
    // Validar si el ID de un producto existe
    private void validateProductId(Integer id) {
        try {
            if (repo.findById(id).isEmpty()) {
                throw new ApiException(HttpStatus.NOT_FOUND, "ID no existente");
            }
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        }
    }
    
    // Manejar excepciones relacionadas con productos
    private void manejarExcepcionProducto(DataAccessException e) {
        if (e.getLocalizedMessage().contains("ux_product_gtin")) {
            throw new ApiException(HttpStatus.CONFLICT, "El gtin del producto ya está registrado");
        }
        if (e.getLocalizedMessage().contains("ux_product_product")) {
            throw new ApiException(HttpStatus.CONFLICT, "El nombre del producto ya está registrado");
        }
        if (e.getLocalizedMessage().contains("fk_product_category")) {
            throw new ApiException(HttpStatus.NOT_FOUND, "El id de categoría no existe");
        }
    }
}
