package com.anhtuan.bookapp.controller;

import com.anhtuan.bookapp.common.ResponseCode;
import com.anhtuan.bookapp.common.Utils;
import static com.anhtuan.bookapp.config.Constant.*;
import com.anhtuan.bookapp.domain.*;
import com.anhtuan.bookapp.request.AddBookRequest;
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
@RequestMapping("bookRequestUp")
@AllArgsConstructor
public class BookRequestUpController {

    private BookRequestUpService bookRequestUpService;
    private BookService bookService;
    private CategoryService categoryService;
    private UserService userService;
    private PurchasedBookService purchasedBookService;
    private NotificationService notificationService;
    private DeviceService deviceService;
    private FirebaseMessagingService firebaseMessagingService;

    @PostMapping("addBookRequestUp")
    public ResponseEntity<Response> addBook(@RequestBody AddBookRequest request){
        Response response = new Response();
        if (bookService.findBookByBookName(request.getBookName()) != null){
            response.setCode(105);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        if (userService.getUserByUserId(request.getUserPost()) == null){
            response.setCode(106);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        if (bookRequestUpService.getBookRequestUpByBookNameAndStatus(request.getBookName(), STATUS_BOOK_REQUEST_UP.REQUEST) != null){
            response.setCode(114);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        BookRequestUp book = new BookRequestUp(request);
        ArrayList<String> categoriesName = request.getBookCategory();
        List<Category> categories = categoryService.findCategoriesByNameList(categoriesName);
        List<String> categoriesId = new ArrayList<>();
        for (Category category: categories){
            categoriesId.add(category.getId());
        }
        book.setBookCategory(categoriesId);
        book.setStatus(STATUS_BOOK_REQUEST_UP.REQUEST);
        book.setRequestTime(System.currentTimeMillis());
        bookRequestUpService.addBookRequestUp(book);
        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("reactBookRequestUp")
    public ResponseEntity<Response> updateBookImage(@RequestParam String bookId,
                                                    @RequestParam int action){
        Response response = new Response();
        if (bookService.findBookById(bookId) != null){
            response.setCode(105);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        BookRequestUp bookRequestUp = bookRequestUpService.getBookRequestUp(bookId, STATUS_BOOK_REQUEST_UP.REQUEST);
        if (bookRequestUp == null){
            response.setCode(115);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        String mess;

        if (action == REACT_UP_BOOK_REQUEST.ACCEPT){
            bookRequestUpService.updateStatusById(bookId, STATUS_BOOK_REQUEST_UP.ACCEPTED);
            long time = System.currentTimeMillis();
            Book book = new Book(bookRequestUp);
            book.setStar(5);
            book.setTotalChapter(0);
            book.setUploadTime(System.currentTimeMillis());
            book.setLastUpdateTime(time);
            bookService.insertBook(book);

            PurchasedBook purchasedBook =
                    new PurchasedBook(bookId, bookRequestUp.getUserPost(), book.getBookName(), 0, time, 0, time, true);
            purchasedBookService.insertPuchasedBook(purchasedBook);

            mess = Utils.messSuccessUploadBook(bookRequestUp.getBookName());
        } else {
            bookRequestUpService.updateStatusById(bookId, STATUS_BOOK_REQUEST_UP.REJECTED);
            mess = Utils.messSuccessUploadBook(bookRequestUp.getBookName());
        }

        Notification notification = new Notification
            (bookRequestUp.getUserPost(), bookId, mess, false, System.currentTimeMillis());
            notificationService.insertNotification(notification);

        Device device = deviceService.getDeviceByUserId(bookRequestUp.getUserPost());
        if (device != null && !device.getDeviceToken().isEmpty()) {
            NotificationMessage message = new NotificationMessage(device.getDeviceToken(), BOOK_REQUEST_UP_TITLE, mess);
            firebaseMessagingService.sendNotificationByToken(message);
        }
        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("getRequestUploadBook")
    public ResponseEntity<Response> getRequestUploadBook(@RequestParam String userId,
                                                         @RequestParam int status){
        Response response = new Response();
        HashMap<String, String> mapCategory = new HashMap<>();
        List<Category> listCategory = categoryService.findAll();

        for (Category category: listCategory){
            mapCategory.put(category.getId(), category.getCategoryName());
        }

        if (userService.getUserByUserId(userId) == null){
            response.setCode(106);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        List<BookRequestUp> bookRequestUpList = bookRequestUpService
                .getBookRequestUpsByUserPostAndStatus(userId, status);

        for (BookRequestUp bookRequestUp:bookRequestUpList){
            List<String> bookCategoryIdList = bookRequestUp.getBookCategory();
            List<String> bookNameList = new ArrayList<>();

            for (String categoryId: bookCategoryIdList){
                bookNameList.add(mapCategory.get(categoryId));
            }

            bookRequestUp.setBookCategory(bookNameList);
        }

        response.setCode(ResponseCode.SUCCESS);
        response.setData(bookRequestUpList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("getAllRequestUploadBook")
    public ResponseEntity<Response> getAllRequestUploadBook(){
        Response response = new Response();
        HashMap<String, String> mapCategory = new HashMap<>();
        List<Category> listCategory = categoryService.findAll();

        for (Category category: listCategory){
            mapCategory.put(category.getId(), category.getCategoryName());
        }

        List<BookRequestUp> bookRequestUpList = bookRequestUpService
                .getBookRequestUpsByStatus(STATUS_BOOK_REQUEST_UP.REQUEST);

        for (BookRequestUp bookRequestUp:bookRequestUpList){
            List<String> bookCategoryIdList = bookRequestUp.getBookCategory();
            List<String> bookNameList = new ArrayList<>();

            for (String categoryId: bookCategoryIdList){
                bookNameList.add(mapCategory.get(categoryId));
            }

            bookRequestUp.setBookCategory(bookNameList);
        }

        response.setCode(ResponseCode.SUCCESS);
        response.setData(bookRequestUpList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("getQuantityPurchased")
    public ResponseEntity<Response> getQuantityPurchased(@RequestParam String bookId,
                                                         @RequestParam String userId){
        Response response = new Response();
        int quantityPurchased;
        quantityPurchased = purchasedBookService.countPurchasedBooksByBookIdAndUserIdIsNot(bookId, userId);
        response.setCode(ResponseCode.SUCCESS);
        response.setData(quantityPurchased);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
