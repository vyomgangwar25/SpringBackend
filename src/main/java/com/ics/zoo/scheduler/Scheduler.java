package com.ics.zoo.scheduler;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
 
import com.ics.zoo.repository.TokenRepository;
import com.ics.zoo.util.JwtUtil;

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

	@Autowired
	private JwtUtil jwtUtil;

	@Scheduled(fixedDelay = 2, timeUnit = TimeUnit.HOURS)
	public void scheduleTask() {
		try {
			repository.findByIsvalid(false).stream().filter(t -> jwtUtil.isTokenExpired(t.getToken()))
					.forEach(expiredToken -> repository.deleteById(expiredToken.getId()));
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}

//		List<TokenCheck> list = repository.findByIsvalid(false);
//
//		for (TokenCheck tokenCheckObj : list) {
//
//			boolean abc = jwtUtil.isTokenExpired(tokenCheckObj.getToken());
		/*
		 * LocalTime creationTime = tokenCheckObj.getCreatedAt(); LocalTime
		 * expirationTime = creationTime.plusHours(2); LocalTime localtime =
		 * LocalTime.now().withNano(0);
		 * 
		 * System.out.println(localtime + "localTime"); System.out.println(creationTime
		 * + "creationTime"); System.out.println(expirationTime + "expirationTime");
		 * 
		 * this willl execute if localtime > expirationTime.
		 * 
		 * if (localtime.isAfter(expirationTime)) {
		 * repository.deleteById(tokenCheckObj.getId()); }
		 */
//			if (abc) {
//				repository.deleteById(tokenCheckObj.getId());
//			}

	}

}
