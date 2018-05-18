package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.util.List;


import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.paumier.shared.events.Event;

public class EventInfect implements Event{
	private final Duration duration;
	private final List<Event> triggeredEventsList;
	private final SimulationImplement simulation;
	
	public EventInfect(Duration duration,
			List<Event> triggeredEventsList,SimulationImplement simulation) { 
		
		this.duration = duration;
		this.triggeredEventsList = triggeredEventsList;
		this.simulation=simulation;
	} 
	
	
	@Override
	public void trigger() {
		
		triggeredEventsList.add(this);
		
	
		simulation.createInfectAndDeathEvent(null,this.triggeredEventsList);
		simulation.setFirstHabitantIsInfected(true);
	}

	
	@Override
	public Duration getDuration() {
		return duration;
	}

}
