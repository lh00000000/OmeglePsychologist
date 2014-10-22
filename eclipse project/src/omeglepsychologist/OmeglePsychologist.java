package omeglepsychologist;

import org.nikki.omegle.Omegle;
import org.nikki.omegle.core.OmegleException;
import org.nikki.omegle.core.OmegleMode;
import org.nikki.omegle.core.OmegleSession;

import codeanticode.eliza.Eliza;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.opengl.PGraphics2D;


public class OmeglePsychologist extends PApplet {

//	work constants
//	public static final int pauseBeforeSaying = 40;
//	public static final int icebreakerPause = 80;
//	public static final int pauseSD = 3;
//	public static final int awkwardSilenceLimit = 250;
//	
	
	//mbp constants
	public static final int pauseBeforeSaying = 100;
	public static final int icebreakerPause = 400;
	public static final int pauseSD = 10;
	public static final int awkwardSilenceLimit = 600;
	
	
	
	Omegle omegle = new Omegle();
	Eliza eliza;
	OmegleSession session;
	
	public static String andTheySaid = "";
	public static String soISaid = "";

	public static boolean strangerWasLastToSpeak = false;
	public static int countdowntoResponse = 0;
	public static int deadsilence = 0;
	public static boolean alone = true;
	public static boolean noPartner = true;
	public static boolean nothingSaidYet = true;


	public static String theyDisplay = "";
	public static String meDisplay = "";

	public static int width = 1000;
	public static int height = 650;
	

	PGraphics pg;

	PFont font; 

	public void setup() {
		font = loadFont("BookAntiqua-26.vlw");

		size(width,height);
		pg = createGraphics(width, height);
		eliza = new Eliza(this);
		newSession();

		textFont(font);
		textSize(24);
		textAlign(CENTER,CENTER);
		frameRate(60);


	}


	public void newSession() {
		deadsilence = 0;
		alone = false;
		noPartner = true;
		System.out.println("...Opening session...");

		try {
			session = omegle.openSession(OmegleMode.NORMAL, new ElizaOmegleEvent(this));
		} catch (OmegleException e) {
			e.printStackTrace();
		}

	}


	public void draw() {
		background(255);
		countdowntoResponse--;
		if (countdowntoResponse <= 0 && strangerWasLastToSpeak && !noPartner && deadsilence > 0) {
			if (strangerWasLastToSpeak || nothingSaidYet){
				think();
				speak();
			}

		} 

		if (alone) {
			deadsilence = 0;
			startover();
		} 
		if (!noPartner){		
			deadsilence ++;
		}

		if (deadsilence > awkwardSilenceLimit && !noPartner) {
			alone = true;
			theyDisplay = "";
			meDisplay = "";
			System.out.println("...Timeout. Leaving Session...");
			deadsilence = 0;
		}

		display();

	}


	private void display() {
//		pg.clear();
//		pg.beginDraw();
//
//		pg.textFont(font);
//		pg.textSize(22);
//		pg.textAlign(CENTER,CENTER);
//
//		pg.fill(218);
//		pg.text(theyDisplay.toUpperCase(), width/2, height/4);
//
//		pg.fill(248);
//		pg.text(meDisplay.toUpperCase(), width/2, 3*height/4);
//
//		pg.endDraw();
//
//		pg.filter(BLUR);

		fill(218);
		noStroke();
		rect(0, 0, width, height/2);

		fill(8);
		noStroke();
		rect(0, height/2, width, height/2);

//		image(pg,0,0);

		fill(0);
		text(theyDisplay.toUpperCase(), width/2, height/4);

		fill(248);
		text(meDisplay.toUpperCase(), width/2, 3*height/4);
	}


	private void startover() {
		try {
			session.disconnect();
		} catch (OmegleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newSession();
	}


	private void speak() {
		try {
			session.send(soISaid, true);
		} catch (OmegleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	void think() {
		andTheySaid = andTheySaid.toLowerCase();
		if (andTheySaid.equals("hi") || 
				andTheySaid.equals("hello") || 
				andTheySaid.equals("hii") || 
				andTheySaid.equals("hola") ||  
				andTheySaid.equals("heyy") || 
				andTheySaid.equals("hey")) {
			soISaid = "hello " + eliza.processInput("Hello");
			//		} else if (andTheySaid.equals("m")) {
			//			soISaid = "f";
		} else if (nothingSaidYet) {
			soISaid = "hi";
		} else {

			soISaid = eliza.processInput(andTheySaid);
		}

		soISaid = soISaid.toLowerCase();
	}


	public void keyPressed() {
		switch (key) {
		case '1':
			try {
				session.send("hi", true);
			} catch (OmegleException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			break;
		case '2':
			try {
				session.send(":(", true);
			} catch (OmegleException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			break;
		case 'q':
			try {
				session.disconnect();
				newSession();
			} catch (OmegleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case 's':
			saveFrame("#####.jpg");
			break;
		default:
			break;
		}
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] { omeglepsychologist.OmeglePsychologist.class.getName() });
	}
}
