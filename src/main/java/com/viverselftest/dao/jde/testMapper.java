package com.viverselftest.dao.jde;

import com.viverselftest.dto.TestNullOrEmptyDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Congwz on 2018/7/20.
 */
@Repository
public interface testMapper {

    /**
     * 测试前端传参时null和""的区别
     * @param add_dto
     * @return
     */
    int addNullOrEmpty(List<TestNullOrEmptyDTO> add_dto);
}
