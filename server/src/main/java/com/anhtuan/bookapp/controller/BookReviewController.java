package com.anhtuan.bookapp.controller;

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
            response.setCode(106);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        if (bookReviewService.getBookReviewByBookIdAndAuthor(request.getBookId(), request.getAuthor()).size() > 0){
            response.setCode(118);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        Book book = bookService.findBookById(request.getBookId());
        if (book == null){
            response.setCode(109);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        BookReview bookReview = new BookReview(request);
        bookReview.setReviewTime(System.currentTimeMillis());
        bookReviewService.addBookReview(bookReview);

        int quantityReview = bookReviewService.countBookReviewsByBookId(request.getBookId()) - 1;
        double reviewStar = request.getReviewStar();
        double star = book.getStar();
        double newStar = ((star * quantityReview) + reviewStar) / (quantityReview + 1);
        bookService.updateStarById(request.getBookId(), newStar);

        response.setCode(100);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getBookReviews")
    public ResponseEntity<Response> getBookReviews(@RequestParam String bookId){
        Response response = new Response();
        List<BookReview> bookReviewList = bookReviewService.getBookReviewByBookId(bookId);

        response.setCode(100);
        response.setData(bookReviewList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getBookReview")
    public ResponseEntity<Response> getBookReview(@RequestParam String bookId,
                                                  @RequestParam String userId){
        Response response = new Response();
        List<BookReview> bookReviewList = bookReviewService.getBookReviewByBookIdAndAuthor(bookId, userId);
        if (bookReviewList == null || bookReviewList.size() == 0){
            response.setCode(119);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setCode(100);
        response.setData(bookReviewList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
