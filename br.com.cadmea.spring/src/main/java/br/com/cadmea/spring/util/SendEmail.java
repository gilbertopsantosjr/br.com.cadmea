package br.com.cadmea.spring.util;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Component("br.com.cadmea.spring.util.SendEmail")
public class SendEmail {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private SimpleMailMessage preConfiguredMessage;
	
	@Autowired
	private VelocityEngine velocityEngine;

	public void send(String to, String subject, Map<String, Object> msg) {
		try {
			TemplateEmail preparator = new TemplateEmail(to, subject, msg);
			preparator.preConfiguredMessage  = preConfiguredMessage;
			preparator.velocityEngine = velocityEngine;
			
			mailSender.send(preparator);
		} catch (Exception e) {
			log.error("error when send an email", e);
		}
	}
}

class TemplateEmail implements MimeMessagePreparator {
	
	String to;
	String subject;
	Map<String, Object> msg;
	
	SimpleMailMessage preConfiguredMessage;
	
	VelocityEngine velocityEngine;
	
	TemplateEmail(String _to, String _subject, Map<String, Object> _msg){
		this.to = _to;
		this.subject = _subject;
		this.msg = _msg;
	}

	@Override
	public void prepare(MimeMessage mimeMessage) throws Exception {
		
		 SimpleMailMessage mailMessage = new SimpleMailMessage(preConfiguredMessage);
		  
		 MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
         message.setTo(to);
         message.setFrom(mailMessage.getFrom()); 
         message.setSubject(subject);
         
         String text = VelocityEngineUtils.mergeTemplateIntoString(
            velocityEngine, "matricula-confirmation.vm", msg);
         message.setText(text, true);
	}
	
}