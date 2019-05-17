package com.xtw.consumer.web;

import com.xtw.api.util.ResultVoUtil;
import com.xtw.api.vo.ResultVo;
import com.xtw.api.exception.MyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : tianwen.xiao
 * @ClassName : GlobleExceptionHandler
 * @Description : 统一异常处理
 * @date : created in 2019/3/6 10:00 AM
 * @Version : 1.0
 */
@ControllerAdvice
public class GlobleExceptionHandler {


    /**
     * 拦截捕捉自定义异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MyException.class)
    public ResultVo myErrorHandler(MyException e){
        return ResultVoUtil.ErroeVo(e.getCode(),  e.getMessage());
    }


    /**
     * 拦截捕捉全局异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResultVo errorHandler(Exception e){
        return ResultVoUtil.ErroeVo(500,e.getMessage());
    }
}
