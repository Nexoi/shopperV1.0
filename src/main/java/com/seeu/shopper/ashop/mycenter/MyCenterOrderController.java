package com.seeu.shopper.ashop.mycenter;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seeu.shopper.ashop.service.NormFormService;
import com.seeu.shopper.order.iservice.OrderStatusService;
import com.seeu.shopper.order.model.OrderBasic;
import com.seeu.shopper.order.model.OrderProduct;
import com.seeu.shopper.order.model.OrderReceiver;
import com.seeu.shopper.order.service.OrderBasicService;
import com.seeu.shopper.order.service.OrderProductService;
import com.seeu.shopper.order.service.OrderReceiverService;
import com.seeu.shopper.ship.model.Ship;
import com.seeu.shopper.ship.service.ShipService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by neo on 10/09/2017.
 */
@Controller
public class MyCenterOrderController {

    @Resource
    OrderBasicService orderBasicService;
    @Resource
    OrderStatusService orderStatusService;
    @Resource
    OrderReceiverService orderReceiverService;
    @Resource
    ShipService shipService;
    @Resource
    OrderProductService orderProductService;
    @Resource
    NormFormService normFormService;

    /**
     * 每页 7 条记录，钉死了。
     *
     * @param model
     * @param select 1\2\3\4\5
     * @param page
     * @return
     */
    @RequestMapping("/mycenter/order")
    public String order(Model model,
                        @RequestAttribute("uid") Integer uid,
                        @RequestParam(value = "select", required = false) Integer select,
                        @RequestParam(value = "page", required = false) Integer page) {
        // 筛选条件
        String sql = "";

        if (select == null) select = 99;
        if (page == null) page = 1;
        switch (select) {
            case 1: // Waiting for payment
                sql = " AND ( status = 2 or status = 201 or status = 211 or status = 212 or status = 213 )";
                break;
            case 2: // Processing
                sql = " AND ( status = 3 or status = 300 or status = 301 or status = 302 or status = 321 )";
                break;
            case 3: // Delivery
                sql = " AND ( status = 4 or status = 400 or status = 401 or status = 402 or status = 403 or status = 421 or status = 4201 )";
                break;
            case 4: // Completed Order
                sql = " AND ( status = 5 or status = 0 )";
                break;
            case 5:// Canceled Order
                sql = " AND ( status = -1 )";
                break;
            default: // All Order
                select = 99;
                sql = "";
        }

        PageHelper.startPage(page, 7);
        Condition condition = new Condition(OrderBasic.class);
        condition.createCriteria().andCondition("uid = " + uid + sql);
        condition.setOrderByClause("create_time desc");
        condition.orderBy("createTime").desc();
        List<OrderBasic> orderBasicList = orderBasicService.findByCondition(condition);
        // 替换状态数据为文字显示
        for (OrderBasic orderBasic : orderBasicList) {
            // 寄存在 userName 里面 [status]
            orderBasic.setUserName(orderStatusService.getStatusNameEnglish(orderBasic.getStatus()));
            // 将是否支付信息寄存在 quantity 里面
            if (orderStatusService.hasPaid(orderBasic.getStatus()))
                orderBasic.setQuantity(1);
            else
                orderBasic.setQuantity(0);
        }
        PageInfo pageInfo = new PageInfo(orderBasicList);


        model.addAttribute("pagelist", pageInfo.getList());
        model.addAttribute("page", pageInfo);
        model.addAttribute("select", select);

        return "shop/mycenter/order";
    }


    /**
     * 必须只能查询自己的定单
     *
     * @param model
     * @param uid
     * @param oid
     * @return
     */
    @RequestMapping("/mycenter/order/{oid}")
    public String orderDetail(Model model,
                              @RequestAttribute("uid") Integer uid,
                              @PathVariable("oid") String oid) {
        if (oid == null || oid.length() != 18) {
            model.addAttribute("code", 404);
            model.addAttribute("message", "#" + oid + " - Order Information not found.");
            return "shop/info";
        }
        OrderBasic basic = orderBasicService.findBy("oid", oid);
        if (basic == null) {
            model.addAttribute("code", 404);
            model.addAttribute("message", "#" + oid + " - Order Information not found.");
            return "shop/info";
        }
        OrderReceiver receiver = orderReceiverService.findBy("oid", oid);
        Ship ship = shipService.findById(basic.getShipId());
        Boolean hasPaid = orderStatusService.hasPaid(basic.getStatus());
        Boolean canShowReceivedBtn = orderStatusService.canShowReceivedBtn(basic.getStatus());
        String status = orderStatusService.getStatusNameEnglish(basic.getStatus());


        // 商品信息
        Condition condi = new Condition(OrderProduct.class);
        condi.createCriteria().andCondition("oid = " + oid);
        List<OrderProduct> products = orderProductService.findByCondition(condi);
        for (OrderProduct pro : products) {
            Map<?, String> map = normFormService.getNormName(pro.getNorms());
            String norm = "";
            for (String v : map.values()) {
                norm += "[" + v + "] ";
            }
            pro.setNorms(norm);
        }
        model.addAttribute("products", products);

        model.addAttribute("status", status);
        model.addAttribute("hasPaid", hasPaid);
        model.addAttribute("receivedBtn", canShowReceivedBtn);
        model.addAttribute("ship", ship);
        model.addAttribute("rc", receiver);
        model.addAttribute("order", basic);
        return "shop/mycenter/order_detail";
    }
}
