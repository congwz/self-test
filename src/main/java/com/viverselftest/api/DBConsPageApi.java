package com.viverselftest.api;

import com.github.pagehelper.PageInfo;
import com.viverselftest.common.ServerResponse;
import com.viverselftest.po.DBConsPagePO;
import com.viverselftest.service.DBConsPageService;
import com.viverselftest.service.MyBatisMapperIfJudgeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "数据库连接与分页接口")
@RestController
@RequestMapping("/api/db/page")
public class DBConsPageApi {

    @Autowired
    private DBConsPageService dbConsPageService;

    @Autowired
    private MyBatisMapperIfJudgeService myBatisMapperIfJudgeService;

    @ApiOperation(value = "查询数据库List-计数")
    @GetMapping("/get/count")
    public int getCountInfo(){
        return dbConsPageService.getCountInfo();
    }

    @ApiOperation(value = "查询数据库List")
    @GetMapping("/get/{workCode}/info")
    //public List<DBConsPagePO> getInfo(@PathVariable String workCode){
    public ServerResponse<List<DBConsPagePO>> getInfo(@PathVariable String workCode){
        //return dbConsPageService.getInfo(workCode);
        List<DBConsPagePO> res = dbConsPageService.getInfo(workCode);
        if(res != null && res.size() > 0){
            return ServerResponse.successCodeMsgData("find data list ok!",dbConsPageService.getInfo(workCode));
        }
        return ServerResponse.errorCodeMsg("该工号下无相关信息");
    }

    /**
     * 分页显示数据
     * @param workCode
     * @return
     */
    @ApiOperation(value = "查询数据库List-分页")
    @GetMapping("/get/{workCode}/info/{pageNumber}/{pageSize}")
    //public PageInfo getPageInfo(@PathVariable String workCode,@PathVariable int pageNumber,@PathVariable int pageSize){
    public ServerResponse<PageInfo> getPageInfo(@PathVariable String workCode,@PathVariable int pageNumber,@PathVariable int pageSize){
        return ServerResponse.successCodeMsgData("分页数据获取成功",dbConsPageService.getPageInfo(workCode,pageNumber,pageSize));
    }

    @ApiOperation(value = "Mybatis-mapper文件中对个参数<if>标签判断")
    @GetMapping("/mybatis/mapper/if/judge")
    public  ServerResponse getResCountMybatisMapperIfJudge(@RequestParam("workCode") String workCode,
                                                           @RequestParam(value = "status", required = false) String status){
        return ServerResponse.successCodeMsgData("<if>标签判断多个参数的查询结果数",myBatisMapperIfJudgeService.findResCountMybatisMapperIfJudge(workCode,status));
    }



}
