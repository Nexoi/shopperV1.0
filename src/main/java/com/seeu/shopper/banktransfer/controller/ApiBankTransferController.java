package com.seeu.shopper.banktransfer.controller;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.shopper.banktransfer.service.BankTransferService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by neo on 17/09/2017.
 */
@RestController
@RequestMapping("/api/admin/v1/banktransfer")
public class ApiBankTransferController {
    @Resource
    BankTransferService bankTransferService;

    @PostMapping("/received")
    public Result changeStatus(@RequestAttribute("aid") Integer aid, @RequestParam("oid") String oid) {
        // 查询订单信息，尝试修改状态
        return bankTransferService.transferSuccessBtn(oid) ? ResultGenerator.genSuccessResult() : ResultGenerator.genFailResult("订单不存在或不可修改为【已支付】状态");
    }
}
