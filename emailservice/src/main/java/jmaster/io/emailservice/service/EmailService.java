package jmaster.io.emailservice.service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jmaster.io.emailservice.dto.BillDTO;
import jmaster.io.emailservice.dto.BillItemDTO;
import jmaster.io.emailservice.dto.MessageDTO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {
	@Value("${mail.sendFrom}")
	private String sendFrom;

	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	SpringTemplateEngine templateEngine;

	public void sendEmail(String to, String subject, String body) {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());

		try {
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);
			helper.setFrom(sendFrom);

			javaMailSender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void billEmail(String userEmail, List<BillItemDTO> billItems) {

		String subject = "New bill ";

		Context ctx = new Context();
		ctx.setVariable("billItems", billItems);
		String body = templateEngine.process("email/NewBill.html", ctx);

		sendEmail(userEmail, subject, body);

	}

	public void sendNewBill(List<BillDTO> bills) {
		String subject = "New bills in the last 5 minutes";

		Context ctx = new Context();
		ctx.setVariable("bills", bills);
		String body = templateEngine.process("email/last5minBills.html", ctx);

		sendEmail("throw2512@gmail.com", subject, body);
	}

	public void sendPassword(String email, String password) {
		// TODO Auto-generated method stub
		String subject = "Your password has been updated";
		String body = "Your new password is " + password;

		sendEmail(email, subject, body);
	}

	@KafkaListener(id = "userBillGroup", topics = "userbill")
	public void listen(MessageDTO bill) {
		log.info("Send to: " + bill.getSendTo());
		billEmail(bill.getSendTo(), (List<BillItemDTO>) bill.getData());
	}

	@KafkaListener(id = "billScanGroup", topics = "billScan")
	public void listenBillScan(MessageDTO bills) {
		log.info("Sending new bills");
		sendNewBill((List<BillDTO>) bills.getData());
		;
	}
	@KafkaListener(id = "passwordGroup", topics = "password")
	public void listenPassword(MessageDTO password) {
		log.info("Sending new password");
		sendPassword(password.getSendTo(),(String)password.getData());
		;
	}
}
