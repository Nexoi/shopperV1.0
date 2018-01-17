package com.seeu.shopper.ashop.dao;

import com.seeu.shopper.ashop.domain.detail.HotProduct;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by neo on 26/08/2017.
 */
public interface HotProductMapper {

    // 上架商品中
    @Select("SELECT pid, name, origin_price, current_price, img FROM product WHERE status = 1 ORDER BY sales DESC LIMIT 5 ")
    List<HotProduct> selectHotProducts();
}
