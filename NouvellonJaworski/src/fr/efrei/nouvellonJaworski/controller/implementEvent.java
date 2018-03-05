package fr.efrei.nouvellonJaworski.controller;
import java.time.Duration;
import fr.efrei.paumier.shared.events.Event;

public class implementEvent implements Event{
	private Duration duration;
	
	
	public void Event() {
	}
	@Override
	public void trigger() {
	}
	
	@Override
	public Duration getDuration() {
		return duration;
	}
	
}
