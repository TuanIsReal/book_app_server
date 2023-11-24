package com.anhtuan.bookapp.repository.implement;

import com.anhtuan.bookapp.config.Constant;
import com.anhtuan.bookapp.domain.User;
import com.anhtuan.bookapp.repository.customize.UserCustomizeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserCustomizeRepository {

    private MongoTemplate mongoTemplate;

    @Override
    public User insertUser(User user) {
        return mongoTemplate.insert(user);
    }

    @Override
    public void updateUserStatus(String userId, Integer status) {
        Query query = new Query();
        query.addCriteria(Criteria.where(User.USER_ID).is(userId));
        Update update = new Update();
        update.set(User.STATUS, status);
        mongoTemplate.updateFirst(query, update, User.class);
    }

    @Override
    public void updateUserIpAndLoggedStats(String userId, String ip) {
        Query query = new Query();
        query.addCriteria(Criteria.where(User.USER_ID).is(userId));
        Update update = new Update();
        update.set(User.LAST_LOGIN_IP, ip);
        update.set(User.STATUS, Constant.USER_STATUS.LOGIN);
        mongoTemplate.updateFirst(query, update, User.class);
    }

    @Override
    public void updatePointByUserId(String userId, int point) {
        Query query = new Query();
        query.addCriteria(Criteria.where(User.USER_ID).is(userId));
        Update update = new Update();
        update.inc(User.POINT, point);
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
    public User findByEmailAndNotLoginGoogle(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where(User.EMAIL).is(email).and(User.IS_GOOGLE_LOGIN).ne(true));
        return mongoTemplate.findOne(query, User.class);
    }

    @Override
    public List<User> findUserByText(String text) {
        Query query = new Query();
        Criteria nameCriteria = Criteria.where(User.NAME).regex(text, "i");
        Criteria emailCriteria = Criteria.where(User.EMAIL).is(text);
        Criteria userIdCriteria = Criteria.where(User.USER_ID).is(text);
        query.addCriteria(new Criteria().orOperator(nameCriteria, emailCriteria, userIdCriteria));
        return mongoTemplate.find(query, User.class);
    }
}
