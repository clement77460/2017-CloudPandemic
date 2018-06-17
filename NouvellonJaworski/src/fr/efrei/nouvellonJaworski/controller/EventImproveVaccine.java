package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;

import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.paumier.shared.events.Event;

public class EventImproveVaccine implements Event{

	private final Duration duration;
	private final SimulationImplement simulation;
	
	public EventImproveVaccine(Duration duration,SimulationImplement simulation) {
		
		this.duration=duration;
		this.simulation=simulation;
	}

	@Override
	public void trigger() {
		
		this.simulation.getRateStorage().increaseDeathRate();
	}
	
	@Override
	public Duration getDuration() {
		
		return duration;
	}
}
