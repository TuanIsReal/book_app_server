package com.anhtuan.bookapp.controller;

import com.anhtuan.bookapp.common.ResponseCode;
import com.anhtuan.bookapp.common.Utils;
import static com.anhtuan.bookapp.config.Constant.*;
import com.anhtuan.bookapp.domain.*;
import com.anhtuan.bookapp.request.AddBookChapterRequest;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("bookChapter")
@AllArgsConstructor
public class BookChapterController {

    private BookChapterService bookChapterService;

    private BookService bookService;

    private PurchasedBookService purchasedBookService;

    private DeviceService deviceService;

    private NotificationService notificationService;

    private FirebaseMessagingService firebaseMessagingService;

    private STFService stfService;

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
        BookChapter bookChapter = new BookChapter(bookId, request.getChapterNumber(), request.getChapterName(), request.getChapterContent(), time, time);
        String chapterId = bookChapterService.insertBookChapter(bookChapter);
        stfService.createChapterText(request.getChapterContent(), chapterId + TXT);
        bookService.increaseTotalChapter(book.getId());

        List<PurchasedBook> purchasedBookList = purchasedBookService.findPurchasedBooksByBookIdAndUserIdIsNot(bookId, book.getAuthor());
        List<String> purchasedUserList = new ArrayList<>();
        for (PurchasedBook purchasedBook: purchasedBookList){
            purchasedUserList.add(purchasedBook.getUserId());
        }

        String messBody = Utils.messBodyAddChapter(book.getBookName(), request.getChapterNumber(), request.getChapterName());
        List<Notification> notificationList = new ArrayList<>();

        List<Device> deviceList = deviceService.getDevicesByUserIdIsIn(purchasedUserList);
        for (Device device:deviceList){
            if (!device.getDeviceToken().isEmpty()){
                NotificationMessage message = new NotificationMessage(device.getDeviceToken(), ADD_CHAPTER_NOTIFICATION_TITLE, messBody);
                firebaseMessagingService.sendNotificationByToken(message);
            }
        }

        for (String userId:purchasedUserList){
            Notification notification = new Notification(userId, bookId, messBody, false, System.currentTimeMillis());
            notificationList.add(notification);
        }
        notificationService.insertNotificationList(notificationList);

        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("getChapterContent")
    private ResponseEntity<Response> getChapterContent(@RequestParam String userId,
                                                       @RequestParam String bookId,
                                                       @RequestParam int chapterNumber){
        Response response = new Response();
        Book book = bookService.findBookById(bookId);
        if (book == null){
            response.setCode(106);
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
            response.setCode(113);
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


}
