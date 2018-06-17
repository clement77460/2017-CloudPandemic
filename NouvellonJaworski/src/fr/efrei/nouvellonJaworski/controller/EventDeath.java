package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.util.List;

import fr.efrei.nouvellonJaworski.model.entities.Habitant;
import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.nouvellonJaworski.model.entities.Ville;
import fr.efrei.paumier.shared.events.Event;

public class EventDeath implements Event{
	private final Ville ville;
	private final Duration duration;
	private final List<Event> triggeredEventsList;
	private final SimulationImplement simulation;
	private Habitant target;
	
	
	public EventDeath(Duration duration,List<Event> triggeredEventsList, 
			Ville ville, Habitant target,SimulationImplement simulation) { 
		this.simulation=simulation;
		this.duration = duration;
		this.triggeredEventsList = triggeredEventsList;
		this.ville=ville;
		this.target=target;
	}
	
	
	@Override
	public void trigger() {
		
		triggeredEventsList.add(this);

		
		if(!target.isEmigrated()) {
			if(target.killHabitant() ) {
				
				this.killHabitantAndIncreasePanic();
			}
		}
	}

	private void killHabitantAndIncreasePanic() {
		ville.getHabitantsInfected().remove(target);
		ville.incrPanic(5);
		
		ville.getHabitantsIsolated().remove(target);			
		ville.getHabitantsDead().add(target);
	}
	
	@Override
	public Duration getDuration() {
		return duration;
	}
	
	public double getRate() {
		
		return simulation.getRateStorage().getDeathRate();
		
	}

}
