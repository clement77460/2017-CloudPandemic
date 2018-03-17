package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.nouvellonJaworski.model.entities.Ville;
import fr.efrei.paumier.shared.engine.GameEngine;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.selection.Selector;

public class EventTaxes implements Event{
	

	private final Duration duration;
	private final GameEngine gameEngine;
	private final List<Event> triggeredEventsList;
	private final SimulationImplement simulation;
	
	private Instant triggeredInstant;
	
	public EventTaxes(Instant currentInstant, Duration duration, GameEngine gameEngine,
			List<Event> triggeredEventsList,SimulationImplement simulation) { 
		
		this.duration = duration;
		this.triggeredEventsList = triggeredEventsList;
		this.gameEngine=gameEngine;
		this.simulation=simulation;
	}
	
	
	@Override
	public void trigger() {
		
		triggeredEventsList.add(this);
		
		if (gameEngine != null) { 
			this.triggeredInstant = gameEngine.getCurrentInstant();
		} 
		System.out.println("on lance un Taxes event a "+this.triggeredInstant.toString());
		simulation.updateMoney();
		Event eventTaxes=new EventTaxes(Instant.EPOCH,  Duration.ofSeconds(5), gameEngine, this.triggeredEventsList,simulation);
		
		gameEngine.register(eventTaxes);
		
	}

	@Override
	public Duration getDuration() {
		
		return duration;
	}

}
