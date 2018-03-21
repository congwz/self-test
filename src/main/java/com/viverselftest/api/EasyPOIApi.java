package com.viverselftest.api;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.util.PoiPublicUtil;
import cn.afterturn.easypoi.word.WordExportUtil;
import com.viverselftest.po.ExcelExportBigDataPO;
import com.viverselftest.po.ExcelExportOneToMorePO;
import com.viverselftest.po.ExcelExportPersonPO;
import com.viverselftest.po.ExcelExportSimplePO;
import com.viverselftest.po.word.WordExportTemplateHardPO;
import com.viverselftest.service.EasyPOIService;
import com.viverselftest.util.ExcelUtill;
import com.viverselftest.util.WordUtill;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(description = "easy poi")
@RestController
@RequestMapping("/api/easy-poi")
public class EasyPOIApi {

    @Autowired
    private EasyPOIService easyPOIService;

    @Autowired
    private ExcelUtill excelUtill;


    /*============================================导出word-word模板========================================*/

    @ApiOperation("word模板导出word文件")
    @GetMapping("/word/template/export")
    public void testWordTemplateExport(HttpServletRequest request, HttpServletResponse response){
        Map map = new HashMap();
        map.put("title","word模板简单测试");
        map.put("person","个人介绍");
        map.put("other","职业相关");

        map.put("name","张聪伟");
        map.put("sex","女");
        map.put("age",25);
        map.put("job","IT民工");
        map.put("hobby","打羽毛球ha");

        try {
            // 获取模板文件
            XWPFDocument doc = WordExportUtil.exportWord07("/template/templateWord.docx",map);
            //ByteArrayOutputStream bous = new ByteArrayOutputStream();
            //doc.write(bous);

            /*写入文件*/
            File file = new File("C:/word");
            if(!file.exists()){
                file.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream("C:/word/templateSimpleWordExport.docx");
            doc.write(fos);
            fos.close();
            System.out.println("模板导出Word 成功!!! -》templateSimpleWordExport.docx");

        } catch (Exception e) {
            e.printStackTrace();
        }

        //File docx = new File("C:/word/templateSimpleWordExport.docx");
        //File pdf = new File("C:/pdf/wordToPDF.pdf");
        /*word转pdf*/
        WordUtill.wordConvertToPdf("C:/word/templateSimpleWordExport.docx","C:/pdf/wordToPDF.pdf");

    }


    /**
     *
     * 失败  封装在list里面失败
     * @param request
     * @param response
     */
    @ApiOperation("word模板导出word文件-表格")
    @GetMapping("/word/template/export/complicate")
    public void testWordTemplateExportHardFail(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException {
        Map<String,Object> map = new HashMap<>();

        List<WordExportTemplateHardPO> list = new ArrayList<>();
        /*List<Map<String,Object>> mapList = new ArrayList<>();*/
        for(int i = 0; i < 3; i++){
            /*Map itemMap = new HashMap();
            itemMap.put("name","Tony" + i);
            itemMap.put("age",22 + i);
            itemMap.put("sex","男i");
            mapList.add(itemMap);*/
            WordExportTemplateHardPO item = new WordExportTemplateHardPO();
            item.setName("Viver" + i);
            item.setAge(18 + i);
            item.setSex("女");
            list.add(item);
        }

        map.put("title","word模板表格");
        map.put("tableTitle","table数据如下");
        map.put("personList",list);
        /*map.put("personList",mapList);*/

        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setDirectoryForTemplateLoading(new File("C:/"));
        File outFile = new File("C:/word/test.doc");
        //获取模板文件
        Template t =  configuration.getTemplate("templateTable.ftl","utf-8");
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"),10240);
        //将填充数据填入模板文件并输出到目标文件
        t.process(map, out);
        out.close();
        System.out.println("表格模板导出Word 成功!!! -》test.doc");

        /*try {
            // 获取模板文件
            XWPFDocument doc = WordExportUtil.exportWord07("/template/templateWordTable.docx",map);

            //写入文件
            File file = new File("C:/word");
            if(!file.exists()){
                file.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream("C:/word/templateWordTableExport.docx");
            doc.write(fos);
            fos.close();
            System.out.println("表格模板导出Word 成功!!! -》templateWordTableExport.docx");

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }






    /*============================================导出Excel-基础========================================*/


    @ApiOperation("简单数据excel导出")
    @GetMapping("/excel/simple/export")
    public void testSimpleExcelExport(HttpServletResponse response, @RequestParam("workCode") String workCode) throws IOException {

        List<ExcelExportSimplePO> list = easyPOIService.findSimpleExcelExportData(workCode);

        ExportParams exportParams = new ExportParams("工号-分部管理","ExcelExport1");
        //导出 工号对应管理的分部信息
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, ExcelExportSimplePO.class,list);
        /*excelResponse(workbook,response);*/

        FileOutputStream fos = new FileOutputStream("C:/excel/simple.xls");
        workbook.write(fos);
        fos.close();
        System.out.println(workCode + "导出简单数据成功啦！");
    }


    @ApiOperation("一对多数据")
    @GetMapping("/excel/oneToMore/list")
    public List<ExcelExportOneToMorePO> testOneToMoreList(HttpServletResponse response) throws IOException {

        List<ExcelExportOneToMorePO> list = easyPOIService.findOneToMoreExcelExportData();
         return list;
    }

    /**
     * 一对多导出：
     * 一个员工对应一个部门
     * 一个员工对应多个类型管理
     * @param response
     * @throws IOException
     */
    @ApiOperation("一对多数据excel导出")
    @PostMapping("/excel/oneToMore/export")
    public void testOneToMoreExcelExport(HttpServletResponse response) throws IOException {

        List<ExcelExportOneToMorePO> list = easyPOIService.findOneToMoreExcelExportData();

        ExportParams exportParams = new ExportParams("用户名 部门 管理类型(大标题)","测试一对多(小标题)","ExcelExport2");
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, ExcelExportOneToMorePO.class,list);
        /*excelResponse(workbook,response);*/

        FileOutputStream fos = new FileOutputStream("C:/excel/oneToMore.xls");
        workbook.write(fos);
        fos.close();
        System.out.println("导出一对多数据成功啦！！");
    }


    /**
     * excel返回
     * @param workbook
     * @param response
     * @throws IOException
     */
    public void excelResponse(Workbook workbook,HttpServletResponse response) throws IOException{
        //String name = "attachment;filename=" + URLEncoder.encode(filename, "UTF-8") + ".xls";
        String name = "attachment;filename=simpleExcelExport.xls";
        response.setHeader("Content-disposition", name);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        //response.setContentType("application/vnd.ms-excel");

        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();

    }




    /*============================================模板导出Excel============================================*/

    @ApiOperation("模板导出Excel")
    @GetMapping("/excel/template/export")
    public void testTemplateExcelExport(HttpServletResponse response) throws IOException {

        /************************************************************************************************************/
        /*List数据导出*/

        List<ExcelExportPersonPO> list = new ArrayList<>();

        ExcelExportPersonPO person1 = new ExcelExportPersonPO();
        ExcelExportPersonPO person2 = new ExcelExportPersonPO();

        person1.setId(1);
        person1.setName("Viver");
        person1.setSex("女");
        person1.setAge(24);
        person1.setJobDate("2017-09-04");

        person2.setId(2);
        person2.setName("池昌旭");
        person2.setSex("男");
        person2.setAge(28);
        person2.setJobDate("2016-12-12");

        list.add(person1);
        list.add(person2);

        Map<String,Object> mapData = new HashMap<>();
        /*List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> mapItem = new HashMap<String, Object>();
            mapItem.put("id", i + 1);
            mapItem.put("name", i == 0 ? "Viver" : "池昌旭");
            mapItem.put("sex", i== 0 ? "女" : "男");
            mapItem.put("age",24);
            mapItem.put("jobDate",i == 0 ? "2017-09-04 09:30:01" : "2017-12-12");
            maplist.add(mapItem);
        }
        mapData.put("personList",maplist);  //封装成map,作为模板导出了哟*/

        mapData.put("personList",list);  //直接是list,不需要封装为map,作为模板导出成功了哟

        //TemplateExportParams params = new TemplateExportParams("C:/excel/viverTemplate.xlsx");
        //标题开始行
        //params.setHeadingStartRow(0);
        //标题行数
        //params.setHeadingRows(1);
        //设置sheetName，若不设置该参数，则使用原本的sheet名称
        //params.setSheetName("person");
        //Workbook workbook = ExcelExportUtil.exportExcel(params,mapData);

        TemplateExportParams params = new TemplateExportParams();
        Workbook workbook = excelUtill.getWorkBook(params, mapData, "viverIdeaTemplate.xlsx"); //方法二：借助工具类ExcelUtill.java获取Workbook
        if(workbook == null){
            System.out.println("workbook为空");
            return;
        }

        /*下载Excel*/
        //设置excel的文件名称
        String excelName = "下载excel";
        //重置响应对象
        response.reset();
        //当前日期，用于导出文件名称
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = "["+excelName+"-"+sdf.format(new Date())+"]";

        //指定下载的文件名--设置响应头
        response.setHeader("Content-Disposition", "attachment;filename=" +dateStr+".xls");
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        //写出数据输出流到页面
        try {
            OutputStream outputStream = response.getOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            workbook.write(bufferedOutputStream);
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*TemplateExportParams params = new TemplateExportParams();
        params.setSheetName("template-test");
        params.setTempParams("t");
        //Workbook workbook = ExcelUtill.getWorkBook(params,mapData,"viverTemplate.xls");
        Workbook workbook = excelUtill.getWorkBook(params,mapData,"viverTemplate.xlsx");*/

        /*写入文件*/
        File file = new File("C:/excel");
        if(!file.exists()){
            file.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("C:/excel/templateExport.xlsx");
        workbook.write(fos);
        fos.close();
        System.out.println("模板导出Excel Success!!! -》templateExport.xlsx");


        /************************************************************************************************************/
        /*单条数据导出*/
        TemplateExportParams params2 = new TemplateExportParams("C:/excel/viverTemplate2.xls");
        params2.setSheetName("person2-single");
        Map<String,Object> map = new HashMap();
        map.put("id",1);
        map.put("name","张聪伟");
        map.put("sex","女");
        map.put("age",18);
        map.put("jobDate","2017-09-04");
        Workbook wb = ExcelExportUtil.exportExcel(params2,map);

        FileOutputStream fos2 = new FileOutputStream("C:/excel/templateExport2.xls");
        wb.write(fos2);
        fos2.close();
        System.out.println("模板2导出Excel Success!!! -》templateExport2.xls");

    }


    /*============================================大数据导出Excel==========================================*/
    @ApiOperation("大数据导出Excel")
    @GetMapping("/excel/bigData/export")
    public void testBigDataExcelExport() throws IOException {
        List<ExcelExportBigDataPO> list = new ArrayList<>();
        Workbook workbook = null;
        Date startDate = new Date();
        ExportParams exportParams = new ExportParams("大数据导出Excel测试","BigData");
        for(int i = 0; i < 1000000; i++){ // 一百万数据量
            ExcelExportBigDataPO item = new ExcelExportBigDataPO();
            item.setId(i + "1");
            item.setWork_code("0"+i);
            item.setUser_name(i+"TestName"+i);
            item.setBirthday(new Date());
            item.setPhone("1525130526"+i);
            item.setUserRemark(i+"大数据测试备注属性");
            list.add(item);

            if(list.size() == 10000){
                workbook = ExcelExportUtil.exportBigExcel(exportParams,ExcelExportBigDataPO.class,list);
                list.clear();
            }
        }//for

        ExcelExportUtil.closeExportBigExcel();
        System.out.println(new Date().getTime() - startDate.getTime());
        File saveFile = new File("C:/excel");
        if(!saveFile.exists()){
            saveFile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("C:/excel/BigDataExcel.xlsx");
        workbook.write(fos);
        fos.close();
    }








    /*============================================导入Excel==========================================*/
    @ApiOperation("导入Excel")
    @PostMapping("/excel/import")
    public String testExcelImport(MultipartFile uploadExcelFile, HttpServletRequest request, HttpServletResponse response) throws IOException {

        //把spring文件上传的MultipartFile转换成CommonsMultipartFile类型
        //CommonsMultipartFile cf = (CommonsMultipartFile)uploadExcelFile; //获取本地存储路径
        //File file = new  File("C:\\excel");
        //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
        //if (!file.exists()) file.mkdirs();
        //新建一个文件
        //FileOutputStream fos = new FileOutputStream("C:\\excel" + new Date().getTime() + ".xlsx");
        InputStream ins = uploadExcelFile.getInputStream();
        //InputStream转换为File(将上传的文件写入新建的文件中)
        try {
            OutputStream ous = new FileOutputStream("C:\\excel\\import\\test1.xlsx");
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
        System.out.println("上传的Excel副本拷贝完成。");

        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(1);
        importParams.setTitleRows(1);
        //importParams.setNeedSave(true);
        /*List<ExcelExportSimplePO> list = ExcelImportUtil.importExcel(new File(PoiPublicUtil.getWebRootPath("import_excel/test.xlsx")),ExcelExportSimplePO.class,importParams);*/
        List<ExcelExportSimplePO> list = ExcelImportUtil.importExcel(new File("C:\\excel\\import\\test1.xlsx"),ExcelExportSimplePO.class,importParams);
        System.out.println(ReflectionToStringBuilder.toString(list.get(0)));
        System.out.println("list：" + list.toString());

        return "Import excel ok.";
    }





}
