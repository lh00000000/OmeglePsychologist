package omeglepsychologist;

import org.nikki.omegle.core.OmegleSession;
import org.nikki.omegle.event.OmegleEventAdaptor;

import processing.core.PApplet;

public class ElizaOmegleEvent extends OmegleEventAdaptor{

	PApplet parent; 
	public ElizaOmegleEvent(PApplet parent) {
		this.parent = parent;
	}
	
	@Override
	public void chatWaiting(OmegleSession session) {
		System.out.println("...Waiting for chat...");
		resetDeadSilenceTimer();
		clearMeDisplay();
		setThemDisplay("...Waiting for chat...");
	}

	@Override
	public void chatConnected(OmegleSession session) {
		System.out.println("You are now talking to a random stranger!");
		setAlone(false);
		resetDeadSilenceTimer();
		setNoPartner(false);
		OmeglePsychologist.andTheySaid = "";
		OmeglePsychologist.soISaid = "";
		OmeglePsychologist.nothingSaidYet = true;
		OmeglePsychologist.countdowntoResponse = OmeglePsychologist.icebreakerPause;
		clearMeDisplay();
		setThemDisplay("You are now talking to a random stranger!");
	}

	@Override
	public void chatMessage(OmegleSession session, String message) {
		System.out.println("Stranger: " + message);
		OmeglePsychologist.nothingSaidYet = false;
		OmeglePsychologist.andTheySaid = message;
		OmeglePsychologist.strangerWasLastToSpeak = true;
		OmeglePsychologist.countdowntoResponse = (int) parent.randomGaussian()*OmeglePsychologist.pauseSD + OmeglePsychologist.pauseBeforeSaying;
		resetDeadSilenceTimer();
		setThemDisplay(message);
		clearMeDisplay();
	}

	@Override
	public void messageSent(OmegleSession session, String string) {
		System.out.println("You: " + string);
		OmeglePsychologist.nothingSaidYet = false;
		OmeglePsychologist.andTheySaid = "";
		OmeglePsychologist.strangerWasLastToSpeak = false;
		resetDeadSilenceTimer();
		setMeDisplay(string);
	}

	@Override
	public void strangerDisconnected(OmegleSession session) {
		System.out.println("Stranger disconnected, goodbye!");
		setAlone(true);
		setNoPartner(true);
		resetDeadSilenceTimer();
		setThemDisplay("Stranger disconnected, goodbye!");
		clearMeDisplay();
	}

	private void resetDeadSilenceTimer() {
		OmeglePsychologist.deadsilence = 0;
	}

	@Override
	public void omegleError(OmegleSession session, String string) {
		System.out.println("ERROR! " + string);
		resetDeadSilenceTimer();
	}
	
	void setNoPartner(boolean flag) {
		OmeglePsychologist.noPartner = flag;
	}
	
	void setAlone(boolean flag) {
		OmeglePsychologist.alone = flag;
	}
	void setThemDisplay(String message) {
		OmeglePsychologist.theyDisplay = message;
	}
	
	void setMeDisplay(String message) {
		OmeglePsychologist.meDisplay = message;
	}
	
	void clearThemDisplay() {
		OmeglePsychologist.theyDisplay = "";
	}
	
	void clearMeDisplay() {
		OmeglePsychologist.meDisplay = "";
	}
}
