package rapanui.service;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailService {
    public void sendMail(String subject, String msgBody) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "mail.broadpark.no");
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage mimeMsg = new MimeMessage(session);
        try {
            mimeMsg.setFrom(new InternetAddress("hamilton108@broadpark.no", "Option Sales Manager"));
            mimeMsg.addRecipient(RecipientType.TO, new InternetAddress("hamilton108@gmail.com", "Dude"));
            mimeMsg.setSubject(subject);
            mimeMsg.setText(msgBody);
            Transport.send(mimeMsg);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
