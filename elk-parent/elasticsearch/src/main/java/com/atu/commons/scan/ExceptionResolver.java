package com.atu.commons.scan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.atu.commons.exception.MyException;
import com.atu.util.BeanUtils;
import com.atu.util.JsonUtil;
import com.atu.util.WebUtils;
import com.atu.util.result.Result;


/**
 * 异常处理，对ajax类型的异常返回ajax错误，避免页面问题
 */
@Component
public class ExceptionResolver implements HandlerExceptionResolver {
	private static final Logger LOGGER = LogManager.getLogger(ExceptionResolver.class);

	@Autowired
	private JacksonObjectMapper jacksonObjectMapper;
	@SuppressWarnings("unchecked")
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
										 HttpServletResponse response, Object handler, Exception e) {
		// log记录异常
		LOGGER.error(e.getMessage(), e);
		// 非控制器请求照成的异常
		if (!(handler instanceof HandlerMethod)) {
			return new ModelAndView("error/500");
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		/**
		 * 是否是ajax请求
		 */
		if (WebUtils.isAjax(handlerMethod)) {
			Result result = new Result();
			result.setMsg(e.getMessage());
			MappingJackson2JsonView view = new MappingJackson2JsonView();
			view.setObjectMapper(jacksonObjectMapper);
			view.setContentType("text/html;charset=UTF-8");
			return new ModelAndView(view, BeanUtils.toMap(result));
		}
		if(e instanceof UnauthorizedException){
			return new ModelAndView("error/unauth").addObject("error",e.getMessage());
		}
		if(e instanceof MyException) {
			Result result = new Result();
			result.setMsg(e.getMessage());
			MappingJackson2JsonView view = new MappingJackson2JsonView();
			view.setObjectMapper(jacksonObjectMapper);
			view.setContentType("text/html;charset=UTF-8");
			LOGGER.info(JsonUtil.toJson(result));
			return new ModelAndView(view, BeanUtils.toMap(result));
		}
		return new ModelAndView("error/500").addObject("error", e.getMessage());
	}

}