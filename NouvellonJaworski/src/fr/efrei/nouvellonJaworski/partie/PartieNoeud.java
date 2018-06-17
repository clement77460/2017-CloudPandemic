package fr.efrei.nouvellonJaworski.partie;

import java.time.Clock;

import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.nouvellonJaworski.model.selection.MySelector;
import fr.efrei.nouvellonJaworski.networking.ClientNoeud;

public class PartieNoeud{
	private Clock clock;
	private MySelector selector;
	protected SimulationImplement simulation;
	protected ClientNoeud cityBorder;
	private Thread thread;
	
	public PartieNoeud(int population,String ip,int port) {
		clock=Clock.systemUTC();
		this.selector=new MySelector();
		
		
		this.cityBorder=new ClientNoeud(ip,port);
		 
		this.simulation = new SimulationImplement(clock, cityBorder,selector, population);
		cityBorder.setSimulation(simulation);
		thread=new Thread( (Runnable) this.cityBorder);
		thread.start();
		
	}
	public void startSimulation() {
		while(this.simulation.getFirstHabitantIsInfected()==false  ||
				!(this.simulation.getInfectedPopulation()==0)) {
			
			try {
				Thread.sleep(200);//pause de 200 ms
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.simulation.update();
			this.cityBorder.sendStatistics(this.simulation.getStatistics());
		}
		this.cityBorder.deconnecter();
		this.thread.interrupt();
	}
}
