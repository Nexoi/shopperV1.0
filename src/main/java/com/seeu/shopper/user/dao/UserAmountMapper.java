package com.seeu.shopper.user.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

public interface UserAmountMapper{

    // 增加消费额
    @Update("update user_info set amount = amount+#{amount} where uid = #{uid}")
    int updateAmount(@Param("uid") Integer uid, @Param("amount") BigDecimal amount);
}