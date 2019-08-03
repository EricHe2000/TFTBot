package com.oaktonridge.eriche.service;

import java.awt.Robot;
import java.awt.event.InputEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Item {
	private Robot robot;

	@Autowired
	public Item(Robot robot) {
		super();
		this.robot = robot;
	}

	public void item1() {
		robot.delay(100); // Click one second
		robot.mouseMove(288, 722); // step 1 // first item
		robot.delay(500); // Click one second
		robot.mousePress(InputEvent.BUTTON1_MASK); // step 2
		// robot.mousePress(InputEvent.BUTTON1_MASK); // step 2
		robot.delay(1000); // Click one second
		robot.mouseMove(907, 567); // step 3
		robot.mouseRelease(InputEvent.BUTTON1_MASK); // step 4
		robot.delay(100); // Click one second
	}

	public void item2() {
		robot.delay(100); // Click one second
		robot.mouseMove(333, 684); // step 1 // second item
		robot.delay(100); // Click one second
		robot.mousePress(InputEvent.BUTTON1_MASK); // step 2
		robot.delay(1000); // Click one second
		robot.mouseMove(907, 567); // step 3
		robot.mouseRelease(InputEvent.BUTTON1_MASK); // step 4
		robot.delay(100); // Click one second
	}

	public void item3() {
		robot.delay(100); // Click one second
		robot.mouseMove(305, 664); // step 1 // second item
		robot.delay(100); // Click one second
		robot.mousePress(InputEvent.BUTTON1_MASK); // step 2
		robot.delay(1000); // Click one second
		robot.mouseMove(907, 567); // step 3
		robot.mouseRelease(InputEvent.BUTTON1_MASK); // step 4
		robot.delay(100); // Click one second
	}

	public void item4() {
		robot.delay(100); // Click one second
		robot.mouseMove(347, 629); // step 1 // second item
		robot.delay(100); // Click one second
		robot.mousePress(InputEvent.BUTTON1_MASK); // step 2
		robot.delay(1000); // Click one second
		robot.mouseMove(974, 456); // step 3
		robot.mouseRelease(InputEvent.BUTTON1_MASK); // step 4
		robot.delay(100); // Click one second
	}
}
