package com.accountBook.accountBook.controller;

import com.accountBook.accountBook.common.Result;
import com.accountBook.accountBook.entity.User;
import com.accountBook.accountBook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public Result<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        users.forEach(u -> u.setPassword(null));
        return Result.success(users);
    }

    @PutMapping("/users/{userId}/status")
    public Result<String> setUserStatus(@PathVariable Long userId,
                                        @RequestParam Integer status,
                                        @RequestAttribute("userId") Long adminId) {
        if (adminId.equals(userId)) {
            return Result.fail("不能禁用自己");
        }
        boolean ok = userService.setUserStatus(userId, status);
        return ok ? Result.success("操作成功") : Result.fail("用户不存在");
    }
}
