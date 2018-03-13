package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import fr.efrei.nouvellonJaworski.model.entities.Habitant;
import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.nouvellonJaworski.model.entities.Ville;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.time.TimeManager;

public class EventSpreading implements Event{
	private final Duration duration;
	private final TimeManager manager;
	private final List<Event> triggeredEventsList;
	private final Ville ville;
	private final Habitant target;
	private final Habitant source;
	private final SimulationImplement simulation;
	 
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
	public EventSpreading(Instant currentInstant, Duration duration, TimeManager manager, List<Event> triggeredEventsList,
			Ville ville,Habitant target,Habitant source,SimulationImplement simulation) {
		
		this.duration = duration; 
		this.manager =  manager;
		this.triggeredEventsList = triggeredEventsList;
		this.ville=ville;
		this.source=source;
		this.target=target;
		this.simulation=simulation;
		
	}
	
	
	@Override
	public void trigger() {
		
		triggeredEventsList.add(this);
		
		if (manager != null) {
			this.triggeredInstant = manager.getCurrentInstant();
		} 
		
	
		source.infectSomeone(target);
		
		ville.getHabitantsInfected().add(target);
		ville.getHabitantsInfected().add(source);
		
		this.simulation.incrNbHabitantsInfected();
		
	}

	@Override
	public Duration getDuration() {
		return duration;
	}

}
