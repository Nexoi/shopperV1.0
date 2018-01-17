package com.seeu.shopper.adminproduct.util;//package com.seeu.shopper.adminpage.util;
//
//import com.alibaba.fastjson.JSONObject;
//import com.seeu.shopper.adminproduct.domain.CategoryItem;
//import com.seeu.shopper.adminproduct.domain.FullModel;
//import com.seeu.shopper.product.domain.ProductCategory;
//import com.seeu.shopper.product.service.ProductCategoryService;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by neo on 26/08/2017.
// * <p>
// * 从数据库读取所有数据，汇集成树
// */
//@Service
//public class CategoryReform {
//    @Resource
//    ProductCategoryService productCategoryService;
//
//    public Object reform2Tree() {
//        List<ProductCategory> productCategories = productCategoryService.findAll();
//        CategoryItem item = new CategoryItem();
//        ProductCategory productCategory = new ProductCategory();
//        productCategory.setId(-1);
//        item.setData(productCategory);
//        item.setChild(new ArrayList<>());
//        form(item, productCategories);
//        // 此时出来的就是最终结果，item 携带了一棵树
//        FullModel.categoryItems = item.getChild();
//        return JSONObject.toJSON(FullModel.categoryItems);
//    }
//
//
//    private void form(CategoryItem item, List<ProductCategory> resource) {
//        // 查询他的孩子
//        for (ProductCategory productCategory : resource) {
//            if (productCategory.getFatherId() == item.getData().getId()) {// 如果等于当前id
//                CategoryItem child = new CategoryItem();
//                child.setChild(new ArrayList<>());
//                child.setData(productCategory);
//                item.getChild().add(child);
//                form(child, resource);// 重复遍历孩子们
//            }
//        }
//        // 装载
//    }
//}
