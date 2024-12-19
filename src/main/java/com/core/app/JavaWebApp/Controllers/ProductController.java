package com.core.app.JavaWebApp.Controllers;

import com.core.app.JavaWebApp.Exceptions.ResponseObject;
import com.core.app.JavaWebApp.Services.IProductService;
import com.core.app.JavaWebApp.ViewModels.ProductViewModel;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Product Controller", description = "API for managing products")
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    IProductService productService;

    @GetMapping("all")
    ResponseObject findAll() {
        return productService.findAll();
    }

    @GetMapping("")
    ResponseObject findAllPagination(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "asc") String sort,
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "10") int pageSize
    ) {
        return productService.findAllPagination(keyword, sort, pageNo, pageSize);
    }

    @GetMapping("/{productId}")
    ResponseObject findById(@PathVariable Integer productId) {
        return productService.findById(productId);
    }

    @PostMapping()
    ResponseObject save(@ModelAttribute ProductViewModel model){
        return productService.save(model);
    }

    @PutMapping("/{productId}")
    ResponseObject update(@PathVariable Integer productId,@RequestBody ProductViewModel model){
        return productService.update(productId, model);
    }

    @DeleteMapping("/{productId}")
    ResponseObject delete(@PathVariable Integer productId){
        return productService.delete(productId);
    }
}
