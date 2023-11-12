package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.cache.UserInfoManager;
import com.anhtuan.bookapp.domain.CustomUserDetails;
import com.anhtuan.bookapp.domain.User;
import com.anhtuan.bookapp.repository.base.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserInfoService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserInfoManager userInfoManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);
    }

    public UserDetails loadUserById(String userId) {
        User user = userInfoManager.getUserByUserId(userId);
        if (user == null) {
            throw new UsernameNotFoundException(userId);
        }
        return new CustomUserDetails(user);
    }
}
