package fr.efrei.nouvellonJaworski.model.entities;
import static org.junit.Assert.assertEquals;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fr.efrei.nouvellonJaworski.controller.EventImplement;
import fr.efrei.nouvellonJaworski.controller.engine.GameEngineImplement;
import fr.efrei.nouvellonJaworski.model.selection.*;
import fr.efrei.paumier.shared.engine.GameEngine;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.events.FakeEvent;
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
	private GameEngine gameEngine;
	protected List<Event> eventTriggered ;
	private Instant lastUpdate;
	private Ville ville;
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
		this.gameEngine=new GameEngineImplement(clock);
		this.eventTriggered = new ArrayList<Event>();
		this.lastUpdate=Instant.now(clock);
		this.ville=new Ville(population);
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
		Instant clockInstant=clock.instant();
		while(Duration.between(lastUpdate, clockInstant).getSeconds()!=0) {
			if(ville.getHabitantsInfected().size()==0) {// pas d event
				Habitant source=selector.selectAmong(ville.getHabitants());
				ville.getHabitants().remove(source);
				Event event1 = new EventImplement(Instant.EPOCH, Duration.ofSeconds(3), null, this.eventTriggered,
						ville.getHabitantsInfected(),source);
				gameEngine.register(event1);
			}else {
				for(Event event:eventTriggered){//chaque event entraine une contamination
					Habitant source=selector.selectAmong(ville.getHabitants());
					ville.getHabitants().remove(source);
					Event eventPropa=new EventImplement(Instant.EPOCH, Duration.ofSeconds(5), gameEngine, this.eventTriggered,
							ville.getHabitantsInfected(),source);
					gameEngine.register(eventPropa);
				}
			}
			gameEngine.update();
			lastUpdate=gameEngine.getCurrentInstant();
		}
		System.out.println(ville.getHabitants().size());
		System.out.println(ville.getHabitantsInfected().size());
		this.nbHabitantsInfected=ville.getHabitantsInfected().size();
		
	}
	@Override
	public Instant getCurrentInstant() {
		return lastUpdate;
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
