package com.seeu.shopper.ashop.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by neo on 03/09/2017.
 * <p>
 * 单位换算器
 * <p>
 * 更新建议：提供数据库更改
 */
@Service
public class UnitService {
    /**
     * @param coin  coin type
     * @param price USD
     * @return
     */
    public BigDecimal exchangeUnit(String coin, BigDecimal price) {
        if (price == null) {
            return BigDecimal.valueOf(0.00);
        }
        if (coin == null) {
            return price.setScale(2, BigDecimal.ROUND_UP);
        }
        switch (coin) {
            case "USD":
                return price;
            case "EUR":
                return price.multiply(BigDecimal.valueOf(0.8432)).setScale(2, BigDecimal.ROUND_UP);
            case "CAD":
                return price.multiply(BigDecimal.valueOf(1.2397)).setScale(2, BigDecimal.ROUND_UP);
            case "GBP":
                return price.multiply(BigDecimal.valueOf(0.7721)).setScale(2, BigDecimal.ROUND_UP);
            default:
                return price.setScale(2, BigDecimal.ROUND_UP);
        }
    }

    /**
     * 获取单位的 icon 图标
     *
     * @param coinType
     * @return
     */
    public String getUnit(String coinType) {
        if (coinType == null) {
            return "US$";
        }
        switch (coinType) {
            case "USD":
                return "US$";
            case "EUR":
                return "EU€";
            case "CAD":
                return "CA$";
            case "GBP":
                return "GB£";
            default:
                return "US$";
        }
    }

    public boolean isAvailable(String coinType) {
        if (coinType == null) {
            return false;
        }
        switch (coinType) {
            case "USD":
            case "EUR":
            case "CAD":
            case "GBP":
                return true;
        }
        return false;
    }
}
