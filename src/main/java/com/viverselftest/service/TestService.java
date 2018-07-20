package com.viverselftest.service;

import com.viverselftest.dto.TestNullOrEmptyDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by Congwz on 2018/6/14.
 */
public interface TestService {

    Map testValueProperties();

    /**
     * 测试前端传参时null和""的区别
     * @param add_dto
     * @return
     */
    String testNullOrEmptyParam(List<TestNullOrEmptyDTO> add_dto);
}
