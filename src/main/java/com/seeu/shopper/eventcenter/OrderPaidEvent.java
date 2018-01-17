package com.seeu.shopper.eventcenter;

import org.springframework.context.ApplicationEvent;

/**
 * Created by neo on 05/09/2017.
 */

public class OrderPaidEvent extends ApplicationEvent {

    private String oid;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public OrderPaidEvent(Object source, String oid) {
        super(source);
        // 做点事情
        this.oid = oid;
    }
}
