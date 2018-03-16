package com.viverselftest.util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import java.io.File;

public class WordUtill {

    private static final int wordFormatPDF = 17; // PDF 格式

    public static void wordConvertToPdf(String wordUrl,String pdfUrl){
        System.out.println("Word转Pdf开始启动..............");
        long start = System.currentTimeMillis();
        ActiveXComponent jacobAXC = null;
        Dispatch doc = null;

        try {
            jacobAXC = new ActiveXComponent("Word.Application");
            jacobAXC.setProperty("Visible",new Variant(false));
            Dispatch docs = jacobAXC.getProperty("Documents").getDispatch();
            doc = Dispatch.call(docs,"Open",wordUrl).toDispatch();
            System.out.println("打开word文档..." + wordUrl);
            System.out.println("转换为pdf文档..." + pdfUrl);

            File toPdfFile = new File(pdfUrl);
            if(toPdfFile.exists()){
                toPdfFile.delete();
            }

            Dispatch.call(doc,"SaveAs",pdfUrl,wordFormatPDF);
            long end = System.currentTimeMillis();
            System.out.println("文档转换完成用时：" + (end - start) + "毫秒");
        } catch (Exception e) {
            System.out.println("word转换为pdf出错啦( ╯□╰ )：" + e.getMessage() + "\n");
            e.printStackTrace();
        }finally {
            if(jacobAXC != null){
                jacobAXC.invoke("Quit",0);
            }
        }

    }
}
