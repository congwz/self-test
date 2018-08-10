package com.viverselftest.dao.jde;

import com.viverselftest.dto.inquireonline.InquireOnlineConditionsDTO;
import com.viverselftest.dto.inquireonline.InquireOnlineConditionsHelpDTO;
import com.viverselftest.po.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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




    /**
     * 查询报价单表头
     * @param helpSearchDTO
     * @return
     */
    List<InquireOnlineHeadPO> findQuotedHead(InquireOnlineConditionsHelpDTO helpSearchDTO);

    /**
     * 对查询的报价表头结果格式化
     * @param listh
     * @return
     */
    List<InquireOnlineExcelPO> findQuotedHeadRes(List<InquireOnlineHeadPO> listh);

    /**
     * 查询格式化的报价表头结果分页的报价单号
     * @param listRes
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<String> findQuotedHeadPage(@Param("listRes") List<InquireOnlineExcelPO> listRes,
                                    @Param("pageNumber") int pageNumber,
                                    @Param("pageSize") int pageSize);

    /**
     * 导出一对多数据的excel
     * @return
     */
    List<InquireOnlineExcelPO> exportOneToMoreExcel(InquireOnlineConditionsHelpDTO helpSearchDTO);

    /**
     * 查询报价单明细
     * @param helpSearchDTO
     * @return
     */
    List<InquireOnlineDetailPO> findQuotedDetail(InquireOnlineConditionsHelpDTO helpSearchDTO);



    @Insert("insert into pa_export_task (id, work_code, user_name, report_title, file_name, file_type,file_url, start_time,end_time, status,down_litm) "
            + "values (#{id}, #{work_code}, #{user_name}, #{report_title}, #{file_name}, #{file_type}, #{file_url}, #{start_time}, #{end_time}, #{status},#{down_litm})")
    void insertExportTask( @Param("id") String id,
                           @Param("work_code") String work_code,
                           @Param("user_name") String user_name,
                           @Param("report_title") String report_title,
                           @Param("file_name") String file_name,
                           @Param("file_type") String file_type,
                           @Param("file_url") String file_url,
                           @Param("start_time") String start_time,
                           @Param("end_time") String end_time,
                           @Param("status") String status,
                           @Param("down_litm") String down_litm);

    @Update("update pa_export_task set file_url = #{file_url},end_time = #{end_time}, status = #{status} where id = #{id}")
    void updateExportTask( @Param("id") String id,
                           @Param("file_url") String file_url,
                           @Param("end_time") String end_time,
                           @Param("status") String status);


    @Select("select * from pa_export_task where work_code = #{work_code} ${query_condit} order by start_time desc")
    List<ExportTaskPO> getExportTasks(@Param("work_code") String work_code, @Param("query_condit") String query_condit);
}
