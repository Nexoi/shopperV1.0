package com.seeu.shopper.ashop.domain.mycenter;

import com.seeu.shopper.ashop.dao.ProductNameMapper;
import com.seeu.shopper.product.model.Product;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by neo on 16/09/2017.
 */
@Service
public class ProductNameService {

    @Resource
    ProductNameMapper mapper;

    public String getName(Integer pid) {
        Product product = mapper.selectProductOnlyName(pid);
        return product == null ? null : product.getName();
    }
}
