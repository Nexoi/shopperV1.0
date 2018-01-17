package com.seeu.shopper.third.web;

import com.seeu.shopper.third.model.Third1;
import com.seeu.shopper.third.service.Third1Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by neo on 17/10/2017.
 */
@Controller
public class ThirdPartController {
    @Resource
    Third1Service third1Service;

    @RequestMapping("/api/v1/flushdata/page.html")
    public String flushDataPage(Model model) {
        List<Third1> third1List = third1Service.findAll();
        model.addAttribute("list", third1List);
        return "third/third1";
    }
}
