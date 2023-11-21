package com.anhtuan.bookapp.controller;

import com.anhtuan.bookapp.common.ResponseCode;
import com.anhtuan.bookapp.common.Utils;
import static com.anhtuan.bookapp.config.Constant.*;
import com.anhtuan.bookapp.domain.*;
import com.anhtuan.bookapp.request.AddBookChapterRequest;
import com.anhtuan.bookapp.request.UpdateBannerWordRequest;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.*;
import com.anhtuan.bookapp.worker.ChapterContainer;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("bookChapter")
@AllArgsConstructor
public class BookChapterController {

    private final BookChapterService bookChapterService;
    private final BookService bookService;
    private final PurchasedBookService purchasedBookService;
    private final STFService stfService;
    private final BannedWordService bannedWordService;
    private final ChapterContainer chapterContainer;

    @PostMapping("addChapter")
    private ResponseEntity<Response> addChapter(@RequestBody AddBookChapterRequest request){
        Response response = new Response();
        Book book = bookService.findBookByBookName(request.getBookName());
        if (book == null){
            response.setCode(ResponseCode.BOOK_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        String bookId = book.getId();
        if (bookChapterService.findBookChapterByBookIdAndChapterNumber(bookId, request.getChapterNumber()) != null){
            response.setCode(ResponseCode.CHAPTER_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        long time = System.currentTimeMillis();
        BookChapter bookChapter = new BookChapter(bookId, request.getChapterNumber(), request.getChapterName(), request.getChapterContent(), time, time, BOOK_CHAPTER_STATUS.NOT_VERIFY);
        String chapterId = bookChapterService.insertBookChapter(bookChapter);
        stfService.createChapterText(request.getChapterContent(), chapterId + TXT);
        chapterContainer.add(chapterId);

        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("getChapterContent")
    private ResponseEntity<Response> getChapterContent(Authentication authentication,
                                                       @RequestParam String bookId,
                                                       @RequestParam int chapterNumber){
        Response response = new Response();

        if (authentication == null) {
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUser().getId();

        Book book = bookService.findBookById(bookId);
        if (book == null){
            response.setCode(ResponseCode.BOOK_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        BookChapter bookChapter = bookChapterService.findBookChapterByBookIdAndChapterNumber(bookId, chapterNumber);
        if (bookChapter == null){
            List<BookChapter> bookChapters = bookChapterService.findBookChaptersByBookIdAndChapterNumberGreaterThanOrderByChapterNumberAsc(bookId, chapterNumber);
            if (bookChapters != null && bookChapters.size() > 0){
                bookChapter = bookChapters.get(0);
            }
        }

        if (bookChapter == null){
            response.setCode(ResponseCode.CHAPTER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        String chapterContent;
        if(purchasedBookService.getPurchasedBookByBookIdAndUserId(bookId, userId) == null){
            if (chapterNumber <= book.getFreeChapter()){
                chapterContent = stfService.getChapterContent(bookChapter.getChapterContent() + TXT);
            } else {
                chapterContent = CHAPTER_BLOCK_CONTENT;
            }
        } else {
            chapterContent = stfService.getChapterContent(bookChapter.getChapterContent() + TXT);
            purchasedBookService.updateLastReadChapterByBookIdAndUserId(bookId, userId, chapterNumber);
        }

        bookChapter.setChapterContent(chapterContent);
        response.setCode(ResponseCode.SUCCESS);
        response.setData(bookChapter);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("getChapterInfo")
    private ResponseEntity<Response> getChapterContent(@RequestParam String chapterId){
        Response response = new Response();

        BookChapter bookChapter = bookChapterService.getBookChapter(chapterId);

        if (bookChapter == null){
            response.setCode(ResponseCode.CHAPTER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        response.setCode(ResponseCode.SUCCESS);
        response.setData(bookChapter);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("getBookChapterList")
    private ResponseEntity<Response> getBookChapterList(@RequestParam String bookId){
        Response response = new Response();

        Book book = bookService.findBookById(bookId);
        if (book == null){
            response.setCode(106);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        List<BookChapter> bookChapters = bookChapterService.getBookChaptersByBookId(bookId);
        response.setCode(ResponseCode.SUCCESS);
        response.setData(bookChapters);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("updateBannedWord")
    private ResponseEntity<Response> updateBannedWord(@RequestBody UpdateBannerWordRequest request){
        Response response = new Response();
        BannedWord bannedWord = bannedWordService.findOne();
        if (Objects.isNull(bannedWord)){
            BannedWord newBannedWord = new BannedWord(1, request.getWords());
            bannedWordService.createBannedWord(newBannedWord);
            response.setCode(ResponseCode.SUCCESS);
            return ResponseEntity.ok(response);
        }

        bannedWordService.updateBannedWord(bannedWord.getVersion(), request.getWords());
        response.setCode(ResponseCode.SUCCESS);
        return ResponseEntity.ok(response);
    }

    @GetMapping("getBannedWord")
    private ResponseEntity<Response> getBannedWord(@RequestParam int version){
        Response response = new Response();
        BannedWord bannedWord = bannedWordService.findOne();
        if (bannedWord.getVersion() == version){
            response.setCode(ResponseCode.BANNED_WORD_NO_CHANGE);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setCode(ResponseCode.SUCCESS);
        response.setData(bannedWord);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
