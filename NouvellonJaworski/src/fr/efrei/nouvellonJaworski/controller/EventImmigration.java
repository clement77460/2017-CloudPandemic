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
		
		Habitant temp=new Habitant();
		temp.setEmigrated(false);
		
		triggeredEventsList.add(this);
		
		
		if(isInfected) {
			temp.infectSomeone();
			ville.getHabitantsInfected().add(temp);
			EventSpreading eventSpreading = new EventSpreading(Duration.ofSeconds(5),
					triggeredEventsList, ville, temp,simulation);
			
			EventDeath eventDeath = new EventDeath(Duration.ofSeconds(15),
					triggeredEventsList, ville, temp,simulation);
			
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
