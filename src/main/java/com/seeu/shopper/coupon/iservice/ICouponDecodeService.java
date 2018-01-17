package com.seeu.shopper.coupon.iservice;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by neo on 30/09/2017.
 */
@Service
public class ICouponDecodeService {
    /**
     * 返回 CID
     *
     * @param CDKEY
     * @return
     */
    public String decode(String CDKEY) {
        if (CDKEY.length() != 20) {
            return null;
        }
        // ~
        char cid[] = new char[4];
        char num[] = new char[6];
        char key[] = new char[10];

        int i = 0;
        while (i < 4) {
            cid[i] = CDKEY.charAt(i * 2);
            i++;
        }
        int j = 0;
        while (j < 6) {
            num[j] = CDKEY.charAt(j * 2 + 8);
            j++;
        }
        int k = 0;
        while (k < 10) {
            key[k] = CDKEY.charAt(2 * k + 1);
            k++;
        }
        String MD5KEY = md5(String.copyValueOf(cid), String.copyValueOf(num));
        if ((MD5KEY != null) && MD5KEY.equals(String.copyValueOf(key))) {
            return String.copyValueOf(cid);
        }
        return null;
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
}
