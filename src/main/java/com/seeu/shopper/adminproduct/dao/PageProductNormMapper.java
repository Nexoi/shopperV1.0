package com.seeu.shopper.adminproduct.dao;

import com.seeu.shopper.product.model.ProductNorm;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

/**
 * Created by neo on 26/08/2017.
 */
public interface PageProductNormMapper {

    @Insert("insert into product_norm(norm_name,pid,team_id) values(#{normName},#{pid},#{teamId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertSelective2(ProductNorm var1);

}
