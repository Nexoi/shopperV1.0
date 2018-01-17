package com.seeu.shopper.ashop.service;

import com.seeu.shopper.ashop.dao.ProductSearchMapper;
import com.seeu.shopper.ashop.domain.search.ProductSearchResultModel;
import com.seeu.shopper.product.imodel.ProductCateFullModel;
import com.seeu.shopper.product.service.ProductCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo on 11/09/2017.
 * <p>
 * 提供给搜索页使用
 * <p>
 * 列出该分类下所有子分类的商品信息
 * <p>
 * 提供基本的关键词检索功能
 */
@Service
public class CateSearchWithSKUService {

    @Resource
    ProductCategoryService productCategoryService;
    // SKU 搜索方式
    @Resource
    ProductSearchMapper productSearchMapper;

    /**
     * 查询出来的商品必须符合以下规则：
     * 1. 有库存，且库存已经启用
     * 2. 已上架
     * <p>
     * 查询结果：
     * 商品信息按 product 表唯一陈列在前台
     * 一个 product 可能对应多个 stock
     *
     * @param fatherCateID   分类 id，一般是根分类
     * @param keyword        搜索关键词
     * @param sortRule       排序规则：-1 不排序，1 价格顺序，2 价格逆序，3 销量顺序，4 销量逆序
     * @param priceCondition 是否加入价格筛选条件
     * @param priceLow       价格筛选：最低价
     * @param priceHigh      价格筛选：最高价
     * @return
     */
    public List<ProductSearchResultModel> searchPros(Integer fatherCateID, String keyword, Integer sortRule, boolean priceCondition, BigDecimal priceLow, BigDecimal priceHigh) {
        String priceCondi = ""; // 必须自带 AND
        if (priceCondition) {
            if (priceLow == null || priceHigh == null || priceLow.compareTo(BigDecimal.ZERO) == -1 || priceHigh.compareTo(priceLow) == -1) { // 如果传入参数有误，则不加价格因素
                return searchPros(fatherCateID, keyword, sortRule, false, null, null);
            }
            priceCondi = "AND ( product.current_price > " + priceLow.doubleValue() + " AND product.current_price < " + priceHigh.doubleValue() + ")";
        }
        String sortCondi = ""; // 放在 SQL 语句最后，可不带任何信息
        if (sortRule == null) sortRule = 2;
        switch (sortRule) {
            case -1:
                break;
            case 1:
                sortCondi = "order by product.current_price";
                break;
            case 2:
                sortCondi = "order by product.current_price desc";
                break;
            case 3:
                sortCondi = "order by product.sales";
                break;
            case 4:
                sortCondi = "order by product.sales desc";
                break;
            default:
                break;
        }

        if (fatherCateID == null) {
            // 查询所有
            if (keyword == null) {
                return productSearchMapper.listAll(priceCondi, sortCondi);
            }
            return productSearchMapper.searchAll(keyword.trim(), priceCondi, sortCondi);
        } else {
            List<Integer> cates = getMyChildCateIDs(fatherCateID);
            if (cates == null) {
                cates = new ArrayList<>();
//                return searchPros(null, keyword, sortRule, priceCondition, priceLow, priceHigh);
            }
            cates.add(fatherCateID);// 搜索时包含根类
            // 查询该分类下商品信息
            String sqlchip = " ";
            for (Integer cate : cates) {
                sqlchip += "OR product.category_id = " + cate + " ";
            }
            sqlchip = sqlchip.replaceFirst("OR", "");
            if (keyword == null) {
                return productSearchMapper.listCates(sqlchip, priceCondi, sortCondi);
            } else {
                return productSearchMapper.searchCates(keyword, sqlchip, priceCondi, sortCondi);
            }

        }
    }

    /**
     * 根据父类 ID 返回该类下面所有层级 ID，且该父类必须是根类
     *
     * @param fatherCateID
     */
    private List<Integer> getMyChildCateIDs(Integer fatherCateID) {
        return ProductCateFullModel.getCateChildID(fatherCateID, productCategoryService);
    }
}
