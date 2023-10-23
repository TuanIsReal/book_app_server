package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.User;

public interface UserService {

    User getUserByEmail(String email);

    User insertUser(User user);

    User getUserByUserId(String userId);

    void updateUserStatus(String userId, Integer status);

    void updateUserIpAndLoggedStatus(String userId, String ip);

    User getUserByIp(String ip);

    void updatePointByUserId(String userId, int point);

    void updateAvatarImageByUserId(String userId, String avatarImage);

    void updatePasswordByUserId(String userId, String password);
    void updateNameByUserId(String userId, String name);
    User getUserByIdAndPassword(String id, String password);
    User findUserLoginGoolge(String email,boolean isGoogleLogin);
    User findByEmailAndNotLoginGoogle(String email);
}
