package fr.efrei.nouvellonJaworski.controller;

import java.time.Duration;
import java.time.Instant;

import fr.efrei.paumier.shared.events.Event;

public class EventStorage implements Event{
	private Event event;
	private Instant creationDate;
	
	public EventStorage(Event event,Instant cd) { 
		this.event=event;
		this.creationDate=cd;
	}
	
	@Override 
	public void trigger() {
		this.event.trigger();
	}

	@Override
	public Duration getDuration() {
		return this.event.getDuration();
	}
	
	
	public Instant getCreationDate() {
		return this.creationDate;
	}
	
	public Event getEvent() {
		return this.event;
	}

}
