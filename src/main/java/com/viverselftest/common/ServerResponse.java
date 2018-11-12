package com.viverselftest.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;

@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL) //保证序列化json的时候，如果null的对象，key也会消失
@JsonSerialize
public class ServerResponse<T> implements Serializable {

    //private int status;
    //private String msg;
    //返回数据【泛型】
    private T data;

    private String success;
    private String errorCode;
    private String errorMessage;

    //私有构造方法
   /* private ServerResponse(int status,String msg){
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(int status,String msg,T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }*/

    private ServerResponse(String success, String errorCode, String errorMessage, T data){
        this.success = success;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }


    //@JsonIgnore
    /*public boolean isSuccess(){
        //1-成功  0-失败
        return this.status == ResponseCode.SUCCESS.getCode();
    }*/

    //静态方法
    /*public static <T> ServerResponse<T> successCodeMsg(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static <T> ServerResponse<T> successCodeMsgData(String msg,T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }

    public static <T> ServerResponse<T> errorCodeMsg(String msg){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),msg);
    }

    public static <T> ServerResponse<T> errorCodeMsgData(String msg,T data){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),msg,data);
    }

    public static <T> ServerResponse<T> errorCodeMsgSelf(int code,String msg){
        return new ServerResponse<T>(code,msg);
    }*/





    //api
    public static <T> ServerResponse<T> successData(T data){
        //return new ServerResponse<T>("1","","",data);
        return new ServerResponse("1","","",data);
    }

    public static <T> ServerResponse<T> errorMsg(String errorCode,String errorMessage){
        return new ServerResponse<T>("0",errorCode,errorMessage,null);
    }

    public static <T> ServerResponse<T> errorMsgData(String errorCode,String errorMessage,T data){
        return new ServerResponse<T>("0",errorCode,errorMessage,data);
    }

}
