package com.viverselftest.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL) //保证序列化json的时候，如果null的对象，key也会消失
public class ServerResponse<T> implements Serializable {

    private int status;
    private String msg;
    //返回数据【泛型】
    private T data;

    //私有构造方法
    private ServerResponse(int status,String msg){
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(int status,String msg,T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore
    public boolean isSuccess(){
        //1-成功  0-失败
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    //静态方法
    public static <T> ServerResponse<T> successCodeMsg(String msg){
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
    }

}
