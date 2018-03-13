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
import org.junit.experimental.categories.Category;

import fr.efrei.paumier.shared.GradingTests;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.selection.FakeSelector;
import fr.efrei.paumier.shared.selection.Selector;
import fr.efrei.paumier.shared.time.FakeClock;

@Category(GradingTests.class)
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
		selector.enqueueRanks(
				1, 1, 1, 1, // sec 00 : 4 screenings
				1, 1, 1, 1, 1, // sec 01 : 5 screenings
				1, 1, 1, 1, 1,  // sec 02 : 5 screenings
				0, 1  // sec 03 : 1 initial infection, 1 screening
				);
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
		selector.enqueueRanks(
			1, 1, 1, 1, // sec 00 : 4 screenings
			3, 3, 3, 3, 3, // sec 01 : 5 screenings
			3, 3, 3, 3, 3, // sec 02 : 5 screenings
			0, 3, 3, 3, 3, 3,  // sec 03 : 1 initial infection, 5 screenings
			3, 3, 3, 3, 3, // sec 04 : 5 screenings
			3, 3, 3, 3, 3, // sec 05 : 5 screenings
			3, 3, 3, 3, 3, // sec 06 : 5 screenings
			3, 3, 3, 3, 3, // sec 07 : 5 screenings
			1, 3 // sec 08 : 1 spreading, 1 screenings
			);
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
		selector.enqueueRanks(
				1, 1, 1, 1, // sec 00 : 4 screenings
				3, 3, 3, 3, 3, // sec 01 : 5 screenings
				3, 3, 3, 3, 3, // sec 02 : 5 screenings
				0, 3, 3, 3, 3, 3,  // sec 03 : 1 initial infection, 5 screenings
				3, 3, 3, 3, 3, // sec 04 : 5 screenings
				3, 3, 3, 3, 3, // sec 05 : 5 screenings
				3, 3, 3, 3, 3, // sec 06 : 5 screenings
				3, 3, 3, 3, 3, // sec 07 : 5 screenings
				1, 3, 3, 3, 3, 3, // sec 08 : 1 spreading, 5 screenings
				3, 3, 3, 3, 3, // sec 09 : 5 screenings
				3, 3, 3, 3, 3, // sec 10 : 5 screenings
				3, 3, 3, 3, 3, // sec 11 : 5 screenings
				3, 3, 3, 3, 3, // sec 12 : 5 screenings
				2, 4, 3 // sec 13 : 2 spreadings, 1 screening
				);
		clock.advance(Duration.ofSeconds(13));
		simulation.update();
		
		assertEquals(100, simulation.getOriginalPopulation());
		assertEquals(100, simulation.getLivingPopulation());
		assertEquals(4, simulation.getInfectedPopulation());
		assertEquals(0, simulation.getQuarantinedPopulation());
		assertEquals(0, simulation.getDeadPopulation());
	}

	@Test
	public void sec018_firstDeath() {
		selector.enqueueRanks(
				1, 1, 1, 1, // sec 00 : 4 screenings
				3, 3, 3, 3, 3, // sec 01 : 5 screenings
				3, 3, 3, 3, 3, // sec 02 : 5 screenings
				0, 3, 3, 3, 3, 3,  // sec 03 : 1 initial infection, 5 screenings
				3, 3, 3, 3, 3, // sec 04 : 5 screenings
				3, 3, 3, 3, 3, // sec 05 : 5 screenings
				3, 3, 3, 3, 3, // sec 06 : 5 screenings
				3, 3, 3, 3, 3, // sec 07 : 5 screenings
				1, 3, 3, 3, 3, 3, // sec 08 : 1 spreading, 5 screenings
				3, 3, 3, 3, 3, // sec 09 : 5 screenings
				3, 3, 3, 3, 3, // sec 10 : 5 screenings
				3, 3, 3, 3, 3, // sec 11 : 5 screenings
				3, 3, 3, 3, 3, // sec 12 : 5 screenings
				2, 4, 3, 3, 3, 3, 3, // sec 13 : 2 spreadings, 5 screening
				3, 3, 3, 3, 3, // sec 14 : 5 screenings
				3, 3, 3, 3, 3, // sec 15 : 5 screenings
				3, 3, 3, 3, 3, // sec 16 : 5 screenings
				3, 3, 3, 3, 3, // sec 17 : 5 screenings
				5, 6, 7, 2  // sec 18 : 3 spreadings, 1 screening
				);
		clock.advance(Duration.ofSeconds(18));
		simulation.update();
		
		assertEquals(100, simulation.getOriginalPopulation());
		assertEquals(99, simulation.getLivingPopulation());
		assertEquals(6, simulation.getInfectedPopulation());
		assertEquals(0, simulation.getQuarantinedPopulation());
		assertEquals(1, simulation.getDeadPopulation());
	}

	@Test
	public void screening_putInQuarantine() {
		selector.enqueueRanks(
				1, 1, 1, 1, // sec 00 : 4 screenings
				3, 3, 3, 3, 3, // sec 01 : 5 screenings
				3, 3, 3, 3, 3, // sec 02 : 5 screenings
				3, 3 // sec 03 : 1 initial infection, 1 screening
				);
		clock.advance(Duration.ofSeconds(3));
		simulation.update();
		
		assertEquals(100, simulation.getOriginalPopulation());
		assertEquals(100, simulation.getLivingPopulation());
		assertEquals(1, simulation.getInfectedPopulation());
		assertEquals(1, simulation.getQuarantinedPopulation());
		assertEquals(0, simulation.getDeadPopulation());
	}

	@Test
	public void screening_early_preventsDeath() {
		selector.enqueueRanks(
				1, 1, 1, 1, // sec 00 : 4 screenings
				3, 3, 3, 3, 3, // sec 01 : 5 screenings
				3, 3, 3, 3, 3, // sec 02 : 5 screenings
				3, 3, 3, 3, 3, 3,  // sec 03 : 1 initial infection, 5 screenings
				3, 3, 3, 3, 3, // sec 04 : 5 screenings
				3, 3, 3, 3, 3, // sec 05 : 5 screenings
				3, 3, 3, 3, 3, // sec 06 : 5 screenings
				3, 3, 3, 3, 3, // sec 07 : 5 screenings
				3, 3); // sec 08 : 1 screening
		clock.advance(Duration.ofSeconds(8));
		simulation.update();
		
		assertEquals(100, simulation.getOriginalPopulation());
		assertEquals(100, simulation.getLivingPopulation());
		assertEquals(0, simulation.getInfectedPopulation());
		assertEquals(0, simulation.getQuarantinedPopulation());
		assertEquals(0, simulation.getDeadPopulation());
	}

	@Test
	public void screening_justInTime_preventsDeath() {
		selector.enqueueRanks(
				1, 1, 1, 1, // sec 00 : 4 screenings
				3, 3, 3, 3, 3, // sec 01 : 5 screenings
				3, 3, 3, 3, 3, // sec 02 : 5 screenings
				0, 3, 3, 3, 3, 3,  // sec 03 : 1 initial infection, 5 screenings
				3, 3, 3, 3, 3, // sec 04 : 5 screenings
				3, 3, 3, 3, 3, // sec 05 : 5 screenings
				3, 3, 3, 3, 3, // sec 06 : 5 screenings
				3, 3, 3, 3, 3, // sec 07 : 5 screenings
				1, 3, 3, 3, 3, 3, // sec 08 : 1 spreading, 5 screenings
				3, 3, 3, 3, 3, // sec 09 : 5 screenings
				3, 3, 3, 3, 3, // sec 10 : 5 screenings
				3, 3, 3, 3, 3, // sec 11 : 5 screenings
				3, 3, 3, 3, 0, // sec 12 : 5 screenings
				2, 50, 50, 50, 50, 50, // sec 13 : 1 spreading, 5 screening
				50, 50, 50, 50, 50, // sec 14 : 5 screenings
				50, 50, 50, 50, 50, // sec 15 : 5 screenings
				50, 50, 50, 50, 50, // sec 16 : 5 screenings
				50, 50, 50, 50, 50, // sec 17 : 5 screenings
				5, 6, 50  // sec 18 : 2 spreadings, 1 screening
				);
		clock.advance(Duration.ofSeconds(18));
		simulation.update();
		
		assertEquals(100, simulation.getOriginalPopulation());
		assertEquals(100, simulation.getLivingPopulation());
		assertEquals(4, simulation.getInfectedPopulation());
		assertEquals(0, simulation.getQuarantinedPopulation());
		assertEquals(0, simulation.getDeadPopulation());
	}

	@Test
	public void screening_late_doesNotPreventsDeath() {
		selector.enqueueRanks(
				1, 1, 1, 1, // sec 00 : 4 screenings
				3, 3, 3, 3, 3, // sec 01 : 5 screenings
				3, 3, 3, 3, 3, // sec 02 : 5 screenings
				0, 3, 3, 3, 3, 3,  // sec 03 : 1 initial infection, 5 screenings
				3, 3, 3, 3, 3, // sec 04 : 5 screenings
				3, 3, 3, 3, 3, // sec 05 : 5 screenings
				3, 3, 3, 3, 3, // sec 06 : 5 screenings
				3, 3, 3, 3, 3, // sec 07 : 5 screenings
				1, 3, 3, 3, 3, 3, // sec 08 : 1 spreading, 5 screenings
				3, 3, 3, 3, 3, // sec 09 : 5 screenings
				3, 3, 3, 3, 3, // sec 10 : 5 screenings
				3, 3, 3, 3, 3, // sec 11 : 5 screenings
				3, 3, 3, 3, 3, // sec 12 : 5 screenings
				2, 4, 0, 50, 50, 50, 50, // sec 13 : 2 spreadings, 5 screening
				50, 50, 50, 50, 50, // sec 14 : 5 screenings
				50, 50, 50, 50, 50, // sec 15 : 5 screenings
				50, 50, 50, 50, 50, // sec 16 : 5 screenings
				50, 50, 50, 50, 50, // sec 17 : 5 screenings
				5, 6, 7, 2  // sec 18 : 3 spreadings, 1 screening
				);
		clock.advance(Duration.ofSeconds(18));
		simulation.update();
		
		assertEquals(100, simulation.getOriginalPopulation());
		assertEquals(99, simulation.getLivingPopulation());
		assertEquals(6, simulation.getInfectedPopulation());
		assertEquals(0, simulation.getQuarantinedPopulation());
		assertEquals(1, simulation.getDeadPopulation());
	}
	
}
