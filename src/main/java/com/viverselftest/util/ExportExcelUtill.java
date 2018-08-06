package com.viverselftest.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

/**
 * @author muhaoxu
 * @date 2017年1月7日 上午11:18:58
 * @version:1.0 类说明
 */
@Component
public class ExportExcelUtill {

    /**
     * 模板路径
     */
    //private static final String TEMPLATE_PATH = "D:/excel";

    //private static final String TEMPLATE_PATH = "/home/harmontronics-erp/exportExcel";

    private static final int num_size = 10000;

    private static final String EXCEL_ROOT_PATH = "/home/harmontronics-erp";

    @Autowired

    public static String exportExcel(List list,Class obj,String title,String sheetName,String fileName,String work_code,String temp_path) throws Exception {

        int num = 1;
        if (list.size() > num_size) {
            num = list.size()/num_size;
            if (list.size() % num_size > 0) {
                num = num + 1;
            }
        }

        Workbook workbook = null;
        Date start = new Date();
        ExportParams params = new ExportParams(title, sheetName);

        List newList = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            int star = i * num_size;
            int end = (i + 1) * num_size;

            if (i + 1 == num) {
                end = list.size();
            }

            newList = list.subList(star,end);
            workbook = ExcelExportUtil.exportBigExcel(params,obj, newList);
        }

        ExcelExportUtil.closeExportBigExcel();
        File savefile = new File(temp_path + "/" + work_code + "/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }

        String filePath = temp_path + "/" + work_code + "/" + fileName;
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();

        //上传阿里云OSS
        File file = new File(filePath);
        String url = UploadUtil.upload(file,work_code);

        return url;
    }


    public static String exportExcelByTemplete(String work_code,List list,String templete_path,String temp_path,String file_name) throws Exception {

        Map map = new HashMap();
        map.put("list", list);

        // 设置导出配置
        // 获取导出excel指定模版
        TemplateExportParams params = new TemplateExportParams(templete_path);
        // 标题开始行
        params.setHeadingStartRow(0);
        // 标题行数
        params.setHeadingRows(1);
        // 设置sheetName，若不设置该参数，则使用得原本得sheet名称
        params.setSheetName("采购单导出");

        Workbook workbook = ExcelExportUtil.exportExcel(params, map);

        File savefile = new File(temp_path + "/" + work_code + "/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }

        String filePath = temp_path + "/" + work_code + "/" +  file_name;
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();

        //上传阿里云OSS
        File file = new File(filePath);
        String url = UploadUtil.upload(file,work_code);

        return url;
    }




    public static String exportAccountExcelByTemplete(String work_code, Map map, String templete_path, String temp_path, String file_name) throws Exception {

        // 获取导出excel的指定模版
        TemplateExportParams params = new TemplateExportParams(templete_path);
        // 设置sheetName，若不设置该参数，则使用得原本的sheet名称
        params.setSheetName("对账单信息");

        Workbook workbook = ExcelExportUtil.exportExcel(params, map);

        File savefile = new File(temp_path + "/" + work_code + "/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }

        String filePath = temp_path + "/" + work_code + "/" +  file_name;
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();

        return filePath;

    }


    public static String exportInquireOnlineExcelByTemplete(String work_code,List list,String templete_path,String temp_path,String file_name) throws Exception {

        Map map = new HashMap();
        map.put("list", list);

        // 设置导出配置
        // 获取导出excel指定模版
        TemplateExportParams params = new TemplateExportParams(templete_path);
        // 标题开始行
        params.setHeadingStartRow(0);
        // 标题行数
        params.setHeadingRows(1);
        // 设置sheetName，若不设置该参数，则使用得原本得sheet名称
        params.setSheetName("报价信息");

        Workbook workbook = ExcelExportUtil.exportExcel(params, map);

        File savefile = new File(temp_path + "/" + work_code + "/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }

        String filePath = temp_path + "/" + work_code + "/" +  file_name;
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();

        //上传阿里云OSS
        File file = new File(filePath);
        String url = UploadUtil.upload(file,work_code);

        return url;
    }


}
