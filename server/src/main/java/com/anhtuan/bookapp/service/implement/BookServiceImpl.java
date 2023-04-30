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
        return bookRepository.findBookByBookName(bookName);
    }

    @Override
    public Book findBookById(String bookId) {
        return bookRepository.findBookById(bookId);
    }

    @Override
    public List<Book> findBooksByUserPost(String userId) {
        return bookRepository.findBooksByUserPost(userId);
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
    public List<Book> findBooksByAuthorAndBookNameIsNot(String author, String bookName) {
        return bookRepository.findBooksByAuthorAndBookNameIsNot(author, bookName);
    }

    @Override
    public List<Book> getBooksByAuthorAndIdIsNot(String author, String bookId) {
        return bookRepository.findBooksByAuthorAndIdIsNot(author, bookId);
    }

    @Override
    public List<Book> getBooksByIdList(List<String> idList) {
        return bookRepository.findBooksByIdIn(idList);
    }

    @Override
    public void updateTotalChapterById(String bookId, int totalChapter) {
        bookRepository.updateTotalChapterById(bookId, totalChapter);
    }

    @Override
    public void updateStarById(String id, double star) {
        bookRepository.updateStarById(id, star);
    }

    @Override
    public List<Book> getNewBookList() {
        return bookRepository.findBooksByOrderByUploadTimeDesc();
    }

    @Override
    public List<Book> getRecommendBookList() {
        return bookRepository.findTop6ByOrderByStarDesc();
    }

    @Override
    public List<Book> getTop8NewBookList() {
        return bookRepository.findTop8ByOrderByUploadTimeDesc();
    }

    @Override
    public List<Book> getTop6RecommendBookList() {
        return bookRepository.findTop6ByOrderByStarDesc();
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
    public List<Book> getMostBuyBookList() {
        return bookRepository.findBooksByOrderByTotalPurchasedDesc();
    }

    @Override
    public List<Book> getMostReviewBookList() {
        return bookRepository.findBooksByOrderByTotalReviewDesc();
    }

    @Override
    public List<Book> getTop6MostBuyBookList() {
        return bookRepository.findTop6ByOrderByTotalPurchasedDesc();
    }

    @Override
    public List<Book> getTop6MostReviewBookList() {
        return bookRepository.findTop6ByOrderByTotalReviewDesc();
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
}
