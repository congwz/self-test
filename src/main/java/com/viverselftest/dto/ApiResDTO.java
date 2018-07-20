package com.viverselftest.dto;

import lombok.Data;

/**
 * Created by Haoxu.Mu on 2017/7/20.
 */
@Data
public class ApiResDTO {
    private String success;
    private String errorCode;
    private String errorMessage;
    private Object data;
}
