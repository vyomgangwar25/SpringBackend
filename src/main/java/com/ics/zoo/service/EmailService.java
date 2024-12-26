package com.ics.zoo.service;

import java.util.HashMap;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Email Service
 * @author Vyom Gangwar
 * */

@Service
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;	
	HashMap<String, Integer> otpvalidation = new HashMap<>();
	
	/**
	 * this method send the email to user  
	 * @param mailTo,subject,key
	 * 
	 * */

	public void sendMail(String mailTo, String subject, String key) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(mailTo);
		message.setSubject(subject);
		message.setText(key + "     you can also use otp    " + generateOTP());
		otpvalidation.put(mailTo, generateOTP());
		mailSender.send(message);
	}

	
	/** 
	 * this method generates the random no 
	 * */
	public Integer generateOTP() {
		Random random = new Random();
		int otp = random.nextInt(900000);
		return otp;
	}

}
