package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import fr.efrei.nouvellonJaworski.model.entities.Habitant;
import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.nouvellonJaworski.model.entities.Ville;
import fr.efrei.paumier.shared.engine.GameEngine;
import fr.efrei.paumier.shared.events.Event;

public class EventDeath implements Event{
	private final Ville ville;
	private final Duration duration;
	private final GameEngine gameEngine;
	private final List<Event> triggeredEventsList;
	private final SimulationImplement simulation;
	private Habitant target;
	private Instant triggeredInstant;
	
	
	public EventDeath(Instant currentInstant, Duration duration, GameEngine gameEngine, 
			List<Event> triggeredEventsList, 
			Ville ville, Habitant target,SimulationImplement simulation) { 
		this.simulation=simulation;
		this.duration = duration;
		this.triggeredEventsList = triggeredEventsList;
		this.ville=ville;
		this.gameEngine=gameEngine;
		this.target=target;
	}
	
	
	@Override
	public void trigger() {
		
		triggeredEventsList.add(this);
		
		if (gameEngine != null) {
			this.triggeredInstant = gameEngine.getCurrentInstant();
		} 
		
		System.out.println("on lance un death event à "+this.triggeredInstant.toString() + "sur "+target.getId());
		
		
		if(!target.isEmigrated()) {
			if(target.killHabitant() ) {//he died
				
				ville.getHabitantsInfected().remove(target);
				ville.incrPanic();
				
				ville.getHabitantsIsolated().remove(target);			
				ville.getHabitantsDead().add(target);
			}
		}
	}

	@Override
	public Duration getDuration() {
		return duration;
	}
	
	public double getRate() {
		
		return simulation.getRateStorage().getDeathRate();
		
	}

}
