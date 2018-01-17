package com.seeu.shopper.adminproduct.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by neo on 26/08/2017.
 */
@Controller
@RequestMapping("/adminx")
public class PageProUpdateController {
    @RequestMapping("product-update.html")
    public String proCate() {
        return "adminx/product-update";
    }
}
