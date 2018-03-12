package fr.efrei.nouvellonJaworski.vue;
import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.nouvellonJaworski.model.selection.MySelector;
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
	private FakeClock clock;
	private int population;
	private MySelector selector;
	private Simulation simulation;
	public Partie(int population) {
		this.population=population;
		selector=new MySelector();
		clock = new FakeClock(Clock.fixed(Instant.EPOCH,
				ZoneId.systemDefault()));
		simulation = new SimulationImplement(clock, selector, 100);
	}
	public void printCarac(SimulationImplement stats) {
		System.out.println(stats);
	}
	public void boucleJeu() {
		while(simulation.getInfectedPopulation()!=100) {
			System.out.println("seconde => "+clock.millis()/1000);
			this.simulation.update();
			clock.advance(Duration.ofSeconds(1));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("toute la population a ete infecte en "+simulation.getCurrentInstant());
	}
}
