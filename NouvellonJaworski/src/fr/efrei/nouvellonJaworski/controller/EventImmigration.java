package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import fr.efrei.nouvellonJaworski.model.entities.Habitant;
import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.nouvellonJaworski.model.entities.Ville;
import fr.efrei.paumier.shared.engine.GameEngine;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.selection.Selector;

public class EventImmigration implements Event{
	
	private final Duration duration;
	private final List<Event> triggeredEventsList;
	private final Ville ville;
	private final GameEngine gameEngine;
	private final Selector selector;
	private final SimulationImplement simulation;
	
	private final boolean isInfected;
	
	
	private Instant triggeredInstant;
	
	
	public EventImmigration(Instant currentInstant, Duration duration, GameEngine gameEngine, 
			List<Event> triggeredEventsList, Ville ville,
			boolean isInfected,Selector selector,SimulationImplement simulation) {
		this.isInfected=isInfected;
		this.duration = duration;
		this.triggeredEventsList = triggeredEventsList;
		this.ville=ville;
		this.gameEngine=gameEngine;
		this.triggeredInstant=currentInstant;
		this.selector=selector;
		this.simulation=simulation;
		
	}
	
	@Override
	public void trigger() {
		
		Habitant temp=new Habitant();
		
		triggeredEventsList.add(this);
		
		if (gameEngine != null) { 
			this.triggeredInstant = gameEngine.getCurrentInstant(); 
		}
		
		if(isInfected) {
			temp.infectSomeone();
			ville.getHabitantsInfected().add(temp);
			EventSpreading eventSpreading = new EventSpreading(Instant.EPOCH, Duration.ofSeconds(5),
					gameEngine, triggeredEventsList, ville, temp,selector);
			
			EventDeath eventDeath = new EventDeath(Instant.EPOCH, Duration.ofSeconds(15),
					gameEngine, triggeredEventsList, ville, temp);
			
			this.gameEngine.register(eventSpreading,eventDeath);
			
		}else {
			ville.getHabitantsHealthy().add(temp);
		}
		
	}

	@Override
	public Duration getDuration() {
		
		return duration;
	}

}
