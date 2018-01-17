package com.seeu.shopper.admin.tools;

import com.seeu.shopper.admin.service.IPageStoreService;
import com.seeu.shopper.store.model.PageStore;
import com.seeu.shopper.store.service.PageStoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by neo on 23/09/2017.
 */
@Controller
@RequestMapping("/adminx")
public class WebSiteCreatorController {
    @Resource
    IPageStoreService iPageStoreService;
    @Resource
    PageStoreService pageStoreService;


    @RequestMapping("/websitecreate.html")
    public String siteCreatorPage(){
        return "adminx/websitecreate";
    }

    @RequestMapping("/websitelist.html")
    public String siteListPage(Model model){
        List<PageStore> pageStoreList = iPageStoreService.queryListAll();
        model.addAttribute("storeList",pageStoreList);
        return "adminx/websitelist";
    }

    @RequestMapping("/website")
    public String siteEditPage(Model model,@RequestParam("id")Integer id){

        PageStore pageStore = pageStoreService.findById(id);
        if (pageStore==null) return "404";

        model.addAttribute("store",pageStore);

        return "adminx/website-edit";
    }

}
