package com.anhtuan.bookapp.controller;

import com.anhtuan.bookapp.config.Constant;
import com.anhtuan.bookapp.domain.Book;
import com.anhtuan.bookapp.domain.BookRequestUp;
import com.anhtuan.bookapp.domain.Category;
import com.anhtuan.bookapp.domain.PurchasedBook;
import com.anhtuan.bookapp.request.AddBookRequest;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

        if (bookRequestUpService.getBookRequestUpByBookNameAndStatus(request.getBookName(), Constant.StatusBookRequestUp.REQUEST) != null){
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
        book.setStatus(Constant.StatusBookRequestUp.REQUEST);
        book.setRequestTime(System.currentTimeMillis());
        bookRequestUpService.addBookRequestUp(book);
        response.setCode(100);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/updateBookRequestUpImage")
    public ResponseEntity<Response> updateBookImage(@RequestParam String bookName,
                                                    @RequestParam("image") MultipartFile image){
        Response response = new Response();
        BookRequestUp book = bookRequestUpService.getBookRequestUpByBookNameAndStatus(bookName, Constant.StatusBookRequestUp.REQUEST);
        if (book == null){
            response.setCode(115);
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
            bookRequestUpService.updateBookImageById(bookId, bookId);
            response.setCode(100);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        BookRequestUp bookRequestUp = bookRequestUpService.getBookRequestUp(bookId, Constant.StatusBookRequestUp.REQUEST);
        if (bookRequestUp == null){
            response.setCode(115);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }


        if (action == Constant.ReactUpBookRequest.ACCEPT){
            bookRequestUpService.updateStatusById(bookId, Constant.StatusBookRequestUp.ACCEPTED);
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
        } else {
            bookRequestUpService.updateStatusById(bookId, Constant.StatusBookRequestUp.REJECTED);
        }

        response.setCode(100);
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

        response.setCode(100);
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
                .getBookRequestUpsByStatus(Constant.StatusBookRequestUp.REQUEST);

        for (BookRequestUp bookRequestUp:bookRequestUpList){
            List<String> bookCategoryIdList = bookRequestUp.getBookCategory();
            List<String> bookNameList = new ArrayList<>();

            for (String categoryId: bookCategoryIdList){
                bookNameList.add(mapCategory.get(categoryId));
            }

            bookRequestUp.setBookCategory(bookNameList);
        }

        response.setCode(100);
        response.setData(bookRequestUpList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/getBookRequestUpImage", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getBookRequestUpImage(@RequestParam String imageName) throws Exception{
        String filePath = Constant.BOOK_IMAGE_STORAGE_PATH + imageName + Constant.PNG;
        File file = new File(filePath);
        BufferedImage image = ImageIO.read(file);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        byte[] bytes = outputStream.toByteArray();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytes);
    }

    @GetMapping("getQuantityPurchased")
    public ResponseEntity<Response> getQuantityPurchased(@RequestParam String bookId,
                                                         @RequestParam String userId){
        Response response = new Response();
        int quantityPurchased;
        quantityPurchased = purchasedBookService.countPurchasedBooksByBookIdAndUserIdIsNot(bookId, userId);
        response.setCode(100);
        response.setData(quantityPurchased);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
