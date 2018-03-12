package com.viverselftest.dao.jde;

import com.viverselftest.po.ExcelExportOneToMorePO;
import com.viverselftest.po.ExcelExportSimplePO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EasyPOIMapper {

    /**
     * 最简单的excel导出
     * @param workCode
     * @return
     */
    @Select("select rownum rn,work_code,user_name,display_code,display_name,display_classify,create_time "
            + "from PA_VIVER_TEST where work_code = #{workCode} ")
    List<ExcelExportSimplePO> findSimpleExcelExportData(@Param("workCode") String workCode);


    /**
     * 一对多数据的excel导出
     * @return
     */
    /*@Select("select pvu.userno id,username,pvd.partname name,"
            + "rownum rn,work_code,user_name,display_code,display_name,display_classify,create_time "
            + "from PA_VIVER_TEST_USER pvu "
            + "join PA_VIVER_TEST_DEPARTMENT pvd on pvu.userno = pvd.userno "
            + "join PA_VIVER_TEST pvt on pvu.userno = pvt.work_code")*/
    List<ExcelExportOneToMorePO> findOneToMoreExcelExportData();
}
