package com.anhtuan.bookapp.controller;

import com.anhtuan.bookapp.common.Utils;
import com.anhtuan.bookapp.config.Constant;
import com.anhtuan.bookapp.domain.*;
import com.anhtuan.bookapp.response.GetUserBookLibraryResponse;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("purchasedBook")
public class PurchasedBookController {

    PurchasedBookService purchasedBookService;

    UserService userService;

    BookService bookService;

    FirebaseMessagingService firebaseMessagingService;

    NotificationService notificationService;

    DeviceService deviceService;

    @PostMapping("buyBook")
    public ResponseEntity<Response> buyBook(@RequestParam String userId,
                                            @RequestParam String bookId){
        Response response = new Response();
        Book book = bookService.findBookById(bookId);
        if (book == null){
            response.setCode(109);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        User user = userService.getUserByUserId(userId);
        User seller = userService.getUserByUserId(book.getUserPost());
        if (user == null || seller == null){
            response.setCode(106);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }



        PurchasedBook purchasedBook = purchasedBookService.getPurchasedBookByBookIdAndUserId(userId, bookId);
        if (purchasedBook != null){
            response.setCode(112);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            int pointUser = user.getPoint();
            int pointSeller = seller.getPoint();
            int price = book.getBookPrice();

            if (price <= pointUser){
                long time = System.currentTimeMillis();
                pointUser -= price;
                int incomePoint = Math.floorDiv(price, 2);
                pointSeller += incomePoint;

                userService.updatePointByUserId(userId, pointUser);
                userService.updatePointByUserId(seller.getId(), pointSeller);

                purchasedBook = new PurchasedBook(bookId, userId, book.getBookName(), 0, time, price, time, true);
                purchasedBookService.insertPuchasedBook(purchasedBook);

                String mess = Utils.messageBodyBuyBook(user.getName(), book.getBookName());
                Notification notification = new Notification
                        (book.getUserPost(), bookId, mess, false, System.currentTimeMillis());
                notificationService.insertNotification(notification);

                Device device = deviceService.getDeviceByUserId(book.getUserPost());
                if (device != null && !device.getDeviceToken().isBlank()){
                    NotificationMessage message = new
                            NotificationMessage(device.getDeviceToken(), Constant.BUY_BOOK_NOTIFICATION_TITLE, mess);
                    firebaseMessagingService.sendNotificationByToken(message);
                }

                response.setCode(100);
            } else {
                response.setCode(111);
            }
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("getUserBookLibrary")
    public ResponseEntity<Response> getUserBookLibrary(@RequestParam String userId){
        Response response = new Response();
        User user = userService.getUserByUserId(userId);
        if (user == null){
            response.setCode(106);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        List<PurchasedBook> purchasedBookList = purchasedBookService.getPurchasedBooksByUserIdAndShowLibrary(userId, true);
        HashMap<String, Integer> totalChapterMap = new HashMap<>();
        HashMap<String, String> bookImageMap = new HashMap<>();
        List<String> idList = new ArrayList<>();

        for (PurchasedBook purchasedBook: purchasedBookList){
            idList.add(purchasedBook.getBookId());
        }

        List<GetUserBookLibraryResponse> getUserBookLibraryResponses = new ArrayList<>();
        List<Book> bookList = bookService.getBooksByIdList(idList);

        for (Book book: bookList){
            totalChapterMap.put(book.getId(), book.getTotalChapter());
            bookImageMap.put(book.getId(), book.getBookImage());
        }

        GetUserBookLibraryResponse getUserBookLibraryResponse;
        for (PurchasedBook purchasedBook: purchasedBookList){
            getUserBookLibraryResponse = new GetUserBookLibraryResponse(purchasedBook);
            getUserBookLibraryResponse.setTotalChapter(totalChapterMap.get(purchasedBook.getBookId()));
            getUserBookLibraryResponse.setBookImage(bookImageMap.get(purchasedBook.getBookId()));
            getUserBookLibraryResponses.add(getUserBookLibraryResponse);
        }

        response.setCode(100);
        response.setData(getUserBookLibraryResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("checkPurchasedBook")
    public ResponseEntity<Response> checkPurchasedBook(@RequestParam String bookId,
                                                       @RequestParam String userId){
        Response response = new Response();
        PurchasedBook purchasedBook = purchasedBookService.getPurchasedBookByBookIdAndUserId(bookId, userId);
        if (purchasedBook != null){
            response.setData(Constant.StatusPurchasedBook.PURCHASED);
        } else {
            response.setData(Constant.StatusPurchasedBook.NOT_PURCHASED);
        }
        response.setCode(100);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("getPurchasedBook")
    public ResponseEntity<Response> getPurchasedBook(@RequestParam String bookId,
                                                       @RequestParam String userId){
        Response response = new Response();
        Book book = bookService.findBookById(bookId);
        if (book == null){
            response.setCode(106);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        PurchasedBook purchasedBook = purchasedBookService.getPurchasedBookByBookIdAndUserId(bookId, userId);
        if (purchasedBook == null){
            response.setCode(112);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setCode(100);
        response.setData(purchasedBook);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
