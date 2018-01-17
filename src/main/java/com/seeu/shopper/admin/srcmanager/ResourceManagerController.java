package com.seeu.shopper.admin.srcmanager;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seeu.shopper.store.model.DbStore;
import com.seeu.shopper.store.service.DbStoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by neo on 23/09/2017.
 */
@Controller
@RequestMapping("/adminx")
public class ResourceManagerController {

    @Resource
    DbStoreService dbStoreService;

    /**
     * 资源列表，分页
     *
     * @return
     */
    @RequestMapping("/resources.html")
    public String resourcesPage(Model model,
                                @RequestParam(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        PageHelper.startPage(page, 20, "create_time desc");
        List<DbStore> dbStoreList = dbStoreService.findAll();
        PageInfo pageInfo = new PageInfo(dbStoreList);
        model.addAttribute("pagelist", pageInfo.getList());
        model.addAttribute("page", pageInfo);
        model.addAttribute("index", pageInfo.getNavigatepageNums());// 页码
        return "adminx/resource-manager";
    }

}
