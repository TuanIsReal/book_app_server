package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.common.Utils;
import com.anhtuan.bookapp.config.Constant;
import com.anhtuan.bookapp.domain.*;
import com.anhtuan.bookapp.repository.base.BookChapterRepository;
import com.anhtuan.bookapp.service.base.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.anhtuan.bookapp.config.Constant.ADD_CHAPTER_NOTIFICATION_TITLE;

@Service
@AllArgsConstructor
public class BookChapterServiceImpl implements BookChapterService {

    private BookChapterRepository bookChapterRepository;
    private final BookService bookService;
    private final PurchasedBookService purchasedBookService;
    private final DeviceService deviceService;
    private final NotificationService notificationService;
    private final FirebaseMessagingService firebaseMessagingService;

    @Override
    public String insertBookChapter(BookChapter bookChapter) {
        String chapterId = bookChapterRepository.insert(bookChapter).getId();
        bookChapterRepository.updateChapterContent(chapterId, chapterId);
        return chapterId;
    }

    @Override
    public BookChapter findBookChapterByBookIdAndChapterNumber(String bookId, int chapterNumber) {
        return bookChapterRepository.findBookChapterByBookIdAndChapterNumber(bookId, chapterNumber);
    }

    @Override
    public List<BookChapter> getBookChaptersByBookId(String bookId) {
        return bookChapterRepository.findBookChaptersByBookIdAndStatus(bookId, Constant.BOOK_CHAPTER_STATUS.VERIFY);
    }

    @Override
    public List<BookChapter> findBookChaptersByBookIdAndChapterNumberGreaterThanOrderByChapterNumberAsc(String bookId, int chapterNumber) {
        return bookChapterRepository.findBookChaptersByBookIdAndStatusAndChapterNumberGreaterThanOrderByChapterNumberAsc(bookId, Constant.BOOK_CHAPTER_STATUS.VERIFY, chapterNumber);
    }

    @Override
    public List<BookChapter> findBookChaptersVerify(String bookId) {
        return bookChapterRepository.findBookChaptersByBookIdIsNotAndStatus(bookId, Constant.BOOK_CHAPTER_STATUS.VERIFY);
    }

    @Override
    public List<BookChapter> findBookChaptersNotVerify() {
        return bookChapterRepository.findBookChaptersByStatus(Constant.BOOK_CHAPTER_STATUS.NOT_VERIFY);
    }

    @Override
    public void updateStatus(String chapterId, int status) {
        bookChapterRepository.updateStatus(chapterId, status);
    }

    @Override
    public void deleteById(String id) {
        bookChapterRepository.deleteById(id);
    }

    @Override
    public BookChapter getBookChapter(String id) {
        return bookChapterRepository.findBookChapterById(id);
    }

    @Override
    public void actionUploadChapter(BookChapter chapter, Book book) {
        bookService.increaseTotalChapter(book.getId(), 1);

        List<PurchasedBook> purchasedBookList = purchasedBookService.findPurchasedBooksByBookIdAndUserIdIsNot(book.getId(), book.getAuthor());
        List<String> purchasedUserList = purchasedBookList.stream().map(PurchasedBook::getUserId).toList();

        String messBody = Utils.messBodyAddChapter(book.getBookName(), chapter.getChapterNumber(), chapter.getChapterName());
        List<Notification> notificationList = new ArrayList<>();

        List<Device> deviceList = deviceService.getDevicesByUserIdIsIn(purchasedUserList);
        for (Device device:deviceList){
            if (!device.getDeviceToken().isEmpty()){
                NotificationMessage message = new NotificationMessage(device.getDeviceToken(), ADD_CHAPTER_NOTIFICATION_TITLE, messBody);
                firebaseMessagingService.sendNotificationByToken(message);
            }
        }

        for (String userId:purchasedUserList){
            Notification notification = new Notification(userId, book.getId(), messBody, false, System.currentTimeMillis());
            notificationList.add(notification);
        }
        notificationService.insertNotificationList(notificationList);
    }

    @Override
    public List<BookChapter> findChapterByIds(List<String> ids) {
        return bookChapterRepository.findChapterByIds(ids);
    }


}
