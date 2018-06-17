package fr.efrei.paumier.shared.events;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import fr.efrei.paumier.shared.time.TimeManager;

public class FakeEvent implements Event {
	private List<Event> triggeredEventsList;
	private Duration duration;
	private TimeManager manager;
	private Instant triggeredInstant;
	
	private double rate = 1;
	
	public FakeEvent(Instant currentInstant, Duration duration, TimeManager manager, List<Event> triggeredEventsList) {
		this.duration = duration;
		this.manager = manager;
		this.triggeredEventsList = triggeredEventsList;
	}

	@Override
	public void trigger() {
		triggeredEventsList.add(this);
		
		if (manager != null) {
			this.triggeredInstant = manager.getCurrentInstant();
		}
	}

	@Override
	public Duration getDuration() {
		return duration;
	}

	@Override
	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
	
	public Instant getTriggeredInstant() {
		return this.triggeredInstant;
	}
}
