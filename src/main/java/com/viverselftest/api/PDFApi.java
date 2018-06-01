package com.viverselftest.api;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Api(description = "PDF")
@RestController
@RequestMapping("/api/pdf")
public class PDFApi {

    @ApiOperation(value = "生成pdf-简单")
    @GetMapping("/generate/simple")
    public void generatePDFSimple(){
        //新建一个文档
        Document document = new Document();
        try {
            File file = new File("C:\\pdf_study");
            if (!file.exists()) {
                file.mkdirs();
            }
            //定义输出位置并把文档对象装入输出对象中
            PdfWriter.getInstance(document,new FileOutputStream("C:\\pdf_study\\helloworld.pdf"));
            //打开文档
            document.open();
            //向文档中添加文字：Hello World!
            document.add(new Paragraph("Hello World!"));
            //关闭文档，释放资源
            document.close();
            System.out.println("PDF写入Hello World完成啦~");
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @ApiOperation(value = "生成pdf-复杂")
    @GetMapping("/generate/complicate")
    public void generatePDFComplicate(){

        /*Rectangle pageSize = new Rectangle(500,100);
        pageSize.setBackgroundColor(Color.blue);
        //新建一个文档
        Document document = new Document(pageSize,5,5,5,5);*/

        //Document document = new Document(PageSize.A4,5,5,5,5);
        Document document = new Document();
        try {
            File file = new File("C:\\pdf_study");
            if (!file.exists()) {
                file.mkdirs();
            }
            //方法一：itextAsian.jar包 支持pdf输出中文
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);
            //Paragraph pragraph = new Paragraph("你好", FontChinese);

            //方法二：使用Windows系统字体(TrueType)-成功
            /*BaseFont baseFont = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
            //com.lowagie.text.Font FontChinese = new com.lowagie.text.Font(baseFont, 12, com.lowagie.text.Font.NORMAL);
            Font font = new Font(baseFont);*/


            //定义输出位置并把文档对象装入输出对象中
            PdfWriter.getInstance(document,new FileOutputStream("C:\\pdf_study\\complicate.pdf"));
            //打开文档
            document.open();
            //向文档中添加文字：Hello World!
            /*document.add(new Paragraph("Good Morning 哈哈【中文测试】!",FontFactory.getFont(FontFactory.COURIER,20, Font.BOLD,new Color(243, 116, 255))));*/

            document.add(new Paragraph("Good Morning 哈哈【中文测试】!",FontChinese));
            /*document.add(new Paragraph("Good Morning 哈哈【中文测试】!",font));*/
            //关闭文档，释放资源
            document.close();
            System.out.println("复杂PDF写入Good Morning完成啦~");
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
