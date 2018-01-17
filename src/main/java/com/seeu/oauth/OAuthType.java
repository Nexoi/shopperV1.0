package com.seeu.oauth;

/**
 * Created by neo on 08/07/2017.
 */
public class OAuthType {
    public static final String SEEU = "seeu";
    public static final String REGISTING = "registing";// 正在注册中
    public static final String VISITOR = "visitor";// 游客
    public static final String ADMIN = "admin";// 游客

    /**
     * 检测是否为已经注册的用户
     */
    public static boolean isAvailable(String type) {
        switch (type) {
            case SEEU:
                return true;
            default:
                return false;
        }
    }

    /**
     * 检测是否为正在注册中的用户（ 邮箱注册 ）
     */
    public static boolean isRegisting(String type) {
        switch (type) {
            case REGISTING:
                return true;
            default:
                return false;
        }
    }
    /**
     * 检测是否为游客
     */
    public static boolean isVisitor(String type) {
        return VISITOR.equals(type);
    }
}

