package com.anhtuan.bookapp.controller;

import static com.anhtuan.bookapp.config.Constant.*;

import com.anhtuan.bookapp.common.ResponseCode;
import com.anhtuan.bookapp.domain.Book;
import com.anhtuan.bookapp.domain.Category;
import com.anhtuan.bookapp.request.AddBookRequest;
import com.anhtuan.bookapp.request.GetBookFilterRequest;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.BookService;
import com.anhtuan.bookapp.service.base.CategoryService;
import com.anhtuan.bookapp.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("book")
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    private UserService userService;

    private CategoryService categoryService;

    @PostMapping("/addBook")
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
        Book book = new Book(request);
        ArrayList<String> categoriesName = request.getBookCategory();
        List<Category> categories = categoryService.findCategoriesByNameList(categoriesName);
        List<String> categoriesId = new ArrayList<>();
        for (Category category: categories){
            categoriesId.add(category.getId());
        }
        book.setBookCategory(categoriesId);
        book.setStar(5);
        book.setTotalChapter(0);
        book.setTotalPurchased(0);
        book.setTotalReview(0);
        book.setUploadTime(System.currentTimeMillis());
        book.setLastUpdateTime(System.currentTimeMillis());
        if (request.getUserPost().equals(ADMIN_ID)){
            book.setAdminUp(true);
        }
        bookService.insertBook(book);
        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    @GetMapping("/getBook")
    public ResponseEntity<Response> getBookByUserPost(@RequestParam String userId){
        Response response = new Response();
        if (userService.getUserByUserId(userId) == null){
            response.setCode(106);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        HashMap<String, String> mapCategory = new HashMap<>();
        List<Category> listCategory = categoryService.findAll();
        for (Category category: listCategory){
            mapCategory.put(category.getId(), category.getCategoryName());
        }

        List<Book> books = bookService.findBooksByUserPost(userId);

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

    @GetMapping("getBookByBookName")
    public ResponseEntity<Response> getBookByBookName(@RequestParam String bookName){
        Response response = new Response();
        Book book = bookService.findBookByBookName(bookName);

        if (book == null){
            response.setCode(109);
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

    @GetMapping("/getBookHome")
    public ResponseEntity<Response> getBookHome(@RequestParam int typeFilter,
                                               @RequestParam boolean limit){
        Response response = new Response();

        HashMap<String, String> mapCategory = new HashMap<>();
        List<Category> listCategory = categoryService.findAll();
        for (Category category: listCategory){
            mapCategory.put(category.getId(), category.getCategoryName());
        }

        List<Book> books = new ArrayList<>();
        if (limit && typeFilter == TYPE_FILTER.NEW_BOOK){
            books = bookService.getTop8NewBookList();
        }
        if (!limit && typeFilter == TYPE_FILTER.NEW_BOOK){
            books = bookService.getNewBookList();
        }
        if (limit && typeFilter == TYPE_FILTER.RECOMMEND_BOOK){
            books = bookService.getTop6RecommendBookList();
        }
        if (!limit && typeFilter == TYPE_FILTER.RECOMMEND_BOOK){
            books = bookService.getRecommendBookList();
        }
        if (limit && typeFilter == TYPE_FILTER.MOST_BUY){
            books = bookService.getTop6MostBuyBookList();
        }
        if (!limit && typeFilter == TYPE_FILTER.MOST_BUY){
            books = bookService.getMostBuyBookList();
        }
        if (limit && typeFilter == TYPE_FILTER.MOST_REVIEW){
            books = bookService.getTop6MostReviewBookList();
        }
        if (!limit && typeFilter == TYPE_FILTER.MOST_REVIEW){
            books = bookService.getMostReviewBookList();
        }


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

    @PostMapping("/getBookFilter")
    public ResponseEntity<Response> getBookFilter(@RequestBody GetBookFilterRequest request){
        Response response = new Response();
        HashMap<String, String> mapCategory = new HashMap<>();
        List<Category> listCategory = categoryService.findAll();
        for (Category category: listCategory){
            mapCategory.put(category.getId(), category.getCategoryName());
        }

        List<Book> books = bookService.searchBookFilter
                (request.getSort(), request.getOrder(), request.getStatus(), request.getPost(), request.getCategory(), request.getPage());

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

}
