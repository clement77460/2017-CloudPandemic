package fr.efrei.nouvellonJaworski.controller.engine;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;

import fr.efrei.nouvellonJaworski.controller.EventQueueImplement;
import fr.efrei.paumier.shared.engine.GameEngine;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.events.EventQueue;
import fr.efrei.paumier.shared.events.FakeEventQueue;
import fr.efrei.paumier.shared.time.FakeClock;

public class GameEngineImplement implements GameEngine{
	private Clock clock;
	private Instant lastUpdate;
	private ArrayList<Event> queue;
	private EventQueueImplement eventQueue;
	public GameEngineImplement(Clock clock) { 
		this.clock=clock;
		lastUpdate=Instant.now(clock);
		eventQueue=new EventQueueImplement();
		queue = new ArrayList<Event>();  
	} 
	
	@Override
	public void update() {// declenche les evenements expirés dans l'ordre croissant en fonction du temps
							//stock la date du dernier update
		boolean alreadyUpdated=false;
		queue=eventQueue.extractRegisteredList();
		Instant lastUpdateTemp=lastUpdate; 
		
		for(Event event :queue) {		
			
			Instant clockInstant=clock.instant();
			if(Duration.between(lastUpdateTemp, clockInstant).getSeconds()>=event.getDuration().getSeconds()) {
				
				if(!alreadyUpdated) {
					lastUpdate=lastUpdate.plusSeconds(event.getDuration().getSeconds());
					alreadyUpdated=true;  
				}
				event.trigger();
			}
			else {
				eventQueue.register(event);
				
			}
		}
	}

	@Override
	public Instant getCurrentInstant() {
		return lastUpdate;
	}

	@Override
	public void register(Event... events) {
		
		eventQueue.register(this.triEvenements(events));
	}

	private Event[] triEvenements(Event... events0) {
		Event[] events=events0;
		Event temp;
		
		for(int i=0;i<events.length-1;i++) {
			
			for(int n=1;n<events.length;n++) {
				
				if(events[i].getDuration().getSeconds()>events[n].getDuration().getSeconds()) {
					
					temp=events[i];
					events[i]=events[n];
					events[n]=temp; 
					
				} 
			}
		}
		return events;
	}
}
