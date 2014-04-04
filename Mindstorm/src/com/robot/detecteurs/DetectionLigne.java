package com.robot.detecteurs;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.ColorSensor.Color;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorHTSensor;

import com.robot.deplacement.Deplacement;

public class DetectionLigne {
	ColorHTSensor cmps = new ColorHTSensor(SensorPort.S2);
	float x;
	float y;
	Deplacement d;
	String[] colorNames = {"Red", "Green", "Blue", "Yellow", "Black", "White"};
	String color = "Color";
	String r = "R";
	String g = "G";
	String b = "B";
	final static int INTERVAL = 200; // milliseconds
	
	public DetectionLigne(Deplacement d) {
		this.d = d;
		x = d.getX();
		y = d.getX();
	}
	
	public void update() {
		while(d.pilot.isMoving()) {
			LCD.clear();
			LCD.drawString(cmps.getVendorID(), 0, 0);
			LCD.drawString(cmps.getProductID(), 0, 1);
			LCD.drawString(cmps.getVersion(), 9, 1);
			LCD.drawString(color, 0, 3);
			LCD.drawInt(cmps.getColorID(),7,3);
			LCD.drawString(colorNames[cmps.getColorID()], 0, 4);
			LCD.drawString(r, 0, 5);
			LCD.drawInt(cmps.getRGBComponent(Color.RED),1,5);
			LCD.drawString(g, 5, 5);
			LCD.drawInt(cmps.getRGBComponent(Color.GREEN),6,5);
			LCD.drawString(b, 10, 5);
			LCD.drawInt(cmps.getRGBComponent(Color.BLUE),11,5);
			LCD.refresh();
			//Thread.sleep(INTERVAL);
		}
	}
}
