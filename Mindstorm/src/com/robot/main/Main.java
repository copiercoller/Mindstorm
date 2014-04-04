package com.robot.main;

import com.robot.deplacement.Deplacement;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;


public class Main {
	public static void main(String[] args) {
		  Button.waitForAnyPress();
		Deplacement d = new Deplacement();
		while(true) {
			d.random_search();
		}
	}
}
