package com.anhtuan.bookapp.repository.customize;

import com.anhtuan.bookapp.domain.BannedWord;

import java.util.List;

public interface BannedWordCustomizeRepository {
    void updateBannedWord(int version , List<String> words);

    void insertBannedWord(BannedWord bannedWord);

    BannedWord findOne();
}
