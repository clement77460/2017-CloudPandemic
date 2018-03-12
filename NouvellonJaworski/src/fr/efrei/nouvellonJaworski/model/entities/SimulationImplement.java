package fr.efrei.nouvellonJaworski.model.entities;
import static org.junit.Assert.assertEquals;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fr.efrei.nouvellonJaworski.controller.EventInfect;
import fr.efrei.nouvellonJaworski.controller.EventSpreading;
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
	private boolean contaminationInitial;

	public SimulationImplement(Clock clock, Selector selector, int population) {
		this.clock=clock;
		this.selector=selector;
		this.nbOriginalHabitants=population;
		this.nbHabitantsAlive=population;
		this.gameEngine=new GameEngineImplement(clock);
		this.eventTriggered = new ArrayList<Event>();
		this.lastUpdate=Instant.now(clock);
		this.ville=new Ville(population);
		this.contaminationInitial=false;
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
		
		while(Duration.between(lastUpdate, clockInstant).getSeconds()>0) {
			if(!contaminationInitial) {// pas d habs infecte et debut de partie
				Habitant target=selector.selectAmong(ville.getHabitants());
				ville.getHabitants().remove(target);
				Event event1 = new EventInfect(Instant.EPOCH, Duration.ofSeconds(3), null, this.eventTriggered,
						ville,target,this);
				gameEngine.register(event1);
				this.contaminationInitial=true;
			}else {
				
				while(ville.getHabitantsInfected().size()!=0 && ville.getHabitants().size()!=0) {
					
					//on fait attention a ne pas créer plus d'event que d'habitants a infecte 
					Habitant contamine=ville.getHabitantsInfected().remove(0);
					Habitant target=selector.selectAmong(ville.getHabitants());
					ville.getHabitants().remove(target);
					Event eventPropa=new EventSpreading(Instant.EPOCH, Duration.ofSeconds(5), gameEngine, this.eventTriggered,
							ville,target,contamine,this);
					gameEngine.register(eventPropa); 
						
				}
			}
			gameEngine.update();
			if(lastUpdate==gameEngine.getCurrentInstant()) {//il n'y a pas eu de maj du coup on sort
				lastUpdate=clockInstant; 
			}else {
				lastUpdate=gameEngine.getCurrentInstant();
			}
		}
		System.out.println("le nombre d'infecte est : "+this.nbHabitantsInfected);
		
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
