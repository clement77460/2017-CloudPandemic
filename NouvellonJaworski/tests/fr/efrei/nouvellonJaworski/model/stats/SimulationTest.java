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
	private FakeSelector selector;
	private FakeClock clock;
	private Simulation simulation;
	
	@Before
	public void setUp() {
		clock = new FakeClock(Clock.fixed(Instant.EPOCH,
				ZoneId.systemDefault()));
		selector = new FakeSelector();
		simulation = createSimulation(clock, selector, 100);
	}
	@Override
	protected Simulation createSimulation(Clock clock, Selector selector, int population) {
		return new SimulationImplement(clock,selector,population);
		//return new StatistiqueImplement2(clock,selector,population);
	}
	@Test
	public void starts_everybodyIsHealthy() {
		System.out.println("none");
		assertEquals(100, simulation.getOriginalPopulation());
		assertEquals(100, simulation.getLivingPopulation());
		assertEquals(0, simulation.getInfectedPopulation());
		assertEquals(0, simulation.getQuarantinedPopulation());
		assertEquals(0, simulation.getDeadPopulation()); 
	}
	@Test
	public void sec003_onePersonIsInfected() {
		System.out.println("one person");
		selector.enqueueRanks(0);
		clock.advance(Duration.ofSeconds(3));
		simulation.update();
		
		assertEquals(100, simulation.getOriginalPopulation());
		assertEquals(100, simulation.getLivingPopulation());
		assertEquals(1, simulation.getInfectedPopulation());
		assertEquals(0, simulation.getQuarantinedPopulation());
		assertEquals(0, simulation.getDeadPopulation());
	}
	@Test
	public void sec008_twoPersonsAreInfected() {
		System.out.println("two persons");
		selector.enqueueRanks(0, 0);
		clock.advance(Duration.ofSeconds(8));
		simulation.update();
		
		assertEquals(100, simulation.getOriginalPopulation());
		assertEquals(100, simulation.getLivingPopulation());
		assertEquals(2, simulation.getInfectedPopulation());
		assertEquals(0, simulation.getQuarantinedPopulation());
		assertEquals(0, simulation.getDeadPopulation());
	}

	@Test
	public void sec013_fourPersonsAreInfected() {
		System.out.println("four persons");
		selector.enqueueRanks(0, 0, 0, 0);
		clock.advance(Duration.ofSeconds(13));
		simulation.update();
		
		assertEquals(100, simulation.getOriginalPopulation());
		assertEquals(100, simulation.getLivingPopulation());
		assertEquals(4, simulation.getInfectedPopulation());
		assertEquals(0, simulation.getQuarantinedPopulation());
		assertEquals(0, simulation.getDeadPopulation());
	}

}
