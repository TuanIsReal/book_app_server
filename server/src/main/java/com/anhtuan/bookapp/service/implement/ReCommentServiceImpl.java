package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.domain.ReComment;
import com.anhtuan.bookapp.repository.base.ReCommentRepository;
import com.anhtuan.bookapp.service.base.ReCommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReCommentServiceImpl implements ReCommentService {
    private ReCommentRepository reCommentRepository;

    @Override
    public void insertReComment(ReComment reComment) {
        reCommentRepository.insert(reComment);
    }

    @Override
    public List<ReComment> getReCommentsByParentCommentId(String parentCommentId) {
        return reCommentRepository.findReCommentsByParentCommentId(parentCommentId);
    }
}
