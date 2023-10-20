package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.config.Constant;
import com.anhtuan.bookapp.domain.Book;
import com.anhtuan.bookapp.repository.base.BookRepository;
import com.anhtuan.bookapp.service.base.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void insertBook(Book book) {
        bookRepository.insert(book);
    }

    @Override
    public Book findBookByBookName(String bookName) {
        return bookRepository.findBookByBookNameAndStatusGreaterThanEqual(bookName, Constant.BOOK_STATUS.REQUEST);
    }

    @Override
    public Book findBookById(String bookId) {
        return bookRepository.findBookById(bookId);
    }

    @Override
    public List<Book> findBooksByAuthorAndStatus(String userId, Integer status) {
        return bookRepository.findBooksByAuthorAndStatus(userId, status);
    }

    @Override
    public List<Book> findBooksUpByAuthor(String userId) {
        return bookRepository.findBooksByAuthorAndStatusGreaterThanEqual(userId, Constant.BOOK_STATUS.ACCEPTED);
    }

    @Override
    public void updateBookImageByBookId(String bookId, String bookImage) {
        bookRepository.updateBookImageByBookId(bookId, bookImage);
    }

    @Override
    public List<Book> findBookByText(String text) {
        return bookRepository.findBookByText(text);
    }

    @Override
    public List<Book> getBooksByAuthorAndIdIsNot(String author, String bookId) {
        return bookRepository.findBooksByAuthorAndStatusGreaterThanEqualAndIdIsNot(author, Constant.BOOK_STATUS.ACCEPTED, bookId);
    }

    @Override
    public List<Book> getBooksByIdList(List<String> idList) {
        return bookRepository.findBooksByIdInAndStatusGreaterThanEqual(idList, Constant.BOOK_STATUS.ACCEPTED);
    }

    @Override
    public void increaseTotalChapter(String bookId) {
        bookRepository.increaseTotalChapter(bookId);
    }

    @Override
    public void updateStarById(String id, double star) {
        bookRepository.updateStarById(id, star);
    }


    @Override
    public void updateTotalPurchasedById(String bookId, int totalPurchased) {
        bookRepository.updateTotalPurchasedById(bookId, totalPurchased);
    }

    @Override
    public void updateTotalReviewById(String bookId, int totalReview) {
        bookRepository.updateTotalReviewById(bookId, totalReview);
    }

    @Override
    public List<Book> searchBookFilter(int sort, int order, int status, int post, List<String> category, int page) {
        String sortObj;
        switch (sort){
            case 0:
                sortObj = "all";
                break;
            case Constant.FILTER_SORT.SORT_BY_TIME:
                sortObj = Book.UPLOAD_TIME;
                break;
            case Constant.FILTER_SORT.SORT_BY_PURCHASED:
                sortObj = Book.TOTAL_PURCHASED;
                break;
            case Constant.FILTER_SORT.SORT_BY_REVIEW:
                sortObj = Book.TOTAL_REVIEW;
                break;
            case Constant.FILTER_SORT.SORT_BY_STAR:
                sortObj = Book.STAR;
                break;
            case Constant.FILTER_SORT.SORT_BY_CHAPTER:
                sortObj = Book.TOTAL_CHAPTER;
                break;
            case Constant.FILTER_SORT.SORT_BY_PRICE:
                sortObj = Book.BOOK_PRICE;
                break;
            default:
                sortObj = "all";
                break;
        }
        return bookRepository.searchBookFilter(sortObj, order, status, post, category, page);
    }

    @Override
    public List<Book> getBooksHome(int type, int limit) {
        return bookRepository.findBookHome(type, limit);
    }
}
