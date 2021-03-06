package com.viverselftest.common;

public enum ResponseCode {

    SUCCESS(1,"SUCCESS"),
    ERROR(0,"ERROR");

    private final int code;

    private final String desc;

    ResponseCode(int code, String desc){
        this.code = code;
        this.desc = desc;
    }


    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
