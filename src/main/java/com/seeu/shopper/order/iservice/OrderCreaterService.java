package com.seeu.shopper.order.iservice;

import com.seeu.oauth.OAuthType;
import com.seeu.shopper.adminorder.model.PriceModel;
import com.seeu.shopper.adminorder.model.PriceProductModel;
import com.seeu.shopper.ashop.service.ProductSaleClickService;
import com.seeu.shopper.ashop.service.UnitService;
import com.seeu.shopper.order.model.*;
import com.seeu.shopper.order.service.*;
import com.seeu.shopper.product.model.Product;
import com.seeu.shopper.product.model.ProductStock;
import com.seeu.shopper.product.service.ProductService;
import com.seeu.shopper.product.service.ProductStockService;
import com.seeu.shopper.ship.model.Ship;
import com.seeu.shopper.ship.service.ShipService;
import com.seeu.shopper.user.model.UserAddress;
import com.seeu.shopper.user.model.UserInfo;
import com.seeu.shopper.user.service.UserAddressService;
import com.seeu.shopper.user.service.UserInfoService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by neo on 30/08/2017.
 * <p>
 * 创建订单时提供的帮助类
 */
@Service
@Transactional
public class OrderCreaterService {

    @Resource
    UserInfoService userInfoService;
    @Resource
    OrderBasicService orderBasicService;
    @Resource
    OrderProductService orderProductService;
    @Resource
    OrderReceiverService orderReceiverService;
    @Resource
    UserAddressService addressService;
    @Resource
    ShipService shipService;
    @Resource
    ProductStockService productStockService;
    @Resource
    UnitService unitService;
    @Resource
    OrderShipService orderShipService;
    @Resource
    ProductService productService;
    @Resource
    ProductSaleClickService productSaleClickService;

    /**
     * @param uid
     * @param unit       计价单位
     * @param priceModel
     * @return
     */
    public String createOrderBasic(Integer uid, String unit, PriceModel priceModel) {
        // 设定订单唯一识别编号
        String oid = genOrderID();
        priceModel.setOid(oid);

        // 创建订单
        OrderBasic orderBasic = new OrderBasic();
        orderBasic.setOid(oid);
        orderBasic.setStatus(OrderStatus.UNPAY);
        orderBasic.setUnit(unit);

        orderBasic.setOriginPrice(priceModel.getOriginPrice());
        orderBasic.setSalePrice(priceModel.getSalePrice());
        orderBasic.setPrice(priceModel.getPrice()); // 总价（加入物流信息后需要重新加上物流价格！）
        orderBasic.setSavePrice(priceModel.getSavePrice());

        orderBasic.setWeight(priceModel.getWeight().intValue());

        orderBasic.setCoupon(priceModel.getCoupon());// 优惠券ID，如果有的话
        orderBasic.setCouponType(priceModel.getCouponType());
//        orderBasic.setPayMethod(paymentID);
        // 获取用户信息
        if (uid != 0) {
            UserInfo userInfo = userInfoService.findById(uid);
            orderBasic.setUserName(userInfo.getUserName());
            orderBasic.setUid(uid);
        } else {
            orderBasic.setUserName("[Visitor]");
            orderBasic.setUid(uid);
        }
        orderBasicService.save(orderBasic);
        // 商品信息
        List<OrderProduct> orderProductList = new ArrayList<>();
        for (PriceProductModel priceProductModel : priceModel.getProducts()) {
            Product product = priceProductModel.getProduct();
            String name = product.getName();
            Integer pid = product.getPid();
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setName(name);
            orderProduct.setOid(oid);
            orderProduct.setAmount(priceProductModel.getQuantity());
            orderProduct.setIsDeal(product.getIsDeal());
            orderProduct.setOriginPrice(priceProductModel.getOriginPrice());
            orderProduct.setCurrentPrice(priceProductModel.getPrice());
            orderProduct.setPid(pid);
            orderProduct.setNorms(priceProductModel.getNormValues());
            // save
            orderProductList.add(orderProduct);
        }
        orderProductService.save(orderProductList);
        return oid;
    }


