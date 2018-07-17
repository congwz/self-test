package com.viverselftest.exception;

import lombok.Data;

/**
* @author muhaoxu 
* @date 2016年12月20日 下午4:58:04
* @version:1.0
* 类说明 
*/
@Data
public class ErrorException extends RuntimeException {
    private String errorCode;
    private String errorMsg;

    public ErrorException(String errorCode, String errorMsg) {
        super(errorCode + ":" + errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
} 
