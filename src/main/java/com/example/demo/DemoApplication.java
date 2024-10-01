package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * Spring main class
 *
 * @author Vyom Gangwar
 * @since 23-Sept-2024
 */
@SpringBootApplication
public class DemoApplication implements CommandLineRunner
{
	public static void main(String... args)
	{
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception
	{

	}
}
