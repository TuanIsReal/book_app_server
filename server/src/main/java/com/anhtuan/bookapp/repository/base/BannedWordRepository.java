package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.BannedWord;
import com.anhtuan.bookapp.repository.customize.BannedWordCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BannedWordRepository extends MongoRepository<BannedWord, String>, BannedWordCustomizeRepository {
}
