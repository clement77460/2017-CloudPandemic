package fr.efrei.paumier.shared.simulation;

import static org.junit.Assert.*;

import java.time.Clock;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import fr.efrei.paumier.shared.GradingTests;
import fr.efrei.paumier.shared.events.Event;
import fr.efrei.paumier.shared.orders.OrderType;
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
		clock = new FakeClock();
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

	@Test
	public void money_startsAtZero() {
		selector.setDefaultValue(50);
		selector.skipNext(14); //  screenings...
		selector.enqueueRanks(10); // sec 03 - infection		
		selector.enqueueRanks(10); // sec 03 - screening
		
		clock.advance(Duration.ofSeconds(4));
		simulation.update();

		assertEquals(0, simulation.getMoney());
	}

	@Test
	public void money_increasedAtSec05_byInhabitants() {
		selector.setDefaultValue(50);
		selector.skipNext(14); //  screenings...
		selector.enqueueRanks(10); // sec 03 - infection		
		selector.enqueueRanks(10); // sec 03 - screening
		
		clock.advance(Duration.ofSeconds(5));
		simulation.update();

		assertEquals(100, simulation.getMoney());
	}

	@Test
	public void money_increasedAtSec20_byLivingInhabitants() {
		selector.setDefaultValue(50);		
		selector.skipNext(14); //  screenings...
		selector.enqueueRanks(10); // sec 03 - infection		
		selector.skipNext(25); //  screenings...
		selector.enqueueRanks(4); // sec 08 - spreading
		selector.enqueueRanks(4); // sec 08 - screening		
		selector.skipNext(24); //  screenings...
		selector.enqueueRanks(3); // sec 13 - spreading
		selector.enqueueRanks(3); // sec 13 - screening		
		selector.skipNext(24); //  screenings...
		selector.enqueueRanks(2); // sec 18 - spreading
		selector.enqueueRanks(2); // sec 18 - screening	
		selector.skipNext(24); //  screenings...	
		
		clock.advance(Duration.ofSeconds(20));
		simulation.update();

		assertEquals(399, simulation.getMoney());
	}	

	@Test
	public void taxeIncrease_firstTime_costs100() {
		selector.setDefaultValue(-1);
		selector.skipNext(14); //  screenings...
		selector.enqueueRanks(10); // sec 03 - infection		
		selector.enqueueRanks(10); // sec 03 - screening
		
		clock.advance(Duration.ofSeconds(5));
		simulation.update();
		simulation.executeOrder(OrderType.INCREASE_TAXES);

		assertEquals(0, simulation.getMoney());
	}

	@Test
	public void taxeIncrease_firstTime_doubleRevenue_butNotNextOne() {
		selector.setDefaultValue(-1);
		selector.skipNext(14); //  screenings...
		selector.enqueueRanks(10); // sec 03 - infection		
		selector.enqueueRanks(10); // sec 03 - screening

		clock.advance(Duration.ofSeconds(5));
		simulation.update();
		simulation.executeOrder(OrderType.INCREASE_TAXES);
		
		clock.advance(Duration.ofSeconds(10));
		simulation.update();

		assertEquals(300, simulation.getMoney());

		clock.advance(Duration.ofSeconds(5));
		simulation.update();
		assertEquals(500, simulation.getMoney());
	}

	@Test
	public void taxeIncrease_secondTime_costs100() {
		selector.setDefaultValue(-1);
		selector.skipNext(14); //  screenings...
		selector.enqueueRanks(10); // sec 03 - infection		
		selector.enqueueRanks(10); // sec 03 - screening
		
		clock.advance(Duration.ofSeconds(5));
		simulation.update();
		simulation.executeOrder(OrderType.INCREASE_TAXES);

		clock.advance(Duration.ofSeconds(5));
		simulation.update();
		simulation.executeOrder(OrderType.INCREASE_TAXES);
		
		assertEquals(0, simulation.getMoney());
	}

	@Test
	public void taxeIncrease_secondTime_tripleRevenue() {
		selector.setDefaultValue(-1);
		selector.skipNext(14); //  screenings...
		selector.enqueueRanks(10); // sec 03 - infection		
		selector.enqueueRanks(10); // sec 03 - screening
		
		clock.advance(Duration.ofSeconds(5));
		simulation.update();
		simulation.executeOrder(OrderType.INCREASE_TAXES);

		clock.advance(Duration.ofSeconds(5));
		simulation.update();
		simulation.executeOrder(OrderType.INCREASE_TAXES);

		clock.advance(Duration.ofSeconds(10));
		simulation.update();
		
		assertEquals(500, simulation.getMoney());
	}

	@Test
	public void screeningCenter_firstTime_costs100() {
		selector.setDefaultValue(-1);
		selector.skipNext(14); //  screenings...
		selector.enqueueRanks(10); // sec 03 - infection		
		selector.enqueueRanks(10); // sec 03 - screening
		
		clock.advance(Duration.ofSeconds(20));
		simulation.update();		
		
		simulation.executeOrder(OrderType.BUILD_SCREENING_CENTER);	
		assertEquals(300, simulation.getMoney());
	}

	@Test
	public void screeningCenter_secondTime_costs100() {
		selector.setDefaultValue(-1);
		selector.skipNext(14); //  screenings...
		selector.enqueueRanks(10); // sec 03 - infection		
		selector.enqueueRanks(10); // sec 03 - screening
		
		clock.advance(Duration.ofSeconds(20));
		simulation.update();		

		simulation.executeOrder(OrderType.BUILD_SCREENING_CENTER);
		simulation.executeOrder(OrderType.BUILD_SCREENING_CENTER);	
		assertEquals(200, simulation.getMoney());
	}

	@Test
	public void screeningCenter_firstTime_trippleScreeningCapacity() {
		selector.setDefaultValue(-1);		
		selector.skipNext(14); //  screenings...
		selector.enqueueRanks(0); // sec 03 - infection (#0}
		selector.skipNext(25); //  screenings...
		selector.enqueueRanks(0); // sec 08 - spreading (#1)
		selector.skipNext(25); //  screenings...
		selector.enqueueRanks(0, 0); // sec 13 - spreading x 2 (#2 & #3)
		selector.skipNext(25); //  screenings... (starts construction at sec 13.2)
		selector.enqueueRanks(4, 5); // sec 18 - spreading x 3 (#4, #5 & #6)
		selector.skipNext(1); // sec 13 - 1 screening
		selector.enqueueRanks(3, 2, 1); // sec 18.2 (construction completed) - screening x 2 (#2 & #1)

		clock.advance(Duration.ofSeconds(13));
		clock.advance(Duration.ofMillis(200));
		simulation.update();

		simulation.executeOrder(OrderType.BUILD_SCREENING_CENTER);
		simulation.executeOrder(OrderType.BUILD_SCREENING_CENTER);

		clock.advance(Duration.ofMillis(4800));
		simulation.update();		

		assertEquals(0, simulation.getQuarantinedPopulation());
		assertEquals(6, simulation.getInfectedPopulation());
		
		clock.advance(Duration.ofMillis(200));
		simulation.update();

		assertEquals(3, simulation.getQuarantinedPopulation());
	}

	@Test
	public void screeningCenter_secondTime_trippleScreeningCapacity() {
		selector.setDefaultValue(-1);		
		selector.skipNext(14); //  screenings...
		selector.enqueueRanks(0); // sec 03 - infection (#0}
		selector.skipNext(25); //  screenings...
		selector.enqueueRanks(0); // sec 08 - spreading (#1)
		selector.skipNext(25); //  screenings...
		selector.enqueueRanks(0, 0); // sec 13 - spreading x 2 (#2 & #3)
		selector.skipNext(25); //  screenings... (starts construction at sec 13.2)
		selector.enqueueRanks(4, 5); // sec 18 - spreading x 3 (#4, #5 & #6)
		selector.skipNext(1); // sec 13 - 1 screening
		selector.enqueueRanks(3, 2, 1); // sec 18.2 (construction completed) - screening x 2 (#2 & #1)

		clock.advance(Duration.ofSeconds(13));
		clock.advance(Duration.ofMillis(200));
		simulation.update();

		simulation.executeOrder(OrderType.BUILD_SCREENING_CENTER);
		simulation.executeOrder(OrderType.BUILD_SCREENING_CENTER);

		clock.advance(Duration.ofMillis(4800));
		simulation.update();		

		assertEquals(0, simulation.getQuarantinedPopulation());
		assertEquals(6, simulation.getInfectedPopulation());
		
		clock.advance(Duration.ofMillis(200));
		simulation.update();

		assertEquals(3, simulation.getQuarantinedPopulation());
	}

	@Test
	public void longScenario_noScreening_sec30() {
		selector.setDefaultValue(50);
		selector.skipNext(14); //  screenings...
		selector.enqueueRanks(0); // sec 00 - infection		
		selector.skipNext(25); //  screenings...
		selector.enqueueRanks(1); // sec 08 - spreading		
		selector.skipNext(25); //  screenings...
		selector.enqueueRanks(2, 3); // sec 13 - 2 spreadings	
		selector.skipNext(25); //  screenings...
		selector.enqueueRanks(4, 5, 6); // sec 18 - (1 death), 2 spreadings	
		selector.skipNext(25); //  screenings...
		selector.enqueueRanks(7, 8, 9, 10, 11); // sec 23 - (1 death), 5 spreadings	
		selector.skipNext(25); //  screenings...
		selector.enqueueRanks(12, 13, 14, 15, 16, 17, 18, 19); // sec 28 - (2 deaths), 8 spreadings	
		
		clock.advance(Duration.ofSeconds(30));
		simulation.update();
		
		assertEquals(100, simulation.getOriginalPopulation());
		assertEquals(96, simulation.getLivingPopulation());
		assertEquals(16, simulation.getInfectedPopulation());
		assertEquals(0, simulation.getQuarantinedPopulation());
		assertEquals(4, simulation.getDeadPopulation());
	}

	@Test
	public void longScenario_perfectScreening_sec30() {
		selector.setDefaultValue(50);
		selector.skipNext(14); //  screenings...
		selector.enqueueRanks(10); // sec 03 - infection		
		selector.enqueueRanks(10); // sec 03 - screening
		
		clock.advance(Duration.ofSeconds(30));
		simulation.update();
		
		assertEquals(100, simulation.getOriginalPopulation());
		assertEquals(100, simulation.getLivingPopulation());
		assertEquals(0, simulation.getInfectedPopulation());
		assertEquals(0, simulation.getQuarantinedPopulation());
		assertEquals(0, simulation.getDeadPopulation());
	}

	@Test
	public void longScenario_patientZeroDies_noQuarantine() {
		selector.setDefaultValue(50);		
		selector.skipNext(14); //  screenings...
		selector.enqueueRanks(10); // sec 03 - infection		
		selector.skipNext(25); //  screenings...
		selector.enqueueRanks(4); // sec 08 - spreading
		selector.enqueueRanks(4); // sec 08 - screening		
		selector.skipNext(24); //  screenings...
		selector.enqueueRanks(3); // sec 13 - spreading
		selector.enqueueRanks(3); // sec 13 - screening		
		selector.skipNext(25); //  screenings...	
		selector.skipNext(25); //  screenings...	
		selector.skipNext(25); //  screenings...	
		
		clock.advance(Duration.ofSeconds(30));
		simulation.update();
		
		assertEquals(100, simulation.getOriginalPopulation());
		assertEquals(99, simulation.getLivingPopulation());
		assertEquals(0, simulation.getInfectedPopulation());
		assertEquals(0, simulation.getQuarantinedPopulation());
		assertEquals(1, simulation.getDeadPopulation());
	}

	@Test
	public void extraLongScenario_patientZeroDies_someInQuarantine() {
		selector.setDefaultValue(-1); // By default (for screening) screen the last one
		selector.skipNext(14); //  screenings...
		selector.enqueueRanks(0); // sec 00 - infection		
		selector.skipNext(25); //  screenings...
		selector.enqueueRanks(0); // sec 08 - 1 spreading		
		selector.skipNext(25); //  screenings...
		selector.enqueueRanks(0, 0); // sec 13 - 2 spreadings	
		selector.skipNext(25); //  screenings...
		selector.enqueueRanks(0, 0, 0); // sec 18 - (1 death), 3 spreadings
		selector.skipNext(25); //  screenings...
		selector.enqueueRankMultipleTimes(0, 5); // sec 23 - (1 death), 5 spreadings
		selector.skipNext(25); //  screenings...
		selector.enqueueRankMultipleTimes(0, 8); // sec 28 - (2 deaths), 8 spreadings
		selector.skipNext(25); //  screenings...
		
		selector.enqueueRankMultipleTimes(0, 13); // sec 33 - (3 deaths), 13 spreadings
		selector.skipNext(25); //  screenings...	
		selector.enqueueRankMultipleTimes(0, 21); // sec 38 - (5 deaths), 21 spreadings
		selector.skipNext(25); //  screenings...	
		selector.enqueueRankMultipleTimes(0, 34); // sec 43 - (8 deaths), 34 spreadings
		selector.skipNext(25); //  screenings...	
		selector.enqueueRankMultipleTimes(0, 12); // sec 48 - (13 deaths), 12 spreadings (max) 
		selector.skipNext(25); //  screenings... 25 people quarantined
		// sec 53 - (21 deaths, 1 cured)		 
		selector.skipNext(25); //  screenings (and 25 cures)
		// sec 58 - (21 deaths, no infected)	
		
		clock.advanceTo(Duration.ofSeconds(43));
		simulation.update();
		
		assertEquals(100, simulation.getOriginalPopulation());
		assertEquals(80, simulation.getLivingPopulation());
		assertEquals(68, simulation.getInfectedPopulation());
		assertEquals(0, simulation.getQuarantinedPopulation());
		assertEquals(20, simulation.getDeadPopulation());
		
		clock.advanceTo(Duration.ofSeconds(48));
		simulation.update();
		
		assertEquals(100, simulation.getOriginalPopulation());
		assertEquals(67, simulation.getLivingPopulation());
		assertEquals(67, simulation.getInfectedPopulation());
		assertEquals(1, simulation.getQuarantinedPopulation());
		assertEquals(33, simulation.getDeadPopulation());
		
		clock.advanceTo(Duration.ofMillis(52800));
		simulation.update();
		
		assertEquals(100, simulation.getOriginalPopulation());
		assertEquals(67, simulation.getLivingPopulation());
		assertEquals(67, simulation.getInfectedPopulation());
		assertEquals(25, simulation.getQuarantinedPopulation());
		assertEquals(33, simulation.getDeadPopulation());
		
		clock.advanceTo(Duration.ofMillis(53000));
		simulation.update();
		
		assertEquals(100, simulation.getOriginalPopulation());
		assertEquals(46, simulation.getLivingPopulation());
		assertEquals(45, simulation.getInfectedPopulation());
		assertEquals(24, simulation.getQuarantinedPopulation());
		assertEquals(54, simulation.getDeadPopulation());

		clock.advanceTo(Duration.ofMillis(57800));
		simulation.update();
		
		assertEquals(100, simulation.getOriginalPopulation());
		assertEquals(46, simulation.getLivingPopulation());
		assertEquals(21, simulation.getInfectedPopulation());
		assertEquals(0, simulation.getQuarantinedPopulation());
		assertEquals(54, simulation.getDeadPopulation());

		clock.advanceTo(Duration.ofMillis(58000));
		simulation.update();
		
		assertEquals(100, simulation.getOriginalPopulation());
		assertEquals(25, simulation.getLivingPopulation());
		assertEquals(0, simulation.getInfectedPopulation());
		assertEquals(0, simulation.getQuarantinedPopulation());
		assertEquals(75, simulation.getDeadPopulation());

		clock.advanceTo(Duration.ofMillis(60000));
		simulation.update();
		
		assertEquals(100, simulation.getOriginalPopulation());
		assertEquals(25, simulation.getLivingPopulation());
		assertEquals(0, simulation.getInfectedPopulation());
		assertEquals(0, simulation.getQuarantinedPopulation());
		assertEquals(75, simulation.getDeadPopulation());
	}
}
