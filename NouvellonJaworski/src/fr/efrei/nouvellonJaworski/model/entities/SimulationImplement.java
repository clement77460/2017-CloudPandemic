package fr.efrei.nouvellonJaworski.model.entities;
import java.time.Clock;
import java.time.Instant;
import java.util.LinkedList;

import fr.efrei.nouvellonJaworski.controller.engine.GameEngineImplement;
import fr.efrei.nouvellonJaworski.model.selection.*;
import fr.efrei.paumier.shared.engine.GameEngine;
import fr.efrei.paumier.shared.selection.Selector;
import fr.efrei.paumier.shared.simulation.Simulation;

public class SimulationImplement implements Simulation{
	int nbHabitantsAlive;//OLD
	int nbHabitantsInfected;//OLD
	int nbHabitantsIsolated;//OLD
	int nbHabitantsDead;//OLD
	int nbOriginalHabitants;
	private Selector selector;
	private Clock clock;
	private LinkedList<Integer> listHabitants ;
	/**
	 * OLD
	 */
	public SimulationImplement(int nbHabitants,Selector mySelector) {
		this.nbHabitantsAlive=nbHabitants;
		this.nbOriginalHabitants=nbHabitants;
		this.nbHabitantsInfected=0;
		this.nbHabitantsIsolated=0;
		this.nbHabitantsDead=0;
	}
	public SimulationImplement(Clock clock, Selector selector, int population) {
		this.clock=clock;
		this.selector=selector;
		this.nbOriginalHabitants=population;
		this.nbHabitantsAlive=population;
		this.listHabitants= new LinkedList<Integer>();
		this.initialisationList(population);
	}
	private void initialisationList(int size) {
		for(int i=0;i<size;i++) {
			this.listHabitants.add(0);
		}
	}
	/**
	 * OLD
	 * @return
	 */
	public int getNbHabitantsAlive() {
		return nbHabitantsAlive;
	}
	/**
	 * OLD
	 * @return
	 */
	public int getNbHabitantsInfected() {
		return nbHabitantsInfected;
	}
	/**
	 * OLD
	 * @return
	 */
	public int getNbHabitantsIsolated() {
		return nbHabitantsIsolated;
	}
	/**
	 * OLD
	 */
	public void incrNbHabitantsInfected() {
		System.out.println("un habitant est infecté");
		this.nbHabitantsInfected++;
	}
	/**
	 * OLD
	 */
	public void incrNbHabitantsIsolated() {
		this.nbHabitantsIsolated++;
	}
	/**
	 * OLD
	 */
	public void decrNbHabitantsInfected() {
		this.nbHabitantsInfected--;
	}
	/**
	 * OLD
	 */
	public void decrNbHabitantsIsolated() {
		this.nbHabitantsIsolated--;
	}
	/**
	 * OLD
	 */
	public void decrNbHabitantsAlive() {
		this.nbHabitantsAlive--;
	}
	/**
	 * OLD
	 */
	@Override
	public String toString() {
		return "Il y a "+this.nbHabitantsAlive+" habitants en vie\n"
				+"Il y a "+this.nbHabitantsInfected+" habitants infectées\n"
				+"Il y a "+this.nbHabitantsIsolated+" habitants isolées\n";
	}
	/** 
	 * OLD
	 * @param nbr
	 */
	public void addHabitantsAlive(int nbr) {
		this.nbHabitantsAlive=this.nbHabitantsAlive+nbr;
	}
	@Override
	public void update() {
		try {
			while(true) {
			if(selector.selectAmong(this.listHabitants).equals(null))
				System.out.println("lel");
			this.nbHabitantsInfected++;
			}
		} catch(java.lang.NullPointerException e) {
			System.out.println("on stop");
		}
	}
	@Override
	public Instant getCurrentInstant() {
		return null;
	}
	@Override
	public int getOriginalPopulation() {
		return nbOriginalHabitants;
	}
	@Override
	public int getLivingPopulation() {
		return nbHabitantsAlive;
	}
	@Override
	public int getInfectedPopulation() {
		return nbHabitantsInfected;
	}
	@Override
	public int getQuarantinedPopulation() {
		return nbHabitantsIsolated;
	}
	@Override
	public int getDeadPopulation() {
		return nbHabitantsDead;
	}
}
