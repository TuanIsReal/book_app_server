package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.User;
import com.anhtuan.bookapp.repository.customize.UserCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String>, UserCustomizeRepository {

    List<User> findUserById(String userId);

    User findUserByEmail(String email);

    List<User> findUserByLastLoginIp(String ip);

    User findUserByIdAndPassword(String id, String password);

    User findByEmailAndIsGoogleLogin(String email,boolean isGoogleLogin);

}
