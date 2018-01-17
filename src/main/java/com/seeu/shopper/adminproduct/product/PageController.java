package com.seeu.shopper.adminproduct.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by neo on 26/08/2017.
 */
@Controller
@RequestMapping("/adminx")
public class PageController {
    @RequestMapping
    public String mainPage1(){
        return "redirect:/adminx/main";
    }

    @RequestMapping("/main")
    public String mainPage() {
        return "adminx/index";
    }

    @RequestMapping("/index.html")
    public String mainPage2() {
        return "adminx/index";
    }
}
