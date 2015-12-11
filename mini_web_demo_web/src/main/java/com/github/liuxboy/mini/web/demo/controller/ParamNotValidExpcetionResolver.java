package com.github.liuxboy.mini.web.demo.controller;

import com.alibaba.fastjson.JSONObject;

import com.github.liuxboy.mini.web.demo.common.util.Logger;
import com.github.liuxboy.mini.web.demo.common.util.LoggerFactory;
import com.github.liuxboy.mini.web.demo.domain.response.BaseResponseDto;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by liuchundong on 2015-03-08 15:34:26
 */
public class ParamNotValidExpcetionResolver implements PriorityOrdered, HandlerExceptionResolver {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final static String UTF8 = "UTF-8";

    /**
     * Try to resolve the given exception that got thrown during on handler execution,
     * returning a ModelAndView that represents a specific error page if appropriate.
     * <p>The returned ModelAndView may be {@linkplain org.springframework.web.servlet.ModelAndView#isEmpty() empty}
     * to indicate that the exception has been resolved successfully but that no view
     * should be rendered, for instance by setting a status code.
     *
     * @param request  current HTTP liu.chun.dong.request
     * @param response current HTTP liu.chun.dong.response
     * @param handler  the executed handler, or <code>null</code> if none chosen at the
     *                 time of the exception (for example, if multipart resolution failed)
     * @param ex       the exception that got thrown during handler execution
     * @return a corresponding ModelAndView to forward to,
     * or <code>null</code> for default processing
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.error(ex);
        if (!(ex instanceof MethodArgumentNotValidException)) {
            return null;
        }
        MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;
        BaseResponseDto baseResponseDto = new BaseResponseDto();
        baseResponseDto.setResultCode(1);

        // 自行处理下提示信息
        BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
        List<FieldError> errors = bindingResult.getFieldErrors();
        if (CollectionUtils.isEmpty(errors)) {
            baseResponseDto.setResultMsg(methodArgumentNotValidException.getMessage());
        } else {
            StringBuilder builder = new StringBuilder();
            for (FieldError fieldError : errors) {
                builder.append(fieldError.getObjectName());
                builder.append("的字段");
                builder.append(fieldError.getField());
                builder.append("的值为:");
                builder.append(fieldError.getRejectedValue());
                builder.append("->");
                builder.append(fieldError.getDefaultMessage());
                builder.append(";");
            }
            if (builder.length() > 0) {
                builder.deleteCharAt(builder.length() - 1);
            }
            logger.error(builder.toString());
            baseResponseDto.setResultMsg(builder.toString());
        }
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(JSONObject.toJSONBytes(baseResponseDto));
        } catch (IOException e) {
            logger.error(e);
        }
        return new ModelAndView();
    }

    /**
     * Return the order value of this object, with a
     * higher value meaning greater in terms of sorting.
     * <p>Normally starting with 0, with <code>Integer.MAX_VALUE</code>
     * indicating the greatest value. Same order values will result
     * in arbitrary positions for the affected objects.
     * <p>Higher values can be interpreted as lower priority. As a
     * consequence, the object with the lowest value has highest priority
     * (somewhat analogous to Servlet "load-on-startup" values).
     *
     * @return the order value
     * 这个值没有什么用处，我们是通过继承PriorityOrdered来让自己排序到第一位的
     */
    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }
}
