package com.seeu.shopper.admin.ship;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.filesystem.service.FileUploadService;
import com.seeu.shopper.admin.model.ResourceModel;
import com.seeu.shopper.ship.model.Ship;
import com.seeu.shopper.ship.service.ShipService;
import com.seeu.shopper.store.model.DbStore;
import com.seeu.shopper.store.service.DbStoreService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by neo on 23/09/2017.
 */

@RestController
@RequestMapping("/api/admin/v1/ship")
public class ApiShipController {

    @Resource
    ShipService shipService;

    @PostMapping
    public Result addResource(Ship ship) {
        if (ship == null) return ResultGenerator.genNoContentResult("上传内容不能为空");

        ship.setShipId(null);
        shipService.save(ship);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/edit")
    public Result update(Ship ship) {

        int i = shipService.update(ship);
        if (i == 0) return ResultGenerator.genFailResult("无需更新");
        return ResultGenerator.genSuccessResult();
    }


    @DeleteMapping("/{id}")
    public Result del(@PathVariable("id") Integer id) {
        shipService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }
}