    /**
     * 调用该方法之后可填充／计算最终订单信息
     *
     * @param oid
     * @param paymentID
     * @param shipID
     * @param addressID
     * @param note
     * @return
     */
    public boolean bindOrderInfo(String oid, Integer paymentID, Integer shipID, Integer addressID, String note) {
        OrderBasic orderBasic = orderBasicService.findBy("oid", oid);
        if (orderBasic == null ||
                (orderBasic.getStatus() != OrderStatus.UNPAY && orderBasic.getStatus() != OrderStatus.PAYING)) {
            // 只有这两种状态可以修改订单信息，其余一并驳回
            return false;
        }

        // 支付方式
        orderBasic.setPayMethod(paymentID);

        // 物流方式计价
        orderBasic.setShipId(shipID);
        Integer weight = orderBasic.getWeight();
        Ship ship = shipService.findById(shipID);
        if (ship == null) {
            return false;
        }
        // 计价
        if (weight <= 1000) { // 低于或等于 1KG
//                    ship.getBasePrice();
            ship.setAddiPrice(BigDecimal.valueOf(0));
        } else {
            int weight_ = weight / 1000;
            ship.setAddiPrice(ship.getAddiPrice().multiply(BigDecimal.valueOf(weight_)));
        }
        orderBasic.setShipPrice(ship.getBasePrice().add(ship.getAddiPrice()));
        // 换算物流价格
        orderBasic.setShipPrice(unitService.exchangeUnit(orderBasic.getUnit(), orderBasic.getShipPrice()));
        // 此处不再加上物流价格计入总价，总价 = 物流 + price
//        orderBasic.setPrice(orderBasic.getPrice().add(orderBasic.getShipPrice()));  // 加上物流价格

        orderBasic.setStatus(OrderStatus.PAYING);// 支付中状态
        orderBasicService.update(orderBasic);


        // 物流地址及留言
        UserAddress address = addressService.findById(addressID);
        OrderReceiver receiver = new OrderReceiver();
        receiver.setOid(oid);
        receiver.setCity(address.getCity());
        receiver.setCountry(address.getCountry());
        receiver.setDetail(address.getDetail());
        receiver.setEmail(address.getEmail());
        receiver.setName(address.getName());
        receiver.setPhone(address.getPhone());
        receiver.setNote(note.substring(0, note.length() > 250 ? 250 : note.length()));// 只能存 255 个 VARCHAR 字符
        receiver.setPostcode(address.getPostcode());

        // 已经录入过，则更新即可
        int i = orderReceiverService.update(receiver);
        if (i == 0)
            orderReceiverService.save(receiver);

        return true;
    }


    @Resource
    OrderLogService orderLogService;

