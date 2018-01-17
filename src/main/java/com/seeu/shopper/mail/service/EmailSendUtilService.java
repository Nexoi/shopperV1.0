package com.seeu.shopper.mail.service;

import com.seeu.configurer.DOMAIN;
import com.seeu.shopper.config.iservice.FullConfigModel;
import com.seeu.shopper.config.model.Config;
import com.seeu.shopper.config.service.ConfigService;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by neo on 20/07/2017.
 */
@Service
public class EmailSendUtilService {

    @Resource
    ConfigService configService;

    /**
     * 默认采取 apache 方式，然后第二种 JavaMail
     *
     * @param who
     * @param title
     * @param text
     * @return
     * @throws Exception
     */
    public boolean send(String who, String title, String text) throws Exception {
//        return sendJavaMail2(who, title, text);
        return sendApacheMail(who, title, text) ? true : sendJavaMail2(who, title, text);
//        return sendJAMail(who, title, text) ? true : sendApacheMail(who, title, text);
    }

    /**
     * Java Mail
     *
     * @param who
     * @param title
     * @param text
     * @return
     * @throws Exception
     */
    public boolean sendJAMail(String who, String title, String text) throws Exception {
        Map<String, String> config = FullConfigModel.getConfig(configService);

        String host = config.get("email_host");
        int port = Integer.parseInt(config.get("email_post"));
        String from = config.get("email_address");
        String username = config.get("email_username");
        String password = config.get("email_password");

        Integer ssl = Integer.parseInt(config.get("email_ssl"));
        boolean isSSL = ssl == null ? false : ssl == 0 ? false : true;
        boolean isAuth = true;

        Properties props = System.getProperties();
        props.put("mail.smtp.ssl.enable", isSSL);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", isAuth);

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setSentDate(new Date());
            message.setHeader("X-Mailer", "Microsoft Outlook Express 6.00.2900.2869");
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(who));
            message.setSubject(title);
            message.setContent(text, "text/html; charset=UTF-8");


            Transport.send(message);
            return true;
        } catch (AddressException e) {
            System.out.println(e.getMessage());
//            e.printStackTrace();
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
//            e.printStackTrace();
        }
        // 如果失败，尝试走 apache 路线
        return send(who, title, text);
    }


    /**
     * Apache Mail
     *
     * @param who
     * @param title
     * @param text
     * @return
     * @throws Exception
     */
    public boolean sendApacheMail(String who, String title, String text) throws Exception {
        Map<String, String> config = FullConfigModel.getConfig(configService);

        String host = config.get("email_host");
        int port = Integer.parseInt(config.get("email_post"));
        String from = config.get("email_address");
        String username = config.get("email_username");
        String password = config.get("email_password");
        Integer ssl = Integer.parseInt(config.get("email_ssl"));
        boolean isSSL = ssl == null ? false : ssl == 0 ? false : true;
        String to = who;
        try {
//            Email email = new SimpleEmail();
            HtmlEmail email = new HtmlEmail();
            email.setDebug(true);

            email.setCharset("UTF-8");
            HashMap map = new HashMap<>();
            map.put("X-Mailer", "SeeU Studio Mailer");
            String now = "" + new Date().getTime();
            map.put("Content-ID", "SeeUCreater" + (now).substring(4, now.length()));
            email.setHeaders(map);
            email.setSentDate(new Date());
            email.setHostName(host);
            email.setSmtpPort(port);
            email.setAuthentication(username, password);
            email.setFrom(from, from);
            email.addTo(to);
            email.setSubject(title);
//            email.setMsg(text);
//            email.setContent(text, "text/html; charset=UTF-8");
            email.setHtmlMsg("<html><body><pre>" + text + "</pre></body></html>");
            email.setSSLOnConnect(isSSL);

            email.send();
        } catch (EmailException e) {
            return false;
        }
        return true;
    }

    /**
     * JavaMail 第二种实现方式
     *
     * @param who
     * @param title
     * @param text
     * @return
     * @throws Exception
     */
    public boolean sendJavaMail2(String who, String title, String text) throws Exception {
        Map<String, String> config = FullConfigModel.getConfig(configService);

        String host = config.get("email_host");
        int port = Integer.parseInt(config.get("email_post"));
        String from = config.get("email_address");
        String username = config.get("email_username");
        String password = config.get("email_password");

        Integer ssl = Integer.parseInt(config.get("email_ssl"));
        boolean isSSL = ssl == null ? false : ssl == 0 ? false : true;
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.ssl.enable", isSSL ? "true" : "false");
        props.setProperty("mail.smtp.host", "" + host);
        props.setProperty("mail.smtp.port", "" + port);
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.debug", "true");

        try {
            Session session = Session.getInstance(props);

            MimeMessage message = new MimeMessage(session);
            message.setSentDate(new Date());
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(who)); // 邮件的收件人
//        message.setRecipient(Message.RecipientType.CC, new InternetAddress(MAIL_CC)); // 邮件的抄送人
//        message.setRecipient(Message.RecipientType.BCC, new InternetAddress(MAIL_BCC)); // 邮件的密送人
            message.setSubject(title);

            MimeBodyPart html = new MimeBodyPart();
            html.setContent(text, "text/html; charset=UTF-8");

            MimeMultipart mm = new MimeMultipart();
            mm.addBodyPart(html);
            mm.setSubType("related");
            message.setContent(mm);
            message.saveChanges();

            Transport ts = session.getTransport();
            ts.connect(host, username, password);
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }
}
