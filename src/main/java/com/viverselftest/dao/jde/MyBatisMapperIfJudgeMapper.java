package com.viverselftest.dao.jde;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MyBatisMapperIfJudgeMapper {

    int findResCountMybatisMapperIfJudge(Map map);

    int findResCountMybatisMapperIfJudge2(Map map);

    int findResCountMybatisMapperIfJudge3(@Param("workCode") String workCode, @Param("status") String status);

}
