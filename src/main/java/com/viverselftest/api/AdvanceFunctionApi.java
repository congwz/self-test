package com.viverselftest.api;

import com.viverselftest.po.MailPO;
import com.viverselftest.service.EmailService;
import com.viverselftest.service.ZipUtilPackageServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipModel;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(description = "高级功能测试接口")
@RestController
@RequestMapping("/api/advance/function")
public class AdvanceFunctionApi {

    @Autowired
    private ZipUtilPackageServices zipUtilPackageServices;

    @Autowired
    private EmailService emailService;


    @ApiOperation(value = "下载zip文件(LiMing-net.lingala.zip4j)")
    @PostMapping("/download/zip")
    public void downloadZip(HttpServletResponse response) throws IOException, InterruptedException, ZipException {

        List<File> fileList = new ArrayList<>();

        //获取本机一些文件
        File file1 = new File("C:\\瀚川文件\\下载的前端对接\\复杂的全部-pdf-模型图\\组件Dom.txt");
        File file2 = new File("C:\\瀚川文件\\下载的前端对接\\复杂的全部-pdf-模型图\\post表单下载.txt");
        File file3 = new File("C:\\瀚川文件\\下载的前端对接\\复杂的全部-pdf-模型图\\全部-模型图1.png");
        //File file4 = new File("C:\瀚川文件\下载的前端对接\复杂的全部-pdf-模型图\\全部-模型图2.png");

        fileList.add(file1);
        fileList.add(file2);
        fileList.add(file3);
        fileList.add(new File("C:\\瀚川文件\\下载的前端对接\\复杂的全部-pdf-模型图\\全部-模型图2.png"));

        //创建压缩包zip【临时】（可删除）
        String zipFileName =
                //Calendar.getInstance().getTimeInMillis()
                //+ "_" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())  //“包含:的特殊字符不能识别,允许空格”
                new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                        + "_" + fileList.size() + ".zip";
        String zipPathFileName = "/zip123" + "/" + zipFileName; //“/zip123”等同于“C:\\zip123”

        ZipModel zipModel = new ZipModel();
        zipModel.setFileNameCharset("GBK");  //"UTF-8"会乱码哦，所以要用GBK
        /*File zipFileDir = new File(zipPathFileName);
        if(!zipFileDir.exists()){ //不存在,进入if条件，进行创建目录
                zipFileDir.setWritable(true);  //赋予写权限
            zipFileDir.setReadable(true);
            zipFileDir.mkdirs(); //可创建层级文件夹(目录)
        }*/
        //FileOutputStream fileOutputStream = new FileOutputStream(new File(zipPathFileName));
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(new File(zipPathFileName)), zipModel);
        //ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFileDir),zipModel);

        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

        for (File fileItem : fileList) {
            zipOutputStream.putNextEntry(fileItem, zipParameters);

            if (fileItem.isDirectory()) {
                zipOutputStream.close();
                continue;
            }

            //先读  再写
            InputStream inputStream = new FileInputStream(fileItem);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            byte[] bytes = new byte[bufferedInputStream.available()];
            bufferedInputStream.read(bytes); //读
            zipOutputStream.write(bytes);  //写
            zipOutputStream.closeEntry();
            inputStream.close();
        }

        zipOutputStream.finish();
        zipOutputStream.close();

        //下载压缩包
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(zipFileName, "UTF-8"));
        response.setContentType("application/octet-stream");

        FileInputStream fileInputStream = new FileInputStream(zipPathFileName);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        byte[] bytes = new byte[bufferedInputStream.available()];
        bufferedInputStream.read(bytes);  //读
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);  //写

        bufferedInputStream.close();
        outputStream.flush();
        outputStream.close();

        //删除压缩包zip
        //new File(zipPathFileName).delete();

    }

    @ApiOperation(value = "下载zip文件（Haoxu.mu-util.zip）")
    @PostMapping("/download/zip/2")
    public void dowloadZip2(HttpServletResponse response, @RequestBody String txtGenString) {
        File txtFile1 = new File("c:\\瀚川文件\\txt1.txt");

        try {
            //txtFile1.createNewFile(); // 创建新文件  /*多余的code 可有可无*/
            //写入txt文件
            //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(txtFile1,true)); /*在原来的文件中继续追加*/
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(txtFile1)); /*存在则先删除旧文件 写入新数据。不存在，则创建文件再写入数据*/
            //bufferedWriter.write(txtGenString+"\n"+"加油哦"); /*换行无效*/
            bufferedWriter.write(txtGenString);
            bufferedWriter.flush();   // 把缓存区内容压入文件
            bufferedWriter.close();  // 关闭文件

            //读取写入txt文件的内容
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(txtFile1));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            line = bufferedReader.readLine();
            while (line != null) {
                System.out.println("读取：line" + "\t" + line);
                line = bufferedReader.readLine(); //终止循环
            }
            inputStreamReader.close();
            bufferedReader.close();

            System.out.println("========================Method2===================");
            StringBuilder sb = new StringBuilder();
            FileReader fileReader = new FileReader(txtFile1);
            int line2 = fileReader.read();
            while (line2 != -1) {
                sb.append((char) line2);
                line2 = fileReader.read();
            }

            System.out.println(sb);  //此时sb等同于sb.toString()
            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("写txt文件异常！");
        }

        System.out.println("zip压缩==================================================");
        //压缩
        zipUtilPackageServices.zipDownload(response, txtFile1);


    }

    /**
     * 使用瀚川邮箱向外发送邮件
     * @param to
     */
    @ApiOperation(value = "发送邮件")
    @GetMapping("/send-email")
    public void sendEmail(@RequestParam("email_to") String to) {
       /*Spring通过MimeMessageHelper发送邮件*/
        MailPO item = new MailPO();
        item.setFrom("viver.zhang@harmontronics.com");
        item.setTo(to);

        try {
            emailService.sendMail(MailPO.build(item.getFrom(), item.getTo()));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
