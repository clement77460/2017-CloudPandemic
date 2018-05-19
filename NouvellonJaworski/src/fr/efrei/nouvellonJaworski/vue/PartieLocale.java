package fr.efrei.nouvellonJaworski.vue;
import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;

import fr.efrei.nouvellonJaworski.model.selection.MySelector;
import fr.efrei.paumier.shared.orders.OrderType;


import java.time.Clock;
import java.util.Scanner;



public class PartieLocale {
	private Clock clock;
	private MySelector selector;
	private SimulationImplement simulation;
	private Scanner sc;
	
	public PartieLocale(int population) {
		this.clock=Clock.systemUTC();
		this.selector=new MySelector();
		this.sc=new Scanner(System.in);
		
		this.simulation = new SimulationImplement(clock, null,selector, population);
		
	}
	
	
	public void boucleJeu() {
		
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
		System.out.println(this.simulation.getStatistics().toString());
		System.out.println("--------------------------------------------------");
	}
	
	private void affichageOptions() {
		System.out.println("0-> afficher les stats");
		System.out.println("1-> améliorer le centre de dépistage");
		System.out.println("2-> augmenter les taxes");
		System.out.println("3-> Recherche de médicaments");
		System.out.println("4-> Recherche de vaccins");
		System.out.println("5-> Augmentation du couvre-feu");
		System.out.println("6-> Réduction du couvre-feu");
		System.out.println("7-> quitter");
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
			this.simulation.executeOrder(OrderType.RESEARCH_IMPROVED_MEDICINE);
			break;
		case 4:
			this.simulation.executeOrder(OrderType.RESEARCH_IMPROVED_VACCINE);
			break;
		case 5:
			this.simulation.executeOrder(OrderType.INCREASE_CURFEW);
			break;
		case 6:
			this.simulation.executeOrder(OrderType.REDUCE_CURFEW);
			break;
		case 7:
			System.exit(0);
			break;
			
		}
		
	}
}
