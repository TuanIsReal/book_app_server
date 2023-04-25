package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.domain.Comment;
import com.anhtuan.bookapp.repository.base.CommentRepository;
import com.anhtuan.bookapp.service.base.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void insertComment(Comment comment) {
        commentRepository.insert(comment);
    }

    @Override
    public List<Comment> getCommentsByBookId(String bookId) {
        return commentRepository.findCommentsByBookIdOrderByCommentTimeAsc(bookId);
    }

    @Override
    public Comment getCommentById(String id) {
        return commentRepository.findCommentById(id);
    }

    @Override
    public void updateTotalReCommentById(String id, int totalReComment) {
        commentRepository.updateTotalReCommentById(id, totalReComment);
    }
}
