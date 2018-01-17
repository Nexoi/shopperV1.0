package com.seeu.shopper.ashop.mycenter;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.shopper.order.iservice.OrderStatus;
import com.seeu.shopper.order.iservice.OrderStatusService;
import com.seeu.shopper.order.model.OrderBasic;
import com.seeu.shopper.order.model.OrderLog;
import com.seeu.shopper.order.model.OrderReceiver;
import com.seeu.shopper.order.service.OrderBasicService;
import com.seeu.shopper.order.service.OrderLogService;
import com.seeu.shopper.order.service.OrderProductService;
import com.seeu.shopper.order.service.OrderReceiverService;
import com.seeu.shopper.product.model.Product;
import com.seeu.shopper.product.model.ProductComment;
import com.seeu.shopper.product.service.ProductCommentService;
import com.seeu.shopper.product.service.ProductService;
import com.seeu.shopper.user.model.UserAddress;
import com.seeu.shopper.user.service.UserAddressService;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by neo on 11/09/2017.
 * <p>
 * 因为地址信息前端写得有点奇葩，所以用 ajax 处理
 * <p>
 * 包含：删除订单、评论商品
 */
@RestController
@RequestMapping("/api/shop/v1/ct/order")
public class ApiMyOrderController {
    @Resource
    OrderBasicService orderBasicService;
    @Resource
    OrderStatusService orderStatusService;
    @Resource
    ProductCommentService productCommentService;
    @Resource
    ProductService productService;
    @Resource
    OrderLogService orderLogService;
//    @Resource
//    OrderReceiverService orderReceiverService;
//    @Resource
//    OrderProductService orderProductService;

    @DeleteMapping("/{oid}")
    public Result deleteOrder(@RequestAttribute("uid") Integer uid,
                              @RequestAttribute("email") String email,
                              @PathVariable("oid") String oid) {
        if (oid == null || oid.length() != 18) return ResultGenerator.genFailResult("No such order information.");
        Condition condition = new Condition(OrderBasic.class);
        condition.createCriteria().andCondition("oid = " + oid + " AND uid = " + uid);
        List<OrderBasic> orderBasics = orderBasicService.findByCondition(condition);
        if (orderBasics.size() == 0) {
            return ResultGenerator.genNoContentResult("No such order information.");
        }
        if (orderStatusService.hasPaid(orderBasics.get(0).getStatus())) {
            // 如果已经支付，则不能取消
            return ResultGenerator.genFailResult("Order has been paid. Cannot cancel.");
        }
        OrderBasic orderBasic = new OrderBasic();
        orderBasic.setOid(orderBasics.get(0).getOid());
        orderBasic.setStatus(OrderStatus.CANCEL);  // 取消订单
        orderBasicService.update(orderBasic);
        // log it
        OrderLog log = new OrderLog();
        log.setOid(oid);
        log.setType("取消订单");
        log.setDetail("用户自行取消");
        log.setUserId(uid);
        log.setUserType(1);
        log.setUserName(email);
        orderLogService.save(log);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 该方法有漏洞：未对用户是否购买该商品做判断，用户可能会存在刷评论现象
     *
     * @param pid
     * @param uid
     * @param email
     * @param comment
     * @param star    0-5
     * @return
     */
    @PostMapping("/comment/{pid}")
    public Result comment(@PathVariable("pid") Integer pid,
                          @RequestAttribute("uid") Integer uid,
                          @RequestAttribute("email") String email,
                          @RequestParam("comment") String comment,
                          @RequestParam("star") Integer star) {
        if (star > 5 || star < 0) star = 5;
        // 如果商品不存在
        Product product = productService.findById(pid);
        if (product == null) return ResultGenerator.genFailResult("No such product information.");

        ProductComment productComment = new ProductComment();
        productComment.setPid(pid);
        productComment.setUid(uid);
        productComment.setName(email);
        productComment.setFatherId(-1);
        productComment.setContentHtml(comment == null ? "empty" : comment.length() > 450 ? comment.substring(0, 450) : comment);
        productComment.setStar(BigDecimal.valueOf(star <= 5 && star >= 0 ? star * 20 : 100));
        productCommentService.save(productComment);

        // 更新 product 星星
        Product p = new Product();
        p.setPid(pid);
        switch (star) {
            case 0:
            case 1:
                p.setStar1Num(product.getStar1Num() + 1);
                break;
            case 2:
                p.setStar2Num(product.getStar2Num() + 1);
                break;
            case 3:
                p.setStar3Num(product.getStar3Num() + 1);
                break;
            case 4:
                p.setStar4Num(product.getStar4Num() + 1);
                break;
            case 5:
                p.setStar5Num(product.getStar5Num() + 1);
                break;
            default:
                p.setStar5Num(product.getStar5Num() + 1);
        }
        p.setStarsNum(product.getStarsNum() + 1);
        BigDecimal zongfen = product.getStars().multiply(BigDecimal.valueOf(product.getStarsNum())).add(BigDecimal.valueOf(star * 20));
        p.setStars(zongfen.divide(BigDecimal.valueOf(p.getStarsNum()), 2, BigDecimal.ROUND_UP));//0-100
        p.setStars(p.getStars().doubleValue() < 0 ? BigDecimal.valueOf(0) : p.getStars());
        p.setStars(p.getStars().doubleValue() > 100 ? BigDecimal.valueOf(100) : p.getStars());
        productService.update(p);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping("/{oid}")
    public Result receivedOrder(@RequestAttribute("uid") Integer uid,
                                @RequestAttribute("email") String email,
                                @PathVariable("oid") String oid) {
        if (oid == null || oid.length() != 18) return ResultGenerator.genFailResult("No such order information.");
        Condition condition = new Condition(OrderBasic.class);
        condition.createCriteria().andCondition("oid = " + oid + " AND uid = " + uid);
        List<OrderBasic> orderBasics = orderBasicService.findByCondition(condition);
        if (orderBasics.size() == 0) {
            return ResultGenerator.genNoContentResult("No such order information.");
        }
        if (!orderStatusService.hasPaid(orderBasics.get(0).getStatus())) {
            // 如果已经支付，则不能取消
            return ResultGenerator.genFailResult("Order has not been paid. Cannot finish it.");
        }
        if (orderStatusService.canShowReceivedBtn(orderBasics.get(0).getStatus())) {
            // 状态修改为 5
            OrderBasic basic = new OrderBasic();
            basic.setOid(oid);
            basic.setStatus(OrderStatus.SHIPEND_FINISHED);// 5
            int i = orderBasicService.update(basic);
            if (i != 0) {
                // log it
                OrderLog log = new OrderLog();
                log.setOid(oid);
                log.setType("关闭订单");
                log.setDetail("用户已确认到货，交易成功");
                log.setUserId(uid);
                log.setUserType(1);
                log.setUserName(email);
                orderLogService.save(log);
                return ResultGenerator.genSuccessResult("Order Finish Success!");
            }
        }
        return ResultGenerator.genExceptionResult("Unknow Exception occurred! Please try again later.");
    }
}
