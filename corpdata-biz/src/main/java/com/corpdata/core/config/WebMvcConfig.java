package com.corpdata.core.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;
import com.corpdata.common.enums.HttpCodeEnum;
import com.corpdata.common.result.Result;
import com.corpdata.core.exception.ServiceException;
import com.corpdata.system.error.entity.SysHttpError;
import com.corpdata.system.error.service.SysHttpErrorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Spring MVC 配置
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);
    
    @Value("${spring.profiles.active}")
    private String env;//当前激活的配置文件

    @Autowired
    private SysHttpErrorService sysHttpErrorService;
     
    //统一异常处理
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new HandlerExceptionResolver() {
            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
                Result result = new Result();
                if (e instanceof ServiceException) {//业务失败的异常，如“账号或密码错误”
                	result.setCode(HttpCodeEnum.FAIL.getCode());
                	result.setError(e.getMessage());
                    result.setMessage(e.getMessage());
                    logger.info(e.getMessage());
                } else if (e instanceof NoHandlerFoundException) {
                	result.setCode(HttpCodeEnum.NOT_FOUND.getCode());
                	result.setError("服务 [" + request.getRequestURI() + "] 不存在");
                    result.setMessage("服务 [" + request.getRequestURI() + "] 不存在");
                } else if (e instanceof ServletException) {
                	result.setCode(HttpCodeEnum.FAIL.getCode());
                	result.setError(e.getMessage());
                    result.setMessage(e.getMessage());
                } else {
                	result.setCode(HttpCodeEnum.INTERNAL_SERVER_ERROR.getCode());
                	result.setError("服务 [" + request.getRequestURI() + "] 内部错误，请联系管理员");
                    result.setMessage("服务内部错误，请联系管理员");
                    String message;
                    String errorMethod = "";
                    String errorServiceUrl="";
                    if (handler instanceof HandlerMethod) {
                        HandlerMethod handlerMethod = (HandlerMethod) handler;
                        message = String.format("服务 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
                                request.getRequestURI(),
                                handlerMethod.getBean().getClass().getName(),
                                handlerMethod.getMethod().getName(),
                                e.getMessage());
                        errorMethod = handlerMethod.getBean().getClass().getName()+"."+handlerMethod.getMethod().getName();
                        errorServiceUrl=request.getRequestURI();
                    } else {
                        message = e.getMessage();
                    }
                    logger.error(message, e);
                    
                    //存储异常到数据库
                    SysHttpError record = new SysHttpError();
                    record.setErrorMessage(message);
                    record.setErrorServiceUrl(errorServiceUrl);
                    record.setErrorServiceMethod(errorMethod);
                    record.setIpAddress(getIpAddress(request));
                    sysHttpErrorService.save(record);
                }
                responseResult(response, result);
                return new ModelAndView();
            }
        });
    }

    //解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //registry.addMapping("/**");
    }

    //添加拦截器
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //接口签名认证拦截器，该签名认证比较简单，实际项目中可以使用Json Web Token或其他更好的方式替代。
//        if (!"dev".equals(env) && !"pro".equals(env)) { //开发环境忽略签名认证
//            registry.addInterceptor(new HandlerInterceptorAdapter() {
//                @Override
//                public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//                    //验证签名
//                    boolean pass = validateSign(request);
//                    if (pass) {
//                        return true;
//                    } else {
//                        logger.warn("签名认证失败，请求接口：{}，请求IP：{}，请求参数：{}",
//                                request.getRequestURI(), getIpAddress(request), JSON.toJSONString(request.getParameterMap()));
//
//                        Result result = new Result();
//                        result.setCode(HttpCodeEnum.UNAUTHORIZED.getCode());
//                        result.setMessage("签名认证失败");
//                        responseResult(response, result);
//                        return false;
//                    }
//                }
//            });
//        }
//    }

    private void responseResult(HttpServletResponse response, Result result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            logger.error(ex.getMessage());
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
        // 如果是多级代理，那么取第一个ip为客户端ip
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(0, ip.indexOf(",")).trim();
        }
        return ip;
    }
}
