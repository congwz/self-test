package com.viverselftest.global;
import com.viverselftest.common.ServerResponse;
import com.viverselftest.exception.ErrorDataException;
import com.viverselftest.exception.ErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = ErrorException.class)
    @ResponseBody
    public ServerResponse jsonErrorHandler(HttpServletRequest req, ErrorException e) {
        return ServerResponse.errorMsg(e.getErrorCode(), e.getErrorMsg());
    }

    @ExceptionHandler(value = ErrorDataException.class)
    @ResponseBody
    public ServerResponse errorDataException(HttpServletRequest req, ErrorDataException e) {
        System.out.println("e.getErrorData() :" + e.getErrorData());
        return ServerResponse.errorMsgData(e.getErrorCode(),e.getErrorMsg(),e.getErrorData());
    }



    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ServerResponse methodArgumentTypeMismatch(HttpServletRequest req, MethodArgumentTypeMismatchException e) {
        logger.info(e.getMessage());
        return ServerResponse.errorMsg("100001", "method argument type mismatch exception:" + e.getMessage());
    }


    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public ServerResponse missingServletRequestParameter(HttpServletRequest req, MissingServletRequestParameterException e) {
        logger.info(e.getMessage());
        return ServerResponse.errorMsg("100001", "missing servlet request Parameter exception:" + e.getMessage());
    }


    @ExceptionHandler(value = SQLException.class)
    @ResponseBody
    public ServerResponse sql(HttpServletRequest req, SQLException e) {
        logger.info("sql exception :" + e .getMessage());
        return ServerResponse.errorMsg("100001", "sql exception: " + e.getMessage());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public ServerResponse constraintViolationException(HttpServletRequest req, ConstraintViolationException e) {
        logger.info(e.getMessage());
        return ServerResponse.errorMsg("100001", "parameter error: " + e.getMessage());
    }

    @ExceptionHandler(value = IOException.class)
    @ResponseBody
    public ServerResponse ioException(HttpServletRequest req, IOException e) {
        logger.info(e.getMessage());
        return ServerResponse.errorMsg("100001", "IO error： " + e.getMessage());
    }

    @ExceptionHandler(value = InterruptedException.class)
    @ResponseBody
    public ServerResponse interruptedException(HttpServletRequest req, InterruptedException e) {
        logger.info(e.getMessage());
        //进程打断
        return ServerResponse.errorMsg("100001", "process interrupt: " + e.getMessage());
    }
}
