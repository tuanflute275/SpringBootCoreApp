package com.core.app.JavaWebApp.Services;

import com.core.app.JavaWebApp.Exceptions.ResponseObject;
import com.core.app.JavaWebApp.ViewModels.ProductViewModel;

public interface IProductService {
    ResponseObject findAll();
    ResponseObject findAllPagination(String keyword, String sort, int pageNo, int pageSize);
    ResponseObject findById(Integer productId);
    ResponseObject save(ProductViewModel model);
    ResponseObject update(Integer productId, ProductViewModel model);
    ResponseObject delete(Integer productId);
}
