package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import fr.efrei.nouvellonJaworski.model.entities.Habitant;
import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.nouvellonJaworski.model.entities.Ville;
import fr.efrei.paumier.shared.engine.GameEngine;
import fr.efrei.paumier.shared.events.Event;

public class EventCure implements Event{
	
	private final Duration duration;
	private final List<Event> triggeredEventsList;
	private final Ville ville;
	private final Habitant target;
	private final GameEngine gameEngine;
	private final SimulationImplement simulation;
	private Instant triggeredInstant;
	
	
	public EventCure(Instant currentInstant, Duration duration, GameEngine gameEngine, 
			List<Event> triggeredEventsList, Ville ville,
			Habitant target,SimulationImplement simulation) {
		
		this.duration = duration;
		this.triggeredEventsList = triggeredEventsList;
		this.ville=ville;
		this.gameEngine=gameEngine;
		this.triggeredInstant=currentInstant;
		this.target=target;
		this.simulation=simulation;
	}

	@Override
	public void trigger() {
		triggeredEventsList.add(this);
		
		if (gameEngine != null) { 
			this.triggeredInstant = gameEngine.getCurrentInstant(); 
		}
		System.out.println("on lance un cure event à "+this.triggeredInstant.toString());
		
		if(target.healHabitant()) { //cure has succeed
			System.out.println("success");
			ville.decrPanic();
			
			ville.getHabitantsHealthy().add(target);
			
			
		}
		ville.getHabitantsIsolated().remove(target);//if dead we still remove the target from isolated ppl
	}

	@Override
	public Duration getDuration() {
		
		return duration;
	}
	public double getRate() {
		
		return simulation.getRateStorage().getCureRate();
		
	}
}
