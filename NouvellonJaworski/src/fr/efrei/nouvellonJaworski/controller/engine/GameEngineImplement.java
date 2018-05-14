package fr.efrei.nouvellonJaworski.controller.engine;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;

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
		
		EventStorage eventTemp;
		
		
		this.updateRateEvents();
		//this.triii();
		if(queue.size()>0) {
			eventTemp=queue.remove(0);
			
			if(this.isClockTimeAfterTriggerTime(eventTemp)) {	
					
					
					this.lastUpdate=eventTemp.getCreationDate().plusMillis((eventTemp.getDuration().toMillis()));
					this.updateRateEvents();
					eventTemp.trigger(); 
					
					this.update();
			}
			else {
				
				
				lastUpdate=clock.instant();
				
				this.registerExistingEvent(eventTemp);
				
				
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
		
		queue.sort(Comparator.naturalOrder());
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
		}
		
		this.triEvenements(storage);
		
	}
	
	/**
	 * Méthode qui remet dans la file un evenement qui existait
	 * 
	 */
	private void registerExistingEvent(EventStorage... eventStorage) {
		
		this.triEvenements(eventStorage);
		
		
	}
	
	

	
	
	
	private void triEvenements(EventStorage... events) {
		boolean isPlaced;
		
		for(int n=0;n<events.length;n++) {
			isPlaced=false;
			
			if(this.queue.size()==0) {
				this.queue.add(events[n]);
			}
			
			else {
				for(int i=0;i<this.queue.size() && !isPlaced;i++) {
					
					isPlaced=this.addEventOnAnNonEmptyQueue(this.queue.get(i), events[n],i);
					
				}
				if(!isPlaced) {
					this.queue.add(events[n]);
				}
			}
		}
	}
	
	
	private boolean addEventOnAnNonEmptyQueue(EventStorage eventInQueue,
			EventStorage eventToAdd,int indiceQueue) {
		
		if(this.creationDatePlusDuration(eventInQueue).isAfter(
				this.creationDatePlusDuration(eventToAdd))) {
			
			this.queue.add(indiceQueue, eventToAdd);
			return true;
		}
		else {
			if(this.compareCreationDateIfTriggerTimeAreSimilar(eventInQueue, eventToAdd)){
					
				this.queue.add(indiceQueue, eventToAdd);
				return true;
				
			}
		}
		return false;
	}
	
	
	private boolean compareCreationDateIfTriggerTimeAreSimilar(EventStorage eventInQueue,
			EventStorage eventToAdd) {
		
		if(this.creationDatePlusDuration(eventInQueue).equals(
				this.creationDatePlusDuration(eventToAdd))) {
			//on choisis celui qui a la plus ancienne date de creation
			
			if(eventInQueue.getCreationDate().isAfter(
					eventToAdd.getCreationDate())) {
				return true;
			}
		}
		return false;
	}
	
	
	private Instant creationDatePlusDuration(EventStorage ev) {
			return ev.getCreationDate().plusMillis(
					ev.getDuration().toMillis());
	}
	
}
