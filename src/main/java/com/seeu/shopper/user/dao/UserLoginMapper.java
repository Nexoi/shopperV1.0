package com.seeu.shopper.user.dao;

import com.seeu.core.Mapper;
import com.seeu.shopper.product.model.Product;
import com.seeu.shopper.user.model.UserLogin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

public interface UserLoginMapper extends Mapper<UserLogin> {
    // 用户注册时，需要返回 uid

    @Insert("insert into user_login(email,password,last_login_ip) values(#{email},#{password},#{lastLoginIp})")
    @Options(useGeneratedKeys = true, keyProperty = "uid", keyColumn = "uid")
    int insertNewUser(UserLogin var1);
}