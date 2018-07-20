package com.viverselftest.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.viverselftest.dao.jde.InquireOnlineMapper;
import com.viverselftest.dto.ApiResDTO;
import com.viverselftest.dto.inquireonline.InquireOnlineHdDTO;
import com.viverselftest.dto.inquireonline.hcSupplierQuotedGenDTO;
import com.viverselftest.service.TransactionTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Congwz on 2018/7/17.
 */
@Api(value = "事务回滚测试接口")
@RestController
@RequestMapping("/transaction-test")
public class TransactionTestApi {

    @Autowired
    private TransactionTestService transactionTestService;

    @Autowired
    private InquireOnlineMapper inquireOnlineMapper;



    @ApiOperation(value = "测试回滚")
    @GetMapping("/rollback/{addName}/{delCode}")
    public void testRollBack(@PathVariable(name = "addName") String name,
                             @PathVariable(name = "delCode") String delCode){
        transactionTestService.testRollBack(name,delCode);
    }

    @ApiOperation("获取一对多数据")
    @PostMapping("/test")
    public int testOneToMore(@RequestBody List<InquireOnlineHdDTO> quotation_orders) {
        List<hcSupplierQuotedGenDTO> hcSupplierDTOList = inquireOnlineMapper.findHcSupplierQuotedInfo(quotation_orders);
        System.out.println(hcSupplierDTOList.toString());


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("token","test_token");
        HttpEntity<List<hcSupplierQuotedGenDTO>> request = new HttpEntity<>(hcSupplierDTOList, headers);
        RestTemplate restTemplate = new RestTemplate();

        String hcsupplier_generate_path = "http://172.28.10.247:8006/api/purchase/addQuotedPrice";
        Date start = new Date();
        ApiResDTO resDTO = restTemplate.postForObject(hcsupplier_generate_path, request, ApiResDTO.class);
        if("0".equals(resDTO.getSuccess())){
            System.out.println(new Date().getTime() - start.getTime());
            System.out.println(resDTO.getErrorMessage());
        }else if("1".equals(resDTO.getSuccess())){
            System.out.println(new Date().getTime() - start.getTime());
            System.out.println("对接成功！");
        }


        /*ResponseEntity<String> responseEntity= restTemplate.postForEntity(hcsupplier_generate_path, request, String.class);
        int statusCodeValue = responseEntity.getStatusCodeValue();
        if(statusCodeValue != 200){
            System.out.println(responseEntity.getBody());
            //回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            //throw new ErrorException("200002", errorMsgUtils.errorMsg("", "200002"));
        }*/
        return 1;
    }


}
