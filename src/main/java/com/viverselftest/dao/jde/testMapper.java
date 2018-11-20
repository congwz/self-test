package com.viverselftest.dao.jde;

import com.viverselftest.dto.TestNullOrEmptyDTO;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Congwz on 2018/7/20.
 */
@Repository
public interface testMapper {

    @Insert("insert into pa_test_2(DATETIME) values (#{d})")
    void addDateToTimeStemp(Timestamp d);

    /**
     * 测试前端传参时null和""的区别
     * @param add_dto
     * @return
     */
    int addNullOrEmpty(List<TestNullOrEmptyDTO> add_dto);
}
