package com.seeu.shopper.third.api;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.shopper.third.model.Third1;
import com.seeu.shopper.third.service.Third1Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by neo on 09/10/2017.
 */
@RestController
public class ThirdPartApi {
    @Resource
    Third1Service third1Service;

    /**
     * 原则上开放所有人都可以获取
     *
     * @return
     */
    @GetMapping("/api/v1/third/flushdata")
    public Result third1() {
        List<Third1> third1List = third1Service.findAll();
        return ResultGenerator.genSuccessResult(third1List);
    }

    @PutMapping("/api/v1/third/flushdata/{id}")
    public Result third1Update(@PathVariable Integer id, @RequestParam String imgUrl, @RequestParam String linkUrl) {
        Third1 third1 = new Third1();
        third1.setId(id);
        third1.setImgurl(imgUrl);
        third1.setLinkurl(linkUrl);
        int i = third1Service.update(third1);
        return i != 0 ? ResultGenerator.genSuccessResult() : ResultGenerator.genFailResult("无此记录可更新");
    }
}
