package com.seeu.shopper.ashop.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by neo on 05/09/2017.
 * <p>
 * 用户点击一次，购买n件商品后在这里记录
 */
public interface ProductClickSaleMapper {

    @Update("update product set click_times=click_times+1 where pid = #{pid}")
    void clickOne(@Param("pid") Integer pid);


    @Update("update product set sales=sales+#{much} where pid = #{pid}")
    void saleMuch(@Param("pid") Integer pid, @Param("much") Integer much);
}
