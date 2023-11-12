package com.anhtuan.bookapp.controller;

import com.anhtuan.bookapp.common.ResponseCode;
import com.anhtuan.bookapp.common.Utils;
import static com.anhtuan.bookapp.config.Constant.*;
import com.anhtuan.bookapp.domain.*;
import com.anhtuan.bookapp.response.GetUserBookLibraryResponse;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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

    TransactionHistoryService transactionHistoryService;

    @PostMapping("buyBook")
    public ResponseEntity<Response> buyBook(Authentication authentication,
                                            @RequestParam String bookId){
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

        User seller = userService.getUserByUserId(book.getAuthor());
        if (seller == null){
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        PurchasedBook purchasedBook = purchasedBookService.getPurchasedBookByBookIdAndUserId(userId, bookId);
        if (purchasedBook != null){
            response.setCode(ResponseCode.PURCHASED_BOOK_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            int price = book.getBookPrice();
            int pointUser = userDetails.getUser().getPoint();
            if (price <= pointUser){
                long time = System.currentTimeMillis();
                int incomePoint = Math.floorDiv(price, 2);

                userService.updatePointByUserId(userId, -1 * price);
                userService.updatePointByUserId(seller.getId(), incomePoint);
                pointUser -= price;
                int pointSeller = seller.getPoint();

                TransactionHistory buyBookHis = new TransactionHistory(userId, -1*price, pointUser, TRANSACTION_TYPE.BUY_BOOK, time);
                TransactionHistory sellBookHis = new TransactionHistory(seller.getId(), incomePoint, pointSeller, TRANSACTION_TYPE.SELL_BOOK, time);
                transactionHistoryService.addTransactionHistory(buyBookHis);
                transactionHistoryService.addTransactionHistory(sellBookHis);

                purchasedBook = new PurchasedBook(bookId, userId, book.getBookName(), 0, time, price, time, true);
                purchasedBookService.insertPuchasedBook(purchasedBook);

                bookService.increaseTotalPurchased(bookId);

                String mess = Utils.messageBodyBuyBook(userDetails.getUser().getName(), book.getBookName());
                Notification notification = new Notification
                        (book.getAuthor(), bookId, mess, false, time);
                notificationService.insertNotification(notification);

                List<Device> devices = deviceService.getDevicesByUserId(book.getAuthor());
                if (devices != null && !devices.isEmpty()){
                    devices.forEach(device -> {
                        NotificationMessage message = new
                                NotificationMessage(device.getDeviceToken(), BUY_BOOK_NOTIFICATION_TITLE, mess);
                        firebaseMessagingService.sendNotificationByToken(message);
                    });
                }

                response.setCode(ResponseCode.SUCCESS);
            } else {
                response.setCode(ResponseCode.NOT_ENOUGH_POINT);
            }
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("getUserBookLibrary")
    public ResponseEntity<Response> getUserBookLibrary(Authentication authentication){
        Response response = new Response();
        if (authentication == null) {
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUser().getId();

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

        response.setCode(ResponseCode.SUCCESS);
        response.setData(getUserBookLibraryResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("checkPurchasedBook")
    public ResponseEntity<Response> checkPurchasedBook(@RequestParam String bookId,
                                                       Authentication authentication){
        Response response = new Response();

        if (authentication.getPrincipal() == null) {
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUser().getId();

        PurchasedBook purchasedBook = purchasedBookService.getPurchasedBookByBookIdAndUserId(bookId, userId);
        if (purchasedBook != null){
            response.setData(STATUS_PURCHASED_BOOK.PURCHASED);
        } else {
            response.setData(STATUS_PURCHASED_BOOK.NOT_PURCHASED);
        }
        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("getPurchasedBook")
    public ResponseEntity<Response> getPurchasedBook(@RequestParam String bookId,
                                                     Authentication authentication){
        Response response = new Response();

        if (authentication.getPrincipal() == null) {
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUser().getId();

        Book book = bookService.findBookById(bookId);
        if (book == null){
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        PurchasedBook purchasedBook = purchasedBookService.getPurchasedBookByBookIdAndUserId(bookId, userId);
        if (purchasedBook == null){
            response.setCode(ResponseCode.PURCHASED_BOOK_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setCode(ResponseCode.SUCCESS);
        response.setData(purchasedBook);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("unShowBook")
    public ResponseEntity<Response> unShowBook(@RequestParam String bookId,
                                               Authentication authentication){
        Response response = new Response();

        if (authentication.getPrincipal() == null) {
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUser().getId();

        PurchasedBook purchasedBook = purchasedBookService.getPurchasedBookByBookIdAndUserId(bookId, userId);
        if (Objects.isNull(purchasedBook)){
            response.setCode(ResponseCode.PURCHASED_BOOK_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        purchasedBookService.unShowPurchasedBook(bookId, userId);
        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
