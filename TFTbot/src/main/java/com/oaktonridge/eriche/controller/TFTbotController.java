package com.oaktonridge.eriche.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oaktonridge.eriche.TFTBot;

@Controller
public class TFTbotController {
	@Autowired
	TFTBot t;

	@GetMapping("/stop")
	public String stop(Model model) {
		t.stop();
		return "stopped";
	}

	@GetMapping("/start")
	public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
			Model model) {
		model.addAttribute("name", name);

		try {
			t.run(name);
		} catch (Exception e) {
			System.out.println("System Aborted: " + e.getMessage());
			e.printStackTrace();
		}
		return "started";
	}

	@GetMapping("/monitor")
	public String monitor(Model model) {

		return "monitor";
	}
}
