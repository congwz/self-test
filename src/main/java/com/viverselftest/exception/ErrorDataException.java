package com.viverselftest.exception;

import lombok.Data;

/**
* @author muhaoxu 
* @date 2016年12月20日 下午4:58:04
* @version:1.0
* 类说明 
*/
@Data
public class ErrorDataException extends RuntimeException {

    private String errorCode;

    private String errorMsg;

    private Object errorData;

    public ErrorDataException(String errorCode, String errorMsg,Object errorData) {

        super(errorCode + ":" + errorMsg + ":" + errorData);
        System.out.println("Object errorData :" + errorData);

        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.errorData = errorData;
    }
} 
