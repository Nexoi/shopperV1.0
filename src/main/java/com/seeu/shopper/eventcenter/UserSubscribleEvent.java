package com.seeu.shopper.eventcenter;

import com.seeu.shopper.user.model.UserLogin;
import org.springframework.context.ApplicationEvent;

/**
 * Created by neo on 05/09/2017.
 */

public class UserSubscribleEvent extends ApplicationEvent {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserSubscribleEvent(Object source, String email) {
        super(source);
        // 做点事情
        this.email = email;
    }
}
