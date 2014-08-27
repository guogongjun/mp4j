package com.yeapoo.odaesan.api.controller.advice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.yeapoo.odaesan.common.model.DataWrapper;

/**
 * 异常处理类，用于处理在处理请求过程中产生的各种异常。
 * 
 * @author Simon
 *
 */
@ControllerAdvice
public class ExceptionAdvice {
    private static Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @Value("${project.baseURL}")
    private String baseURL;

    @Autowired
    private ObjectMapper mapper;

    /**
     * 最高层次的异常处理，返回500错误
     * 
     * @param ex 触发的异常
     * @param request 触发异常导致进入此方法的请求
     * @param response 触发异常导致进入此方法的响应
     */
    @ExceptionHandler(Throwable.class)
    public void handleRuntimeException(Throwable ex, HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.error("Exception occured when process request => {}, exception => {}", request.getRequestURL(), ex.getMessage());
        ex.printStackTrace(); //TODO remove this
        DataWrapper wrapper = new DataWrapper(500, ex.getMessage());
        String content = mapper.writeValueAsString(wrapper);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(content);
        response.flushBuffer();
    }
}
