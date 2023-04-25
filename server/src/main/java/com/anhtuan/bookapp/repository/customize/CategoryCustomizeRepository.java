package com.anhtuan.bookapp.repository.customize;

import com.anhtuan.bookapp.domain.Category;

import java.util.List;

public interface CategoryCustomizeRepository {

    void insertCategory(Category category);

    List<Category> findCategoryByText(String text);
}
