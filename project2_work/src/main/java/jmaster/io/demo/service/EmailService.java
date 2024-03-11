package jmaster.io.demo.service;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	SpringTemplateEngine templateEngine;


	public void testEmail() {
		String to = "throw2512@gmail.com";
		String subject = "Testing Java mail";
		String body = "<h1>Testing if Java mail works</h1>";

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());

		try {
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);
			helper.setFrom("");

			javaMailSender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendBirthdayEmail(String to, String name) {
		String subject = "Happy birthday " + name;
		
		Context ctx= new Context();
		ctx.setVariable("name", name);
		String body = templateEngine.process("email/hpbd.html", ctx);
		
		sendEmail(to,subject,body);
	}

	public void sendEmail(String to, String subject, String body) {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());

		try {
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);
			helper.setFrom("");

			javaMailSender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
