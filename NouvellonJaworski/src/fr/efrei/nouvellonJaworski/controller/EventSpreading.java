package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import fr.efrei.nouvellonJaworski.model.entities.Habitant;
import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.nouvellonJaworski.model.entities.Ville;
import fr.efrei.paumier.shared.engine.GameEngine;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.selection.Selector;
import fr.efrei.paumier.shared.time.TimeManager;

public class EventSpreading implements Event{
	private final Duration duration;
	private final List<Event> triggeredEventsList;
	private final Ville ville;
	private final Habitant source;
	private final GameEngine gameEngine;
	private Selector selector;
	private boolean canTrigger;
	private Instant triggeredInstant;
	
	public EventSpreading(Instant currentInstant, Duration duration, GameEngine gameEngine, List<Event> triggeredEventsList,Ville ville,Habitant source,Selector selector) {
		
		this.duration = duration; 
		this.triggeredEventsList = triggeredEventsList;
		this.ville=ville;
		this.source=source;
		this.gameEngine=gameEngine;
		this.selector=selector; 
		this.canTrigger=false;
		this.checkSourceStatusWhenInfecting();
		
	}
	private void checkSourceStatusWhenInfecting() {
		if(!source.isDead() && !source.isIsolated()) {
			this.canTrigger=true;
		} 
	}
	
	@Override
	public void trigger() {
		System.out.println("on trigger le spreading ? "+this.canTrigger);
		triggeredEventsList.add(this);
		
		if (gameEngine != null) {
			this.triggeredInstant = gameEngine.getCurrentInstant();
		} 
		if(!source.isDead() && !source.isIsolated()) {
			Habitant target = selector.selectAmong(ville.getHabitants());
			source.infectSomeone(target);
			ville.getHabitants().remove(target);
			ville.getHabitantsInfected().add(target);
		
		
			//Creation deux events spreading 
			EventSpreading eventSpreading1 = new EventSpreading(this.triggeredInstant, Duration.ofSeconds(5), gameEngine, triggeredEventsList, ville, source,selector);
			EventSpreading eventSpreading2 = new EventSpreading(this.triggeredInstant, Duration.ofSeconds(5), gameEngine, triggeredEventsList, ville, target,selector);
			EventDeath eventDeath = new EventDeath(Instant.EPOCH, Duration.ofSeconds(15), gameEngine, triggeredEventsList, ville, selector,target);
			//gameEngine.register(eventSpreading1,eventSpreading2,eventDeath);
			gameEngine.register(eventSpreading1,eventSpreading2);
		}
		//gameEngine.update();
	}

	@Override
	public Duration getDuration() {
		return duration;
	}

}
