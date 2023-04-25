package com.anhtuan.bookapp.controller;

import com.anhtuan.bookapp.config.Constant;
import com.anhtuan.bookapp.domain.Book;
import com.anhtuan.bookapp.domain.Category;
import com.anhtuan.bookapp.request.AddBookRequest;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.BookService;
import com.anhtuan.bookapp.service.base.CategoryService;
import com.anhtuan.bookapp.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
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
        book.setUploadTime(System.currentTimeMillis());
        book.setLastUpdateTime(System.currentTimeMillis());
        bookService.insertBook(book);
        response.setCode(100);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/updateBookImage")
    public ResponseEntity<Response> updateBookImage(@RequestParam String bookName,
                                                    @RequestParam("image") MultipartFile image){
        Response response = new Response();
        Book book = bookService.findBookByBookName(bookName);
        if (book == null){
            response.setCode(109);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        String bookId = book.getId();
        byte[] fileData = null;
        try {
            fileData = image.getBytes();
            if (fileData == null){
                response.setCode(108);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            String filePath = Constant.BOOK_IMAGE_STORAGE_PATH + bookId +Constant.PNG;
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(fileData);
            fos.close();
            bookService.updateBookImageByBookId(bookId, bookId);
            response.setCode(100);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        response.setCode(100);
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
        response.setCode(100);
        response.setData(bookList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/getBookImage", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBookImage(@RequestParam String imageName) throws Exception{
        String filePath = Constant.BOOK_IMAGE_STORAGE_PATH + imageName + Constant.PNG;
        File file = new File(filePath);
        BufferedImage image = ImageIO.read(file);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        byte[] bytes = outputStream.toByteArray();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytes);
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
        response.setCode(100);
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
        response.setCode(100);
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
        response.setCode(100);
        response.setData(book);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getNewBook")
    public ResponseEntity<Response> getNewBook(){
        Response response = new Response();

        HashMap<String, String> mapCategory = new HashMap<>();
        List<Category> listCategory = categoryService.findAll();
        for (Category category: listCategory){
            mapCategory.put(category.getId(), category.getCategoryName());
        }

        List<Book> books = bookService.getNewBookList();

        for (Book book:books){
            List<String> bookCategoryIdList = book.getBookCategory();
            List<String> bookNameList = new ArrayList<>();

            for (String categoryId: bookCategoryIdList){
                bookNameList.add(mapCategory.get(categoryId));
            }

            book.setBookCategory(bookNameList);
        }
        response.setCode(100);
        response.setData(books);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getRecommendBook")
    public ResponseEntity<Response> getRecommendBook(){
        Response response = new Response();

        HashMap<String, String> mapCategory = new HashMap<>();
        List<Category> listCategory = categoryService.findAll();
        for (Category category: listCategory){
            mapCategory.put(category.getId(), category.getCategoryName());
        }

        List<Book> books = bookService.getRecommendBookList();

        for (Book book:books){
            List<String> bookCategoryIdList = book.getBookCategory();
            List<String> bookNameList = new ArrayList<>();

            for (String categoryId: bookCategoryIdList){
                bookNameList.add(mapCategory.get(categoryId));
            }

            book.setBookCategory(bookNameList);
        }
        System.out.println(books.size());
        response.setCode(100);
        response.setData(books);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
