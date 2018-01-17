package com.seeu.shopper.ashop.mainpage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by neo on 10/09/2017.
 */
@Controller
public class NoOauthController {
    @RequestMapping("/noauth")
    public String noOauth(){
        return "shop/noauth";
    }
}
