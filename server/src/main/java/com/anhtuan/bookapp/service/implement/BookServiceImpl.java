package com.anhtuan.bookapp.service.implement;

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
        return bookRepository.findTop8ByOrderByUploadTimeDesc();
    }

    @Override
    public List<Book> getRecommendBookList() {
        return bookRepository.findTop6ByOrderByStarDesc();
    }
}
