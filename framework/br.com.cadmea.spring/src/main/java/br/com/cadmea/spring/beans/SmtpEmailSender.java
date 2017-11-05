package br.com.cadmea.spring.beans;

import br.com.cadmea.spring.pojos.TemplateEmail;
import br.com.cadmea.spring.pojos.UserAccess;
import br.com.cadmea.spring.util.SpringFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
@ConditionalOnClass(JavaMailSender.class)
public class SmtpEmailSender {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Lazy
    @Autowired
    private JavaMailSender mailSender;

    @Lazy
    @Autowired
    private HttpServletRequest request;

    /**
     * @param to
     * @param subject
     * @param msg
     */
    public void send(final String to, final String subject, final Map<String, Object> msg) {
        try {
            log.info("{begin == sending email}");

            final UserAccess userAccess = SpringFunctions.getCurrentUser();

            final TemplateEmail preparator = new TemplateEmail(to, subject, userAccess, msg);
            preparator.prepare(mailSender.createMimeMessage());
            //preparator.velocityEngine = velocityEngine;

            mailSender.send(preparator);
            log.info("{end == sending email}");
        } catch (final Exception e) {
            log.error("error when send an email", e);
        }
    }

    /**
     * @param simpleMessage
     */
    public void send(final SimpleMailMessage simpleMessage) {
        mailSender.send(simpleMessage);
    }

}
