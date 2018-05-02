package fr.efrei.nouvellonJaworski.vue;
import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;

import fr.efrei.nouvellonJaworski.model.selection.MySelector;
import fr.efrei.nouvellonJaworski.networking.Client;
import fr.efrei.paumier.shared.domain.CityBorder;
import fr.efrei.paumier.shared.orders.OrderType;
import fr.efrei.paumier.shared.simulation.Simulation;


import java.time.Clock;
import java.util.Scanner;


public class Partie {
	private Clock clock2;
	private MySelector selector;
	private SimulationImplement simulation;
	private CityBorder cityBorder;
	private Scanner sc;
	public Partie(int population) {
		
		this.clock2=Clock.systemUTC();
		
		this.selector=new MySelector();
		
		this.sc=new Scanner(System.in);
		
		//this.cityBorder=new Client("localhost",11111);
		this.cityBorder=new Client("178.62.119.191",4242);
		 
		//passer en agurment city border si le serveur est opérationnel
		//this.simulation = new SimulationImplement(clock2, cityBorder,selector, population);
		this.simulation = new SimulationImplement(clock2, null,selector, population);
		
		((Client) cityBorder).setSimulation(simulation);
		
		//lancer le thread seulement si le serveur est opérationnel
		//new Thread( (Runnable) this.cityBorder).start();
	}
	
	
	public void boucleJeu() {
		//rajouter un bool pour savoir si l'infection initial a eu lieu au lieu de LivingPopulation !=100
		while(this.simulation.getFirstHabitantIsInfected()==false  ||
				!(this.simulation.getInfectedPopulation()==0)) {
			this.affichageOptions();
			this.choix(sc.nextInt());
			this.simulation.update();
			
		}
		if(this.simulation.getLivingPopulation()!=0) {
			System.out.println("victoire");
		}else {
			System.out.println("fin de la partie, toute la population est infectée");
		}
	}
	
	private void affichageStats() {
		this.simulation.update();
		System.out.println("----------------------------------------------------------------");
		System.out.println("seconde : "+clock2.millis()/1000);
		System.out.println("Money : "+this.simulation.getMoney());
		System.out.println("Vivants : "+this.simulation.getLivingPopulation());
		System.out.println("Infectés : "+this.simulation.getInfectedPopulation());
		System.out.println("Morts : "+this.simulation.getDeadPopulation());
		System.out.println("Panic : "+this.simulation.getPanicLevel());
		System.out.println("----------------------------------------------------------------");
	}
	
	private void affichageOptions() {
		System.out.println("0-> afficher les stats");
		System.out.println("1-> améliorer le centre de dépistage");
		System.out.println("2-> augmenter les taxes");
		System.out.println("3-> quitter");
	}
	
	private void choix(int valeur) {
		switch(valeur) {
		case 0:
			this.affichageStats();
			break;
		case 1:
			this.simulation.executeOrder(OrderType.BUILD_SCREENING_CENTER);
			break;
		case 2:
			this.simulation.executeOrder(OrderType.INCREASE_TAXES);
			break;
		case 3:
			System.exit(0);
			break;
			
		}
		
	}
}
