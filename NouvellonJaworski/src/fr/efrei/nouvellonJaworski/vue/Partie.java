package fr.efrei.nouvellonJaworski.vue;
import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;

import fr.efrei.nouvellonJaworski.model.selection.MySelector;
import fr.efrei.paumier.shared.simulation.Simulation;
import fr.efrei.paumier.shared.time.FakeClock;


import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;


public class Partie {
	private final FakeClock clock;
	private int population;
	private MySelector selector;
	private Simulation simulation;
	
	
	public Partie(int population) {
		this.population=population;
		this.selector=new MySelector();
		this.clock = new FakeClock();
		this.simulation = new SimulationImplement(clock, null,selector, population);
	}
	public void printCarac(SimulationImplement stats) {
		System.out.println(stats);
	}
	public void boucleJeu() {
		while(this.simulation.getDeadPopulation()!=100) {
			System.out.println("seconde : "+clock.millis()/1000);
			System.out.println("Money : "+this.simulation.getMoney());
			System.out.println("Viviants : "+this.simulation.getLivingPopulation());
			System.out.println("Infectés : "+this.simulation.getInfectedPopulation());
			System.out.println("Morts : "+this.simulation.getDeadPopulation());
			this.simulation.update();
			clock.advance(Duration.ofSeconds(1));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("toute la population a ete infecte en "+simulation.getCurrentInstant());
	}
}
