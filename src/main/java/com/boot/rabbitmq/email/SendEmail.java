package com.boot.rabbitmq.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 发送邮件
 */
@RestController
public class SendEmail {
    @Autowired
    private JavaMailSender javaMailSender;

//    public static void main(String[] args) {
//        SendEmail sendEmail = new SendEmail();
//
//        //
//        MimeMessage mimeMailMessage = sendEmail.javaMailSender.createMimeMessage();
//        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMailMessage);
//        try {
//            messageHelper.setFrom("920518289@qq.com");
//            messageHelper.setTo("2923959287@qq.com");
//            messageHelper.setSubject("测试邮件主题");
//            messageHelper.setText("测试邮件内容");
//            sendEmail.javaMailSender.send(mimeMailMessage);
//            System.out.println("success");
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }
    @RequestMapping("/send")
    public String send() throws MessagingException {
        //
        MimeMessage mimeMailMessage = this.javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMailMessage);
        messageHelper.setFrom("920518289@qq.com");
        messageHelper.setTo("2923959287@qq.com");
        messageHelper.setSubject("测试邮件主题");
        messageHelper.setText("测试邮件内容");
        this.javaMailSender.send(mimeMailMessage);
        System.out.println("success");
        return "success";
    }
}
