package fr.efrei.paumier.shared.simulation;

import fr.efrei.paumier.shared.time.TimeManager;

public interface Simulation extends TimeManager {

	int getOriginalPopulation();
	int getLivingPopulation();
	int getInfectedPopulation();
	int getQuarantinedPopulation();
	int getDeadPopulation();
	
}
