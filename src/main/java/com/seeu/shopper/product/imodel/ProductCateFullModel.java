package com.seeu.shopper.product.imodel;

import com.seeu.shopper.adminproduct.model.CategoryItem;
import com.seeu.shopper.adminproduct.model.FullModel;
import com.seeu.shopper.product.model.ProductCategory;
import com.seeu.shopper.product.service.ProductCategoryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by neo on 11/09/2017.
 */
public class ProductCateFullModel {
    private static Map<Integer, ArrayList<Integer>> categoryItems = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 返回数据不包含该父类 ID
     *
     * @param cateID  必须是根分类
     * @param service
     * @return
     */
    public static ArrayList<Integer> getCateChildID(Integer cateID, ProductCategoryService service) {
        if (categoryItems == null) {
            fillData(service);
        }
        return categoryItems.get(cateID);
    }

    // 填充数据，将所有的分类数据更新
    private static void fillData(ProductCategoryService service) {
        FullModel.flushCategoryItem();
        List<CategoryItem> cates = FullModel.getCategoryItem(service);
        Map<Integer, ArrayList<Integer>> map = new HashMap<>();
        for (CategoryItem item : cates) {
            ArrayList arrayList = new ArrayList();
            formData(arrayList, item.getChild());
            map.put(item.getData().getId(), arrayList);
        }
        categoryItems = map;
    }

    /**
     * 递归这颗树，将子节点 ID 全部放在数组里
     *
     * @param array
     * @param items
     */
    private static void formData(ArrayList<Integer> array, List<CategoryItem> items) {
        for (CategoryItem item : items) {
            array.add(item.getData().getId());
            if (item.getChild() != null) {
                formData(array, item.getChild());
            }
        }
    }

    public static void flushCategoryItem() {
        FullModel.flushCategoryItem();
        categoryItems = null;
        return;
    }
}
