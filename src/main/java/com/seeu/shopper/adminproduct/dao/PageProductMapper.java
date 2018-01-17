package com.seeu.shopper.adminproduct.dao;

import com.seeu.shopper.product.model.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by neo on 26/08/2017.
 */
public interface PageProductMapper {

    @Insert("insert into product(category_id,name,subname,origin_price,current_price,weight,tag,keyword,status) values(#{categoryId},#{name},#{subname},#{originPrice},#{currentPrice},#{weight},#{tag},#{keyword},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "pid", keyColumn = "pid")
    int insertSelective2(Product var1);

    @Insert("insert into product(category_id,name,subname,origin_price,current_price,weight,tag,keyword,status,img) values(#{categoryId},#{name},#{subname},#{originPrice},#{currentPrice},#{weight},#{tag},#{keyword},#{status},#{img})")
    @Options(useGeneratedKeys = true, keyProperty = "pid", keyColumn = "pid")
    int insertSelective2WithImg(Product var1);

    @Select("SELECT pid, name FROM product")
    List<Product> selectAllNames();

    @Select("SELECT pid, name FROM product where pid = #{pid}")
    Product selectName(@Param("pid") Integer pid);
}
