package fr.efrei.nouvellonJaworski.controller.engine;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;

import fr.efrei.nouvellonJaworski.controller.EventQueueImplement;
import fr.efrei.nouvellonJaworski.controller.EventStorage;
import fr.efrei.paumier.shared.engine.GameEngine;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.events.EventQueue;
import fr.efrei.paumier.shared.events.FakeEventQueue;
import fr.efrei.paumier.shared.time.FakeClock;

public class GameEngineImplement implements GameEngine{
	private Clock clock;
	private Instant lastUpdate;
	private ArrayList<EventStorage> queue;
	private EventQueueImplement eventQueue;
	
	public GameEngineImplement(Clock clock) { 
		this.clock=clock;
		lastUpdate=Instant.now(clock);
		eventQueue=new EventQueueImplement();
		queue = new ArrayList<EventStorage>();  
	} 
	
	@Override
	public void update() {// declenche les evenements expirés dans l'ordre croissant en fonction du temps
							//stock la date du dernier update
		boolean alreadyUpdated=false;
		queue=eventQueue.extractRegisteredList();
		//Instant lastUpdateTemp=lastUpdate; 
		
		for(EventStorage event :queue) {		
			
			if(Duration.between(event.getCreationDate(),this.clock.instant()).getSeconds()
					>= event.getDuration().getSeconds()) {
				
				if(!alreadyUpdated) {
					lastUpdate=event.getCreationDate().plusSeconds((event.getDuration().getSeconds()));
					alreadyUpdated=true;  
				}
				
				event.trigger(); 
				
			}
			else {
				eventQueue.register(event);
				
			}
		}
		if(!alreadyUpdated)
			lastUpdate=clock.instant();
		System.out.println(lastUpdate);
	}
	
	
	
	@Override
	public Instant getCurrentInstant() {
		return lastUpdate;
	}

	@Override
	public void register(Event... events) {
		System.out.println(this.clock.instant());
		EventStorage[] storage=new EventStorage[events.length];
		for(int i=0;i<events.length;i++) {
			//storage[i]=new EventStorage(events[i],this.clock.instant());
			storage[i]=new EventStorage(events[i],this.lastUpdate);
		}
		storage=this.triEvenements(storage);
		eventQueue.register(storage);
	}

	private EventStorage[] triEvenements(EventStorage... events0) {
		EventStorage[] events=events0;
		EventStorage temp;
		
		for(int i=0;i<events.length-1;i++) {
			
			for(int n=1;n<events.length;n++) {
				
				if(Duration.between(events[i].getCreationDate().plusSeconds(events[i].getDuration().getSeconds()),
						events[n].getCreationDate().plusSeconds(events[n].getDuration().getSeconds())).getSeconds()<0){
					
					temp=events[i];
					events[i]=events[n];
					events[n]=temp; 
					
				} 
			}
		}
		return events;
	}
	public void setLastUpdate(Instant updateTime) {
		this.lastUpdate=updateTime;
	}
}
