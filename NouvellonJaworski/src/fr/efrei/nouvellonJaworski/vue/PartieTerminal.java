package fr.efrei.nouvellonJaworski.vue;

import java.time.Clock;

import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.nouvellonJaworski.model.selection.MySelector;
import fr.efrei.nouvellonJaworski.networking.Client;
import fr.efrei.paumier.shared.orders.OrderType;

public class PartieTerminal {
	private Clock clock2;
	private MySelector selector;
	private SimulationImplement simulation;
	private Client cityBorder;
	
	public PartieTerminal(int population) {
		this.clock2=Clock.systemUTC();
		this.selector=new MySelector();
		
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
				Thread.sleep(200);//pause de 200 ms
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
		this.simulation.sendStatistics();
	}

	private void choix(int valeur) {
		switch(valeur) {
		case 1:
			cityBorder.sendClientMessage(OrderType.BUILD_SCREENING_CENTER);
			break;
		case 2:
			cityBorder.sendClientMessage(OrderType.INCREASE_TAXES);
			break;
		case 3:
			cityBorder.sendClientMessage(OrderType.RESEARCH_IMPROVED_MEDICINE);
			break;
		case 4:
			cityBorder.sendClientMessage(OrderType.RESEARCH_IMPROVED_VACCINE);
			break;
		case 5:
			cityBorder.sendClientMessage(OrderType.INCREASE_CURFEW);
			break;
		case 6:
			cityBorder.sendClientMessage(OrderType.REDUCE_CURFEW);
			break;
		case 7:
			System.exit(0);
			break;
			
		}
	}
}
