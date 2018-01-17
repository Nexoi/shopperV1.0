package com.seeu.shopper.eventcenter;

import com.seeu.shopper.user.model.UserLogin;
import org.springframework.context.ApplicationEvent;

/**
 * Created by neo on 05/09/2017.
 */

public class OrderSendEvent extends ApplicationEvent {

    private String oid;
    private String shipCode;// 物流快递单号

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getShipCode() {
        return shipCode;
    }

    public void setShipCode(String shipCode) {
        this.shipCode = shipCode;
    }

    public OrderSendEvent(Object source, String oid, String shipCode) {
        super(source);
        // 做点事情
        this.oid = oid;
        this.shipCode = shipCode;
    }
}
