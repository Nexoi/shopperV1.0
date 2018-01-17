import com.seeu.shopper.mail.service.EmailSendUtilService;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by neo on 28/09/2017.
 */
public class TestEmail {
    public static void main(String[] args)throws Exception {
        TestEmail emailSendUtilService = new TestEmail();
        emailSendUtilService.sendApacheMail("neoxiaoyi@163.com","test","emmmmmm");
    }

    public boolean sendApacheMail(String who, String title, String text) throws Exception {

        String host = "imap.gmail.com";
        int port = 993;
        String from = "leelboxservice@gmail.com";
        String username = "leelboxservice@gmail.com";
        String password = "Leelbox123";
        boolean isSSL = true;
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
}
