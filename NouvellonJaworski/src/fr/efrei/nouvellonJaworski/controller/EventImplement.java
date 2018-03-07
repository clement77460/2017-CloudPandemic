package fr.efrei.nouvellonJaworski.controller;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import fr.efrei.nouvellonJaworski.controller.engine.GameEngineImplement;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.time.TimeManager;

public class EventImplement implements Event{
	private Duration duration;
	private GameEngineImplement manager;
	private List<EventImplement> triggeredEventsList;
	private Instant triggeredInstant;
	
	public EventImplement(Instant currentInstant, Duration duration, TimeManager manager, List<EventImplement> triggeredEventsList) {
		this.duration = duration;
		this.manager = (GameEngineImplement) manager;
		this.triggeredEventsList = triggeredEventsList;
	}
	@Override
	public void trigger() {//declenche l'evenement de contamination
							//infect ou Spreading
		triggeredEventsList.add(this);
		
		if (manager != null) {// si null infection initial?
			this.triggeredInstant = manager.getCurrentInstant();
		}
		System.out.println("non");
	}
	
	@Override
	public Duration getDuration() {//duree avant le déclenchement
									//pour infection initial 3s
									//pour contamination 5s
		
		return duration;
	}
	public Instant getTriggeredInstant() {
		return this.triggeredInstant;
	}
	
}
