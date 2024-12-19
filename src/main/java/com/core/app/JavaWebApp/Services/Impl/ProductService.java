package com.core.app.JavaWebApp.Services.Impl;

import com.core.app.JavaWebApp.DTO.ProductDTO;
import com.core.app.JavaWebApp.Entities.Product;
import com.core.app.JavaWebApp.Exceptions.ResponseObject;
import com.core.app.JavaWebApp.Repositories.ProductRepository;
import com.core.app.JavaWebApp.Services.IProductService;
import com.core.app.JavaWebApp.Utils.Constant;
import com.core.app.JavaWebApp.Utils.DateUtil;
import com.core.app.JavaWebApp.ViewModels.ProductViewModel;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProductService implements IProductService {

    @Autowired
    ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Override
    public ResponseObject findAll() {
        try {
            logger.info("Attempting to fetch all products.");
            List<Product> data = productRepository.findAll();
            List<ProductDTO> listDTO = new ArrayList<>();
            for (Product item : data){
                ProductDTO dto = new ProductDTO();
                dto.setProductId(item.getProductId());
                dto.setName(item.getName());
                dto.setImage(item.getImage());
                dto.setPrice(item.getPrice());
                dto.setStatus(item.isStatus());
                dto.setDescription(item.getDescription());
                dto.setCategoryId(item.getCategory().getCategoryId());
                dto.setCategoryName(item.getCategory().getName());
                dto.setCreateBy(item.getCreateBy());
                dto.setCreateDate(DateUtil.parseDate(item.getCreateDate(), DateUtil.FORMAT_DD_MM_YYYY_HH_MM_SS));
                dto.setUpdateBy(item.getUpdateBy());
                dto.setUpdateDate(DateUtil.parseDate(item.updateDate, DateUtil.FORMAT_DD_MM_YYYY_HH_MM_SS));
                listDTO.add(dto);
            }
            logger.info("Successfully fetched {} products.", listDTO.size());
            return new ResponseObject(Constant.SUCCESS_STATUS, Constant.SUCCESS_MESSAGE, listDTO);
        }
        catch (Exception e){
            logger.error("Error occurred while fetching products: {}", e.getMessage(), e);
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
            logger.info("Starting search for products with keyword: {} and sort order: {}.", keyword, sort);
            // Tạo đối tượng PageRequest để hỗ trợ phân trang và sắp xếp
            PageRequest pageRequest;
            if (sort == null || sort.isEmpty()) sort = "asc"; // Sử dụng mặc định "asc" nếu không có giá trị sắp xếp

            if (sort.equalsIgnoreCase("asc")) {
                pageRequest = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Order.asc("productId")));
            } else {
                pageRequest = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Order.desc("productId")));
            }
            // Tìm kiếm các danh mục với từ khóa tìm kiếm và phân trang
            logger.info("Fetching products from page: {} with page size: {}.", pageNo, pageSize);
            Page<Product> productPage = productRepository.findByNameContainingIgnoreCase(keyword, pageRequest);

            // Kiểm tra nếu không có kết quả
            if (productPage.isEmpty()) {
                logger.warn("No products found for the search with keyword: {}.", keyword);
                return new ResponseObject(Constant.FAILED_STATUS, Constant.FAIL_MESSAGE, null);
            }

            // Chuyển đổi danh sách Category thành DTO (nếu cần)
            List<ProductDTO> productDTOs = productPage.stream()
                    .map(item -> {
                        ProductDTO dto = new ProductDTO();
                        dto.setProductId(item.getProductId());
                        dto.setName(item.getName());
                        dto.setImage(item.getImage());
                        dto.setPrice(item.getPrice());
                        dto.setStatus(item.isStatus());
                        dto.setDescription(item.getDescription());
                        dto.setCategoryId(item.getCategory().getCategoryId());
                        dto.setCategoryName(item.getCategory().getName());
                        dto.setCreateBy(item.getCreateBy());
                        dto.setCreateDate(DateUtil.parseDate(item.getCreateDate(), DateUtil.FORMAT_DD_MM_YYYY_HH_MM_SS));
                        dto.setUpdateBy(item.getUpdateBy());
                        dto.setUpdateDate(DateUtil.parseDate(item.updateDate, DateUtil.FORMAT_DD_MM_YYYY_HH_MM_SS));
                        return dto;
                    })
                    .collect(Collectors.toList());
            logger.info("Successfully fetched {} products for keyword: {}.", productDTOs.size(), keyword);
            // Trả về kết quả dưới dạng ResponseObject
            return new ResponseObject(Constant.SUCCESS_STATUS, Constant.SUCCESS_MESSAGE, productDTOs);
        }
        catch (Exception e){
            logger.error("Error occurred while fetching products with keyword: {}. Error message: {}", keyword, e.getMessage(), e);
            return new ResponseObject(
                    Constant.ERROR_STATUS,
                    Constant.ERROR_MESSAGE,
                    e.getMessage()
            );
        }
    }

    @Override
    public ResponseObject findById(Integer productId) {
        try {
            // Kiểm tra nếu categoryId không hợp lệ (ví dụ: null hoặc không tồn tại trong cơ sở dữ liệu)
            if (productId == null || productId <= 0) {
                logger.warn("GET request failed for Product ID: {} - Product not found", productId);
                return new ResponseObject(
                        Constant.FAILED_STATUS,
                        "Invalid product ID provided.",
                        null
                );
            }

            // Kiểm tra nếu productId không hợp lệ (ví dụ: null hoặc không tồn tại trong cơ sở dữ liệu)
            Optional<Product> product = productRepository.findById(productId);
            // Kiểm tra nếu tìm thấy category
            if (product.isPresent()) {
                ProductDTO dto = new ProductDTO();
                dto.setProductId(product.get().getProductId());
                dto.setName(product.get().getName());
                dto.setImage(product.get().getImage());
                dto.setPrice(product.get().getPrice());
                dto.setStatus(product.get().isStatus());
                dto.setDescription(product.get().getDescription());
                dto.setCategoryId(product.get().getCategory().getCategoryId());
                dto.setCategoryName(product.get().getCategory().getName());
                dto.setCreateBy(product.get().getCreateBy());
                dto.setCreateDate(DateUtil.parseDate(product.get().getCreateDate(), DateUtil.FORMAT_DD_MM_YYYY_HH_MM_SS));
                dto.setUpdateBy(product.get().getUpdateBy());
                dto.setUpdateDate(DateUtil.parseDate(product.get().updateDate, DateUtil.FORMAT_DD_MM_YYYY_HH_MM_SS));
                logger.info("GET request successful for Product ID: {}", productId);
                return new ResponseObject(
                        Constant.SUCCESS_STATUS,
                        Constant.SUCCESS_MESSAGE,
                        dto
                );
            } else {
                logger.warn("GET request failed for Product ID: {} - Product not found", productId);
                return new ResponseObject(
                        Constant.FAILED_STATUS,
                        Constant.FAIL_MESSAGE,
                        Constant.NULL_DATA
                );
            }
        }
        catch (Exception e){
            logger.error("GET request error for Product ID: {} - Error: {}", productId, e.getMessage(), e);
            return new ResponseObject(
                    Constant.ERROR_STATUS,
                    Constant.ERROR_MESSAGE,
                    e.getMessage()
            );
        }
    }

    @Override
    public ResponseObject save(ProductViewModel model) {
        try {
            logger.info("Starting to save product with name: {}", model.getName());
            Product product = new Product();
            product.setName(model.getName());
            product.setCategoryId(model.getCategoryId());
            product.setDescription(model.getDescription());
            product.setPrice(model.getPrice());
            product.setStatus(model.isStatus());
            product.setCreateBy(Constant.SYSTEM);
            product.setCreateDate(new Date());
            logger.info("Product details: Name = {}, CategoryId = {}, Price = {}, Status = {}",
                    model.getName(), model.getCategoryId(), model.getPrice(), model.isStatus());
            // Lưu category vào cơ sở dữ liệu
            product = productRepository.save(product);
            logger.info("Product with ID {} saved successfully.", product.getProductId());
            return new ResponseObject(
                    Constant.SUCCESS_STATUS,
                    Constant.SUCCESS_MESSAGE,
                    product
            );
        }
        catch (Exception e){
            logger.error("Error occurred while saving product. Error message: {}", e.getMessage(), e);
            return new ResponseObject(
                    Constant.ERROR_STATUS,
                    Constant.ERROR_MESSAGE,
                    e.getMessage()
            );
        }
    }

    @Override
    public ResponseObject update(Integer productId, ProductViewModel model) {
        try {
            logger.info("Starting to update product with ID: {}", productId);
            // Tìm kiếm Category từ categoryId
            Optional<Product> existingProductOpt = productRepository.findById(productId);

            // Nếu không tìm thấy category, trả về lỗi
            if (!existingProductOpt.isPresent()) {
                logger.warn("Product with ID {} not found.", productId);
                return new ResponseObject(
                        Constant.ERROR_STATUS,
                        "Product not found",
                        null
                );
            }

            // Lấy Category đã tồn tại
            Product existingProduct = existingProductOpt.get();
            logger.info("Found product with ID {}: Name = {}, Price = {}, Description = {}, CategoryId = {}, Status = {}",
                    existingProduct.getProductId(),
                    existingProduct.getName(),
                    existingProduct.getPrice(),
                    existingProduct.getDescription(),
                    existingProduct.getCategoryId(),
                    existingProduct.isStatus());

            // Cập nhật các thuộc tính từ CategoryViewModel
            existingProduct.setName(model.getName());
            existingProduct.setPrice(model.getPrice());
            existingProduct.setDescription(model.getDescription());
            existingProduct.setCategoryId(model.getCategoryId());
            existingProduct.setStatus(model.isStatus());
            existingProduct.setUpdateBy(Constant.SYSTEM);
            existingProduct.setUpdateDate(new Date());

            // Lưu category đã cập nhật vào cơ sở dữ liệu
            productRepository.save(existingProduct);
            logger.info("Product with ID {} updated successfully.", existingProduct.getProductId());
            // Trả về ResponseObject với thông báo thành công
            return new ResponseObject(
                    Constant.SUCCESS_STATUS,
                    Constant.SUCCESS_MESSAGE,
                    existingProduct
            );
        }
        catch (Exception e){
            logger.error("Error occurred while updating product with ID {}. Error message: {}", productId, e.getMessage(), e);
            return new ResponseObject(
                    Constant.ERROR_STATUS,
                    Constant.ERROR_MESSAGE,
                    e.getMessage()
            );
        }
    }

    @Override
    public ResponseObject delete(Integer productId) {
        try {
            logger.info("Starting to delete product with ID: {}", productId);
            // Kiểm tra xem sản phẩm có tồn tại trong cơ sở dữ liệu không
            Optional<Product> product = productRepository.findById(productId);
            if (product.isPresent()) {
                logger.info("Product with ID {} found. Deleting...", productId);
                // Nếu tìm thấy sản phẩm, tiến hành xóa
                productRepository.delete(product.get());
                logger.info("Product with ID {} deleted successfully.", productId);
                return new ResponseObject(
                        Constant.SUCCESS_STATUS,
                        Constant.SUCCESS_MESSAGE,
                        Constant.NULL_DATA
                );
            } else {
                // Nếu không tìm thấy sản phẩm, trả về thông báo lỗi
                return new ResponseObject(
                        Constant.FAILED_STATUS,
                        Constant.FAIL_MESSAGE,
                        Constant.NULL_DATA
                );
            }
        }
        catch (Exception e){
            logger.warn("Product with ID {} not found. Deletion failed.", productId);
            return new ResponseObject(
                    Constant.ERROR_STATUS,
                    Constant.ERROR_MESSAGE,
                    e.getMessage()
            );
        }
    }
}
