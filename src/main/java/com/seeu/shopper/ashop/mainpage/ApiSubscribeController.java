package com.seeu.shopper.ashop.mainpage;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.shopper.subscrible.model.Subscrible;
import com.seeu.shopper.subscrible.service.SubscribleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by neo on 28/09/2017.
 */
@RestController
@RequestMapping("/api/shop/v1/subscrible")
public class ApiSubscribeController {
    @Resource
    SubscribleService subscribleService;

    @RequestMapping
    public Result subscrible(@RequestParam("email") String email) {
        // 添加邮箱到数据库
        if (email == null || email.length() < 6 || !email.contains("@"))
            return ResultGenerator.genFailResult("email address is not available!");
        Subscrible subscrible = new Subscrible();
        subscrible.setEmail(email);
        subscribleService.save(subscrible);
        return ResultGenerator.genSuccessResult();
    }
}
