package com.seeu.shopper.ashop.mainpage;

import com.seeu.shopper.adminproduct.service.PageProductService;
import com.seeu.shopper.ashop.domain.detail.HotProduct;
import com.seeu.shopper.ashop.domain.detail.NormMap;
import com.seeu.shopper.ashop.service.HotProductService;
import com.seeu.shopper.ashop.service.NormFormService;
import com.seeu.shopper.ashop.service.ProductSaleClickService;
import com.seeu.shopper.ashop.service.UnitService;
import com.seeu.shopper.product.model.*;
import com.seeu.shopper.product.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo on 03/09/2017.
 * <p>
 * 查询对应商品所有信息
 * <p>
 * update:
 * 2017/10/21 重定向中去掉 url 参数项：myemail
 * <p>
 * update:
 * 2017/10/16 将库存信息查询规则修改为：查询最后一个库存信息作为默认库存信息，避开查询第一个会造成访问到未删除的旧数据情况（现象：前台价格、库存量未改变）
 *
 * 2017/11/09 修复产品评论信息显示设置为 false 时依然未被隐藏的 bug
 *
 */
@Controller
public class ProductDetailController {

    @Resource
    ProductService productService;
    @Resource
    ProductImgService productImgService;
    @Resource
    ProductAttributeService productAttributeService;
    @Resource
    ProductCommentService productCommentService;
    @Resource
    ProductIntroService productIntroService;
    @Resource
    ProductNormService productNormService;
    @Resource
    ProductStockService productStockService;

    @Resource
    NormFormService normFormService;
    @Resource
    UnitService unitService;

    @Resource
    HotProductService hotProductService;

    @Resource
    ProductSaleClickService productSaleClickService;

    @Resource
    PageProductService pageProductService;

    @RequestMapping("/product/{pid}")
    public String pro(@PathVariable("pid") Integer pid,
                      @RequestParam(value = "nid", required = false) String nid, // 库存规格信息
                      @RequestParam(value = "coin", required = false) String coin,
                      @RequestParam(value = "myemail", required = false) String email,
                      RedirectAttributes redir) {
        if (nid == null) nid = "";
        if (coin == null) coin = "USD";
        Product product = pageProductService.selectName(pid);
        if (product == null) {
            return "404";
        }
        String name = product.getName() == null ? "product" : product.getName();
        name = name.replaceAll(" ", "-").replaceAll("/", ".");
        if (email != null)
            redir.addFlashAttribute("myemail");
        return "redirect:/product/" + name + "/" + pid + ".html?nid=" + nid + "&coin=" + coin;
    }

