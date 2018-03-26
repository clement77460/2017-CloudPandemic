package fr.efrei.nouvellonJaworski.model.stats;

import java.time.Clock;
import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.paumier.shared.domain.CityBorder;
import fr.efrei.paumier.shared.selection.Selector;
import fr.efrei.paumier.shared.simulation.BaseSimulationTests;
import fr.efrei.paumier.shared.simulation.Simulation;


public class SimulationTest extends BaseSimulationTests{


	@Override
	protected Simulation createSimulation(Clock clock, CityBorder border, Selector selector, int population) {
		
		return new SimulationImplement(clock, selector, population);
	}
	

}
