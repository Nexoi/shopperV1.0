package com.seeu.shopper.ashop.mainpage;

import com.seeu.shopper.ashop.domain.FullPageModel;
import com.seeu.shopper.ashop.domain.index.*;
import com.seeu.shopper.ashop.service.UnitService;
import com.seeu.shopper.page.service.PageService;
import com.seeu.shopper.product.model.Product;
import com.seeu.shopper.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo on 02/09/2017.
 */
@Controller
public class HomeIndexController {
    @Resource
    PageService pageService;
    @Resource
    ProductService productService;
    @Resource
    UnitService unitService;

//    @RequestMapping("/")
//    public String mainpage(@RequestParam(value = "coin", required = false) String coin,RedirectAttributes redir) {
//        redir.addFlashAttribute("myemail");
//        return "redirect:/index";
//    }

    @RequestMapping(value = {"/index", "/"})
    public String index(Model model, @RequestParam(value = "coin", required = false) String coin) {
        // 注入数据
        IndexModel indexModel = FullPageModel.getIndexModel(pageService);
        if (indexModel == null) {
            // 最好初始化一份数据做异常备份
            model.addAttribute("code", 500);
            model.addAttribute("message", "Page Error.");
            return "shop/info";
        }
        // 解析数据(Cate\MainCate\SubCate)
        // 主分类 Cate // 不做处理

        // MainCate
        List<MainCate> mainCates = indexModel.getMainCates(); // 总计 4 个
        List<CateModel> mainCateModel = new ArrayList<>();
        for (int i = 0; i < 4 && i < mainCates.size(); i++) {
            String pids = mainCates.get(i).getPids();
            if (pids == null || pids.trim().length() == 0) {
                pids = "1,1,1,1,1,1,1,1";
            }
//            findbyIds 居然有缓存问题。。。于是只能一个个查了
            List<Product> products = new ArrayList<>();
            String[] pidLis = pids.split(",");
            for (String pid : pidLis) {
                Product product = productService.findById(Integer.parseInt(pid));
                if (product == null) continue;
                products.add(product);
            }
//            List<Product> products = productService.findByIds(pids); // 最多 8 个
            // 换算单位
            for (Product product : products) {
                product.setCurrentPrice(unitService.exchangeUnit(coin, product.getCurrentPrice()));
                product.setOriginPrice(unitService.exchangeUnit(coin, product.getOriginPrice()));
            }

            CateModel cateModel = new CateModel();
            cateModel.setCateName(mainCates.get(i).getCateName());
            cateModel.setProducts(products);
            cateModel.setId("maincate" + i);
            mainCateModel.add(cateModel);
//            model.addAttribute("maincate" + i, products);
        }
        model.addAttribute("maincate", mainCateModel);
        // SubCate
        List<SubCate> subCates = indexModel.getSubCates(); // 总计 3 个
        List<CateModel> subCateModel = new ArrayList<>();
        for (int i = 0; i < 4 && i < subCates.size(); i++) {
            String pids = subCates.get(i).getPids();
            if (pids == null || pids.trim().length() == 0) {
                pids = "1,1,1";
            }
//            findbyIds 居然有缓存问题。。。于是只能一个个查了
            List<Product> products = new ArrayList<>();
            String[] pidLis = pids.split(",");
            for (String pid : pidLis) {
                Product product = productService.findById(Integer.parseInt(pid));
                if (product == null) continue;
                products.add(product);
            }
//            List<Product> products = productService.findByIds(pids); // 最多 3 个
            // 换算单位
            for (Product product : products) {
                product.setCurrentPrice(unitService.exchangeUnit(coin, product.getCurrentPrice()));
                product.setOriginPrice(unitService.exchangeUnit(coin, product.getOriginPrice()));
            }

            CateModel cateModel = new CateModel();
            cateModel.setCateName(subCates.get(i).getCateName());
            cateModel.setSubName(subCates.get(i).getSubName());
            cateModel.setImgURL(subCates.get(i).getImgURL());
            cateModel.setUrl(subCates.get(i).getUrl());
            cateModel.setProducts(products);
            cateModel.setId("subcate" + i);
            subCateModel.add(cateModel);
        }
        model.addAttribute("subcate", subCateModel);

        model.addAttribute("model", indexModel);// 其余信息都添上
        if (coin != null)
            model.addAttribute("coin", coin);
        model.addAttribute("unit", unitService.getUnit(coin));
        return "shop/index";
    }
}
