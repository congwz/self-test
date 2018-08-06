package com.viverselftest.service.impl;

import com.viverselftest.dao.jde.EasyPOIMapper;
import com.viverselftest.dto.inquireonline.InquireOnlineConditionsDTO;
import com.viverselftest.dto.inquireonline.InquireOnlineConditionsHelpDTO;
import com.viverselftest.po.ExcelExportOneToMorePO;
import com.viverselftest.po.ExcelExportSimplePO;
import com.viverselftest.service.EasyPOIService;
import com.viverselftest.util.ExportExcelUtill;
import com.viverselftest.util.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EasyPOIServiceImpl implements EasyPOIService {

    @Autowired
    private EasyPOIMapper easyPOIMapper;

    @Value("${excel.inquire.head.template}")
    private String inquire_head_path;

    @Value("${excel.inquire.detail.template}")
    private String inquire_detail_path;

    @Value("${excel.temp.path}")
    private String excel_temp_path;



    /**
     * 最简单的excel导出
     * @param workCode
     * @return
     */
    @Override
    public List<ExcelExportSimplePO> findSimpleExcelExportData(String workCode) {
        return easyPOIMapper.findSimpleExcelExportData(workCode);
    }

    /**
     * 一对多数据的excel导出
     * @return
     */
    @Override
    public List<ExcelExportOneToMorePO> findOneToMoreExcelExportData() {
        return easyPOIMapper.findOneToMoreExcelExportData();
    }


    @Override
    @Async
    public void export(InquireOnlineConditionsDTO search_dto, String work_code, String user_name) {
        InquireOnlineConditionsHelpDTO helpSearchDTO = new InquireOnlineConditionsHelpDTO();

        helpSearchDTO.setBranch_code(search_dto.getBranch_code());
        String material_type = search_dto.getMateriel_type().replaceAll("(STANDARD)","S").replaceAll("(CUSTOM)","C");
        helpSearchDTO.setMateriel_type(material_type);
        helpSearchDTO.setUrgency(search_dto.getUrgency());
        helpSearchDTO.setHead_conditions(QueryUtils.getQueryCondition(search_dto.getHead_conditions()));
        helpSearchDTO.setDetail_conditions(QueryUtils.getQueryCondition(search_dto.getDetail_conditions()));
        helpSearchDTO.setSort_colum(search_dto.getSort_colum());
        helpSearchDTO.setSort_rule(search_dto.getSort_rule());
        String hOrD = search_dto.getHd_type();

        String task_id = UUID.randomUUID().toString();
        try {
            String star_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String file_name = work_code + "_" + System.currentTimeMillis() + ".xlsx";
            String file_type = "EXCEL";
            String report_title = "";

            if("H".equals(hOrD)){
                report_title = "报价表头导出";
            }else if("D".equals(hOrD)){
                report_title = "报价明细导出";
            }

            easyPOIMapper.insertExportTask(task_id,work_code,user_name,report_title,file_name,file_type,"",star_time,"","0","");

            List list = new ArrayList();
            String template_path = "";
            if("H".equals(hOrD)){
                template_path = inquire_head_path;
                //查询报价单表头
                list = easyPOIMapper.findQuotedHead(helpSearchDTO);
            }else if("D".equals(hOrD)){
                template_path = inquire_detail_path;
                //查询报价单明细
                list = easyPOIMapper.findQuotedDetail(helpSearchDTO);
            }

            String ali_url = ExportExcelUtill.exportInquireOnlineExcelByTemplete(work_code,list,template_path,excel_temp_path,file_name);
            //导出任务完成
            easyPOIMapper.updateExportTask(task_id,ali_url,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),"1");
        } catch (Exception e) {
            //导出任务失败
            easyPOIMapper.updateExportTask(task_id,"",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),"-1");
        }


    }
}
