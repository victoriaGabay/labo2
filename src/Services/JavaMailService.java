package Services;

import Entities.Email;
import Entities.EmailTypes;
import Entities.User;
import Exceptions.EmailException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JavaMailService {

    public void SendEmail(Email email, User user) throws EmailException{
       buildMail(email, user);
    }

    private void buildMail(Email email, User user) throws EmailException {
        Properties properties = System.getProperties();

        properties.put("mail.smtp.host", user.getHost());
        properties.put("mail.smtp.port", user.getPort());
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user.getHostUserName(), user.getHostPassword());
                    }
                });
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user.getHostUserName()));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(email.getTo()));
            message.setSubject(email.getSubject());
            message.setText(email.getBody());

            Transport.send(message);
            System.out.println("message sent successfully....");

        }catch (MessagingException e){
            throw new EmailException(e.getMessage());
        }
    }

    public List<Email> recieve(User user) throws EmailException {
        List<Email> recievedEmails = new ArrayList<>();

        try {
            Properties propvals = new Properties();
            propvals.put("mail.pop3.host", user.getPopHost());
            propvals.put("mail.pop3.port", user.getPopHostPort());
            propvals.put("mail.pop3.starttls.enable", "true");
            Session emailSessionObj = Session.getDefaultInstance(propvals);

            Store storeObj = emailSessionObj.getStore("pop3s");
            storeObj.connect(user.getPopHost(), user.getHostUserName(), user.getHostPassword());

            Folder emailFolderObj = storeObj.getFolder("INBOX");
            emailFolderObj.open(Folder.READ_ONLY);

            Message[] messageobjs = emailFolderObj.getMessages();

            for (int i = 0, n = messageobjs.length; i < n; i++) {
                Message message = messageobjs[i];

                String from = String.valueOf(message.getFrom()[0]);
                String to = String.valueOf(message.getAllRecipients()[0]);
                String subject = message.getSubject();
                String body = message.getContent().toString();
                EmailTypes type = EmailTypes.Recieved;
                
                Email email = new Email(from, to, subject, body, user.getPopHost(), type, user.getId());
                recievedEmails.add(email);
            }
            emailFolderObj.close(false);
            storeObj.close();

        } catch (MessagingException exp) {
            throw new EmailException(exp.getMessage());
        } catch (Exception exp) {
            throw new EmailException(exp.getMessage());
        }
        return recievedEmails;
    }
}
