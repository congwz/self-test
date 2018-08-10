package com.viverselftest.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.viverselftest.dao.jde.EasyPOIMapper;
import com.viverselftest.dto.PageDTO;
import com.viverselftest.dto.inquireonline.InquireOnlineConditionsDTO;
import com.viverselftest.dto.inquireonline.InquireOnlineConditionsHelpDTO;
import com.viverselftest.po.ExcelExportOneToMorePO;
import com.viverselftest.po.ExcelExportSimplePO;
import com.viverselftest.po.InquireOnlineExcelPO;
import com.viverselftest.po.InquireOnlineHeadPO;
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


    /**
     * 获取报价单表头
     * @param search_dto
     * @return
     */
    @Override
    public PageDTO getQuotedInfoByHD(InquireOnlineConditionsDTO search_dto) {
        InquireOnlineConditionsHelpDTO helpSearchDTO = new InquireOnlineConditionsHelpDTO();

        PageDTO pageDTO = new PageDTO();
        int pageNumber = search_dto.getPageNumber();
        int pageSize = search_dto.getPageSize();
        //PageHelper.startPage(pageNumber, pageSize);

        List<InquireOnlineHeadPO> listh = null;
        List<InquireOnlineExcelPO> listres = null;
        List<InquireOnlineExcelPO> list = new ArrayList<>();

        helpSearchDTO.setBranch_code(search_dto.getBranch_code());
        String material_type = search_dto.getMateriel_type().replaceAll("(STANDARD)","S").replaceAll("(CUSTOM)","C");
        helpSearchDTO.setMateriel_type(material_type);
        helpSearchDTO.setUrgency(search_dto.getUrgency());
        helpSearchDTO.setHead_conditions(QueryUtils.getQueryCondition(search_dto.getHead_conditions()));
        helpSearchDTO.setDetail_conditions(QueryUtils.getQueryCondition(search_dto.getDetail_conditions()));
        helpSearchDTO.setSort_colum(search_dto.getSort_colum());
        helpSearchDTO.setSort_rule(search_dto.getSort_rule());

        if("H".equals(search_dto.getHd_type())){
            //查询报价单表头
            /* 这样的写法不能保证排序后仍然保持原序且查询结果存在字段为null需要特殊处理
            listh = easyPOIMapper.findQuotedHead(helpSearchDTO);
            System.out.println("listh: " + listh.toString());
            listres = easyPOIMapper.findQuotedHeadRes(listh);
            System.out.println("listres: " + listres.toString());
            */

            listres = easyPOIMapper.exportOneToMoreExcel(helpSearchDTO);
            System.out.println("listres: " + listres);
            List<String> listPnumberPage = easyPOIMapper.findQuotedHeadPage(listres,pageNumber,pageSize);
            for(int i = 0; i<listres.size(); i++){
                boolean quick = true;
                for(int j = 0; j<listPnumberPage.size(); j++){
                    quick = false;
                    if(listPnumberPage.get(i).equals(listres.get(j).getQPNumber())){
                        list.add(listres.get(i));
                    }
                }
                if(quick){
                    break;
                }
            }
            System.out.println("list: " + list.toString());
            //list = easyPOIMapper.findQuotedHeadPage(listh,pageNumber,pageSize);
        }/*else if("D".equals(search_dto.getHd_type())){
            //查询报价单明细
            list = inquireOnlineMapper.findQuotedDetail(helpSearchDTO);
        }*/

        pageDTO.setPageNumber(pageNumber);
        pageDTO.setPageSize(pageSize);
        pageDTO.setTotal(listres.size());
        pageDTO.setData(list);

        return pageDTO;
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


    /**
     * 导出一对多数据的excel
     * @return
     */
    @Override
    public List<InquireOnlineExcelPO> exportOneToMoreExcel(InquireOnlineConditionsDTO search_dto) {
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

        List list = new ArrayList();
        if("H".equals(hOrD)){
            //查询报价单表头
            list = easyPOIMapper.exportOneToMoreExcel(helpSearchDTO);
        }else if("D".equals(hOrD)){
            //查询报价单明细
            //list = easyPOIMapper.findQuotedDetail(helpSearchDTO);
        }

        return list;
    }
}
