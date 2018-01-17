package com.seeu.shopper.adminproduct.product;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.shopper.adminproduct.model.FullModel;
import com.seeu.shopper.product.model.ProductCategory;
import com.seeu.shopper.product.service.ProductCategoryService;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by neo on 26/08/2017.
 */
@RestController
@RequestMapping("/api/admin/v1/category")
public class ApiPageProCategoryController {

    @Resource
    ProductCategoryService productCategoryService;

    @GetMapping
    public Result getCate() {
        // 管理员在添加分类的时候会自动更新 FullModel 数据
        return ResultGenerator.genSuccessResult(FullModel.getCategoryItem(productCategoryService));
    }

    @GetMapping("/{id}")
    public Result getCate(@PathVariable("id") Integer id) {
        ProductCategory productCategory = productCategoryService.findById(id);
        if (productCategory == null)
            return ResultGenerator.genNoContentResult("无此分类数据");
        return ResultGenerator.genSuccessResult(productCategory);
    }

    @PostMapping
    public Result add(ProductCategory category) {
        // 添加
        if (category.getFatherId() == null || category.getName() == null) {
            return ResultGenerator.genExceptionResult("传入参数不完整");
        }
        category.setId(null);
        productCategoryService.save(category);
        // 更新 FullModel 数据
        FullModel.flushCategoryItem();
        FullModel.flushProductCategory();

        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(ProductCategory category) {
        // 添加
        productCategoryService.update(category);
        // 更新 FullModel 数据
        FullModel.flushCategoryItem();
        FullModel.flushProductCategory();
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result del(@PathVariable("id") Integer id) {
        if (id != null) {
            productCategoryService.deleteById(id);
            // 同时删除子类
            deleteChild(id);
            // 更新 FullModel 数据
            FullModel.flushCategoryItem();
            FullModel.flushProductCategory();
        }
        return ResultGenerator.genSuccessResult();
    }

    private void deleteChild(Integer id) {
        Condition condition = new Condition(ProductCategory.class);
        condition.createCriteria().andCondition("father_id = " + id);
        List<ProductCategory> productCategoryList = productCategoryService.findByCondition(condition);
        if (productCategoryList.size() == 0) {
            return;
        }
        for (ProductCategory productCategory : productCategoryList) {
            Integer childId = productCategory.getId();
            // 一个个删
            productCategoryService.deleteById(childId);
            // 删该分类的所有子类
            deleteChild(childId);
        }
    }

}
