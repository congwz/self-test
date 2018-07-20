package com.viverselftest.service;

import com.viverselftest.dao.jde.testMapper;
import com.viverselftest.dto.TestNullOrEmptyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Congwz on 2018/6/14.
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private testMapper testMapper;

    @Value("${test.value}")
    private String properValue;

    @Value("${test.override:plm-ap:7002}")
    private String properValueOverride;



    @Override
    public Map testValueProperties() {
        /*
        map输出：
        {
          "properValueOverride": "172.30.1.32:7002", //property文件中有test.override=172.30.1.32:7002
          "properValue": "172.30.1.32:7001"
        }
        * */

        /*
        "properValueOverride": "plm-ap:7002",  //property文件中注释了test.override=172.30.1.32:7002
        "properValue": "172.30.1.32:7001"
        * */

        Map map = new HashMap();
        map.put("properValue",properValue);
        map.put("properValueOverride",properValueOverride);
        return map;
    }


    /**
     * 测试前端传参时null和""的区别
     * @param add_dto
     * @return
     */
    @Override
    public String testNullOrEmptyParam(List<TestNullOrEmptyDTO> add_dto) {
        int wCount = testMapper.addNullOrEmpty(add_dto);
        if(wCount > 0 ){
            return "add ok.";
        }

        return "add fail!";

    }
}
