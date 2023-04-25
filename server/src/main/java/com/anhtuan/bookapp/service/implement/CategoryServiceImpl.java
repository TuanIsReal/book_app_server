package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.domain.Category;
import com.anhtuan.bookapp.repository.base.CategoryRepository;
import com.anhtuan.bookapp.service.base.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void insertCategory(Category category) {
        categoryRepository.insertCategory(category);
    }

    @Override
    public Category getCategoryByCategoryName(String categoryName) {
        return categoryRepository.findCategoryByCategoryName(categoryName);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> findCategoriesByNameList(ArrayList<String> categoriesName) {
        return categoryRepository.findCategoriesByCategoryNameIn(categoriesName);
    }

    @Override
    public List<Category> findCategoryByText(String text) {
        return categoryRepository.findCategoryByText(text);
    }

    @Override
    public List<Category> findCategoriesByIdList(List<String> idList) {
        return categoryRepository.findCategoriesByIdIn(idList);
    }


}
