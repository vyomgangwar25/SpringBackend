package com.ics.zoo.scheduler;

 
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ics.zoo.entities.TokenCheck;
import com.ics.zoo.repository.TokenRepository;

@Component
public class Scheduler {
	@Autowired
	private TokenRepository repository;

	@Scheduled(fixedDelay = 2, timeUnit = TimeUnit.HOURS)
	public void scheduleTask() {

		for (TokenCheck ab : repository.findByIsvalid(false))
			repository.deleteById(ab.getId());
	}

}
