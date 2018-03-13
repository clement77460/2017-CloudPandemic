package fr.efrei.nouvellonJaworski.model.entities;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import fr.efrei.nouvellonJaworski.controller.EventInfect;
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
		Habitant target=selector.selectAmong(ville.getHabitants());
		ville.getHabitants().remove(target);
		
		Event event1 = new EventInfect(Instant.EPOCH, Duration.ofSeconds(3), null, 
				this.eventTriggered,ville,target,this);
		gameEngine.register(event1);
		
		this.contaminationInitial=true;
	}
	
	
	private void launchSpreadingContamination() {
		//on fait attention à ne pas créer plus d'event que d'habitants à infecter 
		Habitant contamine=ville.getHabitantsInfected().remove(0);
		Habitant target=selector.selectAmong(ville.getHabitants());
		ville.getHabitants().remove(target);
		
		Event eventPropa=new EventSpreading(Instant.EPOCH, Duration.ofSeconds(5), gameEngine, this.eventTriggered,
				ville,target,contamine,this);
		gameEngine.register(eventPropa); 
	} 
	
	private void updateLastUpdate(Instant clockInstant) {
		if(lastUpdate==gameEngine.getCurrentInstant()) {//il n'y a pas eu de maj du coup on sort
			lastUpdate=clockInstant; 
		}else {
			lastUpdate=gameEngine.getCurrentInstant();
		}
	}
	
	
	@Override 
	public void update() {
		Instant clockInstant=clock.instant();
		while(Duration.between(lastUpdate, clockInstant).getSeconds()>0) {
			if(!contaminationInitial) {// on regarde si la contamination initial a été lancé
				
				this.launchInitialContamination();
				
			}else {
				while(ville.getHabitantsInfected().size()!=0 && ville.getHabitants().size()!=0) {
					
					this.launchSpreadingContamination();	
					
				}
			}
			
			gameEngine.update();
			this.updateLastUpdate(clockInstant);
			
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
