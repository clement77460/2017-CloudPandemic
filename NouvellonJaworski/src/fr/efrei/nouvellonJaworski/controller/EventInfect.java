package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import fr.efrei.nouvellonJaworski.model.entities.Habitant;
import fr.efrei.nouvellonJaworski.model.entities.Ville;
import fr.efrei.paumier.shared.engine.GameEngine;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.selection.Selector;

public class EventInfect implements Event{
	private final Ville ville;
	private final Duration duration;
	private final GameEngine gameEngine;
	private final List<Event> triggeredEventsList;
	private  Selector selector;
	private Instant triggeredInstant;
	
	
	public EventInfect(Instant currentInstant, Duration duration, GameEngine gameEngine,
			List<Event> triggeredEventsList, Ville ville, Selector selector) { 
		
		this.duration = duration;
		this.triggeredEventsList = triggeredEventsList;
		this.ville=ville;
		this.selector=selector;
		this.gameEngine=gameEngine;
	} 
	
	
	@Override
	public void trigger() {
		
		triggeredEventsList.add(this);
		
		if (gameEngine != null) { 
			this.triggeredInstant = gameEngine.getCurrentInstant();
		} 
		
		System.out.println("on lance un infect event a "+this.triggeredInstant.toString());
		
		Habitant target = selector.selectAmong(ville.getHabitantsAlive());
		target.contaminerOuSoigner(true);
		
		ville.getHabitants().remove(target);
		ville.getHabitantsInfected().add(target);
		
		EventSpreading eventSpreading = new EventSpreading(Instant.EPOCH, Duration.ofSeconds(5),
				gameEngine, triggeredEventsList, ville, target,selector);
		
		EventDeath eventDeath = new EventDeath(Instant.EPOCH, Duration.ofSeconds(15),
				gameEngine, triggeredEventsList, ville, target);
		
		gameEngine.register(eventSpreading,eventDeath);
		
	}

	@Override
	public Duration getDuration() {
		return duration;
	}

}
