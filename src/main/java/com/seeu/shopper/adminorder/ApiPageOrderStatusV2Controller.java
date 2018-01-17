package com.seeu.shopper.adminorder;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.shopper.eventcenter.OrderPaidEvent;
import com.seeu.shopper.eventcenter.OrderSendEvent;
import com.seeu.shopper.eventcenter.UserRegistEvent;
import com.seeu.shopper.order.iservice.OrderStatus;
import com.seeu.shopper.order.iservice.OrderStatusService;
import com.seeu.shopper.order.model.OrderBasic;
import com.seeu.shopper.order.model.OrderLog;
import com.seeu.shopper.order.model.OrderShip;
import com.seeu.shopper.order.service.OrderBasicService;
import com.seeu.shopper.order.service.OrderLogService;
import com.seeu.shopper.order.service.OrderShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by neo on 29/08/2017.
 * <p>
 * 修改订单状态
 */
@RestController
@RequestMapping("/api/admin/v1/order")
public class ApiPageOrderStatusV2Controller {

    @Autowired
    OrderStatusService orderStatusService;

    @Resource
    OrderBasicService orderBasicService;
    @Resource
    OrderLogService orderLogService;
    @Resource
    ApplicationContext applicationContext;

    /**
     * 确认收到付款
     *
     * @param oid 订单编号
     * @return
     */
    @PostMapping("/paid/{oid}")
    public Result changeStatus(@RequestAttribute("aid") Integer aid,
                               @RequestAttribute("name") String name,
                               @PathVariable(value = "oid", required = true) String oid) {
        // 验证参数，已经自动检验
        // 查询当前状态
        OrderBasic orderBasic = orderBasicService.findBy("oid", oid);
        if (orderBasic == null) {
            return ResultGenerator.genNoContentResult("No such order information.");
        }
        // 尝试修改，看是否能修改为已支付状态
        boolean result = orderStatusService.canChangeStatus(orderBasic.getStatus(), OrderStatus.PAYED);
        if (result) {
            // 可以修改
            OrderBasic newOrderBasic = new OrderBasic();
            newOrderBasic.setOid(oid);
            newOrderBasic.setStatus(OrderStatus.PAYED);
            int i = orderBasicService.update(newOrderBasic);
            if (0 != i) {
                // log it
                OrderLog log = new OrderLog();
                log.setOid(oid);
                log.setType("收款确认");
                log.setDetail("已确认收款到账");
                log.setUserId(aid);
                log.setUserType(2);
                log.setUserName(name);
                orderLogService.save(log);

                // listener
                applicationContext.publishEvent(new OrderPaidEvent(this, oid));
            }
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("Current status [" + orderStatusService.getStatusNameEnglish(orderBasic.getStatus()) + "] cannot change to [ paid ]");
        }
        //
    }


    @Resource
    OrderShipService orderShipService;

    /**
     * 发货
     *
     * @param oid 订单编号
     * @return
     */
    @PostMapping("/send-product/{oid}/{shipcode}")
    public Result fahuo(@RequestAttribute("aid") Integer aid,
                        @RequestAttribute("name") String name,
                        @PathVariable(value = "oid", required = true) String oid,
                        @PathVariable(value = "shipcode", required = true) String shipcode) {
        // 验证参数，已经自动检验
        // 查询当前状态
        OrderBasic orderBasic = orderBasicService.findBy("oid", oid);
        if (orderBasic == null) {
            return ResultGenerator.genNoContentResult("No such order information.");
        }
        if (!orderStatusService.canChangeStatus(orderBasic.getStatus(), OrderStatus.SHIPING)) {
            return ResultGenerator.genFailResult("Cannot change status [" + orderStatusService.getStatusNameEnglish(orderBasic.getStatus()) + "] to [ 已发货 ].");
        }
        // 修改订单状态
        OrderBasic newOrderBasic = new OrderBasic();
        newOrderBasic.setOid(oid);
        newOrderBasic.setStatus(OrderStatus.SHIPING);
        orderBasicService.update(newOrderBasic);

        //
        OrderShip ship = new OrderShip();
        ship.setOid(oid);
        ship.setShipCode(shipcode);
        int i = orderShipService.update(ship);
        if (0 == i) {
//            orderShipService.save(ship); 不应该在这里吞掉不正常的处理，直接告诉用户无物流信息
            return ResultGenerator.genNoContentResult("No ship information. So you cannot add [快递单号] into this data.");
        }
        // log it
        OrderLog log = new OrderLog();
        log.setOid(oid);
        log.setType("发货");
        log.setDetail("添加快递单号：" + shipcode);
        log.setUserId(aid);
        log.setUserType(2);
        log.setUserName(name);
        orderLogService.save(log);

        // listener
        applicationContext.publishEvent(new OrderSendEvent(this, oid, shipcode));


        return ResultGenerator.genSuccessResult();
    }

    /**
     * 修改物流（快递）单号
     *
     * @param oid 订单编号
     * @return
     */
    @PostMapping("/shipcode/{oid}/{shipcode}")
    public Result changeStatus2(@RequestAttribute("aid") Integer aid,
                                @RequestAttribute("name") String name,
                                @PathVariable(value = "oid", required = true) String oid,
                                @PathVariable(value = "shipcode", required = true) String shipcode) {
        // 验证参数，已经自动检验
        // 查询当前状态
        OrderBasic orderBasic = orderBasicService.findBy("oid", oid);
        if (orderBasic == null) {
            return ResultGenerator.genNoContentResult("No such order information.");
        }
        //
        OrderShip ship = new OrderShip();
        ship.setOid(oid);
        ship.setShipCode(shipcode);
        int i = orderShipService.update(ship);
        if (0 == i) {
//            orderShipService.save(ship); 不应该在这里吞掉不正常的处理，直接告诉用户无物流信息
            return ResultGenerator.genNoContentResult("No ship information. So you cannot add [快递单号] into this data.");
        }
        // log it
        OrderLog log = new OrderLog();
        log.setOid(oid);
        log.setType("修改物流单号");
        log.setDetail("修改物流单号为：" + shipcode);
        log.setUserId(aid);
        log.setUserType(2);
        log.setUserName(name);
        orderLogService.save(log);

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 令该订单强制失败
     *
     * @param oid
     * @return
     */
    @PostMapping("/force-finish/{oid}")
    public Result finishAll(@RequestAttribute("aid") Integer aid,
                            @RequestAttribute("name") String name,
                            @PathVariable(value = "oid", required = true) String oid) {
        OrderBasic newOrderBasic = new OrderBasic();
        newOrderBasic.setOid(oid);
        newOrderBasic.setStatus(OrderStatus.FORCE_CANCEL);
        int i = orderBasicService.update(newOrderBasic);
        if (0 == i) {
            return ResultGenerator.genNoContentResult("No such order information.");
        }
        // log it
        OrderLog log = new OrderLog();
        log.setOid(oid);
        log.setType("强制关闭交易");
        log.setDetail("已成功关闭该交易，该交易将不可操作");
        log.setUserId(aid);
        log.setUserType(2);
        log.setUserName(name);
        orderLogService.save(log);

        return ResultGenerator.genSuccessResult();
    }

}
