package kz.edy.astanait.product.service.security;

import com.sun.mail.smtp.SMTPTransport;
import kz.edy.astanait.product.enviroment.EmailEnvironmentBuilder;
import kz.edy.astanait.product.exception.domain.EmailTokenIsNotValidException;
import kz.edy.astanait.product.exception.domain.UserAlreadyVerifiedException;
import kz.edy.astanait.product.exception.domain.UserNotFoundException;
import kz.edy.astanait.product.model.User;
import kz.edy.astanait.product.model.security.RegistrationEmailVerification;
import kz.edy.astanait.product.model.security.ResetEmailVerification;
import kz.edy.astanait.product.repository.UserRepository;
import kz.edy.astanait.product.repository.security.RegistrationEmailVerificationRepository;
import kz.edy.astanait.product.repository.security.ResetEmailVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.ZoneId;
import java.util.*;

import static javax.mail.Message.RecipientType.TO;

@Service
public class EmailService {

    private final RegistrationEmailVerificationRepository registrationEmailVerificationRepository;
    private final ResetEmailVerificationRepository resetEmailVerificationRepository;
    private final UserRepository userRepository;
    private final EmailEnvironmentBuilder emailEnvironmentBuilder;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public EmailService(RegistrationEmailVerificationRepository registrationEmailVerificationRepository, ResetEmailVerificationRepository resetEmailVerificationRepository, UserRepository userRepository, EmailEnvironmentBuilder emailEnvironmentBuilder, BCryptPasswordEncoder encoder) {
        this.registrationEmailVerificationRepository = registrationEmailVerificationRepository;
        this.resetEmailVerificationRepository = resetEmailVerificationRepository;
        this.userRepository = userRepository;
        this.emailEnvironmentBuilder = emailEnvironmentBuilder;
        this.encoder = encoder;
    }

