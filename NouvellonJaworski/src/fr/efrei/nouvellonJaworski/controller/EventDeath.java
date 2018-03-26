package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import fr.efrei.nouvellonJaworski.model.entities.Habitant;
import fr.efrei.nouvellonJaworski.model.entities.Ville;
import fr.efrei.paumier.shared.engine.GameEngine;
import fr.efrei.paumier.shared.events.Event;

public class EventDeath implements Event{
	private final Ville ville;
	private final Duration duration;
	private final GameEngine gameEngine;
	private final List<Event> triggeredEventsList;
	private Habitant target;
	private Instant triggeredInstant;
	
	
	public EventDeath(Instant currentInstant, Duration duration, GameEngine gameEngine, 
			List<Event> triggeredEventsList, Ville ville, Habitant target) { 
		
		this.duration = duration;
		this.triggeredEventsList = triggeredEventsList;
		this.ville=ville;
		this.gameEngine=gameEngine;
		this.target=target;
	}
	
	
	@Override
	public void trigger() {
		
		triggeredEventsList.add(this);
		
		if (gameEngine != null) {
			this.triggeredInstant = gameEngine.getCurrentInstant();
		} 
		
		//System.out.println("on lance un death event � "+this.triggeredInstant.toString());
		
		if(target.killHabitant()) {//he died
			
			ville.getHabitantsInfected().remove(target);
			//ville.getHabitantsAlive().remove(target);
			ville.getHabitantsDead().add(target);
		}
	}

	@Override
	public Duration getDuration() {
		return duration;
	}

}
