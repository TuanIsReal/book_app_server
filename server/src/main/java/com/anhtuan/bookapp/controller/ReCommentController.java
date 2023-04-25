package com.anhtuan.bookapp.controller;

import com.anhtuan.bookapp.domain.Comment;
import com.anhtuan.bookapp.domain.ReComment;
import com.anhtuan.bookapp.request.AddReCommentRequest;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.CommentService;
import com.anhtuan.bookapp.service.base.ReCommentService;
import com.anhtuan.bookapp.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reComment")
@AllArgsConstructor
public class ReCommentController {
    private ReCommentService reCommentService;
    private CommentService commentService;
    private UserService userService;

    @PostMapping("addReComment")
    public ResponseEntity<Response> addReComment(@RequestBody AddReCommentRequest request){
        Response response = new Response();
        Comment comment = commentService.getCommentById(request.getParentCommentId());

        if (comment == null){
            response.setCode(116);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        if (userService.getUserByUserId(request.getAuthor()) == null){
            response.setCode(106);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        ReComment reComment = new ReComment(request);
        reComment.setCommentTime(System.currentTimeMillis());
        reCommentService.insertReComment(reComment);
        commentService.updateTotalReCommentById(request.getParentCommentId(), comment.getTotalReComment()+1);
        response.setCode(100);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("getReCommentList")
    public ResponseEntity<Response> getReCommentList(@RequestParam String parentCommentId){
        Response response = new Response();
        List<ReComment> reComments = reCommentService.getReCommentsByParentCommentId(parentCommentId);

        response.setCode(100);
        response.setData(reComments);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
