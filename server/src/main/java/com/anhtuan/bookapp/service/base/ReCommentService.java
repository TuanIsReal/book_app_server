package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.ReComment;

import java.util.List;

public interface ReCommentService {
    void insertReComment(ReComment reComment);
    List<ReComment> getReCommentsByParentCommentId(String parentCommentId);
}
