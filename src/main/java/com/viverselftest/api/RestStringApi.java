package com.viverselftest.api;

import com.viverselftest.common.ServerResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(description = "字符串接口")
@RestController
@RequestMapping("/api/string")
public class RestStringApi {

    @ApiOperation(value = "字符串返回")
    @GetMapping("/rest")
    public String strRest(@RequestParam("str") String str){
        if("01000207".equals(str)){
            return "张聪伟";
        }
        return "不留名";
    }

    @ApiOperation(value = "路径参数")
    @PostMapping("/path/test/{str}")
    public int pathTest(@PathVariable String str){
        return Integer.valueOf(str);
    }

    @ApiOperation(value = "字符串返回")
    @GetMapping("/rest-server-response")
    public ServerResponse<String> strRestServerResponse(@RequestParam("str") String str){
        if("01000207".equals(str)){
            //return "张聪伟";
            return ServerResponse.successCodeMsgData("成功","张聪伟");
        }
        return ServerResponse.successCodeMsg("不留名msg");
    }

    @ApiOperation(value = "路径参数")
    @PostMapping("/path/test/server/response{str}")
    public ServerResponse pathTestServerResponse(@PathVariable String str){
        if(str.matches("[0-9]+")){
            return ServerResponse.successCodeMsgData("整数msg",Integer.valueOf(str));

        }
        return ServerResponse.errorCodeMsgSelf(555,"字符串转换错误！");
    }


}
