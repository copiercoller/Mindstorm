package com.robot.deplacement;

import java.util.Enumeration;
import java.util.Hashtable;

import com.robot.detecteurs.DetectionLigne;
import com.robot.main.Main;
import com.robot.prise.Pince;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

public class Deplacement {

	UltrasonicSensor u = new UltrasonicSensor(SensorPort.S4);
	float x;
	float y;
	float angle;
	public static DifferentialPilot pilot;
	TouchSensor bump = new TouchSensor(SensorPort.S1);
	Pince p = new Pince();
	DetectionLigne dl;

	public Deplacement() {
		pilot = new DifferentialPilot(2.25f, 5.5f, Motor.A, Motor.C);
		x = 100;
		y = 30;
		angle = 90;
		dl = new DetectionLigne(this);
	}

	public void random_search(float zone) {
		pilot.setRotateSpeed(45);
		pilot.rotate(-zone);
		pilot.rotate(zone * 2, true);

		Hashtable<Float, Integer> val_sonar = new Hashtable<Float, Integer>();
		while (pilot.isMoving()) {
			Delay.msDelay(30);
			val_sonar
					.put(pilot.getMovement().getAngleTurned(), u.getDistance());
		}

		// recherche min
		Integer min = null;
		Float min_key = null;
		Float key = null;
		Enumeration<Float> nb = val_sonar.keys();
		while (nb.hasMoreElements()) {
			key = nb.nextElement();
			if (min == null || min > val_sonar.get(key)) {
				min = val_sonar.get(key);
				min_key = key;
			}
		}
		// fin recherche min
		min_key -= zone;
		min += 10;

		pilot.rotate(-zone);
		move_palet(min, min_key);

	}

	public void tourner(int angle) {
		Motor.A.rotate(angle);
		Motor.C.rotate(-angle);
	}

	public void move_enbut() {
		double d = (Math.sqrt((100 - x) * (100 - x) + (280 - y) * 280 - y));
		pilot.rotate(Math.acos((100 - x) / d) * 180.0 / Math.PI - angle);
		angle = (float) (Math.acos((100 - x) / d) * 180.0 / Math.PI);
		move(d);
		lacher();
	}

	public void lacher() {
		p.lacher();
		move(-10);
		pilot.rotate(270 - angle);
		angle = 270;
	}

	public void move_palet(double d, float t) {
		// code temp va jusqu'au palet
		pilot.rotate(t);
		angle += t;
		// Tant qu'on est mouvement
		if (d > 20) {
			move(d / 2);
			random_search(10);
		} else {
			move(d);
		}

	}

	public void move(double x) {
		pilot.travel(x, true);
		dl.update();
		while (pilot.isMoving()) {
			/*
			 * refermer pince petite musique passer en retour maison
			 */
			if (bump.isPressed() && !p.getpalet()) {
				pilot.stop();
				p.prendre();

			}
			/*
			 * refermer pince petite musique passer en retour maison
			 */;
			// else if detection ligne
			// mise a jour position
		}
		System.out.println(Math.cos(angle * Math.PI / 180.0)
				* pilot.getMovement().getDistanceTraveled());
		x += Math.cos(angle * Math.PI / 180.0)
				* pilot.getMovement().getDistanceTraveled();
		y += Math.sin(angle * Math.PI / 180.0)
				* pilot.getMovement().getDistanceTraveled();

		System.out.println(x);
		System.out.println(y);
		if (p.getpalet())
			move_enbut();
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
