package fr.efrei.nouvellonJaworski.model.entities;
import static org.junit.Assert.assertEquals;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
		/*try {
			while(true) {
			if(selector.selectAmong(this.listHabitants).equals(null))
				System.out.println("lel");
			this.nbHabitantsInfected++;
			}
		} catch(java.lang.NullPointerException e) {
			System.out.println("on stop");
		}*/
		/*Event event1 = new FakeEvent(Instant.EPOCH, Duration.ofSeconds(3), gameEngine, this.eventTriggered);
		Event event1_1 = new FakeEvent(Instant.EPOCH, Duration.ofSeconds(5+3), gameEngine, this.eventTriggered);
		Event event1_2 = new FakeEvent(Instant.EPOCH, Duration.ofSeconds(5+3+5), gameEngine, this.eventTriggered);
		Event event1_1_1 = new FakeEvent(Instant.EPOCH, Duration.ofSeconds(5+5+3), gameEngine, this.eventTriggered);
		gameEngine.register(event1,event1_1,event1_2,event1_1_1);
		gameEngine.update();
		this.nbHabitantsInfected= eventTriggered.size();*/
		Instant clockInstant=clock.instant();
		while(Duration.between(lastUpdate, clockInstant).getSeconds()!=0) {
			if(selector.selectAmong(this.eventTriggered)==null) {// pas d event
				System.out.println("on initialise l'event de contamination");
				Event event1 = new FakeEvent(Instant.EPOCH, Duration.ofSeconds(3), null, this.eventTriggered);
				gameEngine.register(event1);
			}else {
				System.out.println("la taille de la queu est "+eventTriggered.size()+" et le temps "+this.lastUpdate);
				for(Event event:eventTriggered){//chaque event entraine une contamination
					Event eventPropa=new FakeEvent(Instant.EPOCH, Duration.ofSeconds(5), gameEngine, this.eventTriggered);
					gameEngine.register(eventPropa);
				}
			}
			gameEngine.update();
			lastUpdate=gameEngine.getCurrentInstant();
		}
		
		this.nbHabitantsInfected=eventTriggered.size();
		System.out.println(this.nbHabitantsInfected);
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
