package com.anhtuan.bookapp.controller;

import com.anhtuan.bookapp.common.ResponseCode;
import com.anhtuan.bookapp.common.Utils;
import com.anhtuan.bookapp.config.Constant;
import com.anhtuan.bookapp.domain.*;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static com.anhtuan.bookapp.config.Constant.WARNING_CHAPTER_TITLE;

@RestController
@RequestMapping("warning")
@AllArgsConstructor
public class WarningChapterController {

    private WarningChapterService warningChapterService;
    private BookChapterService bookChapterService;
    private BookService bookService;
    private DeviceService deviceService;
    private NotificationService notificationService;
    private FirebaseMessagingService firebaseMessagingService;

    @GetMapping("getWarningList")
    @Secured("ADMIN")
    public ResponseEntity<Response> getWarningList(){
        Response response = new Response();
        List<WarningChapter> warningChapterList = warningChapterService.findAll();
        response.setCode(ResponseCode.SUCCESS);
        response.setData(warningChapterList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("reactWarning")
    @Secured("ADMIN")
    public ResponseEntity<Response> reactWarning(@RequestParam String chapter,
                                                 @RequestParam int react){
        Response response = new Response();
        WarningChapter warningChapter = warningChapterService.findWarningChapter(chapter);

        if (warningChapter == null || (react != Constant.REACT_WARNING.GOOD && react != Constant.REACT_WARNING.NOT_GOOD) ){
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        BookChapter bookChapter = bookChapterService.getBookChapter(warningChapter.getChapter());

        if (bookChapter == null){
            response.setCode(ResponseCode.UNKNOWN_ERROR);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        Book book = bookService.findBookById(bookChapter.getBookId());

        if (Constant.REACT_WARNING.GOOD == react){
            warningChapterService.deleteByChapter(chapter);
            bookChapterService.updateStatus(chapter, Constant.BOOK_CHAPTER_STATUS.VERIFY);
            bookChapterService.actionUploadChapter(bookChapter, book);
            response.setCode(ResponseCode.SUCCESS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        warningChapterService.deleteByChapter(chapter);
        bookChapterService.deleteById(chapter);

        String mess = Utils.warningChapter(bookChapter.getChapterNumber(), book.getBookName());

        Notification notification = new Notification
                (book.getAuthor(), book.getId(), mess, false, System.currentTimeMillis());
        notificationService.insertNotification(notification);

        List<Device> devices = deviceService.getDevicesByUserId(book.getAuthor());
        if (devices != null && !devices.isEmpty()) {
            devices.forEach(device -> {
                NotificationMessage message = new NotificationMessage(device.getDeviceToken(), WARNING_CHAPTER_TITLE, mess);
                firebaseMessagingService.sendNotificationByToken(message);
            });
        }

        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
