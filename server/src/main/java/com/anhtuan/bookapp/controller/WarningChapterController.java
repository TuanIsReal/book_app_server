package com.anhtuan.bookapp.controller;

import com.anhtuan.bookapp.cache.UserInfoManager;
import com.anhtuan.bookapp.common.ResponseCode;
import com.anhtuan.bookapp.common.Utils;
import com.anhtuan.bookapp.config.Constant;
import com.anhtuan.bookapp.domain.*;
import com.anhtuan.bookapp.response.GetWarningListResponse;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.anhtuan.bookapp.config.Constant.WARNING_CHAPTER_TITLE;

@RestController
@RequestMapping("warning")
@AllArgsConstructor
public class WarningChapterController {

    private final WarningChapterService warningChapterService;
    private final BookChapterService bookChapterService;
    private final BookService bookService;
    private final DeviceService deviceService;
    private final NotificationService notificationService;
    private final FirebaseMessagingService firebaseMessagingService;
    private final UserInfoManager userInfoManager;

    @GetMapping("getWarningList")
    @Secured("ADMIN")
    public ResponseEntity<Response> getWarningList(){
        Response response = new Response();
        List<WarningChapter> warningChapterList = warningChapterService.findAll();
        List<String> chapterList = new ArrayList<>();

        warningChapterList.forEach(warningChapter -> {
            chapterList.add(warningChapter.getChapter());
            chapterList.add(warningChapter.getChapterReport());
        });

        List<BookChapter> bookChapterList = bookChapterService.findChapterByIds(chapterList);
        List<Book> bookList = bookService.findBookByBookIdList(bookChapterList.stream().map(BookChapter::getBookId).collect(Collectors.toList()));
        List<String> userIds = bookList.stream().map(Book::getAuthor).toList();
        Map<String, String> userNameMap = userInfoManager.getUserNameMap(userIds);
        Map<String, BookChapter> bookChapterMap = bookChapterList.stream().collect(Collectors.toMap(BookChapter::getId, bookChapter -> bookChapter));
        Map<String, Book> bookMap = bookList.stream().collect(Collectors.toMap(Book::getId, book -> book));

        List<GetWarningListResponse> getWarningListResponses = new ArrayList<>();

        warningChapterList.forEach(warningChapter -> {
           GetWarningListResponse getWarningListResponse = new GetWarningListResponse();
           BookChapter bookChapter = bookChapterMap.get(warningChapter.getChapter());
           BookChapter bookChapterReport = bookChapterMap.get(warningChapter.getChapterReport());
           bookMap.get(bookChapter.getBookId()).setAuthor(userNameMap.get(bookMap.get(bookChapter.getBookId()).getAuthor()));
           bookMap.get(bookChapterReport.getBookId()).setAuthor(userNameMap.get(bookMap.get(bookChapterReport.getBookId()).getAuthor()));
           getWarningListResponse.setChapter(bookChapter);
           getWarningListResponse.setChapterReport(bookChapterReport);
           getWarningListResponse.setBook(bookMap.get(bookChapter.getBookId()));
           getWarningListResponse.setBookReport(bookMap.get(bookChapterReport.getBookId()));
           getWarningListResponses.add(getWarningListResponse);
        });

        response.setCode(ResponseCode.SUCCESS);
        response.setData(getWarningListResponses);
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
