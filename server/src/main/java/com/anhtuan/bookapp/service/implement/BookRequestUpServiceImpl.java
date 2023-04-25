package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.domain.BookRequestUp;
import com.anhtuan.bookapp.repository.base.BookRequestUpRepository;
import com.anhtuan.bookapp.service.base.BookRequestUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookRequestUpServiceImpl implements BookRequestUpService {

    @Autowired
    BookRequestUpRepository bookRequestUpRepository;

    @Override
    public BookRequestUp getBookRequestUpByBookNameAndStatus(String bookName, int status) {
        return bookRequestUpRepository.findBookRequestUpByBookNameAndStatus(bookName, status);
    }

    @Override
    public void addBookRequestUp(BookRequestUp bookRequestUp) {
        bookRequestUpRepository.insert(bookRequestUp);
    }

    @Override
    public void updateBookImageById(String id, String bookImage) {
        bookRequestUpRepository.updateBookImageById(id, bookImage);
    }

    @Override
    public BookRequestUp getBookRequestUp(String id, int status) {
        return bookRequestUpRepository.findBookRequestUpByIdAndStatus(id, status);
    }

    @Override
    public void updateStatusById(String id, int status) {
        bookRequestUpRepository.updateStatusById(id, status);
    }

    @Override
    public void deleteBookRequestUpById(String id) {
        bookRequestUpRepository.deleteBookRequestUpById(id);
    }

    @Override
    public List<BookRequestUp> getBookRequestUpsByUserPostAndStatus(String userPost, int status) {
        return bookRequestUpRepository.findBookRequestUpsByUserPostAndStatus(userPost, status);
    }

    @Override
    public List<BookRequestUp> getBookRequestUpsByStatus(int status) {
        return bookRequestUpRepository.findBookRequestUpsByStatus(status);
    }
}
