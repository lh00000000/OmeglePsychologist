package omegletherapist;

import processing.core.PApplet;


public class OmegleTherapist extends PApplet {
	int countToElizaToSpeakFirst;
	int countToElizaResponding;
	int countToElizaWaitingForAnswer;
	
	
	
	public void setup() {
		
	}

	public void draw() {
		background(255);
		
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { omegletherapist.OmegleTherapist.class.getName() });
	}
}
