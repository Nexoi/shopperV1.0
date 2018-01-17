package com.seeu.shopper.ashop.mainpage;

import com.seeu.shopper.ashop.domain.FullPageModel;
import com.seeu.shopper.ashop.service.SupportV2Service;
import com.seeu.shopper.store.model.PageStore;
import com.seeu.shopper.store.service.PageStoreService;
import com.seeu.shopper.support.model.Support;
import com.seeu.shopper.support.model.SupportCate;
import com.seeu.shopper.support.service.SupportCateService;
import com.seeu.shopper.support.service.SupportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liuyi on 2017/9/12.
 */
@Controller
public class SupportV2Controller {

    @Resource
    PageStoreService pageStoreService;
    @Resource
    SupportV2Service supportV2Service;

    /**
     * 重定向到 第一个 分类下
     *
     * @return
     */
    @RequestMapping(value = {"/support-index.html", "/support"})
    public String supportAllPage() {
        return "redirect:/support-index-cid-31.html";
    }


    @RequestMapping("/support-index-cid-{id}.html")
    public String supportCatePage(Model model, @PathVariable("id") Integer cateID) {
        List<PageStore> pageStores = FullPageModel.getAllSupportCatesPage(supportV2Service);

        PageStore pageStore = pageStoreService.findBy("name", "support-index-cid-" + cateID + ".html");
        if (pageStore == null) {
            return "404";
        }
        model.addAttribute("cateList", pageStores);
        model.addAttribute("store", pageStore);
        return "shop/support-v2";
    }

    @RequestMapping(value = {"/support-supports-id-{id}.html", "/support-Supports-id-{id}.html"})
    public String supportPage(Model model, @PathVariable("id") Integer id) {
        List<PageStore> pageStores = FullPageModel.getAllSupportCatesPage(supportV2Service);
        PageStore pageStore = pageStoreService.findBy("name", "support-supports-id-" + id + ".html");
        if (pageStore == null) {
            return "404";
        }
        model.addAttribute("cateList", pageStores);
        model.addAttribute("store", pageStore);
        return "shop/support-v2";
    }
}
