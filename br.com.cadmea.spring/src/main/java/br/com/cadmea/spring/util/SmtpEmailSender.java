package br.com.cadmea.spring.util;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Lazy
@Component("br.com.cadmea.spring.util.SmtpEmailSender")
@ConditionalOnClass(JavaMailSender.class)
public class SmtpEmailSender {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Lazy
  @Autowired
  private JavaMailSender mailSender;
  
  @Autowired
  private VelocityEngine velocityEngine;

  public void send(String to, String subject, Map<String, Object> msg) {
    try {
      log.info("{begin == sending email}");
      TemplateEmail preparator = new TemplateEmail(to, subject, msg);
      preparator.preConfiguredMessage = mailSender.createMimeMessage();
      preparator.velocityEngine = velocityEngine;

      mailSender.send(preparator);
      log.info("{end == sending email}");
    } catch (Exception e) {
      log.error("error when send an email", e);
    }
  }

  
}

class TemplateEmail implements MimeMessagePreparator {

  String to;
  String subject;
  Map<String, Object> msg;

  MimeMessage preConfiguredMessage;

  VelocityEngine velocityEngine;

  TemplateEmail(String _to, String _subject, Map<String, Object> _msg) {
    this.to = _to;
    this.subject = _subject;
    this.msg = _msg;
  }

  @Override
  public void prepare(MimeMessage mimeMessage) throws Exception {
    
    String text = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine,
        "template-email.vm", "UTF-8", this.msg);
    
    MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text, true);
  }

}