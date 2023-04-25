package com.anhtuan.bookapp.repository.implement;

import com.anhtuan.bookapp.domain.Comment;
import com.anhtuan.bookapp.repository.customize.CommentCustomizeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CommentRepositoryImpl implements CommentCustomizeRepository {
    private MongoTemplate mongoTemplate;

    @Override
    public void updateTotalReCommentById(String id, int totalReComment) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Comment.ID).is(id));
        Update update = new Update();
        update.set(Comment.TOTAL_RE_COMMENT, totalReComment);
        mongoTemplate.updateFirst(query, update, Comment.class);
    }
}
