package com.viverselftest.api;

import com.viverselftest.service.TransactionTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Congwz on 2018/7/17.
 */
@Api(value = "事务回滚测试接口")
@RestController
@RequestMapping("/transaction-test")
public class TransactionTestApi {

    @Autowired
    private TransactionTestService transactionTestService;

    @ApiOperation(value = "测试回滚")
    @GetMapping("/rollback/{addName}/{delCode}")
    public void testRollBack(@PathVariable(name = "addName") String name,
                             @PathVariable(name = "delCode") String delCode){
        transactionTestService.testRollBack(name,delCode);
    }


}
