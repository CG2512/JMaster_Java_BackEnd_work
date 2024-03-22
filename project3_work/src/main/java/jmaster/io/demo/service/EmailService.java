package jmaster.io.demo.service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jmaster.io.demo.dto.BillItemDTO;
import jmaster.io.demo.entity.Bill;

@Service
public class EmailService {

	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	SpringTemplateEngine templateEngine;
	

	public void testEmail() {
		String to = "send@gmail.com";
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

		Context ctx = new Context();
		ctx.setVariable("name", name);
		String body = templateEngine.process("email/hpbd.html", ctx);

		sendEmail(to, subject, body);
	}

	public void sendEmail(String to, String subject, String body) {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());

		try {
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);
			helper.setFrom("throw2512@gmail.com");

			javaMailSender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void billEmail(String username,String userEmail,List<BillItemDTO> billItems) {
		
		String subject = "New bill for " + username;

		Context ctx = new Context();
		ctx.setVariable("billItems", billItems);
		String body = templateEngine.process("email/NewBill.html", ctx);
		
		sendEmail(userEmail, subject, body);

	}

	public void sendNewBill(List<Bill> bills) {
		String subject = "New bills in the last 5 minutes";
		
		Context ctx = new Context();
		ctx.setVariable("bills", bills);
		String body = templateEngine.process("email/last5minBills.html", ctx);
		
		sendEmail("throw2512@gmail.com", subject, body);
	}

	public void sendPassword(String email, String password) {
		// TODO Auto-generated method stub
		String subject="Your password has been updated";
		String body="Your new password is "+ password;
		
		sendEmail(email,subject,body);
	}

	
}
