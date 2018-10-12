package com.viverselftest.service.impl;

import com.viverselftest.service.StreamDownloadService;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Congwz on 2018/10/11.
 */
@Service
public class StreamDownloadServiceImpl implements StreamDownloadService {
    public static final String fileRoot = "smb://Administrator:Secretadmin$plm@";

    @Override
    public void getResponse(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            System.out.println(file.toString());
            // 取得文件名。
            String filename = file.getName();
            System.out.println("文件名：" + filename);
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
            System.out.println("后缀名：" + ext);

            String fullPath = fileRoot + path;
            SmbFile remoteFile = new SmbFile(fullPath);
            InputStream is = new BufferedInputStream(new SmbFileInputStream(remoteFile));
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();

            String name = "attachment;filename=" + URLEncoder.encode(filename, "UTF-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", name);
            OutputStream os = response.getOutputStream();
            os.write(buffer);
            os.flush();
            os.close();

            /*URL url = new URL(path);
            HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
            String message = httpUrl.getHeaderField(0);
            if (StringUtils.hasText(message) && message.startsWith("HTTP/1.1 404")) {
                System.out.println("不存在");
            } else {
                System.out.println("存在" + message);
                httpUrl.connect();
                // 以流的形式下载文件。
                //InputStream fis = new BufferedInputStream(new FileInputStream(path));
                InputStream fis = new BufferedInputStream(httpUrl.getInputStream());
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
                // 清空response
                response.reset();
                // 设置response的Header
                //response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
                response.addHeader("Content-Length", "" + file.length());
                OutputStream os = new BufferedOutputStream(response.getOutputStream());
                response.setContentType("application/octet-stream");
                os.write(buffer);
                os.flush();
                os.close();
                httpUrl.disconnect();
            }*/
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //return response;


    }






    public static void main(String[] args) {
        String path = "172.30.1.31/000/539/167/agile53916770.PDF";
        // path是指欲下载的文件的路径。
        File file = new File(path);
        // 取得文件名。
        String filename = file.getName();
        // 取得文件的后缀名。
        String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

        System.out.println("文件名: " + filename);
        System.out.println("后缀名: " + ext);

    }


}


