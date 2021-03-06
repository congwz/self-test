import org.junit.Test;

import java.io.*;

/**
 * Created by Congwz on 2019/2/15.
 */
public class test {

    @Test
    public void self() {



        /**
         * TIMESTAMP转化为yyyy-MM-dd格式
         * */
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//定义日期类型格式
//        Timestamp ts = new Timestamp(System.currentTimeMillis());
//        String result = format.format(ts);
//        System.out.println(result);//打印获取的字符串 // 2019-09-20

        /**
         * String转化为TIMESTAMP格式
         * */
//        Timestamp timestamp = Timestamp.valueOf("2019-07-20 11:49:45");
//        System.out.println(timestamp);  // 2019-07-20 11:49:45.0



        /*DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {

            System.out.println(df.parse("2019-09-20"));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/


        //Set集合转换成逗号分割的字段串返回
//        Set<Integer> test = new HashSet<Integer>();
//        test.add(2);
//        test.add(18);
//        test.add(5);
//
//        System.out.println("Set集合 = " + test.toString());
//
//        if(CollectionUtils.isEmpty(test)){
//            System.out.println("set是空集合");
//        }else {
//            StringBuilder sb=new StringBuilder(",");
//            for (Integer integer : test) {
//                if(integer==null){
//                    continue;
//                }
//                sb.append(integer).append(",");
//            }
//            String str = sb.toString();
//            System.out.println("StringBuilder = " + str);
//            str = str.substring(1, str.length() - 1);
//            System.out.println("去掉开头和末尾的逗号 = " + str);
//
//            String[] splitArr = str.split(",");
//            List<String> list = new ArrayList<String>(Arrays.asList(splitArr));
//            System.out.println("list = " + list.toString());
//
//        }







        /**
         * 时间转化
         String pattern = "yyyyMMddHHmmss";
         DateFormat df = new SimpleDateFormat(pattern);

         Date now = new Date();
         long getTime = now.getTime();
         System.out.println(getTime); //1551341546898
         System.out.println(df.format(now)); //20190228161226 2019-02-28 16:12:26


         //long time = 10*60*1000;   //10分钟
         long time = 24*60*60*1000; //一天
         Date beforeDate = new Date(now.getTime() - time);
         System.out.println(beforeDate.getTime()); //1551340946898
         System.out.println(df.format(beforeDate)); //20190228160226 2019-02-28 16:02:26
         **/

        //逗号分割字符串，返回结果为字符串数组
//        String t = "title,name,age";
//        String[] res = t.split(",");
//        List<String> list = new ArrayList<String>(Arrays.asList(res));  //数组转化为List
//        list.forEach(item -> {
//            System.out.println(item);
//        });
        //System.out.println(list.toString());


        /**
         * 对接接口，获取对象数据【方法1--->繁琐】
         String passportUrl = "http://passport-api.hc.com";
         //获取邮箱密码
         String url = passportUrl + "/api/organizational/getUserEmailInfo?token=test_token&work_code=" + "01000207";
         String json_str = new RestTemplate().getForObject(url, String.class);
         System.out.println("json_str :" + json_str);
         Map retMap = JSON.parseObject(json_str, Map.class);

         String password = "";
         if ("1".equals(retMap.get("success"))) {
         UserEmailInfoDTO userEmailInfoDTO = JSON.toJavaObject((JSON) JSON.toJSON(retMap.get("data")), UserEmailInfoDTO.class);
         password = userEmailInfoDTO.getEmail_password();
         System.out.println("user email info: " + userEmailInfoDTO.toString());
         System.out.println("password: " + password);
         } else {
         System.out.println(retMap.get("errorMessage"));
         }
         System.out.println(new Date() + " send email success.");
         * */


//
//        //对接接口，获取对象数据【方法2--->推荐】
//        String passportUrl = "http://passport-api.hc.com";
//        //获取邮箱密码
//        String url = passportUrl + "/api/organizational/getUserEmailInfo?token=test_token&work_code=" + "01000207";
//        Map<String, Object> retMap = new RestTemplate().getForObject(url, Map.class);
//
//        String password = "";
//        if ("1".equals(retMap.get("success"))) {
//            LinkedHashMap map = (LinkedHashMap) retMap.get("data");
//            System.out.println(map.toString());
//            password = (String) map.get("email_password");
//            System.out.println("password: " + password);
//        } else {
//            System.out.println(retMap.get("errorMessage"));
//        }
//        System.out.println(new Date() + " send email success.");
//

        /**
         * //BigDecimal的四舍五入后一位是5的时候不进位的bug
         BigDecimal bgse = new BigDecimal(new Double(0.8256).toString()).setScale(2,BigDecimal.ROUND_HALF_UP);
         System.out.println(bgse);  // 0.83
         * */

//        String a = "制造商部件号：";
//        String a2 = "制造商部件号:";
//        System.out.println(a.length()); //7
//        System.out.println(a2.length()); //7
//        String b = "VFAG16-130-F8-P12-M6-FC4";
//        String b2 = "VF16-";
//        System.out.println(b.length());  //24
//        String b11 = "";
//        String b12 = "";
//        if(b.length() >= 20) {
//            b11 = b.substring(0,20);
//            b12 = b.substring(20,b.length());
//        }
//        System.out.println("b11: " + b11);
//        System.out.println("b12: " + b12);
//
//        System.out.println(b2.length()); //5
//
//        String c = "米思米（中国）精密机械贸易";
//        String c2 = "米（中国)";
//        System.out.println(c.length());  //13
//        System.out.println(c2.length());  //5

        /**
         * //获取跟目录
         String rootPath = "";
         try {
         String tempPath = ResourceUtils.getURL("classpath:") == null
         ?"":ResourceUtils.getURL("classpath:").getPath();
         if (!StringUtil.isEmpty(tempPath)) {
         File path = new File(tempPath);
         if (!path.exists()) {
         path = new File("");
         }
         rootPath = path.getAbsolutePath();
         System.out.println("project root path:" + rootPath);
         }
         } catch (FileNotFoundException e) {
         e.printStackTrace();
         }
         * */


//
//        //String filePath = "https://hcsscm.oss-cn-shanghai.aliyuncs.com/harmontronics-erp/20100326/字段.xls";
//        String filePath = "harmontronics-erp/01000008/订阅号二维码.jpg";
//        String hz = filePath.substring(filePath.lastIndexOf("/") + 1);
//
//        System.out.println("后缀名：" + hz);
//        InputStream in = null;
//        if (filePath.startsWith("https://hcsscm.oss-cn-shanghai.aliyuncs.com/")) {
//            filePath = filePath.substring(filePath.indexOf("https://hcsscm.oss-cn-shanghai.aliyuncs.com/") + "https://hcsscm.oss-cn-shanghai.aliyuncs.com/".length());
//        }
//
//        OSSClient client = new OSSClient("oss-cn-shanghai.aliyuncs.com", "LTAI7tPuY6wE9XSD", "s6qyZBm1754hnvHfoLMuzcUxGGWqvU");
//        InputStream inlast;
//        if(client == null) {
//            System.out.println("阿里云客户端获取失败");
//        }else {
//            try{
//                OSSObject ossObject = client.getObject("hcsscm", filePath);
//                in = ossObject.getObjectContent();
//                ByteArrayOutputStream bous = null;
//                try {
//                    bous = new ByteArrayOutputStream();
//                    byte[] buffer = new byte[1024];
//                    int len;
//                    while ((len = in.read(buffer)) > -1) {
//                        bous.write(buffer, 0, len);
//                    }
//                    bous.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                inlast = new ByteArrayInputStream(bous.toByteArray());
//            } catch (OSSException oe) {
//                System.out.println("从OSS文件中得到内容出错:"+oe.getMessage());
//                throw new RuntimeException("没有得到文件内容");
//            } catch (ClientException ce) {
//                System.out.println("从OSS文件中得到内容出错:"+ce.getMessage());
//                throw new RuntimeException("没有得到文件内容");
//            } finally{
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                client.shutdown();
//            }
//
//
//            OutputStream out = null;
//        /*File file = new File("C:\\self\\sz.xls");
//        if (!file.exists()) {
//            if (!file.getParentFile().exists()) {
//                file.getParentFile().mkdirs();
//            }
//        }*/
//            try {
//                out = new BufferedOutputStream(new FileOutputStream("C:\\self\\" + hz));
//                byte[] buffer = new byte[1024];
//                while (inlast.read(buffer) != -1) {
//                    out.write(buffer);
//                }
//                System.out.println("oss写本地完成啦！");
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    out.flush();
//                    out.close();
//                    out = null;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//


        /**
         * //list里null属性的对象h和不是null的属性
         List<EsSuggestPO> list = new ArrayList<>();
         EsSuggestPO item1 = new EsSuggestPO();
         item1.setId("1");
         item1.setResult("lalalalal");
         list.add(item1);

         EsSuggestPO item2 = new EsSuggestPO();
         item2.setId("3");
         list.add(item2);

         EsSuggestPO item3 = new EsSuggestPO();
         item3.setId("2");
         item3.setResult("");
         list.add(item3);

         List<EsSuggestPO> listExistResult = list.stream().filter(item -> item.getResult() != null).collect(Collectors.toList());

         List<EsSuggestPO> listNullResult = list.stream()
         .filter(item -> item.getResult() == null)
         .map(item -> {
         item.setResult("");
         return item;
         })
         .collect(Collectors.toList());


         System.out.println("original list:" + list.toString());
         System.out.println("exist result list:" + listExistResult.toString());
         System.out.println("exi result is null list:" + listNullResult.toString());

         * */

/*

        TestDTO[] detail = new TestDTO[3];
        for (int i = 0; i < 3; i++) {
            detail[i] = new TestDTO();
            detail[i].setPicking_no("I100214-19001294");
            switch (i) {
                case 0:
                    detail[i].setWarehousing_pick("3K01022             ");
                    break;
                case 1:
                    detail[i].setWarehousing_pick("3K01011             ");
                    break;
                case 2:
                    //detail[i].setWarehousing_pick("3D06013             ");   //null【最小】
                    break;
                default:
                    System.out.println("unexpected error!");
                    break;
            }
        }

        List<TestDTO> tempList = new ArrayList<TestDTO>(Arrays.asList(detail));
        System.out.println("temp list: " + tempList.toString());

*/

        /**
         * 这样调用  遇到null的值会抛出异常
         * */
        //Collections.sort(tempList, Comparator.comparing(TestDTO::getWarehousing_pick));


        /**
         * 这样调用，更好【推荐】--------->（null重新赋值为"",且使用trim去除前后空格）
         * */
/*

        Collections.sort(tempList, Comparator.comparing(TestDTO::trimNull));
*/


        /*
        打印信息如下：
        temp list: [TestDTO(picking_no=I100214-19001294, warehousing_pick=3K01022             ), TestDTO(picking_no=I100214-19001294, warehousing_pick=3K01011             ), TestDTO(picking_no=I100214-19001294, warehousing_pick=null)]
        after sort: [TestDTO(picking_no=I100214-19001294, warehousing_pick=), TestDTO(picking_no=I100214-19001294, warehousing_pick=3K01011), TestDTO(picking_no=I100214-19001294, warehousing_pick=3K01022)]

        * */


        /**
         * 这样调用  遇到null的值会重新赋值为""，从而避免异常
         * */
        //Collections.sort(tempList, Comparator.comparing(i -> i.getWarehousing_pick() == null ? "": i.getWarehousing_pick()));
        //Collections.sort(tempList, Comparator.comparing(i -> i.getWarehousing_pick() == null ? "": i.getWarehousing_pick().trim()));

//        System.out.println("after sort: " + tempList.toString());
//
//        for (TestDTO dto : tempList) {
//
//            //System.out.println(ToStringBuilder.reflectionToString(dto, JsonOutUtils.ToStringStyle.JSON_STYLE));
//            System.out.println(org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString(dto, ToStringStyle.JSON_STYLE));
//        }

        /*detail = tempList.toArray(new TestDTO[tempList.size()]);
        for (TestDTO dto : detail) {
            System.out.println(dto.getPicking_no());
            System.out.println(dto.getWarehousing_pick());

        }*/


        /**
         * 字符串截取和拼接
         * */
//        String purchase_no = "OP00010-19001307";
//        System.out.println(purchase_no.length()); //16
//        String split_dhlx = purchase_no.substring(0, 2);
//        String split_gs = purchase_no.substring(2, 7);
//        String split_dh = purchase_no.substring(8, purchase_no.length());
//        System.out.println(split_dhlx);
//        System.out.println(split_gs);
//        System.out.println(split_dh);

        /**
         * restTemplate方法调用其他接口
         * */
//        String url = "https://calljde.harmontronics.com/api/jde/barcode-py?ukid=10000956&type=01";
//        String res = new RestTemplate().getForObject(url, String.class);
//        System.out.println(res);
        /*if(!"1".equals(res)){
            System.out.println("No");
        }else {
            System.out.println("yes");
        }*/

        //String md5Pws = MD5Util.MD5("Hc01000207"); //05c9dd55ad61953e1465e62250cf3568
        //String md5Pws = MD5Util.MD5("111111");
        //System.out.println(md5Pws);


        //Assert.hasText("","非空且不为空字符串");
        //报错如下：
        /**
         * java.lang.IllegalArgumentException: 非空且不为空字符串

         at org.springframework.util.Assert.hasText(Assert.java:181)
         at test.self(test.java:336)
         at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
         at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
         at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
         at java.lang.reflect.Method.invoke(Method.java:498)
         at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
         at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
         at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
         at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
         at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
         at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
         at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
         at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
         at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
         at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
         at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
         at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
         at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
         at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
         at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:68)
         at com.intellij.rt.execution.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:47)
         at com.intellij.rt.execution.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:242)
         at com.intellij.rt.execution.junit.JUnitStarter.main(JUnitStarter.java:70)
         * */


        //字符串截取substring
        /*String date1 = "2019-07";
        String date2 = "2019-12";
        String rq = date2.substring(5, 7).replace("0","");
        System.out.println(rq);*/

        /*String date = "2019-07-04";
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        try {
            String res = format.parse(date).toString();
            System.out.println(res);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

//        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//        DateFormat format2= new SimpleDateFormat("M月d");
//        Date date = null;
//        String res = null;
//        String str = "2007-03-18";
//        try {
//            date = format1.parse(str);
//            res = format2.format(date);
//            System.out.println(date);
//            System.out.println(res);
//        } catch (ParseException e) {
//         e.printStackTrace();
//        }




/*
        Optional<Integer> max3 = Stream.of(1, 2, 3, 4, 5)
                .max(Comparator.comparingInt(x-> x));

        System.out.println("max3: " + max3); // 5
*/

//        String test = null;
//        List<String> testList = Splitter.on(",").splitToList(test);  //test不能是null
//        System.out.println("逗号分割后的字符串集合：" + testList.toString());




    }
















