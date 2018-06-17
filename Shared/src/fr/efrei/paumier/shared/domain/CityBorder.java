package fr.efrei.paumier.shared.domain;

import fr.efrei.paumier.shared.simulation.Statistics;

public interface CityBorder {

	void sendEmigrant(boolean isInfected);
	void sendStatistics(Statistics statistics);

}
