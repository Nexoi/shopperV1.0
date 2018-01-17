package com.seeu.shopper.ashop.mainpage;

import com.seeu.shopper.store.model.PageStore;
import com.seeu.shopper.store.service.PageStoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by neo on 23/09/2017.
 */
@Controller
@RequestMapping("/web")
public class ModulePageController {
    @Resource
    PageStoreService pageStoreService;

    @RequestMapping("/{name}")
    public String webPage(Model model, @PathVariable("name") String name) {
        PageStore pageStore = pageStoreService.findBy("name", name);
        if (pageStore == null) return "404";
        model.addAttribute("store", pageStore);
        return "module";
    }
}
