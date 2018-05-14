package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.util.List;

import fr.efrei.nouvellonJaworski.model.entities.Habitant;
import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.nouvellonJaworski.model.entities.Ville;
import fr.efrei.paumier.shared.engine.GameEngine;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.selection.Selector;

public class EventInfect implements Event{
	private final Ville ville;
	private final Duration duration;
	private final GameEngine gameEngine;
	private final List<Event> triggeredEventsList;
	private final SimulationImplement simulation;
	private  Selector selector;
	
	public EventInfect(Duration duration, GameEngine gameEngine,
			List<Event> triggeredEventsList, Ville ville, Selector selector,SimulationImplement simulation) { 
		
		this.duration = duration;
		this.triggeredEventsList = triggeredEventsList;
		this.ville=ville;
		this.selector=selector;
		this.gameEngine=gameEngine;
		this.simulation=simulation;
	} 
	
	
	@Override
	public void trigger() {
		
		triggeredEventsList.add(this);
		
		
		Habitant target = selector.selectAmong(ville.getHabitantsHealthy());
		target.contaminerOuSoigner(true);
		
		ville.getHabitantsHealthy().remove(target);
		ville.getHabitantsInfected().add(target);
		
		this.createNewEventsOnTarget(target);
		
		simulation.setFirstHabitantIsInfected(true);
	}

	private void createNewEventsOnTarget(Habitant target) {
		EventSpreading eventSpreading = new EventSpreading(Duration.ofSeconds(5),
				gameEngine, triggeredEventsList, ville, target,selector,simulation);
		
		EventDeath eventDeath = new EventDeath(Duration.ofSeconds(15),
				triggeredEventsList, ville, target,simulation);
		
		gameEngine.register(eventSpreading,eventDeath);
	}
	
	@Override
	public Duration getDuration() {
		return duration;
	}

}
