package com.seeu.shopper.admin.baseconfig;

import com.seeu.shopper.config.iservice.FullConfigModel;
import com.seeu.shopper.config.model.Config;
import com.seeu.shopper.config.service.ConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 基础配置信息，键值对
 *
 * Created by neo on 20/09/2017.
 */
@Controller
@RequestMapping("/adminx")
public class PageBaseConfigController {
    @Resource
    ConfigService configService;

    @RequestMapping("config.html")
    public String mailPage(Model model) {
        List<Config> configList = configService.findAll();
        model.addAttribute("list",configList);
        return "adminx/config";
    }
}
