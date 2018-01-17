package com.seeu.shopper.admin.page;

import com.seeu.shopper.adminproduct.service.PageProductService;
import com.seeu.shopper.ashop.domain.FullPageModel;
import com.seeu.shopper.ashop.domain.footer.FooterModel;
import com.seeu.shopper.ashop.domain.header.HeaderModel;
import com.seeu.shopper.ashop.domain.index.IndexModel;
import com.seeu.shopper.ashop.service.NormFormService;
import com.seeu.shopper.page.service.PageService;
import com.seeu.shopper.product.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by neo on 20/09/2017.
 */
@Controller
@RequestMapping("/adminx")
public class PageConfigController {
    @Resource
    PageProductService pageProductService;
    @Resource
    PageService pageService;
    @Resource
    NormFormService normFormService;

    /**
     * 商品 4*8、1+4*3
     *
     * @param model
     * @return
     */
    @RequestMapping("product-config.html")
    public String proCfgPage(Model model) {
        List<Product> productList = pageProductService.selectAllNames(); // 仅包含名字和 pid
        FullPageModel.flushHeaderModel();
        FullPageModel.flushIndexModel();
        IndexModel indexModel = FullPageModel.getIndexModel(pageService);
        model.addAttribute("index", indexModel);
        model.addAttribute("proList", productList);
        return "adminx/pageconfig-product";
    }

    /**
     * 广告、轮播图
     *
     * @param model
     * @return
     */
    @RequestMapping("home-config.html")
    public String proCfgPage2(Model model) {
        FullPageModel.flushHeaderModel();
        FullPageModel.flushIndexModel();
        IndexModel indexModel = FullPageModel.getIndexModel(pageService);
        model.addAttribute("index", indexModel);
        return "adminx/pageconfig-home";
    }

    /**
     * 商品分类 (header)
     *
     * @param model
     * @return
     */
    @RequestMapping("cate-config.html")
    public String proCfgPage3(Model model) {
        FullPageModel.flushHeaderModel();
        FullPageModel.flushIndexModel();
        HeaderModel headerModel = FullPageModel.getHeaderModel(pageService);
        model.addAttribute("cates", headerModel.getCategory());
        return "adminx/pageconfig-cate";
    }

    /**
     * 底部导航栏 (footer)
     *
     * @param model
     * @return
     */
    @RequestMapping("footer-config.html")
    public String footerConfig(Model model) {
        FullPageModel.flushFooterModel();
        FullPageModel.flushIndexModel();
        FooterModel footerModel = FullPageModel.getFooterModel(pageService);
        model.addAttribute("cates", footerModel.getCategory());
        return "adminx/pageconfig-footer";
    }
}
