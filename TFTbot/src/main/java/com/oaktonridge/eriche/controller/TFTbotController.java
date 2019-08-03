package com.oaktonridge.eriche.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oaktonridge.eriche.model.BotStatus;
import com.oaktonridge.eriche.service.TFTBot;

@RestController
public class TFTbotController {
	final static Logger logger = LoggerFactory.getLogger(TFTbotController.class);

	@Autowired
	TFTBot t;
	static int i = 0;

	@GetMapping("/stop")
	public BotStatus stop() {
		logger.info("Stopping Bot");
		t.stop();
		return new BotStatus(false);
	}

	@GetMapping(value = "/start", produces = "application/json; charset=UTF-8")
	public BotStatus start(@RequestParam(name = "name", required = false, defaultValue = "wildnoble.txt") String name) {
		logger.info("Starting Bot");
		try {
			t.run(name);
		} catch (Exception e) {
			logger.error("System Aborted: " + e.getMessage(), e);
			return new BotStatus(false);
		}

		return new BotStatus(true);
	}

	@GetMapping("/monitor")
	public String monitor() {
		logger.info("Starting monitor");
		return "/images/upload.jpg";
	}
}
