package com.ics.zoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 
 * Spring main class
 *
 * @author Vyom Gangwar
 * @since 23-Sept-2024
 */
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class })
@EnableCaching
public class ZooApplication
{
	public static void main(String... args)
	{
		SpringApplication.run(ZooApplication.class, args);
	}
}
