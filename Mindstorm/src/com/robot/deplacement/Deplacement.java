package com.robot.deplacement;

import java.util.Enumeration;
import java.util.Hashtable;

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
	static DifferentialPilot pilot;
	TouchSensor bump = new TouchSensor(SensorPort.S1);
	Pince p = new Pince();

	public Deplacement() {
		pilot = new DifferentialPilot(2.25f, 5.5f, Motor.A, Motor.C);
		x = 100;
		y = 30;
		angle = 90;
	}

	public void random_search() {
		pilot.setRotateSpeed(45);
		pilot.rotate(-90);
		pilot.rotate(180, true);

		Hashtable<Float, Integer> val_sonar = new Hashtable<Float, Integer>();
		while (pilot.isMoving()) {
			Delay.msDelay(50);
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
		min_key -= 90;
		min += 10;
		System.out.println(min);
		System.out.println(min_key);
		System.out.println(x + Math.cos(min_key * Math.PI / 180) * min);
		System.out.println(y + Math.sin(min_key * Math.PI / 180) * min);
		pilot.rotate(-90);
		move_palet(min,min_key);
		// pilot.rotate(min_key);
		// move (min);

	}

	public void tourner(int angle) {
		Motor.A.rotate(angle);
		Motor.C.rotate(-angle);
	}

	public void move_enbut() {
		double d = (Math.sqrt((100 - x) * (100 - x) + (280 - y) * 280 - y));
		pilot.rotate(Math.acos( (100 - x)/d) * 180.0 / Math.PI - angle);
		angle=(float) (Math.acos( (100 - x)/d) * 180.0 / Math.PI);
		move(d);
		p.lacher();
	}

	  public void move_palet(double d,float t)
	  {
		  //code temp va jusqu'au palet
		  pilot.rotate(t);
		  angle+=t;
		//Tant qu'on est mouvement
		move(d);
			/* On garde en mémoire la distance calculée au debut, 
				On compare avec la nouvelle distance du même palet,
				Si la nouvelle distance est superieure à l'anciene,
				on refait une autre recherche
			*/
			double old_d = d;
			if (old_d >= u.getDistance()) {
				d = u.getDistance();
			} else {
				random_search();
			}
		/*
		 * faire moitié distance avec minimun retenter de reperer la cible si
		 * oui relancer recherche après s'etre replacer dans la bonne direction
		 * si non repasser en détection aléatoire
		 */
	}

	public void move(double x) {
		pilot.travel(x, true);
		while (pilot.isMoving()) {
			/*
			 * refermer pince petite musique passer en retour maison
			 */
			if(bump.isPressed() && !p.getpalet()) {
	        	pilot.stop();
				p.prendre();

	        }
	        	/* refermer pince 
    			petite musique
    			passer en retour maison */ ;
	        //else if  detection ligne
	        	//mise a jour position
	      }
	      System.out.println(Math.cos(angle*Math.PI/180.0)*pilot.getMovement().getDistanceTraveled());
	      x+=Math.cos(angle*Math.PI/180.0)*pilot.getMovement().getDistanceTraveled();
	      y+=Math.sin(angle*Math.PI/180.0)*pilot.getMovement().getDistanceTraveled();
	      
			 System.out.println(x);
			 System.out.println(y);
	      if (p.getpalet()) move_enbut();
	    }
}
