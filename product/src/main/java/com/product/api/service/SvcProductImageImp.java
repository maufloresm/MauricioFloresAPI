package com.product.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.entity.ProductImage;
import com.product.api.repository.RepoProductImage;
import com.product.common.dto.ApiResponse;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

@Service
public class SvcProductImageImp implements SvcProductImage {
    
    @Autowired
    RepoProductImage repo;
    
    @Value("${app.upload.dir}")
    private String uploadDir;

    /**
     * Sube una imagen de producto, la guarda en el sistema de archivos
     * y almacena su ruta en la base de datos.
     */
    @Override
    public ResponseEntity<ApiResponse> createProductImage(DtoProductImageIn in) {
        try {
            // Si la imagen viene con prefijo Base64, lo eliminamos
            if (in.getImage().startsWith("data:image")) {
                int commaIndex = in.getImage().indexOf(",");
                if (commaIndex != -1) {
                    in.setImage(in.getImage().substring(commaIndex + 1));
                }
            }

            // Convertimos la imagen de Base64 a bytes
            byte[] imageBytes = Base64.getDecoder().decode(in.getImage());

            // Generamos un nombre único para la imagen
            String fileName = UUID.randomUUID().toString() + ".png";

            // Construimos la ruta donde se guardará la imagen
            Path imagePath = Paths.get(uploadDir, "img", "product", fileName);
            
            // Aseguramos que el directorio existe
            Files.createDirectories(imagePath.getParent());

            // Guardamos la imagen en el sistema de archivos
            Files.write(imagePath, imageBytes);
            
            // Creamos la entidad de imagen y guardamos la ruta en la base de datos
            ProductImage productImage = new ProductImage();
            productImage.setProduct_id(in.getProduct_id());
            productImage.setImage("img/product/" + fileName);
            productImage.setStatus(1);

            repo.save(productImage);
            
            return new ResponseEntity<>(new ApiResponse("La imagen del producto ha sido actualizada"), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        } catch (IOException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el archivo");
        }
    }
    
    /**
     * Elimina una imagen de producto del sistema de archivos y su registro en la base de datos.
     */
    @Override
    public ResponseEntity<ApiResponse> deleteProductImage(Integer id) {
        try {
            // Buscamos la imagen en la base de datos
            ProductImage productImage = repo.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "El id de la imagen no existe"));
            
            // Obtenemos la ruta de la imagen
            String imageUrl = productImage.getImage();
            if (imageUrl.startsWith("/")) {
                imageUrl = imageUrl.substring(1);
            }
            
            // Construimos la ruta absoluta
            Path imagePath = Paths.get(uploadDir, imageUrl);
            
            // Si el archivo existe, lo eliminamos
            if (Files.exists(imagePath)) {
                Files.delete(imagePath);
            }
            
            // Eliminamos el registro de la base de datos
            repo.delete(productImage);
            
            return new ResponseEntity<>(new ApiResponse("La imagen ha sido eliminada"), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new DBAccessException(e);
        } catch (IOException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar el archivo");
        }
    }
}
