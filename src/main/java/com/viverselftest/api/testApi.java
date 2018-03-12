package com.viverselftest.api;

//import com.harmontronics.erp.util.StrUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
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
            Date date5 = sf.parse("2017-01-20");
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
            cal.add(Calendar.DATE, 30); // 指定日期加上30天  2017-12-31  2018-01-31
            cal2.add(Calendar.DATE, 30); // 指定日期加上30天 2017-11-09  2017-11-30
            cal3.add(Calendar.DATE, 30); // 指定日期加上30天 2018-01-24  2018-01-31
            cal4.add(Calendar.DATE, 15); // 指定日期加上15天 2017-02-25  2017-03-31
            cal5.add(Calendar.DATE, 38); // 指定日期加上15天 2017-02-27  2017-03-31

            if(Integer.valueOf(sf.format(cal.getTime()).substring(8,10)) < 25) {
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            }else {
                cal.add(Calendar.MONTH, 1);  //下一个月
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                //cal.roll(Calendar.DAY_OF_MONTH,-1);
            }

            if(Integer.valueOf(sf.format(cal4.getTime()).substring(8,10)) < 25) {
                cal4.set(Calendar.DAY_OF_MONTH, cal4.getActualMaximum(Calendar.DAY_OF_MONTH));
            }else {
                cal4.add(Calendar.MONTH, 1);  //下一个月
                cal4.set(Calendar.DAY_OF_MONTH, cal4.getActualMaximum(Calendar.DAY_OF_MONTH));
            }
            if(Integer.valueOf(sf.format(cal5.getTime()).substring(8,10)) < 25) {
                cal5.set(Calendar.DAY_OF_MONTH, cal5.getActualMaximum(Calendar.DAY_OF_MONTH));
            }else {
                /*cal5.add(Calendar.MONTH, 1);
                cal5.add(Calendar.DAY_OF_MONTH, -1);*/
                cal5.add(Calendar.MONTH, 1);  //下一个月
                cal5.set(Calendar.DAY_OF_MONTH, cal5.getActualMaximum(Calendar.DAY_OF_MONTH));

            }

            String s =sf.format(cal.getTime());
            String s4 =sf.format(cal4.getTime());
            String s5 =sf.format(cal5.getTime());
            System.out.println(s+"\r\n"+s4+"\r\n"+s5);
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

    public static void main(String[] args) {
        String order_company = "00201";
        String order_company2 = "00010";
        String order_company3 = "01212";

        System.out.println(Integer.valueOf(order_company).toString());
        System.out.println(Integer.valueOf(order_company2).toString());
        System.out.println(Integer.valueOf(order_company3).toString());
    }
}
