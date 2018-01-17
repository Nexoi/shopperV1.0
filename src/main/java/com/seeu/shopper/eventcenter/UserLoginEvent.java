package com.seeu.shopper.eventcenter;

import com.seeu.shopper.user.model.UserLogin;
import org.springframework.context.ApplicationEvent;

/**
 * Created by neo on 05/09/2017.
 */

public class UserLoginEvent extends ApplicationEvent {

    private UserLogin userLogin;

    public UserLogin getUser() {
        return userLogin;
    }

    public void setUser(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

    public UserLoginEvent(Object source, UserLogin user) {
        super(source);
        // 做点事情
        setUser(user);
    }
}
