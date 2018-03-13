package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import fr.efrei.nouvellonJaworski.model.entities.Habitant;
import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.nouvellonJaworski.model.entities.Ville;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.time.TimeManager;

public class EventInfect implements Event{
	private final Ville ville;
	private final Duration duration;
	private final TimeManager manager;
	private final List<Event> triggeredEventsList;
	private final Habitant target;
	private final SimulationImplement simulation;
	
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
	public EventInfect(Instant currentInstant, Duration duration, TimeManager manager, List<Event> triggeredEventsList,
			Ville ville,Habitant target,SimulationImplement simulation) { 
		
		this.duration = duration;
		this.manager =  manager;
		this.triggeredEventsList = triggeredEventsList;
		this.ville=ville;
		this.target=target;
		this.simulation=simulation;
	}
	
	
	@Override
	public void trigger() {
		triggeredEventsList.add(this);
		
		if (manager != null) {
			this.triggeredInstant = manager.getCurrentInstant();
		} 
		
		this.target.contaminerOuSoigner(true);
		this.ville.getHabitantsInfected().add(target);
		this.simulation.incrNbHabitantsInfected();
	}

	@Override
	public Duration getDuration() {
		return duration;
	}

}
