package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.util.List;

import fr.efrei.nouvellonJaworski.model.entities.Habitant;
import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.nouvellonJaworski.model.entities.Ville;
import fr.efrei.paumier.shared.engine.GameEngine;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.selection.Selector;

public class EventSpreading implements Event{
	private final Duration duration;
	private final List<Event> triggeredEventsList;
	private final Ville ville;
	private final Habitant source;
	private final GameEngine gameEngine;
	private final SimulationImplement simulation;
	private Selector selector;

	
	public EventSpreading(Duration duration, 
			GameEngine gameEngine, List<Event> triggeredEventsList,
			Ville ville,Habitant source,Selector selector,SimulationImplement simulation) {
		
		this.duration = duration; 
		this.triggeredEventsList = triggeredEventsList;
		this.ville=ville;
		this.source=source;
		this.gameEngine=gameEngine;
		this.simulation=simulation;
		this.selector=selector; 
		
	}
	
	@Override
	public void trigger() {
		
		triggeredEventsList.add(this);
		
		
		if(!source.isDead() && !source.isIsolated() && source.isInfected() && ville.getHabitantsHealthy().size()!=0) {
			
			this.launchDeathAndSpreading();
			
		}
	}

	private void launchDeathAndSpreading() {
		//s'il y a la présence de non infectés
		if(ville.getHabitantsHealthy().size()>0) {
			Habitant target = selector.selectAmong(ville.getHabitantsHealthy());
			//System.out.println(source.getId()+" on lance un spreading event a "+this.triggeredInstant.toString() +"sur "+target.getId());
			source.infectSomeone(target);
			ville.getHabitantsHealthy().remove(target);
			ville.getHabitantsInfected().add(target);
		
		
			
			EventSpreading eventSpreading1 = new EventSpreading(Duration.ofSeconds(5), gameEngine, triggeredEventsList, ville, source,selector,simulation);
			EventSpreading eventSpreading2 = new EventSpreading(Duration.ofSeconds(5), gameEngine, triggeredEventsList, ville, target,selector,simulation);
			EventDeath eventDeath = new EventDeath(Duration.ofSeconds(15), triggeredEventsList, ville,target,simulation);
			
			gameEngine.register(eventSpreading1,eventSpreading2,eventDeath);
		}
	}
	
	@Override
	public Duration getDuration() {
		return duration;
	}

	public double getRate() {
		
		return simulation.getRateStorage().getSpreadingRate();
	}
}
