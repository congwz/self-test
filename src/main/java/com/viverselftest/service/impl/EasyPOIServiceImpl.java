package com.viverselftest.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.viverselftest.dao.jde.EasyPOIMapper;
import com.viverselftest.dto.PageDTO;
import com.viverselftest.dto.inquireonline.InquireOnlineConditionsDTO;
import com.viverselftest.dto.inquireonline.InquireOnlineConditionsHelpDTO;
import com.viverselftest.po.*;
import com.viverselftest.service.EasyPOIService;
import com.viverselftest.util.ExportExcelUtill;
import com.viverselftest.util.QueryUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
            /*String star_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String file_name = work_code + "_" + System.currentTimeMillis() + ".xlsx";
            String file_type = "EXCEL";
            String report_title = "";

            if("H".equals(hOrD)){
                report_title = "报价表头导出";
            }else if("D".equals(hOrD)){
                report_title = "报价明细导出";
            }

            easyPOIMapper.insertExportTask(task_id,work_code,user_name,report_title,file_name,file_type,"",star_time,"","0","");*/

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
                //输出在 123.txt文件中
                System.out.println("Quoted price detail list to map: " + groupDetailData(list));
            }

            //String ali_url = ExportExcelUtill.exportInquireOnlineExcelByTemplete(work_code,list,template_path,excel_temp_path,file_name);
            //导出任务完成
            //easyPOIMapper.updateExportTask(task_id,ali_url,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),"1");
        } catch (Exception e) {
            //导出任务失败
            //easyPOIMapper.updateExportTask(task_id,"",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),"-1");
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








    /**
     * 行转列导出excel明细与供应商
     * @param search_dto
     */
    @Override
    public void exportDetailAndProvider(InquireOnlineConditionsDTO search_dto) {

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

        List<InquireOnlineDetailExcelPO> list = null;
        List<InquireOnlineDetailExcelHelpPO> NPDList = null;
        if("D".equals(hOrD)){
            //查询报价单明细与供应商（一对多）
            list = easyPOIMapper.findQuotedDetailExcel(helpSearchDTO);
            //查询报价单号供应商与明细（一对多）
            NPDList = easyPOIMapper.findQuotedDetailHelp(helpSearchDTO);

        }
        //System.out.println("detail: " + detail.toString());
        System.out.println("list_QPNumberDetail_Providers: " + list.toString());
        System.out.println("list_QPNumberProvider_Details: " + NPDList.toString());


        List<ExcelExportEntity> colList = new ArrayList<ExcelExportEntity>();

        ExcelExportEntity colEntity = new ExcelExportEntity("报价单号", "bjdh");
        colEntity.setNeedMerge(true);
        colList.add(colEntity);

        colEntity = new ExcelExportEntity("行号", "hh");
        //colEntity.setNeedMerge(true);
        colList.add(colEntity);

        colEntity = new ExcelExportEntity("物料号", "wlh");
        //colEntity.setNeedMerge(true);
        colList.add(colEntity);

        colEntity = new ExcelExportEntity("物料名称", "wlmc");
        //colEntity.setNeedMerge(true);
        colList.add(colEntity);


        for (int i = 0; i < NPDList.size(); i++) {
            InquireOnlineDetailExcelHelpPO item = NPDList.get(i);
            String QPNumber = item.getQPNumber();
            ExcelExportEntity jColGroup = new ExcelExportEntity(item.getSupplier(), item.getQPSLSupplierCode() + QPNumber);
            List<ExcelExportEntity> jColList = new ArrayList<ExcelExportEntity>();
            jColList.add(new ExcelExportEntity("承诺交期", "cnjq"));
            jColList.add(new ExcelExportEntity("未税单价", "wsdj"));
            jColList.add(new ExcelExportEntity("报价状态", "bjzt"));
            jColGroup.setList(jColList);
            colList.add(jColGroup);
        }

        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        for(int x = 0; x<list.size(); x++){
            Map<String, Object> valMap = new HashMap<String, Object>();
            InquireOnlineDetailExcelPO item = list.get(x);
            String QPNumber = item.getQPNumber();
            String QPDFindNum = item.getQPDFindNum();
            valMap.put("bjdh", QPNumber);
            valMap.put("hh", QPDFindNum);
            valMap.put("wlh", item.getQPDItemNo());
            valMap.put("wlmc", item.getQPDItemName());

            for(int i = 0; i<NPDList.size(); i++){
                InquireOnlineDetailExcelHelpPO dItem = NPDList.get(i);
                if(QPNumber.equals(dItem.getQPNumber())){
                    String QPSLSupplierCode = dItem.getQPSLSupplierCode();
                    List<InquireOnlineDetailHelpPO> details = dItem.getDetails();
                    for (int j = 0; j < details.size(); j++) {
                        InquireOnlineDetailHelpPO iItem = details.get(j);
                        if(QPDFindNum.equals(iItem.getQPDFindNum())){
                            List<Map<String, Object>> pList = new ArrayList<Map<String, Object>>();
                            Map<String, Object> pMap = new HashMap<String, Object>();
                            pMap.put("cnjq", iItem.getQPDSLAcceptDate());
                            pMap.put("wsdj", iItem.getQPDSLUnTaxMoney());
                            pMap.put("bjzt", iItem.getQPDSLStatus());
                            pList.add(pMap);
                            valMap.put(QPSLSupplierCode + QPNumber, pList);
                            break;
                        }
                    }

                }

            }
            listMap.add(valMap);

        }

        System.out.println("before order listMap: " + listMap.toString());

        //按行号排序
        Collections.sort(listMap,new Comparator<Map<String,Object>>() {
            public int compare(Map<String,Object> o1, Map<String,Object> o2) {
                //Collator instance = Collator.getInstance(Locale.CHINA);
                //return instance.compare(o1.get("name").toString(), o2.get("name").toString());
                return ((String)o1.get("bjdh")).compareTo((String)o2.get("bjdh"));
            }
        });
        /*Collections.sort(listMap,(o1,o2) -> {
            String bjdh = (String) o1.get("bjdh");
            String hh = (String) o2.get("hh");
            return bjdh.compareTo(hh);
            }
        );*/

        System.out.println("after order listMap: " + listMap.toString());

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("报价明细与供应商表", "data"), colList, listMap);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("C:/excel/exportDetailProvider.xls");
            workbook.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("export detail and provider data success!");

    }





    /**
     * 分组处理 明细的list
     * @param details
     * @return
     */
    private Map<String,Map<String, Map<String, List<InquireOnlineDetailPO>>>> groupDetailData(List<InquireOnlineDetailPO> details) {

        //先根据报价单号分组，在根据报价单行号分组，在根据供应商编号分组,并且按单号排序
        Map<String, Map<String, Map<String, List<InquireOnlineDetailPO>>>> map = new TreeMap<>((k1,k2) -> k1.compareTo(k2));

        map.putAll(details.stream()
                .collect(
                        Collectors.groupingBy(
                                InquireOnlineDetailPO::getQPNumber,                                //报价单号
                                Collectors.groupingBy(
                                        InquireOnlineDetailPO::getQPDFindNum,                      //报价单行号
                                        Collectors.groupingBy(
                                                InquireOnlineDetailPO::getSupplier_code_name        //供应商编号和名称
                                        )
                                )
                        )
                )
        );

        return map;
    }




}
