package com.seeu.shopper.adminproduct.model;

import com.seeu.shopper.adminproduct.service.PageProductService;
import com.seeu.shopper.product.model.Product;
import com.seeu.shopper.product.model.ProductCategory;
import com.seeu.shopper.product.model.ProductNorm;
import com.seeu.shopper.product.service.ProductCategoryService;
import com.seeu.shopper.product.service.ProductNormService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by neo on 26/08/2017.
 * 全局变量，特殊维护
 */
public class FullModel {

    private static List<CategoryItem> categoryItems = null; // 商品分类树
    private static List<ProductCategory> productCategorys = null; // 商品分类列表，包含所有信息
    private static HashMap<Integer, String> productNames = null; // 商品名称信息缓存，根据 pid 查找商品名
    private static HashMap<String, String> productNorms = null; // 商品规格，包含 id - name


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static List<CategoryItem> getCategoryItem(ProductCategoryService service) {
        if (categoryItems == null) {
            List<ProductCategory> productCategories = service.findAll();
            CategoryItem item = new CategoryItem();
            ProductCategory productCategory = new ProductCategory();
            productCategory.setId(-1);
            item.setData(productCategory);
            item.setChild(new ArrayList<>());
            form(item, productCategories);
            // 此时出来的就是最终结果，item 携带了一棵树
            categoryItems = item.getChild();
        }
        return categoryItems;
    }

    public static void flushCategoryItem() {
        categoryItems = null;
        return;
    }

    private static void form(CategoryItem item, List<ProductCategory> resource) {
        // 查询他的孩子
        for (ProductCategory productCategory : resource) {
            if (productCategory.getFatherId() == item.getData().getId()) {// 如果等于当前id
                CategoryItem child = new CategoryItem();
                child.setChild(new ArrayList<>());
                child.setData(productCategory);
                item.getChild().add(child);
                form(child, resource);// 重复遍历孩子们
            }
        }
        // 装载
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static List<ProductCategory> getProductCategorys(ProductCategoryService service) {
        if (productCategorys == null) {
            productCategorys = service.findAll();
        }
        return productCategorys;
    }

    public static void flushProductCategory() {
        productCategorys = null;
        return;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static HashMap<String, String> getProductNorms(ProductNormService service) {
        if (productNorms == null) {
            HashMap<String, String> map = new HashMap();
            List<ProductNorm> products = service.findAll(); // 优化查询，不用每次都把所有信息都查找出来
            for (ProductNorm product : products) {
                map.put("" + product.getId(), product.getNormName());
            }
            productNorms = map;
        }
        return productNorms;
    }

    public static void flushProductNorms() {
        productNorms = null;
        return;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static HashMap<Integer, String> getProductNames(PageProductService service) {
        if (productNames == null) {
            HashMap<Integer, String> map = new HashMap();
            List<Product> products = service.selectAllNames(); // 优化查询，不用每次都把所有信息都查找出来
            for (Product product : products) {
                map.put(product.getPid(), product.getName());
            }
            productNames = map;
        }
        return productNames;
    }

    public static void flushProductNames() {
        productNames = null;
        return;
    }
}
