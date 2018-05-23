package fr.efrei.nouvellonJaworski.controller.frame;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


import fr.efrei.nouvellonJaworski.networking.ClientTerminal;

public class FrameController implements WindowListener{
	private Thread thread;
	private ClientTerminal clientTerminal;
	
	public FrameController(Thread thread,ClientTerminal clientTerminal) {
		this.thread=thread;
		this.clientTerminal=clientTerminal;
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
		this.clientTerminal.deconnecter();
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
