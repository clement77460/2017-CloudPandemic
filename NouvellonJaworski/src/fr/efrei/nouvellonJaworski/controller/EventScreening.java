package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import fr.efrei.nouvellonJaworski.model.entities.Habitant;
import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.nouvellonJaworski.model.entities.Ville;
import fr.efrei.paumier.shared.engine.GameEngine;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.selection.Selector;

public class EventScreening implements Event{
	
	private final Ville ville;
	private final Duration duration;
	private final GameEngine gameEngine;
	private final List<Event> triggeredEventsList;
	private final SimulationImplement simulation;
	private  Selector selector;
	
	
	public EventScreening( Duration duration, GameEngine gameEngine, List<Event> triggeredEventsList,
			Ville ville, Selector selector,SimulationImplement simulation) {
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
		
		this.launchCure();
		
		EventScreening event=new EventScreening( Duration.ofMillis(200), gameEngine, triggeredEventsList, ville, selector,simulation);
		gameEngine.register(event);
		
	}
	
	private void launchCure() {
		
		List<Habitant> habitantsHealthyAndNot=new ArrayList<Habitant>();
		habitantsHealthyAndNot.addAll(ville.getHabitantsHealthy());
		habitantsHealthyAndNot.addAll(ville.getHabitantsInfected());
		
		// trier la liste par ordre croissant d'id
		habitantsHealthyAndNot.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
		
		for(int i=0;i<simulation.getNbUpgradeOfScreeningCenter()+1;i++) {
			if(habitantsHealthyAndNot.size()>0) {
				Habitant target = selector.selectAmong(habitantsHealthyAndNot);
				habitantsHealthyAndNot.remove(target);
				
				
				if(target.isolateHabitant()) {
					this.launchCureEvent(target);
				}
			}
		}
	}
	
	
	private void launchCureEvent(Habitant target) {
		
		ville.getHabitantsIsolated().add(target);
		ville.getHabitantsInfected().remove(target);
		EventCure eventCure=new EventCure(Duration.ofSeconds(5),
				triggeredEventsList, ville, target,simulation);
		gameEngine.register(eventCure);
	}
	
	@Override
	public Duration getDuration() {
		
		return duration;
	}

}
