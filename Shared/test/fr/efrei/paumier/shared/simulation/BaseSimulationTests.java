package fr.efrei.paumier.shared.simulation;

import static org.junit.Assert.*;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.selection.FakeSelector;
import fr.efrei.paumier.shared.selection.Selector;
import fr.efrei.paumier.shared.time.FakeClock;

public abstract class BaseSimulationTests {

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
	
	protected List<Event> eventTriggered = new ArrayList<Event>();

	protected abstract Simulation createSimulation(Clock clock, 
			Selector selector, int population);

	@Test
	public void starts_everybodyIsHealthy() {
		assertEquals(100, simulation.getOriginalPopulation());
		assertEquals(100, simulation.getLivingPopulation());
		assertEquals(0, simulation.getInfectedPopulation());
		assertEquals(0, simulation.getQuarantinedPopulation());
		assertEquals(0, simulation.getDeadPopulation());
	}

	@Test
	public void sec003_onePersonIsInfected() {
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
