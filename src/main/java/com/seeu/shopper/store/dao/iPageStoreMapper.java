package com.seeu.shopper.store.dao;

import com.seeu.shopper.store.model.PageStore;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by neo on 23/09/2017.
 */
public interface iPageStoreMapper {

    @Select("SELECT id, name FROM page_store")
    List<PageStore> queryListAll();
}
