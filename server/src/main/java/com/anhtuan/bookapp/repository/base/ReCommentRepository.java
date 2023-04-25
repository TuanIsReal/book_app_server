package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.ReComment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReCommentRepository extends MongoRepository<ReComment, String> {
    List<ReComment> findReCommentsByParentCommentId(String parentCommentId);
}
