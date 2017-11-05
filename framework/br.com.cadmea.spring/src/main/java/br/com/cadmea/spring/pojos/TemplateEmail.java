package br.com.cadmea.spring.pojos;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Map;

/**
 *
 */
public class TemplateEmail implements MimeMessagePreparator {

    private final String to;
    private final String subject;
    private final Map<String, Object> msg;
    private final UserAccess userAccess;

    MimeMessage preConfiguredMessage;

    //VelocityEngine velocityEngine;

    /**
     *
     */
    public TemplateEmail(final String _to, final String _subject, final UserAccess _userAccess, final Map<String, Object> _msg) {
        to = _to;
        subject = _subject;
        msg = _msg;
        userAccess = _userAccess;
    }

    /**
     *
     */
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
