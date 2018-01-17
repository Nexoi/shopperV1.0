package com.seeu.shopper.admin.baseconfig;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.shopper.config.iservice.FullConfigModel;
import com.seeu.shopper.config.model.Config;
import com.seeu.shopper.config.service.ConfigService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by neo on 20/09/2017.
 */
@RestController
@RequestMapping("/api/admin/v1/config")
public class ApiBaseConfigController {
    @Resource
    ConfigService configService;

    @PutMapping("/{id}")
    public Result updateItem(@PathVariable("id") Integer id,
                             @RequestParam("value") String value) {
        Config cfg = new Config();
        cfg.setId(id);
        cfg.setAttributeValue(value);
        int i = configService.update(cfg);
        if (0 == i) {
            return ResultGenerator.genNoContentResult("无此配置信息");
        }
        FullConfigModel.flushConfig();
        return ResultGenerator.genSuccessResult();
    }
}
