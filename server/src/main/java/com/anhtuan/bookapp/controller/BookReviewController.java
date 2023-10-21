package com.anhtuan.bookapp.controller;

import com.anhtuan.bookapp.common.ResponseCode;
import com.anhtuan.bookapp.domain.Book;
import com.anhtuan.bookapp.domain.BookReview;
import com.anhtuan.bookapp.request.AddBookReviewRequest;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.BookReviewService;
import com.anhtuan.bookapp.service.base.BookService;
import com.anhtuan.bookapp.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bookReview")
@AllArgsConstructor
public class BookReviewController {

    private BookReviewService bookReviewService;
    private UserService userService;
    private BookService bookService;

    @PostMapping("/addBookReview")
    public ResponseEntity<Response> addBookReview(@RequestBody AddBookReviewRequest request){
        Response response = new Response();
        if (userService.getUserByUserId(request.getAuthor()) == null){
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        if (!bookReviewService.getBookReviewByBookIdAndAuthor(request.getBookId(), request.getAuthor()).isEmpty()){
            response.setCode(ResponseCode.BOOK_REVIEW_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        Book book = bookService.findBookById(request.getBookId());
        if (book == null){
            response.setCode(ResponseCode.BOOK_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        BookReview bookReview = new BookReview(request);
        bookReview.setReviewTime(System.currentTimeMillis());
        bookReviewService.addBookReview(bookReview);

        double reviewStar = request.getReviewStar();
        double star = book.getStar();
        double newStar = ((star * book.getTotalReview()) + reviewStar) / (book.getTotalReview() + 1);
        bookService.updateStarById(request.getBookId(), newStar);
        bookService.increaseTotalReview(book.getId());

        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getBookReviews")
    public ResponseEntity<Response> getBookReviews(@RequestParam String bookId){
        Response response = new Response();
        List<BookReview> bookReviewList = bookReviewService.getBookReviewByBookId(bookId);

        response.setCode(ResponseCode.SUCCESS);
        response.setData(bookReviewList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getBookReview")
    public ResponseEntity<Response> getBookReview(@RequestParam String bookId,
                                                  @RequestParam String userId){
        Response response = new Response();
        List<BookReview> bookReviewList = bookReviewService.getBookReviewByBookIdAndAuthor(bookId, userId);
        if (bookReviewList == null || bookReviewList.isEmpty()){
            response.setCode(ResponseCode.BOOK_REVIEW_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setCode(ResponseCode.SUCCESS);
        response.setData(bookReviewList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
