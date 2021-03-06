package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.util.List;

import fr.efrei.nouvellonJaworski.model.entities.Habitant;
import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.nouvellonJaworski.model.entities.Ville;
import fr.efrei.paumier.shared.engine.GameEngine;
import fr.efrei.paumier.shared.events.Event;

public class EventImmigration implements Event{
	
	private final Duration duration;
	private final List<Event> triggeredEventsList;
	private final Ville ville;
	private final GameEngine gameEngine;
	private final SimulationImplement simulation;
	private final boolean isInfected;
	
	
	
	public EventImmigration(Duration duration, GameEngine gameEngine, 
			List<Event> triggeredEventsList, Ville ville,
			boolean isInfected,SimulationImplement simulation) {
		this.isInfected=isInfected;
		this.duration = duration;
		this.triggeredEventsList = triggeredEventsList;
		this.ville=ville;
		this.gameEngine=gameEngine;
		this.simulation=simulation;
		
	}
	
	@Override
	public void trigger() {
		
		Habitant immigrant=new Habitant();
		immigrant.setEmigrated(false);
		
		triggeredEventsList.add(this);
		
		
		if(isInfected) {
			immigrant.infectSomeone();
			ville.getHabitantsInfected().add(immigrant);
			
			this.launchEventsOnImmigrant(immigrant);
			
		}else {
			ville.getHabitantsHealthy().add(immigrant);
		}
		
	}
	
	
	private void launchEventsOnImmigrant(Habitant immigrant) {
		
		
		EventSpreading eventSpreading = new EventSpreading(Duration.ofSeconds(5),
				triggeredEventsList, ville, immigrant,simulation);
		
		EventDeath eventDeath = new EventDeath(Duration.ofSeconds(15),
				triggeredEventsList, ville, immigrant,simulation);
		
		this.gameEngine.register(eventSpreading,eventDeath);
	}
	
	@Override
	public Duration getDuration() {
		
		return duration;
	}

}
