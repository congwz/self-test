package com.viverselftest.service.impl;

import com.viverselftest.po.MailPO;
import com.viverselftest.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Congwz on 2019/1/25.
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Override
    public void sendMail(MailPO data) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(data.getFrom());
        mimeMessageHelper.setTo(data.getTo());
        mimeMessageHelper.setSubject(data.getSubject());
        mimeMessageHelper.setText(data.getText());

        //附件
        //FileSystemResource file = new FileSystemResource(new File(data.getPdfpath()));
        //mimeMessageHelper.addAttachment(data.getPdfdesc(), file);

        //发送策略
        mailSender.setUsername(data.getFrom());
        String password = "hsm@viver";
        mailSender.setPassword(password);
        mailSender.send(mimeMessage);

        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String smStr = sm.format(new Date());
        System.out.println(new Date() + "[ " + smStr +" ] send email success : " + data.toString());

    }
}
