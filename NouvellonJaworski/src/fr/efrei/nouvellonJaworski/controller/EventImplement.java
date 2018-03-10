package fr.efrei.nouvellonJaworski.controller;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import fr.efrei.nouvellonJaworski.controller.engine.GameEngineImplement;
import fr.efrei.nouvellonJaworski.model.entities.Habitant;
import fr.efrei.nouvellonJaworski.model.entities.Ville;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.selection.Selector;
import fr.efrei.paumier.shared.time.TimeManager;

public class EventImplement implements Event{
	private Duration duration;
	private TimeManager manager;
	private List<Event> triggeredEventsList;
	private Instant triggeredInstant;
	private List<Habitant> infectedPpl;
	private Habitant cible;
	private Habitant source;
	public EventImplement(Instant currentInstant, Duration duration, TimeManager manager, List<Event> triggeredEventsList,
			List<Habitant> infectedPpl,Habitant cible) {
		this.duration = duration;
		this.manager =  manager;
		this.triggeredEventsList = triggeredEventsList;
		this.cible=cible;
		this.infectedPpl=infectedPpl;
		if(infectedPpl.size()!=0) {
			this.source=infectedPpl.remove(0);
		}
	}
	@Override 
	public void trigger() {//declenche l'evenement de contamination
							//infect ou Spreading
		triggeredEventsList.add(this);
		
		if (manager != null) {// si null infection initial?
			this.triggeredInstant = manager.getCurrentInstant();
			this.spreading();
		} 
		else { 
			this.infect();
		}
	}
	
	@Override
	public Duration getDuration() {
		
		return duration;
	}
	public Instant getTriggeredInstant() {
		return this.triggeredInstant;
	}
	private void infect() {
		this.infectedPpl.add(cible);
		
	}
	private void spreading() {
		this.infectedPpl.add(cible);
		this.infectedPpl.add(source);
	}
	
}
