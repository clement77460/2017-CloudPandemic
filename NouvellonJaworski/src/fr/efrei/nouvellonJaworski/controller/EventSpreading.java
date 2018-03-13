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
import fr.efrei.paumier.shared.time.TimeManager;

public class EventSpreading implements Event{
	private final Duration duration;
	private final TimeManager manager;
	private final List<Event> triggeredEventsList;
	private final Ville ville;
	private final Habitant source;
	private final GameEngine gameEngine;
	private Selector selector;
	 
	private Instant triggeredInstant;
	
	/**
	 * EventSpreading possède un habitant source et un habitant target
	 * @param currentInstant
	 * @param duration
	 * @param manager
	 * @param triggeredEventsList
	 * @param ville
	 * @param target => habitant qui propage la contamination
	 * @param source => habitant a infecter
	 * @param simulation
	 */
	public EventSpreading(Instant currentInstant, Duration duration, TimeManager manager, List<Event> triggeredEventsList,Ville ville,Habitant source, GameEngine gameEngine,Selector selector) {
		
		this.duration = duration; 
		this.manager =  manager;
		this.triggeredEventsList = triggeredEventsList;
		this.ville=ville;
		this.source=source;
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
		source.infectSomeone(target);
		ville.getHabitants().remove(target);
		ville.getHabitantsInfected().add(target);
		
		
	//Creation deux events spreading 
		EventSpreading eventSpreading1 = new EventSpreading(Instant.EPOCH, Duration.ofSeconds(5), manager, triggeredEventsList, ville, source, gameEngine,selector);
		EventSpreading eventSpreading2 = new EventSpreading(Instant.EPOCH, Duration.ofSeconds(5), manager, triggeredEventsList, ville, target, gameEngine,selector);
		gameEngine.register(eventSpreading1,eventSpreading2);
		gameEngine.update();
	}

	@Override
	public Duration getDuration() {
		return duration;
	}

}
