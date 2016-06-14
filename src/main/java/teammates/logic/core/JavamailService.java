package teammates.logic.core;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import teammates.common.util.EmailWrapper;

public class JavamailService implements EmailSenderService {
    
    @Override
    public MimeMessage parseToEmail(EmailWrapper wrapper) throws AddressException, MessagingException, IOException {
        Session session = Session.getDefaultInstance(new Properties(), null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(wrapper.getSenderEmail(), wrapper.getSenderName()));
        email.setReplyTo(new Address[] { new InternetAddress(wrapper.getReplyTo()) });
        for (String recipient : wrapper.getRecipientsList()) {
            email.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        }
        for (String recipient : wrapper.getBccList()) {
            email.addRecipient(Message.RecipientType.BCC, new InternetAddress(recipient));
        }
        email.setSubject(wrapper.getSubject());
        email.setContent(wrapper.getContent(), "text/html");
        return email;
    }
    
    @Override
    public void sendEmail(EmailWrapper message) throws AddressException, MessagingException, IOException {
        MimeMessage email = parseToEmail(message);
        Transport.send(email);
    }
    
}
