package com.addonis.demo.send;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * EmailSend is a class used for sending emails when users are registering.
 */
public class EmailSend {
    public static void send_2(String emailRecipient, String userName) {
        final String username = "addonis.team@gmail.com";
        final String password = "addonis!";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("addonis.team@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(emailRecipient)
            );
            message.setSubject("Welcome To Addonis");
            message.setText("Dear " + userName
                    + "\n\n Welcome to our humble website! We look forward to you having a wonderful experience!"
                    + "\n\n The Addonis Team!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
