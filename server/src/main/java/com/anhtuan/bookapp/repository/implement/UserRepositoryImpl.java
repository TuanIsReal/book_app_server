package com.anhtuan.bookapp.repository.implement;

import com.anhtuan.bookapp.domain.User;
import com.anhtuan.bookapp.repository.customize.UserCustomizeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserCustomizeRepository {

    private MongoTemplate mongoTemplate;

    @Override
    public void insertUser(User user) {
        mongoTemplate.insert(user);
    }

    @Override
    public void updateUserLoggedStats(String userId, Boolean status) {
        Query query = new Query();
        query.addCriteria(Criteria.where(User.USER_ID).is(userId));
        Update update = new Update();
        update.set(User.IS_LOGGED, status);
        mongoTemplate.updateFirst(query, update, User.class);
    }

    @Override
    public void updateUserIpAndLoggedStats(String userId, String ip, Boolean status) {
        Query query = new Query();
        query.addCriteria(Criteria.where(User.USER_ID).is(userId));
        Update update = new Update();
        update.set(User.IS_LOGGED, status);
        update.set(User.LAST_LOGIN_IP, ip);
        mongoTemplate.updateFirst(query, update, User.class);
    }

    @Override
    public void updatePointByUserId(String userId, int point) {
        Query query = new Query();
        query.addCriteria(Criteria.where(User.USER_ID).is(userId));
        Update update = new Update();
        update.set(User.POINT, point);
        mongoTemplate.updateFirst(query, update, User.class);
    }

    @Override
    public void updateAvatarImageByUserId(String userId, String avtarImage) {
        Query query = new Query();
        query.addCriteria(Criteria.where(User.USER_ID).is(userId));
        Update update = new Update();
        update.set(User.AVATAR_IMAGE, avtarImage);
        mongoTemplate.updateFirst(query, update, User.class);
    }

    @Override
    public void updatePasswordByUserId(String userId, String password) {
        Query query = new Query();
        query.addCriteria(Criteria.where(User.USER_ID).is(userId));
        Update update = new Update();
        update.set(User.PASSWORD, password);
        mongoTemplate.updateFirst(query, update, User.class);
    }

    @Override
    public void updateNameByUserId(String userId, String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where(User.USER_ID).is(userId));
        Update update = new Update();
        update.set(User.NAME, name);
        mongoTemplate.updateFirst(query, update, User.class);
    }

    @Override
    public void updateIsVerifyByUserId(String userId, boolean isVerify) {
        Query query = new Query();
        query.addCriteria(Criteria.where(User.USER_ID).is(userId));
        Update update = new Update();
        update.set(User.IS_VERIFY, isVerify);
        mongoTemplate.updateFirst(query, update, User.class);
    }

    @Override
    public User findUserByEmailAndIsVerify(String email, boolean isVerify) {
        Query query = new Query();
        query.addCriteria(Criteria.where(User.EMAIL).is(email).and(User.IS_VERIFY).in(isVerify));
        return mongoTemplate.findOne(query, User.class);
    }

    @Override
    public User findByEmailAndPasswordAndIsVerify(String email, String password, boolean isVerify) {
        Query query = new Query();
        query.addCriteria(Criteria.where(User.EMAIL).is(email).and(User.PASSWORD).is(password).and(User.IS_VERIFY).in(isVerify));
        return mongoTemplate.findOne(query, User.class);
    }

    @Override
    public User findByEmailAndNotLoginGoogle(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where(User.EMAIL).is(email).and(User.IS_GOOGLE_LOGIN).ne(true));
        return mongoTemplate.findOne(query, User.class);
    }
}
