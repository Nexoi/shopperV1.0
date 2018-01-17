package com.seeu.shopper.ashop.mainpage;

import com.seeu.shopper.support.model.Support;
import com.seeu.shopper.support.model.SupportCate;
import com.seeu.shopper.support.service.SupportCateService;
import com.seeu.shopper.support.service.SupportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liuyi on 2017/9/12.
 *
 * 废掉
 */
@Controller
@RequestMapping("/disabled")
public class SupportController {
    @Resource
    SupportService supportService;
    @Resource
    SupportCateService supportCateService;

    /**
     * 重定向到 第一个 分类下
     *
     * @return
     */
    @RequestMapping("/support")
    public String supportAllPage() {
        List<SupportCate> cates = supportCateService.findAll();
        if (cates.size() == 0) {
            return "404";
        }
        return "redirect:/support/" + cates.get(0).getId();
    }


    @RequestMapping("/support/{cate}")
    public String supportCatePage(ModelMap map, @PathVariable("cate") Integer cateID) {

        // 注入实际面板信息
        SupportCate cate = supportCateService.findById(cateID);
        if (cate == null) {
            return "404";
        }
        map.addAttribute("cateName", cate.getName());

        Condition condition = new Condition(Support.class);
        condition.createCriteria().andCondition("cateID = " + cate.getId());
        List<Support> supportList = supportService.findByCondition(condition);
        map.addAttribute("supportList", supportList);

        // 注入分类栏信息
        List<SupportCate> cates = supportCateService.findAll();
        if (cates.size() == 0) {
            return "404";
        }
        map.addAttribute("cateList", cates);

        return "shop/support";
    }
}
