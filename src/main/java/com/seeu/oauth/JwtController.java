package com.seeu.oauth;

import com.seeu.core.Result;
import com.seeu.core.ResultGenerator;
import com.seeu.core.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by neo on 02/07/2017.
 */
@RestController
@RequestMapping("/api/token")
public class JwtController {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtConstant jwtConstant;
//
//    @RequestMapping("/demo")
//    public String demo(HttpServletRequest request,HttpServletResponse response){
//        // from param
//        String access_token = request.getParameter("access_token");
//        // from header
//        if (access_token == null) access_token = request.getHeader("access_token");
//        // from cookie
//        if (access_token == null) {
//            Cookie[] cookies = request.getCookies();
//            if (cookies != null)
//                for (Cookie cookie : cookies) {
//                    if ("access_token".equals(cookie.getName())) {
//                        access_token = cookie.getValue();
//                        break;
//                    }
//                }
//        }
//        // 拿到了 access_token
//        // 验证：
//        String result = Http请求("http://9.115.83.185:7073/demo/getResource?a=100&b=111&access_token="+access_token);
//        // 根据结果返回验证情况
//        if("成功".equals(result)){
//            // 如果成功，可以刷新 token
//            String newToken = Http请求("http://9.115.83.185:7073/demo/刷新token的地址?&access_token="+access_token);
//            // 将新 token 写入 cookie
//            Cookie cookie = new Cookie("access_token",newToken);
//            response.addCookie(cookie);
//
//            return "errorpage";
//        }else{
//            return "successpage";
//        }
//    }


    /**
     * 更新签名
     * @param request
     * @return
     * @throws ServiceException
     */
    @RequestMapping("refresh")
    public Result refreshToken(HttpServletRequest request) throws ServiceException {
        // token 一般在 header 的 cookie 里面，所以直接取出来就好
        // from param
        String signToken = request.getParameter("token");
        // from header
        if (signToken == null) signToken = request.getHeader("token");
        // from cookie
        if (signToken == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null)
                for (Cookie cookie : cookies) {
                    if ("token".equals(cookie.getName())) {
                        signToken = cookie.getValue();
                        break;
                    }
                }
        }
        //验证签名
        SignTokenUser user = null;
        if (StringUtils.isNotEmpty(signToken) && (user = jwtUtil.parseToken(signToken)) != null) {
            // 成功
            try {
                String subject = jwtUtil.generalSubject(user);
                String token = jwtUtil.createJWT(jwtConstant.getJWT_ID(), subject, jwtConstant.getJWT_INTERVAL());
                return ResultGenerator.genSuccessResult(token);
            } catch (Exception e) {
                throw new ServiceException("签名更新失败");
            }
        }
        // 失败
        return ResultGenerator.genNoAuthResult("签名验证失败，无法更新");
    }

    // 模拟生成一个
    @RequestMapping("/test")
    public Result getDemoInfo(){
        // 生成一个 token , uid = 001, type = seeu, extra = test
        SignTokenUser user = new SignTokenUser();
        user.setUid("1");
        user.setType("seeu");
        user.setExtra("test");
        try {
            String subject = jwtUtil.generalSubject(user);
            String token = jwtUtil.createJWT(jwtConstant.getJWT_ID(), subject, jwtConstant.getJWT_INTERVAL());
            return ResultGenerator.genSuccessResult(token);
        } catch (Exception e) {
            throw new ServiceException("换取签名 TOKEN 失败");
        }

    }
}
