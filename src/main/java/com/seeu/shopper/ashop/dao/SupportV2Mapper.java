package com.seeu.shopper.ashop.dao;

import com.seeu.shopper.product.model.Product;
import com.seeu.shopper.store.model.PageStore;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by neo on 16/09/2017.
 */
public interface SupportV2Mapper {

    @Select("SELECT name FROM page_store where name like 'support-index-cid-%'")
    List<PageStore> selectSupportCateOnlyName();
}
