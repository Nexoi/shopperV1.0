package com.seeu.shopper.adminproduct.service;

import com.seeu.shopper.adminproduct.dao.PageProductMapper;
import com.seeu.shopper.product.model.Product;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by neo on 26/08/2017.
 */
@Service
public class PageProductService {
    @Resource
    PageProductMapper pageProductMapper;

    public Integer insertProduct(Product product) {
        return pageProductMapper.insertSelective2(product);
    }

    public Integer insertProductWithImg(Product product) {
        return pageProductMapper.insertSelective2WithImg(product);
    }

    public List<Product> selectAllNames() {
        return pageProductMapper.selectAllNames();
    }

    public Product selectName(Integer pid) {
        return pageProductMapper.selectName(pid);
    }
}
