//package com.seeu.shopper.adminorder;
//
//import com.seeu.core.Result;
//import com.seeu.core.ResultGenerator;
//import com.seeu.shopper.order.iservice.OrderStatusService;
//import com.seeu.shopper.order.model.OrderBasic;
//import com.seeu.shopper.order.service.OrderBasicService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//
///**
// *
// * 注销，弃用
// * Created by neo on 29/08/2017.
// * <p>
// * 修改订单状态
// */
//@RestController
//@RequestMapping("/api/admin/v1/order/status")
//public class ApiPageOrderStatusController {
//
//    @Autowired
//    OrderStatusService orderStatusService;
//
//    @Resource
//    OrderBasicService orderBasicService;
//
//    /**
//     * @param oid    订单编号
//     * @param status 修改的目标状态
//     * @return
//     */
//    @PutMapping("/{oid}")
//    public Result changeStatus(@PathVariable(value = "oid", required = true) String oid, @RequestParam(value = "status", required = true) Integer status) {
//        // 验证参数，已经自动检验
//        // 查询当前状态
//        OrderBasic orderBasic = orderBasicService.findBy("oid", oid);
//        if (orderBasic == null) {
//            return ResultGenerator.genNoContentResult("No such order information.");
//        }
//        // 尝试修改
//        boolean result = orderStatusService.canChangeStatus(orderBasic.getStatus(), status);
//        if (result) {
//            // 可以修改
//            OrderBasic newOrderBasic = new OrderBasic();
//            newOrderBasic.setOid(oid);
//            newOrderBasic.setStatus(status);
//            orderBasicService.update(newOrderBasic);
//            return ResultGenerator.genSuccessResult();
//        } else {
//            return ResultGenerator.genFailResult("Current status [" + orderBasic.getStatus() + "] cannot change to [" + status + "]");
//        }
//        //
//    }
//}
