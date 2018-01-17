package com.seeu.shopper.ashop.service;

import com.seeu.shopper.ashop.dao.HotProductMapper;
import com.seeu.shopper.ashop.domain.detail.HotProduct;
import com.seeu.shopper.product.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by neo on 04/09/2017.
 *
 * 热卖推荐
 */
@Service
public class HotProductService {
    @Resource
    ProductService productService;
    @Resource
    HotProductMapper hotProductMapper;

    public List<HotProduct> getHotProducts(){
        return hotProductMapper.selectHotProducts();
    }
}
