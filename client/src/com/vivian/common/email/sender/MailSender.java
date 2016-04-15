package com.vivian.common.email.sender;

import com.vivian.common.email.entity.Email;
import com.vivian.common.email.exception.SendEmailFailedException;
import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * author:zhangjian
 * date:2016-01-08
 * describe:为邮件发送的父类
 **/
public class MailSender {

    private static Logger logger = Logger.getLogger(MailSender.class);

    protected JavaMailSender mailSender;

    protected static final String ENCODING = "utf-8";

    private static Properties properties;

    static {
        if(!MailSender.readResourceMailProperties()){
            logger.info("load email default properties");
            MailSender.setDefaultProperties();
        }

    }

    public MailSender(){

        JavaMailSenderImpl  mailSenderImpl = new JavaMailSenderImpl();
        mailSenderImpl.setHost(properties.getProperty("mail.smtp.host"));
        mailSenderImpl.setUsername(properties.getProperty("mail.smtp.username"));
        mailSenderImpl.setPassword(properties.getProperty("mail.smtp.password"));
        mailSenderImpl.setDefaultEncoding(ENCODING);
        mailSenderImpl.setJavaMailProperties(properties);

        mailSender = mailSenderImpl;
    }

    public static void setProperties(Properties properties) {
        MailSender.properties = properties;
    }

    public void setSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean sendEmail(Email email) {
        MimeMessage msg;
        try {
            msg = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(msg, true,
                    ENCODING);
            helper.setFrom(email.getFrom());
            helper.setTo(email.getTo());
            helper.setSubject(MimeUtility.encodeText(email.getSubject(), ENCODING, "B"));
            helper.setText(email.getContext(),true);

            // 添加内嵌文件，第1个参数为cid标识这个文件,第2个参数为资源
            // helper.addInline("welcomePic", new File("d:/welcome.gif")); //
            // 附件内容
            // 这里的方法调用和插入图片是不同的，解决附件名称的中文问题
            if (email.getAttachment() != null) {
                File file = new File(email.getAttachment());
                helper.addAttachment(MimeUtility.encodeWord(file.getName()),
                        file);
            }

            logger.debug("send mail context:");
            logger.debug(email.toString());
            mailSender.send(msg);

        } catch (Exception e) {
            throw new SendEmailFailedException("Send email failed,Error happens", e);
        }

        return true;
    }

    public static Properties getProperties() {
        return properties;
    }


    private static void setDefaultProperties(){
        Properties defaultProperties = new Properties();
        defaultProperties.put("mail.smtp.host","smtp.qq.com");
        defaultProperties.put("mail.smtp.username","1107598012@qq.com");
        defaultProperties.put("email.smtp.password","942645426zj..");
        defaultProperties.put("mail.smtp.auth",true);
        defaultProperties.put("mail.smtp.timeout",25000);
        defaultProperties.put("mail.smtp.port",465);
        defaultProperties.put("mail.smtp.socketFactory.port",465);
        defaultProperties.put("mail.smtp.socketFactory.fallback",false);
        defaultProperties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties = defaultProperties;
    }

    private static boolean readResourceMailProperties(){
        try {
            properties = new Properties();
            properties.load(MailSender.class.getClassLoader().getResourceAsStream("email.properties"));
            return true;
        } catch (IOException e) {
            logger.debug(e);
            return false;
        }
    }
}
