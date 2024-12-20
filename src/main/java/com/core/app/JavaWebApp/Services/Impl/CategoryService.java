package com.core.app.JavaWebApp.Services.Impl;

import com.core.app.JavaWebApp.DTO.CategoryDTO;
import com.core.app.JavaWebApp.Entities.Category;
import com.core.app.JavaWebApp.Exceptions.ResponseObject;
import com.core.app.JavaWebApp.Repositories.CategoryRepository;
import com.core.app.JavaWebApp.Services.ICategoryService;
import com.core.app.JavaWebApp.Utils.Constant;
import com.core.app.JavaWebApp.Utils.DateUtil;
import com.core.app.JavaWebApp.ViewModels.CategoryViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Override
    public ResponseObject findAll() {
        try {
            logger.info("Starting to fetch all categories.");
            List<Category> categories = categoryRepository.findAll();
            List<CategoryDTO> categoryDTOs = new ArrayList<>();
            for (Category item : categories){
                CategoryDTO dto = new CategoryDTO();
                dto.setCategoryId(item.getCategoryId());
                dto.setStatus(item.isStatus());
                dto.setCreateBy(item.getCreateBy());
                dto.setCreateDate(DateUtil.parseDate(item.getCreateDate(), DateUtil.FORMAT_DD_MM_YYYY_HH_MM_SS));
                dto.setUpdateBy(item.getUpdateBy());
                dto.setUpdateDate(DateUtil.parseDate(item.updateDate, DateUtil.FORMAT_DD_MM_YYYY_HH_MM_SS));
                dto.setName(item.getName());
                categoryDTOs.add(dto);
            }
            logger.info("Returning {} category DTOs.", categoryDTOs.size());
            return new ResponseObject(Constant.SUCCESS_STATUS, Constant.SUCCESS_MESSAGE, categoryDTOs);
        }
        catch (Exception e){
            logger.error("Error occurred while fetching categories. Error message: {}", e.getMessage(), e);
            return new ResponseObject(
                    Constant.ERROR_STATUS,
                    Constant.ERROR_MESSAGE,
                    e.getMessage()
            );
        }
    }

    @Override
    public ResponseObject findAllPagination(String keyword, String sort, int pageNo, int pageSize) {
        try {
            logger.info("Start searching categories with keyword: {}, pageNo: {}, pageSize: {}, sort: {}",
                    keyword, pageNo, pageSize, sort);
            // Tạo đối tượng PageRequest để hỗ trợ phân trang và sắp xếp
            PageRequest pageRequest;
            if (sort == null || sort.isEmpty()) sort = "asc"; // Sử dụng mặc định "asc" nếu không có giá trị sắp xếp

            if (sort.equalsIgnoreCase("asc")) {
                pageRequest = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Order.asc("categoryId")));
            } else {
                pageRequest = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Order.desc("categoryId")));
            }
            // Tìm kiếm các danh mục với từ khóa tìm kiếm và phân trang
            Page<Category> categoryPage = categoryRepository.findByNameContainingIgnoreCase(keyword, pageRequest);

            // Kiểm tra nếu không có kết quả
            if (categoryPage.isEmpty()) {
                logger.warn("No categories found for keyword: {} with pageNo: {}, pageSize: {}",
                        keyword, pageNo, pageSize);
                return new ResponseObject(Constant.FAILED_STATUS, Constant.FAIL_MESSAGE, null);
            }
            logger.info("Found {} categories for keyword: {}, pageNo: {}, pageSize: {}",
                    categoryPage.getNumberOfElements(), keyword, pageNo, pageSize);
            // Chuyển đổi danh sách Category thành DTO (nếu cần)
            List<CategoryDTO> categoryDTOs = categoryPage.stream()
                    .map(item -> {
                        CategoryDTO dto = new CategoryDTO();
                        dto.setCategoryId(item.getCategoryId());
                        dto.setName(item.getName());
                        dto.setStatus(item.isStatus());
                        dto.setCreateBy(item.getCreateBy());
                        dto.setCreateDate(DateUtil.parseDate(item.getCreateDate(), DateUtil.FORMAT_DD_MM_YYYY_HH_MM_SS));
                        dto.setUpdateBy(item.getUpdateBy());
                        dto.setUpdateDate(DateUtil.parseDate(item.getUpdateDate(), DateUtil.FORMAT_DD_MM_YYYY_HH_MM_SS));
                        return dto;
                    })
                    .collect(Collectors.toList());
            // Trả về kết quả dưới dạng ResponseObject
            logger.info("Returning {} category DTOs", categoryDTOs.size());
            return new ResponseObject(Constant.SUCCESS_STATUS, Constant.SUCCESS_MESSAGE, categoryDTOs);
        }
        catch (Exception e){
            logger.error("Error occurred while searching categories. Error message: {}", e.getMessage(), e);
            return new ResponseObject(
                    Constant.ERROR_STATUS,
                    Constant.ERROR_MESSAGE,
                    e.getMessage()
            );
        }
    }

    @Override
    public ResponseObject findById(Integer categoryId) {
        try {
            // Kiểm tra nếu categoryId không hợp lệ (ví dụ: null hoặc không tồn tại trong cơ sở dữ liệu)
            if (categoryId == null || categoryId <= 0) {
                logger.warn("Invalid category ID provided: {}", categoryId);
                return new ResponseObject(
                        Constant.FAILED_STATUS,
                        "Invalid category ID provided.",
                        null
                );
            }
            logger.info("Searching for category with ID: {}", categoryId);
            // Kiểm tra nếu categoryId không hợp lệ (ví dụ: null hoặc không tồn tại trong cơ sở dữ liệu)
            Optional<Category> category = categoryRepository.findById(categoryId);
            // Kiểm tra nếu tìm thấy category
            if (category.isPresent()) {
                CategoryDTO dto = new CategoryDTO();
                dto.setCategoryId(category.get().getCategoryId());
                dto.setName(category.get().getName());
                dto.setStatus(category.get().isStatus());
                dto.setCreateBy(category.get().getCreateBy());
                dto.setUpdateBy(category.get().getUpdateBy());
                dto.setCreateDate(DateUtil.parseDate(category.get().getCreateDate(), DateUtil.FORMAT_DD_MM_YYYY_HH_MM_SS));
                dto.setUpdateDate(DateUtil.parseDate(category.get().getUpdateDate(), DateUtil.FORMAT_DD_MM_YYYY_HH_MM_SS));
                logger.info("Category found: {}", category.get().getName());
                return new ResponseObject(
                        Constant.SUCCESS_STATUS,
                        Constant.SUCCESS_MESSAGE,
                        dto // Trả về danh mục nếu tìm thấy
                );
            } else {
                logger.warn("Category not found for ID: {}", categoryId);
                return new ResponseObject(
                        Constant.FAILED_STATUS,
                        Constant.FAIL_MESSAGE,
                        Constant.NULL_DATA  // Không có danh mục thì trả về null
                );
            }
        }
        catch (Exception e){
            logger.error("Error occurred while fetching category with ID: {}", categoryId, e);
            return new ResponseObject(
                    Constant.ERROR_STATUS,
                    Constant.ERROR_MESSAGE,
                    e.getMessage()
            );
        }
    }

    @Override
    public ResponseObject save(CategoryViewModel model) {
        // Validate name
        if (model.getName() == null || model.getName().isEmpty()) {
            return new ResponseObject(Constant.FAILED_STATUS, "Category name is required", null);
        }
        if (model.getName().length() < 3 || model.getName().length() > 100) {
            return new ResponseObject(Constant.FAILED_STATUS, "Category name must be between 3 and 100 characters", null);
        }
        if (!model.getName().matches("^[a-zA-Z0-9 ]+$")) { // Không cho phép ký tự đặc biệt
            return new ResponseObject(Constant.FAILED_STATUS, "Category name can only contain letters, numbers, and spaces", null);
        }
        // validate status
        if (!(model.isStatus() == true || model.isStatus() == false)) {
            return new ResponseObject(Constant.FAILED_STATUS, "Status must be true or false", null);
        }
        try {
            logger.info("Starting category creation with name: {}", model.getName());
            Category category = new Category();
            category.setName(model.getName());
            category.setStatus(model.isStatus());
            category.setCreateBy(Constant.SYSTEM);
            category.setCreateDate(new Date());
            logger.info("Saving new category: {}", category);
            // Lưu category vào cơ sở dữ liệu
            category = categoryRepository.save(category);
            logger.info("Category created successfully with ID: {}", category.getCategoryId());
            return new ResponseObject(
                    Constant.SUCCESS_STATUS,
                    Constant.SUCCESS_MESSAGE,
                    category
            );
        }
        catch (Exception e){
            logger.error("Error occurred while creating category: {}", e.getMessage(), e);
            return new ResponseObject(
                    Constant.ERROR_STATUS,
                    Constant.ERROR_MESSAGE,
                    e.getMessage()
            );
        }
    }

    @Override
    public ResponseObject update(Integer categoryId, CategoryViewModel model) {
        // Validate name
        if (model.getName() == null || model.getName().isEmpty()) {
            return new ResponseObject(Constant.FAILED_STATUS, "Category name is required", null);
        }
        if (model.getName().length() < 3 || model.getName().length() > 100) {
            return new ResponseObject(Constant.FAILED_STATUS, "Category name must be between 3 and 100 characters", null);
        }
        if (!model.getName().matches("^[a-zA-Z0-9 ]+$")) { // Không cho phép ký tự đặc biệt
            return new ResponseObject(Constant.FAILED_STATUS, "Category name can only contain letters, numbers, and spaces", null);
        }
        // validate status
        if (!(model.isStatus() == true || model.isStatus() == false)) {
            return new ResponseObject(Constant.FAILED_STATUS, "Status must be true or false", null);
        }
        try {
            logger.info("Starting category update for ID: {}", categoryId);
            // Tìm kiếm Category từ categoryId
            Optional<Category> existingCategoryOpt = categoryRepository.findById(categoryId);

            // Nếu không tìm thấy category, trả về lỗi
            if (!existingCategoryOpt.isPresent()) {
                logger.warn("Category with ID: {} not found", categoryId);
                return new ResponseObject(
                        Constant.ERROR_STATUS,
                        "Category not found",
                        null
                );
            }

            // Lấy Category đã tồn tại
            Category existingCategory = existingCategoryOpt.get();
            logger.info("Found category: {}", existingCategory);
            // Cập nhật các thuộc tính từ CategoryViewModel
            existingCategory.setName(model.getName());
            existingCategory.setStatus(model.isStatus());
            existingCategory.setUpdateBy(Constant.SYSTEM);
            existingCategory.setUpdateDate(new Date());
            logger.info("Updating category with new data: {}", existingCategory);

            // Lưu category đã cập nhật vào cơ sở dữ liệu
            categoryRepository.save(existingCategory);
            logger.info("Category updated successfully for ID: {}", categoryId);

            // Trả về ResponseObject với thông báo thành công
            return new ResponseObject(
                    Constant.SUCCESS_STATUS,
                    Constant.SUCCESS_MESSAGE,
                    existingCategory
            );
        }
        catch (Exception e){
            logger.error("Error occurred while updating category with ID: {}", categoryId, e);
            return new ResponseObject(
                    Constant.ERROR_STATUS,
                    Constant.ERROR_MESSAGE,
                    e.getMessage()
            );
        }
    }

    @Override
    public ResponseObject delete(Integer categoryId) {
        try {
            logger.info("Initiating delete operation for category ID: {}", categoryId);
            // Kiểm tra xem danh mục có tồn tại trong cơ sở dữ liệu không
            Optional<Category> category = categoryRepository.findById(categoryId);
            if (category.isPresent()) {
                // Nếu tìm thấy danh mục, tiến hành xóa
                categoryRepository.delete(category.get());
                logger.info("Category with ID: {} deleted successfully", categoryId);
                return new ResponseObject(
                        Constant.SUCCESS_STATUS,
                        Constant.SUCCESS_MESSAGE,
                        Constant.NULL_DATA
                );
            } else {
                logger.warn("Category with ID: {} not found", categoryId);
                // Nếu không tìm thấy danh mục, trả về thông báo lỗi
                return new ResponseObject(
                        Constant.FAILED_STATUS,
                        Constant.FAIL_MESSAGE,
                        Constant.NULL_DATA
                );
            }
        }
        catch (Exception e){
            logger.error("Error occurred while deleting category with ID: {}", categoryId, e);
            return new ResponseObject(
                    Constant.ERROR_STATUS,
                    Constant.ERROR_MESSAGE,
                    e.getMessage()
            );
        }
    }
}
