package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.Category;
import com.anhtuan.bookapp.repository.customize.CategoryCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;
import java.util.List;

public interface CategoryRepository extends MongoRepository<Category, String>, CategoryCustomizeRepository {

    Category findCategoryByCategoryName(String categoryName);

    void deleteById(String id);

    List<Category> findCategoriesByCategoryNameIn(ArrayList<String> categoriesName);

    List<Category> findCategoriesByIdIn(List<String> idList);
}
