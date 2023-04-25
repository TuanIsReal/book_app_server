package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.Comment;

import java.util.List;

public interface CommentService {
    void insertComment(Comment comment);
    List<Comment> getCommentsByBookId(String bookId);
    Comment getCommentById(String id);
    void updateTotalReCommentById(String id, int totalReComment);
}
