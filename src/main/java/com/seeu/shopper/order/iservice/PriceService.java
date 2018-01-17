//package com.seeu.shopper.order.iservice;
//
//import com.seeu.shopper.adminorder.model.PriceModel;
//import com.seeu.shopper.adminorder.model.PriceModelStatus;
//import com.seeu.shopper.adminorder.model.PriceProductModel;
//import com.seeu.shopper.adminorder.model.ProductQuantityModelU;
//import com.seeu.shopper.coupon.model.Coupon;
//import com.seeu.shopper.coupon.service.CouponService;
//import com.seeu.shopper.product.model.Product;
//import com.seeu.shopper.product.model.ProductCoupon;
//import com.seeu.shopper.product.model.ProductStock;
//import com.seeu.shopper.product.service.ProductCouponService;
//import com.seeu.shopper.product.service.ProductService;
//import com.seeu.shopper.product.service.ProductStockService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import tk.mybatis.mapper.entity.Condition;
//
//import javax.annotation.Resource;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by neo on 29/08/2017.
// * 提供订单计价功能，无论库存是否可用
// *
// * 注：库存量不在条件影响范围内
// */
//@Service
//@Transactional(readOnly = true)
//public class PriceService {
//
//    @Resource
//    ProductService productService;
//    @Resource
//    ProductStockService productStockService;
//
//    @Resource
//    CouponService couponService;
//    @Resource
//    ProductCouponService productCouponService;
//    @Autowired
//    CouponFormService couponFormService;
//
//    /**
//     * 根据商品信息计价，不支持优惠券，支持闪购计价（折扣）
//     * <p>
//     * 如果pid、amount啥为空，不要传进来，没做非空判断
//     *
//     * @param products
//     * @return
//     */
//    public PriceModel calPrice(List<ProductQuantityModelU> products) {
//        PriceModel model = new PriceModel();
//        if (products == null || products.size() == 0) {
//            model.setStatus(PriceModelStatus.NO_PRODUCT);
//            return model; // 计价失败
//        }
//        //domain.setOid(oid);// oid 不必要
//        List<PriceProductModel> priceProductModels = new ArrayList<>();
//        BigDecimal quantityTT = new BigDecimal(0);
//        BigDecimal originPriceTT = new BigDecimal(0);
//        BigDecimal salePriceTT = new BigDecimal(0);
//        BigDecimal priceTT = new BigDecimal(0);
//        BigDecimal weightTT = new BigDecimal(0);
//
//
//        Condition condition = new Condition(ProductStock.class);
//        for (ProductQuantityModelU productInfo : products) {
//            // 查询商品信息，看看是否闪购
//            Product product = productService.findById(productInfo.getPid());
//            if (product == null) {
//                model.setStatus(PriceModelStatus.NO_PRODUCT);// 存在未知商品信息，该商品可能已被删除
//            }
//            // 查询库存记录的价格
//            condition.clear();
//            condition.createCriteria().andCondition("pid = " + productInfo.getPid() + " AND norm_ids = '" + productInfo.getNormValues() + "'");
//            List<ProductStock> stocks = productStockService.findByCondition(condition);
//            if (stocks.size() == 0) {
//                model.setStatus(PriceModelStatus.NO_STOCKINFO);// 无规格信息
//                return model;
//            }
//            ProductStock stock = stocks.get(0);
//
//            // 提取信息，基本信息
//            BigDecimal quantity = BigDecimal.valueOf(productInfo.getQuantity());
//            BigDecimal originPrice = stock.getOrginPrice();
//            BigDecimal salePrice = stock.getPrice();
//            BigDecimal price = new BigDecimal(9999); // 最终价格会存在这里
//            // 提取信息，闪购信息
//            BigDecimal discount = product.getDealDiscount();
//            Boolean isDeal = product.getIsDeal(); // isDeal 不可能为空，数据库限制了
//            Date dealStart = product.getDealStart();
//            Date dealEnd = product.getDealDdl();
//            Date now = new Date();
//
//
//            // 计算价格，单价
//            PriceProductModel priceProductModel = new PriceProductModel();// 单件商品计价结果存储处
//            if (isDeal
//                    && dealStart != null && dealEnd != null
//                    && now.after(dealStart)
//                    && now.before(dealEnd)) {
//                // 在闪购期间
//                price = salePrice.multiply(discount); // discount 在范围 [0-1] 内
//            } else {
//                price = salePrice;
//                discount = BigDecimal.valueOf(1.00); // 不打折，打 10 折
//            }
//
//            // 该商品总价
//            originPrice = originPrice.multiply(quantity);
//            salePrice = salePrice.multiply(quantity);
//            price = price.multiply(quantity);
//            // 该商品总重量
//            weightTT = weightTT.add(quantity.multiply(product.getWeight()));
//
//            // 填充数据
//            priceProductModel.setPid(productInfo.getPid());
//            priceProductModel.setProduct(product);
//            priceProductModel.setDiscount(discount);
//            priceProductModel.setOriginPrice(originPrice);
//            priceProductModel.setSalePrice(salePrice);
//            priceProductModel.setPrice(price);
//            priceProductModel.setQuantity(productInfo.getQuantity());
//
//            // 添加
//            priceProductModels.add(priceProductModel);
//
//            // 累和数据
//            originPriceTT = originPriceTT.add(originPrice);
//            salePriceTT = salePriceTT.add(salePrice);
//            priceTT = priceTT.add(price);
//            quantityTT = quantityTT.add(quantity);
//        }
//        model.setProducts(priceProductModels);
//        model.setOriginPrice(originPriceTT);
//        model.setSalePrice(salePriceTT);
//        model.setPrice(priceTT);
//        model.setQuantity(quantityTT.intValue());
//        model.setWeight(weightTT);
//        model.setStatus(PriceModelStatus.SUCCESS);
//
//        return model;
//    }
//
//
//    public PriceModel calPrice(List<ProductQuantityModelU> products, String CDKEY) {
//        PriceModel model = new PriceModel();
//        if (products == null || products.size() == 0) {
//            model.setStatus(PriceModelStatus.NO_PRODUCT);
//            return model; // 计价失败
//        }
//        String cid = couponFormService.decode(CDKEY);
//        if (cid == null) {
//            // 优惠券无效
//            model.setStatus(PriceModelStatus.COUPON_FAIL);
//            return model;
//        }
//        // 查找优惠券信息
//        Coupon coupon = couponService.findBy("cid", cid);
//        if (coupon == null) {
//            model.setStatus(PriceModelStatus.COUPON_FAIL);
//            return model;
//        }
//        // 检验是否可用
//        if (!coupon.getIsAvailable()) {
//            model.setStatus(PriceModelStatus.COUPON_NOT_AVAILABLE);// 优惠券未开启使用
//            return model;
//        }
//        Date now = new Date();
//        if (now.before(coupon.getStartTime())) {
//            model.setStatus(PriceModelStatus.COUPON_BEFORE_TIME);// 优惠券还未开始
//            return model;
//        }
//        if (now.after(coupon.getEndTime())) {
//            model.setStatus(PriceModelStatus.COUPON_OUT_TIME);// 优惠券已经过期
//            return model;
//        }
//
//        // 查询该优惠券支持的商品
//        Condition c = new Condition(ProductCoupon.class);
//        c.createCriteria().andCondition("cid = '" + cid + "'");
//        List<ProductCoupon> productCoupons = productCouponService.findByCondition(c);
//        if (productCoupons.size() == 0) {
//            model.setStatus(PriceModelStatus.COUPON_NOT_AVAILABLE);// 优惠券未开启使用（该优惠券没有可支持的商品）
//            return model;
//        }
//        // 将优惠券-商品 信息规整为List
//        List<Integer> coupon_pids = new ArrayList<>();
//        for (ProductCoupon productCoupon : productCoupons) {
//            coupon_pids.add(productCoupon.getPid());
//        }
//
//
//        // 初始化求和数据
//        List<PriceProductModel> priceProductModels = new ArrayList<>();
//        BigDecimal quantityTT = new BigDecimal(0);
//        BigDecimal originPriceTT = new BigDecimal(0);
//        BigDecimal salePriceTT = new BigDecimal(0);
//        BigDecimal priceTT = new BigDecimal(0);
//        BigDecimal weightTT = new BigDecimal(0);
//
//        List<PriceProductModel> priceProductModelsTT_coupon = new ArrayList<>();
//        BigDecimal quantityTT_coupon = new BigDecimal(0);
//        BigDecimal originPriceTT_coupon = new BigDecimal(0);
//        BigDecimal salePriceTT_coupon = new BigDecimal(0);
//        BigDecimal priceTT_coupon = new BigDecimal(0);
//
//        List<PriceProductModel> priceProductModelsTT_normal = new ArrayList<>();
//        BigDecimal quantityTT_normal = new BigDecimal(0);
//        BigDecimal originPriceTT_normal = new BigDecimal(0);
//        BigDecimal salePriceTT_normal = new BigDecimal(0);
//        BigDecimal priceTT_normal = new BigDecimal(0);
//
//        Condition condition = new Condition(ProductStock.class);
//        for (ProductQuantityModelU productInfo : products) {
//            Integer pid = productInfo.getPid();
//            // 商品信息
//            Product product = productService.findById(productInfo.getPid());
//            if (product == null) {
//                model.setStatus(PriceModelStatus.NO_PRODUCT);// 存在未知商品信息，该商品可能已被删除
//            }
//            // 查询库存记录的价格
//            condition.clear();
//            condition.createCriteria().andCondition("pid = " + pid + " AND norm_ids = '" + productInfo.getNormValues() + "'");
//            List<ProductStock> stocks = productStockService.findByCondition(condition);
//            if (stocks.size() == 0) {
//                model.setStatus(PriceModelStatus.NO_STOCKINFO);// 无规格信息
//                return model;
//            }
//            ProductStock stock = stocks.get(0);
//
//            // 提取信息，基本信息
//            BigDecimal quantity = BigDecimal.valueOf(productInfo.getQuantity());
//            BigDecimal originPrice = stock.getOrginPrice();
//            BigDecimal salePrice = stock.getPrice();
//            BigDecimal price = stock.getPrice(); // 最终价格会存在这里
//
//            // 该商品总价
//            originPrice = originPrice.multiply(quantity);
//            salePrice = salePrice.multiply(quantity);
//            price = price.multiply(quantity);
//            // 该商品总重量
//            weightTT = weightTT.add(quantity.multiply(product.getWeight()));
//
//            // 计算价格，单价
//            PriceProductModel priceProductModel = new PriceProductModel();// 单件商品计价结果存储处
//            if (coupon_pids.contains(pid)) {
//                // 优惠券计价
//
//                // 填充数据，该商品（n件）一条记录
//                priceProductModel.setPid(pid);
//                priceProductModel.setProduct(product);
//                priceProductModel.setOriginPrice(originPrice);
//                priceProductModel.setSalePrice(salePrice);
//                priceProductModel.setQuantity(productInfo.getQuantity());
//
//                // 优惠券计价方式：打折2、满减1
//                if (2 == coupon.getType()) { // 打折
//                    BigDecimal discount = coupon.getDiscount();
//                    price = price.multiply(discount);
//                    priceProductModel.setDiscount(discount);
//                    priceProductModel.setPrice(price); // 打折价
//                } else if (1 == coupon.getType()) {// 满减
//                    priceProductModel.setDiscount(BigDecimal.valueOf(1));// 不打折
//                    priceProductModel.setPrice(price);
//                } else {
//                    model.setStatus(PriceModelStatus.COUPON_NOT_AVAILABLE);// 优惠券未开启使用（优惠券类型不合法）
//                    return model;
//                }
//                // 添加
//                priceProductModelsTT_coupon.add(priceProductModel); // 添加到优惠商品集合
//                // 求和优惠商品
//                originPriceTT_coupon = originPriceTT_coupon.add(originPrice);
//                salePriceTT_coupon = salePriceTT_coupon.add(salePrice);
//                priceTT_coupon = priceTT_coupon.add(price);
//                quantityTT_coupon = quantityTT_coupon.add(quantity);
//
//            } else {
//                // 普通计价
//                // 填充数据，该商品（n件）一条记录
//                priceProductModel.setPid(pid);
//                priceProductModel.setProduct(product);
//                priceProductModel.setOriginPrice(originPrice);
//                priceProductModel.setSalePrice(salePrice);
//                priceProductModel.setQuantity(productInfo.getQuantity());
//                priceProductModel.setDiscount(BigDecimal.valueOf(1));// 不打折
//                priceProductModel.setPrice(price);
//
//                // 添加
//                priceProductModelsTT_normal.add(priceProductModel); // 添加到普通商品集合
//                // 求和普通商品
//                originPriceTT_normal = originPriceTT_normal.add(originPrice);
//                salePriceTT_normal = salePriceTT_normal.add(salePrice);
//                priceTT_normal = priceTT_normal.add(price);
//                quantityTT_normal = quantityTT_normal.add(quantity);
//            }
//        }
//
//        if (1 == coupon.getType()) {
//            // 看是否满足满减条件
//            BigDecimal startPrice = coupon.getStartPrice();
//            BigDecimal cutPrice = coupon.getDisprice();
//            if (priceTT_coupon.doubleValue() > startPrice.doubleValue()) {
//                // 满足
//                priceTT_coupon = priceTT_coupon.add(cutPrice.negate());
//            }
//        }
//        // 累计所有商品求和
//        // 求和
//        originPriceTT = originPriceTT_normal.add(originPriceTT_coupon);
//        salePriceTT = salePriceTT_normal.add(salePriceTT_coupon);
//        priceTT = priceTT_normal.add(priceTT_coupon);
//        quantityTT = quantityTT_normal.add(quantityTT_coupon);
//        // 累和数据
//        priceProductModels.addAll(priceProductModelsTT_coupon);
//        priceProductModels.addAll(priceProductModelsTT_normal);
//        model.setProducts(priceProductModels);
//        model.setOriginPrice(originPriceTT);
//        model.setSalePrice(salePriceTT);
//        model.setPrice(priceTT);
//        model.setQuantity(quantityTT.intValue());
//        model.setWeight(weightTT);
//        model.setStatus(PriceModelStatus.SUCCESS);
//
//        model.setCoupon(CDKEY);
//        model.setCouponType(coupon.getType());
//        model.setSavePrice(salePriceTT.add(priceTT.negate()));
//        return model;
//    }
//
//}
