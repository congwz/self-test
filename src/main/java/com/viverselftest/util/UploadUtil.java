package com.viverselftest.util;

import com.aliyun.oss.OSSClient;
import com.viverselftest.consts.AliConstants;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 描述 阿里云上传工具
 *
 * @author Five.Liu
 * @date 2016/9/26
 */
public class UploadUtil {

    /**
     * 上传文件流
     * @param file
     * @throws UnsupportedEncodingException
     * @throws ClientException 
     * @throws OSSException 
     */
    public static String upload(File file, String path) throws Exception {

        if (!file.exists()) return "false";

    	String endpoint = AliConstants.OSS_END_POINT;
		String accessId = AliConstants.OSS_ACCESS_ID;
		String accessKey = AliConstants.OSS_ACCESS_KEY;
		String bucket = AliConstants.OSS_BUCKET;
		path = "harmontronics-erp" + (path.startsWith("/") ? "" : "/") + path + "/" + file.getName();
		String url = "https://" + bucket + "." + endpoint + "/" + path ;


        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(endpoint, accessId, accessKey);
            ossClient.putObject(bucket, path, file);
        } finally {
            if (null != ossClient) {
                ossClient.shutdown();
            }
        }

        file.delete();

        return url;
    }



    public static void smbGet(String remoteUrl, String localDir,String newFileName) {
        InputStream in = null;
        OutputStream out = null;
        try {
            SmbFile remoteFile = new SmbFile(remoteUrl);
            if (remoteFile == null) {
                System.out.println("共享文件不存在");
                return;
            }
            //String fileName = remoteFile.getName();
            File localFile = new File(localDir + File.separator + newFileName);
            in = new BufferedInputStream(new SmbFileInputStream(remoteFile));
            out = new BufferedOutputStream(new FileOutputStream(localFile));
            byte[] buffer = new byte[1024];
            while (in.read(buffer) != -1) {
                out.write(buffer);
                buffer = new byte[1024];
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public static void zipFiles(File[] srcfile,File zipfile) {
        byte[] buf=new byte[1024];
        try {
            //ZipOutputStream类：完成文件或文件夹的压缩
            ZipOutputStream out=new ZipOutputStream(new FileOutputStream(zipfile));
            for(int i=0;i<srcfile.length;i++) {
                FileInputStream in=new FileInputStream(srcfile[i]);
                out.putNextEntry(new ZipEntry(srcfile[i].getName()));
                int len;
                while((len=in.read(buf)) > 0) {
                    out.write(buf,0,len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
            System.out.println("压缩完成.");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
