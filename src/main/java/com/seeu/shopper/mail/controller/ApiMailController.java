package com.seeu.shopper.mail.controller;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.shopper.mail.service.EmailSendUtilService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 目前只提供给管理员使用
 * <p>
 * Created by neo on 20/09/2017.
 */
@RestController
@RequestMapping("/api/admin/v1/mail")
public class ApiMailController {

    @Resource
    EmailSendUtilService emailSendUtilService;

    @PostMapping("/send")
    public Result sendMail(@RequestParam("email") String email,
                           @RequestParam("title") String title,
                           @RequestParam("text") String htmlText) {
        try {
            boolean result = emailSendUtilService.send(email, title, htmlText);
            if (result) {
                return ResultGenerator.genSuccessResult();
            } else {
                return ResultGenerator.genFailResult("发送失败");
            }
        } catch (Exception e) {
            return ResultGenerator.genExceptionResult(e.getMessage());
        }
    }
}
