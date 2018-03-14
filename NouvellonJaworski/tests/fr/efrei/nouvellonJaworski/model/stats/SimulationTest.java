package fr.efrei.nouvellonJaworski.model.stats;


import static org.junit.Assert.assertEquals;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.Before;
import org.junit.Test;

import fr.efrei.nouvellonJaworski.model.entities.SimulationImplement;
import fr.efrei.paumier.shared.selection.FakeSelector;
import fr.efrei.paumier.shared.selection.Selector;
import fr.efrei.paumier.shared.simulation.BaseSimulationTests;
import fr.efrei.paumier.shared.simulation.Simulation;
import fr.efrei.paumier.shared.time.FakeClock;


public class SimulationTest extends BaseSimulationTests{

	@Override
	protected Simulation createSimulation(Clock clock, Selector selector, int population) {
		return new SimulationImplement(clock, selector, population);
	}
	

}
