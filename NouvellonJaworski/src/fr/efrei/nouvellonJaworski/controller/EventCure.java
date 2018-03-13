package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import fr.efrei.nouvellonJaworski.model.entities.Habitant;
import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.nouvellonJaworski.model.entities.Ville;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.time.TimeManager;

public class EventCure implements Event{
	
	private final Duration duration;
	private final TimeManager manager;
	private final List<Event> triggeredEventsList;
	private final Ville ville;
	private final Habitant target;
	private final Habitant source;
	private final SimulationImplement simulation;
	private Instant triggeredInstant;
	
	public EventCure(Instant currentInstant, Duration duration, TimeManager manager, List<Event> triggeredEventsList,
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public Duration getDuration() {
		// TODO Auto-generated method stub
		return null;
	}
}