    public void sendEmailRegistration(String email) throws MessagingException {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            if (!user.get().isActive()) {
                Message message = createEmailRegistration(user.get());
                SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(emailEnvironmentBuilder.SIMPLE_MAIL_TRANSFER_PROTOCOL);
                smtpTransport.connect(emailEnvironmentBuilder.GMAIL_SMTP_SERVER, emailEnvironmentBuilder.USERNAME, emailEnvironmentBuilder.PASSWORD);
                smtpTransport.sendMessage(message, message.getAllRecipients());
                smtpTransport.close();
            } else {
                throw new UserAlreadyVerifiedException("User already verified!");
            }
        } else {
            throw new UserNotFoundException("User not found by email: " + email);
        }
    }

    private Message createEmailRegistration(User user) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(emailEnvironmentBuilder.FROM_EMAIL));
        message.setRecipients(TO, InternetAddress.parse(user.getEmail(), false));
        message.setSubject(emailEnvironmentBuilder.EMAIL_SUBJECT);
        message.setText("Hello, " + user.getName() +
                "!\n\nYour verification link to activate your account: " + createVerificationLinkRegistration(user));
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

    private Session getEmailSession() {
        Properties properties = System.getProperties();
        properties.put(emailEnvironmentBuilder.SMTP_HOST, emailEnvironmentBuilder.GMAIL_SMTP_SERVER);
        properties.put(emailEnvironmentBuilder.SMTP_AUTH, true);
        properties.put(emailEnvironmentBuilder.SMTP_PORT, emailEnvironmentBuilder.DEFAULT_PORT);
        properties.put(emailEnvironmentBuilder.SMTP_STARTTLS_ENABLE, true);
        properties.put(emailEnvironmentBuilder.SMTP_STARTTLS_REQUIRED, true);
        return Session.getInstance(properties, null);
    }

    private String createVerificationLinkRegistration(User user) {
        String token = UUID.randomUUID().toString();

        while (!uniqueTokenRegistration(token)) {
            token = UUID.randomUUID().toString();
        }

        RegistrationEmailVerification registrationEmailVerification = new RegistrationEmailVerification();
        registrationEmailVerification.setToken(token);
        registrationEmailVerification.setUser(user);
        registrationEmailVerificationRepository.save(registrationEmailVerification);
        return emailEnvironmentBuilder.DOMAIN + "abitur/api/v1/auth/user-verification?token=" + registrationEmailVerification.getToken();
    }

    private boolean uniqueTokenRegistration (String token) {
       Optional<RegistrationEmailVerification> registrationEmailVerification =
               registrationEmailVerificationRepository.findByToken(token);
        return registrationEmailVerification.isEmpty();
    }

    public RegistrationEmailVerification getVerificationTokenByTokenRegistration(String token) {

        List<RegistrationEmailVerification> registrationEmailVerificationCheck = registrationEmailVerificationRepository.findAll();
        for(RegistrationEmailVerification registrationEmailVerification : registrationEmailVerificationCheck) {
           if (registrationEmailVerification.getCreatedDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() + 7200000 <= System.currentTimeMillis()) {
               registrationEmailVerificationRepository.delete(registrationEmailVerification);
           }
        }

        Optional<RegistrationEmailVerification> verificationToken = registrationEmailVerificationRepository.findByToken(token);
        if (verificationToken.isPresent()) {
            return verificationToken.get();
        }
        throw new EmailTokenIsNotValidException("Token is not valid");
    }

    public void deleteUsedTokenRegistration(RegistrationEmailVerification registrationEmailVerification)  {
        registrationEmailVerificationRepository.delete(registrationEmailVerification);
    }


   ///RESET EMAIL SERVICE

    public void sendEmailReset(String email, String password) throws MessagingException {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
                Message message = createEmailReset(user.get(), password);
                SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(emailEnvironmentBuilder.SIMPLE_MAIL_TRANSFER_PROTOCOL);
                smtpTransport.connect(emailEnvironmentBuilder.GMAIL_SMTP_SERVER, emailEnvironmentBuilder.USERNAME, emailEnvironmentBuilder.PASSWORD);
                smtpTransport.sendMessage(message, message.getAllRecipients());
                smtpTransport.close();
        } else {
            throw new UserNotFoundException("User not found by email: " + email);
        }
    }

    private Message createEmailReset(User user, String password) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(emailEnvironmentBuilder.FROM_EMAIL));
        message.setRecipients(TO, InternetAddress.parse(user.getEmail(), false));
        message.setSubject(emailEnvironmentBuilder.EMAIL_SUBJECT);
        message.setText("Hello, " + user.getName() +
                "!\n\nYour verification link to reset password: " + createVerificationLinkReset(user, password));
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

    private String createVerificationLinkReset(User user, String password) {
        String token = UUID.randomUUID().toString();

        while (!uniqueTokenReset(token)) {
            token = UUID.randomUUID().toString();
        }

        ResetEmailVerification resetEmailVerification = new ResetEmailVerification();
        resetEmailVerification.setToken(token);
        resetEmailVerification.setUser(user);
        resetEmailVerification.setPassword(encoder.encode(password));
        resetEmailVerificationRepository.save(resetEmailVerification);
        return emailEnvironmentBuilder.DOMAIN + "abitur/api/v1/account-info/reset-password?token=" + resetEmailVerification.getToken();
    }

    private boolean uniqueTokenReset (String token) {
        Optional<ResetEmailVerification> resetEmailVerification =
                resetEmailVerificationRepository.findByToken(token);
        return resetEmailVerification.isEmpty();
    }

    public ResetEmailVerification getVerificationTokenByTokenReset(String token) {

        List<ResetEmailVerification> resetEmailVerifications = resetEmailVerificationRepository.findAll();
        for(ResetEmailVerification resetEmailVerification : resetEmailVerifications) {
            if (resetEmailVerification.getCreatedDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() + 7200000 <= System.currentTimeMillis()) {
                resetEmailVerificationRepository.delete(resetEmailVerification);
            }
        }

        Optional<ResetEmailVerification> resetEmailVerification = resetEmailVerificationRepository.findByToken(token);
        if (resetEmailVerification.isPresent()) {
            return resetEmailVerification.get();
        }
        throw new EmailTokenIsNotValidException("Token is not valid");
    }

    public void deleteUsedTokenReset(ResetEmailVerification resetEmailVerification)  {
        resetEmailVerificationRepository.delete(resetEmailVerification);
    }


    ///USER MESSAGE EMAIL SERVICE


    public void sendEmailMessageToUser(String email, String messageToUser) throws MessagingException {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            Message message = createEmailMessageToUser(user.get(), messageToUser);
            SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(emailEnvironmentBuilder.SIMPLE_MAIL_TRANSFER_PROTOCOL);
            smtpTransport.connect(emailEnvironmentBuilder.GMAIL_SMTP_SERVER, emailEnvironmentBuilder.USERNAME, emailEnvironmentBuilder.PASSWORD);
            smtpTransport.sendMessage(message, message.getAllRecipients());
            smtpTransport.close();
        } else {
            throw new UserNotFoundException("User not found by email: " + email);
        }
    }


    private Message createEmailMessageToUser(User user, String messageToUser) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(emailEnvironmentBuilder.FROM_EMAIL));
        message.setRecipients(TO, InternetAddress.parse(user.getEmail(), false));
        message.setSubject("Message from AITU");
        message.setText(messageToUser);
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

}
