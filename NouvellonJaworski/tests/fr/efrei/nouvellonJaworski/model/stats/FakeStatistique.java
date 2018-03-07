package fr.efrei.nouvellonJaworski.model.stats;
import fr.efrei.nouvellonJaworski.model.entities.*;
import fr.efrei.nouvellonJaworski.model.selection.MySelector;
import fr.efrei.paumier.shared.selection.Selector;
import fr.efrei.paumier.shared.simulation.BaseSimulationTests;
import fr.efrei.paumier.shared.simulation.Simulation;
import fr.efrei.paumier.shared.time.FakeClock;

import static org.junit.Assert.assertEquals;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.Before;
import org.junit.Test;


public class FakeStatistique extends BaseSimulationTests{
	private Simulation stats;
	private Selector selector;
	private FakeClock clock;
	@Before
	public void setUp() {
		clock = new FakeClock(Clock.fixed(Instant.EPOCH,
				ZoneId.systemDefault()));//classe shared
		selector=new MySelector();//ma classe
		stats = createSimulation(clock,selector,100);//ma classe
		
	}
	@Override
	protected Simulation createSimulation(Clock clock, Selector selector, int population) {
		stats=new Statistique(population,(MySelector) selector);
		return stats;
	}
	@Test
	public void starts_everybodyIsHealthy() {
		assertEquals(100, stats.getOriginalPopulation());
		assertEquals(100, stats.getLivingPopulation());
		assertEquals(0, stats.getInfectedPopulation());
		assertEquals(0, stats.getQuarantinedPopulation());
		assertEquals(0, stats.getDeadPopulation());
		System.out.println("test reussi tout le monde est en bonne sante");
	}
	@Test
	public void sec003_onePersonIsInfected() {
		((MySelector) selector).enqueueRanks(0);
		clock.advance(Duration.ofSeconds(13));
		System.out.println(selector);
		stats.update();
		
		assertEquals(1, stats.getInfectedPopulation());
		assertEquals(0, stats.getQuarantinedPopulation());
		assertEquals(0, stats.getDeadPopulation());
		System.out.println("test reussi il y a un infecte");
	}
	@Test
	public void sec008_twoPersonsAreInfected() {
		((MySelector) selector).enqueueRanks(0, 0);
		clock.advance(Duration.ofSeconds(13));
		stats.update();
		
		assertEquals(100, stats.getOriginalPopulation());
		assertEquals(100, stats.getLivingPopulation());
		assertEquals(2, stats.getInfectedPopulation());
		assertEquals(0, stats.getQuarantinedPopulation());
		assertEquals(0, stats.getDeadPopulation());
	}
	@Test
	public void sec013_fourPersonsAreInfected() {
		((MySelector) selector).enqueueRanks(0, 0, 0, 0);
		clock.advance(Duration.ofSeconds(13));
		stats.update();
		
		assertEquals(100, stats.getOriginalPopulation());
		assertEquals(100, stats.getLivingPopulation());
		assertEquals(4, stats.getInfectedPopulation());
		assertEquals(0, stats.getQuarantinedPopulation());
		assertEquals(0, stats.getDeadPopulation());
	}

}
