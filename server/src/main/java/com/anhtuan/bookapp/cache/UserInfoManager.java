package com.anhtuan.bookapp.cache;

import com.anhtuan.bookapp.config.Constant;
import com.anhtuan.bookapp.domain.User;
import com.anhtuan.bookapp.repository.base.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserInfoManager {
    public static final Map<String, User> USER_MAP = new ConcurrentHashMap<>();

    private final UserRepository userRepository;

    public void initData(){
        userRepository.findAll().forEach(user -> USER_MAP.put(user.getId(), user));
    }

    public int getSize(){
        return USER_MAP.size();
    }

    public User getUserByUserId(String userId){
        User user = USER_MAP.get(userId);
        if (user == null) {
            user = userRepository.findUserById(userId);
            if (user != null) addUser(user);
        }
        return user;
    }

    public List<User> getAllUser(){
        List<User> userList = USER_MAP.values().stream()
                .filter(user -> !user.getRole().equals(Constant.USER_ROLE.ADMIN))
                .toList();

        List<User> result = new ArrayList<>();
        userList.forEach(user ->{
            result.add(new User(user));
        });

        return result;
    }

    public Map<String, String> getUserNameMap(List<String> userId){
        return USER_MAP.entrySet().stream()
                .filter(entry -> userId.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getName()));
    }

    public void addUser(User user){
        USER_MAP.put(user.getId(), user);
    }

    public void updateUserStatus(String userId, Integer status){
        User user = USER_MAP.get(userId);
        if (user != null) {
            user.setStatus(status);
            USER_MAP.put(userId, user);
        } else {
            User newUser = userRepository.findUserById(userId);
            if (newUser != null) addUser(newUser);
        }
    }

    public void updateUserIp(String userId, String ip){
        User user = USER_MAP.get(userId);
        if (user != null) {
            user.setLastLoginIp(ip);
            USER_MAP.put(userId, user);
        } else {
            User newUser = userRepository.findUserById(userId);
            if (newUser != null) addUser(newUser);
        }
    }

    public void updateUserPoint(String userId, int point){
        User user = USER_MAP.get(userId);
        if (user != null) {
            int newPoint = user.getPoint() + point;
            user.setPoint(newPoint);
            USER_MAP.put(userId, user);
        } else {
            User newUser = userRepository.findUserById(userId);
            if (newUser != null) addUser(newUser);
        }
    }

    public void updateUserAvatar(String userId, String avatarImage){
        User user = USER_MAP.get(userId);
        if (user != null) {
            user.setAvatarImage(avatarImage);
            USER_MAP.put(userId, user);
        } else {
            User newUser = userRepository.findUserById(userId);
            if (newUser != null) addUser(newUser);
        }
    }

    public void updateUserPassword(String userId, String password){
        User user = USER_MAP.get(userId);
        if (user != null) {
            user.setPassword(password);
            USER_MAP.put(userId, user);
        } else {
            User newUser = userRepository.findUserById(userId);
            if (newUser != null) addUser(newUser);
        }
    }

    public void updateUserName(String userId, String name){
        User user = USER_MAP.get(userId);
        if (user != null) {
            user.setName(name);
            USER_MAP.put(userId, user);
        } else {
            User newUser = userRepository.findUserById(userId);
            if (newUser != null) addUser(newUser);
        }
    }
}
