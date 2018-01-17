package com.seeu.shopper.ashop.dao;

import com.seeu.shopper.ashop.domain.search.ProductSearchResultModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by neo on 11/09/2017.
 * <p>
 * update 2017/10/29 修复：商品必须上架才能被查看
 */
public interface ProductSearchV2Mapper {
    @Select("select * from product where status = 1 ${ordercondition}")
    List<ProductSearchResultModel> listAll(@Param("ordercondition") String ordercondition);

    @Select("select * from product where status = 1 AND (${pricecondition}) ${ordercondition}")
    List<ProductSearchResultModel> listAllWithPriceCondition(@Param("pricecondition") String pricecondition, @Param("ordercondition") String ordercondition);


    @Select("select * from product where (status = 1 AND keyword like '%${keyword}%' ${pricecondition}) ${ordercondition}")
    List<ProductSearchResultModel> searchAll(@Param("keyword") String keyword, @Param("pricecondition") String pricecondition, @Param("ordercondition") String ordercondition);

    @Select("select * from product where (status = 1 AND ( ${sqlchip} ) ${pricecondition}) ${ordercondition}")
    List<ProductSearchResultModel> listCates(@Param("sqlchip") String sqlchip, @Param("pricecondition") String pricecondition, @Param("ordercondition") String ordercondition);


    @Select("select * from product where (status = 1 AND keyword like '%${keyword}%' AND ( ${sqlchip} ) ${pricecondition}) ${ordercondition}")
    List<ProductSearchResultModel> searchCates(@Param("keyword") String keyword, @Param("sqlchip") String sqlchip, @Param("pricecondition") String pricecondition, @Param("ordercondition") String ordercondition);
}
