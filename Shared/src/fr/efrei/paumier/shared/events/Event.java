package fr.efrei.paumier.shared.events;

import java.time.Duration;

public interface Event {
	void trigger();
	Duration getDuration();

	default double getRate() {
    	return 1;
    }
}
