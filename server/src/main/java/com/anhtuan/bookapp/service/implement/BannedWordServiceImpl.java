package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.domain.BannedWord;
import com.anhtuan.bookapp.repository.base.BannedWordRepository;
import com.anhtuan.bookapp.service.base.BannedWordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BannedWordServiceImpl implements BannedWordService {
    private BannedWordRepository bannedWordRepository;


    @Override
    public void updateBannedWord(int version, List<String> words) {
        bannedWordRepository.updateBannedWord(version, words);
    }

    @Override
    public void createBannedWord(BannedWord bannedWord) {
        bannedWordRepository.insertBannedWord(bannedWord);
    }

    @Override
    public BannedWord findOne() {
        return bannedWordRepository.findOne();
    }

}
