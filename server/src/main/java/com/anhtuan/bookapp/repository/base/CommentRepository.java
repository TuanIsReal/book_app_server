package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.Comment;
import com.anhtuan.bookapp.repository.customize.CommentCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String>, CommentCustomizeRepository {
    List<Comment> findCommentsByBookIdOrderByCommentTimeAsc(String bookId);
    Comment findCommentById(String id);
}
