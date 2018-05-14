package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.paumier.shared.events.Event;

public class EventDecreaseCurfew implements Event{

	private final Duration duration;
	private final SimulationImplement simulation;
	
	public EventDecreaseCurfew(Duration duration,SimulationImplement simulation) {
		
		this.duration=duration;
		this.simulation=simulation;
	}

	@Override
	public void trigger() {
		this.simulation.getVille().decrPanic(10.0);
		this.simulation.getRateStorage().decreaseSpreadingRate();
		
		
	}
	
	@Override
	public Duration getDuration() {
		
		return duration;
	}
	
	
}
