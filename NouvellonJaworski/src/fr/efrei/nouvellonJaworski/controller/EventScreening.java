package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import fr.efrei.nouvellonJaworski.model.entities.Habitant;
import fr.efrei.nouvellonJaworski.model.entities.Ville;
import fr.efrei.paumier.shared.engine.GameEngine;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.selection.Selector;

public class EventScreening implements Event{
	
	private final Ville ville;
	private final Duration duration;
	private final GameEngine gameEngine;
	private final List<Event> triggeredEventsList;
	private  Selector selector;
	private Instant triggeredInstant;
	
	
	public EventScreening(Instant currentInstant, Duration duration, GameEngine gameEngine, List<Event> triggeredEventsList, Ville ville, Selector selector) {
		this.duration = duration;
		this.triggeredEventsList = triggeredEventsList;
		this.ville=ville;
		this.selector=selector;
		this.gameEngine=gameEngine;
	}
	
	@Override
	public void trigger() {
		
		triggeredEventsList.add(this);
		
		if (gameEngine != null) {
			this.triggeredInstant = gameEngine.getCurrentInstant(); 
		} 
		System.out.println("on lance un screening event a "+this.triggeredInstant.toString());
		Habitant target = selector.selectAmong(ville.getHabitantsAlive());
		if(target.isolateHabitant()) {
			ville.getHabitantsIsolated().add(target);
			ville.getHabitants().remove(target);
			System.out.println("on le décontamine");
			EventCure eventCure=new EventCure(triggeredInstant, Duration.ofSeconds(5), gameEngine, triggeredEventsList, ville, target);
			gameEngine.register(eventCure);
		}
		EventScreening event=new EventScreening(triggeredInstant, Duration.ofMillis(200), gameEngine, triggeredEventsList, ville, selector);
		gameEngine.register(event);
		//gameEngine.update();
	}

	@Override
	public Duration getDuration() {
		
		return duration;
	}

}
