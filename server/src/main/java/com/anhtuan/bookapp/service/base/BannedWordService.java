package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.BannedWord;

import java.util.List;

public interface BannedWordService {

    void updateBannedWord(int version, List<String> words);

    void createBannedWord(BannedWord bannedWord);

    BannedWord findOne();
}
