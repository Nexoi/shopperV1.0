package com.seeu.shopper.ashop.dao;

import com.seeu.shopper.ashop.domain.search.ProductSearchResultModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by neo on 11/09/2017.
 */
public interface ProductSearchMapper {

    @Select("select * from product, product_stock where (product.pid = product_stock.pid AND product_stock.is_ing = true AND product_stock.current_stock > 0 ${pricecondition}) ${ordercondition}")
    List<ProductSearchResultModel> listAll(@Param("pricecondition") String pricecondition, @Param("ordercondition") String ordercondition);


    @Select("select * from product, product_stock where (product.keyword like '%${keyword}%' ${pricecondition} AND product.pid = product_stock.pid AND product_stock.is_ing = true AND product_stock.current_stock > 0) ${ordercondition}")
    List<ProductSearchResultModel> searchAll(@Param("keyword") String keyword, @Param("pricecondition") String pricecondition, @Param("ordercondition") String ordercondition);

    @Select("select * from product, product_stock where (( ${sqlchip} ) ${pricecondition} AND product.pid = product_stock.pid AND product_stock.is_ing = true AND product_stock.current_stock > 0) ${ordercondition}")
    List<ProductSearchResultModel> listCates(@Param("sqlchip") String sqlchip, @Param("pricecondition") String pricecondition, @Param("ordercondition") String ordercondition);


    @Select("select * from product, product_stock where (product.keyword like '%${keyword}%' AND ( ${sqlchip} ) ${pricecondition} AND product.pid = product_stock.pid AND product_stock.is_ing = true AND product_stock.current_stock > 0) ${ordercondition}")
    List<ProductSearchResultModel> searchCates(@Param("keyword") String keyword, @Param("sqlchip") String sqlchip, @Param("pricecondition") String pricecondition, @Param("ordercondition") String ordercondition);
}
