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

public class EventDeath implements Event{
	private final Ville ville;
	private final Duration duration;
	private final TimeManager manager;
	private final GameEngine gameEngine;
	private final List<Event> triggeredEventsList;
	private Habitant target;
	private  Selector selector;
	private Instant triggeredInstant;
	
	/** 
	 * EventInfect possède uniquement un target car c'est la contamination initial
	 * @param currentInstant
	 * @param duration
	 * @param manager
	 * @param triggeredEventsList
	 * @param ville
	 * @param target => habitant a infecter
	 * @param simulation
	 */
	public EventDeath(Instant currentInstant, Duration duration, TimeManager manager, List<Event> triggeredEventsList, Ville ville, Selector selector, GameEngine gameEngine, Habitant target) { 
		
		this.duration = duration;
		this.manager =  manager;
		this.triggeredEventsList = triggeredEventsList;
		this.ville=ville;
		this.selector=selector;
		this.gameEngine=gameEngine;
		this.target=target;
	}
	
	
	@Override
	public void trigger() {
		triggeredEventsList.add(this);
		
		if (manager != null) {
			this.triggeredInstant = manager.getCurrentInstant();
		} 
		target.setDead(true);
		ville.getHabitantsInfected().remove(target);
		ville.getHabitantsDead().add(target);
	}

	@Override
	public Duration getDuration() {
		return duration;
	}

}
