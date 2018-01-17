package com.seeu.shopper.coupon.iservice;

import com.seeu.shopper.coupon.model.Coupon;
import com.seeu.shopper.coupon.service.CouponService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo on 29/09/2017.
 */
@Service
public class ICouponService {
    public enum COUPON_TYPE {
        PRIVATE, PUBLIC
    }

    @Resource
    CouponService couponService;


    public String addCoupon(Coupon coupon, COUPON_TYPE type) {
        String cid = genCID(type);

        coupon.setCid(cid);
        if (coupon.getType() != null && coupon.getType() == 1) {// 满减
            coupon.setDiscount(BigDecimal.ONE);// 设定打折信息为基础值
        } else {
            coupon.setDisprice(BigDecimal.ZERO);// 设定满减信息为基础值
        }
        coupon.setRest(coupon.getAmount());
        coupon.setAmount(0);
        couponService.save(coupon);
        return cid;
    }

    private String genCID(COUPON_TYPE type) {
        // 生成 CID
        char[] chars = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'W', 'Y', 'Z', 'V', '1', '2', '3',
                '4', '5', '6', '7', '8', '9'};
        int random1 = (int) (chars.length * Math.random()) % chars.length;
        int random2 = (int) (chars.length * Math.random()) % chars.length;
        int random3 = (int) (chars.length * Math.random()) % chars.length;
        int random4 = (int) (chars.length * Math.random()) % chars.length;
        int random5 = (int) (chars.length * Math.random()) % chars.length;
        String cid = null;
        if (type == COUPON_TYPE.PRIVATE) {
            cid = String.copyValueOf(new char[]{chars[random1], chars[random2], chars[random3], chars[random4]});
        } else {
            cid = String.copyValueOf(new char[]{chars[random1], chars[random2], chars[random3], chars[random4], chars[random5]});
        }
        if (couponService.findBy("cid", cid) == null)
            return cid;
        else
            return genCID(type);
    }


    public void deleteCoupon(String cid) {
        Condition condition = new Condition(Coupon.class);
        condition.createCriteria().andCondition("cid = '" + cid + "'");
        couponService.deleteByCondition(condition);
    }

    public List<String> createCoupons(String CID, int count) {
        if (count < 0) return null;
        // 查询该 CID 码
        Coupon coupon = couponService.findBy("cid", CID);
        if (coupon == null) {
            return null;
        }

        int couponMuch = coupon.getRest() - count > 0 ? count : coupon.getRest(); // 需要生成的量
        // 更新 coupon 信息
        Coupon coup = new Coupon();
        coup.setCid(CID);
        coup.setAmount(coupon.getAmount() + count);
        coup.setRest(coupon.getRest() - couponMuch); // 可用剩余量减少
        couponService.update(coup);
        return create(CID, coupon.getAmount(), couponMuch);
    }


    /**
     * @param CID
     * @param start 优惠券序列号起始点
     * @param count 准备生成优惠券数量
     * @return
     */
    private List<String> create(String CID, Integer start, Integer count) {
        if (count > 0xFFFFFF) {
//            return "数量过大";
            return null;
        }
        if (CID.length() != 4) {
//            return "CID 错误";
            return null;
        }
        if (start < 0) {
//            return "起始值错误";
            return null;
        }
        // ~
        ArrayList<String> list = new ArrayList<>();
        int end = start + count;
        for (int i = start; i < end; i++) {
            String NUM = form6bit(Integer.toHexString(i));
            String key = md5(CID, NUM);
            String CDKEY = genCDKEY(CID, NUM, key);
            list.add(CDKEY);
            System.out.println(CDKEY);
        }
        return list;
    }

    /**
     * @param CID
     * @param number
     * @return 十位被加密信息
     */
    private String md5(String CID, String number) {
        try {
            // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 输入的字符串转换成字节数组
            byte[] inputByteArray = (CID + number).getBytes();
            // inputByteArray是输入字符串转换得到的字节数组
            messageDigest.update(inputByteArray);
            // 转换并返回结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();
            // 字符数组转换成字符串返回
            return byteArrayToHex(resultByteArray).substring(0, 10);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String byteArrayToHex(byte[] byteArray) {
        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }

    /**
     * 插值
     *
     * @param CID
     * @param NUM
     * @param KEY
     * @return
     */
    private String genCDKEY(String CID, String NUM, String KEY) {
        char code[] = new char[20];
        int i = 0;
        while (i < 4) {
            code[2 * i] = CID.charAt(i);
            i++;
        }
        int k = 0;
        while (k < 6) {
            code[2 * k + 8] = NUM.charAt(k);
            k++;
        }
        int j = 0;
        while (j < 10) {
            code[2 * j + 1] = KEY.charAt(j);
            j++;
        }
        return String.copyValueOf(code);
    }

    private String form6bit(String src) {
        int length = 6 - src.length();// 需要补齐的长度
        int i = 0;
        StringBuffer buffer = new StringBuffer();
        while (i < length) {
            buffer.append("0");
            i++;
        }
        buffer.append(src);
        return buffer.toString();
    }
}
