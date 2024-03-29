package com.anhtuan.bookapp.controller;

import com.anhtuan.bookapp.common.ResponseCode;
import com.anhtuan.bookapp.domain.Comment;
import com.anhtuan.bookapp.domain.CustomUserDetails;
import com.anhtuan.bookapp.request.AddCommentRequest;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.BookService;
import com.anhtuan.bookapp.service.base.CommentService;
import com.anhtuan.bookapp.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("comment")
@AllArgsConstructor
public class CommentController {
    private CommentService commentService;
    private BookService bookService;

    @PostMapping("addComment")
    public ResponseEntity<Response> addComment(Authentication authentication,
                                               @RequestBody AddCommentRequest request){
        Response response = new Response();

        if (authentication == null) {
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUser().getId();

        if (bookService.findBookById(request.getBookId()) == null){
            response.setCode(ResponseCode.BOOK_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        Comment comment = new Comment(request);
        comment.setAuthor(userId);
        comment.setTotalReComment(0);
        comment.setCommentTime(System.currentTimeMillis());
        commentService.insertComment(comment);

        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("getCommentList")
    public ResponseEntity<Response> getCommentList(@RequestParam String bookId){
        Response response = new Response();
        List<Comment> commentList = commentService.getCommentsByBookId(bookId);

        response.setCode(ResponseCode.SUCCESS);
        response.setData(commentList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
