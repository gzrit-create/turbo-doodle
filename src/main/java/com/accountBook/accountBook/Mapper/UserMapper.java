package com.accountBook.accountBook.Mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.accountBook.accountBook.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
