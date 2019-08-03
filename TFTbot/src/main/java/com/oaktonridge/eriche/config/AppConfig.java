package com.oaktonridge.eriche.config;

import java.awt.AWTException;
import java.awt.Robot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	@Bean
	public Robot createRobot() throws AWTException {
		return new Robot();
	}
}
