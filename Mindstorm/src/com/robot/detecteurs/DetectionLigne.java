package com.robot.detecteurs;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.ColorSensor.Color;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorHTSensor;

import com.robot.deplacement.Deplacement;

public class DetectionLigne {
	float x;
	float y;
	Deplacement d;
	final static int INTERVAL = 200; // milliseconds
	ColorSensor cmps = new ColorSensor(SensorPort.S2);

	public DetectionLigne(Deplacement d) {
		this.d = d;
		x = d.getX();
		y = d.getX();
	}

	public void update() {
		while (d.pilot.isMoving()) {
			switch (cmps.getColorID()) {
			case 1:
				if (x > 80 && x < 120) {
					x = 100;
				} else if (y > 100 && y < 140) {
					y = 120;
				}
				break;
			case 2:
				y = 180;
				break;

			case 3:
				y = 60;
				break;
			case 4:
				x = 150;
				break;
			case 5:
				x = 50;
				break;
			case 6:
				x = 200;
				break;
			}
		}
	}
}
