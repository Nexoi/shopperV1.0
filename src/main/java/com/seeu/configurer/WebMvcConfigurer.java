package com.seeu.configurer;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.seeu.core.Result;
import com.seeu.core.ResultCode;
import com.seeu.core.ServiceException;
import com.seeu.oauth.JwtUtil;
import com.seeu.oauth.OAuthType;
import com.seeu.oauth.SignTokenUser;
import com.seeu.shopper.ashop.domain.footer.FooterModel;
import com.seeu.shopper.ashop.domain.header.HeaderModel;
import com.seeu.shopper.ashop.domain.FullPageModel;
import com.seeu.shopper.page.service.PageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Spring MVC 配置
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(WebMvcConfigurer.class);
    @Value("${spring.profiles.active}")
    private String env;//当前激活的配置文件


    @Autowired
    private JwtUtil jwtUtil;
    @Resource
    private PageService pageService;

    //使用阿里 FastJson 作为JSON MessageConverter
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue,//保留空的字段
                SerializerFeature.WriteNullStringAsEmpty,//String null -> ""
                SerializerFeature.WriteNullNumberAsZero);//Number null -> 0
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        converters.add(converter);

//        ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
//        List<MediaType> list = new ArrayList<MediaType>();
//        list.add(MediaType.IMAGE_JPEG);
//        list.add(MediaType.IMAGE_PNG);
//        list.add(MediaType.APPLICATION_OCTET_STREAM);
//        byteArrayHttpMessageConverter.setSupportedMediaTypes(list);
//
//        converters.add(byteArrayHttpMessageConverter);

//        super.configureMessageConverters(converters);
    }

    //统一异常处理
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new HandlerExceptionResolver() {
            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
                Result result = new Result();
                if (handler instanceof HandlerMethod) {
                    HandlerMethod handlerMethod = (HandlerMethod) handler;

                    if (e instanceof ServiceException) {//业务失败的异常，如“账号或密码错误”
                        String msg = e.getMessage();
                        if (msg.contains("#")) {// 带错误码的返回 （ Message 里面用#隔开，如：[ 404#无此文件 ] ）
                            try {
                                String[] msgs = msg.split("#");
                                result.setCode(Integer.parseInt(msgs[0])).setMessage(msgs[1]);
                            } catch (Exception ee) {
                                result.setCode(ResultCode.FAIL).setMessage(e.getMessage());
                            }
                        } else {
                            result.setCode(ResultCode.FAIL).setMessage(e.getMessage());
                        }
                        logger.info(e.getMessage());
                    } else if (e instanceof NullPointerException) {
                        result.setCode(ResultCode.FAIL).setMessage("请确认传入参数是否完整 [ NullPointerException ]");
                        logger.info(e.getMessage());
                    } else if (e instanceof MissingServletRequestParameterException) {
                        result.setCode(ResultCode.FAIL).setMessage("请确认传入参数是否完整 [ " + e.getMessage() + " ]");
                        logger.info(e.getMessage());
                    } else if (e instanceof MethodArgumentTypeMismatchException) {
                        result.setCode(ResultCode.FAIL).setMessage("请确认传入参数是否正确 [ " + e.getMessage() + " ]");
                        logger.info(e.getMessage());
                    } else {
                        result.setCode(ResultCode.INTERNAL_SERVER_ERROR).setMessage("接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员");
                        String message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                                request.getRequestURI(),
                                handlerMethod.getBean().getClass().getName(),
                                handlerMethod.getMethod().getName(),
                                e.getMessage());
                        logger.error(message, e);
                    }
                } else {
                    if (e instanceof NoHandlerFoundException) {
                        result.setCode(ResultCode.NOT_FOUND).setMessage("接口 [" + request.getRequestURI() + "] 不存在");
                    } else if (e instanceof HttpRequestMethodNotSupportedException) {
                        result.setCode(ResultCode.FAIL).setMessage("不支持该请求类型 [ " + e.getMessage() + " ]");
                        logger.info(e.getMessage());
                    } else {
                        result.setCode(ResultCode.INTERNAL_SERVER_ERROR).setMessage(e.getMessage());
                        logger.error(e.getMessage(), e);
                    }
                }
