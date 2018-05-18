package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.util.List;

import fr.efrei.nouvellonJaworski.model.entities.Habitant;
import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.nouvellonJaworski.model.entities.Ville;
import fr.efrei.paumier.shared.events.Event;

public class EventSpreading implements Event{
	private final Duration duration;
	private final List<Event> triggeredEventsList;
	private final Ville ville;
	private final Habitant source;
	private final SimulationImplement simulation;

	
	public EventSpreading(Duration duration, 
			List<Event> triggeredEventsList,
			Ville ville,Habitant source,SimulationImplement simulation) {
		
		this.duration = duration; 
		this.triggeredEventsList = triggeredEventsList;
		this.ville=ville;
		this.source=source;
		this.simulation=simulation;
		
	}
	
	@Override
	public void trigger() {
		
		triggeredEventsList.add(this);
		
		
		if(!source.isDead() && !source.isIsolated() && source.isInfected() && ville.getHabitantsHealthy().size()!=0) {
			
			simulation.createInfectAndDeathEvent(source, triggeredEventsList);
			
		}
	}
	
	@Override
	public Duration getDuration() {
		return duration;
	}

	public double getRate() {
		
		return simulation.getRateStorage().getSpreadingRate();
	}
}
