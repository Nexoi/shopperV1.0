package com.seeu.shopper.banktransfer.service;

import com.seeu.shopper.order.iservice.OrderStatus;
import com.seeu.shopper.order.iservice.OrderStatusService;
import com.seeu.shopper.order.model.OrderBasic;
import com.seeu.shopper.order.service.OrderBasicService;
import com.seeu.shopper.user.iservice.UserAmountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by neo on 17/09/2017.
 * <p>
 * 确认转账成功
 */
@Service
public class BankTransferService {
    @Resource
    OrderBasicService orderBasicService;
    @Resource
    OrderStatusService orderStatusService;
    @Resource
    UserAmountService userAmountService;

    /**
     * oid 必须存在且符合要求
     *
     * @param oid
     */
    public boolean transferSuccessBtn(String oid) {
        // 改变订单状态
        OrderBasic basic = orderBasicService.findBy("oid", oid);
        if (basic == null) return false;
        if (orderStatusService.canChangeStatus(basic.getStatus(), OrderStatus.PAYED)) {
            // 修改
            OrderBasic orderBasic = new OrderBasic();
            orderBasic.setOid(oid);
            orderBasic.setStatus(OrderStatus.PAYED);
            orderBasicService.update(orderBasic);
            // 修改用户消费额
            userAmountService.updateAmount(orderBasic.getUid(), orderBasic.getPrice().add(orderBasic.getShipPrice()));
            return true;
        }
        return false;
    }
}
