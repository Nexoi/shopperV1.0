package com.seeu.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by neo on 03/08/2017.
 * <p>
 * 优惠券生成、校验
 */
public class CopCreater {
    public static void main(String[] args) {
        CopCreater copCreater = new CopCreater();
        copCreater.create("AE86", 0, 2);
    }


    /**
     * @param CID
     * @param start 优惠券序列号起始点
     * @param count 准备生成优惠券数量
     * @return
     */
    public String create(String CID, Integer start, Integer count) {
        if (count > 0xFFFFFF) {
            return "数量过大";
        }
        if (CID.length() != 4) {
            return "CID 错误";
        }
        if (start < 0) {
            return "起始值错误";
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
        return "";
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
            return byteArrayToHex(resultByteArray).substring(0,10);
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
