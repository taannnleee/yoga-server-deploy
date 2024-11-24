package org.example.yogabusinessmanagementweb.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.yogabusinessmanagementweb.common.Enum.EStatus;
import org.example.yogabusinessmanagementweb.common.entities.Category;
import org.example.yogabusinessmanagementweb.common.mapper.CategoryMapper;
import org.example.yogabusinessmanagementweb.common.mapper.Mappers;
import org.example.yogabusinessmanagementweb.dto.request.category.CategoryCreationRequest;
import org.example.yogabusinessmanagementweb.dto.response.category.CategoryResponse;
import org.example.yogabusinessmanagementweb.dto.response.category.CategoryWithProductResponse;
import org.example.yogabusinessmanagementweb.exception.AppException;
import org.example.yogabusinessmanagementweb.exception.ErrorCode;
import org.example.yogabusinessmanagementweb.repositories.CategoryRepository;
import org.example.yogabusinessmanagementweb.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;


    @Override
    public CategoryResponse addCategory(CategoryCreationRequest categoryCreationRequest) {
        Category category = Mappers.convertToEntity(categoryCreationRequest, Category.class);

        // Gán giá trị mặc định cho status nếu nó là null
        if (category.getStatus() == null) {
            category.setStatus(EStatus.ACTIVE);
        }
        // Kiểm tra nếu tên đã tồn tại và status là true
        Optional<Category> existingCategory = categoryRepository.findByNameAndStatus(category.getName(), category.getStatus());

        if (existingCategory.isPresent()) {
            throw new AppException(ErrorCode.CATEGORY_EXISTS);
        }

        category = categoryRepository.save(category);

        CategoryResponse categoryResponse = Mappers.convertToDto(category, CategoryResponse.class);
        return categoryResponse;
    }

    @Override
    public Category findByIdAndStatus(Long id, EStatus status) {
        // Tìm kiếm danh mục theo tên và trạng thái
        return categoryRepository.findByIdAndStatus(id, status)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponse> list = Mappers.mapperEntityToDto (categoryList, CategoryResponse.class);
        return list;
    }

    public List<CategoryWithProductResponse> getCategoriesWithProducts() {
        List<Category> categories = categoryRepository.findAll();  // Or any method returning List<Category>
        return categories.stream()
                .map(categoryMapper::toCategoryWithProductResponse)  // Map to CategoryWithProductResponse
                .collect(Collectors.toList());
    }


}

