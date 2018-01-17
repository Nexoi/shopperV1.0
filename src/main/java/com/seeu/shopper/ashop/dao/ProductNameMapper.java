package com.seeu.shopper.ashop.dao;

import com.seeu.shopper.product.model.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by neo on 16/09/2017.
 */
public interface ProductNameMapper {

    @Select("SELECT pid, name FROM product where pid = #{pid}")
    Product selectProductOnlyName(@Param("pid")Integer pid);
}
