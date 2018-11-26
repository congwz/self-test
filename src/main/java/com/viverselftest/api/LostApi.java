package com.viverselftest.api;

import com.viverselftest.dto.PageDTO;
import com.viverselftest.po.lost.UserInfoPO;
import com.viverselftest.service.LostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Congwz on 2018/11/12.
 */
@Api(tags = "失物招领接口")
@RestController
@RequestMapping("/api/lost")
public class LostApi {

    @Autowired
    private LostService lostService;


    /**
     * 查询寻物分页信息
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "获取寻物分页列表")
    @GetMapping("/page-list")
    public PageDTO getLostList(@RequestParam(name = "pageNumber") int pageNumber,
                               @RequestParam(name = "pageSize") int pageSize,
                               HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Headers","x-requested-with,content-type");
        return lostService.getLostList(pageNumber,pageSize);
    }

    /**
     * 注册
     * @param user
     */
    @ApiOperation(value = "注册")
    @PostMapping("/register")
    public void addUserRegisterInfo(@RequestBody UserInfoPO user){
        lostService.addUserRegisterInfo(user);
    }

    /**
     * 验证用户名是否已存在
     * @param account
     * @return
     */
    @ApiOperation(value = "验证用户名")
    @GetMapping("/account-verify")
    public boolean verfityAccount(@RequestParam(name = "account") String account){
        return lostService.verfityAccount(account);
    }

}
