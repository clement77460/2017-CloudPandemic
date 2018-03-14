package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import fr.efrei.nouvellonJaworski.model.entities.Habitant;
import fr.efrei.nouvellonJaworski.model.entities.Ville;
import fr.efrei.paumier.shared.engine.GameEngine;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.selection.Selector;
import fr.efrei.paumier.shared.time.TimeManager;

public class EventQuarantine implements Event{
	
	private final Duration duration;
	private final TimeManager manager;
	private final List<Event> triggeredEventsList;
	private final Ville ville;
	private final GameEngine gameEngine;
	private Selector selector;
	 
	private Instant triggeredInstant;
	
	public EventQuarantine(Instant currentInstant, Duration duration, TimeManager manager, List<Event> triggeredEventsList,	Ville ville, GameEngine gameEngine, Selector selector) {
		
		this.duration = duration;
		this.manager =  manager;
		this.triggeredEventsList = triggeredEventsList;
		this.ville=ville;
		this.gameEngine=gameEngine;
		this.selector=selector;
	}

	@Override
	public void trigger() {
		
		
		triggeredEventsList.add(this);
		
		if (manager != null) {
			this.triggeredInstant = manager.getCurrentInstant();
		} 
		
		Habitant target = selector.selectAmong(ville.getHabitants());
		if(target.isIsolated()) {
				target.isolateHabitant();
				ville.getHabitants().remove(target);
				ville.getHabitantsIsolated().add(target);
		}
		else {
			System.err.println("j'ai dis puteeee");
		}
		
		EventQuarantine eventQuarantine = new EventQuarantine(Instant.EPOCH, Duration.ofSeconds(3), manager, triggeredEventsList, ville, gameEngine, selector);
		//En prévision de la V4 avec les amelio
		//EventQuarantine eventQuarantine2 = new EventQuarantine(Instant.EPOCH, Duration.ofSeconds(3), manager, triggeredEventsList, ville, target, gameEngine, selector);

		gameEngine.register(eventQuarantine);
		gameEngine.update();
	}
	
	

	@Override
	public Duration getDuration() {
		// TODO Auto-generated method stub
		return duration;
	}
}
