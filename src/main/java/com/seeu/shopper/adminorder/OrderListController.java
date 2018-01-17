package com.seeu.shopper.adminorder;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seeu.shopper.ashop.service.NormFormService;
import com.seeu.shopper.ashop.service.UnitService;
import com.seeu.shopper.order.iservice.OrderStatus;
import com.seeu.shopper.order.iservice.OrderStatusService;
import com.seeu.shopper.order.model.*;
import com.seeu.shopper.order.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by neo on 17/09/2017.
 */
@Controller
@RequestMapping("/adminx")
public class OrderListController {
    @Resource
    OrderBasicService orderBasicService;

    @RequestMapping("/order-list.html")
    public String orderList(Model model,
                            @RequestParam(value = "wd", required = false) String wd,
                            @RequestParam(value = "page", required = false) Integer page,
                            @RequestParam(value = "size", required = false) Integer size,
                            @RequestParam(value = "by", required = false) String bywhat) {
        if (page == null) page = 1;
        if (size == null) size = 20;
        // 处理筛选条件
        String property = null;
        if (bywhat == null) {
            bywhat = "oid";
        }
        switch (bywhat) {
            case "oid":
                property = "oid";
                break;
            case "username":
                property = "userName";
                break;
            default:
                property = "oid";
        }
        // 查询
        PageInfo pageInfo = null;
        if (wd != null && wd.trim().length() != 0) {
            PageHelper.startPage(page, size, "update_time desc");
            Condition condition = new Condition(OrderBasic.class);
            condition.createCriteria().andLike(property, "%" + wd + "%");
            List<OrderBasic> orderBasics = orderBasicService.findByCondition(condition);
            pageInfo = new PageInfo(orderBasics);
        } else {
            PageHelper.startPage(page, size, "update_time desc");
            List<OrderBasic> infoList = orderBasicService.findAll();
            pageInfo = new PageInfo(infoList);
        }

        model.addAttribute("word", wd == null ? "" : wd);
        model.addAttribute("pagelist", pageInfo.getList());
        model.addAttribute("page", pageInfo);
        model.addAttribute("index", pageInfo.getNavigatepageNums());// 页码

        return "adminx/order-list";
    }


    @Resource
    OrderProductService orderProductService;
    @Resource
    OrderReceiverService orderReceiverService;
    @Resource
    OrderShipService orderShipService;
    @Resource
    OrderLogService orderLogService;
    @Resource
    NormFormService normFormService;
    @Resource
    OrderStatusService orderStatusService;
    @Resource
    UnitService unitService;

    /**
     * 根据订单号查询订单信息
     * 包含：
     * order_basic
     * order_products
     * order_receiver
     * order_ship
     * order_log
     *
     * @param model
     * @param oid
     * @return
     */
    @RequestMapping("/order-detail.html")
    public String orderDetail(Model model,
                              @RequestParam(value = "oid", required = true) String oid) {
        OrderBasic orderBasic = orderBasicService.findBy("oid", oid);
        if (orderBasic == null) {
            model.addAttribute("code", 404);
            model.addAttribute("message", "订单信息查询不到，请确认后操作");
            return "adminx/info";
        }

        Condition condition = new Condition(OrderProduct.class);
        condition.createCriteria().andCondition("oid = " + oid);
        List<OrderProduct> products = orderProductService.findByCondition(condition);

        for (OrderProduct pro : products) {
            Map<Integer, String> map = normFormService.getNormName(pro.getNorms());
            String norms = "";
            for (String n : map.values()) {
                norms += "[" + n + "] ";
            }
            pro.setNorms(norms);
        }

        OrderReceiver receiver = orderReceiverService.findBy("oid", oid);

        OrderShip ship = orderShipService.findBy("oid", oid);

        List<OrderLog> logs = orderLogService.findByCondition(condition);

        model.addAttribute("basic", orderBasic);
        model.addAttribute("products", products);
        model.addAttribute("rc", receiver == null ? new OrderReceiver() : receiver);
        model.addAttribute("ship", ship == null ? new OrderShip() : ship);
        model.addAttribute("logs", logs);

        // 状态信息，确定哪些 button 能亮
        int status = orderBasic.getStatus();
        model.addAttribute("shoukuan", !orderStatusService.hasPaid(status));
        model.addAttribute("fahuo", status == OrderStatus.PAYED);// 支付成功即可发货
        model.addAttribute("shibai", status != OrderStatus.FORCE_CANCEL && status != OrderStatus.FINISH && status != OrderStatus.SHIPEND_FINISHED);

        return "adminx/order-detail";
    }

    @RequestMapping("order-print.html")
    public String orderPrint(Model model,
                             @RequestParam(value = "oid", required = true) String oid) {
        OrderBasic orderBasic = orderBasicService.findBy("oid", oid);
        if (orderBasic == null) {
            model.addAttribute("code", 404);
            model.addAttribute("message", "订单信息查询不到，请确认后操作");
            return "adminx/info";
        }
        Condition condition = new Condition(OrderProduct.class);
        condition.createCriteria().andCondition("oid = " + oid);
        List<OrderProduct> products = orderProductService.findByCondition(condition);

        for (OrderProduct pro : products) {
            Map<Integer, String> map = normFormService.getNormName(pro.getNorms());
            String norms = "";
            for (String n : map.values()) {
                norms += "[" + n + "] ";
            }
            pro.setNorms(norms);
        }

        OrderReceiver receiver = orderReceiverService.findBy("oid", oid);

        OrderShip ship = orderShipService.findBy("oid", oid);

        model.addAttribute("unit", unitService.getUnit(orderBasic.getUnit())); // return as 'US$'
        model.addAttribute("now", new Date());
        model.addAttribute("basic", orderBasic);
        model.addAttribute("products", products);
        model.addAttribute("rc", receiver == null ? new OrderReceiver() : receiver);
        model.addAttribute("ship", ship == null ? new OrderShip() : ship);


        return "adminx/order-print";
    }
}