//                responseResult(response, result);
                // 这将意味着 api 接口 utf8 比较麻烦了...还是根据请求 url 区分一下吧
                String uri = request.getRequestURI();
                if (uri == null || uri.startsWith("/api")) {
                    responseResult(response, result);
                    return new ModelAndView();
                }
                // 如果不是 api 接口，则返回 html 页面
                ModelAndView modelAndView = new ModelAndView("shop/info");
                modelAndView.addObject("code", result.getCode());
                modelAndView.addObject("message", result.getMessage());
                return modelAndView;
            }

        });
    }

    //解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
    }

    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // SEEU Sign Check
//        if (!StringUtils.contains(env, "dev")) { //开发环境忽略签名认证

        // 管理员身份验证
        registry.addInterceptor(new HandlerInterceptorAdapter() {

            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

                String signToken = null;
                // from cookie
                if (signToken == null) {
                    Cookie[] cookies = request.getCookies();
                    if (cookies != null)
                        for (Cookie cookie : cookies) {
                            if ("stoken".equals(cookie.getName())) {
                                signToken = cookie.getValue();
                                if (signToken.trim().length() == 0) {
                                    continue;
                                }
                                break;
                            }
                        }
                }
                //验证签名
                SignTokenUser user = null;
                if (StringUtils.isNotEmpty(signToken) && (user = jwtUtil.parseToken(signToken)) != null) {
                    request.setAttribute("aid", user.getUid());
                    request.setAttribute("name", user.getEmail());
                    request.setAttribute("type", user.getType());
                    request.setAttribute("extra", user.getExtra());
                    return true;
                } else {
                    // 重定向到无权访问页面
                    response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
                    response.setHeader("Location", "/adminx/signin.html");
                    // 清除登录 token
                    Cookie cookie = new Cookie("stoken", "");
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    return false;
                }
            }

        }).addPathPatterns("/api/admin/v1/**", "/api/upload/**", "/adminx/**").excludePathPatterns("/adminx/signin.html", "/adminx/login.html");


        // 给所有页面添加 header、footer ， 不需要验证签名
        registry.addInterceptor(new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                // 用户身份验证
                String signToken = null;
                // from cookie
                if (signToken == null) {
                    Cookie[] cookies = request.getCookies();
                    if (cookies != null)
                        for (Cookie cookie : cookies) {
                            if ("token".equals(cookie.getName())) {
                                signToken = cookie.getValue();
                                if (signToken.trim().length() == 0) {
                                    continue;
                                }
                                break;
                            }
                        }
                }
                //验证签名
                SignTokenUser user = null;
                if (StringUtils.isNotEmpty(signToken) && (user = jwtUtil.parseToken(signToken)) != null) {
                    request.setAttribute("uid", user.getUid());
                    request.setAttribute("type", user.getType());
                    request.setAttribute("extra", user.getExtra());
                    request.setAttribute("email", user.getEmail());
                    return true;
                } else {
                    // 游客模式，extra 附带 ip 地址
                    request.setAttribute("uid", "0");
                    request.setAttribute("type", OAuthType.VISITOR);
                    request.setAttribute("extra", getIpAddress(request));
                    return true;
                }
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
                super.postHandle(request, response, handler, modelAndView);
                // 用户属性
                String type = request.getAttribute("type").toString();
                if (OAuthType.isAvailable(type)) {
                    // 注入邮件信息，已放在 email 字段
                    Object email = request.getAttribute("email");
                    modelAndView.addObject("myemail", email); // 如果值为非空则表示 true，空表示 false[thymleaf]
                }

                // header
                String coin = request.getParameter("coin");
                if (coin == null || coin.trim().length() == 0) {
                    coin = "USD";
                }
                HeaderModel headerModel = FullPageModel.getHeaderModel(pageService);
                FooterModel footerModel = FullPageModel.getFooterModel(pageService);

                modelAndView.addObject("coin", coin);
                modelAndView.addObject("header", headerModel);
                modelAndView.addObject("footer", footerModel);
                // footer
                // 暂时无数据
            }
        }).addPathPatterns("/", "/web/*", "/home", "/index", "/index.*", "/product", "/product/**", "/support", "/support/*", "/support*", "/mycart", "/pay/*", "/paypal/cancel", "/paypal/success", "/404", "/500", "/noauth", "/signin", "/login", "/regist", "/activemyaccount", "/activesuccess")
                .excludePathPatterns("/api/**", "/store1/**", "/adminx/**");// 首页和其余页面一致都要添加，图片加载以及各种 API 不需要添加

        // 权限验证后才能进入的页面 header、footer
        registry.addInterceptor(new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                // 用户身份验证
                String signToken = null;
                // from cookie
                if (signToken == null) {
                    Cookie[] cookies = request.getCookies();
                    if (cookies != null)
                        for (Cookie cookie : cookies) {
                            if ("token".equals(cookie.getName())) {
                                signToken = cookie.getValue();
                                if (signToken.trim().length() == 0) {
                                    continue;
                                }
                                break;
                            }
                        }
                }
                //验证签名
                SignTokenUser user = null;
                if (StringUtils.isNotEmpty(signToken) && (user = jwtUtil.parseToken(signToken)) != null) {
                    request.setAttribute("uid", user.getUid());
                    request.setAttribute("type", user.getType());
                    request.setAttribute("extra", user.getExtra());
                    request.setAttribute("email", user.getEmail());
                    return true;
                } else {
                    // 重定向到无权访问页面
                    response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
                    response.setHeader("Location", "/noauth");
                    // 清除登录 token
                    Cookie cookie = new Cookie("token", "");
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    return false;
                }
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
                super.postHandle(request, response, handler, modelAndView);
                // 用户属性
                String type = request.getAttribute("type").toString();
                if (OAuthType.isAvailable(type)) {
                    // 注入邮件信息，已放在 email 字段
                    Object email = request.getAttribute("email");
                    modelAndView.addObject("myemail", email); // 如果值为非空则表示 true，空表示 false[thymleaf]
                }
                // header
                String coin = request.getParameter("coin");
                if (coin == null || coin.trim().length() == 0) {
                    coin = "USD";
                }
                HeaderModel headerModel = FullPageModel.getHeaderModel(pageService);
                FooterModel footerModel = FullPageModel.getFooterModel(pageService);
                modelAndView.addObject("coin", coin);
                modelAndView.addObject("header", headerModel);
                modelAndView.addObject("footer", footerModel);
                // footer
                // 暂时无数据
            }
        }).addPathPatterns("/checkout", "/placeorder", "/banktransfer", "/mycenter/**", "/signout", "/activecoupon", "/activecoupon/**")
                .excludePathPatterns("/adminx/**");

        // 用户权限验证后才能进入的 API
        registry.addInterceptor(new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                // 用户身份验证
                String signToken = null;
                // from cookie
                if (signToken == null) {
                    Cookie[] cookies = request.getCookies();
                    if (cookies != null)
                        for (Cookie cookie : cookies) {
                            if ("token".equals(cookie.getName())) {
                                signToken = cookie.getValue();
                                if (signToken.trim().length() == 0) {
                                    continue;
                                }
                                break;
                            }
                        }
                }
                //验证签名
                SignTokenUser user = null;
                if (StringUtils.isNotEmpty(signToken) && (user = jwtUtil.parseToken(signToken)) != null) {
                    request.setAttribute("uid", user.getUid());
                    request.setAttribute("type", user.getType());
                    request.setAttribute("extra", user.getExtra());
                    request.setAttribute("email", user.getEmail());
                    return true;
                } else {
                    logger.warn("签名认证失败，请求接口：{}，请求IP：{}，请求参数：{}",
                            request.getRequestURI(), getIpAddress(request), JSON.toJSONString(request.getParameterMap()));
                    Result result = new Result();
                    result.setCode(ResultCode.UNAUTHORIZED).setMessage("签名认证失败");
                    // 清除登录 token
                    Cookie cookie = new Cookie("token", "");
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    responseResult(response, result);
                    return false;
                }
            }
        }).addPathPatterns("/api/shop/v1/ct/**").excludePathPatterns("/adminx/**");
    }

    private void responseResult(HttpServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果是多级代理，那么取第一个ip为客户ip
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(0, ip.indexOf(",")).trim();
        }

        return ip;
    }

}
