package com.viverselftest.service;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ZipUtilPackageServices {

    public void zipDownload(HttpServletResponse response,File file){
        //压缩 使用java.util.zip包下的方法，单独放在service里
        String zipFileName = Calendar.getInstance().getTimeInMillis()
                +"-"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                + ".zip";
        String zipPathFileName = "/zipViver"+"/"+zipFileName;


        OutputStream outputStream = null;
        ZipOutputStream zipOutputStream = null;

        BufferedInputStream bufferedInputStream = null;
        try {
            outputStream =new BufferedOutputStream(new FileOutputStream(zipPathFileName));
            zipOutputStream = new ZipOutputStream(outputStream);

            //创建临时的zip压缩包
            //SmbFile smbFile = new SmbFile("C:\\瀚川文件\\下载的前端对接\\复杂的全部-pdf-模型图\\组件Dom.txt"); /*参数应该是：服务器上文件的路径*/
            //File file = new File("C:\\瀚川文件\\下载的前端对接\\复杂的全部-pdf-模型图\\组件Dom.txt");
            if(file != null){
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipOutputStream.putNextEntry(zipEntry);

                bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                //推荐这种方式进行读写
                /*byte[] bytes = new byte[bufferedInputStream.available()];
                bufferedInputStream.read(bytes);
                zipOutputStream.write(bytes);*/

                //or 下面一种方式实现 【读写】
                int len = 0;
                byte[] bytes = new byte[8192];
                while ( (len = bufferedInputStream.read(bytes) ) > 0){
                    zipOutputStream.write(bytes,0,len);
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        /*catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        finally {
            try {
                zipOutputStream.closeEntry();
                zipOutputStream.finish();  //关闭zip流
                zipOutputStream.close();  //关闭基础流和zip流
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(bufferedInputStream != null){
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        //下载zip包
        File fileZip = new File(zipPathFileName);
        /*if(!fileZip.exists()){ //不存在,进入if条件，进行创建目录
            fileZip.setWritable(true);  //赋予写权限
            fileZip.setReadable(true);
            fileZip.mkdirs(); //可创建层级文件夹(目录)
        }*/

        InputStream ins = null;
        OutputStream ous = null;
        BufferedOutputStream bous = null;

        try {
            //System.out.println(fileZip.getPath());
            ins = new BufferedInputStream(new FileInputStream(fileZip.getPath()));
            //获取文件输出IO流
            ous = response.getOutputStream();
            bous = new BufferedOutputStream(ous);
            response.setContentType("application/x-download");
            response.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(zipFileName,"UTF-8"));

            //推荐这种方式进行读写
            /*byte[] bytes = new byte[ins.available()];
            ins.read(bytes);
            bous.write(bytes);*/

            //or 下面一种方式实现 【读写】
            int bytesReadLen = 0;
            byte[] bytes = new byte[8192];
            //网络文件流
            while ( (bytesReadLen = ins.read(bytes,0,8192)) != -1 ){
                bous.write(bytes,0,bytesReadLen);
            }

            bous.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(ins != null){
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(ous != null){
                try {
                    ous.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bous != null){
                try {
                    bous.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //删除
        if(fileZip.exists()){
            fileZip.delete();
            System.out.println("临时压缩包zip 删除OK*************************************");
        }else {
            System.out.println("临时压缩包zip 删除Fail*************************************8");
        }

    }

}
