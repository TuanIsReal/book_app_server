package com.anhtuan.bookapp.controller;

import com.anhtuan.bookapp.common.ResponseCode;
import com.anhtuan.bookapp.config.Constant;
import com.anhtuan.bookapp.domain.Book;
import com.anhtuan.bookapp.domain.CustomUserDetails;
import com.anhtuan.bookapp.domain.User;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.BookService;
import com.anhtuan.bookapp.service.base.STFService;
import com.anhtuan.bookapp.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.anhtuan.bookapp.config.Constant.*;
import static com.anhtuan.bookapp.config.Constant.JPG;

@RestController
@RequestMapping("/stf")
@AllArgsConstructor
public class STFController {

    private BookService bookService;
    private STFService STFService;
    private UserService userService;

    @PostMapping("/updateBookImage")
    public ResponseEntity<Response> updateBookImage(@RequestParam String bookName,
                                                    @RequestParam("image") MultipartFile image){
        Response response = new Response();
        Book book = bookService.findBookByBookName(bookName);
        if (book == null){
            response.setCode(ResponseCode.BOOK_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        String bookId = book.getId();
        byte[] fileData = null;
        long time = System.currentTimeMillis();
        String filePath = BOOK_IMAGE_STORAGE_PATH + bookId + "-" + time + JPG;

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fileData = image.getBytes();
            if (fileData.length == 0){
                response.setCode(ResponseCode.UPLOAD_FILE_FAILED);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            fos.write(fileData);

            STFService.createThumbnail(BOOK_IMAGE_STORAGE_PATH, bookId + "-" + time + JPG);
            bookService.updateBookImageByBookId(bookId, bookId + "-" + time);
            response.setCode(ResponseCode.SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/updateAvatarImage")
    public ResponseEntity<Response> updateAvatarImage(Authentication authentication,
                                                      @RequestParam("image") MultipartFile image){
        Response response = new Response();
        if (authentication.getPrincipal() == null) {
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUser().getId();

        User user = userService.getUserByUserId(userId);
        if (user == null){
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        byte[] fileData = null;
        String filePath = AVATAR_IMAGE_STORAGE_PATH + userId + JPG;

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fileData = image.getBytes();
            if (fileData.length == 0){
                response.setCode(ResponseCode.UPLOAD_FILE_FAILED);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            fos.write(fileData);

            STFService.createThumbnail(AVATAR_IMAGE_STORAGE_PATH, userId + JPG);
            userService.updateAvatarImageByUserId(userId, userId);
            response.setCode(ResponseCode.SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/getBookImage")
    public ResponseEntity<Response> getBookImage(@RequestParam String imageName){
        Response response = new Response();
        String filePath = BOOK_IMAGE_STORAGE_PATH_RESPONSE + imageName + Constant.JPG;
        response.setCode(ResponseCode.SUCCESS);
        response.setData(filePath);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/getThumbnail")
    public ResponseEntity<Response> getThumbnail(@RequestParam String thumbnailName){
        Response response = new Response();
        String filePath = THUMBNAIL_STORAGE_PATH_RESPONSE + thumbnailName + Constant.JPG;
        response.setCode(ResponseCode.SUCCESS);
        response.setData(filePath);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/getAvatar")
    public ResponseEntity<Response> getAvatar(@RequestParam String imageName){
        Response response = new Response();
        String filePath = AVATAR_IMAGE_STORAGE_PATH_RESPONSE + imageName + Constant.JPG;
        response.setCode(ResponseCode.SUCCESS);
        response.setData(filePath);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/test")
    @Secured("USER")
    public ResponseEntity<Response> getAvatar(Authentication authentication){
        Response response = new Response();
        String email = "fail";
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            email = userDetails.getUsername();
        }
        response.setCode(ResponseCode.SUCCESS);
        response.setData(email);
        return ResponseEntity.ok(response);
    }
}
