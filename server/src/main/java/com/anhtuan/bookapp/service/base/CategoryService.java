package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.Category;

import java.util.ArrayList;
import java.util.List;

public interface CategoryService {
    void insertCategory(Category category);

    Category getCategoryByCategoryName(String categoryName);

    List<Category> findAll();

    void deleteById(String id);

    List<Category> findCategoriesByNameList(ArrayList<String> categoriesName);

    List<Category> findCategoryByText(String text);

    List<Category> findCategoriesByIdList(List<String> idList);
}
