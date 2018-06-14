package com.viverselftest.api;

//import com.harmontronics.erp.util.StrUtils;
import com.google.common.base.Splitter;
import com.viverselftest.consts.TableTypeConsts;
import com.viverselftest.dto.outputtest.HandlePlanDetailDTO;
import com.viverselftest.po.TestLowerOrUpperPO;
import com.viverselftest.po.TestPO;
import com.viverselftest.service.TestService;
import com.viverselftest.util.CryptUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.Key;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/api/test/console")
public class testApi {

    /*    @ExceptionHandler(value = ClassCastException.class)
    @ResponseBody
    public ApiResDTO classCastException(HttpServletRequest req, ClassCastException e, HttpServletResponse res) {
        return ResUtils.successMsg(res.getHeader("str"));
   }*/

    @Autowired
    private TestService testService;

    private Logger logger = LoggerFactory.getLogger(testApi.class);


    /**
     * 测试字符串的返回
     * @return
     */
    @ApiOperation(value = "测试String的返回")
    @GetMapping("/str")
    public String testStr(HttpServletResponse res){
        String a = "";
        String b = " ";
        String c = "  ";
        String d = null;
        //System.out.println(a.length()+"\t"+b.length()+"\t"+c.length()+"\t"+d.length()+"\r");
        System.out.println(a.length()+"\t"+b.length()+"\t"+c.length()+"\r");
        //System.out.println(StrUtils.isEmpty(a)+"\t"+StrUtils.isEmpty(b)+"\t"+StrUtils.trim(c).length()+"\t"+StrUtils.isEmpty(d)+"\r\n");
        /*0	1	2
        true	false	0	true*/
        res.setHeader("str","12356789");

        String test = " ";
        String[] testArray = test.split(",");
        System.out.println(testArray.length+"\r\n"+testArray.toString());
        String test2 = ""+","+" ";
        String[] test2Array = test2.split(",");
        System.out.println(test2Array.length+"\r\n"+test2Array.toString());
        /*1
                [Ljava.lang.String;@1b882a33
        2
                [Ljava.lang.String;@4dd57ae1*/

        return res.getHeader("str");
    }

