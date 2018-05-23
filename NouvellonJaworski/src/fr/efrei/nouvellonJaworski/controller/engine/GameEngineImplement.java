package fr.efrei.nouvellonJaworski.controller.engine;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;

import fr.efrei.nouvellonJaworski.controller.EventStorage;
import fr.efrei.paumier.shared.engine.GameEngine;
import fr.efrei.paumier.shared.events.Event;

public class GameEngineImplement implements GameEngine{
	private final Clock clock;
	
	private Instant lastUpdate;
	private ArrayList<EventStorage> queue;
	
	public GameEngineImplement(Clock clock) { 
		this.clock=clock;
		lastUpdate=Instant.now(clock);
		queue = new ArrayList<EventStorage>();  
	} 
	
	@Override
	public void update() {// declenche les evenements expirés dans l'ordre croissant en fonction du temps
							//stock la date du dernier update
		
		EventStorage eventToUpdate;
	
		this.updateRateEvents();
		if(queue.size()>0) {
			eventToUpdate=queue.remove(0);
			
			if(this.isClockTimeAfterTriggerTime(eventToUpdate)) {	
					
					
					this.lastUpdate=eventToUpdate.getCreationDate().plusMillis((eventToUpdate.getDuration().toMillis()));
					this.updateRateEvents();
					eventToUpdate.trigger(); 
					
					this.update();
			}
			else {
				
				
				lastUpdate=clock.instant();
				
				this.registerExistingEvent(eventToUpdate);
				
				
			}
		}else {
			lastUpdate=clock.instant();
		}
		this.updateRateEvents();
	}
	
	private void updateRateEvents() {
		for(EventStorage event:queue) {
			event.increaseEffort(this.lastUpdate);
		}
		this.sortQueue();
	}
	
	
	private boolean isClockTimeAfterTriggerTime(EventStorage event) {
		
		event.increaseEffort(lastUpdate);
		
		if(Duration.between(event.getCreationDate(),this.clock.instant()).toMillis()
				>= event.getDuration().toMillis()) {
			return true;
		}
		return false;
	}
	
	
	@Override
	public Instant getCurrentInstant() {
		return lastUpdate;
	}
	
	/**
	 * méthode qui met un nouveau evenement dans un EventStorage
	 */
	@Override
	public void register(Event... events) {
		EventStorage[] storage=new EventStorage[events.length];
		for(int i=0;i<events.length;i++) {
			storage[i]=new EventStorage(events[i],this.lastUpdate);
			storage[i].increaseEffort(lastUpdate);
			this.queue.add(storage[i]);
		}
		queue.sort(Comparator.naturalOrder());
		
	}
	
	/**
	 * Méthode qui remet dans la file un evenement qui existait
	 * 
	 */
	private void registerExistingEvent(EventStorage... eventStorage) {
		this.queue.add(eventStorage[0]);
		this.sortQueue();
		
		
	}
	
	private void sortQueue() {
		try {
			queue.sort(Comparator.naturalOrder());
		}catch(ConcurrentModificationException e) {
			
		}
	}
	
}
