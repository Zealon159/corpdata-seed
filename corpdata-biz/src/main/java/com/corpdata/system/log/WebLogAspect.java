package com.corpdata.system.log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.corpdata.system.log.entity.WebLogger;
import com.corpdata.system.log.service.WebLogService;
import com.corpdata.system.log.util.WebLoggerUtils;
import com.corpdata.system.security.shiro.util.UserUtil;

@Aspect
@Component
public class WebLogAspect {

	private Logger logger = Logger.getLogger(getClass());

	ThreadLocal<WebLogger> loggerEntityThreadLocal = new ThreadLocal<WebLogger>();
	
	/**
	 * 定义切点（基于注解）
	 */
	@Pointcut("@annotation(com.corpdata.system.log.WebLog)")
	public void webLog() {
	}

	/**
	 * 前置通知，方法调用前被调用
	 * 
	 * @param joinPoint
	 */
	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		// 获取方法签名
		MethodSignature ms = (MethodSignature)joinPoint.getSignature();
		// 获取注解类
		WebLog log = ms.getMethod().getAnnotation(WebLog.class);
		
		// 创建日志实体
		WebLogger logger = new WebLogger();
		logger.setTitle(log.name());
		// 请求开始时间
		logger.setStartTime(System.currentTimeMillis());
		// 获取请求sessionId
		String sessionId = request.getRequestedSessionId();
		// 请求路径
		String url = request.getRequestURI();
		// 获取请求参数信息
		String paramData = JSON.toJSONString(request.getParameterMap(),
				SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue);
		// 设置客户端ip
		logger.setClientIp(WebLoggerUtils.getCliectIp(request));
		// 设置请求方法
		logger.setMethod(request.getMethod());
		// 设置请求类型（json|普通请求）
		logger.setType(WebLoggerUtils.getRequestType(request));
		// 设置请求地址
		logger.setUri(url);
		// 设置sessionId
		logger.setSessionId(sessionId);
		// 请求的类及名称
		logger.setClassMethod(
				joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
		// 设置请求参数内容json字符串
		logger.setParamData(paramData);
		loggerEntityThreadLocal.set(logger);
	}

	/**
	 * 后置返回通知
	 * 
	 * @param joinPoint
	 * @param returnData
	 * @throws Throwable
	 */
	@AfterReturning(returning = "returnData", pointcut = "webLog()")
	public void doAfterReturning(JoinPoint joinPoint, Object returnData) {
		
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = attributes.getResponse();

		// 获取请求错误码
		int status = response.getStatus();
		// 获取本次请求日志实体
		WebLogger loggerEntity = loggerEntityThreadLocal.get();
		// 操作人id
		loggerEntity.setUserId(UserUtil.getCurrentUserid());
		// 请求结束时间
		loggerEntity.setEndTime(System.currentTimeMillis());
		// 设置请求时间差
		loggerEntity.setTimeConsuming(Integer.valueOf((loggerEntity.getEndTime() - loggerEntity.getStartTime()) + ""));
		// 设置返回时间
		loggerEntity.setReturnTime(loggerEntity.getEndTime() + "");
		// 设置返回错误码
		loggerEntity.setHttpStatusCode(status + "");
		// 设置返回值
		loggerEntity.setReturnData(JSON.toJSONString(returnData, SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.WriteMapNullValue));
		// 执行将日志写入数据库
		logger.info(JSON.toJSONString(loggerEntity));
	}

	@AfterThrowing(value = "webLog()", throwing = "exception")
	public void doAfterThrowingAdvice(JoinPoint joinPoint, Throwable exception) {
		// 目标方法名：
		WebLogger loggerEntity = loggerEntityThreadLocal.get();
		loggerEntity.setExceptionMessage(exception.getMessage());
		logger.info(JSON.toJSONString(loggerEntity));
		// if(exception instanceof NullPointerException){
		// System.out.println("发生了空指针异常!!!!!");
		// }
	}
}
