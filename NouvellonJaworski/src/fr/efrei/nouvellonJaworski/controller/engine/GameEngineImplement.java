package fr.efrei.nouvellonJaworski.controller.engine;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;


import fr.efrei.paumier.shared.engine.GameEngine;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.events.FakeEventQueue;
import fr.efrei.paumier.shared.time.FakeClock;

public class GameEngineImplement implements GameEngine{
	private Clock clock;
	private Instant lastUpdate;
	private ArrayList<Event> queue;
	private FakeEventQueue eventQueue;
	public GameEngineImplement(Clock clock) {
		this.clock=clock;
		lastUpdate=Instant.now(clock);
		eventQueue=new FakeEventQueue();
		queue = new ArrayList<Event>();
	}
	
	@Override
	public void update() {// declenche les evenements expires dans l'ordre croissant du temps
							//stock la date du dernier update
		/*Event temp=queue.peek();
		if(temp!=null) {
			Instant clockInstant=clock.instant();
			if(Duration.between(lastUpdate, clockInstant).getSeconds()>=temp.getDuration().getSeconds()) {
				lastUpdate=lastUpdate.plusSeconds(temp.getDuration().getSeconds());
				temp.trigger();
				queue.removeFirst();
				//vider la liste
			}
		}*/
		
		queue=eventQueue.extractRegisteredList();
		for(Event event :queue) {
			Instant clockInstant=clock.instant();
			if(Duration.between(lastUpdate, clockInstant).getSeconds()>=event.getDuration().getSeconds()) {
				lastUpdate=lastUpdate.plusSeconds(event.getDuration().getSeconds());
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
		Event temp;
		if(events.length!=1) {
			for(int i=0;i<events.length-1;i++) {
				for(int n=1;n<events.length;n++) {
					if(events[i].getDuration().getSeconds()>events[n].getDuration().getSeconds()) {
						temp=events[i];
						events[i]=events[n];
						events[n]=temp;
					}
				}
			}
		}
		eventQueue.register(events);
		
		
	}


}
