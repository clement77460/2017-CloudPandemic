package fr.efrei.nouvellonJaworski.controller.frame;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import fr.efrei.nouvellonJaworski.networking.Client;

public class FrameController implements WindowListener{
	private Thread thread;
	private Client client;
	
	public FrameController(Thread thread,Client client) {
		this.thread=thread;
		this.client=client;
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		this.thread.interrupt();
		this.client.deconnecter();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
