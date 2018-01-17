package com.seeu.shopper.order.iservice;

import com.seeu.shopper.adminorder.model.PriceModel;
import com.seeu.shopper.adminorder.model.PriceModelStatus;
import com.seeu.shopper.adminorder.model.PriceProductModel;
import com.seeu.shopper.adminorder.model.ProductQuantityModelU;
import com.seeu.shopper.ashop.service.NormFormService;
import com.seeu.shopper.coupon.model.Coupon;
import com.seeu.shopper.coupon.service.CouponService;
import com.seeu.shopper.product.model.Product;
import com.seeu.shopper.product.model.ProductStock;
import com.seeu.shopper.product.service.ProductCouponService;
import com.seeu.shopper.product.service.ProductService;
import com.seeu.shopper.product.service.ProductStockService;
import com.seeu.shopper.user.model.UserCoupon;
import com.seeu.shopper.user.service.UserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by neo on 29/08/2017.
 * 提供订单计价功能，且库存必须为可用，仅限于 mycart 页面使用
 * <p>
 * 注：库存量不在条件影响范围内
 * <p>
 * update 2017/10/18 用原价计算优惠券价格；添加信息限制用户使用次数（一个用户只能使用一次优惠券）
 * <p>
 * update 2017/10/29 优惠券计价规则修改：商品原价 = 售价时候可以使用优惠券，否则只能使用售价进行销售
 */
@Service
//@Transactional(readOnly = true)
public class PriceAtStockV2Service {

    @Resource
    ProductService productService;
    @Resource
    ProductStockService productStockService;

    @Resource
    CouponService couponService;
    @Resource
    ProductCouponService productCouponService;
    @Autowired
    CouponFormService couponFormService;
    @Resource
    NormFormService normFormService;

