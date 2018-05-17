package fr.efrei.nouvellonJaworski.vue;

import java.time.Clock;
import java.util.Scanner;

import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.nouvellonJaworski.model.selection.MySelector;
import fr.efrei.nouvellonJaworski.networking.Client;
import fr.efrei.paumier.shared.orders.OrderType;

public class PartieNoeud {
	private Clock clock2;
	private MySelector selector;
	private SimulationImplement simulation;
	private Client cityBorder;
	private Scanner sc;
	
	public PartieNoeud(int population) {
		this.clock2=Clock.systemUTC();
		this.selector=new MySelector();
		this.sc=new Scanner(System.in);
		
		this.cityBorder=new Client("localhost",11111);
		//this.cityBorder=new Client("178.62.119.191",4242);
		 
		this.simulation = new SimulationImplement(clock2, cityBorder,selector, population);
		((Client) cityBorder).setSimulation(simulation);
		
		//lancer le thread seulement si le serveur est opérationnel
		new Thread( (Runnable) this.cityBorder).start();
	}
	
	
	public void boucleJeu() {
		
		while(this.simulation.getFirstHabitantIsInfected()==false  ||
				!(this.simulation.getInfectedPopulation()==0)) {
			try {
				Thread.sleep(5000);//pause de 5 secondes
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.simulation.update();
			this.envoiStats();
			
		}
		if(this.simulation.getLivingPopulation()!=0) {
			System.out.println("victoire");
		}else {
			System.out.println("fin de la partie, toute la population est infectée");
		}
	}
	
	private void envoiStats() {
		this.simulation.update();
		System.out.println(this.simulation.getStatistics().toString());
		this.simulation.sendStatistics();
	}
	
}
