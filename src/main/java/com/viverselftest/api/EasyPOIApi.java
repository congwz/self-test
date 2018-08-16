package com.viverselftest.api;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.util.PoiPublicUtil;
import cn.afterturn.easypoi.word.WordExportUtil;
import com.viverselftest.dto.PageDTO;
import com.viverselftest.dto.inquireonline.InquireOnlineConditionsDTO;
import com.viverselftest.po.*;
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
            item.setAge(20 + i);
            item.setSex("女");
            list.add(item);
        }

        map.put("title","word模板表格");
        map.put("tableTitle","table数据如下");
        map.put("personList",list);
        /*map.put("personList",mapList);*/

        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        //方法一：使用本地磁盘上的模板文件
        //configuration.setDirectoryForTemplateLoading(new File("C:/"));

        //方法二：使用idea中resource下的template文件夹下的模板文件
/*
        configuration.setDirectoryForTemplateLoading(new File(getClass().getClassLoader().getResource("template").getFile()));
*/
        //  /C:/Users/Congwz/IdeaProjects/VIVERSELFTEST/target/classes/template
/*
        System.out.println(getClass().getClassLoader().getResource("template").getFile());
*/

        //方法三：使用临时文件  拷贝模板文件的副本  最后手动删除临时文件
        File tempTemplateFile = ExcelUtill.getTempTemplateFile("template/templateTable.ftl");
        configuration.setDirectoryForTemplateLoading(new File("C:/viverself"));

        File outFile = new File("C:/word/test.doc");
        //获取模板文件
        //Template t =  configuration.getTemplate("templateTable.ftl","utf-8");
        Template t =  configuration.getTemplate(tempTemplateFile.getName(),"utf-8");
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"),10240);
        //将填充数据填入模板文件并输出到目标文件
        t.process(map, out);
        out.close();

        //手动删除模板的临时文件（副本）
        if(tempTemplateFile.exists()){
            tempTemplateFile.delete();
            System.out.println("临时模板文件手动删除完成!");
        }
        System.out.println("表格模板导出Word 成功!!! -》test.doc");

        /*临时模板文件手动删除完成!
                表格模板导出Word 成功!!! -》test.doc
        Word转Pdf开始启动..............
        打开word文档...C:/word/test.doc
        转换为pdf文档...C:/pdf/test.pdf
        文档转换完成用时：3235毫秒*/

        /*try {
            // 获取模板文件
            XWPFDocument doc = WordExportUtil.exportWord07("/template/templateWordTable.docx",map);  //失败的方法

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


        /*word转pdf*/
        WordUtill.wordConvertToPdf("C:/word/test.doc","C:/pdf/test.pdf");
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


    /**
     * 数据导出excel-数值存储
     * @param response
     * @throws IOException
     */
    @ApiOperation("模板导出Excel-数值存储")
    @GetMapping("/excel/templateNumber/export")
    public void testTemplateNumberExcelExport(HttpServletResponse response) throws IOException{
        /*单条数据导出*/
        TemplateExportParams params = new TemplateExportParams("C:/excel/viverTemplate3.xls");
        params.setSheetName("person3-single");
        Map<String,Object> map = new HashMap();
        map.put("id",1.12);
        map.put("name","李钟硕");
        map.put("sex","男");
        map.put("age",18);
        map.put("jobDate","2018-04-16");
        Workbook wb = ExcelExportUtil.exportExcel(params,map);

        FileOutputStream fos2 = new FileOutputStream("C:/excel/templateExport3.xls");
        wb.write(fos2);
        fos2.close();
        System.out.println("模板3导出Excel Success!!! -》templateExport3.xls");
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
            item.setId(i);
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




    /**
     * 查询报价单表头一对多显示与查询
     * @param search_dto
     * @return
     */
    @ApiOperation("获取报价单表头一对多显示与查询")
    @PostMapping("/get-info-by-conditions")
    public PageDTO getQuotedInfoByHD(@RequestBody InquireOnlineConditionsDTO search_dto) {
        return easyPOIService.getQuotedInfoByHD(search_dto);
    }

    /*============================================导出excel-使用模板+指令==================================*/
    /**
     * 根据查询条件导出报价信息
     * @param search_dto
     */
    @ApiOperation(value = "根据查询条件导出报价excel")
    @PostMapping("/export")
    public int export(@RequestBody InquireOnlineConditionsDTO search_dto){
        easyPOIService.export(search_dto, "01000207", "张聪伟");
        return 0;
    }


    /**
     * 指令导出excel
     * easypoi的三目运算  t.age == 24 ? '无' : t.age 格式为字符串
     */
    @ApiOperation(value = "指令导出excel")
    @PostMapping("/export/test")
    public void exportTest(HttpServletResponse response){
        List<ExcelExportPersonPO> list = new ArrayList<>();

        ExcelExportPersonPO person1 = new ExcelExportPersonPO();
        ExcelExportPersonPO person2 = new ExcelExportPersonPO();
        ExcelExportPersonPO person3 = new ExcelExportPersonPO();

        person1.setId(1);
        person1.setName("Viver");
        person1.setSex("女");
        person1.setAge(24000);
        person1.setJobDate("2017-09-04");

        person2.setId(2);
        person2.setName("池昌旭");
        person2.setSex("男");
        person2.setAge(28);
        person2.setJobDate("2016-12-12");

        person3.setId(3);
        person3.setName("李钟硕");
        person3.setSex("男");
        person3.setAge(999);
        person3.setJobDate("2000-01-01");

        list.add(person1);
        list.add(person2);
        list.add(person3);

        Map<String,Object> mapData = new HashMap<>();
        mapData.put("personList",list);  //直接是list,不需要封装为map,作为模板导出成功了哟
        TemplateExportParams params = new TemplateExportParams("C:/excel/2.xlsx");
        //标题开始行
        params.setHeadingStartRow(0);
        //标题行数
        params.setHeadingRows(1);
        //设置sheetName，若不设置该参数，则使用原本的sheet名称
        params.setSheetName("person");
        Workbook workbook = ExcelExportUtil.exportExcel(params,mapData);

        /*写入文件*/
        File file = new File("C:/excel");
        if(!file.exists()){
            file.mkdirs();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("C:/excel/templateExportExcelTest.xlsx");
            workbook.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("指令模板导出Excel Success!!! -》templateExportExcelTest.xlsx");


    }


    /**
     * 导出一对多数据的excel支持搜索和排序
     * @param search_dto
     * @throws IOException
     */
    @ApiOperation("导出一对多数据的excel支持搜索和排序")
    @PostMapping("/export/oneToMore")
    public void exportOneToMoreExcel(@RequestBody InquireOnlineConditionsDTO search_dto) throws IOException {

        List<InquireOnlineExcelPO> list = easyPOIService.exportOneToMoreExcel(search_dto);

        ExportParams exportParams = new ExportParams("","","export_one_to_more");
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, InquireOnlineExcelPO.class,list);

        FileOutputStream fos = new FileOutputStream("C:/excel/exportOneToMore.xls");
        workbook.write(fos);
        fos.close();
        System.out.println("export one to more data success.");
    }


    /**
     * 行转列导出excel明细与供应商
     * @param search_dto
     */
    @ApiOperation("行转列导出excel明细与供应商")
    @PostMapping("/export/detail/provider")
    public void exportDetailAndProvider(@RequestBody InquireOnlineConditionsDTO search_dto) {
        easyPOIService.exportDetailAndProvider(search_dto);
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
