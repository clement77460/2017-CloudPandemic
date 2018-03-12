package fr.efrei.paumier.shared.time;

import java.time.Instant;

public interface TimeManager {
	void update();
	Instant getCurrentInstant();
}
