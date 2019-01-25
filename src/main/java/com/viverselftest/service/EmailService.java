package com.viverselftest.service;

import com.viverselftest.po.MailPO;

import javax.mail.MessagingException;

/**
 * Created by Congwz on 2019/1/25.
 */
public interface EmailService {

    void sendMail(MailPO mailPO) throws MessagingException;
}
