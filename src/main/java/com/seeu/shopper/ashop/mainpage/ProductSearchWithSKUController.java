//package com.seeu.shopper.ashop.mainpage;
//
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.seeu.shopper.ashop.domain.detail.HotProduct;
//import com.seeu.shopper.ashop.domain.search.ProductSearchResultModel;
//import com.seeu.shopper.ashop.service.CateSearchService;
//import com.seeu.shopper.ashop.service.HotProductService;
//import com.seeu.shopper.ashop.service.NormFormService;
//import com.seeu.shopper.ashop.service.UnitService;
//import com.seeu.shopper.product.model.ProductBanner;
//import com.seeu.shopper.product.model.ProductCategory;
//import com.seeu.shopper.product.service.ProductBannerService;
//import com.seeu.shopper.product.service.ProductCategoryService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import tk.mybatis.mapper.entity.Condition;
//
//import javax.annotation.Resource;
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by neo on 10/09/2017.
// *
// * 暂时作废
// */
//@Controller
//@RequestMapping("/disabled")
//public class ProductSearchWithSKUController {
//    @Resource
//    CateSearchService cateSearchService;
//    @Resource
//    HotProductService hotProductService;
//    @Resource
//    UnitService unitService;
//    @Resource
//    NormFormService normFormService;
//    @Resource
//    ProductBannerService productBannerService;
//    @Resource
//    ProductCategoryService productCategoryService;
//
//    @RequestMapping("/product")
//    public String search(Model model,
//                         @RequestParam(value = "coin", required = false) String coin,
//                         @RequestParam(value = "page", required = false) Integer page,
//                         @RequestParam(value = "size", required = false) Integer size,
//                         @RequestParam(value = "cate", required = false) Integer cate,
//                         @RequestParam(value = "wd", required = false) String keyword,
//                         @RequestParam(value = "sort", required = false) Integer sortRule,
//                         @RequestParam(value = "min", required = false) BigDecimal min,
//                         @RequestParam(value = "max", required = false) BigDecimal max) {
//        if (page == null || page < 0) page = 1;
////        if (size == null || size < 1) size = 8;
//        size = 12;// 固定死
//        if (keyword == null || keyword.trim().length() == 0) keyword = null;
//        if (cate == null || cate < 0) cate = null;
//        boolean priceCondition = !(min == null || max == null || min.compareTo(BigDecimal.ZERO) == -1 || max.compareTo(min) == -1);
//
//        PageHelper.startPage(page, size);
//        List<ProductSearchResultModel> result = cateSearchService.searchPros(cate, keyword, sortRule, priceCondition, min, max);
//
//        PageInfo info = new PageInfo(result);
//
//        // 获取热门产品
//        List<HotProduct> hots = hotProductService.getHotProducts();
//
//        // 获取总分类信息（根分类）
//        Condition condition = new Condition(ProductCategory.class);
//        condition.createCriteria().andCondition("father_id = -1");
//        List<ProductCategory> categories = productCategoryService.findByCondition(condition);
//
//        for (ProductSearchResultModel product : result) {
//            // 规格信息
//            Map<?, String> map = normFormService.getNormName(product.getNorm_ids());
//            String norms = "";
//            for (String value : map.values()) {
//                norms += "[" + value + "] ";
//            }
//            product.setTag(norms);
//            // banner 信息
//            // TODO 优化查询！！
//            ProductBanner banner = productBannerService.findById(product.getBanner_type());
//            if (banner != null)
//                product.setSubname(banner.getUrl());
//            else
//                product.setSubname(null);
//        }
//
//        String unit = "US$";
//        // 单位换算
//        if (unitService.isAvailable(coin)) {
//            // 换算
//            unit = unitService.getUnit(coin);
//            if (!"US$".equals(unit)) {
//                for (ProductSearchResultModel product : result) {
//                    product.setCurrent_price(unitService.exchangeUnit(coin, product.getCurrent_price()));
//                    product.setOrigin_price(unitService.exchangeUnit(coin, product.getOrigin_price()));
//                }
//                for (HotProduct product : hots) {
//                    product.setCurrent_price(unitService.exchangeUnit(coin, product.getCurrent_price()));
//                    product.setOrigin_price(unitService.exchangeUnit(coin, product.getOrigin_price()));
//                }
//            }
//        } else {
//            coin = "USD";
//        }
//
//        model.addAttribute("unit", unit);
//
//        model.addAttribute("categories", categories);
//        model.addAttribute("hotList", hots);
//
//        model.addAttribute("page", info.getPageNum());// 当前页码[]
//        model.addAttribute("size", info.getSize());
//        model.addAttribute("cate", cate);
//        model.addAttribute("wd", keyword);
//        model.addAttribute("sort", sortRule);
//        model.addAttribute("priceCondition", priceCondition);
//        model.addAttribute("min", min);
//        model.addAttribute("max", max);
//        model.addAttribute("total", info.getTotal());
//
//        model.addAttribute("list", info.getList());
//        model.addAttribute("page", info);
//        return "shop/product";
//    }
//}
