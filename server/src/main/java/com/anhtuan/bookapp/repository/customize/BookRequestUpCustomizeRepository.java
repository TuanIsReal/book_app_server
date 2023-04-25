package com.anhtuan.bookapp.repository.customize;

public interface BookRequestUpCustomizeRepository {
    void updateBookImageById(String id, String bookImage);
    void updateStatusById(String id, int status);
}
