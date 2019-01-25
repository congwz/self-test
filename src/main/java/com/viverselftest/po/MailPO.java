package com.viverselftest.po;

import lombok.Data;

/**
 * 邮件对象
 * Created by snake on 17-9-14.
 */
@Data
public class MailPO {
    private String from;

    private String to;

    private String subject;

    private String text;

    private String pdfpath;

    private String pdfdesc;

    private String cc;

    private String excelpath;

    private String exceldesc;



    public static MailPO build(String from, String to) {
        MailPO po = new MailPO();

        po.setFrom(from);
        po.setTo(to);

        String subject = "Spring Email Send Test";
        po.setSubject(subject);

        String text = "This is a spring email func send contents" + " :\n"
                + "2019-01-25 viver祝你🐖年大吉，诸事顺利~";
        po.setText(text);

        //String pdfdesc = "询价单" + inquiry.getInquiryNum() + ".xls";
        //po.setPdfdesc(pdfdesc);
        //po.setPdfpath(pdfpath);

        return po;
    }


}
