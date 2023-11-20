package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.cache.UserInfoManager;
import com.anhtuan.bookapp.domain.User;
import com.anhtuan.bookapp.repository.base.UserRepository;
import com.anhtuan.bookapp.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    private final UserInfoManager userInfoManager;

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User insertUser(User user) {
        return userRepository.insertUser(user);
    }

    @Override
    public User getUserByUserId(String userId) {
        return userInfoManager.getUserByUserId(userId);
    }

    @Override
    public void updateUserStatus(String userId, Integer status) {
        userRepository.updateUserStatus(userId, status);
        userInfoManager.updateUserStatus(userId, status);
    }

    @Override
    public void updateUserIpAndLoggedStatus(String userId, String ip) {
        userRepository.updateUserIpAndLoggedStats(userId, ip);
        userInfoManager.updateUserIp(userId, ip);
    }

    @Override
    public User getUserByIp(String ip) {
        List<User> userList = userRepository.findUserByLastLoginIp(ip);
        if (userList.size() == 0){
            return null;
        }
        return userRepository.findUserByLastLoginIp(ip).get(0);
    }

    @Override
    public void updatePointByUserId(String userId, int point) {
        userRepository.updatePointByUserId(userId, point);
        userInfoManager.updateUserPoint(userId, point);
    }

    @Override
    public void updateAvatarImageByUserId(String userId, String avatarImage) {
        userRepository.updateAvatarImageByUserId(userId, avatarImage);
        userInfoManager.updateUserAvatar(userId, avatarImage);
    }

    @Override
    public void updatePasswordByUserId(String userId, String password) {
        userRepository.updatePasswordByUserId(userId, password);
        userInfoManager.updateUserPassword(userId, password);
    }

    @Override
    public void updateNameByUserId(String userId, String name) {
        userRepository.updateNameByUserId(userId, name);
        userInfoManager.updateUserName(userId, name);
    }


    @Override
    public User findUserLoginGoolge(String email, boolean isGoogleLogin) {
        return userRepository.findByEmailAndIsGoogleLogin(email,isGoogleLogin);
    }


    @Override
    public User findByEmailAndNotLoginGoogle(String email) {
        return userRepository.findByEmailAndNotLoginGoogle(email);
    }

    @Override
    public List<User> findUserByText(String text) {
        return userRepository.findUserByText(text);
    }
}
