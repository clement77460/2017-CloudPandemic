package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import fr.efrei.nouvellonJaworski.model.entities.Habitant;
import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.nouvellonJaworski.model.entities.Ville;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.selection.Selector;
import fr.efrei.paumier.shared.simulation.Simulation;
import fr.efrei.paumier.shared.time.TimeManager;

public class EventInfect implements Event{
	private Duration duration;
	private TimeManager manager;
	private List<Event> triggeredEventsList;
	private Instant triggeredInstant;
	private Ville ville;
	private Habitant target;
	private SimulationImplement simulation;
	public EventInfect(Instant currentInstant, Duration duration, TimeManager manager, List<Event> triggeredEventsList,
			Ville ville,Habitant target,SimulationImplement simulation) {
		this.duration = duration;
		this.manager =  manager;
		this.triggeredEventsList = triggeredEventsList;
		this.ville=ville;
		this.target=target;
		this.simulation=simulation;
	}
	@Override
	public void trigger() {
		triggeredEventsList.add(this);
		
		if (manager != null) {// si null infection initial?
			this.triggeredInstant = manager.getCurrentInstant();
		} 
		
		target.contaminerOuSoigner(true);
		ville.getHabitantsInfected().add(target);
		this.simulation.incrNbHabitantsInfected();
	}

	@Override
	public Duration getDuration() {
		// TODO Auto-generated method stub
		return duration;
	}

}
