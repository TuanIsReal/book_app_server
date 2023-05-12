package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.domain.User;
import com.anhtuan.bookapp.repository.base.UserRepository;
import com.anhtuan.bookapp.service.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        List<User> userList = userRepository.findByEmailAndPassword(email, password);
        if (userList.size() != 0){
            return userList.get(0);
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        List<User> userList = userRepository.findUserByEmail(email);
        if (userList.size() != 0){
            return userList.get(0);
        }
        return null;
    }

    @Override
    public void insertUser(User user) {
        userRepository.insertUser(user);
    }

    @Override
    public User getUserByUserId(String userId) {
        return userRepository.findUserById(userId).get(0);
    }

    @Override
    public void updateUserLoggedStatus(String userId, Boolean status) {
        userRepository.updateUserLoggedStats(userId, status);
    }

    @Override
    public void updateUserIpAndLoggedStatus(String userId, String ip, Boolean status) {
        userRepository.updateUserIpAndLoggedStats(userId, ip, status);
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
    }

    @Override
    public void updateAvatarImageByUserId(String userId, String avatarImage) {
        userRepository.updateAvatarImageByUserId(userId, avatarImage);
    }

    @Override
    public void updatePasswordByUserId(String userId, String password) {
        userRepository.updatePasswordByUserId(userId, password);
    }

    @Override
    public void updateNameByUserId(String userId, String name) {
        userRepository.updateNameByUserId(userId, name);
    }

    @Override
    public User getUserByIdAndPassword(String id, String password) {
        return userRepository.findUserByIdAndPassword(id, password);
    }

    @Override
    public User findUserLoginGoolge(String email, boolean isGoogleLogin) {
        return userRepository.findByEmailAndIsGoogleLogin(email,isGoogleLogin);
    }


    @Override
    public User findByEmailAndNotLoginGoogle(String email) {
        return userRepository.findByEmailAndNotLoginGoogle(email);
    }
}
