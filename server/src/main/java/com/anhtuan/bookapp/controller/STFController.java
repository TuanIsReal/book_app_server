package com.anhtuan.bookapp.controller;

import com.anhtuan.bookapp.config.Constant;
import com.anhtuan.bookapp.domain.Book;
import com.anhtuan.bookapp.domain.BookRequestUp;
import com.anhtuan.bookapp.domain.User;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.BookRequestUpService;
import com.anhtuan.bookapp.service.base.BookService;
import com.anhtuan.bookapp.service.base.STFService;
import com.anhtuan.bookapp.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.anhtuan.bookapp.config.Constant.*;
import static com.anhtuan.bookapp.config.Constant.JPG;

@RestController
@RequestMapping("image")
@AllArgsConstructor
public class STFController {

    private BookService bookService;
    private BookRequestUpService bookRequestUpService;
    private STFService STFService;
    private UserService userService;

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
            String filePath = BOOK_IMAGE_STORAGE_PATH + bookId + JPG;
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(fileData);
            fos.close();
            STFService.createThumbnailImage(bookId + JPG);
            bookService.updateBookImageByBookId(bookId, bookId);
            response.setCode(100);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/updateBookRequestUpImage")
    public ResponseEntity<Response> updateBookRequestUpImage(@RequestParam String bookName,
                                                    @RequestParam("image") MultipartFile image){
        Response response = new Response();
        BookRequestUp book = bookRequestUpService.getBookRequestUpByBookNameAndStatus(bookName, Constant.STATUS_BOOK_REQUEST_UP.REQUEST);
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
            String filePath = BOOK_IMAGE_STORAGE_PATH + bookId + JPG;
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(fileData);
            fos.close();
            STFService.createThumbnailImage(bookId + JPG);
            bookRequestUpService.updateBookImageById(bookId, bookId);
            response.setCode(100);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/updateAvatarImage")
    public ResponseEntity<Response> updateAvatarImage(@RequestParam String userId,
                                                      @RequestParam("image") MultipartFile image){
        Response response = new Response();
        User user = userService.getUserByUserId(userId);
        if (user == null){
            response.setCode(106);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        byte[] fileData = null;
        try {
            fileData = image.getBytes();
            if (fileData == null){
                response.setCode(108);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            String filePath = AVATAR_IMAGE_STORAGE_PATH + userId + JPG;
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(fileData);
            fos.close();
            userService.updateAvatarImageByUserId(userId, userId);
            response.setCode(100);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/getImage")
    public ResponseEntity<String> getBookImage(@RequestParam String imageName){
        String filePath = BOOK_IMAGE_STORAGE_PATH + imageName + Constant.JPG;
        return ResponseEntity.ok(filePath);
    }

    @GetMapping(value = "/getThumbnail")
    public ResponseEntity<String> getThumbnail(@RequestParam String thumbnailName){
        String filePath = BOOK_THUMBNAIL_STORAGE_PATH + thumbnailName + Constant.JPG;
        return ResponseEntity.ok(filePath);
    }

    @GetMapping(value = "/getAvatar")
    public ResponseEntity<String> getAvatar(@RequestParam String imageName){
        String filePath = AVATAR_IMAGE_STORAGE_PATH + imageName + Constant.JPG;
        return ResponseEntity.ok(filePath);
    }
}
