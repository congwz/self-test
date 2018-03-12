package com.viverselftest.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Map;

@Component
public class ExcelUtill {

    private static final String TEMPLATE_PATH = "template/";


    /**
     * 生成excel对象
     * @param params 模板导出参数设置
     * @param mapData 模板导出数据
     * @param templateName 模板文件名
     * @return WorkBook对象
     */
    public static Workbook getWorkBook(TemplateExportParams params, Map<String, Object> mapData, String templateName) throws IOException {
        String templatePath = TEMPLATE_PATH + templateName;
        File file = getTempTemplateFile(templatePath);
        params.setTemplateUrl(file.getAbsolutePath());
        System.out.println(file.getAbsolutePath());
        Workbook workbook = ExcelExportUtil.exportExcel(params,mapData);
        //手动删除模板的临时文件（副本）
        if(file.exists()){
            file.delete();
        }

        return workbook;

    }


    /**
     * 获取模板的副本（副本为临时文件），用完后需要手动删除
     * <p>由于springboot打包成jar之后，不能以绝对路径的形式读取模板文件，故此处将模板文件以临时文件的形式写到磁盘中，用完请手动删除</p>
     * @param templatePath
     * @return
     */
    public static File getTempTemplateFile(String templatePath) throws IOException {
        File tempFile = File.createTempFile("temp",null);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(templatePath);
        if(resources.length == 1){
            InputStream ins = resources[0].getInputStream();
            //InputStream转换为File
            try {
                OutputStream ous = new FileOutputStream(tempFile);
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ( (bytesRead = ins.read(buffer,0,8192)) != -1 ){
                    ous.write(buffer,0,bytesRead);
                }
                ous.close();
                ins.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("模板文件不存在！！！");
        }

        return tempFile;
    }

}
