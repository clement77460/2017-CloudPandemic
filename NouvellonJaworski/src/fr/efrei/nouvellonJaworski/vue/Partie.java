package fr.efrei.nouvellonJaworski.vue;
import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.paumier.shared.selection.FakeSelector;
import fr.efrei.paumier.shared.selection.Selector;
import fr.efrei.paumier.shared.simulation.Simulation;
import fr.efrei.paumier.shared.time.FakeClock;


import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Scanner;

import fr.efrei.nouvellonJaworski.controller.*;
public class Partie {
	private Controller controllerPartie;
	private FakeClock clock;
	private int population;
	private FakeSelector selector;
	private Simulation simulation;
	public Partie(Controller controllerPartie) {
		this.controllerPartie=controllerPartie;
	}
	public Partie(int population) {
		this.population=population;
		selector=new FakeSelector();
		clock = new FakeClock(Clock.fixed(Instant.EPOCH,
				ZoneId.systemDefault()));
		simulation = new SimulationImplement(clock, selector, 100);
	}
	public void askAction() {
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("******************************************");
			System.out.println("1. Try to infect someone randomly");
			System.out.println("2. try to heal someone randomly");
			System.out.println("3. try to isolate someone randomly");
			System.out.println("4. try to kill someone randomly");
			System.out.println("5. quit");
			controllerPartie.choixAction(sc.nextInt());
		}
	}
	public void printCarac(SimulationImplement stats) {
		System.out.println(stats);
	}
	public void boucleJeu() throws InterruptedException {
		for(int i=0;i<100;i++) {
			selector.enqueueRanks(0);
		}
		while(simulation.getInfectedPopulation()!=100) {
			System.out.println("seconde => "+clock.millis()/1000);
			this.simulation.update();
			clock.advance(Duration.ofSeconds(1));
			Thread.sleep(2000);
		}
		System.out.println("toute la population a ete infecte en "+simulation.getCurrentInstant());
	}
}
