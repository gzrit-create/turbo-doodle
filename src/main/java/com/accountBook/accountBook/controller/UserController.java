package com.accountBook.accountBook.controller;

import com.accountBook.accountBook.common.Result;
import com.accountBook.accountBook.dto.PasswordUpdateRequest;
import com.accountBook.accountBook.dto.UserUpdateRequest;
import com.accountBook.accountBook.entity.User;
import com.accountBook.accountBook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
//晚点把注入方式改为构造注入
    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public Result<User> getUserInfo(@RequestAttribute("userId") Long userId) {
        User user = userService.getUserById(userId);//这里的调用不太懂
        if (user == null) return Result.fail("用户不存在");
        user.setPassword(null);
        return Result.success(user);
    }

    @PutMapping("/info")
    public Result<String> updateUserInfo(@RequestBody UserUpdateRequest request,
                                         @RequestAttribute("userId") Long userId) {
        boolean ok = userService.updateUserInfo(userId, request);
        return ok ? Result.success("更新成功") : Result.fail("更新失败");
    }

    @PutMapping("/password")
    public Result<String> updatePassword(@RequestBody PasswordUpdateRequest request,
                                         @RequestAttribute("userId") Long userId) {
        boolean ok = userService.updatePassword(userId, request);
        return ok ? Result.success("修改成功") : Result.fail("旧密码错误");
    }
}
