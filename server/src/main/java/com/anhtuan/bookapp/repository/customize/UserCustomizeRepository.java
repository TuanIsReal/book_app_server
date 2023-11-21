package com.anhtuan.bookapp.repository.customize;

import com.anhtuan.bookapp.domain.User;

import java.util.List;

public interface UserCustomizeRepository {
    User insertUser(User user);

    void updateUserStatus(String userId, Integer status);

    void updateUserIpAndLoggedStats(String userId, String ip);

    void updatePointByUserId(String userId, int point);

    void updateAvatarImageByUserId(String userId, String avatarImage);

    void updatePasswordByUserId(String userId, String password);

    void updateNameByUserId(String userId, String name);

    User findByEmailAndNotLoginGoogle(String email);

    List<User> findUserByText(String text);
}
