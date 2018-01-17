package com.seeu.shopper.adminproduct.product;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seeu.shopper.adminproduct.model.FullModel;
import com.seeu.shopper.product.model.Product;
import com.seeu.shopper.product.model.ProductCategory;
import com.seeu.shopper.product.service.ProductCategoryService;
import com.seeu.shopper.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by neo on 25/08/2017.
 */
@Controller
@RequestMapping("/adminx")
public class PageProListController {
    @Resource
    ProductService productService;
    @Resource
    ProductCategoryService productCategoryService;

    @RequestMapping("/product-list.html")
    public String userlist(
            Model model,
            @RequestParam(value = "wd", required = false) String wd,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(value = "by", required = false) String bywhat,
            @RequestParam(value = "deal", required = false) Boolean deal,
            @RequestParam(value = "onstock", required = false) Boolean onstock) {

        if (deal == null) {
            deal = false;
        }
        if (onstock == null) {
            onstock = false;
        }
        // 处理筛选条件
        String property = null;
        if (bywhat == null) {
            bywhat = "name";
        }
        switch (bywhat) {
            case "name":
                property = "name";
                break;
            default:
                property = "name";
        }
        // 查询
        PageInfo pageInfo = null;
        if (wd != null && wd.trim().length() != 0) {
            PageHelper.startPage(page, size);
            Condition condition = new Condition(Product.class);
            if (deal) {
                condition.createCriteria().andCondition(" is_deal = true ");
            }
            if (onstock) {
                condition.createCriteria().andCondition(" status = 1 "); // 1 上架
            }
            condition.createCriteria().andLike(property, "%" + wd + "%");
            List<Product> products = productService.findByCondition(condition);
            formList(products);
            pageInfo = new PageInfo(products);
        } else {
            PageHelper.startPage(page, size);
            Condition condition = new Condition(Product.class);
            if (deal) {
                condition.createCriteria().andCondition(" is_deal = true ");
            }
            if (onstock) {
                condition.createCriteria().andCondition(" status = 1 ");
            }
            List<Product> products = productService.findByCondition(condition);
            formList(products);
            pageInfo = new PageInfo(products);
        }
        if (wd == null) {
            wd = "";
        }

        model.addAttribute("deal", deal);
        model.addAttribute("onstock", onstock);
        model.addAttribute("word", wd);
        model.addAttribute("pagelist", pageInfo.getList());
        model.addAttribute("page", pageInfo);
        model.addAttribute("index", pageInfo.getNavigatepageNums());// 页码
        return "adminx/product-list";
    }

    private void formList(List<Product> products) {
        for (Product product : products) {
            Integer cate = product.getCategoryId();
            List<ProductCategory> categories = getProductCategories();
            for (ProductCategory category : categories) {
                if (cate == category.getId()) {
                    product.setSubname(category.getName());// 用subname来替代分类名，充当临时数据
                }
            }
            // 处理空值问题
//            product.setIsDeal(product.getIsDeal() == null ? false : product.getIsDeal());
        }
    }

    private List<ProductCategory> getProductCategories() {
        return FullModel.getProductCategorys(productCategoryService);
    }
}
