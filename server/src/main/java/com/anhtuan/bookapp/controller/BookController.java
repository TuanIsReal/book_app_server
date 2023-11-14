package com.anhtuan.bookapp.controller;

import static com.anhtuan.bookapp.config.Constant.*;

import com.anhtuan.bookapp.cache.UserInfoManager;
import com.anhtuan.bookapp.common.ResponseCode;
import com.anhtuan.bookapp.common.Utils;
import com.anhtuan.bookapp.domain.*;
import com.anhtuan.bookapp.request.AddBookRequest;
import com.anhtuan.bookapp.request.GetBookFilterRequest;
import com.anhtuan.bookapp.request.UpdateBookRequest;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("book")
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    private UserService userService;

    private CategoryService categoryService;
    private DeviceService deviceService;
    private FirebaseMessagingService firebaseMessagingService;
    private NotificationService notificationService;
    private PurchasedBookService purchasedBookService;
    private UserInfoManager userInfoManager;
    private STFService stfService;

    @PostMapping("/addBook")
    public ResponseEntity<Response> addBook(Authentication authentication,
                                            @RequestBody AddBookRequest request){
        Response response = new Response();

        if (authentication == null) {
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUser().getId();

        if (bookService.findBookByBookName(request.getBookName()) != null){
            response.setCode(ResponseCode.BOOK_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        Book book = new Book(request);
        book.setAuthor(userId);

        ArrayList<String> categoriesName = request.getBookCategory();
        List<Category> categories = categoryService.findCategoriesByNameList(categoriesName);
        List<String> categoriesId = new ArrayList<>();
        for (Category category: categories){
            categoriesId.add(category.getId());
        }
        book.setBookCategory(categoriesId);
        book.setStar(0);
        book.setTotalChapter(0);
        book.setTotalPurchased(0);
        book.setTotalReview(0);
        Long currentTime = System.currentTimeMillis();
        book.setRequestTime(currentTime);
        book.setAdminUp(USER_ROLE.ADMIN == userDetails.getUser().getRole());
        if (book.isAdminUp()){
            book.setStatus(BOOK_STATUS.ACCEPTED);
            book.setUploadTime(currentTime);
        } else {
            book.setStatus(BOOK_STATUS.REQUEST);
        }
        book.setLastUpdateTime(currentTime);

        bookService.insertBook(book);
        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("reactBookRequestUp")
    @Secured("ADMIN")
    public ResponseEntity<Response> updateBookImage(@RequestParam String bookId,
                                                    @RequestParam int action){
        Response response = new Response();
        Book book = bookService.findBookById(bookId);
        if (book == null){
            response.setCode(ResponseCode.BOOK_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        String mess;

        if (action == REACT_UP_BOOK_REQUEST.ACCEPT){
            bookService.updateBookStatus(bookId, BOOK_STATUS.ACCEPTED);
            mess = Utils.messSuccessUploadBook(book.getBookName());
            Long time = System.currentTimeMillis();
            PurchasedBook purchasedBook =
                    new PurchasedBook(bookId, book.getAuthor(), book.getBookName(), 0, time, 0, time, true);
            purchasedBookService.insertPuchasedBook(purchasedBook);
        } else {
            bookService.updateBookStatus(bookId, BOOK_STATUS.REJECTED);
            mess = Utils.messRejectUploadBook(book.getBookName());
        }

        Notification notification = new Notification
                (book.getAuthor(), bookId, mess, false, System.currentTimeMillis());
        notificationService.insertNotification(notification);

        List<Device> devices = deviceService.getDevicesByUserId(book.getAuthor());
        if (devices != null && !devices.isEmpty()) {
            devices.forEach(device -> {
                NotificationMessage message = new NotificationMessage(device.getDeviceToken(), BOOK_REQUEST_UP_TITLE, mess);
                firebaseMessagingService.sendNotificationByToken(message);
            });
        }
        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getBookUp")
    public ResponseEntity<Response> getBookByAuthor(Authentication authentication){
        Response response = new Response();
        if (authentication == null) {
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUser().getId();

        HashMap<String, String> mapCategory = new HashMap<>();
        List<Category> listCategory = categoryService.findAll();
        for (Category category: listCategory){
            mapCategory.put(category.getId(), category.getCategoryName());
        }

        List<Book> books = bookService.findBooksUpByAuthor(userId);

        for (Book book:books){
            List<String> bookCategoryIdList = book.getBookCategory();
            List<String> bookNameList = new ArrayList<>();

            for (String categoryId: bookCategoryIdList){
                bookNameList.add(mapCategory.get(categoryId));
            }

            book.setBookCategory(bookNameList);
        }
        response.setCode(ResponseCode.SUCCESS);
        response.setData(books);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("getRequestUploadBook")
    public ResponseEntity<Response> getRequestUploadBook(Authentication authentication,
                                                         @RequestParam int status){
        Response response = new Response();

        if (authentication == null) {
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUser().getId();

        HashMap<String, String> mapCategory = new HashMap<>();
        List<Category> listCategory = categoryService.findAll();

        for (Category category: listCategory){
            mapCategory.put(category.getId(), category.getCategoryName());
        }

        if (userService.getUserByUserId(userId) == null){
            response.setCode(106);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        List<Book> bookRequestUpList = bookService.findBooksByAuthorAndStatus(userId, status);
        if (status == BOOK_STATUS.ACCEPTED){
            List<Book> completedBook = bookService.findBooksByAuthorAndStatus(userId, BOOK_STATUS.COMPLETED);
            bookRequestUpList.addAll(completedBook);
        }

        for (Book bookRequestUp:bookRequestUpList){
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

    @GetMapping("/searchBook")
    public ResponseEntity<Response> searchBook(@RequestParam String text){
        Response response = new Response();
        HashMap<String, String> mapCategory = new HashMap<>();
        List<Category> listCategory = categoryService.findAll();
        for (Category category: listCategory){
            mapCategory.put(category.getId(), category.getCategoryName());
        }
        List<Book> bookList = bookService.findBookByText(text);
        for (Book book:bookList){
            List<String> bookCategoryIdList = book.getBookCategory();
            List<String> bookNameList = new ArrayList<>();

            for (String categoryId: bookCategoryIdList){
                bookNameList.add(mapCategory.get(categoryId));
            }

            book.setBookCategory(bookNameList);
        }
        response.setCode(ResponseCode.SUCCESS);
        response.setData(bookList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("getAllRequestUploadBook")
    @Secured("ADMIN")
    public ResponseEntity<Response> getAllRequestUploadBook(){
        Response response = new Response();
        HashMap<String, String> mapCategory = new HashMap<>();
        List<Category> listCategory = categoryService.findAll();

        for (Category category: listCategory){
            mapCategory.put(category.getId(), category.getCategoryName());
        }

        List<Book> bookRequestUpList = bookService.getBookByStatus(BOOK_STATUS.REQUEST);

        for (Book bookRequestUp:bookRequestUpList){
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

    @GetMapping("getBookByBookName")
    public ResponseEntity<Response> getBookByBookName(@RequestParam String bookName){
        Response response = new Response();
        Book book = bookService.findBookByBookName(bookName);

        if (book == null){
            response.setCode(ResponseCode.BOOK_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        List<Category> categories = categoryService.findCategoriesByIdList(book.getBookCategory());
        List<String> categoriesName = new ArrayList<>();
        for (Category category: categories){
            categoriesName.add(category.getCategoryName());
        }
        book.setBookCategory(categoriesName);
        response.setCode(ResponseCode.SUCCESS);
        response.setData(book);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getBookByAuthor")
    public ResponseEntity<Response> getBookByAuthor(@RequestParam String author,
                                                    @RequestParam String bookId){
        Response response = new Response();

        HashMap<String, String> mapCategory = new HashMap<>();
        List<Category> listCategory = categoryService.findAll();
        for (Category category: listCategory){
            mapCategory.put(category.getId(), category.getCategoryName());
        }
        List<Book> bookList = bookService.getBooksByAuthorAndIdIsNot(author, bookId);
        for (Book book:bookList){
            List<String> bookCategoryIdList = book.getBookCategory();
            List<String> bookNameList = new ArrayList<>();

            for (String categoryId: bookCategoryIdList){
                bookNameList.add(mapCategory.get(categoryId));
            }

            book.setBookCategory(bookNameList);
        }
        response.setCode(ResponseCode.SUCCESS);
        response.setData(bookList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("getABook")
    public ResponseEntity<Response> getBookById(@RequestParam String bookId){
        Response response = new Response();
        Book book = bookService.findBookById(bookId);

        if (book == null){
            response.setCode(109);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        List<String> categoriesName = categoryService.findCategoriesByIdList(book.getBookCategory())
                .stream()
                .map(Category::getCategoryName)
                .collect(Collectors.toList());

        book.setBookCategory(categoriesName);
        response.setCode(ResponseCode.SUCCESS);
        response.setData(book);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }    @GetMapping("/getBookHome")
    public ResponseEntity<Response> getBookHome(@RequestParam int typeFilter,
                                               @RequestParam int limit){
        Response response = new Response();

        HashMap<String, String> mapCategory = new HashMap<>();
        List<Category> listCategory = categoryService.findAll();
        for (Category category: listCategory){
            mapCategory.put(category.getId(), category.getCategoryName());
        }

        List<Book> books = bookService.getBooksHome(typeFilter, limit);
        if (books.isEmpty()){
            response.setCode(ResponseCode.SUCCESS);
            response.setData(Collections.emptyList());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        Map<String, String> userAvatarMap = stfService.getBookImagePathMap(books);

        books.forEach(book -> {
            List<String> bookCategoryIdList = book.getBookCategory();
            List<String> bookNameList = bookCategoryIdList.stream()
                    .map(mapCategory::get)
                    .collect(Collectors.toList());
            book.setBookCategory(bookNameList);

            if (userAvatarMap.containsKey(book.getId())){
                book.setBookImage(userAvatarMap.get(book.getId()));
            }
        });

        response.setCode(ResponseCode.SUCCESS);
        response.setData(books);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/getBookFilter")
    public ResponseEntity<Response> getBookFilter(@RequestBody GetBookFilterRequest request) {
        Response response = new Response();
        List<Category> listCategory = categoryService.findAll();
        Map<String, String> mapCategory = listCategory.stream()
                .collect(Collectors.toMap(Category::getId, Category::getCategoryName));

        List<Book> books = bookService.searchBookFilter(
                request.getSort(),
                request.getOrder(),
                request.getStatus(),
                request.getPost(),
                request.getCategory(),
                request.getPage()
        );

        if (books.isEmpty()){
            response.setCode(ResponseCode.SUCCESS);
            response.setData(Collections.emptyList());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        List<String> userIds = books.stream().map(Book::getAuthor).toList();
        Map<String, String> userNameMap = userInfoManager.getUserNameMap(userIds);
        Map<String, String> userAvatarMap = stfService.getBookImagePathMap(books);

        books.forEach(book -> {
            List<String> bookCategoryIdList = book.getBookCategory();
            List<String> bookNameList = bookCategoryIdList.stream()
                    .map(mapCategory::get)
                    .collect(Collectors.toList());
            book.setBookCategory(bookNameList);

            if (userNameMap.containsKey(book.getAuthor())){
                book.setAuthor(userNameMap.get(book.getAuthor()));
            }

            if (userAvatarMap.containsKey(book.getId())){
                book.setBookImage(userAvatarMap.get(book.getId()));
            }
        });

        response.setCode(ResponseCode.SUCCESS);
        response.setData(books);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/updateBookInfo")
    public ResponseEntity<Response> updateBookInfo(Authentication authentication,
                                                   @RequestBody UpdateBookRequest request) {
        Response response = new Response();

        if (authentication == null) {
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUser().getId();
        
        Book book = bookService.findBookById(request.getBookId());
        if (Objects.isNull(book) || !book.getAuthor().equals(userId)){
            response.setCode(ResponseCode.BOOK_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        Book newBook = new Book(request);

        ArrayList<String> categoriesName = request.getBookCategory();
        if (!Objects.isNull(categoriesName) && !categoriesName.isEmpty()){
            List<Category> categories = categoryService.findCategoriesByNameList(categoriesName);
            List<String> categoriesId = new ArrayList<>();
            for (Category category: categories){
                categoriesId.add(category.getId());
            }
            newBook.setBookCategory(categoriesId);
        }

        bookService.updateBookInfo(newBook, request.getIsFinish());

        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