    /********************************文件传输基础——Java IO流**********************************/


    /**
     * 1、文件的编码
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void encodingTest() throws UnsupportedEncodingException {
        String a = "慕课ABC";
        //字符串转换成字节序列用的是项目默认的编码utf-8
        byte[] bytes = a.getBytes();
        for (byte b : bytes) {
            //把字节（转换成了int）以16进制的方式显示
            /**
             * （4*8）32位（4个字节）
             *  ffffffe6 ffffff85 ffffff95 ffffffe8 ffffffaf ffffffbe 41 42 43
             **/
            /*System.out.print(Integer.toHexString(b) + " ");*/


            /**
             * e6 85 95 e8 af be 41 42 43
             * */
            //去掉前面的24个0，只留下后8为
            //utf-8编码中文占用3个字节，英文占用1个字节
            System.out.print(Integer.toHexString(b & 0xff) + " ");  //e6 85 95 e8 af be 41 42 43

        }

        System.out.println();
        byte[] bytes2 = a.getBytes("gbk");
        for (byte b : bytes2) {
            //gbk编码中文占用2个字节，英文占用1个字节
            System.out.print(Integer.toHexString(b & 0xff) + " "); //c4 bd bf ce 41 42 43
        }

        System.out.println();
        //java是双字节编码（即utf-16be编码）
        byte[] bytes3 = a.getBytes("utf-16be");
        for (byte b : bytes3) {
            //utf-16be编码中文占用2个字节，英文占用2个字节
            System.out.print(Integer.toHexString(b & 0xff) + " ");  //61 55 8b fe 0 41 0 42 0 43
        }


        System.out.println();
        /**
         * 当你的字节序列是某种编码时，这个时候想要把字节序列变成字符串，也需要用这中编码方式，否则会出现乱码
         * */
        String s3 = new String(bytes3); //byte3是utf-16be编码，现在用项目默认的编码转换字节序列为字符串
        System.out.println(s3);  //出现乱码 ---> aU�� A B C

        String s33 = new String(bytes3, "utf-16be");
        System.out.println(s33);  //不是乱码了  ---->  慕课ABC


        /**
         * 文本文件就是字节序列
         * 可以是任意编码的字节序列
         * 如果我们在中文机器上直接创建文本文件，那么该文本文件只认识ansi编码
         * 中文系统下，ansi编码即表示GBK编码
         * */

    }


    /**
     * 2、File类的使用
     */
    @Test
    public void FileTest() {

        //File file = new File("C:\\self\\demo");  //转义字符\
        File file = new File("C:" + File.separator + "self" + File.separator + "demo");
        System.out.println(file.exists());  //文件/文件夹是否存在  存在true，不存在false
        if (!file.exists()) {
            //创建一级目录
            file.mkdir(); //创建文件夹

            //创建多级目录
            //file.mkdirs();
        } else {
            file.delete(); //删除文件/文件夹
        }


        //File file2 = new File("C:\\self\\日记1.txt");
        File file2 = new File("C:\\self", "日记1.txt");
        if (!file2.exists()) {  //不存在则创建文件
            try {
                file2.createNewFile(); //创建文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            file2.delete(); //删除文件/文件夹
        }


        /**
         * 常用的File对象API
         * */
        System.out.println(file);  // C:\self\demo 等价于 ---> System.out.println(file.toString());
        System.out.println(file.getAbsolutePath());  // C:\self\demo
        System.out.println(file.getName());  // demo 目录名
        System.out.println(file2.getName());  // 日记1.txt 文件名
        System.out.println(file.getParent()); //父路径 C:\self

        System.out.println(file2.getParent()); //父路径 C:\self
        //等价于下面2种方式：
        //System.out.println(file2.getParentFile().getAbsolutePath());  //C:\self
        //System.out.println(file2.getParentFile());  //C:\self


    }

    /**
     * 2.2、列出File的一些常用操作，如：过滤、遍历等
     */
    @Test
    public void FileUtilTest() {
        /**
         * 列出指定目录下（包括其子目录）的所有文件
         * */
        File dir = new File("c:\\self");
        if (!dir.exists()) {
            throw new IllegalArgumentException("目录：" + dir + "不存在");
        } else if (!dir.isDirectory()) {
            throw new IllegalArgumentException(dir + "不是目录");
        }

        //list()方法用于列出当前目录下的（直接）子目录和文件
        String[] fileNames = dir.list();  //返回的是字符串数组,内容是直接的子目录名称，不包含子目录下的内容
        for (String name : fileNames) {
            System.out.println(name);
        }

        System.out.println("===============================");

        //listDirAllFiles(dir);
        listDirAllFiles(new File("C:\\self\\Compare_tool"));

    }

    /**
     * 列出指定目录下（包括其子目录）的所有文件
     *
     * @param dir
     */
    private void listDirAllFiles(File dir) {
        if (!dir.exists()) {
            throw new IllegalArgumentException("目录：" + dir + "不存在");
        } else if (!dir.isDirectory()) {
            throw new IllegalArgumentException(dir + "不是目录");
        }

        File[] files = dir.listFiles();//返回直接子目录的对象
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    //递归
                    listDirAllFiles(file);
                } else {
                    System.out.println(file);
                }
            }
        }
    }


    /*
    RandomAccessFile 是java提供的对文件内容的访问，既可以读文件，也可以写文件。
    RandomAccessFile支持随机访问文件，可以访问文件的任意位置

    java文件模型：在硬盘上的文件是byte byte byte存储的，是数据的集合

    打开文件有两种模式：“rw”【读写】 “r”【只读】
    RandomAccessFile raf = new RandomAccessFile(file,"rw");
    RandomAccessFile是支持随机访问的，所以有个 文件指针，打开文件时，指针在开头：pointer = 0,写/读的时候，指针都会移动
    写：raf.write(int) //只写一个字节（后8位），同时指针指向下一个位置，准备再次写入
    读：raf.read() //读一个字节
    文件读写完成以后一定要关闭（oracle官方说明）
    * */

    /**
     * 比较复杂，放弃
     */
    @Test
    public void randomAccessFileTest() throws IOException {
        //相对路径
        File file = new File("randomAccessFileDemo");
        if (!file.exists()) {
            file.mkdir();
        }
        System.out.println(file.getAbsolutePath());  // C:\Users\Congwz\IdeaProjects\VIVERSELFTEST\randomAccessFileDemo

        File file1 = new File(file, "test.txt");
        if (!file1.exists()) {
            file1.createNewFile();
        }

        //对文件进行读写操作
        RandomAccessFile raf = new RandomAccessFile(file1, "rw");
        //指针的位置
        System.out.println(raf.getFilePointer());  // 0

    }


    /**
     * 3.1、字节流
     * 抽象类：InputStream、OutputStream
     * InputStream  抽象了应用程序读取数据的方式
     * OutputStream 抽象了应用程序写出数据的方式
     * <p>
     * 达到文件结尾称为EOF即end 或者 读到-1就是读到结尾
     * <p>
     * 输入流的基本方法：【读】
     * int b = in.read();读取一个字节，无符号填充到int低八位。-1是EOF
     * in.read(byte[] buf)  读取数据填充到字节数组buf
     * in.read(byte[] buf,int start,int size) 读取数据到字节数组buf,从buf的start位置开始，存放size长度的数据
     * <p>
     * 输出流的基本方法：【写】
     * out.write(int b) 写出一个byte到流，写的是b的低8位（一个int是32位）
     * out.write(byte[] buf) 将缓冲区buf的字节数组都写入到流
     * out.write(byte[] buf, int start, int size) 字节数组buf从start位置开始，写size长度的字节到流
     * <p>
     * 子类FileInputStream（继承了InputStream） 具体实现了从文件中读取数据
     **/
    @Test
    public void IOZiJieInStreamTest() throws IOException {

        /**
         * 读取指定文件的内容，按照16进制输出到控制台
         * 同时，每输出10个byte换行
         * */
        String fileName = "C:\\self\\Compare_tool\\Compare\\wincmp3_PConline.ini";

        /**
         * //把文件作为字节流，进行读操作  【方式1】
         FileInputStream in = new FileInputStream(fileName);
         int b;
         int i = 1;
         while ((b = in.read()) != -1) {

         if(b <= 0xf) { //1位
         //单位(即1位)数，前面补0，变成双位数
         System.out.print("0");

         }

         //将整型b转换成16进制表示的字符串
         System.out.print(Integer.toHexString(b) + "  ");
         if(i++ %10 == 0) {
         System.out.println();
         }
         }
         in.close();
         * */


        /**
         * //把文件作为字节流，进行读操作  【方式2】
         FileInputStream in = new FileInputStream(fileName);
         byte[] buf = new byte[20*1024];  //20k （足够大）
         //从in中批量读取字节，放入buf这个字节数组中，从第0个位置开始放，最多放buf.length个，
         //返回的bytes是读到的字节个数
         int bytes =in.read(buf,0,buf.length);//一次性读完，说明字节数组足够大
         int j = 1;
         for(int i = 0; i<bytes; i++) {
         if(buf[i] <= 0xf) {
         System.out.print("0");
         }
         System.out.print(Integer.toHexString(buf[i]) + "  ");
         if(j++ %10 == 0) {
         //换行
         System.out.println();
         }
         }
         in.close();
         * */


        //把文件作为字节流，进行读操作  【方式3 ----> 推荐（适用于字节数组不够大，一次性读不完文件）】
        FileInputStream in = new FileInputStream(fileName);
        byte[] buf = new byte[6 * 1024];
        int bytes = 0;
        int j = 1;
        while ((bytes = in.read(buf, 0, buf.length)) != -1) {
            for (int i = 0; i < bytes; i++) {
                //& 0xff的作用：byte类型是8位，int类型是32位，为了避免数据转换错误，通过&0xff将高24位清0
                System.out.print(Integer.toHexString(buf[i] & 0xff) + "  ");
                if (j++ % 10 == 0) {
                    System.out.println();
                }
            }
        }
        in.close();


    }

    /**
     * 子类FileOutputStream（继承了OutputStream） 具体实现了向文件中写byte数据的方法
     *
     * @throws IOException
     */
    @Test
    public void IOZiJieOutStreamTest() throws IOException {

        /**
         * 读取指定文件的内容，按照16进制输出到控制台
         * 同时，每输出10个byte换行
         * */
        //String fileName = "C:\\self\\Compare_tool\\Compare\\wincmp3_PConline.ini";

        //如果该文件不存在，则直接创建。如果存在，删除后创建
        FileOutputStream out = new FileOutputStream("randomAccessFileDemo/out.txt");

        //如果该文件不存在，则直接创建。如果存在，则在文件中追加内容
        //FileOutputStream out = new FileOutputStream("randomAccessFileDemo/out.txt",true);

        out.write('A'); //写出了'A'的低八位
        out.write('B'); //写出了'B'的低八位
        int a = 10; //write只能写8位，那么写一个int需要4次，每次8位，如下：

        /*
        //底层实现
        out.write(a >>> 24);  //右移24位，这样把高8位移到后面了，前面都是0，写（高位的）低8位
        out.write(a >>> 16);
        out.write(a >>> 8);
        out.write(a);  //最后写的是a的最低8位
        */


        byte[] utf8 = "中国".getBytes();
        out.write(utf8);
        out.close();


    }

    /**
     * 文件复制操作例子
     *
     * @throws IOException
     */
    @Test
    public void IOZiJieInAndOutStreamDemo() throws IOException {
        //源文件
        File srcFile = new File("C:\\self\\vueProjectStudy\\Travel\\static\\note");
        if (!srcFile.exists()) {
            throw new IllegalArgumentException("文件：" + srcFile + "不存在");
        }
        if (!srcFile.isFile()) {
            throw new IllegalArgumentException(srcFile + "不是文件");
        }
        //目标文件
        File desFile = new File("C:\\self\\in-outDemo.txt");

        FileInputStream in = new FileInputStream(srcFile);  //读文件
        FileOutputStream out = new FileOutputStream(desFile); //写文件
        byte[] buf = new byte[8 * 1024];
        int b;
        //【不带缓冲，批量读取写出  -------> 读取写出速度最快，推荐哦！】
        /*while ((b = in.read(buf,0,buf.length)) != -1) {
            out.write(buf,0,b);
            out.flush();
        }*/

        //或者这样读取、写出 【不带缓冲，单字节读取写出】
        while ((b = in.read()) != -1) {
            out.write(b);
            out.flush();  //可有可无，推荐加上这一句
        }
        in.close();
        out.close();

    }

    /**
     * DataOutputStream
     * DataOutputStream dout = new DataOutputStream(new FileOutputStream("randomAccessFileDemo/out.txt"));
     * dout.writeInt(10)
     * dout.writeDouble(10.6)
     * dout.writeLong(10)
     *
     * DataInputStream
     * DataInputStream din = new DataInputStream(new FileInputStream("randomAccessFileDemo/out.txt"))
     * din.readInt()
     * din.readDouble()
     * din.readLong()
     * */


    /**
     * 带缓冲的字节流,实现文件的复制
     * BufferedInputStream
     * <p>
     * BufferedOutputStream
     */
    @Test
    public void IOZiJieInAndOutBuffStreamDemo() throws IOException {
        //源文件
        File srcFile = new File("C:\\self\\vueProjectStudy\\Travel\\static\\note");
        if (!srcFile.exists()) {
            throw new IllegalArgumentException("文件：" + srcFile + "不存在");
        }
        if (!srcFile.isFile()) {
            throw new IllegalArgumentException(srcFile + "不是文件");
        }
        //目标文件
        File desFile = new File("C:\\self\\in-outDemo.txt");

        //读文件
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
        //写文件
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(desFile));

        int c;
        //【带缓冲的读取】
        while ((c = bis.read()) != -1) {
            bos.write(c);
            bos.flush(); //刷新缓冲区
        }
        bis.close();
        bos.close();

    }


    /**
     * 3.2、字符流  ---> 操作的是文本文件
     * <p>
     * 文本
     * java中的文本（char）是16位无符号整数，是字符的unicode编码，是一种双字节编码
     * 文本文件
     * 文件是byte byte byte ...的数据序列
     * 文本文件是文本（char）序列按照某种编码方案（utf-8,utf-16be,gbk,...）序列化为byte的存储结果
     * <p>
     * 字符流（Reader、Writer）
     * 字符的处理：一次处理一个字符
     * 字符的底层仍然是基本的字节序列
     * <p>
     * InputStreamReader 完成byte流解析为char流，按照编码解析
     * OutputStreamWriter 提供char流到byte流，按照编码处理
     */
    @Test
    public void IOZiFuStreamTest() throws IOException {

        /*******************Reader************************/

//
//
//        //默认使用项目的编码
//        /**
//         * 注意：操作的时候，是要写文件的编码格式
//         * */
//        //InputStreamReader isr = new InputStreamReader(new FileInputStream("C:\\self\\vueProjectStudy\\Travel\\static\\note"));
//        InputStreamReader isr = new InputStreamReader(new FileInputStream("C:\\self\\vueProjectStudy\\Travel\\static\\note"),"utf-8");  //默认使用项目的编码
//
//        //InputStreamReader isr = new InputStreamReader(new FileInputStream("C:\\self\\vueProjectStudy\\Travel\\static\\note"),"gbk"); //中文乱码了哦
//
//        //方法1
//        /*int c;
//        while ((c = isr.read())!= -1) {
//            System.out.print((char)c);
//        }*/
//
//        //方法2
//        char[] buf = new char[8*1024];
//        int c;
//        //批量读取，放入buf这个字符数组，从第0个位置开始放，最多放buf.length个
//        //返回的是读到的字符个数
//        while ((c = isr.read(buf,0,buf.length)) != -1) {
//            String s = new String(buf,0,c);
//            System.out.print(s);
//        }
//        isr.close();
//


        /*******************Writer************************/
        InputStreamReader isr = new InputStreamReader(new FileInputStream("C:\\self\\vueProjectStudy\\Travel\\static\\note"), "utf-8");  //默认使用项目的编码

        FileOutputStream out = new FileOutputStream("C:\\self\\oswWriter.txt");
        //默认是项目的编码，其实还是要根据文件的编码格式来指明
        //OutputStreamWriter osw = new OutputStreamWriter(out);
        OutputStreamWriter osw = new OutputStreamWriter(out, "utf-8");
        char[] buf2 = new char[8 * 1024];
        int c2;
        //批量读取，放入buf这个字符数组，从第0个位置开始放，最多放buf.length个
        //返回的是读到的字符个数
        while ((c2 = isr.read(buf2, 0, buf2.length)) != -1) {
            osw.write(buf2, 0, c2);
            osw.flush();
        }

        isr.close();
        osw.close();
    }

    /**
     * FileReader
     *  FileReader fr = new FileReader("c:\\self\\1.txt")
     *
     * FileWriter
     *  FileWriter fw = new FileWriter("c:\\self\\2.txt")
     *  FileWriter fw = new FileWriter("c:\\self\\2.txt",true) //对文件的内容进行追加
     * */


    /**
     * 字符流的过滤器
     * BufferedReader  ----> readLine 一次读一行，但是不识别换行符
     *  BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("c:\\self\\1.txt")))
     * String s = br.readLine() 当br.readLin() == null说明读完了
     *
     * BufferedWriter / PrintWriter  ---->写一行
     *  BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("c:\\self\\2.txt")))
     * bw.write("123")  //写字符串
     * bw.newLine() //换行操作（识别换行）
     *
     * PrintWriter pw = new PrintWriter("c:\\self\\3.txt")
     * pw.print("123");  //输出字符串123
     * pw.println("456") //换行输出（识别换行）
     * pw.flush()
     * pw.close
     * */


    /**
     * 对象的序列化、反序列
     *
     * 对象序列化：将Object对象转换成byte序列。反之，称为对象的反序列化
     *
     * 【写】序列化流：ObjectOutputStream,是字节过滤流 ---->  writeObject(Object o)方法
     *  ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("randomAccessFileDemo/1.dat"))
     *
     * 【读】反序列化流：ObjectInputStream  ----> readObject()方法,返回值需要强制转化为对象类型比如Student、Person、Car等等
     *  ObjectInputStream ois = new ObjectInputStream(new FileInputStream("randomAccessFileDemo/1.dat"))
     *
     * 对象序列化接口：
     *    Serializable
     *    对象必须实现序列化接口，才能序列化，否则将出现异常
     *    这个序列化接口没有任何方法，只是一个标准
     *
     *
     * transient修饰对象中的某些属性，该属性则不会进行虚拟机jvm默认的序列化，也可以自己完成该属性的序列化（自定义序列化writeObject）,可以自定义反序列化readObject  ----> 参考ArrayList源码中的writeObject、readObject
     * 如：
     *   private transient int age;
     *
     *
     *  序列化中子类、父类（包括爷爷级、祖爷爷级...）的构造函数调用问题：
     *      1. 一个类实现了序列化接口，那么它的字类都能实现序列化
     *      2. 序列化的时候，递归调用父类的构造函数
     *      3. 对子类对象实现反序列化的时候，如果其父类没有实现序列化接口，那么其父类的构造函数会被调用
     * */


    public static void main(String[] args) {
        /*String s = "https://open.work.weixin.qq.com/wwopen/sso/qrConnect?appid=wx6337ae93b823df88&agentid=1000035&redirect_uri=http://passport-test.harmontronics.com/loginByWx&state=wx_login&login_type=jssdk";
        String dom = new RestTemplate().getForObject(s,String.class);
        //System.out.println(dom);
        String regex ="window.settings[\\s={\\s(appid):\"0-9a-zA-Z,]+(style)";
        Pattern pattern= Pattern.compile(regex);
        Matcher m = pattern.matcher(dom);
        if(m.find()) {
            String group = m.group(0);
            System.out.println("/////////////////////////////////////////////////");
            System.out.println(group);
        }
        //String regexRes = "key[\\s:\"]+(\\w+)\"";
        String regexRes = "key[\\s:\"]+(\\w+)\"";
        Pattern patternRes = Pattern.compile(regexRes);
        Matcher mRes = patternRes.matcher(m.group(0));
        if(mRes.find()) {
            String groupRes = mRes.group(1);
            System.out.println("最后key: " + groupRes);
        }else {
            System.out.println("sorry");
        }*/




    }
}
