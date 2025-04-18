package com.master.oslyOnlineShoping.service;

import com.master.oslyOnlineShoping.model.AppConfig;
import com.master.oslyOnlineShoping.model.Config;
import com.master.oslyOnlineShoping.model.ModelMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;

@Service
public class ServiceMail {

    private final Logger logger = LoggerFactory.getLogger(ServiceMail.class);
    public ModelMessage sendMail(String toEmail, String code) {

        Yaml yaml = new Yaml();


        InputStream inputStream = ServiceMail.class
                .getClassLoader()
                .getResourceAsStream("emailConfig.yml");

        AppConfig appConfig = yaml.loadAs(inputStream, AppConfig.class);

        Config config = appConfig.getConfig();

        ModelMessage ms = new ModelMessage(false, "");
        String from = config.getEmail();

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2"); // ✅ Force TLS 1.2

        String username = config.getUsername();
        String password = config.getPassword();

        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Osly Verify Access Code");
            message.setText("Your Verification Code for Osly is: "+code);
            Transport.send(message);
            ms.setSuccess(true);
            logger.info("successfully send verification email: {}", toEmail);
        } catch (MessagingException e) {
            if (e.getMessage().equals("Invalid Addresses")) {
                ms.setMessage("Invalid email");
                logger.error("Invalid email: {} {}", e, toEmail);
            } else {
                ms.setMessage("Error Fatal sending verification email to EMAIL: ("+ toEmail +" )");
                logger.error("Issue sending verification email: {} {}", e, toEmail);
            }
        }
        return ms;
    }

}
