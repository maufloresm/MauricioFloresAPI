package com.product;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CtrlProduct {

    @GetMapping
    public List<Category> getCategories() {
        return List.of(
            new Category(1, "Lentes", "Lts", 1),
            new Category(2, "Relojes", "Rljs", 1)
        );
    }
}