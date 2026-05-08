package com.accountBook.accountBook.service;


import com.accountBook.accountBook.Mapper.UserMapper;
import com.accountBook.accountBook.common.DataBase;
import com.accountBook.accountBook.dto.LoginRequest;
import com.accountBook.accountBook.dto.PasswordUpdateRequest;
import com.accountBook.accountBook.dto.RegisterRequest;
import com.accountBook.accountBook.dto.UserUpdateRequest;
import com.accountBook.accountBook.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public boolean register(RegisterRequest request) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, request.getUsername());
        if (userMapper.selectCount(wrapper) > 0) {
            return false;
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword()); // 后续可加密
        user.setNickname(request.getNickname());
        user.setStatus(1);
        user.setRole("USER");
        return userMapper.insert(user) > 0;
    }


    public User login(String username, String password) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username)
                .eq(User::getPassword, password)
                .eq(User::getStatus, 1);
        return userMapper.selectOne(wrapper);
    }


    public User getUserById(Long userId) {
        return userMapper.selectById(userId);
    }


    public boolean updateUserInfo(Long userId, UserUpdateRequest request) {
        User user = new User();
        user.setId(userId);
        user.setNickname(request.getNickname());
        return userMapper.updateById(user) > 0;
    }


    public boolean updatePassword(Long userId, PasswordUpdateRequest request) {
        User user = userMapper.selectById(userId);
        if (user == null || !user.getPassword().equals(request.getOldPassword())) {
            return false;
        }
        user.setPassword(request.getNewPassword());
        return userMapper.updateById(user) > 0;
    }


    public List<User> getAllUsers() {
        return userMapper.selectList(null);
    }


    public boolean setUserStatus(Long userId, Integer status) {
        User user = new User();
        user.setId(userId);
        user.setStatus(status);
        return userMapper.updateById(user) > 0;
    }
}
