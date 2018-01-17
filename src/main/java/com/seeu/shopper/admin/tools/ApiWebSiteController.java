package com.seeu.shopper.admin.tools;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.shopper.ashop.domain.FullPageModel;
import com.seeu.shopper.store.model.PageStore;
import com.seeu.shopper.store.service.PageStoreService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by neo on 23/09/2017.
 */
@RestController
@RequestMapping("/api/admin/v1/website")
public class ApiWebSiteController {
    @Resource
    PageStoreService pageStoreService;

    @PostMapping
    public Result create(@RequestParam("name") String name,
                         @RequestParam("content") String content) {
        if (name.length() == 0 || content.length() == 0) {
            return ResultGenerator.genFailResult("Content cannot be empty.");
        }
        // 先查询一遍名字是否重复
        PageStore pageStore = pageStoreService.findBy("name", name);
        if (pageStore != null) return ResultGenerator.genFailResult("名称不可重复");

        PageStore store = new PageStore();
        store.setName(name);
        store.setHtml(content);
        pageStoreService.save(store);

        // 如果该页面是 support 页，则刷新缓存
        FullPageModel.flushSupportCates();
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable("id") Integer id,
                         @RequestParam("name") String name,
                         @RequestParam("content") String content) {
        if (name.length() == 0 || content.length() == 0) {
            return ResultGenerator.genFailResult("Content cannot be empty.");
        }
        // 先查询一遍名字是否重复
        PageStore pageStore = pageStoreService.findById(id);
        if (pageStore == null) return ResultGenerator.genNoContentResult("无此页面记录");

        PageStore store = new PageStore();
        store.setId(id);
        store.setName(name);
        store.setHtml(content);
        store.setUpdateTime(new Date());
        pageStoreService.update(store);

        // 如果该页面是 support 页，则刷新缓存
        FullPageModel.flushSupportCates();
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result del(@PathVariable("id") Integer id) {
        // 先查询一遍名字是否重复
        pageStoreService.deleteById(id);

        // 如果该页面是 support 页，则刷新缓存
        FullPageModel.flushSupportCates();
        return ResultGenerator.genSuccessResult();
    }
}
