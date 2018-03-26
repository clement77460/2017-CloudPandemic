package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.time.Instant;
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
	private Instant triggeredInstant;
	
	
	public EventScreening(Instant currentInstant, Duration duration, GameEngine gameEngine, 
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
		
		if (gameEngine != null) { 
			this.triggeredInstant = gameEngine.getCurrentInstant(); 
		} 
		
		this.launchCure();
		
		EventScreening event=new EventScreening(triggeredInstant, Duration.ofMillis(200), gameEngine, triggeredEventsList, ville, selector,simulation);
		gameEngine.register(event);
		
	}
	
	private void launchCure() {
		List<Habitant> habitantsHealthyAndNot=new ArrayList<Habitant>();
		habitantsHealthyAndNot.addAll(ville.getHabitantsHealthy());
		habitantsHealthyAndNot.addAll(ville.getHabitantsInfected());
		// trier la liste par ordre croissant d'id
		habitantsHealthyAndNot.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
		for(Habitant habs:habitantsHealthyAndNot) {
			System.out.println(habs.getId());
		}
		for(int i=0;i<simulation.getNbUpgradeOfScreeningCenter()+1;i++) {
			Habitant target = selector.selectAmong(habitantsHealthyAndNot);
			habitantsHealthyAndNot.remove(target);
			System.out.println("l'id du selectionné est : "+target.getId());
			
			if(target.isolateHabitant()) {
				System.out.println("on isole un hab");
				ville.getHabitantsIsolated().add(target);
				ville.getHabitantsInfected().remove(target);
				EventCure eventCure=new EventCure(triggeredInstant, Duration.ofSeconds(5), gameEngine, triggeredEventsList, ville, target);
				gameEngine.register(eventCure);
			}
		
		}
	}
	
	@Override
	public Duration getDuration() {
		
		return duration;
	}

}
