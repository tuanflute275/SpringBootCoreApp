package com.core.app.JavaWebApp.Services;

import com.core.app.JavaWebApp.Exceptions.ResponseObject;
import com.core.app.JavaWebApp.ViewModels.CategoryViewModel;

public interface ICategoryService {
    ResponseObject findAll();
    ResponseObject findAllPagination(String keyword, String sort, int pageNo, int pageSize);
    ResponseObject findById(Integer categoryId);
    ResponseObject save(CategoryViewModel model);
    ResponseObject update(Integer categoryId, CategoryViewModel model);
    ResponseObject delete(Integer categoryId);
}
