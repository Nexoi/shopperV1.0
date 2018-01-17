//package com.seeu.shopper.adminproduct.product;
//
//import com.seeu.core.Result;
//import com.seeu.core.ResultGenerator;
//import com.seeu.shopper.config.model.Config;
//import com.seeu.shopper.config.service.ConfigService;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import tk.mybatis.mapper.entity.Condition;
//
//import javax.annotation.Resource;
//
///**
// * Created by neo on 28/08/2017.
// * <p>
// * 配置信息修改
// */
//@RestController
//@RequestMapping("/api/admin/v1/config")
//public class ApiPageConfigController {
//
//    @Resource
//    ConfigService configService;
//
//    /**
//     * 测试
//     */
//
//    @PostMapping
//    public Result ccc(@RequestBody Config config) {
//        if (config.getAttributeName() == null || config.getAttributeValue() == null) {
//            return ResultGenerator.genFailResult("传入参数不完整");
//        }
//        // 尝试更新
//        Condition condition = new Condition(Config.class);
//        condition.createCriteria().andCondition("attribute_name = '" + config.getAttributeName()+"'");
//        int i = configService.updateCondition(config, condition);
//        if (i == 0) {// 说明更新条数为 0 ，未更新
//            configService.save(config);
//        }
//        return ResultGenerator.genSuccessResult();
//    }
//
//}
