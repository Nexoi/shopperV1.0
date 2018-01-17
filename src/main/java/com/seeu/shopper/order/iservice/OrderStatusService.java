package com.seeu.shopper.order.iservice;

import com.seeu.shopper.config.model.ConfigOrderRule;
import com.seeu.shopper.config.service.ConfigOrderRuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by neo on 29/08/2017.
 */
@Service
public class OrderStatusService {
    @Resource
    ConfigOrderRuleService configOrderRuleService;

    public boolean canChangeStatus(Integer currentStatus, Integer nextStatus) {
        if (nextStatus == 0) {
            return false;
        }
        ConfigOrderRule rule = configOrderRuleService.findById(currentStatus);
        if (rule == null) return false;
        String[] rules = rule.getNextStatus().split(",");
        for (String status : rules) {
            if (status.equals(String.valueOf(nextStatus)))
                return true;
        }
        return false;
    }

    /**
     * 判断是否已经支付
     *
     * @param status
     * @return 如果支付过则返回 true，否则 false
     */
    public boolean hasPaid(Integer status) {
        if (status == null) return false;
        switch (status) {
            case 1:
            case 2:
            case 201:
            case 211:
            case 212:
            case 213:
                return false;
        }
        return true;
    }



    public boolean canShowReceivedBtn(Integer status) {
        if (status == null) return false;
        switch (status) {
            case 4:
            case 400:
            case 421:
                return true;
            default:
                return false;
        }
    }

    public String getStatusNameEnglish(Integer status) {
        if (status == null) return "unknow";
        switch (status) {
            case 0:
            case 5:
            case 201:
            case 4021:
            case 403:
                return "finish";
            case 302:
            case 402:
                return "in refund";
            case 321:
            case 421:
                return "delivery";
            case 2:
            case 211:
            case 212:
            case 213:
                return "wait for payment";
//            case 211:
//                return "transferring";
            case 3:
                return "payment successful & wait for delivery";
            case -1:
            case -2:
                return "cancel";
            case 1:
                return "unpaid";
            case 4:
                return "shipped";
            case 301:
                return "apply for drawback";
            case 400:
                return "apply for refund";
            case 401:
                return "accept refund";
            default:
                return "unknow";
        }
    }

}
