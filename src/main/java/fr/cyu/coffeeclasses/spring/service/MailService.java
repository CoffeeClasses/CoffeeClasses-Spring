package fr.cyu.coffeeclasses.spring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import fr.cyu.coffeeclasses.spring.model.user.User;

@Service
public class MailService {
	private static final Logger logger = LoggerFactory.getLogger(MailService.class);

	@Autowired
	private MailSender mailSender;

	public void sendMail(User dest, String subject, String body) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(dest.getEmail());
			message.setSubject(subject);
			message.setText(body);
			mailSender.send(message);
		} catch (Exception e) {
			logger.error("Error while sending email: {}", e.getMessage());
		}
	}
}