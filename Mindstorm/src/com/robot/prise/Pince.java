package com.robot.prise;

import lejos.nxt.Motor;

public class Pince {
	boolean palet ;
	public Pince () {
		palet =false;
	}
	
	public void prendre() {
		Motor.B.rotate(-100);
		palet=true;
	}
	
	public void lacher() {
		Motor.B.rotate(100);
		palet=false;
	}
	
	public boolean getpalet()
	{
		return palet;
	}
}
