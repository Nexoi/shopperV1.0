//package com.seeu.shopper.order.service.impl;
//
//import com.seeu.core.AbstractService;
//import com.seeu.shopper.order.domain.*;
//import com.seeu.shopper.order.service.OrderProductService;
//import com.seeu.shopper.order.service.OrderService;
//import com.seeu.shopper.product.domain.Product;
//import com.seeu.shopper.product.service.ProductService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//
///**
// * Created by neoxiaoyi on 2017/08/10.
// */
//@Service
//@Transactional
//public class OrderServiceImpl implements OrderService {
//
//    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
//    @Autowired
//    ProductService productService;
//    @Autowired
//    OrderProductService orderProductService;
//
//    @Override
//    public String genOrderID() {
//        // 时间戳 + 随机数
//        String time = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
//        Integer rand = 1000 + (int) (8999 * Math.random());
//        return time + rand;
//    }
//
//
//    @Override
//    public boolean checkReceiverData(OrderReceiver receiver) {
//        if (isEmpty(receiver.getName())
//                || isEmpty(receiver.getDetail())
//                || isEmpty(receiver.getCountry())
//                || isEmpty(receiver.getCity())
//                || isEmpty(receiver.getEmail())
//                || isEmpty(receiver.getPhone())
////                || isEmpty(receiver.getPostcode())
//                ) {
//            // 只要有一个是空的
//            return false;
//        }
//        return true;
//    }
//
//    private boolean isEmpty(String str) {
//        return str == null || str.trim().length() == 0;
//    }
//
//    @Override
//    public PriceModel calPriceNormal(List<ProductAmount> products) {
//        BigDecimal total_price = new BigDecimal(0);
//        BigDecimal deal_total_price = new BigDecimal(0);
//        for (ProductAmount product : products) {
//            Integer pid = product.getPid();
//            Integer amount = product.getAmount();
//            Product productModel = productService.findById(pid);
//            if (productModel == null) {
//                return null;// 商品信息不存在
//            }
//            BigDecimal price = productModel.getCurrentPrice(); // 默认现价
//            total_price = total_price.add(price.multiply(BigDecimal.valueOf(amount)));
//
//            if (productModel.getIsDeal()) { // 是否促销、闪购
//                price = productModel.getOriginPrice().multiply(productModel.getDealDiscount());
//            }
//            deal_total_price = deal_total_price.add(price.multiply(BigDecimal.valueOf(amount)));
//        }
//        PriceModel priceModel = new PriceModel();
//        priceModel.setCurrentPrice(deal_total_price);
//        priceModel.setTotalPrice(total_price);
//        return priceModel;
//    }
//
//
//    /**
//     * @param products
//     * @param discount    0-10
//     * @param providePIDs
//     * @return
//     */
//    @Override
//    public PriceModel calPriceCouponDiscount(List<ProductAmount> products, BigDecimal startPrice, Integer discount, List<Integer> providePIDs) {
//        BigDecimal total_price = new BigDecimal(0);// 不打折
//        BigDecimal subtotal_price = new BigDecimal(0);
//        BigDecimal discount_price = new BigDecimal(0);// 打折总价
//        for (ProductAmount product : products) {
//            Integer pid = product.getPid();
//            Integer amount = product.getAmount();
//            Product productModel = productService.findById(pid);
//            if (productModel == null) {
//                return null;// 商品信息不存在
//            }
//            BigDecimal price = productModel.getCurrentPrice().multiply(BigDecimal.valueOf(amount)); // 默认现价
//            // 优惠券不支持促销计价
////            if (productModel.getIsDeal()) { // 是否促销、闪购
////                price = productModel.getOriginPrice().multiply(BigDecimal.valueOf(0.1*(float)productModel.getDealDiscount()));
////            }
//            if (providePIDs.contains(pid)) {
//                subtotal_price = subtotal_price.add(price);// 计算有打折资格的商品总原价
//                total_price = total_price.add(price);// 不打折总价
//                // 优惠后
//                price = price.multiply(BigDecimal.valueOf(((float) discount) * 0.1));
//                discount_price = discount_price.add(price); // 打折后总价
//
//            } else {
//                total_price = total_price.add(price);// 不打折计价器总价
//                discount_price = discount_price.add(price); // 打折计价器总价
//            }
//        }
//        PriceModel priceModel = new PriceModel();
//        priceModel.setTotalPrice(total_price);
//        if (subtotal_price.doubleValue() > startPrice.doubleValue()) {
//            // 打折
//            priceModel.setCurrentPrice(discount_price);
//        } else {
//            priceModel.setCurrentPrice(total_price);
//        }
//        return priceModel;
//    }
//
//    @Override
//    public PriceModel calPriceCouponDisprice(List<ProductAmount> products, BigDecimal startPrice, BigDecimal disprice, List<Integer> providePIDs) {
//        BigDecimal total_price = new BigDecimal(0);// 未优惠的总价格
//        BigDecimal subtotal_price = new BigDecimal(0); // 优惠部分商品的原价格
//        for (ProductAmount product : products) {
//            Integer pid = product.getPid();
//            Integer amount = product.getAmount();
//            Product productModel = productService.findById(pid);
//            if (productModel == null) {
//                return null;// 商品信息不存在
//            }
//            BigDecimal price = productModel.getCurrentPrice(); // 默认现价
//            // 优惠券不支持促销计价
////            if (productModel.getIsDeal()) { // 是否促销、闪购
////                price = productModel.getOriginPrice().multiply(BigDecimal.valueOf(0.1*(float)productModel.getDealDiscount()));
////            }
//            if (providePIDs.contains(pid)) {
//                // 优惠
//                subtotal_price = subtotal_price.add(price.multiply(BigDecimal.valueOf(amount)));// 满减支持的商品单独算
//            }
//            total_price = total_price.add(price.multiply(BigDecimal.valueOf(amount)));
//
//        }
//        PriceModel priceModel = new PriceModel();
//        priceModel.setTotalPrice(total_price);
//        if (subtotal_price.doubleValue() > startPrice.doubleValue()) {
//            priceModel.setCurrentPrice(total_price.add(startPrice.negate()));
//        } else {
//            priceModel.setCurrentPrice(total_price);
//        }
//        return priceModel;
//    }
//
//    /**
//     * 保存商品信息
//     *
//     * @param products
//     */
//    @Override
//    public void saveProducts(String OID, List<ProductAmount> products, boolean isUseCoupon) {
//        for (ProductAmount product : products) {
//            Integer pid = product.getPid();
//            Integer amount = product.getAmount();
//            Product productModel = productService.findById(pid);
//            if (productModel != null) {
//                OrderProduct orderProduct = new OrderProduct();
//                orderProduct.setOid(OID);
//                orderProduct.setPid(pid);
//                orderProduct.setName(productModel.getName());
//                orderProduct.setAmount(amount);
//                orderProduct.setOriginPrice(productModel.getOriginPrice());
//                orderProduct.setIsDeal(productModel.getIsDeal());
//                if (isUseCoupon) {
//                    orderProduct.setIsDeal(false);// 使用了优惠券，所以 False
//                    orderProduct.setCurrentPrice(productModel.getCurrentPrice());
//                } else {
//                    if (productModel.getIsDeal()) {
//                        orderProduct.setCurrentPrice(productModel.getOriginPrice().multiply(productModel.getDealDiscount()));
//                    } else {
//                        orderProduct.setCurrentPrice(productModel.getCurrentPrice());
//                    }
//                }
//                orderProductService.save(orderProduct);
//            }
//            logger.info("商品信息添加成功 订单编号：" + OID);
//        }
//
//    }
//
//    @Override
//    public PriceModel calPriceActivity(List<ProductAmount> products, List<Integer> providePIDs) {
//        return null;
//    }
//
//    /**
//     * @param CDKEY
//     * @return CID
//     */
//    @Override
//    public String decode(String CDKEY) {
//        if (CDKEY.length() != 20) {
//            return null;
//        }
//        // ~
//        char cid[] = new char[4];
//        char num[] = new char[6];
//        char key[] = new char[10];
//
//        int i = 0;
//        while (i < 4) {
//            cid[i] = CDKEY.charAt(i * 2);
//            i++;
//        }
//        int j = 0;
//        while (j < 6) {
//            num[j] = CDKEY.charAt(j * 2 + 8);
//            j++;
//        }
//        int k = 0;
//        while (k < 10) {
//            key[k] = CDKEY.charAt(2 * k + 1);
//            k++;
//        }
//        String cidStr = String.copyValueOf(cid);
//        String MD5KEY = md5(cidStr, String.copyValueOf(num));
//        if ((MD5KEY != null) && MD5KEY.equals(String.copyValueOf(key))) {
//            return cidStr;
//        }
//        return null;
//    }
//
//
//    /**
//     * @param CID
//     * @param number
//     * @return 十位被加密信息
//     */
//    private String md5(String CID, String number) {
//        try {
//            // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
//            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
//            // 输入的字符串转换成字节数组
//            byte[] inputByteArray = (CID + number).getBytes();
//            // inputByteArray是输入字符串转换得到的字节数组
//            messageDigest.update(inputByteArray);
//            // 转换并返回结果，也是字节数组，包含16个元素
//            byte[] resultByteArray = messageDigest.digest();
//            // 字符数组转换成字符串返回
//            return byteArrayToHex(resultByteArray).substring(0, 10);
//        } catch (NoSuchAlgorithmException e) {
//            return null;
//        }
//    }
//
//    private static String byteArrayToHex(byte[] byteArray) {
//        // 首先初始化一个字符数组，用来存放每个16进制字符
//        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
//        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
//        char[] resultCharArray = new char[byteArray.length * 2];
//        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
//        int index = 0;
//        for (byte b : byteArray) {
//            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
//            resultCharArray[index++] = hexDigits[b & 0xf];
//        }
//        // 字符数组组合成字符串返回
//        return new String(resultCharArray);
//    }
//}
