package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.util.List;

import fr.efrei.nouvellonJaworski.model.entities.Habitant;
import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.nouvellonJaworski.model.entities.Ville;
import fr.efrei.paumier.shared.events.Event;

public class EventCure implements Event{
	
	private final Duration duration;
	private final List<Event> triggeredEventsList;
	private final Ville ville;
	private final Habitant target;
	private final SimulationImplement simulation;
	
	
	public EventCure(Duration duration,
			List<Event> triggeredEventsList, Ville ville,
			Habitant target,SimulationImplement simulation) {
		
		this.duration = duration;
		this.triggeredEventsList = triggeredEventsList;
		this.ville=ville;
		this.target=target;
		this.simulation=simulation;
	}

	@Override
	public void trigger() {
		triggeredEventsList.add(this);
		
		if(target.healHabitant()) { 
			ville.decreasePanic(2.5);
			ville.getHabitantsHealthy().add(target);
			
		}
		ville.getHabitantsIsolated().remove(target);
	}

	@Override
	public Duration getDuration() {
		
		return duration;
	}
	public double getRate() {
		
		return simulation.getRateStorage().getCureRate();
		
	}
}
