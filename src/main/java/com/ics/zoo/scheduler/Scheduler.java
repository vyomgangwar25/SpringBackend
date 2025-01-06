package com.ics.zoo.scheduler;

import java.time.LocalTime;
 
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import com.ics.zoo.repository.TokenRepository;

//import com.ics.zoo.util.JwtUtil;
/**
 * Scheduler
 * 
 * @author Vyom Gangwar
 **/

@Component
public class Scheduler {
	@Autowired
	private TokenRepository repository;

	@Scheduled(fixedDelay = 2, timeUnit = TimeUnit.HOURS)
	public void scheduleTask() {
 
		try {
			repository.findAll().stream().filter(t->LocalTime.now().withNano(0).isAfter(t.getCreatedAt().plusHours(2)))
			.forEach(expiredToken-> repository.deleteById(expiredToken.getId()));
		}
		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
		}
	 
		
//		List<TokenCheck> ll = repository.findAll();
//		for (TokenCheck tc : ll) {
//			LocalTime creationTime = tc.getCreatedAt();
//			LocalTime expirationTime = creationTime.plusHours(2);
//			
//			// System.out.println(localtime);
//			if (localtime.isAfter(expirationTime)) {
//				repository.deleteById(tc.getId());
//			}

		}

	}

 
