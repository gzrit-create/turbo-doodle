package com.accountBook.accountBook.common;

import com.accountBook.accountBook.entity.Bill;
import com.accountBook.accountBook.entity.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataBase {
    //用户表
    public static Map<String, User> userMap=new ConcurrentHashMap<>();
    public static Map<Long,User> userIdMap=new ConcurrentHashMap<>();
    public static Long userIdCounter=1L;
    //账单
    public static Map<Long, Bill> billMap=new ConcurrentHashMap<>();
    public static Long billIdCounter=1L;

    //管理员账号
    static{
        User admin=new User();
        admin.setId(userIdCounter++);
        admin.setUsername("admin");
        admin.setPassword("adminTim");
        admin.setRole("admin");
        admin.setNickname("admin");
        admin.setStatus(1);
        userMap.put(admin.getUsername(),admin);
        userIdMap.put(admin.getId(),admin);
    }
}