    /**
     * 根据商品信息计价，不支持优惠券，支持闪购计价（折扣）
     * <p>
     * 如果pid、amount啥为空，不要传进来，没做非空判断
     *
     * @param products
     * @return
     */
    public PriceModel calPrice(List<ProductQuantityModelU> products) {
        PriceModel model = new PriceModel();
        if (products == null || products.size() == 0) {
            model.setStatus(PriceModelStatus.NO_PRODUCT);
            return model; // 计价失败
        }
        //domain.setOid(oid);// oid 不必要
        List<PriceProductModel> priceProductModels = new ArrayList<>();
        BigDecimal quantityTT = new BigDecimal(0);
        BigDecimal originPriceTT = new BigDecimal(0);
        BigDecimal salePriceTT = new BigDecimal(0);
        BigDecimal priceTT = new BigDecimal(0);
        BigDecimal weightTT = new BigDecimal(0);


        Condition condition = new Condition(ProductStock.class);
        for (ProductQuantityModelU productInfo : products) {
            // 查询商品信息，看看是否闪购
            Product product = productService.findById(productInfo.getPid());
            if (product == null) {
                model.setStatus(PriceModelStatus.NO_PRODUCT);// 存在未知商品信息，该商品可能已被删除
            }
            // 查询库存记录的价格
            condition.clear();
            condition.createCriteria().andCondition("pid = " + productInfo.getPid() + " AND norm_ids = '" + productInfo.getNormValues() + "' AND is_ing = true ");
            List<ProductStock> stocks = productStockService.findByCondition(condition);

            if (stocks.size() == 0) {
                // 无规格信息
                // 选用第一个库存为标准记录
                condition.clear();
                condition.createCriteria().andCondition("pid = " + productInfo.getPid() + " AND is_ing = true AND current_stock > 0 ");
                List<ProductStock> stocks2 = productStockService.findByCondition(condition);
                if (stocks2.size() == 0) {
                    model.setStatus(PriceModelStatus.NO_STOCKINFO);// 无规格信息
//                    return model;
                    continue;
                }
                stocks = stocks2;
            }
            ProductStock stock = stocks.get(0);
            // 判断是否库存足够
            if (stock.getCurrentStock() < productInfo.getQuantity()) {
                // 最多只能购买库存余量
                productInfo.setQuantity(stock.getCurrentStock());
            }

            // 提取信息，基本信息
            BigDecimal quantity = BigDecimal.valueOf(productInfo.getQuantity());
            BigDecimal originPrice = stock.getOrginPrice();
            BigDecimal salePrice = stock.getPrice();
            BigDecimal price = new BigDecimal(9999); // 最终价格会存在这里
            // 提取信息，闪购信息
            BigDecimal discount = product.getDealDiscount();
            Boolean isDeal = product.getIsDeal(); // isDeal 不可能为空，数据库限制了
            Date dealStart = product.getDealStart();
            Date dealEnd = product.getDealDdl();
            Date now = new Date();


            // 计算价格，单价
            PriceProductModel priceProductModel = new PriceProductModel();// 单件商品计价结果存储处
            // TODO 打个记号-规格、库存信息
            Map<Integer, String> norms = normFormService.getNormName(stock.getNormIds());
            priceProductModel.setNorms(norms);
            priceProductModel.setNormValues(stock.getNormIds());
            priceProductModel.setCurrentStock(stock.getCurrentStock());

            if (isDeal
                    && dealStart != null && dealEnd != null
                    && now.after(dealStart)
                    && now.before(dealEnd)) {
                // 在闪购期间
                price = salePrice.multiply(discount); // discount 在范围 [0-1] 内
            } else {
                price = salePrice;
                discount = BigDecimal.valueOf(1.00); // 不打折，打 10 折
            }

            // 该商品总价
            originPrice = originPrice.multiply(quantity);
            salePrice = salePrice.multiply(quantity);
            price = price.multiply(quantity);
            // 该商品总重量
            weightTT = weightTT.add(quantity.multiply(product.getWeight()));

            // 填充数据
            priceProductModel.setPid(productInfo.getPid());
            priceProductModel.setProduct(product);
            priceProductModel.setDiscount(discount);
            priceProductModel.setOriginPrice(originPrice);
            priceProductModel.setSalePrice(salePrice);
            priceProductModel.setPrice(price);
            priceProductModel.setQuantity(quantity.intValue());

            // 添加
            priceProductModels.add(priceProductModel);

            // 累和数据
            originPriceTT = originPriceTT.add(originPrice);
            salePriceTT = salePriceTT.add(salePrice);
            priceTT = priceTT.add(price);
            quantityTT = quantityTT.add(quantity);
        }
        model.setProducts(priceProductModels);
        model.setOriginPrice(originPriceTT);
        model.setSalePrice(salePriceTT);
        model.setPrice(priceTT);
        model.setQuantity(quantityTT.intValue());
        model.setWeight(weightTT);
        model.setStatus(PriceModelStatus.SUCCESS);

        return model;
    }

    @Resource
    UserCouponService userCouponService;


