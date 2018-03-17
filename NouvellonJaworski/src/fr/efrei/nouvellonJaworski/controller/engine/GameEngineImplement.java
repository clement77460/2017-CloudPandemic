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
	//private EventQueueImplement eventQueue;
	
	public GameEngineImplement(Clock clock) { 
		this.clock=clock;
		lastUpdate=Instant.now(clock);
		//eventQueue=new EventQueueImplement();
		queue = new ArrayList<EventStorage>();  
	} 
	
	@Override
	public void update() {// declenche les evenements expirés dans l'ordre croissant en fonction du temps
							//stock la date du dernier update
		boolean alreadyUpdated=false;
		ArrayList<EventStorage> queueTemp;
		queueTemp=this.extractRegisteredList();
		EventStorage eventToTrigger = null;
		
		for(EventStorage event :queueTemp) {	
			if(Duration.between(event.getCreationDate(),this.clock.instant()).toMillis()
					>= event.getDuration().toMillis()) {
				
				if(!alreadyUpdated) {
					lastUpdate=event.getCreationDate().plusMillis((event.getDuration().toMillis()));
					alreadyUpdated=true;  
					eventToTrigger=event;
				}
				else {
					this.registerExistingEvent(event);
				}
				//event.trigger(); 
				
			}
			else {
				this.registerExistingEvent(event);
				
			}
		}
		if(!alreadyUpdated)
			lastUpdate=clock.instant();
		else {
			eventToTrigger.trigger(); 
			this.update();
		}
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
		}
		/*for(EventStorage store:storage) {
			this.queue.add(store);
		}*/
		this.triEvenements(storage);
	}
	/**
	 * Méthode qui remet dans la file un evenement qui existait
	 * 
	 */
	private void registerExistingEvent(EventStorage... eventStorage) {
		/*for(EventStorage store:eventStorage) {
			queue.add(store);
		}*/
		this.triEvenements(eventStorage);
	}
	
	
	/*private void triEvenements() {
		EventStorage temp;
		for(int i=0;i<this.queue.size()-1;i++) {
			for(int n=1;n<this.queue.size();n++) {
				
				if(this.creationDatePlusDuration(i).isAfter(
						this.creationDatePlusDuration(n))) {
					//n prend la place de i
					temp=this.queue.remove(n);
					this.queue.add(i, temp);
					
				}
				else {
					if(this.creationDatePlusDuration(i).equals(
							this.creationDatePlusDuration(n))) {//on choisis celui qui a la plus ancienne date de creation
						
						if(this.queue.get(i).getCreationDate().isAfter(
								this.queue.get(n).getCreationDate())) {
							//n prend la place de i
							temp=this.queue.remove(n);
							this.queue.add(i, temp);
						}
					}
				}
			}
		}
	}*/
	private void triEvenements(EventStorage... events) {
		boolean isPlaced;
		
		//else {
		for(int n=0;n<events.length;n++) {
			isPlaced=false;
			if(this.queue.size()==0) {
				this.queue.add(events[n]);
			}
			else {
			for(int i=0;i<this.queue.size() && !isPlaced;i++) {
				if(this.creationDatePlusDuration(this.queue.get(i)).isAfter(
						this.creationDatePlusDuration(events[n]))) {
					this.queue.add(i, events[n]);
					isPlaced=true;
				}
				else {
					if(this.creationDatePlusDuration(this.queue.get(i)).equals(
							this.creationDatePlusDuration(events[n]))) {//on choisis celui qui a la plus ancienne date de creation
						
						if(this.queue.get(i).getCreationDate().isAfter(
								events[n].getCreationDate())) {
							this.queue.add(i, events[n]);
							isPlaced=true;
						}
					}
				}
			}
			if(!isPlaced) {
				this.queue.add(events[n]);
			}
		}
		}
	}
	private Instant creationDatePlusDuration(EventStorage ev) {
			return ev.getCreationDate().plusMillis(
					ev.getDuration().toMillis());
	}
	private Instant creationDatePlusDuration(int index) {
		return this.queue.get(index).getCreationDate().plusMillis(
				this.queue.get(index).getDuration().toMillis());
	}
	
	
	
	private ArrayList<EventStorage> extractRegisteredList() {
		ArrayList<EventStorage> list = this.queue;
		
		this.queue = new ArrayList<EventStorage>();
		
		return list;
	}
}
