package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import fr.efrei.nouvellonJaworski.model.entities.Habitant;
import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.nouvellonJaworski.model.entities.Ville;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.simulation.Simulation;
import fr.efrei.paumier.shared.time.TimeManager;

public class EventSpreading implements Event{
	private Duration duration;
	private TimeManager manager;
	private List<Event> triggeredEventsList;
	private Instant triggeredInstant;
	private Ville ville;
	private Habitant target;
	private Habitant source;
	private SimulationImplement simulation;
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
		
		if (manager != null) {// si null infection initial?
			this.triggeredInstant = manager.getCurrentInstant();
		} 
		
	
		source.infectSomeone(target);
		ville.getHabitantsInfected().add(target);
		ville.getHabitantsInfected().add(source);
		this.simulation.incrNbHabitantsInfected();
		
	}

	@Override
	public Duration getDuration() {
		// TODO Auto-generated method stub
		return duration;
	}

}
