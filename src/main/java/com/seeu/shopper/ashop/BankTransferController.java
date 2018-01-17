package com.seeu.shopper.ashop;

import com.seeu.shopper.order.model.OrderBasic;
import com.seeu.shopper.order.model.OrderProduct;
import com.seeu.shopper.order.service.OrderBasicService;
import com.seeu.shopper.order.service.OrderProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by neo on 01/09/2017.
 */
@Controller
public class BankTransferController {
    @Resource
    OrderBasicService orderBasicService;

    @Resource
    OrderProductService orderProductService;

    @RequestMapping("/banktransfer")
    public String bankInfo(Model model, @RequestParam("oid") String oid) {
        // 查找订单记录，填充信息
        OrderBasic orderBasic = orderBasicService.findBy("oid", oid);
        if (orderBasic == null) {
            // 没有订单记录
            return "404";
        }
        Condition condition = new Condition(OrderProduct.class);
        condition.createCriteria().andCondition("oid = " + oid);
        List<OrderProduct> productList = orderProductService.findByCondition(condition);
        model.addAttribute("products", productList);

        model.addAttribute("unit", orderBasic.getUnit());
        model.addAttribute("price", orderBasic.getPrice().add(orderBasic.getShipPrice()));
        model.addAttribute("oid", orderBasic.getOid());
        return "shop/banktransfer";
    }
}