    @ApiOperation("测试日期计算")
    @GetMapping("test-data-cal")
    public void testDateCal(){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //Date date = sf.parse("2017-12-1"); // 指定日期
            Date date = sf.parse("2017-12-01"); // 指定日期
            Date date2 = sf.parse("2017-10-10");
            Date date3 = sf.parse("2017-12-25");
            Date date4 = sf.parse("2017-02-10");
            Date date5 = sf.parse("2018-01-01");
            Calendar cal = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            Calendar cal3 = Calendar.getInstance();
            Calendar cal4 = Calendar.getInstance();
            Calendar cal5 = Calendar.getInstance();
            cal.setTime(date);
            cal2.setTime(date2);
            cal3.setTime(date3);
            cal4.setTime(date4);
            cal5.setTime(date5);
            cal.add(Calendar.DATE, 30); // 指定日期加上30天  2017-12-31  2018-01-25
            //cal2.add(Calendar.DATE, 30); // 指定日期加上30天 2017-11-09  2017-11-30
            cal2.add(Calendar.DATE, 10); // 指定日期加上30天 2017-10-20  2017-10-25
            cal3.add(Calendar.DATE, 30); // 指定日期加上30天 2018-01-24  2018-01-25
            cal4.add(Calendar.DATE, 15); // 指定日期加上15天 2017-02-25  2017-02-25
            cal5.add(Calendar.DATE, 25); // 指定日期加上15天 2018-01-26  2018-02-25

            if(Integer.valueOf(sf.format(cal.getTime()).substring(8,10)) <= 25) {
                /*cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));*/
                cal.set(Calendar.DAY_OF_MONTH, 25);
            }else {
                cal.add(Calendar.MONTH, 1);  //下一个月
                /*cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));*/
                cal.set(Calendar.DAY_OF_MONTH, 25);
                //cal.roll(Calendar.DAY_OF_MONTH,-1);
            }

            if(Integer.valueOf(sf.format(cal2.getTime()).substring(8,10)) <= 25) {
                cal2.set(Calendar.DAY_OF_MONTH, 25);
            }else {
                cal2.add(Calendar.MONTH, 1);  //下一个月
                cal2.set(Calendar.DAY_OF_MONTH, 25);
            }

            if(Integer.valueOf(sf.format(cal4.getTime()).substring(8,10)) <= 25) {
                cal4.set(Calendar.DAY_OF_MONTH, 25);
            }else {
                cal4.add(Calendar.MONTH, 1);  //下一个月
                cal4.set(Calendar.DAY_OF_MONTH, 25);
            }
            if(Integer.valueOf(sf.format(cal5.getTime()).substring(8,10)) <= 25) {
                cal5.set(Calendar.DAY_OF_MONTH, 25);
            }else {
                /*cal5.add(Calendar.MONTH, 1);
                cal5.add(Calendar.DAY_OF_MONTH, -1);*/
                cal5.add(Calendar.MONTH, 1);  //下一个月
                cal5.set(Calendar.DAY_OF_MONTH, 25);

            }

            String s =sf.format(cal.getTime());
            String s2 =sf.format(cal2.getTime());
            String s4 =sf.format(cal4.getTime());
            String s5 =sf.format(cal5.getTime());
            System.out.println(s+"\r\n"+s2+"\r\n"+s4+"\r\n"+s5);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @ApiOperation("测试是否是目录or文件")
    @GetMapping("judge-dir")
    public void judgeDir(){
        //String rNull = "";
        String rNull = "123";
        //if(StrUtils.isEmpty(rNull)) return;
        /*C:\瀚川文件\2016激活工具\cert	是目录
        C:\Recovery.txt	不是目录
        C:\Recovery.txt	不是目录


        C:\瀚川文件\2016激活工具\cert	cert	不是文件
        C:\Recovery.txt	Recovery.txt	是文件
        C:\Recovery.txt	Recovery.txt	是文件
        AuditRecordServiceImpl方法.txt	AuditRecordServiceImpl方法.txt不是文件
        C:\self\mmall\src\main	main是目录
        C:\self\mmall\src\main	main不是文件
        C:\Users\Congwz\Desktop\AuditRecordServiceImpl方法.txt	AuditRecordServiceImpl方法.txt是文件
        C:\Users\Congwz\桌面\AuditRecordServiceImpl方法.txt	AuditRecordServiceImpl方法.txt不是文件*/

        String strN = "C:\\瀚川文件\\2016激活工具\\cert";
        String strY = "C:\\Recovery.txt\\";
        String str = "C:\\Recovery.txt";

        File fileN = new File(strN);
        File fileY = new File(strY);
        File file = new File(str);
        if(fileN.isDirectory()){
            System.out.println(fileN.getPath()+"\t是目录");
        }else{
            System.out.println(fileN.getPath()+"\t不是目录");
        }

        if(fileY.isDirectory()){
            System.out.println(fileY.getPath()+"\t是目录");
        }else{
            System.out.println(fileY.getPath()+"\t不是目录");
        }

        if(file.isDirectory()){
            System.out.println(file.getPath()+"\t是目录");
        }else{
            System.out.println(file.getPath()+"\t不是目录");
        }


        /*========================测试是否是文件=========================*/

        System.out.println("\r\n");
        if(fileN.isFile()){
            System.out.println(fileN.getPath()+"\t"+fileN.getName()+"\t是文件");
        }else{
            System.out.println(fileN.getPath()+"\t"+fileN.getName()+"\t不是文件");
        }

        if(fileY.isFile()){
            System.out.println(fileY.getPath()+"\t"+fileY.getName()+"\t是文件");
        }else{
            System.out.println(fileY.getPath()+"\t"+fileY.getName()+"\t不是文件");
        }

        if(file.isFile()){
            System.out.println(file.getPath()+"\t"+file.getName()+"\t是文件");
        }else{
            System.out.println(file.getPath()+"\t"+file.getName()+"\t不是文件");
        }


        File filTest = new File("AuditRecordServiceImpl方法.txt");
        if(filTest.isFile()){
            System.out.println(filTest.getPath()+"\t"+filTest.getName()+"\t"+"是文件");
        }
        System.out.println(filTest.getPath()+"\t"+filTest.getName()+"不是文件");

        File l = new File("C:\\self\\mmall\\src\\main");
        if(l.isDirectory()){
            System.out.println(l.getPath()+"\t"+l.getName()+"是目录");
        }else{
            System.out.println(l.getPath()+"\t"+l.getName()+"不是目录");
        }

        if(l.isFile()){
            System.out.println(l.getPath()+"\t"+l.getName()+"是文件");
        }else {
            System.out.println(l.getPath()+"\t"+l.getName()+"不是文件");
        }

        File t = new File("C:\\Users\\Congwz\\桌面\\AuditRecordServiceImpl方法.txt");
        if(t.isFile()){
            System.out.println(t.getPath()+"\t"+t.getName()+"是文件");
        }else {
            System.out.println(t.getPath()+"\t"+t.getName()+"不是文件");
        }

    }

    @ApiOperation("测试List中的addAll()方法")
    @GetMapping("/test-addAllMethod")
    public void testAddAllMethod(){
        /*addAll方法执行后：5
        1	2	a	b	c*/

        List<String> a = new ArrayList<>(3);
        a.add("a");
        a.add("b");
        a.add("c");

        List<String> b = new ArrayList<>();
        b.add("1");
        b.add("2");
        b.addAll(a);

        System.out.println("addAll方法执行后："+b.size());
        for (String item : b) {
            System.out.print(item+"\t");
        }
    }

    @ApiOperation("BigDeciaml测试")
    @GetMapping("/test-bigDecimal")
    public void testBigDeciamlMath(){

        /*123.12	BigDecimal：123.12	字符串：123.12
        11.125	11.13	11.13
        11.103	11.10	11.10

        0	BigDecimal：0.00	字符串：0.00
        11.0	11.00	11.00
        String:	12.00*/

        BigDecimal bg1 = new BigDecimal("0");
        BigDecimal sbg1 = bg1.setScale(2,BigDecimal.ROUND_HALF_UP);
        System.out.println(bg1+"\tBigDecimal："+sbg1+"\t字符串："+sbg1.toString());

        BigDecimal bg2 = new BigDecimal(Double.toString(11));
        BigDecimal sbg2 = bg2.setScale(2,BigDecimal.ROUND_HALF_UP);
        System.out.println(bg2+"\t"+sbg2+"\t"+sbg2.toString());

        String sbg3 = new BigDecimal("12").setScale(2,BigDecimal.ROUND_HALF_UP).toString();  //""->空字符串则会抛异常哦
        System.out.println("String:"+"\t"+sbg3);

    }

    @ApiOperation("List去重")
    @GetMapping("/test-list-duplicate")
    public void testListDuplicate(){
/*===================================Method1：set去重=============================
[a, b, c]
set string：[a, b, c]
[a, b, c]
no dup list string：[a, b, c]
===================================Method2：contains去重=============================
listDup2：[a, b, c, 1, a]
listNoDup2：[a, b, c, 1]*/


        /*===================================Method1：set去重=============================*/
        List<String> listDup = new ArrayList<>();
        listDup.add("a");
        listDup.add("b");
        listDup.add("c");
        listDup.add("a");
        listDup.add("b");
        Set<String> set = new HashSet<String>(listDup);
        System.out.println("===================================Method1：set去重=============================");
        System.out.println(set+"\nset string："+set.toString());

        listDup.clear();
        listDup.addAll(set);
        System.out.println(listDup+"\n"+"no dup list string："+listDup.toString());

        /*===================================Method2：contains去重=============================*/

        List<String> listDup2 = new ArrayList<>();
        listDup2.add("a");
        listDup2.add("b");
        listDup2.add("c");
        listDup2.add("1");
        listDup2.add("a");

        List<String> listNoDup2 = new ArrayList<>();
        for (String item : listDup2) {
            if(!listNoDup2.contains(item)){
                listNoDup2.add(item);
            }
        }
        System.out.println("===================================Method2：contains去重=============================");
        System.out.println("listDup2："+listDup2);
        System.out.println("listNoDup2："+listNoDup2);
        return;

        /*===================================Method3：remove去重(双重循环---》不推荐)=============================*/

    }


    /**
     * 增加/更新/删除计划设置的明细信息
     * @param detail_dto
     * @return
     */
    @ApiOperation(value = "处理计划设置的明细信息")
    @PostMapping("/handle-detail")
    public void handlePlanSetDetail(@RequestBody HandlePlanDetailDTO detail_dto){
        logger.info("处理计划设置的明细信息：" + "plan_head_id: " + detail_dto.getPlan_head_id() + " plan_status: " + detail_dto.getPlan_status()
                + " 模块List: "+detail_dto.getModule_list().toString() + " 材料List: " + detail_dto.getMaterial_list().toString());
    }


    @ApiOperation("AOP测试log输出1")
    @PostMapping("/test-aop-output1")
    public void testAOPLogOutPut1(@RequestParam(name = "work_code") String work_code,
                                 @RequestParam(name = "age") int age,
                                 @RequestBody HandlePlanDetailDTO detail_dto){
        System.out.println("测试AOP Log1");

    }


    @ApiOperation("AOP测试log输出2")
    @GetMapping("/test-aop-output2/{work_code}/{age}")
    public void testAOPLogOutPut2(@PathVariable(name = "work_code") String work_code,
                                  @PathVariable(name = "age") int age){
        System.out.println("测试AOP Log2");

    }


    @ApiOperation("测试swagger上显示大小写")
    @GetMapping("/test-swagger-show")
    public TestLowerOrUpperPO testSwaggerLowerOrUpperShow(){
        TestLowerOrUpperPO res = new TestLowerOrUpperPO();
        res.setIds("123");
        res.setUserNo("01000207");
        res.setUserName("张聪伟");
        res.setDepartment_bianHao("1048");
        res.setDepartment_mingCheng("IT部");
        return res;
    }


    @ApiOperation("测试property文件与覆盖")
    @GetMapping("/test-value-properties")
    public Map<String,String> testValueProperties(){
        return testService.testValueProperties();
    }







    public static void main(String[] args) {

        /*一、String转换成int类型*/

        /*String order_company = "00201";
        String order_company2 = "00010";
        String order_company3 = "01212";

        System.out.println(Integer.valueOf(order_company).toString());   //201
        System.out.println(Integer.valueOf(order_company2).toString());  //10
        System.out.println(Integer.valueOf(order_company3).toString());  //1212



        /*二、List转换成String并用,分割连接*/

        /*List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        String s1 = "http://www.baidu.com";
        String s2 = "http://www.qq.cn";
        String s3 = "https://shanghai.gov.cn";
        String s4 = "https://test.harmtronics.com";
        list.add(s1);
        list.add(s2);
        list.add(s3);
        list2.add(s4);

        String listToString = StringUtils.join(list," ,");
        System.out.println("list转为字符串：" + listToString);  //list转为字符串：http://www.baidu.com ,http://www.qq.cn ,https://shanghai.gov.cn

        String list2ToString = StringUtils.join(list2," ,");
        System.out.println("list2转为字符串：" + list2ToString);  //list2转为字符串：https://test.harmtronics.com

        String list3ToString = StringUtils.join(list3," ,");
        System.out.println("list3数组：" + list3);  //list3数组：[]
        System.out.println("list3转为字符串：" + list3ToString); //list3转为字符串：  */




        /*三、测试List元素移除--->通过下标/索引移除 or 通过数组元素（完全一样的数组元素才会移除成功）移除*/

//        List<String> strList = new ArrayList<>();
//
//        strList.add("a");
//        strList.add("b");
//        strList.add("c");
//        strList.add("d");
//
//        strList.remove("a");
//        System.out.println(strList.size() + strList.toString());  //移除成功 //移除成功  //3[b, c, d]
//
//        List<TestPO> oList = new ArrayList<>();
//        for(int i = 18; i<= 21; i++){
//            TestPO item = new TestPO();
//            item.setAge(i);
//            item.setName("张聪伟"+ i);
//            item.setSex("女");
//            oList.add(item);
//        }
//
//        oList.remove(1);
//        System.out.println(oList.size() + oList.toString()); //3[TestPO(age=18, name=张聪伟18, sex=女), TestPO(age=20, name=张聪伟20, sex=女), TestPO(age=21, name=张聪伟21, sex=女)]
//
//        TestPO rmPO = new TestPO();
//        rmPO.setAge(21);
//        rmPO.setName("张聪伟21");
//        rmPO.setSex("女");
//        oList.remove(rmPO);
//        System.out.println(oList.size() + oList.toString());  //移除成功  //2[TestPO(age=18, name=张聪伟18, sex=女), TestPO(age=20, name=张聪伟20, sex=女)]
//
//        TestPO rmPO2 = new TestPO();
//        rmPO2.setAge(18);
//        rmPO2.setName("张聪伟18");
//        oList.remove(rmPO2);
//        System.out.println(oList.size() + oList.toString());  //移除不成功哦
//
//        System.out.println("/*********************************************************/");
//        List<String> nullOrEmptyList = new ArrayList<>();
//        nullOrEmptyList.add("");
//        nullOrEmptyList.add("XX");
//        nullOrEmptyList.add("");
//        nullOrEmptyList.add("null");
//        nullOrEmptyList.add(null);
//        nullOrEmptyList.add("yy");
//        nullOrEmptyList.add(null);
//        nullOrEmptyList.add("         ");
//        //8	[, XX, , null, null, yy, null,          ]
//        System.out.println(nullOrEmptyList.size() + "\t" + nullOrEmptyList.toString());
//
//        //移除list中所有null对象
//        nullOrEmptyList.removeAll(Collections.singleton(null));
//        //6	[, XX, , null, yy,          ]
//        System.out.println(nullOrEmptyList.size() + "\t" + nullOrEmptyList);
//
//        List<String> tempList = new ArrayList<>();
//        tempList.add("");
//        tempList.add("  ");
//        //移除list中所有""和"  "对象(空格严格匹配才会移除)
//        nullOrEmptyList.removeAll(tempList);
//        //4	[XX, null, yy,          ]
//        System.out.println(nullOrEmptyList.size() + "\t" + nullOrEmptyList);






        /*四、文件创建时的文件名、路径相关测试*/

        /*try {
            //File tempFile = File.createTempFile("temp",null);
            //File tempFile = File.createTempFile("test",".txt");
            File tempFile = File.createTempFile("test",".txt", new File("C:\\viverself\\"));
            //boolean b = tempFile.renameTo(new File("C:/viverself/123.txt"));
            //System.out.println(b);  //true  改名成功

            //boolean b = tempFile.renameTo(new File("123.txt"));
            //System.out.println(b);  //false  改名失败

            //boolean b = tempFile.renameTo(new File(tempFile.getParent()+"/123.txt"));
            //System.out.println(b);  //true  改名成功

            System.out.println("name：" + tempFile.getName());
            System.out.println("absolute path：" + tempFile.getAbsolutePath());
            System.out.println("path：" + tempFile.getPath());
            System.out.println("parent：" + tempFile.getParent());
            System.out.println("parent file：" + tempFile.getParentFile());
            System.out.println("Canonical path：" + tempFile.getCanonicalPath());
            System.out.println("Canonical file：" + tempFile.getCanonicalFile());

            if(tempFile.exists()){
                tempFile.delete();
                System.out.println("测试的临时文件删除成功！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*
        name：temp4498861985333927274.tmp
        absolute path：C:\Users\Congwz\AppData\Local\Temp\temp4498861985333927274.tmp
        path：C:\Users\Congwz\AppData\Local\Temp\temp4498861985333927274.tmp
        parent：C:\Users\Congwz\AppData\Local\Temp
        parent file：C:\Users\Congwz\AppData\Local\Temp
        测试的临时文件删除成功！
        **/


        /*
        name：test6108646453893488455.txt
        absolute path：C:\viverself\test6108646453893488455.txt
        path：C:\viverself\test6108646453893488455.txt
        parent：C:\viverself
        parent file：C:\viverself
        Canonical path：C:\viverself\test6108646453893488455.txt
        Canonical file：C:\viverself\test6108646453893488455.txt
        测试的临时文件删除成功！
        **/



        /*五、测试BigDecimal用于取绝对值判断、负数的加减*/

        /*double d1 = 12.10;
        double d2 = -1.2;

        if(Math.abs(d1-d2) >= 0.1){
            System.out.println(Math.abs(d1-d2));  //13.299999999999999
            System.out.println("Error double");   //Error double
        }

        BigDecimal bg1 = new BigDecimal(d1).setScale(2,BigDecimal.ROUND_HALF_UP);
        BigDecimal bg2 = new BigDecimal(d2).setScale(2,BigDecimal.ROUND_HALF_UP);

        if(Math.abs(bg1.doubleValue()-bg2.doubleValue()) >= 0.1){
            System.out.println("BigDecimal Error double");  //BigDecimal Error double
        }
        System.out.println(bg2);  //-1.20

        BigDecimal bgsum = new BigDecimal(0);
        for(int i = 0; i< 10; i++){
            //double j = 0.1;
            //bgsum = bgsum.add(new BigDecimal(Double.toString(j)));

            BigDecimal j = new BigDecimal(0.1);
            bgsum = bgsum.add(j);


        }
        bgsum = bgsum.setScale(2,BigDecimal.ROUND_HALF_UP);
        System.out.println("0.1的求和："+bgsum);  //0.1的求和：1.00

        BigDecimal bgHsjeSum = new BigDecimal("0");
        for(int i = 0; i< 1; i++){
            if(1 ==1){
                bgHsjeSum = bgHsjeSum.add(new BigDecimal(Double.toString(-1.17)));
            }
        }
        bgHsjeSum = bgHsjeSum.setScale(2,BigDecimal.ROUND_HALF_UP);
        System.out.println(bgHsjeSum); //-1.17  */



        /*六、java的反射*/
        /*Field[] fields = TableTypeConsts.class.getFields();
        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        Map map = new HashMap();
        for (Field field : fields) {
            try {
                Object val = field.get(TableTypeConsts.class);
                list.add((String) val);  //添加field值

                String nameVal = field.getName();
                list2.add(nameVal);  //添加field属性名称
                map.put(nameVal,val);  //key:field属性名称 value:field值
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        System.out.println(list.toString());  //[unprocess_supply, order_details, auto_category, order_handle, offer_info, submitting_purchase_detail, submitting_purchase_nodetail, inquiry_processing, all_orders, review_nodetails, review_details]

        System.out.println(list2.toString());  //[UNPROCESS_SUPPLY, ORDER_DETAILS, AUTO_CATEGORY, ORDER_HANDLE, OFFER_INFO, SUBMITTING_PURCHASE_DETAIL, SUBMITTING_PURCHASE_NODETAIL, INQUIRY_PROCESSING, ALL_ORDERS, REVIEW_NODETAILS, REVIEW_DETAILS]

        System.out.println(map.toString()); //{SUBMITTING_PURCHASE_DETAIL=submitting_purchase_detail, ORDER_DETAILS=order_details, ALL_ORDERS=all_orders, INQUIRY_PROCESSING=inquiry_processing, ORDER_HANDLE=order_handle, SUBMITTING_PURCHASE_NODETAIL=submitting_purchase_nodetail, UNPROCESS_SUPPLY=unprocess_supply, REVIEW_DETAILS=review_details, OFFER_INFO=offer_info, REVIEW_NODETAILS=review_nodetails, AUTO_CATEGORY=auto_category}*/




        /*七、测试字符串转double*/
        /*String a = "0.12";
        String b = "2.12";

        String s = ".55";  //转换成double的时候，小数点前面自动补0

        double c = 0.99;
        double d = 2.99;
        int e = 10;
        double d1 = Double.parseDouble(a);
        System.out.println(d1);  //0.12
        double d2 = Double.parseDouble(b);
        System.out.println(d2);   //2.12
        System.out.println(c);   //0.99
        System.out.println(d);   //2.99
        System.out.println(e);  //10

        System.out.println(Double.parseDouble(s));  //0.55*/



        /*八、java中的对称加密算法：AES、DES*/
        /*String b = "123456";
        byte[] barry = b.getBytes();
        for(int i = 0; i< barry.length; i++){
            System.out.print(barry[i] + "  ");  //49  50  51  52  53  54
        }
        try {
            System.out.println(getKey(barry));  //javax.crypto.spec.SecretKeySpec@18704
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(CryptUtils.encrypt("abc123"));             //98d9a038503205d1
        System.out.println(CryptUtils.decrypt("98d9a038503205d1"));  //abc123

        *//*DES加密、解密字符串算法(java版)*//*
        System.out.println(CryptUtils.encrypt("dey-1-23nfd-dfdj:^@f"));             //5593a17201664d8d25aa27fbe15845c7dea947d951a497ff
        System.out.println(CryptUtils.decrypt("5593a17201664d8d25aa27fbe15845c7dea947d951a497ff"));  //dey-1-23nfd-dfdj:^@f*/




        /*九、*/



    }//main


    private static Key getKey(byte[] arrBTmp) throws Exception {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];

        // 将原始字节数组转换为8位
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }

        // 生成密钥
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
        return key;
    }





}


