package br.com.cadmea.spring.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

@Component
@ConditionalOnClass(JavaMailSender.class)
public class SmtpEmailSender {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Lazy
    @Autowired
    private JavaMailSender mailSender;

    public void send(final String to, final String subject, final Map<String, Object> msg,
                     final Locale locale) {
        try {
            log.info("{begin == sending email}");
            final TemplateEmail preparator = new TemplateEmail(to, subject, msg);
            preparator.preConfiguredMessage = mailSender.createMimeMessage();
            //preparator.velocityEngine = velocityEngine;

            mailSender.send(preparator);
            log.info("{end == sending email}");
        } catch (final Exception e) {
            log.error("error when send an email", e);
        }
    }

    public void send(final SimpleMailMessage simpleMessage) {
        mailSender.send(simpleMessage);
    }

}

class TemplateEmail implements MimeMessagePreparator {

    String to;
    String subject;
    Map<String, Object> msg;

    MimeMessage preConfiguredMessage;

    //VelocityEngine velocityEngine;

    TemplateEmail(final String _to, final String _subject, final Map<String, Object> _msg) {
        to = _to;
        subject = _subject;
        msg = _msg;
    }

    @Override
    public void prepare(final MimeMessage mimeMessage) throws Exception {

        //String text = VelocityEngineUtils.mergeTemplateIntoString(null, "template-email.vm", "UTF-8", this.msg);

        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
        message.setTo(to);
        message.setSentDate(new Date());
        message.setFrom(new InternetAddress("gilbertopsantosjr@gmail.com"));
        message.setSubject(subject);
        message.setText("", true);
    }

}