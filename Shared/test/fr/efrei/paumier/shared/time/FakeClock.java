package fr.efrei.paumier.shared.time;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

public class FakeClock extends Clock {

	private ZoneId zoneId; 
	private Duration simulatedDuration = Duration.ZERO; 

	public FakeClock() {
		this(ZoneId.systemDefault());
	}

	public FakeClock(ZoneId zoneId) {
		this.zoneId = zoneId;
	}
	
	public void advance(Duration duration) {
		simulatedDuration = simulatedDuration.plus(duration);
	}
	
	public void advanceTo(Duration duration) {
		simulatedDuration = duration;
	}

	@Override
	public ZoneId getZone() {
		return this.zoneId;
	}

	@Override
	public Instant instant() {
		return Instant.EPOCH.plus(simulatedDuration);
	}

	@Override
	public Clock withZone(ZoneId zone) {
		FakeClock clock = new FakeClock(zone);
		clock.advanceTo(simulatedDuration);
		
		return clock;
	}
	
}
