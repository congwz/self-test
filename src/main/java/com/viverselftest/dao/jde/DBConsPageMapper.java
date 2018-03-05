package com.viverselftest.dao.jde;

import com.viverselftest.dto.MapOneToMoreAccountDTO;
import com.viverselftest.dto.MapOneToMoreDTO;
import com.viverselftest.po.DBConsPagePO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DBConsPageMapper {

    @Select("select count(1) from PA_VIVER_TEST")
    int findCountInfo();

    @Select("select rownum rn,work_code,user_name,display_code,display_name,display_classify,create_time "
            + "from PA_VIVER_TEST where work_code = #{workCode}")
    List<DBConsPagePO> findInfo(@Param("workCode") String workCode);

    /**
     * 一对多映射-职工与管理的分部
     * @return
     */
    List<MapOneToMoreDTO> mybatisOneToMore();

    /**
     * 一对多映射-对账表头与明细
     * @return
     */
    List<MapOneToMoreAccountDTO> mybatisOneToMoreAccount();
}
