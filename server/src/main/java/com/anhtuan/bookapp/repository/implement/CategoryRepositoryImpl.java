package com.anhtuan.bookapp.repository.implement;

import com.anhtuan.bookapp.domain.Category;
import com.anhtuan.bookapp.repository.customize.CategoryCustomizeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CategoryRepositoryImpl implements CategoryCustomizeRepository {

    private MongoTemplate mongoTemplate;

    @Override
    public void insertCategory(Category category) {
        mongoTemplate.insert(category);
    }

    @Override
    public List<Category> findCategoryByText(String text) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Category.CATEGORY_NAME).regex(text, "i"));
        return mongoTemplate.find(query, Category.class);
    }
}
