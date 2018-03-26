package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.paumier.shared.engine.GameEngine;
import fr.efrei.paumier.shared.events.Event;

public class EventScreeningCenter implements Event{

	private final Duration duration;
	private final GameEngine gameEngine;
	private final List<Event> triggeredEventsList;
	private final SimulationImplement simulation;
	private Instant triggeredInstant;
	
	public EventScreeningCenter(Instant currentInstant, Duration duration, GameEngine gameEngine,
			List<Event> triggeredEventsList,SimulationImplement simulation) {
		
		this.triggeredInstant=currentInstant;
		this.duration=duration;
		this.gameEngine=gameEngine;
		this.triggeredEventsList=triggeredEventsList;
		this.simulation=simulation;
	}

	@Override
	public void trigger() {
		//System.out.println("on trigger un event de Screening centers");
		
		simulation.incrUpradeOfScreeningCenter();
		
	}
	
	@Override
	public Duration getDuration() {
		
		return duration;
	}

}