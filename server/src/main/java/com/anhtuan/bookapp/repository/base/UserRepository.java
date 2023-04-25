package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.User;
import com.anhtuan.bookapp.repository.customize.UserCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String>, UserCustomizeRepository {

    List<User> findByEmailAndPassword(String email, String password);

    List<User> findUserById(String userId);

    List<User> findUserByEmail(String email);

    List<User> findUserByLastLoginIp(String ip);

    User findUserByIdAndPassword(String id, String password);
}
