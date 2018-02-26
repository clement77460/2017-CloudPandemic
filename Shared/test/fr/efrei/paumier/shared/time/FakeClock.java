package fr.efrei.paumier.shared.time;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

public class FakeClock extends Clock {
	
	private Clock currentClock; 

	public FakeClock(Clock startingClock) {
		setClock(startingClock);
	}
	
	public void setClock(Clock newClock) {
		this.currentClock = newClock;		
	}
	
	public void advance(Duration duration) {
		setClock(Clock.offset(currentClock, duration));
	}

	@Override
	public ZoneId getZone() {
		return this.currentClock.getZone();
	}

	@Override
	public Instant instant() {
		return this.currentClock.instant();
	}

	@Override
	public Clock withZone(ZoneId zone) {
		return new FakeClock(this.currentClock.withZone(zone));
	}
	
}
