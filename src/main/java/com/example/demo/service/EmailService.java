package com.example.demo.service;

import java.util.HashMap;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;
	
	HashMap<String, Integer> otpvalidation = new HashMap<>();

	public void sendMail(String mailTo, String subject, String url) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(mailTo);
		message.setSubject(subject);
		message.setText(url + "     you can also use otp    " + generateOTP());
		otpvalidation.put(mailTo, generateOTP());
		mailSender.send(message);
	}

	public Integer generateOTP() {
		Random random = new Random();
		int otp = random.nextInt(900000);
		return otp;
	}

}
