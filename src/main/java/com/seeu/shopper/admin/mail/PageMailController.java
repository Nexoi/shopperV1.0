package com.seeu.shopper.admin.mail;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by neo on 20/09/2017.
 */
@Controller
@RequestMapping("/adminx")
public class PageMailController {
    @RequestMapping("mail.html")
    public String mailPage() {
        return "adminx/mail";
    }
}
