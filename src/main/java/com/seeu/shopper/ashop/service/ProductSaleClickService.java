package com.seeu.shopper.ashop.service;

import com.seeu.shopper.ashop.dao.ProductClickSaleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by neo on 05/09/2017.
 */
@Service
public class ProductSaleClickService {
    @Resource
    ProductClickSaleMapper mapper;

    /**
     * 点击该商品次数
     * @param pid
     */
    public void clickOne(Integer pid) {
        mapper.clickOne(pid);
    }

    /**
     * 销售商品数量
     * @param pid
     * @param much
     */
    public void salesMuch(Integer pid, Integer much) {
        if (much == null || much <= 0) {
            return;
        }
        mapper.saleMuch(pid, much);
    }
}
