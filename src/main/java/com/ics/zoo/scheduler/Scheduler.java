package com.ics.zoo.scheduler;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ics.zoo.entities.TokenCheck;
import com.ics.zoo.repository.TokenRepository;
//import com.ics.zoo.util.JwtUtil;

@Component
public class Scheduler {
	@Autowired
	private TokenRepository repository;

//	@Autowired
//	private JwtUtil jwtUtil;

	@Scheduled(fixedDelay = 2, timeUnit = TimeUnit.HOURS)
	public void scheduleTask() {

		for (TokenCheck tokenCheckObj : repository.findByIsvalid(false)) {

			// boolean abc = jwtUtil.isTokenExpired(ab.getToken());
			LocalTime creationTime = tokenCheckObj.getCreatedAt();
			LocalTime expirationTime = creationTime.plusHours(2);
			LocalTime localtime = LocalTime.now().withNano(0);
//
//			System.out.println(localtime + "localTime");
//			System.out.println(creationTime + "creationTime");
//			System.out.println(expirationTime + "expirationTime");

			/**
			 * this willl execute if localtime > expirationTime.
			 */
			if (localtime.isAfter(expirationTime)) {
				repository.deleteById(tokenCheckObj.getId());
			}

		}

	}

}
