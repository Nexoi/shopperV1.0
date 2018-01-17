package com.seeu.shopper.eventcenter;

import com.seeu.shopper.user.model.UserLogin;
import org.springframework.context.ApplicationEvent;

/**
 * Created by neo on 05/09/2017.
 */

public class UserRegistEvent extends ApplicationEvent {

    private UserLogin userLogin;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserLogin getUser() {
        return userLogin;
    }

    public void setUser(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

    public UserRegistEvent(Object source, UserLogin user, String name) {
        super(source);
        // 做点事情
        this.userName = name;
        setUser(user);
    }
}
