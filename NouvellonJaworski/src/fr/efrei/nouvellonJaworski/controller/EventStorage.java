package fr.efrei.nouvellonJaworski.controller;


import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import fr.efrei.paumier.shared.events.Event;

public class EventStorage implements Event,Comparable<EventStorage>{
	
	private Event event;
	private Instant creationDate;
	private double effort;
	private Instant lastUpdateInstant;
	
	public EventStorage(Event event,Instant cd) { 
		this.event=event;
		this.creationDate=cd;
		this.lastUpdateInstant=cd;
		this.effort=0.0;
		
	}
	
	public void increaseEffort(Instant currentInstant) {
		
		Clock clockCI=Clock.fixed(currentInstant, ZoneId.systemDefault());
		Clock clockLU=Clock.fixed(lastUpdateInstant, ZoneId.systemDefault());
		
		effort+=(event.getRate()*(clockCI.millis()-clockLU.millis()));
		
		this.lastUpdateInstant=currentInstant;
		
	}
	
	@Override 
	public void trigger() {
		
		this.event.trigger();
	}

	@Override
	public Duration getDuration() {
		
		return Duration.ofMillis(this.tmpsRestant());
		
		
	}
	
	
	public Instant getCreationDate() {
		return this.creationDate;
	}
	
	public Event getEvent() {
		return this.event;
	}
	public double getEffort() {
		
		return this.effort;
	}
	public double getRate() {
		return this.event.getRate();
	}
	
	
	public Long tmpsRestant() {
		Clock clockLU=Clock.fixed(lastUpdateInstant, ZoneId.systemDefault());
		Clock clockCreation=Clock.fixed(this.creationDate, ZoneId.systemDefault());
		
		
		long tmpsRestantSansRate=(long) (event.getDuration().toMillis()-effort);
		long tmpsRestantAvecRate=(long) (tmpsRestantSansRate/event.getRate());
		
		Long calcul=clockLU.millis()+tmpsRestantAvecRate-clockCreation.millis();
		
		return clockLU.millis()+tmpsRestantAvecRate-clockCreation.millis();
		

	
	}

	@Override
	public int compareTo(EventStorage o) {
		//0 c'est 
		//1 this > o
		// -1 this <0
		
		if(this.creationDatePlusDuration(this).isAfter(
				this.creationDatePlusDuration(o))) {
			return 1;
		}else {
			if(this.creationDatePlusDuration(this).isBefore(
				this.creationDatePlusDuration(o))) {
					return -1;
			}else {//ils sont egaux
				
				if(this.getCreationDate().isBefore(o.getCreationDate())) {
					//this a ete cree avant donc this > o dans la file
					
					return -1;
				}
				else {
					return 1;
				}
			}
		}
	}
	
	private Instant creationDatePlusDuration(EventStorage ev) {
		return ev.getCreationDate().plusMillis(
				ev.getDuration().toMillis());
	}
	
}