    /**
     * 直接下单，包含 createOrder 和 bindOrder 方法所有内容
     * <p>
     * 会添加 log 信息
     *
     * @param uid
     * @param unit
     * @param priceModel
     * @param paymentID
     * @param shipID
     * @param addressID
     * @param note
     */
    public OrderBasic placeOrder(Integer uid, String unit, PriceModel priceModel, Integer paymentID, Integer shipID, Integer addressID, String note) {
// 设定订单唯一识别编号
        String oid = genOrderID();
        priceModel.setOid(oid);

        // 添加日志记录
        OrderLog log = new OrderLog();
        log.setOid(oid);
        log.setType("创建订单");

        // 创建订单
        OrderBasic orderBasic = new OrderBasic();
        orderBasic.setOid(oid);
        orderBasic.setStatus(OrderStatus.UNPAY);
        orderBasic.setUnit(unit);

        orderBasic.setOriginPrice(priceModel.getOriginPrice());
        orderBasic.setSalePrice(priceModel.getSalePrice());
        orderBasic.setPrice(priceModel.getPrice()); // 总价（加入物流信息后需要重新加上物流价格！）
        orderBasic.setSavePrice(priceModel.getSavePrice());

        orderBasic.setQuantity(priceModel.getQuantity());
        orderBasic.setWeight(priceModel.getWeight().intValue());

        orderBasic.setCoupon(priceModel.getCoupon());// 优惠券ID，如果有的话
        orderBasic.setCouponType(priceModel.getCouponType());
//        orderBasic.setPayMethod(paymentID);
        // 获取用户信息
        if (uid != 0) {
            UserInfo userInfo = userInfoService.findById(uid);
            orderBasic.setUserName(userInfo.getUserName());
            orderBasic.setUid(uid);
            log.setUserId(uid);
            log.setUserType(1);
            log.setUserName(userInfo.getUserName());
        } else {
            orderBasic.setUserName("[Visitor]");
            orderBasic.setUid(uid);
            log.setUserId(uid);
            log.setUserType(1);
            log.setUserName("[Visitor]");
        }
//        orderBasicService.save(orderBasic);

        // 商品信息
        List<OrderProduct> orderProductList = new ArrayList<>();
        for (PriceProductModel priceProductModel : priceModel.getProducts()) {
            Product product = priceProductModel.getProduct();
            String name = product.getName();
            Integer pid = product.getPid();
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setName(name);
            orderProduct.setOid(oid);
            orderProduct.setAmount(priceProductModel.getQuantity());
            orderProduct.setIsDeal(product.getIsDeal());
            orderProduct.setOriginPrice(priceProductModel.getOriginPrice());
            orderProduct.setCurrentPrice(priceProductModel.getPrice());
            orderProduct.setPid(pid);
            orderProduct.setNorms(priceProductModel.getNormValues());
            // save
            orderProductList.add(orderProduct);
        }
        orderProductService.save(orderProductList);


        // 支付方式
        orderBasic.setPayMethod(paymentID);

        // 物流方式计价
        orderBasic.setShipId(shipID);
        Integer weight = orderBasic.getWeight();
        Ship ship = shipService.findById(shipID);
        if (ship == null) {
            log.setDetail("订单创建失败，物流信息不存在【物流ID：" + shipID + "】");
            orderLogService.save(log);
            return null;
        }
        // 计价
        if (weight <= 1000) { // 低于或等于 1KG
//                    ship.getBasePrice();
            ship.setAddiPrice(BigDecimal.valueOf(0));
        } else {
            int weight_ = weight / 1000;
            ship.setAddiPrice(ship.getAddiPrice().multiply(BigDecimal.valueOf(weight_)));
        }
        orderBasic.setShipPrice(ship.getBasePrice().add(ship.getAddiPrice()));
        // 换算物流价格
        orderBasic.setShipPrice(unitService.exchangeUnit(orderBasic.getUnit(), orderBasic.getShipPrice()));
        // 此处不再加上物流价格计入总价，总价 = 物流 + price
//        orderBasic.setPrice(orderBasic.getPrice().add(orderBasic.getShipPrice()));  // 加上物流价格

        orderBasic.setStatus(OrderStatus.PAYING);// 支付中状态
        orderBasicService.save(orderBasic);

        // 物流信息
        OrderShip orderShip = new OrderShip();
        orderShip.setOid(oid);
        orderShip.setPrice(orderBasic.getShipPrice());
        orderShip.setShipCode(null);
        orderShip.setShipName(ship.getName());
        orderShip.setShipNote(ship.getNote());
        orderShipService.save(orderShip);

        // 地址及留言
        UserAddress address = addressService.findById(addressID);
        if (address == null) {
            log.setDetail("订单创建失败，用户地址不存在");
            orderLogService.save(log);
            return null;
        }
        OrderReceiver receiver = new OrderReceiver();
        receiver.setOid(oid);
        receiver.setCity(address.getCity());
        receiver.setCountry(address.getCountry());
        receiver.setDetail(address.getDetail());
        receiver.setEmail(address.getEmail());
        receiver.setName(address.getName());
        receiver.setPhone(address.getPhone());
        receiver.setNote(note.substring(0, note.length() > 250 ? 250 : note.length()));// 只能存 255 个 VARCHAR 字符
        receiver.setPostcode(address.getPostcode());
        orderReceiverService.save(receiver);


        log.setDetail("订单创建成功");
        orderLogService.save(log);

        return orderBasic;
    }

    /**
     * 根据商品信息进行库存量的减少，如果有已经无足够库存的商品，则返回 false
     *
     * @param oid
     * @return
     */
    @Transactional
    public boolean stockUpdate(String oid) {
        if (oid == null) return false;
        Condition condition = new Condition(OrderProduct.class);
        condition.createCriteria().andCondition("oid = " + oid);
        List<OrderProduct> products = orderProductService.findByCondition(condition);
        if (products.size() == 0) {
            return false;
        }
        // 更新库存
        Condition condi = new Condition(ProductStock.class);
        for (OrderProduct product : products) {
            Integer pid = product.getPid();
            String norm = product.getNorms();
            Integer qty = product.getAmount();
            if (qty == 0) {
                continue;
            }
            condi.clear();
            condi.createCriteria().andCondition("pid = " + pid + " AND norm_ids = '" + norm + "' AND is_ing = true");
            List<ProductStock> stocks = productStockService.findByCondition(condi);
            if (stocks.size() == 0) {
                return false;
            }
            ProductStock stock = stocks.get(0);
            Integer currentStock = stock.getCurrentStock();
            if (currentStock < qty) {// 如果库存不足
                return false;
            }
            ProductStock newStock = new ProductStock();
            newStock.setSid(stock.getSid());
            newStock.setCurrentStock(currentStock - qty);
            productStockService.update(newStock);

            // 商品销量加 n
            productSaleClickService.salesMuch(pid, qty);
        }
        return true;
    }

    private String genOrderID() {
        // 时间戳 + 随机数
        String time = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        Integer rand = 1000 + (int) (8999 * Math.random());
        return time + rand;
    }
}
