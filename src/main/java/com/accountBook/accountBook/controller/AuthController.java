package com.accountBook.accountBook.controller;

import com.accountBook.accountBook.common.Result;
import com.accountBook.accountBook.dto.LoginRequest;
import com.accountBook.accountBook.dto.RegisterRequest;
import com.accountBook.accountBook.entity.User;
import com.accountBook.accountBook.service.UserService;
import com.accountBook.accountBook.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

        @Autowired
        private UserService userService;
        @Autowired
        private JwtUtil jwtUtil;

        @PostMapping("/register")
        public Result<String> register(@RequestBody RegisterRequest request) {
            if (request.getUsername() == null || request.getPassword() == null) {
                return Result.fail("用户名或密码不能为空");
            }
            boolean success = userService.register(request);
            return success ? Result.success("注册成功") : Result.fail("用户名已存在");
        }

        @PostMapping("/login")
        public Result<Map<String, Object>> login(@RequestBody LoginRequest request) {
            User user = userService.login(request.getUsername(), request.getPassword());
            if (user != null) {
                String token = jwtUtil.generateToken(user.getUsername(), user.getId());
                Map<String, Object> data = new HashMap<>();
                data.put("token", token);
                data.put("user", user);
                return Result.success(data);
            } else {
                return Result.fail("用户名或密码错误");
            }
        }
    }

