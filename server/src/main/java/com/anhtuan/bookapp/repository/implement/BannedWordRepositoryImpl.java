package com.anhtuan.bookapp.repository.implement;

import com.anhtuan.bookapp.domain.BannedWord;
import com.anhtuan.bookapp.repository.customize.BannedWordCustomizeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class BannedWordRepositoryImpl implements BannedWordCustomizeRepository {

    private MongoTemplate mongoTemplate;

    @Override
    public void updateBannedWord(int version, List<String> words) {
        Query query = new Query();
        query.addCriteria(Criteria.where(BannedWord.VERSION).is(version));
        Update update = new Update();
        update.set(BannedWord.VERSION, version + 1);
        update.set(BannedWord.WORDS, words);
        mongoTemplate.updateFirst(query, update, BannedWord.class);

    }

    @Override
    public void pushBannedWord(int version, String word) {
        Query query = new Query();
        query.addCriteria(Criteria.where(BannedWord.VERSION).is(version));
        Update update = new Update();
        update.set(BannedWord.VERSION, version + 1);
        update.push(BannedWord.WORDS, word);
        mongoTemplate.updateFirst(query, update, BannedWord.class);
    }

    @Override
    public void pullBannedWord(int version, String word) {
        Query query = new Query();
        query.addCriteria(Criteria.where(BannedWord.VERSION).is(version));
        Update update = new Update();
        update.set(BannedWord.VERSION, version + 1);
        update.pull(BannedWord.WORDS, word);
        mongoTemplate.updateFirst(query, update, BannedWord.class);
    }


    @Override
    public void insertBannedWord(BannedWord bannedWord) {
        mongoTemplate.insert(bannedWord);
    }

    @Override
    public BannedWord findOne() {
        Query query = new Query();
        return mongoTemplate.findOne(query, BannedWord.class);
    }
}