    @RequestMapping(value = {"/product/**/{pid}.html"})
    public String productDetailPage(Model model,
                                    @PathVariable("pid") Integer pid,
                                    @RequestParam(value = "nid", required = false) String nid, // 库存规格信息
                                    @RequestParam(value = "coin", required = false) String coin) {
        // 解析
        // 商品基本信息
        Product product = productService.findById(pid);
        if (product == null || product.getStatus() == 0) { // 0 下架, 1 上架
            return "404";
        }
        // 记录商品被点击一次
        productSaleClickService.clickOne(pid);

        //// 拉取数据
        Condition condition = new Condition(ProductImg.class);
        condition.createCriteria().andCondition("pid = " + pid);
        // 图片组
        List<ProductImg> productImgs = productImgService.findByCondition(condition);

        // 参数
        List<ProductAttribute> productAttributes = productAttributeService.findByCondition(condition);

        // 评论
        Condition conditionComment = new Condition(ProductComment.class);
        conditionComment.createCriteria().andCondition("pid = " + pid + " AND is_visible = true"); // 评论不可见的不会被显示出来
        List<ProductComment> productComments = productCommentService.findByCondition(conditionComment);
        // 评论隐私化 email
        for (ProductComment comment : productComments) {
            String name = comment.getName();
            comment.setName(formName(name));
        }

        // 详情介绍
        ProductIntro productIntro = productIntroService.findById(pid);

        // 库存
//        List<ProductStock> productStocks = productStockService.findByCondition(condition);


        // 规格
        List<ProductNorm> productNorms = productNormService.findByCondition(condition);

        // 规格信息需要重新整合一下
        ArrayList<NormMap> normMaps = normFormService.formNorms(productNorms);
        model.addAttribute("productNorms", normMaps);

        // 查询该商品该库存 sid 是否有
        if (nid == null || nid.trim().length() == 0) {
            nid = "0";
        }
        Boolean canBuy = false;// 通知前台该库存是否可以购买
        Integer currentStock = 0;
        Condition conditionStock = new Condition(ProductStock.class);
        conditionStock.createCriteria().andCondition("pid = " + pid + " AND is_ing = true AND norm_ids = '" + nid + "'");
        List<ProductStock> stocks = productStockService.findByCondition(conditionStock);
        // 有nid库存信息
        if (stocks.size() != 0) {
            // 取第一个 xxxxxxxx 取最后一个，避免数据库数据过旧未删除。。
            ProductStock stock = stocks.get(stocks.size() - 1);
            // 替换价格信息，同时注入前台规格数据
            product.setOriginPrice(stock.getOrginPrice());
            product.setCurrentPrice(stock.getPrice());
            currentStock = stock.getCurrentStock();
            if (currentStock > 0) {
                canBuy = true;
            }
        } else {
            // 没有nid库存信息，查询第一个有效库存信息
            /* 注释掉了xxxxx Start */
            conditionStock.clear();
            conditionStock.createCriteria().andCondition("pid = " + pid + " AND is_ing = true ");
            List<ProductStock> stocks2 = productStockService.findByCondition(conditionStock);
            if (stocks2.size() != 0) {
                ProductStock stock = stocks2.get(stocks2.size() - 1); // 取最后一个
                // 替换价格信息，同时注入前台规格数据
                product.setOriginPrice(stock.getOrginPrice());
                product.setCurrentPrice(stock.getPrice());
                currentStock = stock.getCurrentStock();
                nid = stock.getNormIds();
                if (currentStock > 0) {
                    canBuy = true;
                }
            } else {
                nid = "0";
            }
            /* 注释掉了xxxxx End */
        }
        model.addAttribute("stockRest", currentStock);// 当前库存，如果库存为 0，前端 js 应提示用户不可添加购物车
        model.addAttribute("nid", nid);
        model.addAttribute("canBuy", canBuy);

        // 价格单位
        if (coin == null || coin.trim().length() == 0) {
            coin = "USD";
        }
        model.addAttribute("unit", unitService.getUnit(coin));

        // 加入热卖产品
        List<HotProduct> hotProducts = hotProductService.getHotProducts();

        // 换算汇率
        if (!"USD".equals(coin)) {
            product.setOriginPrice(unitService.exchangeUnit(coin, product.getOriginPrice()));
            product.setCurrentPrice(unitService.exchangeUnit(coin, product.getCurrentPrice()));
//            库存信息不需要再全部查询返回给前台
//            for (ProductStock stock : productStocks) {
//                stock.setPrice(unitService.exchangeUnit(coin, stock.getPrice()));
//                stock.setOrginPrice(unitService.exchangeUnit(coin, stock.getOrginPrice()));
//            }
            for (HotProduct hotProduct : hotProducts) {
                hotProduct.setOrigin_price(unitService.exchangeUnit(coin, hotProduct.getOrigin_price()));
                hotProduct.setCurrent_price(unitService.exchangeUnit(coin, hotProduct.getCurrent_price()));
            }
        }


        model.addAttribute("product", product);
//        model.addAttribute("productStocks", productStocks);
        model.addAttribute("productImgs", productImgs);
        model.addAttribute("productAttributes", productAttributes);
        model.addAttribute("productComments", productComments);
        model.addAttribute("productIntro", productIntro);
        model.addAttribute("hotProducts", hotProducts);

        return "shop/product_detail";
    }

    private String formName(String name) {
        if (name == null) return "";
        if (name.length() > 4) {
            int subLength = name.length() / 2;
            subLength = subLength > 5 ? 5 : subLength;
            return name.substring(0, subLength) + "****" + name.substring(name.length() - subLength, name.length());
        }
        return name;
    }
}