    public PriceModel calPrice(List<ProductQuantityModelU> products, String CDKEY, Integer uid) {
        PriceModel model = new PriceModel();
        if (products == null || products.size() == 0) {
            model.setStatus(PriceModelStatus.NO_PRODUCT);
            return model; // 计价失败
        }
        String cid = couponFormService.decode(CDKEY);
        if (cid == null) {
            // 优惠券无效
//            model.setStatus(PriceModelStatus.COUPON_FAIL);
//            return model;
            return calPrice(products);// 使用普通计价器
        }
        // 查找优惠券信息
        Coupon coupon = couponService.findBy("cid", cid);
        if (coupon == null) {
//            model.setStatus(PriceMode lStatus.COUPON_FAIL);
//            return model;
            return calPrice(products);// 使用普通计价器
        }

        // 检验是否可用
        if (!coupon.getIsAvailable()) {
            model.setStatus(PriceModelStatus.COUPON_NOT_AVAILABLE);// 优惠券未开启使用
            return model;
        }
        Date now = new Date();
        if (now.before(coupon.getStartTime())) {
            model.setStatus(PriceModelStatus.COUPON_BEFORE_TIME);// 优惠券还未开始
            return model;
        }
        if (now.after(coupon.getEndTime())) {
            model.setStatus(PriceModelStatus.COUPON_OUT_TIME);// 优惠券已经过期
            return model;
        }
        // 检查是否已被消费
        Condition condition1 = new Condition(UserCoupon.class);
        condition1.createCriteria().andCondition("code = '" + CDKEY + "'");
        List<UserCoupon> userCoupons = userCouponService.findByCondition(condition1);
        if (!userCoupons.isEmpty()) {
            UserCoupon userCoupon = userCoupons.get(0);
            if (userCoupon != null && userCoupon.getStatus() != 0) { // 状态为 1 表示已使用
                return calPrice(products); // 普通计价器（优惠券已经被使用 or 该优惠券不是他的）
            }
        }
        model.setCid(cid);

        // 初始化求和数据
        List<PriceProductModel> priceProductModels = new ArrayList<>();
        BigDecimal quantityTT = new BigDecimal(0);
        BigDecimal originPriceTT = new BigDecimal(0);
        BigDecimal salePriceTT = new BigDecimal(0);
        BigDecimal priceTT = new BigDecimal(0);
        BigDecimal weightTT = new BigDecimal(0);

        List<PriceProductModel> priceProductModelsTT_coupon = new ArrayList<>();
        BigDecimal quantityTT_coupon = new BigDecimal(0);
        BigDecimal originPriceTT_coupon = new BigDecimal(0);
        BigDecimal salePriceTT_coupon = new BigDecimal(0);
        BigDecimal priceTT_coupon = new BigDecimal(0);

        List<PriceProductModel> priceProductModelsTT_normal = new ArrayList<>();
        BigDecimal quantityTT_normal = new BigDecimal(0);
        BigDecimal originPriceTT_normal = new BigDecimal(0);
        BigDecimal salePriceTT_normal = new BigDecimal(0);
        BigDecimal priceTT_normal = new BigDecimal(0);

        boolean flag = false; // 优惠券在此次计价中是否使用
        Condition condition = new Condition(ProductStock.class);
        for (ProductQuantityModelU productInfo : products) {
            Integer pid = productInfo.getPid();
            // 商品信息
            Product product = productService.findById(productInfo.getPid());
            if (product == null) {
                model.setStatus(PriceModelStatus.NO_PRODUCT);// 存在未知商品信息，该商品可能已被删除
            }
            // 查询库存记录的价格
            condition.clear();
            condition.createCriteria().andCondition("pid = " + pid + " AND norm_ids = '" + productInfo.getNormValues() + "' AND is_ing = true ");
            List<ProductStock> stocks = productStockService.findByCondition(condition);
            if (stocks.size() == 0) {
                // 无规格信息
                // 选用第一个库存为标准记录
                condition.clear();
                condition.createCriteria().andCondition("pid = " + productInfo.getPid() + " AND is_ing = true AND current_stock > 0 ");
                List<ProductStock> stocks2 = productStockService.findByCondition(condition);
                if (stocks2.size() == 0) {
                    model.setStatus(PriceModelStatus.NO_STOCKINFO);// 无规格信息
//                    return model;
                    continue;
                }
                stocks = stocks2;
            }
            ProductStock stock = stocks.get(0);
            // 判断是否库存足够
            if (stock.getCurrentStock() < productInfo.getQuantity()) {
                // 最多只能购买库存余量
                productInfo.setQuantity(stock.getCurrentStock());
            }

            // 提取信息，基本信息
            BigDecimal quantity = BigDecimal.valueOf(productInfo.getQuantity());
            BigDecimal originPrice = stock.getOrginPrice();
            BigDecimal salePrice = stock.getPrice();
            // 是否满足优惠条件（原价 == 售价）
            boolean couponCal = originPrice.compareTo(salePrice) == 0;
            // 如果满足优惠条件，则用原价计算价格，否则用促销价格进行计算
            BigDecimal price = couponCal ? stock.getOrginPrice() : stock.getPrice(); // 最终价格会存在这里 TODO update 2017/10/29 用售价计算

            // 该商品总价
            originPrice = originPrice.multiply(quantity);
            salePrice = salePrice.multiply(quantity);
            price = price.multiply(quantity);
            // 该商品总重量
            weightTT = weightTT.add(quantity.multiply(product.getWeight()));

            // 计算价格，单价
            PriceProductModel priceProductModel = new PriceProductModel();// 单件商品计价结果存储处
            // TODO 打个记号
            Map<Integer, String> norms = normFormService.getNormName(stock.getNormIds());
            priceProductModel.setNorms(norms);
            priceProductModel.setNormValues(stock.getNormIds());
            priceProductModel.setCurrentStock(stock.getCurrentStock());

            // 优惠券计价

            // 填充数据，该商品（n件）一条记录
            priceProductModel.setPid(pid);
            priceProductModel.setProduct(product);
            priceProductModel.setOriginPrice(originPrice);
            priceProductModel.setSalePrice(salePrice);
            priceProductModel.setQuantity(quantity.intValue());


            // 优惠券计价方式：打折2、满减1
            if (2 == coupon.getType()) { // 打折
                BigDecimal discount = couponCal ? coupon.getDiscount() : BigDecimal.valueOf(1);
                price = couponCal ? price.multiply(discount) : price;
                priceProductModel.setDiscount(discount);
                priceProductModel.setPrice(price.setScale(2, BigDecimal.ROUND_UP)); // 打折价
            } else if (1 == coupon.getType()) { // 满减
                priceProductModel.setDiscount(BigDecimal.valueOf(1));// 不打折
                priceProductModel.setPrice(couponCal ? price.setScale(2, BigDecimal.ROUND_UP) : price);
            } else {
                model.setStatus(PriceModelStatus.COUPON_NOT_AVAILABLE);// 优惠券未开启使用（优惠券类型不合法）
                return model;
            }
            // 添加
            priceProductModelsTT_coupon.add(priceProductModel); // 添加到优惠商品集合
            // 求和优惠商品
            originPriceTT_coupon = originPriceTT_coupon.add(originPrice);
            salePriceTT_coupon = salePriceTT_coupon.add(salePrice);
            priceTT_coupon = priceTT_coupon.add(price);
            quantityTT_coupon = quantityTT_coupon.add(quantity);

            flag = flag ? true : couponCal;
        }

        if (1 == coupon.getType()) {
            // 看是否满足满减条件
            BigDecimal startPrice = coupon.getStartPrice();
            BigDecimal cutPrice = coupon.getDisprice();
            if (priceTT_coupon.doubleValue() > startPrice.doubleValue()) {
                // 满足
                priceTT_coupon = priceTT_coupon.add(cutPrice.negate());
            }
        }
        // 累计所有商品求和
        // 求和
        originPriceTT = originPriceTT_normal.add(originPriceTT_coupon);
        salePriceTT = salePriceTT_normal.add(salePriceTT_coupon);
        priceTT = priceTT_normal.add(priceTT_coupon);
        quantityTT = quantityTT_normal.add(quantityTT_coupon);
        // 累和数据
        priceProductModels.addAll(priceProductModelsTT_coupon);
        priceProductModels.addAll(priceProductModelsTT_normal);
        model.setProducts(priceProductModels);
        model.setOriginPrice(originPriceTT.setScale(2, BigDecimal.ROUND_UP));
        model.setSalePrice(salePriceTT.setScale(2, BigDecimal.ROUND_UP));
        model.setPrice(priceTT.setScale(2, BigDecimal.ROUND_UP));
        model.setQuantity(quantityTT.intValue());
        model.setWeight(weightTT);
        model.setStatus(flag ? PriceModelStatus.SUCCESS_COUPON : PriceModelStatus.SUCCESS);

        model.setCoupon(CDKEY);
        model.setCouponType(coupon.getType());
        model.setSavePrice(salePriceTT.add(priceTT.negate()));

        return model;
    }

