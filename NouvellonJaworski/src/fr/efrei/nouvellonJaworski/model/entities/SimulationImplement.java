package fr.efrei.nouvellonJaworski.model.entities;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import fr.efrei.nouvellonJaworski.controller.EventInfect;
import fr.efrei.nouvellonJaworski.controller.EventScreening;
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

	
	public SimulationImplement(Clock clock, Selector selector, int population) {
		this.clock=clock;
		this.selector=selector;
		this.nbOriginalHabitants=population;
		this.nbHabitantsAlive=population;
		this.gameEngine=new GameEngineImplement(clock);
		this.eventTriggered = new ArrayList<Event>();
		this.lastUpdate=clock.instant();
		this.ville=new Ville(population);
		this.launchInitialContamination();

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
	
	
	private void launchInitialContamination() {
		Event event1 = new EventInfect(Instant.EPOCH,  Duration.ofSeconds(3), gameEngine, this.eventTriggered,ville, selector);
		Event eventScreening = new EventScreening(Instant.EPOCH, Duration.ofMillis(200), gameEngine,this.eventTriggered, ville, selector);
		//register eventScreening et finir de l'implémenter avant
		gameEngine.register(event1,eventScreening);
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
		return ville.getHabitantsAlive().size();
	}
	
	
	@Override
	public int getInfectedPopulation() {
		return ville.getHabitantsInfected().size(); 
	}
	
	
	@Override
	public int getQuarantinedPopulation() {
		return ville.getHabitantsIsolated().size();
	}
	
	
	@Override
	public int getDeadPopulation() {
		return ville.getHabitantsDead().size();
	}
	
	
}
