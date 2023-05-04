package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.User;

public interface UserService {

    User getUserByEmailAndPassword(String email, String password);

    User getUserByEmail(String email);

    void insertUser(User user);

    User getUserByUserId(String userId);

    void updateUserLoggedStatus(String userId, Boolean status);

    void updateUserIpAndLoggedStatus(String userId, String ip, Boolean status);

    User getUserByIp(String ip);

    void updatePointByUserId(String userId, int point);

    void updateAvatarImageByUserId(String userId, String avatarImage);

    void updatePasswordByUserId(String userId, String password);
    void updateNameByUserId(String userId, String name);
    User getUserByIdAndPassword(String id, String password);
    void updateIsVerifyByUserId(String userId, boolean isVerify);
    User getUserByEmailAndIsVerify(String email, boolean isVerify);
    User getByEmailAndPasswordAndIsVerify(String email, String password, boolean isVerify);
}
