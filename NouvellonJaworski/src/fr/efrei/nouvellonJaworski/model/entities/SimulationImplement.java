package fr.efrei.nouvellonJaworski.model.entities;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import fr.efrei.nouvellonJaworski.controller.EventInfect;
import fr.efrei.nouvellonJaworski.controller.EventQuarantine;
import fr.efrei.nouvellonJaworski.controller.EventSpreading;
import fr.efrei.nouvellonJaworski.controller.engine.GameEngineImplement;
import fr.efrei.paumier.shared.engine.GameEngine;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.selection.Selector;
import fr.efrei.paumier.shared.simulation.Simulation;

public class SimulationImplement implements Simulation{
	private final Selector selector;
	private final Clock clock;
	private final GameEngine gameEngine;
	protected final List<Event> eventTriggered ;
	private final Ville ville;
	
	private int nbHabitantsAlive;
	private int nbHabitantsInfected;
	private int nbHabitantsIsolated;
	private int nbHabitantsDead;
	private int nbOriginalHabitants;
	
	private Instant lastUpdate;
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
		this.launchInitialContamination();
		this.launchScreening();
	} 
	
	
	public int getNbHabitantsAlive() {
		return nbHabitantsAlive;
	}
	
	
	public int getNbHabitantsInfected() {
		return nbHabitantsInfected;
	}
	
	
	public int getNbHabitantsIsolated() {
		return nbHabitantsIsolated;
	}
	
	
	public void incrNbHabitantsInfected() {
		this.nbHabitantsInfected++;
	}
	
	
	public void incrNbHabitantsIsolated() {
		this.nbHabitantsIsolated++;
	}
	
	
	public void decrNbHabitantsInfected() {
		this.nbHabitantsInfected--;
	}
	
	
	public void decrNbHabitantsIsolated() {
		this.nbHabitantsIsolated--;
	}
	
	
	public void decrNbHabitantsAlive() {
		this.nbHabitantsAlive--;
	}
	
	
	@Override
	public String toString() {
		return "Il y a "+this.nbHabitantsAlive+" habitants en vie\n"
				+"Il y a "+this.nbHabitantsInfected+" habitants infectées\n"
				+"Il y a "+this.nbHabitantsIsolated+" habitants isolées\n";
	}
	
	
	public void addHabitantsAlive(int nbr) {
		this.nbHabitantsAlive=this.nbHabitantsAlive+nbr;
	}
	
	private void launchScreening() {
		
		EventQuarantine eventScreening = new EventQuarantine(Instant.EPOCH, Duration.ofSeconds(2), null, this.eventTriggered, ville, gameEngine, selector);
		gameEngine.register(eventScreening);
		gameEngine.update();
			
	}
	
	private void launchInitialContamination() {
		Event event1 = new EventInfect(Instant.EPOCH,  Duration.ofSeconds(3), null, this.eventTriggered,ville, selector, gameEngine);
		gameEngine.register(event1);
		gameEngine.update();
		
	}
	
	

	
	
	@Override 
	public void update() {
		this.gameEngine.update();
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
		return ville.getHabitantsInfected().size();
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
