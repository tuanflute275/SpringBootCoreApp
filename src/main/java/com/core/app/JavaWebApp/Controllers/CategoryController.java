package com.core.app.JavaWebApp.Controllers;

import com.core.app.JavaWebApp.Exceptions.ResponseObject;
import com.core.app.JavaWebApp.Services.ICategoryService;
import com.core.app.JavaWebApp.ViewModels.CategoryViewModel;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Category Controller", description = "API for managing categories")
@RestController
@RequestMapping("${spring.api.prefix}/category")
public class CategoryController {

    @Autowired
    ICategoryService categoryService;

    @GetMapping("all")
    ResponseObject findAll() {
        return categoryService.findAll();
    }

    @GetMapping("")
    ResponseObject findAllPagination(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "asc") String sort,
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "10") int pageSize
    ) {
        return categoryService.findAllPagination(keyword, sort, pageNo, pageSize);
    }

    @GetMapping("/{categoryId}")
    ResponseObject findById(@PathVariable Integer categoryId) {
        return categoryService.findById(categoryId);
    }

    @PostMapping()
    ResponseObject save(@RequestBody CategoryViewModel model){
       return categoryService.save(model);
    }

    @PutMapping("/{categoryId}")
    ResponseObject update(@PathVariable Integer categoryId,@RequestBody CategoryViewModel model){
        return categoryService.update(categoryId, model);
    }

    @DeleteMapping("/{categoryId}")
    ResponseObject delete(@PathVariable Integer categoryId){
        return categoryService.delete(categoryId);
    }
}
