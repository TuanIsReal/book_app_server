package com.anhtuan.bookapp.repository.customize;

import com.anhtuan.bookapp.domain.User;

import java.util.List;

public interface UserCustomizeRepository {
    void insertUser(User user);

    void updateUserLoggedStats(String userId, Boolean status);

    void updateUserIpAndLoggedStats(String userId, String ip, Boolean status);

    void updatePointByUserId(String userId, int point);

    void updateAvatarImageByUserId(String userId, String avatarImage);

    void updatePasswordByUserId(String userId, String password);

    void updateNameByUserId(String userId, String name);

    void updateIsVerifyByUserId(String userId, boolean isVerify);
    User findUserByEmailAndIsVerify(String email, boolean isVerify);
    User findByEmailAndPasswordAndIsVerify(String email, String password, boolean isVerify);
    User findByEmailAndNotLoginGoogle(String email);
}
