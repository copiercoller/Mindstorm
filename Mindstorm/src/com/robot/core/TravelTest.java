package com.robot.core;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;

/**
 * Robot that stops if it hits something before it completes its travel.
 */
public class TravelTest {
	DifferentialPilot pilot;
	TouchSensor bump = new TouchSensor(SensorPort.S1);

	public void go() {
		pilot.travel(2000, true);
		while (pilot.isMoving()) {
			if (bump.isPressed() || Button.LEFT.isDown()
					|| Button.RIGHT.isDown() || Button.ENTER.isDown())
				pilot.stop();
		}
		System.out.println(" " + pilot.getMovement().getDistanceTraveled());
		// Button.waitForAnyPress();
	}

	public void left() {
		Motor.A.rotate(720);
		Motor.C.rotate(-720);
	}

	void right() {
		Motor.A.rotate(-720);
		Motor.C.rotate(720);
	}

	public static void main(String[] args) {
		Button.waitForAnyPress();
		TravelTest traveler = new TravelTest();
		traveler.pilot = new DifferentialPilot(2.25f, 5.5f, Motor.A, Motor.C);
		while (!Button.ENTER.isDown()) {
			if (Button.LEFT.isDown()) {
				traveler.left();
			} else if (Button.RIGHT.isDown()) {
				traveler.right();
			} else {
				traveler.go();
			}
		}

		Motor.B.rotate(-360);
	}
}