    /* 5 位优惠券码 */
    public PriceModel calPriceCD5(List<ProductQuantityModelU> products, String CID5, Integer uid) {
        PriceModel model = new PriceModel();
        if (products == null || products.size() == 0) {
            model.setStatus(PriceModelStatus.NO_PRODUCT);
            return model; // 计价失败
        }
        if (CID5 == null) {
            // 优惠券无效
//            model.setStatus(PriceModelStatus.COUPON_FAIL);
//            return model;
            return calPrice(products);// 使用普通计价器
        }
        // 查找优惠券信息
        Coupon coupon = couponService.findBy("cid", CID5);
        if (coupon == null) {
//            model.setStatus(PriceMode lStatus.COUPON_FAIL);
//            return model;
            return calPrice(products);// 使用普通计价器
        }

        // 检验是否可用
        if (!coupon.getIsAvailable()) {
            model.setStatus(PriceModelStatus.COUPON_NOT_AVAILABLE);// 优惠券未开启使用
            return model;
        }
        Date now = new Date();
        if (now.before(coupon.getStartTime())) {
            model.setStatus(PriceModelStatus.COUPON_BEFORE_TIME);// 优惠券还未开始
            return model;
        }
        if (now.after(coupon.getEndTime())) {
            model.setStatus(PriceModelStatus.COUPON_OUT_TIME);// 优惠券已经过期
            return model;
        }
        // 检查是否已被消费
        Condition condition1 = new Condition(UserCoupon.class);
        condition1.createCriteria().andCondition("uid = " + uid + " AND cid = '" + CID5 + "'");
        List<UserCoupon> userCoupons = userCouponService.findByCondition(condition1);
        if (!userCoupons.isEmpty()) {
            UserCoupon userCoupon = userCoupons.get(0);
            if (userCoupon != null && userCoupon.getStatus() != 0) { // 状态为 1 表示已使用
                return calPrice(products); // 普通计价器（优惠券已经被使用 or 该优惠券不是他的）
            }
        }
        model.setCid(CID5);

        // 初始化求和数据
        List<PriceProductModel> priceProductModels = new ArrayList<>();
        BigDecimal quantityTT = new BigDecimal(0);
        BigDecimal originPriceTT = new BigDecimal(0);
        BigDecimal salePriceTT = new BigDecimal(0);
        BigDecimal priceTT = new BigDecimal(0);
        BigDecimal weightTT = new BigDecimal(0);

        List<PriceProductModel> priceProductModelsTT_coupon = new ArrayList<>();
        BigDecimal quantityTT_coupon = new BigDecimal(0);
        BigDecimal originPriceTT_coupon = new BigDecimal(0);
        BigDecimal salePriceTT_coupon = new BigDecimal(0);
        BigDecimal priceTT_coupon = new BigDecimal(0);

        List<PriceProductModel> priceProductModelsTT_normal = new ArrayList<>();
        BigDecimal quantityTT_normal = new BigDecimal(0);
        BigDecimal originPriceTT_normal = new BigDecimal(0);
        BigDecimal salePriceTT_normal = new BigDecimal(0);
        BigDecimal priceTT_normal = new BigDecimal(0);

        boolean flag = false; // 优惠券在此次计价中是否使用
        Condition condition = new Condition(ProductStock.class);
        for (ProductQuantityModelU productInfo : products) {
            Integer pid = productInfo.getPid();
            // 商品信息
            Product product = productService.findById(productInfo.getPid());
            if (product == null) {
                model.setStatus(PriceModelStatus.NO_PRODUCT);// 存在未知商品信息，该商品可能已被删除
            }
            // 查询库存记录的价格
            condition.clear();
            condition.createCriteria().andCondition("pid = " + pid + " AND norm_ids = '" + productInfo.getNormValues() + "' AND is_ing = true ");
            List<ProductStock> stocks = productStockService.findByCondition(condition);
            if (stocks.size() == 0) {
                // 无规格信息
                // 选用第一个库存为标准记录
                condition.clear();
                condition.createCriteria().andCondition("pid = " + productInfo.getPid() + " AND is_ing = true AND current_stock > 0 ");
                List<ProductStock> stocks2 = productStockService.findByCondition(condition);
                if (stocks2.size() == 0) {
                    model.setStatus(PriceModelStatus.NO_STOCKINFO);// 无规格信息
//                    return model;
                    continue;
                }
                stocks = stocks2;
            }
            ProductStock stock = stocks.get(0);
            // 判断是否库存足够
            if (stock.getCurrentStock() < productInfo.getQuantity()) {
                // 最多只能购买库存余量
                productInfo.setQuantity(stock.getCurrentStock());
            }

            // 提取信息，基本信息
            BigDecimal quantity = BigDecimal.valueOf(productInfo.getQuantity());
            BigDecimal originPrice = stock.getOrginPrice();
            BigDecimal salePrice = stock.getPrice();
            // 是否满足优惠条件（原价 == 售价）
            boolean couponCal = originPrice.compareTo(salePrice) == 0;
            // 如果满足优惠条件，则用原价计算价格，否则用促销价格进行计算
            BigDecimal price = couponCal ? stock.getOrginPrice() : stock.getPrice(); // 最终价格会存在这里 TODO update 2017/10/29 用售价计算


            // 该商品总价
            originPrice = originPrice.multiply(quantity);
            salePrice = salePrice.multiply(quantity);
            price = price.multiply(quantity);
            // 该商品总重量
            weightTT = weightTT.add(quantity.multiply(product.getWeight()));

            // 计算价格，单价
            PriceProductModel priceProductModel = new PriceProductModel();// 单件商品计价结果存储处
            // TODO 打个记号
            Map<Integer, String> norms = normFormService.getNormName(stock.getNormIds());
            priceProductModel.setNorms(norms);
            priceProductModel.setNormValues(stock.getNormIds());
            priceProductModel.setCurrentStock(stock.getCurrentStock());

            // 优惠券计价

            // 填充数据，该商品（n件）一条记录
            priceProductModel.setPid(pid);
            priceProductModel.setProduct(product);
            priceProductModel.setOriginPrice(originPrice);
            priceProductModel.setSalePrice(salePrice);
            priceProductModel.setQuantity(quantity.intValue());

            // 优惠券计价方式：打折2、满减1
            if (2 == coupon.getType()) { // 打折
                BigDecimal discount = couponCal ? coupon.getDiscount() : BigDecimal.valueOf(1);
                price = couponCal ? price.multiply(discount) : price;
                priceProductModel.setDiscount(discount);
                priceProductModel.setPrice(price.setScale(2, BigDecimal.ROUND_UP)); // 打折价
            } else if (1 == coupon.getType()) { // 满减
                priceProductModel.setDiscount(BigDecimal.valueOf(1));// 不打折
                priceProductModel.setPrice(couponCal ? price.setScale(2, BigDecimal.ROUND_UP) : price);
            } else {
                model.setStatus(PriceModelStatus.COUPON_NOT_AVAILABLE);// 优惠券未开启使用（优惠券类型不合法）
                return model;
            }
            // 添加
            priceProductModelsTT_coupon.add(priceProductModel); // 添加到优惠商品集合
            // 求和优惠商品
            originPriceTT_coupon = originPriceTT_coupon.add(originPrice);
            salePriceTT_coupon = salePriceTT_coupon.add(salePrice);
            priceTT_coupon = priceTT_coupon.add(price);
            quantityTT_coupon = quantityTT_coupon.add(quantity);

            flag = flag ? true : couponCal;
        }

        if (1 == coupon.getType()) {
            // 看是否满足满减条件
            BigDecimal startPrice = coupon.getStartPrice();
            BigDecimal cutPrice = coupon.getDisprice();
            if (priceTT_coupon.doubleValue() > startPrice.doubleValue()) {
                // 满足
                priceTT_coupon = priceTT_coupon.add(cutPrice.negate());
            }
        }
        // 累计所有商品求和
        // 求和
        originPriceTT = originPriceTT_normal.add(originPriceTT_coupon);
        salePriceTT = salePriceTT_normal.add(salePriceTT_coupon);
        priceTT = priceTT_normal.add(priceTT_coupon);
        quantityTT = quantityTT_normal.add(quantityTT_coupon);
        // 累和数据
        priceProductModels.addAll(priceProductModelsTT_coupon);
        priceProductModels.addAll(priceProductModelsTT_normal);
        model.setProducts(priceProductModels);
        model.setOriginPrice(originPriceTT.setScale(2, BigDecimal.ROUND_UP));
        model.setSalePrice(salePriceTT.setScale(2, BigDecimal.ROUND_UP));
        model.setPrice(priceTT.setScale(2, BigDecimal.ROUND_UP));
        model.setQuantity(quantityTT.intValue());
        model.setWeight(weightTT);
        model.setStatus(flag ? PriceModelStatus.SUCCESS_COUPON_PUBLIC : PriceModelStatus.SUCCESS);

        model.setCoupon(CID5);// 这个作为5位优惠券码发放前台
        model.setCouponType(coupon.getType());
        model.setSavePrice(salePriceTT.add(priceTT.negate()));
        return model;
    }

}
